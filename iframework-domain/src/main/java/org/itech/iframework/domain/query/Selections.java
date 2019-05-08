package org.itech.iframework.domain.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            return null;
        }

        @Override
        public String getAlias() {
            return null;
        }
    }
}
