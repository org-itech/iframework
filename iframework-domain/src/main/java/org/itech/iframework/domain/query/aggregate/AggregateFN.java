package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.data.BitEnum;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

/**
 * AggregateFN
 *
 * @author liuqiang
 */
@SuppressWarnings("unchecked")
public enum AggregateFN implements BitEnum<AggregateFN> {
    /**
     * 求和
     */
    SUM("求和") {
        @Override
        public Expression toExpression(Expression p, CriteriaBuilder cb) {
            return cb.sum(p);
        }
    },

    /**
     * 计数
     */
    COUNT("计数") {
        @Override
        public Expression toExpression(Expression p, CriteriaBuilder cb) {
            return cb.count(p);
        }
    },

    /**
     * 最小值
     */
    MIN("最小值") {
        @Override
        public Expression toExpression(Expression p, CriteriaBuilder cb) {
            return cb.min(p);
        }
    },

    /**
     * 最大值
     */
    MAX("最大值") {
        @Override
        public Expression toExpression(Expression p, CriteriaBuilder cb) {
            return cb.max(p);
        }
    },

    /**
     * 平均值
     */
    AVG("平均值") {
        @Override
        public Expression toExpression(Expression p, CriteriaBuilder cb) {
            return cb.avg(p);
        }
    },

    /**
     * 计数
     */
    COUNT_DISTINCT("计数") {
        @Override
        public Expression toExpression(Expression p, CriteriaBuilder cb) {
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

    public abstract Expression toExpression(Expression<?> p, CriteriaBuilder cb);
}
