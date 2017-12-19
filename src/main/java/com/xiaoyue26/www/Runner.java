package com.xiaoyue26.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xiaoyue26 on 17/12/19.
 */
@Component
public class Runner implements CommandLineRunner {
    @Autowired
    WordCount wordCount;
    @Autowired
    private ConfigurableApplicationContext context;


    @Override
    public void run(String... args) throws Exception {
        List<Count> res = wordCount.count();
        for (Count c : res) {
            System.out.println(c.getWord() + ":" + c.getCount());
        }
        SpringApplication.exit(context);
    }
}
