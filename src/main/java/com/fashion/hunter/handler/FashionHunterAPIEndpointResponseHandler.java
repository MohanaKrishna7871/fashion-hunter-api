package com.fashion.hunter.handler;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.constants.FashionHunterTransactionIDValues;
import com.fashion.hunter.services.APIRequestBO;
import com.fashion.hunter.services.APIResponseBO;
import com.fashion.hunter.services.business.ProductBO;
import com.fashion.hunter.services.business.SiteConfigBO;
import com.fashion.hunter.services.business.UserBO;
import com.fashion.hunter.services.business.UserLoginBO;
import com.fashion.hunter.validation.APIRequestValidator;
import com.fashion.hunter.validation.ValidationResult;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class FashionHunterAPIEndpointResponseHandler implements APIEndpointResponseHandler<APIResponse>{

	private final Logger logger = LogManager.getLogger(this.getClass());

	private APIRequestValidator apiRequestValidationBO;
	private APIRequestBO apiRequestBO;
	private APIResponseBO apiResponseBO;

    @Autowired
    private UserBO userBO;
    @Autowired
    private UserLoginBO userLoginBO;
    @Autowired
    private SiteConfigBO siteConfigBO;
    @Autowired
    private ProductBO productBO;
    
	public FashionHunterAPIEndpointResponseHandler() {
		this.apiResponseBO = new APIResponseBO();
		this.apiRequestBO = new APIRequestBO();
		this.apiRequestValidationBO = new APIRequestValidator();
	}

    /**
	 * Handles the initial request from the {@link fashionHunterAPIEndpoint}
	 *
	 * @param httpRequest
	 *                    - The HTTP request object
	 * @param requestBody
	 *                    - The body of the HTTP request (as json), broken into a
	 *                    map
	 */
	@Override
	public APIResponse handleAPIRequest(HttpServletRequest httpRequest, Map<String, Object> requestBody) {

		APIRequest apiRequest = apiRequestBO.convertHttpRequestBodyMapToAPIRequest(requestBody);

		// ========================================================
		// High Level Validation
		// ========================================================
		try {
			ValidationResult validationResult = apiRequestValidationBO.validateAPIRequest(apiRequest);

			if (!validationResult.getIsValid()) {
				logger.error("API request failed high level validation: [Code: {} | Detailed Message: {} | Errors: {}]",
						validationResult.getApiError().getErrorCode(),
						validationResult.getApiError().getDetailMessage(),
						validationResult.getApiError().getErrors());

				return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
			}

			// ========================================================
			// Now Attempt Transaction
			// ========================================================
			return generateAPIResponse(apiRequest);
		} catch (Exception ex) {
			logger.fatal(ex);
			String errorCode = apiResponseBO.generateResponseErrorCode();
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

    /**
     * Executes transaction based on transactionId
     */
	private APIResponse generateAPIResponse(APIRequest apiRequest) {

		switch (apiRequest.getApiContext().getTransactionId()) {

			// -- USER -- //
			case FashionHunterTransactionIDValues.ADD_NEW_USER:
				return userBO.addUser(apiRequest);
			case FashionHunterTransactionIDValues.GET_USER_DETAILS_BY_USER_ID:
				return userBO.getUserDetailsByUserId(apiRequest);
			case FashionHunterTransactionIDValues.EDIT_USER_DETAILS:
				return userBO.updateUserByUserId(apiRequest);
			case FashionHunterTransactionIDValues.DELETE_USER_BY_USER_ID:
				return userBO.deleteUserByUserId(apiRequest);
				
			// -- LOGIN -- //
			case FashionHunterTransactionIDValues.USER_LOGIN_AUTHENTICATION:
				return userLoginBO.userLoginAuthentication(apiRequest);
				
			// -- SITE CONFIG -- //
			case FashionHunterTransactionIDValues.GET_SITE_CONFIG:
				return siteConfigBO.getSiteConfigDetails(apiRequest);
			case FashionHunterTransactionIDValues.UPDATE_SITE_CONFIG:
				return siteConfigBO.updateSiteConfigDetails(apiRequest);
			case FashionHunterTransactionIDValues.ADD_SITE_CONFIG:
				return siteConfigBO.addSiteConfig(apiRequest);
				
			// -- PRODUCT IMAGE -- //
			case FashionHunterTransactionIDValues.ADD_PRODUCT_IMAGE:
				return productBO.addProductImage(apiRequest);
			case FashionHunterTransactionIDValues.GET_PRODUCTS_IMAGE:
				return productBO.getProductImage(apiRequest);
			case FashionHunterTransactionIDValues.UPDATE_PRODUCT_IMAGE:
				return productBO.updateProductImage(apiRequest);

			// --- DEFAULT --- //
			default:
				return apiResponseBO.generateInvalidAPIEndpointErrorResponse(apiRequest);
		}
	}
}
