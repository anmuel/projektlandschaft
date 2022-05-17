package com.example.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableJpaRepositories
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@PWA(name = "Vaadin CRM",
    shortName = "CRM",
    offlinePath = "offline.html",
    offlineResources = {"./images/offline.png"})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
