package org.itech.iframework.service;

import org.itech.iframework.domain.model.AbstractEntity;
import org.itech.iframework.domain.model.Persistable;
import org.itech.iframework.domain.projection.DTO;
import org.itech.iframework.domain.query.filter.Filter;
import org.itech.iframework.service.dto.CommandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * AbstractService
 *
 * @param <T>  T
 * @param <ID> ID
 * @param <D>  D
 * @author liuqiang
 */
public abstract class AbstractService<T extends AbstractEntity, ID, D extends CommandDTO<T>> implements Service<T, String, D> {
    public AbstractService() {

    }

    @Override
    public D create(@Valid @NotNull(message = DTO_NOT_NULL) D dto) {
        return null;
    }

    @Override
    public D update(@Valid @NotNull(message = DTO_NOT_NULL) D dto) {
        return null;
    }

    @Override
    public void delete(@NotBlank(message = ID_NOT_NULL) String id, long version) {

    }

    @Override
    public D findById(@NotBlank(message = ID_NOT_NULL) String id) {
        return null;
    }

    @Override
    public boolean exists(@NotNull(message = SPEC_NOT_NULL) Specification<T> specification) {
        return false;
    }

    @Override
    public boolean existsById(@NotBlank(message = ID_NOT_NULL) String id) {
        return false;
    }

    @Override
    public <DO extends DTO<T>> DO findById(@NotBlank(message = ID_NOT_NULL) String id, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return null;
    }

    @Override
    public <DO extends DTO<T>> Optional<DO> findOne(Specification<T> specification, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return Optional.empty();
    }

    @Override
    public <DO extends DTO<T>> Page<DO> findAll(Specification<T> specification, @NotNull(message = PAGEABLE_NOT_NULL) Pageable pageable, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return null;
    }

    @Override
    public <DO extends DTO<T>> List<DO> findAll(Specification<T> specification, Sort sort, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return null;
    }

    @Override
    public <DO extends DTO<T>> Page<DO> list(Filter filter, Pageable pageable, Map<String, Object> extra) {
        return null;
    }
}
