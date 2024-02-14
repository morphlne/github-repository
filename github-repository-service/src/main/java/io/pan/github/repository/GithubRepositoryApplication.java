package io.pan.github.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GithubRepositoryApplication {

    public static void main(String[] args) {
        new SpringApplication(GithubRepositoryApplication.class).run(args);
    }

}
