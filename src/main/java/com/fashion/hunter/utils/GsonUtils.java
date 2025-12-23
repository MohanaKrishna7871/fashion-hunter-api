package com.fashion.hunter.utils;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.fashion.hunter.common.dto.GsonConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GsonUtils {
	public GsonUtils() {
	}

	public static Gson createIncomingGson() {
		GsonConfig config = new GsonConfig();
		config.setSerializeNulls(Boolean.TRUE);
		return createGson(config);
	}

	public static Gson createOutcomingGson() {
		GsonConfig config = new GsonConfig();
		config.setSerializeNulls(Boolean.FALSE);
		return createGson(config);
	}

	private static Gson createGson(GsonConfig config) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(config.getDateFormat());
		if (config.isDisableHtmlEscaping()) {
			gsonBuilder.disableHtmlEscaping();
		}

		if (config.isExcludeFieldsWithoutExposeAnnotation()) {
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		}

		if (config.isPrettyPrinting()) {
			gsonBuilder.setPrettyPrinting();
		}

		if (config.isSerializeNulls()) {
			gsonBuilder.serializeNulls();
		}

		return gsonBuilder.create();
	}

	public static String deserializeObjectToJSON(Gson gson, Object obj) {
		return gson.toJson(obj);
	}

	public static <T> Object serializeObjectFromJSON(Gson gson, String json, Class<T> classType) {
		return gson.fromJson(json, classType);
	}

	public static <T> Object serializeObjectFromJSON(Gson gson, String json, Type classType) {
		return gson.fromJson(json, classType);
	}

	public static <T> List<T> serializeListOfObjectsFromJSON(Gson gson, String json, Type listType) {
		return (List) gson.fromJson(json, listType);
	}

	public static JsonElement toJsonTree(Gson gson, Object yourMap) {
		return gson.toJsonTree(yourMap);
	}

	public static <T> Object serializeObjectFromMap(Gson gson, Map<String, Object> objectAttributeMap, Type classType) {
		JsonElement jsonElement = gson.toJsonTree(objectAttributeMap);
		return gson.fromJson(jsonElement, classType);
	}

	public static <T> T serializeObjectFromJSON(Gson gson, BufferedReader reader, Class<T> classType) {
		return gson.fromJson(reader, classType);
	}

	public static boolean isJsonValid(String json) {
		try {
			JsonParser.parseString(json);
			return true;
		} catch (JsonSyntaxException var2) {
			return false;
		}
	}
}