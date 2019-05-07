package org.itech.iframework.domain.model;

/**
 * Persistable
 *
 * @param <ID> 标识类型
 * @author liuqiang
 */
public interface Persistable<ID> {
    /**
     * 获取标识
     *
     * @return 标识
     */
    ID getId();

    /**
     * 是否是新的
     *
     * @return 是否是新的
     */
    default boolean isNew() {
        return null == getId();
    }
}