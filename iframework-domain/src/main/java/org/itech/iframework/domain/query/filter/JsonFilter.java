package org.itech.iframework.domain.query.filter;

import org.itech.iframework.domain.data.DataType;
import org.itech.iframework.domain.data.Operator;

/**
 * JsonFilter
 *
 * @author liuqiang
 */
public class JsonFilter extends AbstractFilterItem implements FilterItem {
    public JsonFilter(String property, Object value, Operator operator) {
        super(property, value, operator, DataType.JSON);
    }
}