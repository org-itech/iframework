package org.itech.iframework.service.dto;

import org.itech.iframework.domain.dto.DTO;
import org.itech.iframework.domain.model.Persistable;

/**
 * QueryDTO
 *
 * @param <T> T
 * @author liuqiang
 */
public interface QueryDTO<T extends Persistable<String>> extends DTO<T, String> {
}
