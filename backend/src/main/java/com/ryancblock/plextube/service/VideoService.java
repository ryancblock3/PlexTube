package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.Video;
import com.ryancblock.plextube.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(Integer id) {
        return videoRepository.findById(id);
    }

    public Optional<Video> getVideoByYoutubeId(String youtubeVideoId) {
        return videoRepository.findByYoutubeVideoId(youtubeVideoId);
    }

    public List<Video> getVideosByChannelId(Integer channelId) {
        return videoRepository.findByChannelId(channelId);
    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public void deleteVideo(Integer id) {
        videoRepository.deleteById(id);
    }
}