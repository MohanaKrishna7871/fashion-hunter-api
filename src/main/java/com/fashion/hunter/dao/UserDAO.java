package com.fashion.hunter.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fashion.hunter.dto.UserDTO;

@Repository
public interface UserDAO {

	void insertUserLogin(@Param("apiParamMap") UserDTO requestDTO) throws Exception;

	void insertUserDetails(@Param("apiParamMap") UserDTO requestDTO) throws Exception;

	void insertUserRole(@Param("user") UserDTO requestDTO, @Param("roleId") String roleId,
			@Param("roleCode") String roleCode) throws Exception;

	Map<String, Object> findActiveRole(@Param("roleId") String roleId, @Param("roleCode") String roleCode,
			@Param("statusCode") String statusCode);

	Map<String, Object> checkUserConflict(@Param("email") String email, @Param("username") String username);

	UserDTO getUserDetailsByUserId(@Param("userId") String userId);

	void updateUserLogin(UserDTO requestDTO);

	void updateUserDetails(UserDTO requestDTO);

	UserDTO getUserMetaData(@Param("userId") String userId);

	void deleteUserByUserId(String userId);
}