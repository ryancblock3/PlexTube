import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Channel } from '../interfaces/channel.interface';

@Injectable({
  providedIn: 'root'
})
export class ChannelService {
  private apiUrl = 'http://localhost:8080/api/channels'; // Adjust this URL as needed

  constructor(private http: HttpClient) { }

  getChannels(): Observable<Channel[]> {
    return this.http.get<Channel[]>(this.apiUrl);
  }

  getChannelById(id: number): Observable<Channel> {
    return this.http.get<Channel>(`${this.apiUrl}/${id}`);
  }

  createChannel(channel: Partial<Channel>): Observable<Channel> {
    return this.http.post<Channel>(this.apiUrl, channel);
  }

  updateChannel(id: number, channel: Partial<Channel>): Observable<Channel> {
    return this.http.put<Channel>(`${this.apiUrl}/${id}`, channel);
  }

  deleteChannel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  addChannelFromYouTube(youtubeChannelId: string, maxVideos: number): Observable<Channel> {
    return this.http.post<Channel>(`${this.apiUrl}/youtube`, null, {
      params: { youtubeChannelId, maxVideos: maxVideos.toString() }
    });
  }
}
