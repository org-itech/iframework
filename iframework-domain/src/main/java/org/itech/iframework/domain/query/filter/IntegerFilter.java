package org.itech.iframework.domain.query.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * IntegerFilter
 *
 * @author liuqiang
 */
public class IntegerFilter extends AbstractFilterItem implements FilterItem {
    public IntegerFilter(String property, Object value, Operator operator) {
        super(property, value, operator, DataType.INTEGER);
    }
}