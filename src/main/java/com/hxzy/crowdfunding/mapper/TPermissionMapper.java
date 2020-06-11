package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TPermission;
import com.hxzy.crowdfunding.bean.TPermissionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TPermissionMapper {
    long countByExample(TPermissionExample example);

    int deleteByExample(TPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    List<TPermission> selectByExample(TPermissionExample example);

    TPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPermission record, @Param("example") TPermissionExample example);

    int updateByExample(@Param("record") TPermission record, @Param("example") TPermissionExample example);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);
}