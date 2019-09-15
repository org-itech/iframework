package org.itech.iframework.domain.meta;

import org.itech.iframework.domain.data.DataSubType;
import org.itech.iframework.domain.data.DataType;

import java.lang.annotation.*;

/**
 * FieldMeta
 *
 * @author liuqiang
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface FieldMeta {
    /**
     * 名称
     *
     * @return 名称
     */
    String name();

    /**
     * 数据类型
     *
     * @return 数据类型
     */
    DataType type() default DataType.STRING;

    /**
     * 数据子类型
     *
     * @return 数据子类型
     */
    DataSubType subType() default DataSubType.SHORT_STRING;

    /**
     * 是否只读
     *
     * @return 是否只读
     */
    boolean readOnly() default false;
}
