package com.dsky.baas.gameinvite.util;

import java.util.List;
import java.util.Map;

public class HttpUtilResultBean {
    private Map<String, List<String>> header;
    private String body;
    private Exception exception;

    public Map<String, List<String>> getHeader() {
        return header;
    }

    public void setHeader(Map<String, List<String>> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}