package com.fashion.hunter.services.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.constants.DTOConstants;
import com.fashion.hunter.dao.SiteConfigDAO;
import com.fashion.hunter.dto.SiteConfigDTO;
import com.fashion.hunter.dto.SiteConfigDTO.SiteConfigRowDTO;
import com.fashion.hunter.services.APIResponseBO;
import com.fashion.hunter.utils.CommonUtils;
import com.fashion.hunter.utils.GsonUtils;
import com.fashion.hunter.validation.ValidationResult;
import com.fashion.hunter.validation.business.UserValidationBO;
import com.google.gson.Gson;

@Service
public class SiteConfigBO extends BaseBO {

	@Autowired
	private SiteConfigDAO siteConfigDAO;

	@Autowired
	private UserValidationBO userValidationBO;

	private final Logger logger = LogManager.getLogger(this.getClass());

	private Gson gsonBOIncoming = GsonUtils.createIncomingGson();

	private APIResponseBO apiResponseBO;

	public SiteConfigBO() {
		this.apiResponseBO = new APIResponseBO();
	}

	public APIResponse getSiteConfigDetails(APIRequest apiRequest) {

		// =====================================================
		// 1. Mandatory validation (shopCodes)
		// =====================================================
		ValidationResult validationResult = userValidationBO.validateMandatoryField(apiRequest,
				DTOConstants.SHOP_CODES);

		if (!validationResult.getIsValid()) {
			return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			SiteConfigDTO requestDTO = (SiteConfigDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming,
					apiRequest.getApiParams(), SiteConfigDTO.class);

			List<String> shopCodes = requestDTO.getShopCodes();
			// =====================================================
			// 3. Fetch DB rows
			// =====================================================
			List<SiteConfigRowDTO> rows = siteConfigDAO.getSiteConfigRows(shopCodes);

			if (CollectionUtils.isEmpty(rows)) {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noConfigFound"));
			}
			// =====================================================
			// 4. Group by shop_code
			// =====================================================
			Map<String, List<SiteConfigDTO.SiteConfigRowDTO>> rowsByShop = rows.stream()
					.collect(Collectors.groupingBy(SiteConfigRowDTO::getShopCode));

			List<SiteConfigDTO> responseList = new ArrayList<>();

			for (Map.Entry<String, List<SiteConfigRowDTO>> entry : rowsByShop.entrySet()) {

				String shopCode = entry.getKey();
				List<SiteConfigRowDTO> shopRows = entry.getValue();

				SiteConfigDTO dto = new SiteConfigDTO();
				dto.setShopCode(shopCode);
				dto.setStatusCode(shopRows.get(0).getStatusCode());

				Map<String, List<SiteConfigDTO.SiteConfigItemDTO>> grouped = shopRows.stream()
						.collect(Collectors.groupingBy(SiteConfigRowDTO::getConfigKey, Collectors.mapping(row -> {
							SiteConfigDTO.SiteConfigItemDTO item = new SiteConfigDTO.SiteConfigItemDTO();
							item.setConfigId(row.getConfigId());
							item.setConfigKey(row.getConfigKey());
							item.setConfigValue(row.getConfigValue());
							item.setStatusCode(row.getStatusCode());
							return item;
						}, Collectors.toList())));

				dto.setAddresses(grouped.getOrDefault(DTOConstants.SITE_ADDRESS, List.of()));
				dto.setPhoneNumbers(grouped.getOrDefault(DTOConstants.SITE_PHONE, List.of()));
				dto.setEmails(grouped.getOrDefault(DTOConstants.SITE_EMAIL, List.of()));
				dto.setWebs(grouped.getOrDefault(DTOConstants.SITE_WEB, List.of()));
				responseList.add(dto);
			}

			return apiResponseBO.buildValidResponse(apiRequest, responseList);
		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error("Error fetching site config details", ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

	public APIResponse updateSiteConfigDetails(APIRequest apiRequest) {

		// =====================================================
		// 1. Mandatory validation
		// =====================================================
		ValidationResult validationResult = userValidationBO.siteConfigValidateMandatoryField(apiRequest, DTOConstants.UPDATE_SITE_CONFIGS_MANDATORY_FIELDS);

		if (!validationResult.getIsValid()) {
			return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			SiteConfigDTO requestDTO = (SiteConfigDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming,
					apiRequest.getApiParams(), SiteConfigDTO.class);

			requestDTO.setUpdatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));

			// =====================================================
			// Execute delete
			// =====================================================
			boolean isDelete = DTOConstants.ACTIVE.equalsIgnoreCase(CommonUtils.safeTrimUpper(requestDTO.getDeleteInd()));
			if (isDelete) {
				int rowsAffected = siteConfigDAO.deleteSiteConfigDetails(requestDTO);

				if (rowsAffected == 0) {
					return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noConfigDeleted"));
				}

				return apiResponseBO.buildValidResponse(apiRequest, resolveMessage("fh.deleteSuccessful", DTOConstants.CONFIG));
			}

			// =====================================================
			// Execute update
			// =====================================================
			int rowsUpdated = siteConfigDAO.updateSiteConfigDetails(requestDTO);

			if (rowsUpdated == 0) {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noConfigUpdated"));
			}

			return apiResponseBO.buildValidResponse(apiRequest,
					resolveMessage("fh.updatedSuccessful", DTOConstants.CONFIG));

		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error("Error updating site config", ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

	public APIResponse addSiteConfig(APIRequest apiRequest) {

		// =====================================================
		// 1. Mandatory validation
		// =====================================================
		ValidationResult validationResult = userValidationBO.siteConfigValidateMandatoryField(apiRequest,
				DTOConstants.ADD_SITE_CONFIGS_MANDATORY_FIELDS);

		if (!validationResult.getIsValid()) {
			return apiResponseBO.buildValidationFailureErrorResponse(apiRequest, validationResult);
		}

		try {
			// =====================================================
			// 2. Deserialize request
			// =====================================================
			SiteConfigDTO requestDTO = (SiteConfigDTO) GsonUtils.serializeObjectFromMap(gsonBOIncoming,
					apiRequest.getApiParams(), SiteConfigDTO.class);

			if (CollectionUtils.isEmpty(requestDTO.getSiteConfigs())) {
				return apiResponseBO.buildValidResponse(apiRequest, validationMessage("fh.noConfigToAdd"));
			}

			requestDTO.setCreatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));
			requestDTO.setUpdatedBy(CommonUtils.safeTrimUpper(apiRequest.getApiContext().getUsername()));

			siteConfigDAO.insertSiteConfig(requestDTO);
			return apiResponseBO.buildValidResponse(apiRequest,
					resolveMessage("fh.addedSuccessful", DTOConstants.SITE_CONFIGS));

		} catch (Exception ex) {
			String errorCode = apiResponseBO.generateResponseErrorCode();
			logger.error("Error adding site config", ex);
			return apiResponseBO.buildInternalSystemErrorResponse(apiRequest, errorCode);
		}
	}

}