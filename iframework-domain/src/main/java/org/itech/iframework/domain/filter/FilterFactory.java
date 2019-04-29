package org.itech.iframework.domain.filter;

import org.itech.iframework.domain.data.Operator;

/**
 * FilterFactory
 *
 * @author liuqiang
 */
public class FilterFactory {
    public static Filter stringFilter(String property, Object value, Operator operator) {
        return new StringFilter(property, value, operator);
    }

    public static Filter booleanFilter(String property, Object value, Operator operator) {
        return new BooleanFilter(property, value, operator);
    }

    public static Filter numberFilter(String property, Object value, Operator operator) {
        return new NumberFilter(property, value, operator);
    }

    public static Filter dateFilter(String property, Object value, Operator operator) {
        return new DateFilter(property, value, operator);
    }

    public static Filter jsonFilter(String property, Object value, Operator operator) {
        return new JsonFilter(property, value, operator);
    }
}
