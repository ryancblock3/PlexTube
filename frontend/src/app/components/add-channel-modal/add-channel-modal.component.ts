import { Component, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-channel-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-channel-modal.component.html',
  styleUrls: ['./add-channel-modal.component.css']
})
export class AddChannelModalComponent {
  @Input() isVisible: boolean = false;
  @Output() addChannel = new EventEmitter<{ url: string, maxVideos: number }>();
  @Output() closeModal = new EventEmitter<void>();

  url: string = '';
  maxVideos: number = 10;

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.addChannel.emit({
        url: this.url,
        maxVideos: this.maxVideos
      });
      this.resetForm();
    }
  }

  onClose() {
    this.closeModal.emit();
    this.resetForm();
  }

  private resetForm() {
    this.url = '';
    this.maxVideos = 10;
  }
}
