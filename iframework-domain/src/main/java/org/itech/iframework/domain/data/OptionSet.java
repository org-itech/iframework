package org.itech.iframework.domain.data;

import java.util.Collection;
import java.util.HashSet;

/**
 * OptionSet
 *
 * @param <O>
 * @author liuqiang
 */
public class OptionSet<O extends OptionItem> extends HashSet<O> {
    public OptionSet() {
    }

    public OptionSet(Collection<? extends O> collection) {
        super(collection);
    }

    public OptionSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public OptionSet(int initialCapacity) {
        super(initialCapacity);
    }
}
