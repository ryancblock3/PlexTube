import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {
  private apiUrl = 'http://localhost:8080/api/playlists';

  constructor(private http: HttpClient) { }

  getAllPlaylists(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getPlaylistById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  createPlaylist(playlist: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, playlist).pipe(
      catchError(this.handleError)
    );
  }

  updatePlaylist(id: number, playlist: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, playlist).pipe(
      catchError(this.handleError)
    );
  }

  deletePlaylist(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    throw error;
  }
}
