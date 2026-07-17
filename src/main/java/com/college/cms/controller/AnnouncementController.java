package com.college.cms.controller;

import com.college.cms.model.Announcement;
import com.college.cms.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementController {

    private final AnnouncementService service;

    public AnnouncementController(AnnouncementService service) {
        this.service = service;
    }

    @PostMapping
    public Announcement create(@RequestBody Announcement announcement) {
        return service.create(announcement);
    }

    @GetMapping
    public List<Announcement> getAll() {
        return service.getAll();
    }

    @GetMapping("/active")
    public List<Announcement> getActive() {
        return service.getActive();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}