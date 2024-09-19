import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SettingsService } from '../../services/settings.service';

interface Settings {
  downloadPath: string;
  maxConcurrentDownloads: number;
  defaultVideoQuality: string;
}

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  settings: Settings = {
    downloadPath: '',
    maxConcurrentDownloads: 3,
    defaultVideoQuality: '720p'
  };

  constructor(private settingsService: SettingsService) { }

  ngOnInit(): void {
    this.loadSettings();
  }

  loadSettings(): void {
    this.settingsService.getSettings().subscribe({
      next: (settings) => {
        this.settings = settings;
      },
      error: (error) => {
        console.error('Error loading settings:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }

  saveSettings(): void {
    this.settingsService.updateSettings(this.settings).subscribe({
      next: () => {
        console.log('Settings saved successfully');
        // Optionally, show a success message to the user
      },
      error: (error) => {
        console.error('Error saving settings:', error);
        // Handle error (e.g., show error message to user)
      }
    });
  }
}
