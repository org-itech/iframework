package org.itech.iframework.domain.data;

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
}
