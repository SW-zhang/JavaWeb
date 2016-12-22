package test;

import com.framework.service.impl.GenericCrudServiceImpl;
import com.wang.demo.model.Demo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by wang on 2016/12/21.
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:/applicationContext.xml");
        GenericCrudServiceImpl action = (GenericCrudServiceImpl) context.getBean("crudService");

//        Demo function = action.get(Demo.class, new SimpleLongID(1L));
//        System.out.println(JSON.toJSONString(function));
//        function.setPath(function.getPath() + "###");
//        action.saveOrUpdate(function);
//        Demo function = new Demo(new SimpleLongID(2L));
//        System.out.println(function.toString());


        action.list(Demo.class);
    }
}
