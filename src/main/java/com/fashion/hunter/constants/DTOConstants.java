package com.fashion.hunter.constants;

import java.util.Arrays;
import java.util.List;

public class DTOConstants {

	// User Details
	public static final String USER = "user";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String STATUS_CODE = "statusCode";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PHONE_NUMBER = "phoneNumber";

	// Role Details
	public static final String ROLE_CODES = "roleCodes";
	public static final String ROLE_ID = "roleId";
	public static final String ROLE_CODE = "roleCode";

	public static final String YES = "Yes";
	public static final String NO = "No";

	// Status
	public static final String ACTIVE = "Y";
	public static final String INACTIVE = "N";

	// Site Config
	public static final String SHOP_CODE = "shopCode";
	public static final String SHOP_CODES = "shopCodes";
	public static final String SITE_ADDRESS = "SITE_ADDRESS";
	public static final String SITE_PHONE = "SITE_PHONE";
	public static final String SITE_EMAIL = "SITE_EMAIL";
	public static final String SITE_WEB = "SITE_WEB";
	public static final String CONFIG_ID = "configId";
	public static final String CONFIG = "config";
	public static final String SITE_CONFIGS = "siteConfigs";
	public static final String CONFIG_KEY = "configKey";
	public static final String DELETE_IND = "deleteInd";

	// Product
	public static final String PRODUCT_NAME = "productName";
	public static final String IMAGE_DATA = "imageData";
	public static final String SORT_ORDER = "sortOrder";
	public static final String DESCRIPTION = "description";
	public static final String PRODUCT_IMAGE = "productImage";
	public static final String PRODUCT_ID = "productId";

	// Mandatory Fields list
	public static final List<String> ADD_USER_MANDATORY_FIELDS = Arrays.asList(USER_NAME, PASSWORD, EMAIL, FIRST_NAME,
			LAST_NAME, PHONE_NUMBER);
	public static final List<String> USER_LOGIN_MANDATORY_FIELDS = Arrays.asList(USER_NAME, PASSWORD);

	public static final List<String> ADD_SITE_CONFIGS_MANDATORY_FIELDS = Arrays.asList(SHOP_CODE, SITE_CONFIGS);

	public static final List<String> UPDATE_SITE_CONFIGS_MANDATORY_FIELDS = Arrays.asList(CONFIG_ID, CONFIG_KEY,
			DELETE_IND);

	public static final List<String> ADD_PRODUCT_MANDATORY_FIELDS = Arrays.asList(PRODUCT_NAME, IMAGE_DATA,
			SORT_ORDER, DESCRIPTION);

}
