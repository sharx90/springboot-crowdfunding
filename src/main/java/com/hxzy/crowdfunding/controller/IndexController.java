package com.hxzy.crowdfunding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    @RequestMapping({"/","/index"})
    public String index(){
        log.info("访问首页...");
        return "index";
    }
}
