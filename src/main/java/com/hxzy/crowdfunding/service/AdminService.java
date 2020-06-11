package com.hxzy.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.hxzy.crowdfunding.bean.TAdmin;
import org.springframework.ui.Model;

public interface AdminService {

    /**
     * 查询所有的管理员信息
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageInfo<TAdmin> selectAdminAll(Integer pageNum, Integer pageSize, String search);

    /**
     * 添加管理员
     * @param tAdmin
     */
    void saveAdmin(TAdmin tAdmin);

    /**
     * 删除管理员
     * @param id
     */
    void deleteAdmin(Integer id);

    /**
     * 根据ID查询管理员
     * @param id
     * @return
     */
    TAdmin selectAdminById(Integer id);

    /**
     *  修改管理员
     * @param tAdmin
     */
    void updateAdmin(TAdmin tAdmin);

    /**
     * 批量删除管理员
     * @param ids
     */
    void batchDelete(Integer[] ids);

    /**
     * 访问角色授权
     * @param id
     * @param model
     */
    void selectAssignRole(Integer id, Model model);

    /**
     * 角色授权
     * @param uid
     * @param rids
     */
    void assignRole(Integer uid, Integer[] rids);

    void unAssignRole(Integer uid, Integer[] rids);
}
