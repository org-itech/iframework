package org.itech.iframework.domain.query.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * DateFilter
 *
 * @author liuqiang
 */
public class DateFilter extends AbstractFilterItem implements FilterItem {
    public DateFilter(String property, Object value, Operator operator) {
        super(property, value, operator, DataType.DATE);
    }
}