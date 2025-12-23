package com.fashion.hunter.common.dto;

public class GsonConfig {
	private String dateFormat = "MM/dd/yyyy HH:mm:ss";
	private boolean disableHtmlEscaping = true;
	private boolean excludeFieldsWithoutExposeAnnotation = true;
	private boolean prettyPrinting = true;
	private boolean serializeNulls = true;

	public GsonConfig() {
	}

	public String getDateFormat() {
		return this.dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean isDisableHtmlEscaping() {
		return this.disableHtmlEscaping;
	}

	public void setDisableHtmlEscaping(boolean disableHtmlEscaping) {
		this.disableHtmlEscaping = disableHtmlEscaping;
	}

	public boolean isExcludeFieldsWithoutExposeAnnotation() {
		return this.excludeFieldsWithoutExposeAnnotation;
	}

	public void setExcludeFieldsWithoutExposeAnnotation(boolean excludeFieldsWithoutExposeAnnotation) {
		this.excludeFieldsWithoutExposeAnnotation = excludeFieldsWithoutExposeAnnotation;
	}

	public boolean isPrettyPrinting() {
		return this.prettyPrinting;
	}

	public void setPrettyPrinting(boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
	}

	public boolean isSerializeNulls() {
		return this.serializeNulls;
	}

	public void setSerializeNulls(boolean serializeNulls) {
		this.serializeNulls = serializeNulls;
	}
}
