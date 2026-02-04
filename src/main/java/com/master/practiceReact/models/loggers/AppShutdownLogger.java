package com.master.practiceReact.models.loggers;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class AppShutdownLogger {
    @PreDestroy
    public void onShutdown() {
        System.out.println("Application shutting down...");
    }
}
