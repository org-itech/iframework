package org.itech.iframework.domain.query.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * NumberFilter
 *
 * @author liuqiang
 */
public class NumberFilter extends AbstractFilterItem implements FilterItem {
    public NumberFilter(String property, Object value, Operator operator) {
        super(property, value, operator, DataType.NUMBER);
    }
}