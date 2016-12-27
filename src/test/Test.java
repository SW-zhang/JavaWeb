package test;

import com.alibaba.fastjson.JSON;
import com.framework.service.GenericCrudService;
import com.framework.service.impl.GenericCrudServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

public class Test {

    private static GenericCrudService crudService;

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:/applicationContext.xml");
        GenericCrudServiceImpl action = (GenericCrudServiceImpl) context.getBean("crudService");
        List<Object[]> results = action.sqlPager("select name,level from function", 0, 10);
        for (Object[] d : results) {
            System.out.println(JSON.toJSONString(d));
        }
    }

}
