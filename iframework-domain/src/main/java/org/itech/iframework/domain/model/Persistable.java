package org.itech.iframework.domain.model;

import org.springframework.util.StringUtils;

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
        return StringUtils.isEmpty(getId());
    }
}