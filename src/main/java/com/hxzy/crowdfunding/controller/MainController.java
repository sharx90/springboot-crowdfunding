package com.hxzy.crowdfunding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MainController {

    @RequestMapping("admin")
    public String admin(){
        log.info("访问后台首页...");
        return "admin/main";
    }

}
