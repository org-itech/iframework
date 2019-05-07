package org.itech.iframework.domain.query.filter;

/**
 * AbstractFilter
 *
 * @author liuqiang
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public Filter and(Filter filter) {
        return new ComposedFilter(this, filter, Connector.AND);
    }

    /**
     * or
     *
     * @param filter query
     * @return query
     */
    @Override
    public Filter or(Filter filter) {
        return new ComposedFilter(this, filter, Connector.OR);
    }
}
