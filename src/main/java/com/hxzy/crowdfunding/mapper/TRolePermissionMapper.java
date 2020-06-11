package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TRolePermission;
import com.hxzy.crowdfunding.bean.TRolePermissionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TRolePermissionMapper {
    long countByExample(TRolePermissionExample example);

    int deleteByExample(TRolePermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TRolePermission record);

    int insertSelective(TRolePermission record);

    List<TRolePermission> selectByExample(TRolePermissionExample example);

    TRolePermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TRolePermission record, @Param("example") TRolePermissionExample example);

    int updateByExample(@Param("record") TRolePermission record, @Param("example") TRolePermissionExample example);

    int updateByPrimaryKeySelective(TRolePermission record);

    int updateByPrimaryKey(TRolePermission record);

    /**
     * 为指定角色分配许可
     * @param rid
     * @param ids
     */
    int assignPermission(@Param("rid") Integer rid,@Param("ids") Integer[] ids);
}