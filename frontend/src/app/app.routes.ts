import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  // We'll add more routes here as we create other components
  { path: 'channels', loadComponent: () => import('./pages/channels/channels.component').then(m => m.ChannelsComponent) },
  { path: 'videos', loadComponent: () => import('./pages/videos/videos.component').then(m => m.VideosComponent) },
  { path: 'playlists', loadComponent: () => import('./pages/playlists/playlists.component').then(m => m.PlaylistsComponent) },
  { path: 'settings', loadComponent: () => import('./pages/settings/settings.component').then(m => m.SettingsComponent) },
  { path: '**', redirectTo: '' } // Redirect any unknown routes to home
];
