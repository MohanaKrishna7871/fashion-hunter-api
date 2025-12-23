package com.fashion.hunter.validation;

import com.fashion.hunter.common.dto.APIError;

public class ValidationResult {
	private Object objectForValidation;
	private APIError apiError;
	private boolean isValid = false;

	public ValidationResult() {
	}

	public Object getObjectForValidation() {
		return this.objectForValidation;
	}

	public void setObjectForValidation(Object objectForValidation) {
		this.objectForValidation = objectForValidation;
	}

	public APIError getApiError() {
		return this.apiError;
	}

	public void setApiError(APIError apiError) {
		this.apiError = apiError;
	}

	public boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
}
