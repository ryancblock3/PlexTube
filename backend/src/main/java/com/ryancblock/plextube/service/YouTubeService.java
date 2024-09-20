package com.ryancblock.plextube.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class YouTubeService {

    private static final Logger logger = LoggerFactory.getLogger(YouTubeService.class);

    private final YouTube youtube;

    @Value("${youtube.api.key}")
    private String apiKey;

    public YouTubeService(YouTube youtube) {
        this.youtube = youtube;
    }

    public Channel getChannelInfo(String channelIdOrHandle) throws IOException {
        logger.info("Fetching channel info for: {}", channelIdOrHandle);
        try {
            YouTube.Channels.List request = youtube.channels()
                    .list(Collections.singletonList("snippet,contentDetails,statistics"))
                    .setKey(apiKey);

            if (channelIdOrHandle.startsWith("@") || !channelIdOrHandle.matches("[A-Za-z0-9_-]+")) {
                // It's a handle or custom URL, so we need to search for it
                YouTube.Search.List searchRequest = youtube.search()
                        .list(Collections.singletonList("snippet"))
                        .setKey(apiKey)
                        .setQ(channelIdOrHandle)
                        .setType(Collections.singletonList("channel"))
                        .setMaxResults(1L);

                com.google.api.services.youtube.model.SearchListResponse searchResponse = searchRequest.execute();
                if (searchResponse.getItems().isEmpty()) {
                    logger.warn("No channel found for: {}", channelIdOrHandle);
                    return null;
                }
                String channelId = searchResponse.getItems().get(0).getSnippet().getChannelId();
                request.setId(Collections.singletonList(channelId));
            } else {
                // Try both as channel ID and as username
                request.setId(Collections.singletonList(channelIdOrHandle));
                ChannelListResponse response = request.execute();
                if (response.getItems() == null || response.getItems().isEmpty()) {
                    request.setId(null).setForUsername(channelIdOrHandle);
                }
            }

            ChannelListResponse response = request.execute();
            logger.debug("API Response for channel {}: {}", channelIdOrHandle, response);

            if (response.getItems() == null || response.getItems().isEmpty()) {
                logger.warn("No channel found for: {}", channelIdOrHandle);
                return null;
            }
            return response.getItems().get(0);
        } catch (IOException e) {
            logger.error("Error fetching channel info for: " + channelIdOrHandle, e);
            throw e;
        }
    }

    public Video getVideoInfo(String videoId) throws IOException {
        logger.info("Fetching video info for ID: {}", videoId);
        try {
            YouTube.Videos.List request = youtube.videos()
                    .list(Collections.singletonList("snippet,contentDetails,statistics"))
                    .setKey(apiKey)
                    .setId(Collections.singletonList(videoId));

            VideoListResponse response = request.execute();
            logger.debug("API Response for video {}: {}", videoId, response);

            if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
                logger.warn("No video found for ID: {}", videoId);
                return null;
            }
            return response.getItems().get(0);
        } catch (IOException e) {
            logger.error("Error fetching video info for ID: " + videoId, e);
            throw e;
        }
    }
}