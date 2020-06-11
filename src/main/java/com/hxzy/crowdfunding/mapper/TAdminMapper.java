package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TAdmin;
import com.hxzy.crowdfunding.bean.TAdminExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TAdminMapper {
    long countByExample(TAdminExample example);

    int deleteByExample(TAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAdmin record);

    int insertSelective(TAdmin record);

    List<TAdmin> selectByExample(TAdminExample example);

    TAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAdmin record, @Param("example") TAdminExample example);

    int updateByExample(@Param("record") TAdmin record, @Param("example") TAdminExample example);

    int updateByPrimaryKeySelective(TAdmin record);

    int updateByPrimaryKey(TAdmin record);
}
