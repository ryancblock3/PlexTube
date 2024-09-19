import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlaylistService } from '../../services/playlist.service';

interface Playlist {
  id: number;
  name: string;
  description: string;
  thumbnailUrl: string;
  videoCount: number;
}

@Component({
  selector: 'app-playlists',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './playlists.component.html',
  styleUrls: ['./playlists.component.css']
})
export class PlaylistsComponent implements OnInit {
  playlists: Playlist[] = [];

  constructor(private playlistService: PlaylistService) { }

  ngOnInit(): void {
    this.loadPlaylists();
  }

  loadPlaylists(): void {
    this.playlistService.getAllPlaylists().subscribe({
      next: (playlists: Playlist[]) => {
        this.playlists = playlists;
      },
      error: (error: any) => {
        console.error('Error fetching playlists:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }

  openAddPlaylistModal(): void {
    // Implement logic to open add playlist modal
    console.log('Open add playlist modal');
  }

  viewPlaylist(playlist: Playlist): void {
    // Implement logic to view playlist details
    console.log('View playlist:', playlist.name);
  }
}
