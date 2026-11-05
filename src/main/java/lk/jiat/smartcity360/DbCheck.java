package lk.jiat.smartcity360;

import java.sql.Connection;
import java.sql.DriverManager;

/** Temporary: delete after the DB connects. */
public class DbCheck {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/smartcity360"
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Colombo";
        try (Connection c = DriverManager.getConnection(url, "root", "2.2Dhanesh")) {
            System.out.println("Connected to " + c.getMetaData().getDatabaseProductName()
                    + " " + c.getMetaData().getDatabaseProductVersion());
        }
    }
}