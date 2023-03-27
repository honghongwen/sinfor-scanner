package com.sinfor.scanner.result;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result() {

    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultDefaultCode.SUCCESS(), null);
    }

    public static <T> Result<T> ok(T t) {
        return new Result<>(ResultDefaultCode.SUCCESS(), t);
    }

    public static <T> Result<T> ok(ResultCode result, T t) {
        return new Result<>(result, t);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultDefaultCode.FAIL(), null);
    }

    public static <T> Result<T> fail(T t) {
        return new Result<>(ResultDefaultCode.FAIL(), t);
    }

    public static <T> Result<T> fail(ResultCode result, T t) {
        return new Result<>(result, t);
    }

}
