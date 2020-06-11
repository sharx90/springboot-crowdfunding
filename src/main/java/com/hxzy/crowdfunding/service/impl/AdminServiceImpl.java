package com.hxzy.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxzy.crowdfunding.bean.TAdmin;
import com.hxzy.crowdfunding.bean.TAdminExample;
import com.hxzy.crowdfunding.bean.TRole;
import com.hxzy.crowdfunding.exception.AdminException;
import com.hxzy.crowdfunding.mapper.TAdminMapper;
import com.hxzy.crowdfunding.mapper.TAdminRoleMapper;
import com.hxzy.crowdfunding.mapper.TRoleMapper;
import com.hxzy.crowdfunding.service.AdminService;
import com.hxzy.crowdfunding.util.AppDateUtils;
import com.hxzy.crowdfunding.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TAdminMapper tAdminMapper;

    @Autowired
    private TRoleMapper tRoleMapper;

    @Autowired
    private TAdminRoleMapper tAdminRoleMapper;

    @Override
    public void unAssignRole(Integer uid, Integer[] rids) {
        try {
            int i= tAdminRoleMapper.unAassignRole(uid,rids);
            if (i <= 0){
                throw new RuntimeException("异步授权角色失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异步授权角色失败---{}",e.getMessage());
            throw new RuntimeException("系统错误,请求稍后访问"); // 给用户看的异常信息
        }
    }

    @Override
    public void assignRole(Integer uid, Integer[] rids) {
        try {
            int i= tAdminRoleMapper.assignRole(uid,rids);
            if (i <= 0){
                throw new RuntimeException("异步授权角色失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异步授权角色失败---{}",e.getMessage());
            throw new RuntimeException("系统错误,请求稍后访问"); // 给用户看的异常信息
        }
    }

    @Override
    public void selectAssignRole(Integer id, Model model) {
        // 查询所有角色数据
        List<TRole> roles = tRoleMapper.selectByExample(null);

        // 查询已分配的角色
        List<Integer> assignRoleIds = tAdminRoleMapper.selectRoleIdsByAdminId(id);

        // 已分配的角色
        List<TRole> assignList = new ArrayList<>();

        // 未分配的角色
        List<TRole> unAssignList = new ArrayList<>();

        // 判断未分配的角色
        for (TRole role : roles) {
            if(assignRoleIds.contains(role.getId())){
                assignList.add(role);
            }else {
                unAssignList.add(role);
            }
        }

        model.addAttribute("assignList",assignList);
        model.addAttribute("unAssignList",unAssignList);
    }



    //批量删除管理员
    @Transactional
    @Override
    public void batchDelete(Integer[] ids) {
        try {
            TAdminExample example = new TAdminExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            int i = tAdminMapper.deleteByExample(example);
            if(i <= 0){
                throw new AdminException("批量删除管理员失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("批量删除管理员异常---{}", e.getMessage());
            throw new AdminException("系统错误,请稍后访问"); // 这里抛的异常信息是给用户看
        }
    }

    // 修改管理员
    @Override
    public void updateAdmin(TAdmin tAdmin) {
        try {
            int i = tAdminMapper.updateByPrimaryKeySelective(tAdmin);
            if(i <= 0){
                throw new AdminException("修改管理员失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改管理员异常---{}", e.getMessage());
            throw new AdminException("系统错误,请稍后访问"); // 这里抛的异常信息是给用户看
        }
    }

    //根据ID查询管理员
    @Override
    public TAdmin selectAdminById(Integer id) {
        TAdmin tAdmin = tAdminMapper.selectByPrimaryKey(id);
        return tAdmin;
    }

    // 删除管理员
    @Transactional
    @Override
    public void deleteAdmin(Integer id) {
        try {
            int i = tAdminMapper.deleteByPrimaryKey(id);
            if(i <= 0){
                throw new AdminException("删除管理员失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除管理员异常---{}", e.getMessage());
            throw new AdminException("系统错误,请稍后访问"); // 这里抛的异常信息是给用户看
        }
    }

    // 添加管理员
    @Transactional
    @Override
    public void saveAdmin(TAdmin tAdmin) {
        try {
            tAdmin.setUserpswd(MD5Util.digest("123456"));
            tAdmin.setStatus(1);
            tAdmin.setCreatetime(AppDateUtils.getFormatTime());
            int i = tAdminMapper.insertSelective(tAdmin);
            if(i <= 0){
                throw new AdminException("添加管理员失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加管理员异常---{}", e.getMessage());
            throw new AdminException("系统错误,请稍后访问"); // 这里抛的异常信息是给用户看
        }
    }

    // 查询所有管理员
    @Override
    public PageInfo<TAdmin> selectAdminAll(Integer pageNum, Integer pageSize, String search) {

        PageHelper.startPage(pageNum, pageSize);

        TAdminExample example = new TAdminExample();
        example.setOrderByClause("createtime desc");

        // 判断是否有条件
        if(!StringUtils.isEmpty(search)){
            // 第一个登录账号的条件
            example.createCriteria().andLoginacctLike("%"+search+"%");

            // 新建条件
            TAdminExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameLike("%"+search+"%");

            // 第一个条件 或上第二个条件
            example.or(criteria);
        }

        List<TAdmin> tAdmins = tAdminMapper.selectByExample(example);

        PageInfo<TAdmin> pageInfo = new PageInfo<>(tAdmins,5);

        return pageInfo;
    }
}
