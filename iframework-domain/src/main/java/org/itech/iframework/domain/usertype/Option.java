package org.itech.iframework.domain.usertype;

/**
 * Option
 *
 * @author liuqiang
 */
public interface Option {
    /**
     * get name
     *
     * @return name
     */
    String getName();

    /**
     * get code
     *
     * @return code
     */
    String getCode();

    /**
     * get items
     *
     * @return items
     */
    String getItems();

    /**
     * 是否支持位运算
     *
     * @return 支持位运算
     */
    boolean isBitwise();
}
