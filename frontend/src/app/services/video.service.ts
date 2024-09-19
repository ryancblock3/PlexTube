import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class VideoService {
  private apiUrl = 'http://localhost:8080/api/videos';

  constructor(private http: HttpClient) { }

  getAllVideos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getVideoById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getVideosByChannelId(channelId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/channel/${channelId}`).pipe(
      catchError(this.handleError)
    );
  }

  createVideo(video: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, video).pipe(
      catchError(this.handleError)
    );
  }

  updateVideo(id: number, video: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, video).pipe(
      catchError(this.handleError)
    );
  }

  deleteVideo(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    throw error;
  }
}
