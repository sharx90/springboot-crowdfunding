package com.hxzy.crowdfunding.controller;

import com.github.pagehelper.PageInfo;
import com.hxzy.crowdfunding.bean.TPermission;
import com.hxzy.crowdfunding.bean.TRole;
import com.hxzy.crowdfunding.service.RoleService;
import com.hxzy.crowdfunding.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("role/index")
    public String index(){
        log.info("访问角色列表页面");
        return "admin/role/index";
    }

    @ResponseBody
    @RequestMapping("role/asyncData")
    public ResponseData<PageInfo> asyncData(String search
            , @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum
            , @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

        log.info("异步获取所有角色信息...");

        try {
            PageInfo<TRole> pageInfo = roleService.selectRoleAll(pageNum,pageSize,search);
            ResponseData ok = ResponseData.ok(pageInfo);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 分配许可 role/assignPermission
    @ResponseBody
    @RequestMapping("role/assignPermission")
    public ResponseData<List> assignPermission(Integer rid,Integer[] ids){

        log.info("异步分配许可---{}---{}", rid, Arrays.toString(ids));
        try {

            roleService.assignPermission(rid,ids);
            log.info("分配许可成功");
            ResponseData ok = ResponseData.ok(null);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 访问分配许可页面
    @RequestMapping("role/toPermission")
    public String toPermission(){ // 请求中有 角色 ID
        log.info("访问分配许可页面...");
        return "admin/role/assignPermission";
    }

    // 异步获取许可信息
    @ResponseBody
    @RequestMapping("role/asyncPermissionData")
    public ResponseData<List> asyncPermissionData(Integer id){

        log.info("异步获取角色 {} 号的许可信息...", id);

        try {
            List<TPermission> list = roleService.selectPermissionAll(id);
            ResponseData ok = ResponseData.ok(list);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }


}
