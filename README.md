# Plextube

Plextube is a video downloader application that allows users to manage and download videos from channels and playlists. It's designed to be hosted on a personal Unraid server and integrated with Plex.

## Features

- Channel-based downloading with video limits
- Automatic video rotation (delete oldest, download newest)
- Specific video downloading via URL
- Playlist support for downloading entire playlists
- Video quality options and subtitle downloading
- Metadata scraping (descriptions, tags, upload dates)
- Dark mode UI with light mode toggle
- Integration with Plex media server

## Tech Stack

- Frontend: Angular 17 with Tailwind CSS
- Backend: Spring Boot 3.3.4 (Java 17)
- Database: PostgreSQL
- ORM: Spring Data JPA
- Database Migration: Liquibase

## Prerequisites

- Node.js (v14 or later)
- Java Development Kit (JDK) 17
- PostgreSQL
- Maven
- Docker (optional, for containerized deployment)

## Installation

### Backend Setup

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/plextube.git
   cd plextube/backend
   ```

2. Configure the database in `src/main/resources/application.properties`:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/plextube
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build and run the Spring Boot application:
   ```
   mvn spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd ../frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   ng serve
   ```

4. Open your browser and visit `http://localhost:4200`

## Usage

1. Add channels or playlists through the web interface.
2. Configure download settings for each channel or playlist.
3. Plextube will automatically download new videos based on your settings.
4. Access downloaded videos through your Plex media server.

## Development

### Running Tests

- Backend: `mvn test`
- Frontend: `ng test`

### API Documentation

API documentation is available at `http://localhost:8080/swagger-ui.html` when running the backend server.

## Deployment

### Docker Deployment

1. Build the Docker images:
   ```
   docker-compose build
   ```

2. Run the containers:
   ```
   docker-compose up -d
   ```

### Unraid Deployment

Instructions for deploying on Unraid will be provided in a separate document.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Contact

Your Name - your.email@example.com

Project Link: [https://github.com/yourusername/plextube](https://github.com/yourusername/plextube)
