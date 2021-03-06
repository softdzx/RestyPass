package com.github.df.restypass.testclient.service;

import com.github.df.restypass.annotation.RestyMethod;
import com.github.df.restypass.annotation.RestyService;
import com.github.df.restypass.lb.RandomLoadBalancer;
import com.github.df.restypass.testclient.entity.Response;
import com.github.df.restypass.testclient.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/11/22
 */
@RestyService(serviceName = "server",
        fallbackEnabled = true,
        fallbackClass = ProxyServiceImpl.class,
        forceBreakEnabled = false,
        circuitBreakEnabled = false,
        loadBalancer = RandomLoadBalancer.NAME,
        retry = 1,
        requestTimeout = 10000,
        limit = 1000
)
@RequestMapping(value = "/resty")
public interface ProxyService extends ApplicationService {

    @RestyMethod(retry = 2, forceBreakEnabled = "false", limit = 10)
    @RequestMapping(value = "/get_nothing", method = RequestMethod.GET, headers = "Client=RestyProxy", params = "Param1=val1")
    void getNothing();


    @RestyMethod
    @RequestMapping(value = "/get_string", method = RequestMethod.GET)
    String getString();

    @RestyMethod
    @RequestMapping(value = "/get_user", method = RequestMethod.GET)
    User getUser();

    @RestyMethod()
    @RequestMapping(value = "/list")
    List<User> getList();

    @RestyMethod()
    @RequestMapping(value = "/get_age", method = RequestMethod.GET)
    Response<String> getAge(@RequestParam("id") Long id, String code, @PathVariable(value = "name") String name);

    int getHeight(Long id);

    @RestyMethod
    @RequestMapping(value = "/update/{name}", method = RequestMethod.POST)
    String update(@RequestParam("id") Long id, @PathVariable(value = "name") String name, User user);

}
