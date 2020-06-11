package com.hxzy.crowdfunding.controller;

import com.hxzy.crowdfunding.bean.TMember;
import com.hxzy.crowdfunding.service.LoginService;
import com.hxzy.crowdfunding.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginRegController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("tologin")
    public String toLogin(){
        log.info("访问登录页面...");
        return "login";
    }

    // 实现异步登录 (规范返回值)
    @ResponseBody
    @RequestMapping("doLogin") // JSON 数据
    public ResponseData<String> doLogin(String loginacct, String userpswd, HttpSession session){

        try {
            // 登录验证
            loginService.login(loginacct,userpswd,session);
            log.info("登录成功...");
            return ResponseData.ok(session.getServletContext().getContextPath()+"/admin/"); // 登录成功后,返回后台访问链接
        } catch (Exception e) {
            e.printStackTrace();
            log.error("登录失败---{}",e.getMessage());
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 实现注册
    @RequestMapping("toReg")
    public String toReg(){
        log.info("跳转注册页面...");
        return "reg";
    }
    // 注册
    @ResponseBody
    @RequestMapping("doReg")
    public ResponseData<String> doReg(TMember tMember,String code, HttpSession session){

        try {
            loginService.doReg(tMember, code, session);
            log.error("注册成功");
            return ResponseData.ok(session.getServletContext().getContextPath()+"/tologin"); // 注册成功响应登录映射路径
        } catch (Exception e) {
            e.printStackTrace();
            log.error("注册失败---{}", e.getMessage());
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 获取验证码
    @ResponseBody
    @RequestMapping("getCode")
    public ResponseData getCode(String phone, HttpSession session){

        try {
            loginService.getCode(phone, session);
            log.error("获取验证码成功");
            return ResponseData.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取验证码失败---{}", e.getMessage());
            return ResponseData.fail(null);
        }
    }


}
