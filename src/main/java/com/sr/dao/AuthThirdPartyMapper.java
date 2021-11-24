package com.sr.dao;

import com.sr.entity.AuthThirdParty;
import com.sr.entity.example.AuthThirdPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthThirdPartyMapper {
    long countByExample(AuthThirdPartyExample example);

    int deleteByExample(AuthThirdPartyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthThirdParty record);

    int insertSelective(AuthThirdParty record);

    List<AuthThirdParty> selectByExample(AuthThirdPartyExample example);

    AuthThirdParty selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthThirdParty record, @Param("example") AuthThirdPartyExample example);

    int updateByExample(@Param("record") AuthThirdParty record, @Param("example") AuthThirdPartyExample example);

    int updateByPrimaryKeySelective(AuthThirdParty record);

    int updateByPrimaryKey(AuthThirdParty record);
}