package org.itech.iframework.service;

import org.itech.iframework.domain.dto.DTO;
import org.itech.iframework.domain.model.Persistable;
import org.itech.iframework.domain.query.filter.Filter;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Service
 *
 * @author liuqiang
 */
public interface Service<T extends Persistable<ID>, D extends DTO<T, ID>, ID> extends ResolvableTypeProvider {
    /**
     * 创建实体
     *
     * @param dto DTO
     * @return DTO
     */
    D create(D dto);

    /**
     * 修改实体
     *
     * @param dto DTO
     * @return DTO
     */
    D update(D dto);

    /**
     * 删除实体
     *
     * @param id      标识
     * @param version 版本号
     */
    void delete(ID id, long version);

    /**
     * 根据标识获取DTO
     *
     * @param id 标识
     * @return DTO
     */
    D findById(ID id);

    /**
     * 是否存在满足规约的实体
     *
     * @param specification 规约
     * @return 是否存在
     */
    boolean exists(Specification<T> specification);

    /**
     * 是否存在
     *
     * @param id 标识
     * @return 是否存在
     */
    boolean existsById(ID id);

    /**
     * 根据标识获取DTO
     *
     * @param id    标识
     * @param clazz DTO
     * @param <DO>  DTO
     * @return DTO
     */
    <DO extends DTO<T, ID>> DO findById(ID id, Class<DO> clazz);

    /**
     * 根据筛选器获取DTO
     *
     * @param specification 规约
     * @param clazz         DTO
     * @param <DO>          DTO
     * @return DTO
     */
    <DO extends DTO<T, ID>> Optional<DO> findOne(Specification<T> specification, Class<DO> clazz);

    /**
     * 获取分页列表dto
     *
     * @param specification 筛选器对象
     * @param pageable      分页对象
     * @param clazz         DTO类型
     * @param <DO>          类型
     * @return 分页DTO
     */
    <DO extends DTO<T, ID>> Page<DO> findAll(Specification<T> specification, Pageable pageable, Class<DO> clazz);

    /**
     * 获取列表DTO
     *
     * @param specification 规约
     * @param sort          排序器对象
     * @param clazz         clazz
     * @param <DO>          类型
     * @return 分页DTO
     */
    <DO extends DTO<T, ID>> List<DO> findAll(Specification<T> specification, Sort sort, Class<DO> clazz);

    /**
     * 获取列表DTO
     *
     * @param filter   筛选对象
     * @param pageable 分页对象
     * @param extra    扩展参数
     * @return 分页DTO
     */
    <DO extends DTO<T, ID>> Page<DO> list(Filter filter, Pageable pageable, Map<String, Object> extra);

    @SuppressWarnings("unchecked")
    default Class<T> getEntityClass() {
        return (Class<T>) Objects.requireNonNull(getResolvableType()).getGeneric(0).resolve();
    }

    @SuppressWarnings("unchecked")
    default Class<D> getDTOClass() {
        return (Class<D>) Objects.requireNonNull(getResolvableType()).getGeneric(1).resolve();
    }

    default ResolvableType getResolvableType() {
        return ResolvableType.forClass(this.getClass());
    }
}
