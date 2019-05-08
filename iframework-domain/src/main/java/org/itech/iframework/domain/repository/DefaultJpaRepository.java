package org.itech.iframework.domain.repository;

import org.itech.iframework.domain.dto.DTO;
import org.itech.iframework.domain.query.Selections;
import org.itech.iframework.domain.query.aggregate.Aggregator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Map<String, Object>> findById(String id, Selections selections) {
        return Optional.empty();
    }

    @Override
    public List<Map<String, Object>> findAll(Selections selections) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAllById(Iterable<String> ids, Selections selections) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAll(Sort sort, Selections selections) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> findAll(Pageable pageable, Selections selections) {
        return null;
    }

    @Override
    public Optional<Map<String, Object>> findOne(Specification<T> spec, Selections selections) {
        return Optional.empty();
    }

    @Override
    public List<Map<String, Object>> findAll(Specification<T> spec, Selections selections) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> findAll(Specification<T> spec, Pageable pageable, Selections selections) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAll(Specification<T> spec, Sort sort, Selections selections) {
        return null;
    }

    protected <D extends DTO<T>, S extends T> TypedQuery<D> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort, Class<D> dtoClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<D> query = builder.createQuery(dtoClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

        query.select(builder.construct(dtoClass, getSelections(dtoClass)));

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(em.createQuery(query));
    }

    private <D extends DTO<T>> Selection[] getSelections(Class<D> dtoClass) {
        return new Selection[0];
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
