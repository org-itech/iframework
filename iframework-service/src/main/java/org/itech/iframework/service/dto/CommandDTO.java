package org.itech.iframework.service.dto;

import org.itech.iframework.domain.model.Optimistic;
import org.itech.iframework.domain.projection.DTO;

/**
 * CommandDTO
 *
 * @param <T> T
 * @author liuqiang
 */
public interface CommandDTO<ID, Version, T extends Optimistic<ID, Version>> extends DTO<ID, T> {
    /**
     * set id
     *
     * @param id id
     */
    void setId(ID id);

    /**
     * set version
     *
     * @param version version
     */
    void setVersion(Version version);
}
