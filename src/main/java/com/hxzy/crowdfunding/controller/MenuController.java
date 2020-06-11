package com.hxzy.crowdfunding.controller;

import com.hxzy.crowdfunding.bean.TMenu;
import com.hxzy.crowdfunding.service.MenuService;
import com.hxzy.crowdfunding.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/index")
    public String index(){
        log.info("访问菜单管理页面...");
        return "admin/menu/index";
    }

    @ResponseBody
    @RequestMapping("menu/asyncMenuData")
    public ResponseData asyncMenuData(){
        log.info("异步获取菜单数据...");
        try {
            List<TMenu> menus = menuService.selectMenuAll();
            ResponseData ok = ResponseData.ok(menus);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    @ResponseBody
    @PostMapping("menu/menu")
    public ResponseData addMenu(TMenu tMenu){
        log.info("异步添加菜单...");
        try {
            menuService.addMenu(tMenu);
            ResponseData ok = ResponseData.ok(null);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

}
