package org.itech.iframework.domain.data;

import org.itech.iframework.domain.util.PredicateUtils;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;

import javax.persistence.criteria.*;
import java.util.List;

import static org.itech.iframework.domain.constant.DomainConstants.BIT_AND_FUNCTION_NAME;
import static org.itech.iframework.domain.constant.DomainConstants.MATCH_FUNCTION_NAME;

/**
 * 数据操作符
 *
 * @author liuqiang
 */
public enum Operator implements IEnum<Operator> {
    /**
     * 等于
     */
    EQ("等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            if (value == null) {
                return path.isNull();
            } else {
                return cb.equal(path, value);
            }
        }
    },

    /**
     * 不等于
     */
    NEQ("不等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            if (value == null) {
                return path.isNotNull();
            } else {
                return cb.equal(path, value);
            }
        }
    },

    /**
     * 大于
     */
    GT("大于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.greaterThan(path, (Comparable) value);
        }
    },

    /**
     * 大于等于
     */
    GTE("大于等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.greaterThanOrEqualTo(path, (Comparable) value);
        }
    },

    /**
     * 小于
     */
    LT("小于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.lessThan(path, (Comparable) value);
        }
    },

    /**
     * 小于等于
     */
    LTE("小于等于") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.lessThanOrEqualTo(path, (Comparable) value);
        }
    },

    /**
     * 范围
     */
    BTW("范围") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            Predicate predicate = null;
            List<Comparable> list = (List<Comparable>) value;

            Object v1 = list.get(0);
            Object v2 = list.get(1);

            if (v1 != null) {
                predicate = cb.greaterThanOrEqualTo(path, (Comparable) v1);
            }

            if (v2 != null) {
                if (predicate == null) {
                    predicate = cb.lessThanOrEqualTo(path, (Comparable) v2);
                } else {
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(path, (Comparable) v2));
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
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.like(path, "%" + value + "%");
        }
    },

    /**
     * 以...开头
     */
    SW("以...开头") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.like(path, "%" + value);
        }
    },

    /**
     * 以...结尾
     */
    EW("以...结尾") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return cb.like(path, value + "%");
        }
    },

    /**
     * 全文检索
     */
    MT("全文检索") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            Expression<Double> expression = cb.function(MATCH_FUNCTION_NAME, Double.class, path, cb.literal(value));

            return cb.greaterThan(expression, .0);
        }
    },

    /**
     * 在...之内
     */
    IN("在...之内") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return path.in(value);
        }
    },

    /**
     * 不在...之内
     */
    NOT_IN("不在...之内") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            return path.in(value).not();
        }
    },

    /**
     * 位值，在...之内
     */
    BIT_IN("位值，在...之内") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            Expression expression = cb.function(BIT_AND_FUNCTION_NAME, Long.class, path, cb.literal(value));

            return cb.equal(expression, path);
        }
    },

    /**
     * 位值，包含
     */
    BIT_CT("位值，包含") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            Expression path = QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));

            Expression expression = cb.function(BIT_AND_FUNCTION_NAME, Long.class, path, cb.literal(value));

            return cb.equal(expression, value);
        }
    },

    /**
     * 子代
     */
    DES("子代") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            return PredicateUtils.des(root, query, cb, value, property);
        }
    },

    /**
     * 子代
     */
    DES_AS("子代及本身") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            return PredicateUtils.desAs(root, query, cb, value, property);
        }
    },

    /**
     * 父代
     */
    ANC("父代") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            return PredicateUtils.anc(root, query, cb, value, property);
        }
    },

    /**
     * 父代
     */
    ANC_AS("父代及本身") {
        @Override
        public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value) {
            return PredicateUtils.ancAs(root, query, cb, value, property);
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

    public abstract Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String property, Object value);
}
