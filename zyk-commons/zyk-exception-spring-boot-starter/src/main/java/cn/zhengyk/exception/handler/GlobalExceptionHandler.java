package cn.zhengyk.exception.handler;

import cn.zhengyk.core.beans.R;
import cn.zhengyk.exception.service.SendExceptionMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {


    @Autowired
    private SendExceptionMailService sendExceptionMailService;

    @Value("${spring.profiles.active:dev}")
    private String activeProfiles;

    @ExceptionHandler(Exception.class)
    public R<?> exception(HttpServletRequest request, Exception ex) {
        String msg = "系统错误，请稍后再试！";
        String exceptionMsg = this.getTrace(ex);

        if (!(ex instanceof HttpRequestMethodNotSupportedException)) {
            sendExceptionMailService.sendExceptionEmail(activeProfiles +"环境发生异常", exceptionMsg);
        }
        log.error(exceptionMsg);
        return R.error(msg);
    }

    public String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}