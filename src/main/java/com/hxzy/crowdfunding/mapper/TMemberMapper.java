package com.hxzy.crowdfunding.mapper;

import com.hxzy.crowdfunding.bean.TMember;
import com.hxzy.crowdfunding.bean.TMemberExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TMemberMapper {
    long countByExample(TMemberExample example);

    int deleteByExample(TMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMember record);

    int insertSelective(TMember record);

    List<TMember> selectByExample(TMemberExample example);

    TMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByExample(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByPrimaryKeySelective(TMember record);

    int updateByPrimaryKey(TMember record);
}
