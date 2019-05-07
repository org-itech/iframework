package org.itech.iframework.domain.filter;

import org.itech.iframework.domain.DomainException;
import org.itech.iframework.domain.constant.DomainConstants;
import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
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
        Assert.notNull(operator, "操作符 operator 不能为空！");

        Assert.hasText(property, "属性 property 不能为空！");

        Assert.isTrue(dataType.isSupportedOperator(operator), "数据类型 " + dataType.getName() + " 不支持 " + operator.getName() + " 操作符！");

        if (operator == Operator.BTW || operator == Operator.IN || operator == Operator.NOT_IN) {
            if (getValue() == null) {
                throw new DomainException("筛选器值错误，操作符 " + operator.getCode() + " 的值不能为空！");
            }

            Assert.notNull(value, "操作符是 " + operator.getName() + " 时，值不能为空！");

            Assert.isTrue(List.class.isAssignableFrom(getValue().getClass()), "操作符是 " + operator.getName() + " 时，值应该为数组！");

            if (operator == Operator.BTW) {
                Assert.isTrue(((List) getValue()).size() == DomainConstants.TWO, "操作符是 " + operator.getName() + " 时，值应包含两个元素！");
            }
        }

        Assert.isTrue(isValueValid(value), "值 value 无法转换成 " + dataType.getName() + " 类型！");
    }

    private boolean isValueValid(Object value) {
        boolean result = true;

        if (value != null) {
            if (List.class.isAssignableFrom(value.getClass())) {
                for (Object item : (List) value) {
                    if (!isValueValid(item)) {
                        result = false;
                        break;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Expression expression = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));
        return operator.toPredicate(root, query, cb, expression, property, value);
    }
}
