package com.sr.util;

import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/9/30 16:19
 */
public class ReturnCodeBuilder {
    //状态码
    private int statusCode;
    //状态信息
    private String message;
    //传输的数据
    private Object data;
    //请求URL
    private String url;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String,Object> buildMap () {
        Map<String,Object> statusMap = new HashMap<>();
        statusMap.put("statusCode", statusCode);
        statusMap.put("message", message);
        statusMap.put("timestamp", new Date().getTime());
        statusMap.put("data", data);
        statusMap.put("url", url);
        return statusMap;
    }

    public static Builder successBuilder () {
        return new Builder().code(200).message(StatusEnum.SUCCESS.getDescription());
    }

    public static Builder failedBuilder () {
        return new Builder().code(700).message(StatusEnum.FAIL.getDescription());
    }

    public static Builder failedBuilder (int code) {
        StatusEnum statusEnum = StatusEnum.getByCode(code);
        if (statusEnum == null) {
            return failedBuilder();
        }
        return new Builder().code(code).message(statusEnum.getDescription());
    }

    public static Builder failedBuilder (String errorName) {
        StatusEnum statusEnum = StatusEnum.valueOf(errorName);
        return new Builder().code(statusEnum.getCode()).message(statusEnum.getDescription());
    }

    public static Builder failedBuilder (StatusException statusException) {
        return new Builder().code(statusException.getCode()).message(statusException.getMessage());
    }

    public static class Builder{
        //状态码
        private int statusCode;
        //状态信息
        private String message;
        //传输的数据
        private Object data;
        //请求url
        private String url;

        public Builder code (int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder message (String message) {
            this.message = message;
            return this;
        }

        public Builder addDataValue (Map<String,Object> data) {
            this.data = data;
            return this;
        }

        public Builder url (String url) {
            this.url = url;
            return this;
        }

        public ReturnCodeBuilder build () {
            ReturnCodeBuilder returnCodeBuilder = new ReturnCodeBuilder();
            returnCodeBuilder.setStatusCode(this.statusCode);
            returnCodeBuilder.setMessage(this.message);
            returnCodeBuilder.setData(this.data);
            returnCodeBuilder.setUrl(url);
            return returnCodeBuilder;
        }
    }
}
