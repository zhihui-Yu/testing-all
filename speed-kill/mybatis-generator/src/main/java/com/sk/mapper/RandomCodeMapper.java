package com.sk.mapper;

import com.sk.pojo.RandomCode;
import com.sk.pojo.RandomCodeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RandomCodeMapper {
    int countByExample(RandomCodeExample example);

    int deleteByExample(RandomCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RandomCode record);

    int insertSelective(RandomCode record);

    List<RandomCode> selectByExample(RandomCodeExample example);

    RandomCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RandomCode record, @Param("org.example") RandomCodeExample example);

    int updateByExample(@Param("record") RandomCode record, @Param("org.example") RandomCodeExample example);

    int updateByPrimaryKeySelective(RandomCode record);

    int updateByPrimaryKey(RandomCode record);
}