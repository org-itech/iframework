package org.itech.iframework.domain.data;

import java.util.List;

/**
 * Option
 *
 * @author liuqiang
 */
public interface Option<Item extends OptionItem> {
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
    List<Item> getItems();

    /**
     * 是否支持位运算
     *
     * @return 支持位运算
     */
    boolean isBitwise();
}
