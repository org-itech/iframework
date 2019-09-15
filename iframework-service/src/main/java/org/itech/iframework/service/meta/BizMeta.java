package org.itech.iframework.service.meta;

import java.lang.annotation.*;

/**
 * BizMeta
 *
 * @author liuqiang
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BizMeta {

}
