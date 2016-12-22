package com.wang.demo.service;

import com.framework.bean.SimpleLongID;
import com.framework.service.GenericCrudService;
import com.wang.demo.model.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wang on 2016/12/22.
 */
@Transactional(rollbackFor={Exception.class})
@Service
public class DemoService {

    @Autowired
    private GenericCrudService crudService;

    public void add(Demo function) throws Exception {
        function.setId(new SimpleLongID());
        function.setCreate_time(new Date());
        function.setName("测试444");
        function.setPath("#####");
        crudService.saveOrUpdate(function);
    }
}
