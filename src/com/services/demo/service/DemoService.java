package com.services.demo.service;

import com.framework.bean.SimpleLongID;
import com.framework.service.GenericCrudService;
import com.services.demo.model.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wang on 2016/12/22.
 */
@Transactional(readOnly = true)
@Service
public class DemoService {

    @Autowired
    private GenericCrudService crudService;

    @Transactional
    public void add(Demo function) {
        function.setId(new SimpleLongID());
        function.setCreateTime(new Date());
        function.setName("测试444");
        function.setPath("#####");
        crudService.saveOrUpdate(function);
    }
}
