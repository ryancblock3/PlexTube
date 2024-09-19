import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-channel-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-channel-modal.component.html',
  styleUrls: ['./add-channel-modal.component.css']
})
export class AddChannelModalComponent {
  @Input() isVisible = false;
  @Output() closeModal = new EventEmitter<void>();
  @Output() addChannel = new EventEmitter<{url: string, maxVideos: number}>();

  channelUrl = '';
  maxVideos = 10;

  close() {
    this.closeModal.emit();
  }

  onSubmit() {
    if (this.channelUrl && this.maxVideos > 0) {
      this.addChannel.emit({ url: this.channelUrl, maxVideos: this.maxVideos });
      this.channelUrl = '';
      this.maxVideos = 10;
      this.close();
    }
  }
}
