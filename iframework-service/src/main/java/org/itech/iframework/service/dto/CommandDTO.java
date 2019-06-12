package org.itech.iframework.service.dto;

import org.itech.iframework.domain.dto.DTO;
import org.itech.iframework.domain.model.Optimistic;

/**
 * CommandDTO
 *
 * @param <T> T
 * @author liuqiang
 */
public interface CommandDTO<T extends Optimistic<String, Integer>> extends DTO<T, String> {
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
