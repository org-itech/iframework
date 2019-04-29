package org.itech.iframework.domain.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * 筛选项接口
 *
 * @author liuqiang
 */
public interface FilterItem extends Filter {
    /**
     * 获取数据操作符
     *
     * @return 数据操作符
     */
    Operator getOperator();

    /**
     * 获取数据类型
     *
     * @return 数据类型
     */
    DataType getType();

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    String getProperty();

    /**
     * 获取值
     *
     * @return 值
     */
    Object getValue();
}
