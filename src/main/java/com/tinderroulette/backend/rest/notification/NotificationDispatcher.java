package com.tinderroulette.backend.rest.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.tinderroulette.backend.rest.CAS.CASCookie;

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

    @GetMapping("/notification/")
    public static ResponseEntity getEvent(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) {
        CASCookie.decodeLoginCookie(userCookie, credCookie);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<NotificationData> list = notificationMap.remove(auth.getPrincipal());
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
