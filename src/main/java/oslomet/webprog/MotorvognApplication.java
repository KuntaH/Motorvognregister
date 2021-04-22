package oslomet.webprog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class MotorvognApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotorvognApplication.class, args);
    }

}
