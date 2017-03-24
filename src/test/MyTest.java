package test;

import com.services.demo.model.Demo;
import com.services.demo.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void addTest() {
        demoService.add(new Demo());
    }

    @Test
    public void listTest() {
        for (Demo demo : demoService.findAll()) {
            System.out.println(demo.getCreateTime());
        }
    }

}
