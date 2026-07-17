package com.college.cms.scheduler;

import com.college.cms.model.News;
import com.college.cms.model.Notification;
import com.college.cms.service.NewsService;
import com.college.cms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class DataFetcher {

    @Autowired
    private NotificationService notificationService;

    private final String NOTIF_URL = "https://sheetdb.io/api/v1/YOUR_NOTIF_ID";
    @Autowired

private NewsService newsService;

private final String NEWS_URL = "https://sheetdb.io/api/v1/YOUR_NEWS_ID";
@Scheduled(cron = "0 0 6 * * ?")
public void fetchNews() {

    RestTemplate rest = new RestTemplate();

    try {
        List<Map<String, String>> data =
                rest.getForObject(NEWS_URL, List.class);

        for (Map<String, String> item : data) {

            String title = item.get("title");
            String dateStr = item.get("date");

            java.time.LocalDate date = java.time.LocalDate.parse(dateStr);

            if (newsService.exists(title, date)) continue;

            News n = new News();
            n.setTitle(title);
            n.setContent(item.get("content"));
            n.setDate(date);
            n.setActive(true);

            newsService.create(n);
        }

        System.out.println("✅ News synced");

    } catch (Exception e) {
        System.out.println("❌ News fetch failed");
    }
}

    @Scheduled(cron = "0 0 6 * * ?") // once daily at 6 AM
    public void fetchNotifications() {

        RestTemplate rest = new RestTemplate();

        try {
            List<Map<String, String>> data =
                    rest.getForObject(NOTIF_URL, List.class);

            if (data == null || data.isEmpty()) {
                System.out.println("No data received from SheetDB");
                return;
            }

            for (Map<String, String> item : data) {

                String title = item.get("title");
                String date = item.get("date");

                // ✅ skip if already exists
                if (notificationService.exists(title, date)) {
                    continue;
                }

                Notification n = new Notification();
                n.setTitle(title);
                n.setContent(item.get("content"));
                n.setDate(LocalDate.parse(item.get("date")));
                n.setActive(true);

                notificationService.create(n);
            }
            

            System.out.println("✅ Notifications synced from SheetDB");

        } catch (Exception e) {
            System.out.println("❌ Fetch failed, using DB data");
        }
    }
}