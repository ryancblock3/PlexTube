package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.Channel;
import com.ryancblock.plextube.repository.ChannelRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.ZonedDateTime;

@Service
public class ChannelService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    private final ChannelRepository channelRepository;
    private final YouTubeService youTubeService;

    public ChannelService(ChannelRepository channelRepository, YouTubeService youTubeService) {
        this.channelRepository = channelRepository;
        this.youTubeService = youTubeService;
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public Page<Channel> getChannels(Pageable pageable) {
        return channelRepository.findAll(pageable);
    }

    public Optional<Channel> getChannelById(Integer id) {
        return channelRepository.findById(id);
    }

    public Channel getChannelByYoutubeId(String youtubeChannelId) {
        return channelRepository.findByYoutubeChannelId(youtubeChannelId);
    }

    public Channel saveChannel(Channel channel) {
        if (channel.getCreatedAt() == null) {
            channel.setCreatedAt(ZonedDateTime.now());
        }
        channel.setUpdatedAt(ZonedDateTime.now());
        return channelRepository.save(channel);
    }

    public void deleteChannel(Integer id) {
        channelRepository.deleteById(id);
    }

    public Channel addChannelFromYouTube(String youtubeChannelUrl, int maxVideos) throws IOException {
        logger.info("Adding channel from YouTube URL: {}", youtubeChannelUrl);
        String channelIdOrHandle = extractChannelIdOrHandle(youtubeChannelUrl);
        logger.info("Extracted channel ID or handle: {}", channelIdOrHandle);
        
        com.google.api.services.youtube.model.Channel youtubeChannel = youTubeService.getChannelInfo(channelIdOrHandle);
        if (youtubeChannel == null) {
            logger.warn("YouTube channel not found or not accessible: {}", channelIdOrHandle);
            throw new IllegalArgumentException("YouTube channel not found or not accessible: " + channelIdOrHandle);
        }

        String youtubeChannelId = youtubeChannel.getId();
        logger.info("YouTube channel ID: {}", youtubeChannelId);

        Channel existingChannel = channelRepository.findByYoutubeChannelId(youtubeChannelId);
        if (existingChannel != null) {
            logger.warn("Channel already exists in the database: {}", youtubeChannelId);
            throw new IllegalStateException("Channel already exists in the database");
        }

        Channel channel = new Channel();
        channel.setYoutubeChannelId(youtubeChannelId);
        channel.setName(youtubeChannel.getSnippet().getTitle());
        channel.setDescription(youtubeChannel.getSnippet().getDescription());
        
        if (youtubeChannel.getSnippet().getThumbnails() != null && 
            youtubeChannel.getSnippet().getThumbnails().getDefault() != null) {
            channel.setThumbnailUrl(youtubeChannel.getSnippet().getThumbnails().getDefault().getUrl());
        }
        
        channel.setMaxVideos(maxVideos);

        logger.info("Saving new channel: {}", channel.getName());
        return channelRepository.save(channel);
    }

    private String extractChannelIdOrHandle(String url) {
        logger.debug("Extracting channel ID or handle from URL: {}", url);
        Pattern pattern = Pattern.compile("(?:https?://)?(?:www\\.)?(?:youtube\\.com|youtu\\.be)/(?:channel/|user/|c/|@)?([\\w-]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String result = matcher.group(1);
            logger.debug("Extracted: {}", result);
            return result;
        }
        logger.debug("No match found, returning original URL");
        return url;
    }

    public Channel updateChannel(Integer id, Channel channelDetails) {
        return channelRepository.findById(id)
            .map(channel -> {
                channel.setName(channelDetails.getName());
                channel.setDescription(channelDetails.getDescription());
                channel.setMaxVideos(channelDetails.getMaxVideos());
                channel.setUpdatedAt(ZonedDateTime.now());
                return channelRepository.save(channel);
            })
            .orElseThrow(() -> new IllegalArgumentException("Channel not found with id: " + id));
    }
}