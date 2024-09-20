package com.ryancblock.plextube.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ryancblock.plextube.entity.Channel;
import com.ryancblock.plextube.service.ChannelService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllChannels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Channel> channelPage = channelService.getChannels(PageRequest.of(page, size));
        
        Map<String, Object> response = new HashMap<>();
        response.put("channels", channelPage.getContent());
        response.put("currentPage", channelPage.getNumber());
        response.put("totalItems", channelPage.getTotalElements());
        response.put("totalPages", channelPage.getTotalPages());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable Integer id) {
        return channelService.getChannelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Channel createChannel(@RequestBody Channel channel) {
        return channelService.saveChannel(channel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable Integer id, @RequestBody Channel channelDetails) {
        return channelService.getChannelById(id)
                .map(channel -> {
                    channel.setName(channelDetails.getName());
                    channel.setDescription(channelDetails.getDescription());
                    channel.setMaxVideos(channelDetails.getMaxVideos());
                    return ResponseEntity.ok(channelService.saveChannel(channel));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Integer id) {
        return channelService.getChannelById(id)
                .map(channel -> {
                    channelService.deleteChannel(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/youtube")
    public ResponseEntity<?> addChannelFromYouTube(@RequestParam String youtubeChannelUrl,
            @RequestParam int maxVideos) {
        if (youtubeChannelUrl == null || youtubeChannelUrl.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("YouTube channel URL is required"));
        }
        if (maxVideos <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Maximum videos must be greater than 0"));
        }
        try {
            Channel channel = channelService.addChannelFromYouTube(youtubeChannelUrl, maxVideos);
            return ResponseEntity.ok(channel);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid YouTube channel URL: {}", youtubeChannelUrl, e);
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (IllegalStateException e) {
            logger.warn("Channel already exists: {}", youtubeChannelUrl, e);
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(new ErrorResponse(e.getMessage()));
        } catch (IOException e) {
            logger.error("Error fetching YouTube channel info: {}", youtubeChannelUrl, e);
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error fetching YouTube channel info. Please try again later."));
        } catch (Exception e) {
            logger.error("Unexpected error while adding YouTube channel: {}", youtubeChannelUrl, e);
            return ResponseEntity.internalServerError().body(new ErrorResponse("An unexpected error occurred. Please try again later."));
        }
    }

    // Create a simple ErrorResponse class
    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        @JsonProperty("message")
        public String getMessage() {
            return message;
        }
    }
}