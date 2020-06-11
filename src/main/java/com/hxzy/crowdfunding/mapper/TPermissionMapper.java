package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TPermission;
import com.hxzy.crowdfunding.bean.TPermissionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    /**
     * 查询指定用户所有权限
     * @param id
     * @return
     */
    @Select("select DISTINCT p.* from t_admin_role ta " +
            "LEFT JOIN t_role_permission tr on ta.roleid = tr.roleid " +
            "LEFT JOIN t_permission p on p.id = tr.permissionid " +
            "where ta.adminid = #{id} ")
    List<TPermission> selectAdminToPermissions(Integer id);
}