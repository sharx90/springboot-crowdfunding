package com.hxzy.crowdfunding.service;

import com.hxzy.crowdfunding.bean.TMember;

import javax.servlet.http.HttpSession;

public interface LoginService {

    /**
     * 登录验证
     * @param loginacct
     * @param userpswd
     * @param session
     */
    void login(String loginacct, String userpswd, HttpSession session);

    /**
     * 获取验证码
     * @param phone
     * @param session
     */
    void getCode(String phone, HttpSession session);

    /**
     * 实现注册功能
     * @param tMember
     * @param code
     * @param session
     */
    void doReg(TMember tMember, String code, HttpSession session);
}
