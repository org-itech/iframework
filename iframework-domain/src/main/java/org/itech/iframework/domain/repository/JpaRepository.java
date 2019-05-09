package org.itech.iframework.domain.repository;

import org.itech.iframework.domain.projection.DTO;
import org.itech.iframework.domain.query.Selections;
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
 * 仓储接口
 *
 * @param <T> 聚合根类型
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
     * @param entity 实体
     * @return the saved entity
     */
    <S extends T> S saveAndRefresh(S entity);

    /**
     * 是否存在满足规约的实体
     *
     * @param specification 规约
     * @return 是否存在
     */
    boolean exists(Specification<T> specification);

    /**
     * 计算聚合
     *
     * @param aggregator 聚合器
     * @return 聚合结果
     */
    List<Map<String, Object>> aggregate(Aggregator aggregator);

    /**
     * 根据id获取DTO
     *
     * @param id       聚合根标识
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto
     */
    <D extends DTO<T>> Optional<D> findById(String id, Class<D> dtoClass);

    /**
     * 获取全部dto
     *
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAll(Class<D> dtoClass);

    /**
     * 根据id集合获取全部dto
     *
     * @param ids      id集合
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAllById(Iterable<String> ids, Class<D> dtoClass);

    /**
     * 根据排序对象获取全部dto
     *
     * @param sort     排序对象
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAll(Sort sort, Class<D> dtoClass);

    /**
     * 根据分页对象获取dto分页数据
     *
     * @param pageable 分页对象
     * @param dtoClass dto class
     * @return dto分页数据
     */
    <D extends DTO<T>> Page<D> findAll(Pageable pageable, Class<D> dtoClass);


    /**
     * 根据规约获取dto
     *
     * @param spec     规约
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto
     */
    <D extends DTO<T>> Optional<D> findOne(Specification<T> spec, Class<D> dtoClass);

    /**
     * 根据规约获取全部dto
     *
     * @param spec     规约
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto集合
     */
    <D extends DTO<T>> List<D> findAll(Specification<T> spec, Class<D> dtoClass);

    /**
     * 根据规约查找dto分页数据
     *
     * @param spec     规约
     * @param pageable 分页对象
     * @param dtoClass dto class
     * @param <D>      类型
     * @return dto分页数据
     */
    <D extends DTO<T>> Page<D> findAll(Specification<T> spec, Pageable pageable, Class<D> dtoClass);

    /**
     * 根据规约查找dto数据，返回排序数据
     *
     * @param spec     规约
     * @param sort     排序对象
     * @param dtoClass dto class
     * @param <D>      类型
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
    Optional<Map<String, Object>> findById(String id, Selections selections);

    /**
     * 获取指定数据
     *
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Selections selections);

    /**
     * 获取指定数据
     *
     * @param ids        ids
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAllById(Iterable<String> ids, Selections selections);

    /**
     * 获取指定数据
     *
     * @param sort       sort
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Sort sort, Selections selections);

    /**
     * 获取指定数据
     *
     * @param pageable   pageable
     * @param selections selections
     * @return 指定数据
     */
    Page<Map<String, Object>> findAll(Pageable pageable, Selections selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param selections selections
     * @return 指定数据
     */
    Optional<Map<String, Object>> findOne(Specification<T> spec, Selections selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Specification<T> spec, Selections selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param pageable   pageable
     * @param selections selections
     * @return 指定数据
     */
    Page<Map<String, Object>> findAll(Specification<T> spec, Pageable pageable, Selections selections);

    /**
     * 获取指定数据
     *
     * @param spec       spec
     * @param sort       sort
     * @param selections selections
     * @return 指定数据
     */
    List<Map<String, Object>> findAll(Specification<T> spec, Sort sort, Selections selections);
}
