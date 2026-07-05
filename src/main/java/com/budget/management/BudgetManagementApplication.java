package com.budget.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BudgetManagementApplication {

    public static void main(String[] args) {
        // Lecture des variables d'environnement (plus de mot de passe en clair !)
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");

        if (dbHost != null && dbPort != null && dbName != null) {
            System.setProperty("spring.datasource.url", "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=true&trustServerCertificate=true");
        }

        System.setProperty("spring.datasource.username", System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "avnadmin");
        System.setProperty("spring.datasource.password", System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "");
        System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");

        System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
        System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        SpringApplication.run(BudgetManagementApplication.class, args);
        System.out.println(">>> Budget-Management-Application FIN DE COMPILATION >>>>>>");
    }
}
