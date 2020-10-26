package cn.zhengyk.exception.bean;

/**
 * @author yakai.zheng
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}