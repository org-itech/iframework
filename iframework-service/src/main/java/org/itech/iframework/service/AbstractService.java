package org.itech.iframework.service;

import com.github.dozermapper.core.Mapper;
import org.itech.iframework.domain.dto.DTO;
import org.itech.iframework.domain.model.Persistable;
import org.itech.iframework.domain.query.filter.Filter;
import org.itech.iframework.domain.repository.JpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * AbstractService
 *
 * @param <T> T
 * @param <D> D
 * @author liuqiang
 */
@Validated
public abstract class AbstractService<T extends Persistable<String>, D extends DTO<T, String>>
        implements Service<T, D, String>, ResolvableTypeProvider {
    private final static String DTO_NOT_NULL = "参数 dto 不能为空！";
    private final static String ID_NOT_NULL = "参数 id 不能为空！";
    private final static String SPEC_NOT_NULL = "参数 specification 不能为空！";
    private final static String CLAZZ_NOT_NULL = "参数 clazz 不能为空！";
    private final static String PAGEABLE_NOT_NULL = "参数 pageable 不能为空！";

    private final JpaRepository<T, String> repository;
    private final Mapper mapper;

    public AbstractService(JpaRepository<T, String> repository
            , Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public D create(@Valid @NotNull(message = DTO_NOT_NULL) D dto) {
        dto.setId(null);

        doBeforeCreate(dto);

        T entity = mapDTOToEntity(dto);

        entity = repository.saveAndRefresh(entity);

        doAfterCreate(entity, dto);

        return mapEntityToDTO(entity);
    }

    @Override
    public D update(@Valid @NotNull(message = DTO_NOT_NULL) D dto) {
        return null;
    }

    @Override
    public void delete(@NotBlank(message = ID_NOT_NULL) String s, long version) {

    }

    @Override
    public D findById(@NotBlank(message = ID_NOT_NULL) String s) {
        return null;
    }

    @Override
    public boolean exists(@NotNull(message = SPEC_NOT_NULL) Specification<T> specification) {
        return false;
    }

    @Override
    public boolean existsById(@NotBlank(message = ID_NOT_NULL) String s) {
        return false;
    }

    @Override
    public <DO extends DTO<T, String>> DO findById(@NotBlank(message = ID_NOT_NULL) String s, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return null;
    }

    @Override
    public <DO extends DTO<T, String>> Optional<DO> findOne(Specification<T> specification, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return Optional.empty();
    }

    @Override
    public <DO extends DTO<T, String>> Page<DO> findAll(Specification<T> specification, @NotNull(message = PAGEABLE_NOT_NULL) Pageable pageable, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return null;
    }

    @Override
    public <DO extends DTO<T, String>> List<DO> findAll(Specification<T> specification, Sort sort, @NotNull(message = CLAZZ_NOT_NULL) Class<DO> clazz) {
        return null;
    }

    @Override
    public <DO extends DTO<T, String>> Page<DO> list(Filter filter, @NotNull(message = PAGEABLE_NOT_NULL) Pageable pageable, Map<String, Object> extra) {
        return null;
    }

    public JpaRepository<T, String> getRepository() {
        return repository;
    }

    public Mapper getMapper() {
        return mapper;
    }

    protected D mapEntityToDTO(T entity) {
        return this.getMapper().map(entity, getDTOClass());
    }

    protected T mapDTOToEntity(D dto) {
        return this.getMapper().map(dto, getEntityClass());
    }

    protected abstract void doAfterCreate(T entity, D dto);

    protected abstract void doBeforeCreate(D dto);

    private T getEntityInstance() {
        return BeanUtils.instantiateClass(getEntityClass());
    }

    private D getDTOInstance() {
        return BeanUtils.instantiateClass(getDTOClass());
    }
}
