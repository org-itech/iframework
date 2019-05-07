package org.itech.iframework.domain.query.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * NumberFilter
 *
 * @author liuqiang
 */
public class BooleanFilter extends AbstractFilterItem implements FilterItem {
    public BooleanFilter(String property, Object value, Operator operator) {
        super(property, value, operator, DataType.BOOLEAN);
    }
}