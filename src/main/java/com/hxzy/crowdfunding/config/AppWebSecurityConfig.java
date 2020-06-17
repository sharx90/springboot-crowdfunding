package com.hxzy.crowdfunding.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxzy.crowdfunding.util.ResponseData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        super.configure(http);

        http.formLogin()
                .loginPage("/tologin")
                .loginProcessingUrl("/doLogin") //登录映射, 默认 login
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")
                .successHandler((req, resp, authentication) -> {
                    HttpSession session = req.getSession();
                    ResponseData ok = ResponseData.ok(session.getServletContext().getContextPath() + "/admin");
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(ok));

                })   //前后端分离使用
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    ResponseData fail = ResponseData.fail(e.getMessage());
                    if(e instanceof LockedException) {
                        fail.setMsg("账号被锁定");
                    }else if(e instanceof BadCredentialsException) {
                        fail.setMsg("用户名或者密码错误");
                    }else if(e instanceof DisabledException) {
                        fail.setMsg("账户被禁用");
                    }else if(e instanceof AccountExpiredException) {
                        fail.setMsg("账户过期");
                    }else if(e instanceof CredentialsExpiredException) {
                        fail.setMsg("密码过期");
                    }else {
                        fail.setMsg("登录失败");
                    }
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(fail));
                }); // 登录成功路径 (重定向)


        http.authorizeRequests()
                // 设置指定的映射路径不认证
                .antMatchers("/static/**","/index","/","/tologin","/toReg","/doReg","/getCode").permitAll()
                .anyRequest().authenticated();

        // 当前没有权限是访问的页面地址
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        // 开启记住我功能
        http.rememberMe(); // 登录表单需要添加 remember-me 参数

        // 添加注销
        http.logout().logoutSuccessUrl("/index"); //默认的退出映射路径 logout

        http.csrf().disable();// 禁用 csrf （不建议禁用）
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyUserDetailService myUserDetailService(){
        return new MyUserDetailService();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService(myUserDetailService()).passwordEncoder(passwordEncoder());
    }
}
