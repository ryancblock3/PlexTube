# Plextube Project Summary

## Project Overview
Plextube is a YouTube video downloader application that allows users to manage and download videos from YouTube channels and playlists. It's designed to be hosted on a personal Unraid server and integrated with Plex for media consumption.

## Technology Stack
1. Frontend: Angular with Tailwind CSS
2. Backend: Spring Boot 3.3.4 (Java 17)
3. Database: PostgreSQL
4. ORM: Spring Data JPA
5. Database Migration: Liquibase
6. YouTube Integration: YouTube Data API v3, youtube-dl or yt-dlp (to be implemented)

## Current Progress
1. Backend Setup: Spring Boot application initialized
2. Database Integration: PostgreSQL with Liquibase for schema management
3. Entity Classes and Repositories: Created for all database tables
4. Service Layer: Implemented with CRUD operations and business logic
5. REST Controllers: Basic CRUD operations exposed through API
6. YouTube API: Basic integration for fetching channel and video information
7. Environment Configuration: .gitignore, .env file, and updated application.properties

## Core Functionality (To Be Implemented)
1. Channel-based downloading with video limits
2. Automatic video rotation (delete oldest, download newest)
3. Specific video downloading via URL
4. Playlist support for downloading entire YouTube playlists
5. Video quality options and subtitle downloading
6. Metadata scraping (descriptions, tags, upload dates)
7. Flexible storage locations for development and production

## Frontend Features (To Be Implemented)
1. Dark mode by default with light mode toggle
2. Channel and video management interfaces
3. Recently downloaded videos display
4. Download progress tracking
5. Video metadata display (names, channel names, thumbnails)
6. Mobile responsiveness
7. Dedicated error tab for displaying issues

## Backend Features (To Be Implemented)
1. Scheduled tasks for checking new videos and managing rotations
2. File system management for organizing downloaded videos
3. Simple authentication system using Spring Security

## Management Features (To Be Implemented)
1. Search functionality for finding specific videos or channels
2. Storage management (usage display, alerts for capacity limits)

## Integration and Deployment (To Be Implemented)
1. Docker support for easy deployment on Unraid server
2. Plex integration (auto-update Plex library, sync watch status)

## Database Schema

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

## Next Steps
1. Implement YouTube video download functionality
2. Create scheduled tasks for video management
3. Implement error handling and logging
4. Set up security and authentication
5. Develop the Angular frontend
6. Integrate with Plex
7. Write unit and integration tests
8. Configure Docker for deployment
9. Implement API documentation (Swagger or SpringFox)
10. Add pagination and filtering to list endpoints

## Notes for AI Assistants
When assisting with this project, consider the existing structure and technologies. Provide code examples and explanations that align with the current setup and best practices for Spring Boot and Angular development. Focus on enhancing and expanding the existing components, particularly in areas not yet implemented.