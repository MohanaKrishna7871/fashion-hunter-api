package com.fashion.hunter.services.business;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.constants.DTOConstants;
import com.fashion.hunter.dao.UserDAO;
import com.fashion.hunter.dto.UserDTO;
import com.fashion.hunter.dto.UserRoleDTO;
import com.fashion.hunter.services.APIResponseBO;
import com.fashion.hunter.utils.CommonUtils;
import com.fashion.hunter.utils.GsonUtils;
import com.fashion.hunter.utils.PasswordUtils;
import com.fashion.hunter.validation.ValidationResult;
import com.fashion.hunter.validation.business.UserValidationBO;
import com.google.gson.Gson;

@Service
public class UserBO extends BaseBO {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
    private PasswordUtils passwordUtils;
	
	@Autowired
	private UserValidationBO userValidationBO;
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	private Gson gsonBOIncoming = GsonUtils.createIncomingGson();

	private APIResponseBO apiResponseBO;
	
	public UserBO() {
		this.apiResponseBO = new APIResponseBO();
	}

	public APIResponse addUser(APIRequest apiRequest) {
		ValidationResult validationResult;
		try {
			validationResult = userValidationBO.addUserValidation(apiRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
			return apiResponseBO.buildUnserializationFailureErrorResponse(apiRequest);
		}

		if (!validationResult.getIsValid()) {
			return buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			UserDTO requestDTO = (UserDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming, apiRequest.getApiParams(), UserDTO.class);
			UUID uuid = UUID.randomUUID();
			requestDTO.setCreatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			requestDTO.setUpdatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			requestDTO.setUserId(uuid.toString());
			requestDTO.setUsername(CommonUtils.safeTrimUpper(requestDTO.getUsername()));
			requestDTO.setEmail(CommonUtils.safeTrimUpper(requestDTO.getEmail()));
			requestDTO.setStatusCode(DTOConstants.ACTIVE);
			requestDTO.setIsLocked(DTOConstants.ACTIVE);
			requestDTO.setLastLoginDateTime(null);
			String hashedPassword = passwordUtils.hashPassword(requestDTO.getPassword());
			requestDTO.setPassword(hashedPassword);
			Set<String> exceptions = new LinkedHashSet<>();
			Map<String, Object> checkUserConflict = ObjectUtils.defaultIfNull(
					userDAO.checkUserConflict(requestDTO.getEmail(), requestDTO.getUsername()), Collections.emptyMap());

			boolean emailExists = Boolean.TRUE.equals(MapUtils.getBoolean(checkUserConflict, "email_exists"));

			boolean usernameExists = Boolean.TRUE.equals(MapUtils.getBoolean(checkUserConflict, "username_exists"));

			if (emailExists || usernameExists) {
				if (emailExists) {
					exceptions.add(resolveMessage("fh.checkAlreadyExists", DTOConstants.EMAIL));
				}
				if (usernameExists) {
					exceptions.add(resolveMessage("fh.checkAlreadyExists", DTOConstants.USER_NAME));
				}

				ValidationResult checkUserValidation = userValidationBO.buildValidationResult(apiRequest, exceptions);

				return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, checkUserValidation);
			}

			// ================= Insert Login =================
            userDAO.insertUserLogin(requestDTO);

            // ================= Insert Details =================
            requestDTO.setFirstName(CommonUtils.safeTrimUpper(requestDTO.getFirstName()));
            requestDTO.setLastName(CommonUtils.safeTrimUpper(requestDTO.getLastName()));
            requestDTO.setPhoneNumber(StringUtils.trimToNull(requestDTO.getPhoneNumber()));
            userDAO.insertUserDetails(requestDTO);

            // ================= Assign Roles =================
            if (CollectionUtils.isNotEmpty(requestDTO.getRoleCodes())) {

                for (UserRoleDTO role : requestDTO.getRoleCodes()) {

                    String roleId = StringUtils.trimToNull(role.getRoleId());
                    String roleCode = StringUtils.trimToNull(role.getRoleCode());

                    // ================= VALIDATE ROLE =================
                    Map<String, Object> dbRole = userDAO.findActiveRole(roleId, roleCode,DTOConstants.ACTIVE);

					if (MapUtils.isEmpty(dbRole)) {
						final ValidationResult getValidationResult = userValidationBO.buildGenericValidationResult(apiRequest,
								"Invalid role provided. roleId=" + roleId + ", roleCode=" + roleCode);
						if (!getValidationResult.getIsValid()) {
							return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, getValidationResult);
						}
					}

                    // ================= INSERT ROLE =================
                    userDAO.insertUserRole(requestDTO, roleId, roleCode);
                }
            }
			return apiResponseBO.buildValidResponse(apiRequest, resolveMessage("fh.createdSuccessful", DTOConstants.USER));
		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error(ex.getMessage(), ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

	public APIResponse getUserDetailsByUserId(APIRequest apiRequest) {
		ValidationResult validationResult;
		try {
			validationResult = userValidationBO.getUserDetailsValidation(apiRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
			return apiResponseBO.buildUnserializationFailureErrorResponse(apiRequest);
		}

		if (!validationResult.getIsValid()) {
			return buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			UserDTO requestDTO = (UserDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming, apiRequest.getApiParams(), UserDTO.class);
			UserDTO getUserDetailsByUserId = userDAO.getUserDetailsByUserId(requestDTO.getUserId());
			if(ObjectUtils.isNotEmpty(getUserDetailsByUserId)) {
				getUserDetailsByUserId.getStatus();
				return apiResponseBO.buildValidResponse(apiRequest, getUserDetailsByUserId);
			} else {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noUserFound"));
			}
		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error(ex.getMessage(), ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}
	
	public APIResponse updateUserByUserId(APIRequest apiRequest) {

		ValidationResult validationResult;
		try {
			validationResult = userValidationBO.validateMandatoryField(apiRequest, DTOConstants.USER_ID);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return apiResponseBO.buildUnserializationFailureErrorResponse(apiRequest);
		}

		if (!validationResult.getIsValid()) {
			return buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			UserDTO requestDTO = (UserDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming, apiRequest.getApiParams(),
					UserDTO.class);

			UserDTO userMetaData = userDAO.getUserMetaData(requestDTO.getUserId());

			if (ObjectUtils.isEmpty(userMetaData)) {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noUserFound"));
			}

			requestDTO.setUpdatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			requestDTO.setUsername(CommonUtils.safeTrimUpper(requestDTO.getUsername()));
			requestDTO.setEmail(CommonUtils.safeTrimUpper(requestDTO.getEmail()));
			if (StringUtils.isNotBlank(CommonUtils.safeTrimUpper(requestDTO.getStatusCode()))) {
				requestDTO.setStatusCode(CommonUtils.safeTrimUpper(requestDTO.getStatusCode()));
			}

//			String newPlainPassword = StringUtils.trimToNull(requestDTO.getPassword());
//			String existingHashedPassword = StringUtils.trimToNull(userMetaData.getPassword());
//
//			if (newPlainPassword != null && (existingHashedPassword == null || !passwordUtils.checkPassword(newPlainPassword, existingHashedPassword))) {
//				requestDTO.setPassword(passwordUtils.hashPassword(newPlainPassword));
//			} else {
//				requestDTO.setPassword(null); // prevents password update
//			}

			/* ================= Conflict check ONLY if changed ================= */
			boolean emailChanged = !StringUtils.equalsIgnoreCase(requestDTO.getEmail(), userMetaData.getEmail());
			boolean usernameChanged = !StringUtils.equalsIgnoreCase(requestDTO.getUsername(), userMetaData.getUsername());

			if (emailChanged || usernameChanged) {
				Map<String, Object> checkUserConflict = ObjectUtils.defaultIfNull(
						userDAO.checkUserConflict(requestDTO.getEmail(), requestDTO.getUsername()),
						Collections.emptyMap());
				boolean emailExists = MapUtils.getBoolean(checkUserConflict, "email_exists", false);
				boolean usernameExists = MapUtils.getBoolean(checkUserConflict, "username_exists", false);

				if (emailExists || usernameExists) {
					Set<String> exceptions = new LinkedHashSet<>();
					if (emailExists) {
						exceptions.add(resolveMessage("fh.checkAlreadyExists", DTOConstants.EMAIL));
					}
					if (usernameExists) {
						exceptions.add(resolveMessage("fh.checkAlreadyExists", DTOConstants.USER_NAME));
					}
					ValidationResult checkUserValidation = userValidationBO.buildValidationResult(apiRequest, exceptions);
					return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, checkUserValidation);
				}
			}

			/* ================= Update Login ================= */
			userDAO.updateUserLogin(requestDTO);

			/* ================= Update Details ================= */
			requestDTO.setFirstName(CommonUtils.safeTrimUpper(requestDTO.getFirstName()));
			requestDTO.setLastName(CommonUtils.safeTrimUpper(requestDTO.getLastName()));
			requestDTO.setPhoneNumber(StringUtils.trimToNull(requestDTO.getPhoneNumber()));

			userDAO.updateUserDetails(requestDTO);

			return apiResponseBO.buildValidResponse(apiRequest, resolveMessage("fh.updatedSuccessful", DTOConstants.USER));
		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error(ex.getMessage(), ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

	public APIResponse deleteUserByUserId(APIRequest apiRequest) {
		ValidationResult validationResult;
		try {
			validationResult = userValidationBO.validateMandatoryField(apiRequest, DTOConstants.USER_ID);
		} catch (Exception ex) {
			ex.printStackTrace();
			return apiResponseBO.buildUnserializationFailureErrorResponse(apiRequest);
		}

		if (!validationResult.getIsValid()) {
			return buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			UserDTO requestDTO = (UserDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming, apiRequest.getApiParams(), UserDTO.class);
			UserDTO getUserDetailsByUserId = userDAO.getUserMetaData(requestDTO.getUserId());
			if(ObjectUtils.isNotEmpty(getUserDetailsByUserId)) {
				userDAO.deleteUserByUserId(requestDTO.getUserId());
				return apiResponseBO.buildValidResponse(apiRequest, resolveMessage("fh.deleteSuccessful", DTOConstants.USER));
			} else {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noUserFound"));
			}
		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error(ex.getMessage(), ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

}