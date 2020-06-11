package com.hxzy.crowdfunding.service.impl;

import com.hxzy.crowdfunding.bean.TMenu;
import com.hxzy.crowdfunding.bean.TMenuExample;
import com.hxzy.crowdfunding.exception.AdminException;
import com.hxzy.crowdfunding.mapper.TMenuMapper;
import com.hxzy.crowdfunding.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    // 添加菜单
    @Override
    public void addMenu(TMenu tMenu) {
        try {
            int i = tMenuMapper.insertSelective(tMenu);
            if(i <= 0){
                throw new AdminException("添加菜单失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加菜单异常---{}", e.getMessage());
            throw new AdminException("系统错误,请稍后访问"); // 这里抛的异常信息是给用户看
        }
    }

    //查询所有菜单数据
    @Override
    public List<TMenu> selectMenuAll() {

        TMenuExample example = new TMenuExample();
        example.setOrderByClause("id asc");
        List<TMenu> menus = tMenuMapper.selectByExample(example);

        return menus;
    }
}
