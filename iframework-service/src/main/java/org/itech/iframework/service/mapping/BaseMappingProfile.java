package org.itech.iframework.service.mapping;

import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.FieldDefinition;
import com.github.dozermapper.core.loader.api.FieldsMappingOptions;
import org.itech.iframework.domain.data.Stateful;
import org.itech.iframework.domain.model.*;
import org.itech.iframework.service.dto.CommandDTO;

/**
 * BaseMappingProfile
 *
 * @author liuqiang
 */
public class BaseMappingProfile extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(Stateful.class, Stateful.class)
                .exclude("status");

        mapping(Persistable.class, Persistable.class)
                .exclude("id");

        mapping(Optimistic.class, Optimistic.class)
                .exclude("verison");

        mapping(Auditable.class, Auditable.class)
                .exclude("createdOn")
                .exclude("createdById")
                .exclude("createdBy")
                .exclude("lastModifiedOn")
                .exclude("lastModifiedById")
                .exclude("lastModifiedBy");

        mapping(Persistable.class, CommandDTO.class)
                .fields("id", "id", FieldsMappingOptions.oneWay());

        mapping(AbstractEntity.class, CommandDTO.class)
                .fields((new FieldDefinition("id")).accessible(true), "id");

        mapping(AbstractAggregateRoot.class, CommandDTO.class)
                .fields("id", "id", FieldsMappingOptions.oneWay());

        mapping(Optimistic.class, CommandDTO.class)
                .fields("verison", "verison", FieldsMappingOptions.oneWay());

        mapping(Auditable.class, CommandDTO.class)
                .fields("createdOn", "createdOn", FieldsMappingOptions.oneWay())
                .fields("createdById", "createdById", FieldsMappingOptions.oneWay())
                .exclude("createdBy")
                .fields("lastModifiedOn", "lastModifiedOn", FieldsMappingOptions.oneWay())
                .fields("lastModifiedById", "lastModifiedById", FieldsMappingOptions.oneWay())
                .exclude("lastModifiedBy");
    }
}
