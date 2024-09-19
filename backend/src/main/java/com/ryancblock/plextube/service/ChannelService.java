package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.Channel;
import com.ryancblock.plextube.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public Channel addChannelFromYouTube(String youtubeChannelId, int maxVideos) throws IOException {
        com.google.api.services.youtube.model.Channel youtubeChannel = youTubeService.getChannelInfo(youtubeChannelId);
        if (youtubeChannel == null) {
            throw new IllegalArgumentException("YouTube channel not found");
        }

        Channel channel = new Channel();
        channel.setYoutubeChannelId(youtubeChannelId);
        channel.setName(youtubeChannel.getSnippet().getTitle());
        channel.setDescription(youtubeChannel.getSnippet().getDescription());
        channel.setThumbnailUrl(youtubeChannel.getSnippet().getThumbnails().getDefault().getUrl());
        channel.setMaxVideos(maxVideos);

        return channelRepository.save(channel);
    }
}