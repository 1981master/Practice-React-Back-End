package com.master.practiceReact.config.DBConfig;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class H2Shutdown {

    @Autowired
    private DataSource dataSource;

    @PreDestroy
    public void shutdown() {
        try (Connection connection = dataSource.getConnection()) {
            // Ensure to shutdown H2 gracefully
            connection.createStatement().execute("SHUTDOWN");
            System.out.println("H2 shutdown cleanly.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error during H2 shutdown: " + e.getMessage());
        }
    }
}
