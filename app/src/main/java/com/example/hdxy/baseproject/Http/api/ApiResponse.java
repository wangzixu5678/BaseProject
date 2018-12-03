package com.example.hdxy.baseproject.Http.api;


/**
 * 服务器返回的json基类
 */
public class ApiResponse<T>{
    private int code;
    private T response;
    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                ", code=" + code +
                ", response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
