package com.hxzy.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxzy.crowdfunding.bean.*;
import com.hxzy.crowdfunding.mapper.TPermissionMapper;
import com.hxzy.crowdfunding.mapper.TRoleMapper;
import com.hxzy.crowdfunding.mapper.TRolePermissionMapper;
import com.hxzy.crowdfunding.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private TRoleMapper roleMapper;

    @Autowired
    private TRoleMapper tRoleMapper;

    @Autowired
    private TPermissionMapper tPermissionMapper;

    @Autowired
    private TRolePermissionMapper tRolePermissionMapper;

    // 为指定角色分配许可
    @Transactional
    @Override
    public void assignPermission(Integer rid, Integer[] ids) {

        try {

            TRolePermissionExample example = new TRolePermissionExample();
            example.createCriteria().andRoleidEqualTo(rid);
            // 清空之前的许可
            tRolePermissionMapper.deleteByExample(example);

            int i = tRolePermissionMapper.assignPermission(rid, ids);

            if (i <= 0){
                throw new RuntimeException("添加数据失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("异步分配许可失败---{}",e.getMessage());
            throw new RuntimeException("系统错误,请求稍后访问"); // 给用户看的异常信息
        }
    }

    // 查询角色已分配的许可
    @Override
    public List<Integer> selectAssignPermission(Integer id) {
        return roleMapper.selectAssignPermission(id);
    }

    // 查询许可信息
    @Override
    public List<TPermission> selectPermissionAll(Integer id) {
        TPermissionExample example = new TPermissionExample();
        example.setOrderByClause("id asc");

        List<TPermission> list = tPermissionMapper.selectByExample(example);

        // 查询已被分配的许可
        List<Integer> permissionIds = tRoleMapper.selectAssignPermission(id);

        // 设置是否选中
        for (TPermission permission : list) {
            if(permissionIds.contains(permission.getId())){
                permission.setChecked(true);
            }
        }

        return list;
    }

    // 查询所有角色
    @Override
    public PageInfo<TRole> selectRoleAll(Integer pageNum, Integer pageSize, String search) {

        try {
            PageHelper.startPage(pageNum,pageSize);

            TRoleExample example = new TRoleExample();
            example.setOrderByClause("id asc");

            if(!StringUtils.isEmpty(search)){
                example.createCriteria().andNameLike("%"+ search +"%");
            }

            List<TRole> tRoles = roleMapper.selectByExample(example);

            PageInfo<TRole>  pageInfo = new PageInfo<>(tRoles,5);

            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异步查询角色信息异常---{}",e.getMessage());
            throw new RuntimeException("系统错误,请求稍后访问"); // 给用户看的异常信息
        }

    }
}
