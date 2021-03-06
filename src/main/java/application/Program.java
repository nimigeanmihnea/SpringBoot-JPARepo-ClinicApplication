package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Mihnea on 02/05/2017.
 */

@SpringBootApplication
@EnableAutoConfiguration
public class Program extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(Program.class);
    }

    public static void main(String[] args){
        SpringApplication.run(Program.class, args);
    }
}
