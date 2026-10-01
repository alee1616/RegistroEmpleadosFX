package com.example.registroempleadosfx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static String URL = "jdbc:postgresql://localhost:5432/empleado_fx";
    private final static String USER = "postgres";
    private final static String PASSWORD = "Admin123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
