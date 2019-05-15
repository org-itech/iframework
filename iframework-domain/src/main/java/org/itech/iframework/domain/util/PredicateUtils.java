package org.itech.iframework.domain.util;

import org.itech.iframework.domain.model.AbstractTreeAggregateRoot_;
import org.itech.iframework.domain.model.AbstractTreePath_;
import org.itech.iframework.domain.model.TreeNode;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;

import static org.itech.iframework.domain.constant.DomainConstants.PROPERTY_DELIMITER;
import static org.itech.iframework.domain.constant.DomainConstants.ROOT_ID;

/**
 * PredicateUtils
 *
 * @author liuqiang
 */
public final class PredicateUtils {
    public static Predicate des(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
        return getDesPredicate(root, query, cb, expression, property, value, null, false);
    }

    public static Predicate desAs(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
        return getDesPredicate(root, query, cb, expression, property, value, null, true);
    }

    public static Predicate anc(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
        return getAncPredicate(root, query, cb, expression, property, value, null, false);
    }

    public static Predicate ancAs(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value) {
        return getAncPredicate(root, query, cb, expression, property, value, null, true);
    }

    public static Predicate des(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value, Long depth) {
        return getDesPredicate(root, query, cb, expression, property, value, depth, false);
    }

    public static Predicate desAs(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value, Long depth) {
        return getDesPredicate(root, query, cb, expression, property, value, depth, true);
    }

    public static Predicate anc(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value, Long depth) {
        return getAncPredicate(root, query, cb, expression, property, value, depth, false);
    }

    public static Predicate ancAs(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value, Long depth) {
        return getAncPredicate(root, query, cb, expression, property, value, depth, true);
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    static Predicate getDesPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value, Long depth, boolean containSelf) {
        Class treeClazz = ((Path) expression).getParentPath().getJavaType();

        Assert.isAssignable(TreeNode.class, treeClazz, "非树实体无法使用子代操作符！");

        Join ancestorJoin = null;

        String[] props = property.split(PROPERTY_DELIMITER);

        if (props.length == 1) {
            ancestorJoin = QueryUtils.getOrCreateJoin(root, AbstractTreeAggregateRoot_.ANCESTORS);
        } else {
            for (int i = 0; i < props.length - 1; i++) {
                if (ancestorJoin == null) {
                    ancestorJoin = QueryUtils.getOrCreateJoin(root, props[i]);
                } else {
                    ancestorJoin = QueryUtils.getOrCreateJoin(ancestorJoin, props[i]);
                }
            }

            ancestorJoin = QueryUtils.getOrCreateJoin(ancestorJoin, AbstractTreeAggregateRoot_.ANCESTORS);
        }

        Predicate predicate;

        if (ROOT_ID.equals(value) || value == null) {
            Class treePath = ResolvableType.forType(treeClazz.getGenericSuperclass()).getGeneric(0).resolve();
            Subquery subquery = query.subquery(treePath);
            Root path = subquery.from(treePath);

            subquery.select(path.get(AbstractTreePath_.ANCESTOR).get(AbstractTreeAggregateRoot_.ID));

            Predicate subQueryPredicate = cb.and(cb.equal(path.get(AbstractTreePath_.DESCENDANT).get(AbstractTreeAggregateRoot_.ID)
                    , ancestorJoin.get(AbstractTreePath_.ANCESTOR).get(AbstractTreeAggregateRoot_.ID))
                    , cb.notEqual(path.get(AbstractTreePath_.DESCENDANT).get(AbstractTreeAggregateRoot_.ID)
                            , path.get(AbstractTreePath_.ANCESTOR).get(AbstractTreeAggregateRoot_.ID)));

            if (depth != null) {
                subQueryPredicate = cb.and(subQueryPredicate, cb.lessThanOrEqualTo(ancestorJoin.get(AbstractTreePath_.DEPTH), depth));
            }

            subquery.where(subQueryPredicate);

            predicate = cb.exists(subquery).not();
        } else {
            predicate = cb.equal(ancestorJoin.get(AbstractTreePath_.ANCESTOR).get(AbstractTreeAggregateRoot_.ID), value);

            if (!containSelf) {
                predicate = cb.and(predicate, cb.greaterThan(ancestorJoin.get(AbstractTreePath_.DEPTH), 0L));
            }

            if (depth != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(ancestorJoin.get(AbstractTreePath_.DEPTH), depth));
            }
        }

        return predicate;
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    static Predicate getAncPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Expression expression, String property, Object value, Long depth, boolean containSelf) {
        Class treeClazz = ((Path) expression).getParentPath().getJavaType();

        Assert.isAssignable(TreeNode.class, treeClazz, "非树实体无法使用父代操作符！");

        Join descendantJoin = null;
        String[] props = property.split(PROPERTY_DELIMITER);

        if (props.length == 1) {
            descendantJoin = QueryUtils.getOrCreateJoin(root, AbstractTreeAggregateRoot_.DESCENDANTS);
        } else {
            for (int i = 0; i < props.length - 1; i++) {
                if (descendantJoin == null) {
                    descendantJoin = QueryUtils.getOrCreateJoin(root, props[i]);
                } else {
                    descendantJoin = QueryUtils.getOrCreateJoin(descendantJoin, props[i]);
                }
            }

            descendantJoin = QueryUtils.getOrCreateJoin(descendantJoin, AbstractTreeAggregateRoot_.DESCENDANTS);
        }

        Predicate predicate = cb.equal(descendantJoin.get(AbstractTreePath_.DESCENDANT).get(AbstractTreeAggregateRoot_.ID), value);

        if (!containSelf) {
            predicate = cb.and(predicate, cb.greaterThan(descendantJoin.get(AbstractTreePath_.DEPTH), 0L));
        }

        if (depth != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(descendantJoin.get(AbstractTreePath_.DEPTH), depth));
        }

        return predicate;
    }
}
