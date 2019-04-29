package org.itech.iframework.domain.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * StringFilter
 *
 * @author liuqiang
 */
public class StringFilter extends AbstractFilterItem implements FilterItem {
    public StringFilter(String property, Object value, Operator operator) {
        super(property, value, operator, DataType.STRING);
    }
}