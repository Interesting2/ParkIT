package com.G23.ParkIt.util;

import lombok.Data;

@Data
public class Result {
    private static final String CODE_SUCCESS = "200";
    private static final String CODE_AUTH_ERROR = "401";
    private static final String CODE_SYS_ERROR = "500";

    private String code;
    private String message;
    private Object data;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(){
        return new Result(CODE_SUCCESS, "Success", null);
    }
    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, "Success", data);
    }

    public static Result success(String message, Object data) {
        return new Result(CODE_SUCCESS, message, data);
    }

    public static Result authError(String message) {
        return new Result(CODE_AUTH_ERROR, message);
    }

    public static Result sysError(String message) {
        return new Result(CODE_SYS_ERROR, message);
    }
}

