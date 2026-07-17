package com.college.cms.repository;

import com.college.cms.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByActiveTrueOrderByDateDesc();

    boolean existsByTitleAndDate(String title, String date);
}