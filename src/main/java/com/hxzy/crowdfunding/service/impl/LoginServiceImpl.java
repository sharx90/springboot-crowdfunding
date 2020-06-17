package com.hxzy.crowdfunding.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.hxzy.crowdfunding.bean.TAdmin;
import com.hxzy.crowdfunding.bean.TAdminExample;
import com.hxzy.crowdfunding.bean.TMember;
import com.hxzy.crowdfunding.component.SmsTemplate;
import com.hxzy.crowdfunding.exception.AdminException;
import com.hxzy.crowdfunding.mapper.TAdminMapper;
import com.hxzy.crowdfunding.mapper.TMemberMapper;
import com.hxzy.crowdfunding.service.LoginService;
import com.hxzy.crowdfunding.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TAdminMapper tAdminMapper;

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private TMemberMapper tMemberMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // 登录验证
    @Override
    public void login(String loginacct, String userpswd, HttpSession session) {

        // 查询用户是否存在 (根据登录账号查询用户)
        TAdminExample example = new TAdminExample();
        // 手动添加条件
        example.createCriteria().andLoginacctEqualTo(loginacct);
        List<TAdmin> admins = tAdminMapper.selectByExample(example);

        if(admins.isEmpty()){
            log.error("{}--账户不存在",loginacct);
            throw new AdminException("用户不存在");
        }

        TAdmin admin = admins.get(0);

        // 验证密码是否正确  需要将 userpswd 页面的密码进行加密,再比较
        if(!MD5Util.digest(userpswd).equals(admin.getUserpswd())){
            log.error("{}--账户密码错误",loginacct);
            throw new AdminException("账户密码不正确");
        }

        // 验证账户是否可用
        if(admin.getStatus() == 2){
            log.error("{}--账户已锁定",loginacct);
            throw new AdminException("账户已锁定");
        }else if (admin.getStatus() == 0){
            log.error("{}--账户未启用",loginacct);
            throw new AdminException("账户未启用");
        }

        // 登录成功,需要将 登录用户保存到 session
        admin.setUserpswd(null);
        session.setAttribute("loginUser", admin);

    }

    // 获取验证码
    @Override
    public void getCode(String phone, HttpSession session) {
        try {
            // 获取随机验证码
            String code = smsTemplate.randomCode();
            log.info("{}---验证码---{}",phone,code);

            SendSmsResponse smsResponse = smsTemplate.sendSms(phone,code);

            if("OK".equals(smsResponse.getCode())){
                // 将验证码保存至 session
                ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
                ops.set(phone,code,5, TimeUnit.MINUTES);
            }else{
                log.error("获取验证码失败---{}",smsResponse.getMessage());
                throw new RuntimeException("获取验证码失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取验证码失败---{}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 注册
    @Override
    public void doReg(TMember tMember, String code, HttpSession session) {

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String s = ops.get(tMember.getLoginacct());
        // 判断验证码是否正确
        if(!code.equals(s)){
            throw new RuntimeException("验证码错误");
        }

        try {
            int i = tMemberMapper.insertSelective(tMember);
            if(i <= 0){
                log.info("注册失败");
                throw new RuntimeException("数据未添加");
            }else{
                log.info("注册成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行SQL语句失败---{}", e.getMessage());
            throw new RuntimeException("系统错误");
        }

    }


}
