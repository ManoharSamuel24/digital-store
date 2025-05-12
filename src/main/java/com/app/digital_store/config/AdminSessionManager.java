package com.app.digital_store.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class AdminSessionManager {

    public static final ConcurrentHashMap<String, Boolean> adminSessionMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void resetAdminSession() {
        adminSessionMap.clear();
        System.out.println("Admin session reset on application startup.");
    }

    public static void markAdminLoggedIn(String sessionId) {
        adminSessionMap.put(sessionId, true);
    }

    public static boolean isAdmin(String sessionId) {
        return Boolean.TRUE.equals(adminSessionMap.get(sessionId));
    }
}
