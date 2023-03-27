package com.sinfor.scanner.exception;

import lombok.Data;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description TODO
 **/
@Data
public class ScanException extends RuntimeException {

    private Integer code;

    public ScanException(String message) {
        super(message);
        this.code = 500;
    }
}
