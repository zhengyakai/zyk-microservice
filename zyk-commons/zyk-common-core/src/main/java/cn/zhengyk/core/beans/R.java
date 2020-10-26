package cn.zhengyk.core.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: zlt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    public static final int OK = 200;

    /**
     * 服务异常
     */
    public static final int ERROR = 500;

    /**
     * 服务熔断
     */
    public static final int FALL_BACK = 600;


    private int code;
    private String msg;
    private T data;

    public boolean isOk() {
        return code == OK;
    }

    public boolean isFallback() {
        return code == FALL_BACK;
    }

    public static <T> R<T> ok() {
        return of(OK, null, null);
    }

    public static <T> R<T> of(int code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    public static <T> R<T> ok(T body) {
        return of(OK,null, body);
    }

    public static <T> R<T> error(String message) {
        return of(ERROR, message, null);
    }

    public static <T> R<T> error(int errorCode, String message) {
        return of(errorCode, message, null);
    }

    public static <T> R<T> fallback() {
        return of(FALL_BACK, null, null);
    }


    public static <T> R<T> fallback(T body) {
        return of(FALL_BACK, null, body);
    }
}
