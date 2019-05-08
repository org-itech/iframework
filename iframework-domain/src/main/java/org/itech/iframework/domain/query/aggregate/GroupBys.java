package org.itech.iframework.domain.query.aggregate;

import org.itech.iframework.domain.query.Selection;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.mapping.PropertyPath;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GroupBys
 *
 * @author liuqiang
 */
public class GroupBys implements Iterable<GroupBys.GroupBy> {
    private List<GroupBy> groupBys;

    private GroupBys(List<GroupBy> groupBys) {
        this.groupBys = groupBys;
    }

    @Override
    public Iterator<GroupBy> iterator() {
        return groupBys.iterator();
    }

    public List<javax.persistence.criteria.Selection> toJpaSelection(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        return groupBys.stream()
                .map(item -> item.toJpaSelection(root, query, cb))
                .collect(Collectors.toList());
    }

    public static GroupBysBuilder builder() {
        return new GroupBysBuilder();
    }

    public static class GroupBysBuilder {
        private List<GroupBys.GroupBy> groupBys;

        GroupBysBuilder() {
            this.groupBys = new ArrayList<>();
        }

        public GroupBysBuilder add(String property, String alias) {
            this.groupBys.add(GroupBys.GroupBy.by(property, alias));

            return this;
        }

        public GroupBysBuilder add(String property) {
            this.groupBys.add(GroupBy.by(property, property));

            return this;
        }

        public GroupBys build() {
            return new GroupBys(this.groupBys);
        }
    }

    /**
     * GroupBy
     *
     * @author liuqiang
     */
    public static class GroupBy implements Selection {
        /**
         * 属性
         */
        private String property;

        /**
         * 别名
         */
        private String alias;

        private GroupBy(String property, String alias) {
            this.property = property;
            this.alias = alias;
        }

        public static GroupBy by(String property) {
            return new GroupBy(property, property);
        }

        public static GroupBy by(String property, String alias) {
            return new GroupBy(property, alias);
        }

        @Override
        public String getProperty() {
            return property;
        }

        @Override
        public String getAlias() {
            return alias;
        }

        @Override
        public javax.persistence.criteria.Selection toJpaSelection(Root root, CriteriaQuery query, CriteriaBuilder cb) {
            return QueryUtils.toExpressionRecursively(root, PropertyPath.from(property, root.getJavaType()));
        }
    }
}
