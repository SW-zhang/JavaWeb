package test;

import com.framework.service.GenericCrudService;
import com.framework.service.impl.GenericCrudServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {

    private static GenericCrudService crudService;

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:/applicationContext.xml");
        GenericCrudServiceImpl action = (GenericCrudServiceImpl) context.getBean("crudService");
    }

}
