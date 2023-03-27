package com.sinfor.scanner.result;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class ResultCode {

    private Integer code;

    private String message;

}
