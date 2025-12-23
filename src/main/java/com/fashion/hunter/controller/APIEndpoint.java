package com.fashion.hunter.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface APIEndpoint<T> {
	
	T request(HttpServletRequest var1, Map<String, Object> var2);

}
