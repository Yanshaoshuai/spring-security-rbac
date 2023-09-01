package com.rbac.springsecurity.common;


import org.springframework.http.HttpStatus;

import java.util.List;


public record PageResult<T>(long total,int pageNumber, int pageSize,int code, String status, String message, List<T> data) {


    public static <T> PageResult<T> error() {
        return new PageResult<>(0L,0,0,HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null);
    }

    public static <T> PageResult<T> error(HttpStatus status) {
        return new PageResult<>(0L,0,0,status.value(), status.getReasonPhrase(), null, null);
    }

    public static <T> PageResult<T> error(String message) {
        return new PageResult<>(0L,0,0,HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message, null);
    }

    public static <T> PageResult<T> error(HttpStatus status, String message) {
        return new PageResult<>(0L,0,0,status.value(), status.getReasonPhrase(), message, null);
    }

    public static <T> PageResult<T> ok(long total,int pageNumber,int pageSize,List<T> data) {
        return new PageResult<>(total,pageNumber,pageSize,HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, data);
    }

    public static <T> PageResult<T> ok(long total,int pageNumber,int pageSize,List<T> data, String message) {
        return new PageResult<>(total,pageNumber,pageSize,HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), message, data);
    }
}
