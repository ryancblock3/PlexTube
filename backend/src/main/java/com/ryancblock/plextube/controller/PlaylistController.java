package com.ryancblock.plextube.controller;

import com.ryancblock.plextube.entity.Playlist;
import com.ryancblock.plextube.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer id) {
        return playlistService.getPlaylistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.savePlaylist(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Integer id, @RequestBody Playlist playlistDetails) {
        return playlistService.getPlaylistById(id)
                .map(playlist -> {
                    playlist.setName(playlistDetails.getName());
                    playlist.setDescription(playlistDetails.getDescription());
                    playlist.setMaxVideos(playlistDetails.getMaxVideos());
                    return ResponseEntity.ok(playlistService.savePlaylist(playlist));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Integer id) {
        return playlistService.getPlaylistById(id)
                .map(playlist -> {
                    playlistService.deletePlaylist(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}