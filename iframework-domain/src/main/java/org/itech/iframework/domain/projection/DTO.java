package org.itech.iframework.domain.projection;

/**
 * DTO
 *
 * @author liuqiang
 */
public interface DTO<T, ID> {
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
