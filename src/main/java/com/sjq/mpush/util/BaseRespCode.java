package com.sjq.mpush.util;

/**
 * @ClassName: BaseRespCode
 */
public enum BaseRespCode implements RespCodeInterface {

    SUCCESS(200,"操作成功"),
    SYSTEM_BUSY(500,"系统繁忙，请稍候再试"),
    USER_OR_PASSWORD_ERROR(10001,"账号或密码错误"),
    LOGIN_FAILE(10002,"用户登陆验证失败"),
    TOKEN_EXPIRE(10003,"token过期失效");




    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误消息
     */
    private final String msg;

    BaseRespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
