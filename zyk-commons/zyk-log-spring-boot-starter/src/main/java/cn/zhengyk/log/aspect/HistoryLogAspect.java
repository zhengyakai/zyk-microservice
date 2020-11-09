package cn.zhengyk.log.aspect;

import cn.zhengyk.core.utils.SpELUtil;
import cn.zhengyk.log.annotation.HistoryLog;
import cn.zhengyk.log.model.LogHistory;
import cn.zhengyk.log.properties.HistoryLogProperties;
import cn.zhengyk.log.service.LogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * @author yakai.zheng
 */
@Slf4j
@Aspect
@Configuration
@EnableConfigurationProperties(HistoryLogProperties.class)
@ConditionalOnClass({HttpServletRequest.class, RequestContextHolder.class})
@ConditionalOnProperty(prefix = "history-log", name = "enabled", havingValue = "true")
public class HistoryLogAspect {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private HistoryLogProperties historyLogProperties;

    @Autowired
    private LogHistoryService logHistoryService;



    @Before("@within(historyLog) || @annotation(historyLog)")
    public void beforeMethod(JoinPoint joinPoint, HistoryLog historyLog) {
        LogHistory logHistory = this.getLogHistory(historyLog, joinPoint);
        logHistoryService.saveHistoryLog(logHistory);
    }



    /**
     * 构建 操作历史
     */
    private LogHistory getLogHistory(HistoryLog historyLog, JoinPoint joinPoint) {
        LogHistory logHistory = new LogHistory();
        logHistory.setOperateTime(new Date());
        logHistory.setApplicationName(applicationName);

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        logHistory.setClassName(methodSignature.getDeclaringTypeName());
        logHistory.setMethodName(methodSignature.getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 可以从 session 里获取当前登录用户的信息
        HttpSession session = attributes.getRequest().getSession();
        logHistory.setUserId("");
        logHistory.setUserName("");

        String operation = historyLog.operation();
        if (operation.contains("#")) {
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            operation = SpELUtil.getValBySpel(operation, methodSignature, args);
        }
        logHistory.setOperation(operation);

        return logHistory;
    }
}
