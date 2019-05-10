package org.itech.iframework.domain.repository;

import org.itech.iframework.domain.projection.DTO;
import org.itech.iframework.domain.query.Selection;
import org.itech.iframework.domain.query.aggregate.Aggregator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * jpa repository interface
 *
 * @param <T> T
 * @author liuqiang
 */
@NoRepositoryBean
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * Flushes all pending changes to the datasource.
     */
    void flush();

    /**
     * Saves an entity and refresh changes instantly.
     *
     * @param entity entity
     * @return the saved entity
     */
    <S extends T> S saveAndRefresh(S entity);

    /**
     * 是否存在满足规约的实体
     *
     * @param spec spec
     * @return boolean
     */
    boolean exists(Specification<T> spec);

    /**
     * 计算聚合
     *
     * @param aggregator aggregator
     * @return 聚合结果
     */
    List<Map<String, Object>> aggregate(Aggregator aggregator);

    /**
     * 计算聚合
     *
     * @param sort       sort
     * @param aggregator aggregator
     * @return 聚合结果
     */
    List<Map<String, Object>> aggregate(Sort sort, Aggregator aggregator);

    /**
     * 计算聚合
     *
     * @param pageable   pageable
     * @param aggregator aggregator
     * @return 聚合结果
     */
    List<Map<String, Object>> aggregate(Pageable pageable, Aggregator aggregator);

    /**
     * 计算聚合
     *
     * @param spec       spec
     * @param pageable   pageable
     * @param aggregator aggregator
     * @return 聚合结果
     */
    List<Map<String, Object>> aggregate(Specification<T> spec, Pageable pageable, Aggregator aggregator);

    /**
     * 根据id获取DTO
     *
     * @param id       id
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto
     */
    <D extends DTO<T>> Optional<D> findById(String id, Class<D> dtoClass);

    /**
     * 获取全部dto
     *
     * @param dtoClass dto
     * @param <D>      D
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAll(Class<D> dtoClass);

    /**
     * 根据id集合获取全部dto
     *
     * @param ids      ids
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAllById(Iterable<String> ids, Class<D> dtoClass);

    /**
     * 根据排序对象获取全部dto
     *
     * @param sort     sort
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAll(Sort sort, Class<D> dtoClass);

    /**
     * 根据分页对象获取dto分页数据
     *
     * @param pageable pageable
     * @param dtoClass dto class
     * @return dto分页数据
     */
    <D extends DTO<T>> Page<D> findAll(Pageable pageable, Class<D> dtoClass);


    /**
     * 根据规约获取dto
     *
     * @param spec     spec
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto
     */
    <D extends DTO<T>> Optional<D> findOne(Specification<T> spec, Class<D> dtoClass);

    /**
     * 根据规约获取全部dto
     *
     * @param spec     spec
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAll(Specification<T> spec, Class<D> dtoClass);

    /**
     * 根据规约查找dto分页数据
     *
     * @param spec     spec
     * @param pageable pageable
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto分页数据
     */
    <D extends DTO<T>> Page<D> findAll(Specification<T> spec, Pageable pageable, Class<D> dtoClass);

    /**
     * 根据规约查找dto数据，返回排序数据
     *
     * @param spec     spec
     * @param sort     sort
     * @param dtoClass dto class
     * @param <D>      D
     * @return dto分页数据
     */
    <D extends DTO<T>> List<D> findAll(Specification<T> spec, Sort sort, Class<D> dtoClass);

    /**
     * 获取指定数据
     *
     * @param id         id
     * @param selections selections
     * @return 指定数据
     */
    Optional<Map<String, Object>> findById(String id, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param ids        ids
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAllById(Iterable<String> ids, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param sort       sort
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Sort sort, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param pageable   pageable
     * @param selections selections
     * @return 指定数据
     */
    Page<Map<String, Object>> findAll(Pageable pageable, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param selections selections
     * @return 指定数据
     */
    Optional<Map<String, Object>> findOne(Specification<T> spec, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Specification<T> spec, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param pageable   pageable
     * @param selections selections
     * @return 指定数据
     */
    Page<Map<String, Object>> findAll(Specification<T> spec, Pageable pageable, Iterable<Selection<?>> selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param sort       sort
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Specification<T> spec, Sort sort, Iterable<Selection<?>> selections);
}
