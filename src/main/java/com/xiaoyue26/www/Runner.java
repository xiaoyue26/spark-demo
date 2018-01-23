package com.xiaoyue26.www;

import com.xiaoyue26.www.data.Count;
import com.xiaoyue26.www.data.TestEntity;
import com.xiaoyue26.www.service.ISparkJob;
import com.xiaoyue26.www.service.WordCount;
import com.xiaoyue26.www.storage.ITestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private WordCount wordCount;
    @Autowired
    private ITestDAO testDAO;

    //@Qualifier("netWordCount")
    //@Qualifier("decemberWordCountReader")
    @Qualifier("solarReader")
    //@Qualifier("requestSolarReader")
    //@Qualifier("decemberReader")
    @Autowired
    private ISparkJob job;


    @Autowired
    private ConfigurableApplicationContext context;


    @Override
    public void run(String... args) throws Exception {
        /*List<Count> res = wordCount.count();
        for (Count c : res) {
            System.out.println(c.getWord() + ":" + c.getCount());
        }
        List<TestEntity> rr = testDAO.findAll();
        for (TestEntity t : rr) {
            System.out.println(t.getDt());
        }
        TestEntity t = new TestEntity();
        t.setDt("2017-12-20");
        t.setCol1(1);
        t.setCol2("col2_3");
        testDAO.addTestEntity(t);
        */
        // spark job
        job.run();

        SpringApplication.exit(context);
    }
}
