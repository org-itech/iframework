package org.itech.iframework.domain.usertype;

/**
 * OptionItem
 *
 * @author liuqiang
 */
public interface OptionItem {
    /**
     * get item name
     *
     * @return item name
     */
    String getName();

    /**
     * get item value
     *
     * @return itme value
     */
    Long getValue();

    /**
     * get option
     *
     * @return option
     */
    Option getOption();

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
