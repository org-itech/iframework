package org.itech.iframework.domain.projection;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Projection
 *
 * @author liuqiang
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target({ElementType.FIELD})
public @interface Projection {
    /**
     * 投影路径
     *
     * @return 路径
     */
    @AliasFor("path")
    String value() default "";

    /**
     * 投影路径
     *
     * @return 路径
     */
    @AliasFor("value")
    String path() default "";

    /**
     * 是否忽略
     *
     * @return 是否忽略
     */
    boolean ignore() default false;
}
