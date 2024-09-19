package com.ryancblock.plextube.repository;

import com.ryancblock.plextube.entity.DownloadJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DownloadJobRepository extends JpaRepository<DownloadJob, Integer> {
    List<DownloadJob> findByStatus(String status);
}