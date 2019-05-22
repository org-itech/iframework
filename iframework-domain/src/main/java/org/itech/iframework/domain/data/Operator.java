package org.itech.iframework.domain.data;

import org.itech.iframework.domain.util.PredicateUtils;

import javax.persistence.criteria.*;
import java.util.List;

import static org.itech.iframework.domain.constant.DomainConstants.BIT_AND_FUNCTION_NAME;
import static org.itech.iframework.domain.constant.DomainConstants.MATCH_FUNCTION_NAME;

/**
 * 数据操作符
 *
 * @author liuqiang
 */
@SuppressWarnings("unchecked")
public enum Operator implements BitEnum<Operator> {
    /**
     * 等于
     */
    EQ("等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            if (value == null) {
                OptionSet set = new OptionSet();

                return expression.isNull();
            } else {
                return cb.equal(expression, value);
            }
        }
    },

    /**
     * 不等于
     */
    NEQ("不等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            if (value == null) {
                return expression.isNotNull();
            } else {
                return cb.equal(expression, value);
            }
        }
    },

    /**
     * 大于
     */
    GT("大于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.greaterThan(expression, (Comparable) value);
        }
    },

    /**
     * 大于等于
     */
    GTE("大于等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.greaterThanOrEqualTo(expression, (Comparable) value);
        }
    },

    /**
     * 小于
     */
    LT("小于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.lessThan(expression, (Comparable) value);
        }
    },

    /**
     * 小于等于
     */
    LTE("小于等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.lessThanOrEqualTo(expression, (Comparable) value);
        }
    },

    /**
     * 范围
     */
    BTW("范围") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            Predicate predicate = null;
            List<Comparable> list = (List<Comparable>) value;

            Object v1 = list.get(0);
            Object v2 = list.get(1);

            if (v1 != null) {
                predicate = cb.greaterThanOrEqualTo(expression, (Comparable) v1);
            }

            if (v2 != null) {
                if (predicate == null) {
                    predicate = cb.lessThanOrEqualTo(expression, (Comparable) v2);
                } else {
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(expression, (Comparable) v2));
                }
            }

            return predicate;
        }
    },

    /**
     * 包含
     */
    CT("包含") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.like(expression, "%" + value + "%");
        }
    },

    /**
     * 以...开头
     */
    SW("以...开头") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.like(expression, "%" + value);
        }
    },

    /**
     * 以...结尾
     */
    EW("以...结尾") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return cb.like(expression, value + "%");
        }
    },

    /**
     * 全文检索
     */
    MT("全文检索") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            Expression<Double> ex = cb.function(MATCH_FUNCTION_NAME, Double.class, expression, cb.literal(value));

            return cb.greaterThan(ex, .0);
        }
    },

    /**
     * 在...之内
     */
    IN("在...之内") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return expression.in(value);
        }
    },

    /**
     * 不在...之内
     */
    NOT_IN("不在...之内") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return expression.in(value).not();
        }
    },

    /**
     * 位值，在...之内
     */
    BIT_IN("位值，在...之内") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            Expression ex = cb.function(BIT_AND_FUNCTION_NAME, Long.class, expression, cb.literal(value));

            return cb.equal(expression, ex);
        }
    },

    /**
     * 位值，包含
     */
    BIT_CT("位值，包含") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            Expression ex = cb.function(BIT_AND_FUNCTION_NAME, Long.class, expression, cb.literal(value));

            return cb.equal(expression, value);
        }
    },

    /**
     * 子代
     */
    DES("子代") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return PredicateUtils.des(root, query, cb, expression, property, value);
        }
    },

    /**
     * 子代
     */
    DES_AS("子代及本身") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return PredicateUtils.desAs(root, query, cb, expression, property, value);
        }
    },

    /**
     * 父代
     */
    ANC("父代") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return PredicateUtils.anc(root, query, cb, expression, property, value);
        }
    },

    /**
     * 父代
     */
    ANC_AS("父代及本身") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
            return PredicateUtils.ancAs(root, query, cb, expression, property, value);
        }
    };

    /**
     * 构造方法
     */
    Operator(String name) {
        this.name = name;
    }

    /**
     * 名称
     */
    private final String name;

    @Override
    public String getName() {
        return this.name;
    }

    public abstract Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value);
}
