package com.hxzy.crowdfunding.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class tMenuVo {
    private Integer id;

    private Integer pid;

    private String name;

    private String icon;

    private String url;

    private List<tMenuVo> children = new ArrayList<>();

}
