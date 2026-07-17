package com.college.cms.controller;

import com.college.cms.model.News;
import com.college.cms.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@CrossOrigin
public class NewsController {

    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    @PostMapping   // ✅ ADD THIS
    public News create(@RequestBody News news) {
        return service.create(news);
    }

    @GetMapping
    public List<News> getAll() {
        return service.getAll();
    }

   
    @GetMapping("/latest")
public List<News> getLatest() {
    return service.getLatest();
}

   @DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
    service.delete(id);
}

@GetMapping("/active")
public List<News> getActive() {
    return service.getActive();
}
}
