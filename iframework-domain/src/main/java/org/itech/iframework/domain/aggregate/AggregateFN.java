package org.itech.iframework.domain.aggregate;

import org.itech.iframework.domain.data.IEnum;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

/**
 * AggregateFN
 *
 * @author liuqiang
 */
public enum AggregateFN implements IEnum<AggregateFN> {
    /**
     * 求和
     */
    SUM("求和") {
        @Override
        public Expression toSelection(Expression p, CriteriaBuilder cb, String property) {
            return cb.sum(p);
        }
    },

    /**
     * 计数
     */
    COUNT("计数") {
        @Override
        public Expression toSelection(Expression p, CriteriaBuilder cb, String property) {
            return cb.count(p);
        }
    },

    /**
     * 最小值
     */
    MIN("最小值") {
        @Override
        public Expression toSelection(Expression p, CriteriaBuilder cb, String property) {
            return cb.min(p);
        }
    },

    /**
     * 最大值
     */
    MAX("最大值") {
        @Override
        public Expression toSelection(Expression p, CriteriaBuilder cb, String property) {
            return cb.max(p);
        }
    },

    /**
     * 平均值
     */
    AVG("平均值") {
        @Override
        public Expression toSelection(Expression p, CriteriaBuilder cb, String property) {
            return cb.avg(p);
        }
    },

    /**
     * 计数
     */
    COUNT_DISTINCT("计数") {
        @Override
        public Expression toSelection(Expression p, CriteriaBuilder cb, String property) {
            return cb.countDistinct(p);
        }
    };

    /**
     * 构造方法
     */
    AggregateFN(String name) {
        this.name = name;

    }

    /**
     * 名称
     */
    private final String name;

    /**
     * 获取枚举名称
     *
     * @return 枚举名称
     */
    @Override
    public String getName() {
        return name;
    }

    public abstract Expression toSelection(Expression<?> p, CriteriaBuilder cb, String property);
}
