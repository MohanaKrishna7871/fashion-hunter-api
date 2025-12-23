package com.fashion.hunter.services;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fashion.hunter.common.dto.APIRequest;
import com.fashion.hunter.utils.GsonUtils;
import com.google.gson.Gson;

@Component
public class APIRequestBO {
	private Gson gsonIncoming = GsonUtils.createIncomingGson();

	public APIRequestBO() {
	}

	public APIRequest convertHttpRequestBodyMapToAPIRequest(Map<String, Object> requestBody) {
		return (APIRequest) GsonUtils.serializeObjectFromMap(this.gsonIncoming, requestBody, APIRequest.class);
	}

	public APIRequest convertJsonRequestToAPIRequest(String requestBody) {
		return (APIRequest) GsonUtils.serializeObjectFromJSON(this.gsonIncoming, requestBody, APIRequest.class);
	}
}