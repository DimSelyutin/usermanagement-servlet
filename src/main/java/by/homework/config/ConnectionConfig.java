package by.homework.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import by.homework.exception.ConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class ConnectionConfig {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/usermanagement";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "qwertyui";

    private static HikariDataSource dataSource;

    static {
        try {
            log.info("Load connection driver..");

            DriverManager.registerDriver(new org.postgresql.Driver());
            initializeDataSource();
            // initializeFlyway();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    private static void initializeFlyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USERNAME, PASSWORD)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
        dataSource.close();
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close(); // Закрываем соединение
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
