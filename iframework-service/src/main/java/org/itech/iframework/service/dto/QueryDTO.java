package org.itech.iframework.service.dto;

import org.itech.iframework.domain.model.Optimistic;
import org.itech.iframework.domain.projection.DTO;

/**
 * QueryDTO
 *
 * @param <T> T
 * @author liuqiang
 */
public interface QueryDTO<ID, Version, T extends Optimistic<ID, Version>> extends DTO<ID, T> {
}
