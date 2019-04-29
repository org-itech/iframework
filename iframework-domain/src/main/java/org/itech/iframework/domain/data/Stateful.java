package org.itech.iframework.domain.data;

/**
 * Stateful
 *
 * @param <T>
 * @author liuqiang
 */
public interface Stateful<T extends Enum<T>> {
    /**
     * 获取状态
     *
     * @return 状态
     */
    T getStatus();

    /**
     * 设置状态
     *
     * @param status 状态
     */
    void setStatus(T status);
}
