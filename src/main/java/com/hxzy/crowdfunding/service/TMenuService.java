package com.hxzy.crowdfunding.service;

import javax.servlet.http.HttpSession;


public interface TMenuService {
    /**
     * 查询所有的menu
     * @param session
     */
    void selectMenuAll(HttpSession session);
}
