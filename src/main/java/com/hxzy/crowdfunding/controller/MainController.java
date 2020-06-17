package com.hxzy.crowdfunding.controller;

import com.hxzy.crowdfunding.service.TMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private TMenuService tMenuService;


    @RequestMapping("admin")
    public String admin(HttpSession session){
        log.info("访问后台首页...");

        try {
            tMenuService.selectMenuAll(session);
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/error/5xx";
        }

        return "admin/main";
    }

}
