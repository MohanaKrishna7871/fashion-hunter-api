package com.fashion.hunter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashion.hunter.common.dto.APIResponse;
import com.fashion.hunter.config.APIEndpointContexts;
import com.fashion.hunter.config.APIVersions;
import com.fashion.hunter.handler.FashionHunterAPIEndpointResponseHandler;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(APIVersions.VERSION_1 + APIEndpointContexts.REQUESTS_API_CONTEXT)
public class FashionHunterAPIEndpoint implements APIEndpoint<ResponseEntity<APIResponse>>{
	@Autowired
	private FashionHunterAPIEndpointResponseHandler responseHandler;

	/**
	 * The single end-point
	 */
	@Override
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> request(HttpServletRequest httpRequest, @RequestBody Map<String, Object> requestBody) {
		APIResponse apiResponse = responseHandler.handleAPIRequest(httpRequest, requestBody);
		return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
	}
}