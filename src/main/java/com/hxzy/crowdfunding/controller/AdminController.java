package com.hxzy.crowdfunding.controller;

import com.github.pagehelper.PageInfo;
import com.hxzy.crowdfunding.bean.TAdmin;
import com.hxzy.crowdfunding.service.AdminService;
import com.hxzy.crowdfunding.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 为用户授权角色
    @ResponseBody
    @RequestMapping("user/assignRole")
    public ResponseData assignRole(Integer uid,Integer[] rids){
        log.info("为 {} 用户授权角色---{}",uid, Arrays.toString(rids));
        try {

            adminService.assignRole(uid,rids);

            log.info("授权成功");
            return ResponseData.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }
    //为用户删除角色
    @ResponseBody
    @RequestMapping("user/unAssignRole")
    public ResponseData unAssignRole(Integer uid,Integer[] rids){
        log.info("为 {} 用户删除角色---{}",uid, Arrays.toString(rids));
        try {

            adminService.unAssignRole(uid,rids);

            log.info("删除成功");
            return ResponseData.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 访问授权角色页面
    @RequestMapping("/user/toAssign")
    public String toAssign(Integer id, Model model){

        log.info("访问 {} 号用户授权角色页面", id);

        // 查询所有数据
        // 查询已分配的角色
        // 判断未分配的角色
        adminService.selectAssignRole(id,model);

        return "admin/user/assignRole";// 请求转发
    }
    // 批量删除
    @RequestMapping("user/batchDelete")
    @ResponseBody
    public ResponseData<String> batchDelete(Integer[] ids){
        log.info("批量删除管理员---{}", Arrays.toString(ids));
        try {
            adminService.batchDelete(ids);
            log.info("批量删除管理员成功");
            return ResponseData.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 修改
    @ResponseBody
    @PutMapping("user/edit")
    public ResponseData<String> doUpdate(TAdmin tAdmin,Integer pageNum, HttpServletRequest request){
        log.info("修改管理员...");
        try {
            adminService.updateAdmin(tAdmin);
            log.info("修改管理员成功");
            return ResponseData.ok(request.getContextPath()+"/user/index?pageNum="+pageNum);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 去修改页面
    @GetMapping("user/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, Integer pageNum, Model model){
        log.info("跳转管理员修改页面...");

        // 根据ID查询管理员
        TAdmin tAdmin = adminService.selectAdminById(id);

        model.addAttribute("admin", tAdmin);

        return "admin/user/edit";
    }

    // 删除
    @ResponseBody
    @DeleteMapping("user/edit/{id}")
    public ResponseData<String> deleteAdmin(@PathVariable("id") Integer id){
        log.info("删除管理员---{}",id);
        try {
            adminService.deleteAdmin(id);
            log.info("删除管理员成功");
            return ResponseData.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 添加
    @ResponseBody
    @PostMapping("user/edit")
    public ResponseData<String> doAdd(TAdmin tAdmin, HttpServletRequest request){
        log.info("添加管理员...");
        try {
            adminService.saveAdmin(tAdmin);
            log.info("添加管理员成功");
            return ResponseData.ok(request.getContextPath()+"/user/index");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData<String> fail = ResponseData.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }

    // 去新增页面
    @RequestMapping("user/toAdd")
    public String toAdd(){
        log.info("跳转管理员新增页面...");
        return "admin/user/edit";
    }

    // 列表查询 (包含条件查询)
    @RequestMapping("user/index")
    public String index(Model model, String search
        , @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum
        , @RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize){
        log.info("访问用户维护列表页---条件:{}",search);

        // 查询后台管理员信息
        PageInfo<TAdmin> pageInfo = adminService.selectAdminAll(pageNum,pageSize,search);

        model.addAttribute("pageInfo", pageInfo);

        return "admin/user/index"; // 请求转发
    }
}
