package org.itech.iframework.domain.util;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.metamodel.Attribute.PersistentAttributeType.*;

/**
 * QueryUtils
 *
 * @author liuqiang
 */
public final class QueryUtils {
    private static final Map<Attribute.PersistentAttributeType, Class<? extends Annotation>> ASSOCIATION_TYPES;

    static {
        Map<Attribute.PersistentAttributeType, Class<? extends Annotation>> persistentAttributeTypes = new HashMap<>();
        persistentAttributeTypes.put(ONE_TO_ONE, OneToOne.class);
        persistentAttributeTypes.put(ONE_TO_MANY, null);
        persistentAttributeTypes.put(MANY_TO_ONE, ManyToOne.class);
        persistentAttributeTypes.put(MANY_TO_MANY, null);
        persistentAttributeTypes.put(ELEMENT_COLLECTION, null);

        ASSOCIATION_TYPES = Collections.unmodifiableMap(persistentAttributeTypes);
    }

    public static Expression<?> toExpressionRecursively(From<?, ?> from, PropertyPath property) {
        return toExpressionRecursively(from, property, false);
    }

    @SuppressWarnings("ConstantConditions")
    static Expression<?> toExpressionRecursively(From<?, ?> from, PropertyPath property, boolean isForSelection) {

        Bindable<?> propertyPathModel;
        Bindable<?> model = from.getModel();
        String segment = property.getSegment();

        if (model instanceof ManagedType) {

            /*
             *  Required to keep support for EclipseLink 2.4.x. TODO: Remove once we drop that (probably Dijkstra M1)
             *  See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=413892
             */
            propertyPathModel = (Bindable<?>) ((ManagedType<?>) model).getAttribute(segment);
        } else {
            propertyPathModel = from.get(segment).getModel();
        }

        if (requiresOuterJoin(propertyPathModel, model instanceof PluralAttribute, !property.hasNext(), isForSelection)
                && !isAlreadyFetched(from, segment)) {
            Join<?, ?> join = getOrCreateJoin(from, segment);
            return (property.hasNext() ? toExpressionRecursively(join, property.next(), isForSelection)
                    : join);
        } else {
            Path<Object> path = from.get(segment);
            return (property.hasNext() ? toExpressionRecursively(path, property.next()) : path);
        }
    }

    public static Join<?, ?> getOrCreateJoin(From<?, ?> from, String attribute) {

        for (Join<?, ?> join : from.getJoins()) {

            boolean sameName = join.getAttribute().getName().equals(attribute);

            if (sameName && join.getJoinType().equals(JoinType.LEFT)) {
                return join;
            }
        }

        return from.join(attribute, JoinType.LEFT);
    }

    @SuppressWarnings("ConstantConditions")
    static Expression<?> toExpressionRecursively(Path<Object> path, PropertyPath property) {

        Path<Object> result = path.get(property.getSegment());
        return property.hasNext() ? toExpressionRecursively(result, property.next()) : result;
    }

    private static boolean requiresOuterJoin(@Nullable Bindable<?> propertyPathModel, boolean isPluralAttribute,
                                             boolean isLeafProperty, boolean isForSelection) {

        if (propertyPathModel == null && isPluralAttribute) {
            return true;
        }

        if (!(propertyPathModel instanceof Attribute)) {
            return false;
        }

        Attribute<?, ?> attribute = (Attribute<?, ?>) propertyPathModel;

        if (!ASSOCIATION_TYPES.containsKey(attribute.getPersistentAttributeType())) {
            return false;
        }

        // if this path is an optional one to one attribute navigated from the not owning side we also need an explicit
        // outer join to avoid https://hibernate.atlassian.net/browse/HHH-12712 and
        // https://github.com/eclipse-ee4j/jpa-api/issues/170
        boolean isInverseOptionalOneToOne = Attribute.PersistentAttributeType.ONE_TO_ONE == attribute.getPersistentAttributeType()
                && StringUtils.hasText(getAnnotationProperty(attribute, "mappedBy", ""));

        // if this path is part of the select list we need to generate an explicit outer join in order to prevent Hibernate
        // to use an inner join instead.
        // see https://hibernate.atlassian.net/browse/HHH-12999.
        if (isLeafProperty && !isForSelection && !attribute.isCollection() && !isInverseOptionalOneToOne) {
            return false;
        }

        return getAnnotationProperty(attribute, "optional", true);
    }


    private static boolean isAlreadyFetched(From<?, ?> from, String attribute) {

        for (Fetch<?, ?> fetch : from.getFetches()) {

            boolean sameName = fetch.getAttribute().getName().equals(attribute);

            if (sameName && fetch.getJoinType().equals(JoinType.LEFT)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getAnnotationProperty(Attribute<?, ?> attribute, String propertyName, T defaultValue) {

        Class<? extends Annotation> associationAnnotation = ASSOCIATION_TYPES.get(attribute.getPersistentAttributeType());

        if (associationAnnotation == null) {
            return defaultValue;
        }

        Member member = attribute.getJavaMember();

        if (!(member instanceof AnnotatedElement)) {
            return defaultValue;
        }

        Annotation annotation = AnnotationUtils.getAnnotation((AnnotatedElement) member, associationAnnotation);
        return annotation == null ? defaultValue : (T) AnnotationUtils.getValue(annotation, propertyName);
    }
}
