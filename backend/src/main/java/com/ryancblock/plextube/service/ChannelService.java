package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.Channel;
import com.ryancblock.plextube.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final YouTubeService youTubeService;

    public ChannelService(ChannelRepository channelRepository, YouTubeService youTubeService) {
        this.channelRepository = channelRepository;
        this.youTubeService = youTubeService;
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public Optional<Channel> getChannelById(Integer id) {
        return channelRepository.findById(id);
    }

    public Channel getChannelByYoutubeId(String youtubeChannelId) {
        return channelRepository.findByYoutubeChannelId(youtubeChannelId);
    }

    public Channel saveChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    public void deleteChannel(Integer id) {
        channelRepository.deleteById(id);
    }

    public Channel addChannelFromYouTube(String youtubeChannelUrl, int maxVideos) throws IOException {
        String channelIdOrHandle = extractChannelIdOrHandle(youtubeChannelUrl);
        com.google.api.services.youtube.model.Channel youtubeChannel = youTubeService.getChannelInfo(channelIdOrHandle);
        if (youtubeChannel == null) {
            throw new IllegalArgumentException("YouTube channel not found or not accessible: " + channelIdOrHandle);
        }

        String youtubeChannelId = youtubeChannel.getId();
        Channel existingChannel = channelRepository.findByYoutubeChannelId(youtubeChannelId);
        if (existingChannel != null) {
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

        return channelRepository.save(channel);
    }

    private String extractChannelIdOrHandle(String url) {
        Pattern pattern = Pattern.compile("(?:https?://)?(?:www\\.)?(?:youtube\\.com|youtu\\.be)/(?:channel/|user/|c/|@)?([\\w-]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String result = matcher.group(1);
            return result.startsWith("@") ? result : result.toLowerCase();
        }
        // If no match found, return the original URL (it might be a direct channel ID or handle)
        return url;
    }
}