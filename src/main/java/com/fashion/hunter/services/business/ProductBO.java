package com.fashion.hunter.services.business;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.constants.DTOConstants;
import com.fashion.hunter.dao.ProductDAO;
import com.fashion.hunter.dto.ProductDTO;
import com.fashion.hunter.services.APIResponseBO;
import com.fashion.hunter.utils.CommonUtils;
import com.fashion.hunter.utils.GsonUtils;
import com.fashion.hunter.validation.ValidationResult;
import com.fashion.hunter.validation.business.UserValidationBO;
import com.google.gson.Gson;

@Service
public class ProductBO extends BaseBO {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private UserValidationBO userValidationBO;

	private final Logger logger = LogManager.getLogger(this.getClass());

	private Gson gsonBOIncoming = GsonUtils.createIncomingGson();

	private APIResponseBO apiResponseBO;

	public ProductBO() {
		this.apiResponseBO = new APIResponseBO();
	}

	public APIResponse addProductImage(APIRequest apiRequest) {

		// =====================================================
		// 1. Mandatory validation
		// =====================================================
		ValidationResult validationResult = userValidationBO.validateMandatoryField(apiRequest,
				DTOConstants.ADD_PRODUCT_MANDATORY_FIELDS);

		if (!validationResult.getIsValid()) {
			return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			ProductDTO requestDTO = (ProductDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming,
					apiRequest.getApiParams(), ProductDTO.class);
			requestDTO.setCreatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			requestDTO.setUpdatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			if (StringUtils.isNotBlank(requestDTO.getImageData())) {
				requestDTO.setImageBytes(Base64.getDecoder().decode(requestDTO.getImageData()));
			}
			productDAO.insertProductImage(requestDTO);
			return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.productImageaddedSuccessful"));

		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error("Error adding product image", ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

	public APIResponse getProductImage(APIRequest apiRequest) {

		// =====================================================
		// 1. Mandatory validation
		// =====================================================
		ValidationResult validationResult = userValidationBO.validateMandatoryField(apiRequest,
				DTOConstants.STATUS_CODE);

		if (!validationResult.getIsValid()) {
			return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			ProductDTO requestDTO = (ProductDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming,
					apiRequest.getApiParams(), ProductDTO.class);

			List<ProductDTO> rows = productDAO.getAllProductRecords(requestDTO);

			if (CollectionUtils.isEmpty(rows)) {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noRecords"));
			}

			// =====================================================
			// 2. Group FIRST (null-safe)
			// =====================================================
			Map<String, List<ProductDTO>> grouped = rows.stream()
					.filter(p -> StringUtils.isNotBlank(p.getProductName())).collect(
							Collectors.groupingBy(ProductDTO::getProductName, LinkedHashMap::new, Collectors.toList()));

			// =====================================================
			// 3. Convert BYTEA → Base64 AFTER grouping
			// =====================================================
			grouped.values().forEach(list -> list.forEach(p -> {
				if (p.getImageBytes() != null) {
					p.setImageData(Base64.getEncoder().encodeToString(p.getImageBytes()));
					p.setImageBytes(null); // free memory
				}
			}));

			// =====================================================
			// 4. Build response
			// =====================================================
			List<Map<String, Object>> response = new ArrayList<>();

			grouped.forEach((productName, products) -> {
				Map<String, Object> block = new LinkedHashMap<>();
				block.put("productName", productName);
				block.put("products", products);
				response.add(block);
			});

			return apiResponseBO.buildValidResponse(apiRequest, response);

		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error("Error fetching product list", ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

	public APIResponse updateProductImage(APIRequest apiRequest) {

		// =====================================================
		// 1. Mandatory validation
		// =====================================================
		ValidationResult validationResult = userValidationBO.updateProductImagevalidateMandatoryField(apiRequest,
				DTOConstants.PRODUCT_ID);

		if (!validationResult.getIsValid()) {
			return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			// =====================================================
			// 2. Deserialize request
			// =====================================================
			ProductDTO requestDTO = (ProductDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming,
					apiRequest.getApiParams(), ProductDTO.class);

			requestDTO.setUpdatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			boolean isDelete = DTOConstants.ACTIVE.equalsIgnoreCase(CommonUtils.safeTrimUpper(requestDTO.getDeleteInd()));
			int rowsAffected;

			// =====================================================
			// 3. HARD DELETE
			// =====================================================
			if (isDelete) {
				rowsAffected = productDAO.deleteProductImage(requestDTO.getProductId());
				if (rowsAffected == 0) {
					return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noProductImageDeleted"));
				}
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.productImageDeleted"));
			}

			// =====================================================
			// 4. UPDATE IMAGE
			// =====================================================
			if (StringUtils.isNotBlank(requestDTO.getImageData())) {
				byte[] imageBytes = Base64.getDecoder().decode(requestDTO.getImageData());
				requestDTO.setImageBytes(imageBytes);
			}
			rowsAffected = productDAO.updateProductImage(requestDTO);
			if (rowsAffected == 0) {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noProductImageUpdated"));
			}
			return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.productImageUpdated"));
		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error("Error updating/deleting product image", ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

}