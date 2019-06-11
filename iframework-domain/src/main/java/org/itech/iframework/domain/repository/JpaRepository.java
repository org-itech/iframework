package org.itech.iframework.domain.repository;

import org.itech.iframework.domain.projection.DTO;
import org.itech.iframework.domain.query.Selection;
import org.itech.iframework.domain.query.aggregate.Aggregator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.Tuple;
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
public interface JpaRepository<T extends Persistable<ID>, ID> extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {
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
     * aggregate
     *
     * @param aggregator aggregator
     * @return data
     */
    List<Tuple> aggregate(Aggregator aggregator);

    /**
     * aggregate
     *
     * @param sort       sort
     * @param aggregator aggregator
     * @return data
     */
    List<Tuple> aggregate(Sort sort, Aggregator aggregator);

    /**
     * aggregate
     *
     * @param pageable   pageable
     * @param aggregator aggregator
     * @return data
     */
    Page<Tuple> aggregate(Pageable pageable, Aggregator aggregator);

    /**
     * aggregate
     *
     * @param spec       spec
     * @param pageable   pageable
     * @param aggregator aggregator
     * @return data
     */
    Page<Tuple> aggregate(Specification<T> spec, Pageable pageable, Aggregator aggregator);

    /**
     * findById
     *
     * @param id       id
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> Optional<D> findById(ID id, Class<D> dtoClass);

    /**
     * findAll
     *
     * @param dtoClass dto
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> List<D> findAll(Class<D> dtoClass);

    /**
     * findAllById
     *
     * @param ids      ids
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> List<D> findAllById(Iterable<ID> ids, Class<D> dtoClass);

    /**
     * findAll
     *
     * @param sort     sort
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> List<D> findAll(Sort sort, Class<D> dtoClass);

    /**
     * findAll
     *
     * @param pageable pageable
     * @param dtoClass dto class
     * @return data
     */
    <D extends DTO<T>> Page<D> findAll(Pageable pageable, Class<D> dtoClass);

    /**
     * findOne
     *
     * @param spec     spec
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> Optional<D> findOne(Specification<T> spec, Class<D> dtoClass);

    /**
     * findAll
     *
     * @param spec     spec
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> List<D> findAll(Specification<T> spec, Class<D> dtoClass);

    /**
     * findAll
     *
     * @param spec     spec
     * @param pageable pageable
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> Page<D> findAll(Specification<T> spec, Pageable pageable, Class<D> dtoClass);

    /**
     * findAll
     *
     * @param spec     spec
     * @param sort     sort
     * @param dtoClass dto class
     * @param <D>      D
     * @return data
     */
    <D extends DTO<T>> List<D> findAll(Specification<T> spec, Sort sort, Class<D> dtoClass);

    /**
     * findById
     *
     * @param id         id
     * @param selections selections
     * @return data
     */
    Optional<Tuple> findById(ID id, Iterable<Selection> selections);

    /**
     * findAll
     *
     * @param selections selections
     * @return data
     */
    List<Tuple> findAll(Iterable<Selection> selections);

    /**
     * findAllById
     *
     * @param ids        ids
     * @param selections selections
     * @return data
     */
    List<Tuple> findAllById(Iterable<ID> ids, Iterable<Selection> selections);

    /**
     * findAll
     *
     * @param sort       sort
     * @param selections selections
     * @return data
     */
    List<Tuple> findAll(Sort sort, Iterable<Selection> selections);

    /**
     * findAll
     *
     * @param pageable   pageable
     * @param selections selections
     * @return data
     */
    Page<Tuple> findAll(Pageable pageable, Iterable<Selection> selections);

    /**
     * findOne
     *
     * @param spec       spec
     * @param selections selections
     * @return data
     */
    Optional<Tuple> findOne(Specification<T> spec, Iterable<Selection> selections);

    /**
     * findAll
     *
     * @param spec       spec
     * @param selections selections
     * @return data
     */
    List<Tuple> findAll(Specification<T> spec, Iterable<Selection> selections);

    /**
     * findAll
     *
     * @param spec       spec
     * @param pageable   pageable
     * @param selections selections
     * @return data
     */
    Page<Tuple> findAll(Specification<T> spec, Pageable pageable, Iterable<Selection> selections);

    /**
     * findAll
     *
     * @param spec       spec
     * @param sort       sort
     * @param selections selections
     * @return data
     */
    List<Tuple> findAll(Specification<T> spec, Sort sort, Iterable<Selection> selections);
}
