package com.hxzy.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.hxzy.crowdfunding.bean.TPermission;
import com.hxzy.crowdfunding.bean.TRole;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有角色
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageInfo<TRole> selectRoleAll(Integer pageNum, Integer pageSize, String search);

    /**
     * 查询许可信息
     * @param id
     * @return
     */
    List<TPermission> selectPermissionAll(Integer id);

    /**
     * 查询角色已分配的许可
     * @param id
     * @return
     */
    List<Integer> selectAssignPermission(Integer id);

    /**
     * 为指定角色分配许可
     * @param rid
     * @param ids
     */
    void assignPermission(Integer rid, Integer[] ids);
}
