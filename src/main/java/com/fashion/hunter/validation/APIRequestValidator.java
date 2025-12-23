package com.fashion.hunter.validation;
import java.util.HashSet;
import java.util.Set;

import com.fashion.hunter.common.dto.APIContext;
import com.fashion.hunter.common.dto.APIError;
import com.fashion.hunter.common.dto.APIRequest;

public class APIRequestValidator {
	   public APIRequestValidator() {
	   }

	   public ValidationResult validateGRPCRequest(APIRequest apiRequest) {
	      return this.getValidationResult(apiRequest);
	   }

	   public ValidationResult validateAPIRequest(APIRequest apiRequest) {
	      return this.getValidationResult(apiRequest);
	   }

	   private ValidationResult getValidationResult(APIRequest apiRequest) {
	      ValidationResult validationResult = new ValidationResult();
	      Set<String> allValidationExceptions = new HashSet();
	      Boolean isValid = true;
	      if (apiRequest != null) {
	         if (apiRequest.getApiContext() != null) {
	            APIContext context = apiRequest.getApiContext();
	            this.validateField(context.getTransactionId(), "transactionId is required in apiContext object of request.", allValidationExceptions);
	            this.validateField(context.getSource(), "source is required in apiContext object of request.", allValidationExceptions);
	            this.validateField(context.getUsername(), "username is required in apiContext object of request.", allValidationExceptions);
	            if (context.getRequestProtocol() == null) {
	               allValidationExceptions.add("protocol is required in apiContext object of request and must be a valid protocol.");
	            }
	         } else {
	            allValidationExceptions.add("apiContext object is required in request.");
	         }
	      } else {
	         allValidationExceptions.add("Invalid request. Please check your request body and try again.");
	      }

	      if (!allValidationExceptions.isEmpty()) {
	         isValid = false;
	         validationResult.setApiError(this.createAPIError(allValidationExceptions));
	      }

	      validationResult.setIsValid(isValid);
	      validationResult.setObjectForValidation(apiRequest);
	      return validationResult;
	   }

	   private void validateField(String field, String errorMessage, Set<String> exceptions) {
	      if (field == null || field.isEmpty()) {
	         exceptions.add(errorMessage);
	      }

	   }

	   private APIError createAPIError(Set<String> exceptions) {
	      if (exceptions.isEmpty()) {
	         return null;
	      } else {
	         APIError apiError = new APIError();
	         apiError.setShortMessage("Validation Failure");
	         apiError.setDetailMessage("Cannot process request due to validation failure(s)");
	         apiError.setErrors(exceptions);
	         return apiError;
	      }
	   }
	}
