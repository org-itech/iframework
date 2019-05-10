package org.itech.iframework.domain.repository;

import org.itech.iframework.domain.projection.DTO;
import org.itech.iframework.domain.projection.Projection;
import org.itech.iframework.domain.query.Selection;
import org.itech.iframework.domain.query.aggregate.Aggregator;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * DefaultJpaRepository
 *
 * @param <T>
 * @param <ID>
 * @author liuqiang
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DefaultJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID> {
    private static ConcurrentHashMap<Class<?>, javax.persistence.criteria.Selection<?>[]> selectionsCache = new ConcurrentHashMap<>(64);
    private final EntityManager em;

    public DefaultJpaRepository(JpaEntityInformation<T, ?> entityInformation,
                                EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.em = entityManager;
    }

    @Override
    public <S extends T> S saveAndRefresh(S entity) {
        return null;
    }

    @Override
    public boolean exists(Specification<T> specification) {
        return false;
    }

    @Override
    public List<Map<String, Object>> aggregate(Aggregator aggregator) {
        return null;
    }

    @Override
    public List<Map<String, Object>> aggregate(Sort sort, Aggregator aggregator) {
        return null;
    }

    @Override
    public List<Map<String, Object>> aggregate(Pageable pageable, Aggregator aggregator) {
        return null;
    }

    @Override
    public List<Map<String, Object>> aggregate(Specification<T> spec, Pageable pageable, Aggregator aggregator) {
        return null;
    }

    @Override
    public <D extends DTO<T>> Optional<D> findById(String id, Class<D> dtoClass) {
        return Optional.empty();
    }

    @Override
    public <D extends DTO<T>> List<D> findAll(Class<D> dtoClass) {
        return null;
    }

    @Override
    public <D extends DTO<T>> List<D> findAllById(Iterable<String> ids, Class<D> dtoClass) {
        return null;
    }

    @Override
    public <D extends DTO<T>> List<D> findAll(Sort sort, Class<D> dtoClass) {
        return null;
    }

    @Override
    public <D extends DTO<T>> Page<D> findAll(Pageable pageable, Class<D> dtoClass) {
        return null;
    }

    @Override
    public <D extends DTO<T>> Optional<D> findOne(Specification<T> spec, Class<D> dtoClass) {
        return Optional.empty();
    }

    @Override
    public <D extends DTO<T>> List<D> findAll(Specification<T> spec, Class<D> dtoClass) {
        return null;
    }

    @Override
    public <D extends DTO<T>> Page<D> findAll(Specification<T> spec, Pageable pageable, Class<D> dtoClass) {
        return null;
    }

    @Override
    public <D extends DTO<T>> List<D> findAll(Specification<T> spec, Sort sort, Class<D> dtoClass) {
        return null;
    }

    @Override
    public Optional<Map<String, Object>> findById(String id, Iterable<Selection<?>> selections) {
        return Optional.empty();
    }

    @Override
    public List<Map<String, Object>> findAll(Iterable<Selection<?>> selections) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAllById(Iterable<String> ids, Iterable<Selection<?>> selections) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAll(Sort sort, Iterable<Selection<?>> selections) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> findAll(Pageable pageable, Iterable<Selection<?>> selections) {
        return null;
    }

    @Override
    public Optional<Map<String, Object>> findOne(Specification<T> spec, Iterable<Selection<?>> selections) {
        return Optional.empty();
    }

    @Override
    public List<Map<String, Object>> findAll(Specification<T> spec, Iterable<Selection<?>> selections) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> findAll(Specification<T> spec, Pageable pageable, Iterable<Selection<?>> selections) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAll(Specification<T> spec, Sort sort, Iterable<Selection<?>> selections) {
        return null;
    }

    protected <D extends DTO<T>, S extends T> TypedQuery<D> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort, Class<D> dtoClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<D> query = builder.createQuery(dtoClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        query.select(builder.construct(dtoClass, getSelections(root, builder, dtoClass)));

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    protected <S extends T> TypedQuery<Tuple> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort, List<javax.persistence.criteria.Selection<?>> selections) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        query.multiselect(selections);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    protected <S extends T> TypedQuery<Tuple> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort, Aggregator aggregator) {
        List<javax.persistence.criteria.Selection<?>> selections = new ArrayList<>();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        if (aggregator.getGroupBys() != null) {
            List<javax.persistence.criteria.Expression<?>> groupBySelections = new ArrayList<>();

            //noinspection unchecked
            aggregator.getGroupBys().forEach(item -> groupBySelections.add(item.toJpaSelection(root, query, builder)));

            selections.addAll(groupBySelections);

            query.groupBy(groupBySelections);
        }

        if (aggregator.getAggregates() != null) {
            List<javax.persistence.criteria.Expression<?>> aggregateSelections = new ArrayList<>();

            //noinspection unchecked
            aggregator.getAggregates().forEach(item -> aggregateSelections.add(item.toJpaSelection(root, query, builder)));

            selections.addAll(aggregateSelections);
        }

        if (aggregator.getHaving() != null) {
            query.having(aggregator.getHaving().toPredicate(root, query, builder));
        }

        query.multiselect(selections);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    private <D extends DTO<T>, S extends T> javax.persistence.criteria.Selection<?>[] getSelections(Root<S> root, CriteriaBuilder builder, Class<D> dtoClass) {
        javax.persistence.criteria.Selection<?>[] result = selectionsCache.get(dtoClass);

        if (result == null) {
            List<javax.persistence.criteria.Selection<?>> selections = new ArrayList<>();

            ReflectionUtils.doWithFields(dtoClass, field -> {
                String property = field.getName();

                javax.persistence.criteria.Selection<?> selection;

                Projection projection = field.getAnnotation(Projection.class);

                if (projection != null) {
                    if (!projection.ignore()) {
                        selection = builder.nullLiteral(field.getType()).alias(property);
                    } else {
                        selection = QueryUtils.toExpressionRecursively(root, PropertyPath.from(projection.path(), root.getJavaType())).alias(property);
                    }
                } else {
                    selection = root.get(property).alias(property);
                }

                selections.add(selection);
            });

            javax.persistence.criteria.Selection<?>[] selectionArray = new javax.persistence.criteria.Selection[selections.size()];
            selections.toArray(selectionArray);

            result = selectionsCache.putIfAbsent(dtoClass, selectionArray);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private <U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass, CriteriaQuery query) {
        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {
        if (getRepositoryMethodMetadata() == null) {
            return query;
        }

        LockModeType type = getRepositoryMethodMetadata().getLockModeType();
        return type == null ? query : query.setLockMode(type);

        // todo
        // applyQueryHints(toReturn);

        // return toReturn;
    }
}
