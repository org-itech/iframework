package org.itech.iframework.service.meta;

import java.lang.annotation.*;

/**
 * ActionMeta
 *
 * @author liuqiang
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActionMeta {
}
