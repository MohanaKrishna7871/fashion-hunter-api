package com.fashion.hunter.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fashion.hunter.dto.UserDTO;

@Repository
public interface UserLoginDAO {

	UserDTO getUserDetailsByUserName(@Param("apiParamMap") Map<String, Object> apiParams);
}