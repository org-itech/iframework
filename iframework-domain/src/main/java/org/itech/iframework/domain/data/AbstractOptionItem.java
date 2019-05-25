package org.itech.iframework.domain.data;

import java.util.Objects;

/**
 * AbstractOptionItem
 *
 * @author liuqiang
 */
public abstract class AbstractOptionItem<O extends Option> implements OptionItem<O> {
    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private Long value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractOptionItem<?> that = (AbstractOptionItem<?>) o;
        return Objects.equals(this.value, that.value) && Objects.equals(this.getOptionCode(), that.getOptionCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getOptionCode());
    }
}
