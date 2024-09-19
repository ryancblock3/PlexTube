package com.ryancblock.plextube.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "youtube_playlist_id", unique = true, nullable = false)
    private String youtubePlaylistId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "max_videos", nullable = false)
    private Integer maxVideos;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYoutubePlaylistId() {
        return youtubePlaylistId;
    }

    public void setYoutubePlaylistId(String youtubePlaylistId) {
        this.youtubePlaylistId = youtubePlaylistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxVideos() {
        return maxVideos;
    }

    public void setMaxVideos(Integer maxVideos) {
        this.maxVideos = maxVideos;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
