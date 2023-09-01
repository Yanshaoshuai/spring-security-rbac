package com.rbac.springsecurity.common;

import org.springframework.http.HttpStatus;

public record Result<T>(int code, String status, String message, T data) {


    public static <T> Result<T> error() {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null);
    }

    public static <T> Result<T> error(HttpStatus status) {
        return new Result<>(status.value(), status.getReasonPhrase(), null, null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message, null);
    }

    public static <T> Result<T> error(HttpStatus status, String message) {
        return new Result<>(status.value(), status.getReasonPhrase(), message, null);
    }
    public static <T> Result<T> ok() {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, data);
    }

    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), message, data);
    }
}
