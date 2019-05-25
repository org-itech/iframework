package org.itech.iframework.service;

import org.itech.iframework.domain.model.Persistable;
import org.itech.iframework.domain.projection.DTO;
import org.itech.iframework.domain.query.aggregate.Aggregator;
import org.itech.iframework.domain.query.filter.Filter;
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
 * Service
 *
 * @author liuqiang
 */
public interface Service<T extends Persistable<ID>, ID, D extends DTO<T, ID>> {
    String DTO_NOT_NULL = "参数 dto 不能为空！";
    String ID_NOT_NULL = "参数 id 不能为空！";
    String SPEC_NOT_NULL = "参数 specification 不能为空！";
    String CLAZZ_NOT_NULL = "参数 clazz 不能为空！";
    String PAGEABLE_NOT_NULL = "参数 pageable 不能为空！";

    /**
     * 创建实体
     *
     * @param dto DTO
     * @return DTO
     */
    D create(@Valid @NotNull(message = DTO_NOT_NULL) D dto);

    /**
     * 修改实体
     *
     * @param dto DTO
     * @return DTO
     */
    D update(@Valid @NotNull(message = DTO_NOT_NULL) D dto);

    /**
     * 删除实体
     *
     * @param id      标识
     * @param version 版本号
     */
    void delete(@NotBlank(message = ID_NOT_NULL) String id, long version);

    /**
     * 根据标识获取DTO
     *
     * @param id 标识
     * @return DTO
     */
    D findById(@NotBlank(message = ID_NOT_NULL) String id);

    /**
     * 是否存在满足规约的实体
     *
     * @param specification 规约
     * @return 是否存在
     */
    boolean exists(@NotNull(message = SPEC_NOT_NULL) Specification<T> specification);

    /**
     * 是否存在
     *
     * @param id 标识
     * @return 是否存在
     */
    boolean existsById(@NotBlank(message = ID_NOT_NULL) String id);

    /**
     * 根据标识获取DTO
     *
     * @param id    标识
     * @param clazz DTO
     * @param <D>   DTO
     * @return DTO
     */
    <D extends DTO<T, ID>> DTO findById(@NotBlank(message = ID_NOT_NULL) String id, @NotNull(message = CLAZZ_NOT_NULL) Class<D> clazz);

    /**
     * 根据筛选器获取DTO
     *
     * @param specification 规约
     * @param clazz         DTO
     * @param <D>           DTO
     * @return DTO
     */
    <D extends DTO<T, ID>> Optional<D> findOne(Specification<T> specification, @NotNull(message = CLAZZ_NOT_NULL) Class<D> clazz);

    /**
     * 获取分页列表dto
     *
     * @param specification 筛选器对象
     * @param pageable      分页对象
     * @param clazz         DTO类型
     * @param <D>           类型
     * @return 分页DTO
     */
    <D extends DTO<T, ID>> Page<D> findAll(Specification<T> specification, @NotNull(message = PAGEABLE_NOT_NULL) Pageable pageable, @NotNull(message = CLAZZ_NOT_NULL) Class<D> clazz);

    /**
     * 获取列表DTO
     *
     * @param specification 规约
     * @param sort          排序器对象
     * @param clazz         clazz
     * @param <D>           类型
     * @return 分页DTO
     */
    <D extends DTO<T, ID>> List<D> findAll(Specification<T> specification, Sort sort,@NotNull(message = CLAZZ_NOT_NULL) Class<D> clazz);

    /**
     * 获取列表DTO
     *
     * @param filter   筛选对象
     * @param pageable 分页对象
     * @param extra    扩展参数
     * @return 分页DTO
     */
    <D extends DTO<T, ID>> Page<D> list(Filter filter, Pageable pageable, Map<String, Object> extra);

    /**
     * 获取指标
     *
     * @param filter    筛选对象
     * @param pageable  分页对象
     * @param aggregate 聚合对象
     * @param extra     扩展参数
     * @return 分页DTO
     */
    Page<?> statistic(Filter filter, Pageable pageable, Aggregator aggregate, Map<String, Object> extra);
}
