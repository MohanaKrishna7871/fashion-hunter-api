package com.fashion.hunter.common.dto;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIRequest {

    @Expose
    @SerializedName("apiContext")
    private APIContext apiContext;

    @Expose
    @SerializedName("apiParams")
    private Map<String, Object> apiParams;

    public APIRequest() {
    }

    private APIRequest(APIRequestBuilder builder) {
        this.apiContext = builder.context;
        this.apiParams = builder.apiParams;
    }

    public APIContext getApiContext() {
        return apiContext;
    }

    public void setApiContext(APIContext apiContext) {
        this.apiContext = apiContext;
    }

    public Map<String, Object> getApiParams() {
        return apiParams;
    }

    public void setApiParams(Map<String, Object> apiParams) {
        this.apiParams = apiParams;
    }

    // ======================================================
    // Builder
    // ======================================================
    public static class APIRequestBuilder {

        private APIContext context = new APIContext();
        private Map<String, Object> apiParams = new HashMap<>();

        public APIRequestBuilder(String transactionId) {
            this.context.setTransactionId(transactionId);
        }

        public APIRequestBuilder setTransactionId(String transactionId) {
            this.context.setTransactionId(transactionId);
            return this;
        }

        public APIRequestBuilder setSource(String source) {
            this.context.setSource(source);
            return this;
        }

        public APIRequestBuilder setApplicationId(String applicationId) {
            this.context.setApplicationId(applicationId);
            return this;
        }

        public APIRequestBuilder setUsername(String username) {
            this.context.setUsername(username);
            return this;
        }

        public APIRequestBuilder addApiParam(String key, Object value) {
            this.apiParams.put(key, value);
            return this;
        }

        public APIRequest build() {
            return new APIRequest(this);
        }
    }
}
