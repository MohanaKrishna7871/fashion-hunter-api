package com.fashion.hunter.handler;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface APIEndpointResponseHandler<T> {
	T handleAPIRequest(HttpServletRequest var1, Map<String, Object> var2);
}
