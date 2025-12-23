package com.fashion.hunter.services.business;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.constants.DTOConstants;
import com.fashion.hunter.dao.UserDAO;
import com.fashion.hunter.dao.UserLoginDAO;
import com.fashion.hunter.dto.UserDTO;
import com.fashion.hunter.services.APIResponseBO;
import com.fashion.hunter.utils.CommonUtils;
import com.fashion.hunter.utils.GsonUtils;
import com.fashion.hunter.utils.PasswordUtils;
import com.fashion.hunter.validation.ValidationResult;
import com.fashion.hunter.validation.business.UserValidationBO;
import com.google.gson.Gson;

@Service
public class UserLoginBO extends BaseBO {
	
	@Autowired
	private UserLoginDAO userLoginDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
    private PasswordUtils passwordUtils;
	
	@Autowired
	private UserValidationBO userValidationBO;
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	private Gson gsonBOIncoming = GsonUtils.createIncomingGson();

	private APIResponseBO apiResponseBO;
	
	public UserLoginBO() {
		this.apiResponseBO = new APIResponseBO();
	}

	public APIResponse userLoginAuthentication(APIRequest apiRequest) {
		ValidationResult validationResult;
		try {
			validationResult = userValidationBO.validateMandatoryField(apiRequest, DTOConstants.USER_LOGIN_MANDATORY_FIELDS);
		} catch (Exception ex) {
			ex.printStackTrace();
			return apiResponseBO.buildUnserializationFailureErrorResponse(apiRequest);
		}

		if (!validationResult.getIsValid()) {
			return buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			UserDTO requestDTO = (UserDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming, apiRequest.getApiParams(), UserDTO.class);
			final Map<String, Object> apiParams = new HashMap<>();
			if (ObjectUtils.isNotEmpty(requestDTO.getUsername())) {
				apiParams.put(DTOConstants.USER_NAME, CommonUtils.safeTrimUpper(requestDTO.getUsername()));
			}
			if (ObjectUtils.isNotEmpty(requestDTO.getPassword().trim())) {
				apiParams.put(DTOConstants.PASSWORD, requestDTO.getPassword().trim());
			}
			final Set<String> errorMsg = new HashSet<>();

			final UserDTO userDetails = userLoginDAO.getUserDetailsByUserName(apiParams);
			if (userDetails == null || ObjectUtils.isEmpty(userDetails)) {
				errorMsg.add(validationMessage("fh.invalidUserLoginId"));
				ValidationResult checkUserValidation = userValidationBO.buildValidationResult(apiRequest, errorMsg);
				return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, checkUserValidation);
			}

			if (!passwordUtils.checkPassword(requestDTO.getPassword(), userDetails.getPassword())) {
				errorMsg.add(validationMessage("fh.invalidPassword"));
				ValidationResult checkUserValidation = userValidationBO.buildValidationResult(apiRequest, errorMsg);
				return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, checkUserValidation);
			}
			UserDTO getUserDetailsByUserId = userDAO.getUserDetailsByUserId(userDetails.getUserId());
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

}