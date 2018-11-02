package com.tinderroulette.backend.rest.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationDispatcher {
    public static Map<String, List<NotificationData>> notificationMap;

    public NotificationDispatcher() {
        if (notificationMap == null) {
            synchronized (this) {
                if (notificationMap == null) {
                    notificationMap = new HashMap<>();
                }
            }
        }
    }

    public static void addEvent(String cip, NotificationData data) {
        if (!notificationMap.containsKey(cip)) {
            notificationMap.put(cip, new ArrayList<NotificationData>());
        }
        List<NotificationData> events = notificationMap.get(cip);
        events.add(data);
        notificationMap.put(cip, events);
        System.out.println(((NotificationData) notificationMap.get(cip).get(0)).getDescription());
    }

    @GetMapping("/notification/{cip}/")
    public static ResponseEntity getEvent(@PathVariable String cip) {
        List<NotificationData> list = notificationMap.remove(cip);
        System.out.println(list);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
