package org.itech.iframework.domain.dto;

import org.springframework.util.StringUtils;

/**
 * DTO
 *
 * @author liuqiang
 */
public interface DTO<T, ID> {
    /**
     * get id
     *
     * @return id
     */
    ID getId();

    /**
     * set id
     */
    void setId(ID id);

    /**
     * 是否是新的
     *
     * @return 是否是新的
     */
    default boolean isNew() {
        return StringUtils.isEmpty(getId());
    }
}
