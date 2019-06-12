package org.itech.iframework.domain.repository;

import org.itech.iframework.domain.dto.DTO;
import org.itech.iframework.domain.model.Persistable;
import org.itech.iframework.domain.projection.Projection;
import org.itech.iframework.domain.query.Selection;
import org.itech.iframework.domain.query.aggregate.Aggregator;
import org.itech.iframework.domain.util.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * DefaultJpaRepository
 *
 * @param <T>
 * @param <ID>
 * @author liuqiang
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DefaultJpaRepository<T extends Persistable<ID>, ID> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID> {
    private static ConcurrentHashMap<Class<?>, javax.persistence.criteria.Selection<?>[]> selectionsCache = new ConcurrentHashMap<>(64);
    private static String AGGREGATOR_NOT_NULL = "聚合对象 aggregator 不能为空！";
    private static String PAGEABLE_NOT_NULL = "分页对象 pageable 不能为空！";
    private static String ID_NOT_NULL = "标识 id 不能为空！";
    private static String IDS_NOT_NULL = "标识 ids 不能为空！";
    private static String DTO_CLASS_NOT_NULL = "DTO类型 dtoClass 不能为空！";
    private static String SELECTIONS_NOT_NULL = "选贼对象 selections 不能为空！";
    private final EntityManager em;
    private final JpaEntityInformation<T, ?> entityInformation;

    public DefaultJpaRepository(JpaEntityInformation<T, ?> entityInformation,
                                EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> S saveAndRefresh(S entity) {
        this.save(entity);

        em.refresh(entity);

        return entity;
    }

    @Override
    public boolean exists(Specification<T> specification) {
        return false;
    }

    @Override
    public List<Tuple> aggregate(Aggregator aggregator) {
        return this.aggregate(Sort.unsorted(), aggregator);
    }

    @Override
    public List<Tuple> aggregate(Sort sort, Aggregator aggregator) {
        Assert.notNull(aggregator, AGGREGATOR_NOT_NULL);

        TypedQuery<Tuple> query = this.getQuery(null, getDomainClass(), sort == null ? Sort.unsorted() : sort, aggregator);

        return query.getResultList();
    }

    @Override
    public Page<Tuple> aggregate(Pageable pageable, Aggregator aggregator) {
        return this.aggregate(null, pageable, aggregator);
    }

    @Override
    public Page<Tuple> aggregate(Specification<T> spec, Pageable pageable, Aggregator aggregator) {
        Assert.notNull(aggregator, AGGREGATOR_NOT_NULL);
        Assert.notNull(pageable, PAGEABLE_NOT_NULL);

        TypedQuery<Tuple> query = applyPageable(this.getQuery(spec, getDomainClass(), pageable.getSort(), aggregator), pageable);

        return PageableExecutionUtils.getPage(query.getResultList(), pageable,
                () -> executeCountQuery(getCountQuery(spec, getDomainClass())));
    }

    @Override
    public <D extends DTO<T, ID>> Optional<D> findById(ID id, Class<D> dtoClass) {
        Assert.notNull(id, ID_NOT_NULL);
        Assert.notNull(dtoClass, DTO_CLASS_NOT_NULL);

        TypedQuery<D> query = getQuery(new IdSpecification(id), getDomainClass(), Sort.unsorted(), dtoClass);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public <D extends DTO<T, ID>> List<D> findAll(Class<D> dtoClass) {
        return this.findAll(Sort.unsorted(), dtoClass);
    }

    @Override
    public <D extends DTO<T, ID>> List<D> findAllById(Iterable<ID> ids, Class<D> dtoClass) {
        Assert.notNull(ids, IDS_NOT_NULL);
        Assert.notNull(dtoClass, DTO_CLASS_NOT_NULL);

        if (!ids.iterator().hasNext()) {
            return Collections.emptyList();
        }

        if (entityInformation.hasCompositeId()) {
            List<D> results = new ArrayList<>();

            for (ID id : ids) {
                findById(id, dtoClass).ifPresent(results::add);
            }

            return results;
        } else {
            TypedQuery<D> query = getQuery(new IdsSpecification(ids), getDomainClass(), Sort.unsorted(), dtoClass);

            return query.getResultList();
        }
    }

    @Override
    public <D extends DTO<T, ID>> List<D> findAll(Sort sort, Class<D> dtoClass) {
        return this.findAll(null, sort, dtoClass);
    }

    @Override
    public <D extends DTO<T, ID>> Page<D> findAll(Pageable pageable, Class<D> dtoClass) {
        return this.findAll(null, pageable, dtoClass);
    }

    @Override
    public <D extends DTO<T, ID>> Optional<D> findOne(Specification<T> spec, Class<D> dtoClass) {
        Assert.notNull(dtoClass, DTO_CLASS_NOT_NULL);

        TypedQuery<D> query = applyPageable(getQuery(null, getDomainClass(), Sort.unsorted(), dtoClass), PageRequest.of(0, 1));

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public <D extends DTO<T, ID>> List<D> findAll(Specification<T> spec, Class<D> dtoClass) {
        return this.findAll(spec, Sort.unsorted(), dtoClass);
    }

    @Override
    public <D extends DTO<T, ID>> Page<D> findAll(Specification<T> spec, Pageable pageable, Class<D> dtoClass) {
        Assert.notNull(dtoClass, DTO_CLASS_NOT_NULL);
        Assert.notNull(pageable, PAGEABLE_NOT_NULL);

        TypedQuery<D> query = applyPageable(getQuery(spec, getDomainClass(), pageable.getSort(), dtoClass), pageable);

        return PageableExecutionUtils.getPage(query.getResultList(), pageable,
                () -> executeCountQuery(getCountQuery(spec, getDomainClass())));
    }

    @Override
    public <D extends DTO<T, ID>> List<D> findAll(Specification<T> spec, Sort sort, Class<D> dtoClass) {
        Assert.notNull(dtoClass, DTO_CLASS_NOT_NULL);

        TypedQuery<D> query = getQuery(spec, getDomainClass(), sort, dtoClass);

        return query.getResultList();
    }

    @Override
    public Optional<Tuple> findById(ID id, Iterable<Selection> selections) {
        Assert.notNull(id, ID_NOT_NULL);

        TypedQuery<Tuple> query = getQuery(new IdSpecification(id), getDomainClass(), Sort.unsorted(), selections);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tuple> findAll(Iterable<Selection> selections) {
        return this.findAll(null, Sort.unsorted(), selections);
    }

    @Override
    public List<Tuple> findAllById(Iterable<ID> ids, Iterable<Selection> selections) {
        Assert.notNull(ids, IDS_NOT_NULL);
        Assert.notNull(selections, SELECTIONS_NOT_NULL);

        if (!ids.iterator().hasNext()) {
            return Collections.emptyList();
        }

        if (entityInformation.hasCompositeId()) {
            List<Tuple> results = new ArrayList<>();

            for (ID id : ids) {
                findById(id, selections).ifPresent(results::add);
            }

            return results;
        } else {
            TypedQuery<Tuple> query = getQuery(new IdsSpecification(ids), getDomainClass(), Sort.unsorted(), selections);

            return query.getResultList();
        }
    }

    @Override
    public List<Tuple> findAll(Sort sort, Iterable<Selection> selections) {
        return this.findAll(null, sort, selections);
    }

    @Override
    public Page<Tuple> findAll(Pageable pageable, Iterable<Selection> selections) {
        return this.findAll(null, pageable, selections);
    }

    @Override
    public Optional<Tuple> findOne(Specification<T> spec, Iterable<Selection> selections) {
        Assert.notNull(selections, SELECTIONS_NOT_NULL);

        TypedQuery<Tuple> query = applyPageable(getQuery(null, getDomainClass(), Sort.unsorted(), selections), PageRequest.of(0, 1));

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tuple> findAll(Specification<T> spec, Iterable<Selection> selections) {
        return this.findAll(spec, Sort.unsorted(), selections);
    }

    @Override
    public Page<Tuple> findAll(Specification<T> spec, Pageable pageable, Iterable<Selection> selections) {
        Assert.notNull(pageable, PAGEABLE_NOT_NULL);
        Assert.notNull(selections, SELECTIONS_NOT_NULL);

        TypedQuery<Tuple> query = applyPageable(getQuery(spec, getDomainClass(), pageable.getSort(), selections), pageable);

        return PageableExecutionUtils.getPage(query.getResultList(), pageable,
                () -> executeCountQuery(getCountQuery(spec, getDomainClass())));
    }

    @Override
    public List<Tuple> findAll(Specification<T> spec, Sort sort, Iterable<Selection> selections) {
        Assert.notNull(selections, SELECTIONS_NOT_NULL);

        TypedQuery<Tuple> query = getQuery(spec, getDomainClass(), sort, selections);

        return query.getResultList();
    }

    protected <D extends DTO<T, ID>, S extends T> TypedQuery<D> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort, Class<D> dtoClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<D> query = builder.createQuery(dtoClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        query.select(builder.construct(dtoClass, getSelections(root, builder, dtoClass)));

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    protected <S extends T> TypedQuery<Tuple> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort, Iterable<Selection> selections) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        query.multiselect(QueryUtils.toJpaSelections(selections, root, query, builder));

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    protected <S extends T> TypedQuery<Tuple> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort, Aggregator aggregator) {
        List<javax.persistence.criteria.Selection<?>> selections = new ArrayList<>();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        aggregator.getSelections().forEach(item -> selections.add(item.toJpaSelection(root, query, builder)));

        if (aggregator.getHaving() != null) {
            query.having(aggregator.getHaving().toPredicate(root, query, builder));
        }

        query.multiselect(selections);

        if (sort != null && sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    private <D extends DTO<T, ID>, S extends T> javax.persistence.criteria.Selection<?>[] getSelections(Root<S> root, CriteriaBuilder builder, Class<D> dtoClass) {
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
    private <U extends T> Root<U> applySpecificationToCriteria(Specification<U> spec, Class<U> domainClass, CriteriaQuery query) {
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
    }

    private List<Map<String, Object>> convertTupleToMap(List<Tuple> tuples, Iterable<Selection> selections) {
        return tuples.stream()
                .map(tuple -> convertTupleToMap(tuple, selections)).collect(Collectors.toList());
    }

    private Map<String, Object> convertTupleToMap(Tuple tuple, Iterable<Selection> selections) {
        Map<String, Object> result = new HashMap<>(16);

        for (Selection selection : selections) {
            result.put(selection.getAlias(), tuple.get(selection.getAlias()));
        }

        return result;
    }

    private <S> TypedQuery<S> applyPageable(TypedQuery<S> query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return query;
    }

    private static long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query, "TypedQuery must not be null!");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    private class IdSpecification implements Specification<T> {
        private final ID id;

        IdSpecification(ID id) {
            this.id = id;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = null;
            if (!entityInformation.hasCompositeId()) {
                predicate = cb.equal(root.get(entityInformation.getIdAttribute()), id);
            } else {
                for (String attribute : entityInformation.getIdAttributeNames()) {
                    Object value = entityInformation.getCompositeIdAttributeValue(id, attribute);

                    if (predicate != null) {
                        predicate = cb.and(predicate, cb.equal(root.get(attribute), value));
                    } else {
                        predicate = cb.equal(root.get(attribute), value);
                    }
                }
            }

            return predicate;
        }
    }

    private class IdsSpecification implements Specification<T> {
        private final Iterable<ID> ids;

        IdsSpecification(Iterable<ID> ids) {
            this.ids = ids;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return root.get(entityInformation.getIdAttribute()).in(ids);
        }
    }
}
