package com.fashion.hunter.dto;

import java.util.List;

import com.google.gson.annotations.Expose;

public class SiteConfigDTO extends BaseDomain {

	@Expose
	private String shopCode;

	@Expose
	private List<String> shopCodes;

	@Expose
	private List<SiteConfigItemDTO> addresses;

	@Expose
	private List<SiteConfigItemDTO> phoneNumbers;

	@Expose
	private List<SiteConfigItemDTO> emails;

	@Expose
	private List<SiteConfigItemDTO> webs;

	@Expose
	private String statusCode;

	@Expose
	private List<SiteConfigItemDTO> siteConfigs;

	@Expose
	private String deleteInd;

	@Expose
	private String configId;

	@Expose
	private String configKey;

	@Expose
	private String configValue;

	/* ================= Getters & Setters ================= */

	public List<SiteConfigItemDTO> getSiteConfigs() {
		return siteConfigs;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getDeleteInd() {
		return deleteInd;
	}

	public void setDeleteInd(String deleteInd) {
		this.deleteInd = deleteInd;
	}

	public void setSiteConfigs(List<SiteConfigItemDTO> siteConfigs) {
		this.siteConfigs = siteConfigs;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public List<SiteConfigItemDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<SiteConfigItemDTO> addresses) {
		this.addresses = addresses;
	}

	public List<SiteConfigItemDTO> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<SiteConfigItemDTO> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<SiteConfigItemDTO> getEmails() {
		return emails;
	}

	public void setEmails(List<SiteConfigItemDTO> emails) {
		this.emails = emails;
	}

	public List<SiteConfigItemDTO> getWebs() {
		return webs;
	}

	public void setWebs(List<SiteConfigItemDTO> webs) {
		this.webs = webs;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public List<String> getShopCodes() {
		return shopCodes;
	}

	public void setShopCodes(List<String> shopCodes) {
		this.shopCodes = shopCodes;
	}

	/*
	 * ===================================================== ITEM DTO (Used for
	 * update) =====================================================
	 */
	public static class SiteConfigItemDTO {

		@Expose
		private String configId;

		@Expose
		private String configKey;

		@Expose
		private String configValue;

		@Expose
		private String statusCode;

		public String getConfigId() {
			return configId;
		}

		public void setConfigId(String configId) {
			this.configId = configId;
		}

		public String getConfigKey() {
			return configKey;
		}

		public void setConfigKey(String configKey) {
			this.configKey = configKey;
		}

		public String getConfigValue() {
			return configValue;
		}

		public void setConfigValue(String configValue) {
			this.configValue = configValue;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
	}

	/*
	 * ===================================================== ROW DTO (Mapper-level)
	 * =====================================================
	 */
	public static class SiteConfigRowDTO {

		private String configId;
		private String shopCode;
		private String configKey;
		private String configValue;
		private String statusCode;

		public String getConfigId() {
			return configId;
		}

		public void setConfigId(String configId) {
			this.configId = configId;
		}

		public String getShopCode() {
			return shopCode;
		}

		public void setShopCode(String shopCode) {
			this.shopCode = shopCode;
		}

		public String getConfigKey() {
			return configKey;
		}

		public void setConfigKey(String configKey) {
			this.configKey = configKey;
		}

		public String getConfigValue() {
			return configValue;
		}

		public void setConfigValue(String configValue) {
			this.configValue = configValue;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
	}
}
