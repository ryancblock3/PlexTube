package com.ryancblock.plextube.repository;

import com.ryancblock.plextube.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    Channel findByYoutubeChannelId(String youtubeChannelId);
}