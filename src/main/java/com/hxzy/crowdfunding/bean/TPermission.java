package com.hxzy.crowdfunding.bean;

import lombok.Data;

@Data
public class TPermission {

    private Integer id;

    private String name;

    private String title;

    private String icon;

    private Integer pid;

    private boolean checked; // 表示是否勾选

}