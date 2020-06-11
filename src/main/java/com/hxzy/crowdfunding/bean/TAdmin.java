package com.hxzy.crowdfunding.bean;

import lombok.Data;

@Data
public class TAdmin {

    private Integer id;

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String createtime;

    private Integer status; // 账户状态  0.未启用   1.启用    2.锁定
}
