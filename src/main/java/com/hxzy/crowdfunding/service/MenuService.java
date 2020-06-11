package com.hxzy.crowdfunding.service;

import com.hxzy.crowdfunding.bean.TMenu;

import java.util.List;

public interface MenuService {

    /**
     *  查询所有菜单数据
     * @return
     */
    List<TMenu> selectMenuAll();

    /**
     * 添加菜单
     * @param tMenu
     */
    void addMenu(TMenu tMenu);
}
