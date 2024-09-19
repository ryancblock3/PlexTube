# Plextube Project Summary - Updated

## Project Overview
Plextube is a YouTube video downloader application that allows users to manage and download videos from YouTube channels and playlists. It's designed to be hosted on a personal Unraid server and integrated with Plex for media consumption.

## Technology Stack
1. Frontend: Angular 17 with Tailwind CSS
2. Backend: Spring Boot 3.3.4 (Java 17)
3. Database: PostgreSQL
4. ORM: Spring Data JPA
5. Database Migration: Liquibase
6. YouTube Integration: YouTube Data API v3, youtube-dl or yt-dlp (to be implemented)

## Current Progress

### Backend
1. Spring Boot application initialized and configured
2. PostgreSQL database integrated with Liquibase for schema management
3. Entity classes created for all database tables:
   - User
   - Channel
   - Video
   - Playlist
   - DownloadJob
4. Repository interfaces created for all entities
5. Service layer implemented with CRUD operations and business logic for all entities
6. REST Controllers implemented with basic CRUD operations for all entities:
   - ChannelController
   - VideoController
   - PlaylistController
   - DownloadJobController
7. Basic YouTube API integration for fetching channel and video information
8. Environment configuration set up (.gitignore, .env file, application.properties)
9. CORS configuration added to allow requests from the Angular frontend

### Frontend
1. Angular project initialized with Angular 17 and Tailwind CSS
2. Standalone components approach adopted
3. Basic routing implemented in app.routes.ts
4. Services created for API interactions:
   - ChannelService
   - VideoService
   - PlaylistService
   - DownloadJobService
5. Components created and partially implemented:
   - HeaderComponent
   - FooterComponent
   - HomeComponent
   - ChannelsComponent
   - VideosComponent
   - PlaylistsComponent
   - SettingsComponent (basic structure)
6. Dark mode implemented by default

## Core Functionality (Implemented)
1. Channel management (add, view, update, delete)
2. Video management (view, update, delete)
3. Playlist management (add, view, update, delete)
4. Basic error handling and console logging

## Core Functionality (To Be Implemented)
1. Channel-based downloading with video limits
2. Automatic video rotation (delete oldest, download newest)
3. Specific video downloading via URL
4. Playlist support for downloading entire YouTube playlists
5. Video quality options and subtitle downloading
6. Metadata scraping (descriptions, tags, upload dates)
7. Flexible storage locations for development and production
8. YouTube video download functionality
9. Scheduled tasks for video management
10. Advanced error handling and user-facing error messages
11. Security and authentication
12. Plex integration
13. Unit and integration tests
14. Docker configuration for deployment
15. API documentation (Swagger or SpringFox)
16. Pagination and filtering for list endpoints

## Frontend Features (To Be Implemented)
1. Light mode toggle
2. Recently downloaded videos display on home page
3. Download progress tracking
4. Improved video metadata display (names, channel names, thumbnails)
5. Mobile responsiveness improvements
6. Dedicated error tab for displaying issues
7. Search functionality for finding specific videos or channels
8. Storage management display (usage, alerts for capacity limits)
9. Add Video functionality
10. Video playback integration

## Backend Features (To Be Implemented)
1. Scheduled tasks for checking new videos and managing rotations
2. File system management for organizing downloaded videos
3. Simple authentication system using Spring Security
4. Integration with youtube-dl or yt-dlp for video downloading
5. Advanced YouTube API integration for comprehensive metadata retrieval
6. Implement business logic for video rotation and management

## Management Features (To Be Implemented)
1. User management and authentication
2. Application settings management
3. Logging and monitoring
4. Backup and restore functionality

## Integration and Deployment (To Be Implemented)
1. Docker support for easy deployment on Unraid server
2. Plex integration (auto-update Plex library, sync watch status)
3. Continuous Integration/Continuous Deployment (CI/CD) pipeline

## Database Schema (Current)

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP WITH TIME ZONE
);

CREATE TABLE channels (
    id SERIAL PRIMARY KEY,
    youtube_channel_id VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    thumbnail_url VARCHAR(255),
    max_videos INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE videos (
    id SERIAL PRIMARY KEY,
    youtube_video_id VARCHAR(100) UNIQUE NOT NULL,
    channel_id INTEGER REFERENCES channels(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    publish_date TIMESTAMP WITH TIME ZONE,
    duration INTEGER,
    thumbnail_url VARCHAR(255),
    file_path VARCHAR(255),
    file_size BIGINT,
    video_quality VARCHAR(20),
    download_date TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE playlists (
    id SERIAL PRIMARY KEY,
    youtube_playlist_id VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    max_videos INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE download_jobs (
    id SERIAL PRIMARY KEY,
    video_id INTEGER REFERENCES videos(id),
    status VARCHAR(20) NOT NULL,
    progress INTEGER,
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```

## Detailed Next Steps

1. Frontend Development:
   - Implement "Add Video" functionality in VideosComponent
   - Create modals for adding/editing channels, videos, and playlists
   - Implement video playback functionality
   - Develop the SettingsComponent fully
   - Create a dedicated ErrorComponent for displaying application-wide errors
   - Implement search functionality across channels and videos
   - Add pagination to list views (channels, videos, playlists)
   - Implement loading indicators for API calls
   - Create a dashboard view for the home page with recent downloads and stats
   - Develop a user authentication flow (login, register, password reset)

2. Backend Development:
   - Implement YouTube video download functionality using youtube-dl or yt-dlp
   - Create scheduled tasks for checking new videos and managing rotations
   - Implement file system management for organizing downloaded videos
   - Set up Spring Security for authentication and authorization
   - Develop advanced YouTube API integration for comprehensive metadata retrieval
   - Implement business logic for video rotation and management
   - Create endpoints for application settings management
   - Implement logging and monitoring features
   - Develop backup and restore functionality

3. Integration:
   - Implement Plex integration for library updates and watch status sync
   - Develop a plugin or script for Plex to recognize and categorize Plextube downloads

4. Testing:
   - Write unit tests for frontend services and components
   - Implement integration tests for backend services and controllers
   - Develop end-to-end tests for critical user flows

5. Documentation:
   - Implement API documentation using Swagger or SpringFox
   - Create user documentation for setting up and using Plextube
   - Develop technical documentation for future maintenance and contributions

6. Deployment:
   - Configure Docker for containerization of both frontend and backend
   - Set up a CI/CD pipeline for automated testing and deployment
   - Create scripts for easy deployment on Unraid servers

7. Performance Optimization:
   - Implement caching strategies for frequently accessed data
   - Optimize database queries and indexes
   - Implement lazy loading for frontend components and routes

8. Security Enhancements:
   - Implement rate limiting for API endpoints
   - Set up SSL/TLS for secure communications
   - Develop a robust error handling and logging system to catch and report security issues

9. User Experience Improvements:
   - Implement drag-and-drop functionality for reordering playlists or videos
   - Develop a notification system for long-running processes (e.g., downloads)
   - Create keyboard shortcuts for common actions

10. Scalability Considerations:
    - Implement database sharding strategies for handling large amounts of data
    - Develop a microservices architecture for better scalability of individual components
    - Implement a message queue system for handling asynchronous tasks

This updated project summary provides a comprehensive overview of the Plextube project, including its current state, implemented features, and detailed plans for future development. It serves as a thorough guide for AI assistants to understand the full scope of the project and provide accurate assistance in its ongoing development.
