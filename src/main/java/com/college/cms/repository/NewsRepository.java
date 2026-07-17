package com.college.cms.repository;

import com.college.cms.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

List<News> findTop3ByActiveTrueOrderByDateDescIdDesc();
    boolean existsByTitleAndDate(String title, java.time.LocalDate date);

    @Query("SELECT n FROM News n WHERE n.active = true ORDER BY n.date DESC, n.id DESC")
List<News> getLatestNews();

List<News> findByActiveTrueOrderByDateDescIdDesc();
}