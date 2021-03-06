package com.services.demo.web;

import com.framework.dao.Pager;
import com.framework.response.AjaxResult;
import com.framework.service.GenericCrudService;
import com.services.demo.model.Demo;
import com.services.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private GenericCrudService crudService;
    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list() {
        List<Demo> result = crudService.list(Demo.class);
        return AjaxResult.successObject(result);
    }

    @RequestMapping(value = "/list_page")
    public ModelAndView list(WebRequest request,Demo param, final Pager<Demo> pager) {
        crudService.hqlPager(pager, "from Demo", new Object[]{});
        ModelAndView view = new ModelAndView();
        view.setViewName("/demo/pager_demo");
        view.addObject("pageParam", request.getParameterMap());
        view.addObject("pager", pager);
        return view;
    }

    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public AjaxResult detail(@PathVariable Long id) {
        Demo demo = crudService.get(Demo.class, id);
        return AjaxResult.successObject(demo);
    }

    @RequestMapping(value = "/add_init")
    public ModelAndView addInit() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/demo/add_page");
        view.addObject("init_data", "");
        return view;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public AjaxResult add() {
        try {
            demoService.add(new Demo());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("异常");
        }
        return AjaxResult.success();
    }
}
