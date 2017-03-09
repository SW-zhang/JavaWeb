package test;

import com.services.demo.model.Demo;
import com.services.demo.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:/applicationContext.xml");
        DemoService action = (DemoService) context.getBean("demoService");
        action.add(new Demo());

    }

}
