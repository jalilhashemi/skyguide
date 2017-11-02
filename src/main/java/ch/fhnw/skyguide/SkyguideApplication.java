package ch.fhnw.skyguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SkyguideApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SkyguideApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SkyguideApplication.class);
    }
}
