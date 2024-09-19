package com.ryancblock.plextube.controller;

import com.ryancblock.plextube.entity.Channel;
import com.ryancblock.plextube.service.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
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
    public ResponseEntity<Channel> addChannelFromYouTube(@RequestParam String youtubeChannelId, @RequestParam int maxVideos) {
        try {
            Channel channel = channelService.addChannelFromYouTube(youtubeChannelId, maxVideos);
            return ResponseEntity.ok(channel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}