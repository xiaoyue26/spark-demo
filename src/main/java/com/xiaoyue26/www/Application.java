package com.xiaoyue26.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xiaoyue26 on 17/12/19.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication application = new SpringApplication(Application.class);

        // To disabled web environment, change `true` to `false`
        application.setWebEnvironment(true);
        application.run(args);
    }
}
