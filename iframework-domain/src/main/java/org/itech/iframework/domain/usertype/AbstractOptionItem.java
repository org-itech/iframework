package org.itech.iframework.domain.usertype;

/**
 * AbstractOptionItem
 *
 * @author liuqiang
 */
public abstract class AbstractOptionItem<O extends AbstractOptionItem> implements OptionItem {
    private String name;
    private Long value;

    public AbstractOptionItem(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    public AbstractOptionItem(Long value) {
        this(value, null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
    }
}
