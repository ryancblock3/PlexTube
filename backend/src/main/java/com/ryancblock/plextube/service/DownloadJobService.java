package com.ryancblock.plextube.service;

import com.ryancblock.plextube.entity.DownloadJob;
import com.ryancblock.plextube.repository.DownloadJobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DownloadJobService {

    private final DownloadJobRepository downloadJobRepository;

    public DownloadJobService(DownloadJobRepository downloadJobRepository) {
        this.downloadJobRepository = downloadJobRepository;
    }

    public List<DownloadJob> getAllDownloadJobs() {
        return downloadJobRepository.findAll();
    }

    public Optional<DownloadJob> getDownloadJobById(Integer id) {
        return downloadJobRepository.findById(id);
    }

    public List<DownloadJob> getDownloadJobsByStatus(String status) {
        return downloadJobRepository.findByStatus(status);
    }

    public DownloadJob saveDownloadJob(DownloadJob downloadJob) {
        return downloadJobRepository.save(downloadJob);
    }

    public void deleteDownloadJob(Integer id) {
        downloadJobRepository.deleteById(id);
    }
}