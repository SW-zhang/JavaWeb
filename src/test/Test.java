package test;

import com.alibaba.fastjson.JSON;
import com.framework.dao.Pager;
import com.framework.service.GenericCrudService;
import com.framework.service.impl.GenericCrudServiceImpl;
import com.wang.demo.model.Demo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by wang on 2016/12/21.
 */
public class Test {

    private static GenericCrudService crudService;

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("classpath:/applicationContext.xml");
        GenericCrudServiceImpl action = (GenericCrudServiceImpl) context.getBean("crudService");
        Pager pager = new Pager<Demo>();
        action.sqlPager(pager, Demo.class, "select * from function where path = ? and level = ?", "#", 1);
        System.out.println(JSON.toJSONString(pager));
    }

}
