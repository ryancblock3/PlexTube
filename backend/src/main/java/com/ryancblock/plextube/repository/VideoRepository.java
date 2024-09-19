package com.ryancblock.plextube.repository;

import com.ryancblock.plextube.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByChannelId(Integer channelId);
    Optional<Video> findByYoutubeVideoId(String youtubeVideoId);
}