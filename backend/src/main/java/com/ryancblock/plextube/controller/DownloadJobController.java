package com.ryancblock.plextube.controller;

import com.ryancblock.plextube.entity.DownloadJob;
import com.ryancblock.plextube.service.DownloadJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/download-jobs")
public class DownloadJobController {

    private final DownloadJobService downloadJobService;

    public DownloadJobController(DownloadJobService downloadJobService) {
        this.downloadJobService = downloadJobService;
    }

    @GetMapping
    public List<DownloadJob> getAllDownloadJobs() {
        return downloadJobService.getAllDownloadJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DownloadJob> getDownloadJobById(@PathVariable Integer id) {
        return downloadJobService.getDownloadJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<DownloadJob> getDownloadJobsByStatus(@PathVariable String status) {
        return downloadJobService.getDownloadJobsByStatus(status);
    }

    @PostMapping
    public DownloadJob createDownloadJob(@RequestBody DownloadJob downloadJob) {
        return downloadJobService.saveDownloadJob(downloadJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DownloadJob> updateDownloadJob(@PathVariable Integer id, @RequestBody DownloadJob jobDetails) {
        return downloadJobService.getDownloadJobById(id)
                .map(job -> {
                    job.setStatus(jobDetails.getStatus());
                    job.setProgress(jobDetails.getProgress());
                    job.setErrorMessage(jobDetails.getErrorMessage());
                    return ResponseEntity.ok(downloadJobService.saveDownloadJob(job));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDownloadJob(@PathVariable Integer id) {
        return downloadJobService.getDownloadJobById(id)
                .map(job -> {
                    downloadJobService.deleteDownloadJob(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}