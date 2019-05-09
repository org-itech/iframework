package org.itech.iframework.domain.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Selections
 *
 * @author liuqiang
 */
public class Selections implements Iterable<Selection> {
    private List<Selection> selections;

    private Selections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public Iterator<Selection> iterator() {
        return selections.iterator();
    }

    public static SelectionsBuilder builder() {
        return new SelectionsBuilder();
    }

    public List<javax.persistence.criteria.Selection<?>> toJpaSelection(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        return selections.stream()
                .map(item -> (javax.persistence.criteria.Selection<?>) item.toJpaSelection(root, query, cb))
                .collect(Collectors.toList());
    }

    public static class SelectionsBuilder {
        private List<Selection> selections;

        private SelectionsBuilder() {
            selections = new ArrayList<>();
        }

        public SelectionsBuilder add(String property) {
            return add(property, property);
        }

        public SelectionsBuilder add(String property, String alias) {
            SimpleSelection simpleSelection = SimpleSelection.by(property, alias);

            selections.add(simpleSelection);

            return this;
        }

        public Selections build() {
            return new Selections(this.selections);
        }
    }

    public static class SimpleSelection implements Selection {
        private String property;
        private String alias;

        private SimpleSelection(String property) {
            this(property, property);
        }

        private SimpleSelection(String property, String alias) {
            this.property = property;
            this.alias = alias;
        }

        public static SimpleSelection by(String property) {
            return new SimpleSelection(property, property);
        }

        public static SimpleSelection by(String property, String alias) {
            return new SimpleSelection(property, alias);
        }

        @Override
        public String getProperty() {
            return property;
        }

        @Override
        public String getAlias() {
            return alias;
        }
    }
}
