package com.ryancblock.plextube.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class YouTubeService {

    private final YouTube youtube;

    @Value("${youtube.api.key}")
    private String apiKey;

    public YouTubeService(YouTube youtube) {
        this.youtube = youtube;
    }

    public Channel getChannelInfo(String channelId) throws IOException {
        YouTube.Channels.List request = youtube.channels()
                .list(Collections.singletonList("snippet,contentDetails,statistics"))
                .setKey(apiKey)
                .setId(Collections.singletonList(channelId));

        ChannelListResponse response = request.execute();
        if (response.getItems().isEmpty()) {
            return null;
        }
        return response.getItems().get(0);
    }

    public Video getVideoInfo(String videoId) throws IOException {
        YouTube.Videos.List request = youtube.videos()
                .list(Collections.singletonList("snippet,contentDetails,statistics"))
                .setKey(apiKey)
                .setId(Collections.singletonList(videoId));

        VideoListResponse response = request.execute();
        if (response.getItems().isEmpty()) {
            return null;
        }
        return response.getItems().get(0);
    }
}