package org.itech.iframework.domain.data;

/**
 * OptionItem
 *
 * @author liuqiang
 */
public interface OptionItem<O extends Option> {
    /**
     * get item name
     *
     * @return item name
     */
    String getName();

    /**
     * set name
     *
     * @param name name
     */
    void setName(String name);

    /**
     * get item value
     *
     * @return itme value
     */
    Long getValue();

    /**
     * set value
     *
     * @param value value
     */
    void setValue(Long value);

    /**
     * get option
     *
     * @return option
     */
    O getOption();

    /**
     * get option code
     *
     * @return option code
     */
    default String getOptionCode() {
        return getOption().getCode();
    }

    /**
     * get option name
     *
     * @return option name
     */
    default String getOptionName() {
        return getOption().getName();
    }

    /**
     * is bitwise
     *
     * @return bitwise
     */
    default boolean isBitwise() {
        return getOption().isBitwise();
    }
}
