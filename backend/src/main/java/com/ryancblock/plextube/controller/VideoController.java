package com.ryancblock.plextube.controller;

import com.ryancblock.plextube.entity.Video;
import com.ryancblock.plextube.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Integer id) {
        return videoService.getVideoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/channel/{channelId}")
    public List<Video> getVideosByChannelId(@PathVariable Integer channelId) {
        return videoService.getVideosByChannelId(channelId);
    }

    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return videoService.saveVideo(video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Integer id, @RequestBody Video videoDetails) {
        return videoService.getVideoById(id)
                .map(video -> {
                    video.setTitle(videoDetails.getTitle());
                    video.setDescription(videoDetails.getDescription());
                    video.setFilePath(videoDetails.getFilePath());
                    video.setFileSize(videoDetails.getFileSize());
                    video.setVideoQuality(videoDetails.getVideoQuality());
                    return ResponseEntity.ok(videoService.saveVideo(video));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Integer id) {
        return videoService.getVideoById(id)
                .map(video -> {
                    videoService.deleteVideo(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}