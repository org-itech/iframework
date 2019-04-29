package org.itech.iframework.domain.filter;

import org.itech.iframework.domain.DomainException;
import org.itech.iframework.domain.constant.DomainConstants;
import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 筛选项抽象基类
 *
 * @author liuqiang
 */
public abstract class AbstractFilterItem extends AbstractFilter implements FilterItem {
    /**
     * 数据操作符
     */
    private final Operator operator;

    /**
     * 数据类型
     */
    private final DataType dataType;

    /**
     * 属性名称
     */
    private final String property;

    /**
     * 值
     */
    private final Object value;

    public AbstractFilterItem(String property, Object value, Operator operator, DataType dataType) {

        this.property = property;
        this.value = value;
        this.operator = operator;
        this.dataType = dataType;

        afterPropertySet();
    }

    /**
     * 获取数据操作符
     *
     * @return 数据操作符
     */
    @Override
    public Operator getOperator() {
        return operator;
    }

    /**
     * 获取数据类型
     *
     * @return 数据类型
     */
    @Override
    public DataType getType() {
        return dataType;
    }

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    @Override
    public String getProperty() {
        return property;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    @Override
    public Object getValue() {
        return value;
    }

    private void afterPropertySet() {
        if (!dataType.isSupportedOperator(operator)) {
            throw new DomainException("筛选器的操作符错误，不支持的操作符, " + this.toString());
        }

        if (operator == Operator.BTW || operator == Operator.IN || operator == Operator.NOT_IN) {
            if (getValue() == null) {
                throw new DomainException("筛选器值错误，操作符 " + operator.getCode() + " 的值不能为空！");
            }

            if (!List.class.isAssignableFrom(getValue().getClass())) {
                throw new DomainException("筛选器值错误，操作符 " + operator.getCode() + " 的值应该为数据！");
            }

            if (operator == Operator.BTW) {
                if (((List) getValue()).size() != DomainConstants.TWO) {
                    throw new DomainException("筛选器值错误，操作符 " + operator.getCode() + " 的值应该包含两个元素！");
                }
            }
        }

        if (!isValueValid(value)) {
            throw new DomainException("筛选器的值类型错误, " + this.toString() + "！");
        }
    }

    private boolean isValueValid(Object value) {
        boolean result = true;

        if (value == null) {
            result = true;
        }

        if (List.class.isAssignableFrom(value.getClass())) {
            for (Object item : (List) value) {
                if (!isValueValid(item)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return operator.toPredicate(root, query, cb, property, value);
    }
}
