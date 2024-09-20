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
  isLoading = false;
  error: string | null = null;
  currentPage = 0; // Changed to 0-based indexing
  pageSize = 9;
  totalPages = 1;

  constructor(private channelService: ChannelService) { }

  ngOnInit(): void {
    this.loadChannels();
  }

  loadChannels(): void {
    this.isLoading = true;
    this.error = null;
    this.channelService.getChannels(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.channels = response.channels;
        this.totalPages = response.totalPages;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error fetching channels:', error);
        this.error = 'Failed to load channels. Please try again.';
        this.isLoading = false;
      }
    });
  }

  openAddChannelModal() {
    this.isAddChannelModalVisible = true;
  }

  closeAddChannelModal() {
    this.isAddChannelModalVisible = false;
  }

  addNewChannel(channelData: { url: string, maxVideos: number }) {
    this.isLoading = true;
    this.error = null;
    this.channelService.addChannelFromYouTube(channelData.url, channelData.maxVideos).subscribe({
      next: (newChannel: Channel) => {
        if (this.channels) {
          this.channels.unshift(newChannel);
        } else {
          this.channels = [newChannel];
        }
        this.closeAddChannelModal();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error adding channel:', error);
        if (error.status === 400) {
          this.error = error.error.message || 'Invalid input. Please check your YouTube channel URL and try again.';
        } else if (error.status === 409) {
          this.error = 'This channel has already been added to the database.';
        } else if (error.status === 500) {
          this.error = 'Server error occurred. Please try again later.';
        } else {
          this.error = 'An unexpected error occurred. Please try again.';
        }
        this.isLoading = false;
      }
    });
  }

  openChannelDetails(channel: Channel) {
    // Implement the logic to open channel details
    console.log('Open channel details for', channel.name);
  }

  deleteChannel(channel: Channel) {
    if (confirm(`Are you sure you want to delete ${channel.name}?`)) {
      this.isLoading = true;
      this.error = null;
      this.channelService.deleteChannel(channel.id).subscribe({
        next: () => {
          this.channels = this.channels.filter(c => c.id !== channel.id);
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error deleting channel:', error);
          this.error = 'Failed to delete channel. Please try again.';
          this.isLoading = false;
        }
      });
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadChannels();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadChannels();
    }
  }
}
