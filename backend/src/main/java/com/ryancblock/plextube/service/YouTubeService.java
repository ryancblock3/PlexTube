package com.ryancblock.plextube.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
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
        
        if (channelIdOrHandle.startsWith("@") || !channelIdOrHandle.startsWith("UC")) {
            return getChannelByHandle(channelIdOrHandle);
        } else {
            return getChannelById(channelIdOrHandle);
        }
    }

    private Channel getChannelById(String channelId) throws IOException {
        logger.info("Attempting to get channel directly by ID: {}", channelId);
        ChannelListResponse response = youtube.channels()
                .list(Collections.singletonList("snippet,contentDetails,statistics"))
                .setKey(apiKey)
                .setId(Collections.singletonList(channelId))
                .execute();

        return processChannelResponse(response, "ID", channelId);
    }

    private Channel getChannelByHandle(String handle) throws IOException {
        // Remove '@' if it's present at the beginning
        String cleanHandle = handle.startsWith("@") ? handle.substring(1) : handle;
        logger.info("Attempting to get channel by handle: {}", cleanHandle);
        
        ChannelListResponse response = youtube.channels()
                .list(Collections.singletonList("snippet,contentDetails,statistics"))
                .setKey(apiKey)
                .setForHandle(cleanHandle)
                .execute();

        return processChannelResponse(response, "handle", handle);
    }

    private Channel processChannelResponse(ChannelListResponse response, String type, String value) {
        logger.debug("API Response: {}", response);

        if (response.getItems() != null && !response.getItems().isEmpty()) {
            logger.info("Channel found by {}", type);
            return response.getItems().get(0);
        }
        
        logger.warn("No channel found for {} : {}", type, value);
        return null;
    }
}