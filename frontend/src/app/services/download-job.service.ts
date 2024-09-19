import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DownloadJobService {
  private apiUrl = 'http://localhost:8080/api/download-jobs';

  constructor(private http: HttpClient) { }

  getAllDownloadJobs(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getDownloadJobById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getDownloadJobsByStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/status/${status}`).pipe(
      catchError(this.handleError)
    );
  }

  createDownloadJob(downloadJob: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, downloadJob).pipe(
      catchError(this.handleError)
    );
  }

  updateDownloadJob(id: number, downloadJob: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, downloadJob).pipe(
      catchError(this.handleError)
    );
  }

  deleteDownloadJob(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    throw error;
  }
}
