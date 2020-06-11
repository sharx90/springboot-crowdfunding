package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TAdminRole;
import com.hxzy.crowdfunding.bean.TAdminRoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TAdminRoleMapper {
    long countByExample(TAdminRoleExample example);

    int deleteByExample(TAdminRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAdminRole record);

    int insertSelective(TAdminRole record);

    List<TAdminRole> selectByExample(TAdminRoleExample example);

    TAdminRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAdminRole record, @Param("example") TAdminRoleExample example);

    int updateByExample(@Param("record") TAdminRole record, @Param("example") TAdminRoleExample example);

    int updateByPrimaryKeySelective(TAdminRole record);

    int updateByPrimaryKey(TAdminRole record);

    /**
     * 角色授权
     * @param uid
     * @param rids
     * @return
     */
    int assignRole(@Param("uid") Integer uid,@Param("rids") Integer[] rids);

    /**
     * 查询用户授权角色
     * @param id
     * @return
     */
    @Select("select roleid from t_admin_role where adminid = #{id}")
    List<Integer> selectRoleIdsByAdminId(@Param("id") Integer id);

    int unAassignRole(@Param("uid") Integer uid,@Param("rids") Integer[] rids);
}