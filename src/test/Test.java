package test;

import com.framework.bean.SimpleLongID;
import com.framework.service.GenericCrudService;
import com.services.demo.model.Demo;
import com.services.demo.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {

    private static GenericCrudService crudService;

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:/applicationContext.xml");
        GenericCrudService action = (GenericCrudService) context.getBean("crudService");
        System.out.println(action.get(Demo.class, new SimpleLongID(1L)));
    }

}
