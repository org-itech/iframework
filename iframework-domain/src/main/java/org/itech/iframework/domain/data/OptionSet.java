package org.itech.iframework.domain.data;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import java.util.HashSet;
import java.util.Objects;

/**
 * OptionSet
 *
 * @param <O>
 * @author liuqiang
 */
public class OptionSet<O extends OptionItem> extends HashSet<O> implements ResolvableTypeProvider {
    public OptionSet() {
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClass(this.getClass());
    }

    @SuppressWarnings("unchecked")
    public Class<O> getOptionClass() {
        return (Class<O>) Objects.requireNonNull(getResolvableType()).getGeneric(0).resolve();
    }
}
