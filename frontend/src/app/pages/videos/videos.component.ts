import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VideoService } from '../../services/video.service';

interface Video {
  id: number;
  title: string;
  channelName: string;
  thumbnailUrl: string;
  publishDate: string;
  duration: number;
}

@Component({
  selector: 'app-videos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './videos.component.html',
  styleUrls: ['./videos.component.css']
})
export class VideosComponent implements OnInit {
  videos: Video[] = [];

  constructor(private videoService: VideoService) { }

  ngOnInit(): void {
    this.loadVideos();
  }

  loadVideos(): void {
    this.videoService.getAllVideos().subscribe({
      next: (videos: Video[]) => {
        this.videos = videos;
      },
      error: (error: any) => {
        console.error('Error fetching videos:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }

  playVideo(video: Video): void {
    // Implement video playback logic
    console.log('Playing video:', video.title);
  }

  openAddVideoModal(): void {
    // Implement logic to open add video modal
    console.log('Open add video modal');
    // You might want to open a modal dialog here or navigate to a new page
    // for adding a video, depending on your application's design
  }
}
