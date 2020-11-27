package vip.hyzt.common.annotation;

import java.lang.annotation.*;

/**
 * 数据选项过滤注解
 *
 * @author musix
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope
{
    /**
     * 部门表别名
     */
    public String deptAlias() default "";

    /**
     * 用户表别名
     */
    public String userAlias() default "";
}
