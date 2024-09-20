import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Channel } from '../interfaces/channel.interface';

@Injectable({
  providedIn: 'root'
})
export class ChannelService {
  private apiUrl = 'http://localhost:8080/api/channels'; // Adjust this URL as needed

  constructor(private http: HttpClient) { }

  getChannels(page: number, pageSize: number): Observable<{ channels: Channel[], totalPages: number }> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('pageSize', pageSize.toString());

    return this.http.get<{ channels: Channel[], totalPages: number }>(`${this.apiUrl}`, { params });
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

  addChannelFromYouTube(url: string, maxVideos: number): Observable<Channel> {
    const params = new HttpParams()
      .set('youtubeChannelUrl', url)
      .set('maxVideos', maxVideos.toString());

    return this.http.post<Channel>(`${this.apiUrl}/youtube`, null, { params })
      .pipe(
        catchError(error => {
          console.error('Error adding YouTube channel:', error);
          let errorMessage = 'Failed to add YouTube channel';
          if (error.error && error.error.message) {
            errorMessage = error.error.message;
          } else if (error.status === 400) {
            errorMessage = 'Invalid YouTube channel URL or maximum videos value';
          } else if (error.status === 500) {
            errorMessage = 'Server error occurred while adding the channel';
          }
          return throwError(() => new Error(errorMessage));
        })
      );
  }
}
