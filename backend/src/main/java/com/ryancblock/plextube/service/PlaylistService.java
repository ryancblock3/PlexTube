package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.Playlist;
import com.ryancblock.plextube.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }

    public Playlist getPlaylistByYoutubeId(String youtubePlaylistId) {
        return playlistRepository.findByYoutubePlaylistId(youtubePlaylistId);
    }

    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Integer id) {
        playlistRepository.deleteById(id);
    }
}