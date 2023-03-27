package com.sinfor.scanner.exception;

import lombok.Data;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description TODO
 **/
@Data
public class CommandNotFoundException extends RuntimeException {

    private Integer code;

    public CommandNotFoundException(String message) {
        super(message);
        this.code = 500;
    }
}
