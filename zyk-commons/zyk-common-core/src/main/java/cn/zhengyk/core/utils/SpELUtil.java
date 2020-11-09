package cn.zhengyk.core.utils;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author by yakai.zheng
 * @Description spel 表达式工具类
 */
public class SpELUtil {

    private SpELUtil(){}



    /**
     * 用于SpEL表达式解析.
     */
    private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();


    /**
     * 解析spEL表达式
     */
    public static String getValBySpel(String spelLockKey, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = NAME_DISCOVERER.getParameterNames(methodSignature.getMethod());
        if (paramNames == null || paramNames.length == 0) {
            return null;
        }
        Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(spelLockKey);
        // spring的表达式上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        // 给上下文赋值
        for(int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        Object value = expression.getValue(context);
        if (value != null) {
            return value.toString();
        }else {
            return null;
        }
    }
}
