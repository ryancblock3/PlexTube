package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.Video;
import com.ryancblock.plextube.entity.DownloadJob;
import com.ryancblock.plextube.repository.VideoRepository;
import com.ryancblock.plextube.repository.DownloadJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class YouTubeDownloaderService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private DownloadJobRepository downloadJobRepository;

    @Value("${youtube.download.output.path}")
    private String outputPath;

    public void downloadVideo(String videoId) {
        Video video = videoRepository.findByYoutubeVideoId(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        DownloadJob job = new DownloadJob();
        job.setVideo(video);
        job.setStatus("STARTED");
        job = downloadJobRepository.save(job);

        try {
            Path outputFilePath = Paths.get(outputPath, videoId + ".mp4");
            ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp",
                "-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]/best",
                "-o", outputFilePath.toString(),
                "https://www.youtube.com/watch?v=" + videoId
            );

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                // Update progress based on yt-dlp output
                updateJobProgress(job, line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                job.setStatus("COMPLETED");
                video.setFilePath(outputFilePath.toString());
                videoRepository.save(video);
            } else {
                job.setStatus("FAILED");
                job.setErrorMessage("yt-dlp process exited with code " + exitCode);
            }
        } catch (Exception e) {
            job.setStatus("FAILED");
            job.setErrorMessage(e.getMessage());
        }

        downloadJobRepository.save(job);
    }

    private void updateJobProgress(DownloadJob job, String ytDlpOutput) {
        // Parse yt-dlp output to extract progress information
        // This is a simplified example and may need to be adjusted based on actual output
        if (ytDlpOutput.contains("%")) {
            try {
                int percentIndex = ytDlpOutput.indexOf("%");
                String percentString = ytDlpOutput.substring(percentIndex - 4, percentIndex).trim();
                int progress = Integer.parseInt(percentString);
                job.setProgress(progress);
                downloadJobRepository.save(job);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // Log the error, but continue processing
                System.err.println("Error parsing progress: " + e.getMessage());
            }
        }
    }
}