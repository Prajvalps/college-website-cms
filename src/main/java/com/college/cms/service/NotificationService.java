package com.college.cms.service;

import com.college.cms.model.Notification;
import com.college.cms.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public Notification create(Notification notification) {
        return repository.save(notification);
    }

    public List<Notification> getAll() {
        return repository.findAll();
    }

    public List<Notification> getActive() {
        return repository.findByActiveTrueOrderByDateDesc();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public boolean exists(String title, String date) {
    return repository.existsByTitleAndDate(title, date);
}
}