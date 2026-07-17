package com.college.cms.service;

import com.college.cms.model.News;
import com.college.cms.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {
        this.repository = repository;
    }

    public News create(News news) {
        return repository.save(news);
    }

    public List<News> getAll() {
        return repository.findAll();
    }

    public boolean exists(String title, LocalDate date) {
        return repository.existsByTitleAndDate(title, date);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<News> getLatest() {
        return repository.getLatestNews().stream().limit(3).toList();
    }

    public List<News> getActive() {
        return repository.findByActiveTrueOrderByDateDescIdDesc();
    }
}