package com.sinfor.scanner.result;

/**
 * @author fengwen
 * @date 2022/5/9
 * @description TODO
 **/
public class ResultDefaultCode extends ResultCode {

    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "SUCCESS";

    private static final Integer FAIL_CODE = 1009;
    private static final String FAIL_MESSAGE = "FAILED";

    private ResultDefaultCode() {

    }

    private ResultDefaultCode(Integer code, String message) {
        super.setCode(code);
        super.setMessage(message);
    }

    public static ResultDefaultCode SUCCESS() {
        return new ResultDefaultCode(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static ResultDefaultCode FAIL() {
        return new ResultDefaultCode(FAIL_CODE, FAIL_MESSAGE);
    }

}
