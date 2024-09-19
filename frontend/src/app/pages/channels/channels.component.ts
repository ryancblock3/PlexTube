import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddChannelModalComponent } from '../../components/add-channel-modal/add-channel-modal.component';
import { ChannelService } from '../../services/channel.service';
import { Channel } from '../../interfaces/channel.interface';

@Component({
  selector: 'app-channels',
  standalone: true,
  imports: [CommonModule, AddChannelModalComponent],
  templateUrl: './channels.component.html',
  styleUrls: ['./channels.component.css']
})
export class ChannelsComponent implements OnInit {
  channels: Channel[] = [];
  isAddChannelModalVisible = false;

  constructor(private channelService: ChannelService) { }

  ngOnInit(): void {
    this.loadChannels();
  }

  loadChannels(): void {
    this.channelService.getChannels().subscribe({
      next: (channels) => {
        this.channels = channels;
      },
      error: (error) => {
        console.error('Error fetching channels:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }

  openAddChannelModal() {
    this.isAddChannelModalVisible = true;
  }

  closeAddChannelModal() {
    this.isAddChannelModalVisible = false;
  }

  addNewChannel(channelData: {url: string, maxVideos: number}) {
    // Extract YouTube channel ID from URL (you might want to improve this regex)
    const youtubeChannelId = channelData.url.match(/channel\/([^\/]+)/)?.[1];

    if (!youtubeChannelId) {
      console.error('Invalid YouTube channel URL');
      return;
    }

    this.channelService.addChannelFromYouTube(youtubeChannelId, channelData.maxVideos).subscribe({
      next: (newChannel) => {
        this.channels.unshift(newChannel);
        this.closeAddChannelModal();
      },
      error: (error) => {
        console.error('Error adding channel:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }

  openChannelDetails(channel: Channel) {
    // Implement the logic to open channel details
    console.log('Open channel details for', channel.name);
  }

  deleteChannel(channel: Channel) {
    this.channelService.deleteChannel(channel.id).subscribe({
      next: () => {
        this.channels = this.channels.filter(c => c.id !== channel.id);
      },
      error: (error) => {
        console.error('Error deleting channel:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }
}
