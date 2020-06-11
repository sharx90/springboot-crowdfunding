package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TRole;
import com.hxzy.crowdfunding.bean.TRoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TRoleMapper {
    long countByExample(TRoleExample example);

    int deleteByExample(TRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TRole record);

    int insertSelective(TRole record);

    List<TRole> selectByExample(TRoleExample example);

    TRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TRole record, @Param("example") TRoleExample example);

    int updateByExample(@Param("record") TRole record, @Param("example") TRoleExample example);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    /**
     * 查询角色已分配的许可
     * @param id
     * @return
     */
    @Select("select permissionid from t_role_permission where roleid = #{id}")
    List<Integer> selectAssignPermission(Integer id);
}
