package com.fashion.hunter.validation.business;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.constants.DTOConstants;
import com.fashion.hunter.validation.ValidationResult;

@Service
public class UserValidationBO extends BaseValidationBO {

	public ValidationResult addUserValidation(APIRequest apiRequest) throws Exception {

	    Set<String> exceptions = new LinkedHashSet<>();
	    // =====================================================
	    // 1. Mandatory field validation
	    // =====================================================
	    List<String> mandatoryFields = DTOConstants.ADD_USER_MANDATORY_FIELDS;
	    ValidationResult mandatoryValidation = validateMandatoryField(apiRequest, mandatoryFields);

	    if (!mandatoryValidation.getIsValid()) {
	        return mandatoryValidation;
	    }

	    // =====================================================
	    // 2. roleCodes presence validation
	    // =====================================================
	    Object roleCodesObj = apiRequest.getApiParams().get("roleCodes");

	    if (!(roleCodesObj instanceof List)
	            || CollectionUtils.isEmpty((List<?>) roleCodesObj)) {

	        exceptions.add(resolveMessage(resolveMessage("fh.missingParam", DTOConstants.ROLE_CODES)));
	        return buildValidationResult(apiRequest, exceptions);
	    }

	    @SuppressWarnings("unchecked")
	    List<Map<String, Object>> roleCodes = (List<Map<String, Object>>) roleCodesObj;

	    // =====================================================
	    // 3. roleId & roleCode validation
	    // =====================================================
	    for (Map<String, Object> role : roleCodes) {

	        String roleId = StringUtils.trimToNull(Objects.toString(role.get("roleId"), null));

	        String roleCode = StringUtils.trimToNull(Objects.toString(role.get("roleCode"), null));

	        if (StringUtils.isBlank(roleId)) {
	            exceptions.add(resolveMessage("fh.missingObjectParam", DTOConstants.ROLE_ID,DTOConstants.ROLE_CODES));
	        } else {
	        	validateUUIDAttribute(role, exceptions, DTOConstants.ROLE_ID);
	        }

	        if (StringUtils.isBlank(roleCode)) {
	            exceptions.add(resolveMessage("fh.missingObjectParam", DTOConstants.ROLE_CODE,DTOConstants.ROLE_CODES));
	        }
	    }

	    // =====================================================
	    // 4. Final validation result
	    // =====================================================
	    return buildValidationResult(apiRequest, exceptions);
	}

	public ValidationResult getUserDetailsValidation (APIRequest apiRequest) throws Exception {

	    Set<String> exceptions = new LinkedHashSet<>();
	    String mandatoryFields = DTOConstants.USER_ID;
	    ValidationResult mandatoryValidation = validateMandatoryField(apiRequest, mandatoryFields);

	    if (!mandatoryValidation.getIsValid()) {
	        return mandatoryValidation;
	    }

	    return buildValidationResult(apiRequest, exceptions);
	}
	
	public ValidationResult updateUserDetailsValidation (APIRequest apiRequest) throws Exception {
		final Map<String, Object> apiParams = apiRequest.getApiParams();
	    Set<String> exceptions = new LinkedHashSet<>();
	    String mandatoryFields = DTOConstants.USER_ID;
	    if (isObjectPresentInMap(apiParams, mandatoryFields)) {
	    	exceptions.add(resolveMessage("fh.missingParam", mandatoryFields));
		}
	    if (apiParams.containsKey(DTOConstants.STATUS_CODE)) {
			String status = ((String) apiParams.get(DTOConstants.STATUS_CODE)).trim();
			if(StringUtils.isNotEmpty(status)) {
		        if (!DTOConstants.ACTIVE.equalsIgnoreCase(status) && !DTOConstants.INACTIVE.equalsIgnoreCase(status)) {
		        	exceptions.add(resolveMessage("fh.invalidYesOrNoValue", DTOConstants.STATUS_CODE));
		        }
			}
	    }

	    return buildValidationResult(apiRequest, exceptions);
	}
	
}
