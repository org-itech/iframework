package org.itech.iframework.service.dto;

import org.itech.iframework.domain.model.AbstractEntity;
import org.itech.iframework.domain.projection.DTO;

/**
 * CommandDTO
 *
 * @param <T> T
 * @author liuqiang
 */
public interface CommandDTO<T extends AbstractEntity> extends DTO<T> {
    /**
     * get id
     *
     * @return id
     */
    String getId();

    /**
     * set id
     *
     * @param id id
     */
    void setId(String id);

    /**
     * get version
     *
     * @return version
     */
    Integer getVersion();

    /**
     * set version
     *
     * @param version version
     */
    void setVersion(Integer version);
}
