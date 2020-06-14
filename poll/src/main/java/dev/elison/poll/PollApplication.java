package dev.elison.poll;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PollApplication {
    public static void main(String[] args) {
        SpringApplication.run(PollApplication.class, args);
    }

}
