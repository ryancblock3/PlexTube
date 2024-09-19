package com.ryancblock.plextube.repository;

import com.ryancblock.plextube.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Playlist findByYoutubePlaylistId(String youtubePlaylistId);
}