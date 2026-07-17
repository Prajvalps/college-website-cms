package com.college.cms.service;

import com.college.cms.model.Announcement;
import com.college.cms.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository repository;

    public AnnouncementService(AnnouncementRepository repository) {
        this.repository = repository;
    }

    public Announcement create(Announcement announcement) {
        return repository.save(announcement);
    }

    public List<Announcement> getAll() {
        return repository.findAll();
    }

    public List<Announcement> getActive() {
        return repository.findByActiveTrueOrderByYearDesc();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}