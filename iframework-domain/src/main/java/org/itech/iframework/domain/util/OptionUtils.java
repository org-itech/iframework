package org.itech.iframework.domain.util;

import org.itech.iframework.domain.data.OptionItem;
import org.itech.iframework.domain.data.OptionSet;

/**
 * OptionUtils
 *
 * @author liuqiang
 */
public class OptionUtils {
    public static <O extends OptionItem> long getValue(Class<O> clazz, OptionSet<O> options) {
        return 0;
    }

    public static <O extends OptionItem> OptionSet<O> getItems(Class<O> clazz, long value) {
        return new OptionSet<>();
    }
}
