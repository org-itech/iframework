package org.itech.iframework.service.mapping;

import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.FieldsMappingOptions;
import org.itech.iframework.domain.model.Auditable;
import org.itech.iframework.domain.model.Optimistic;
import org.itech.iframework.domain.model.Persistable;
import org.itech.iframework.service.dto.CommandDTO;

/**
 * BaseMappingProfile
 *
 * @author liuqiang
 */
public class BaseMappingProfile extends BeanMappingBuilder {
    @Override
    protected void configure() {
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

        mapping(Optimistic.class, CommandDTO.class)
                .fields("verison", "verison", FieldsMappingOptions.oneWay());

        mapping(Auditable.class, CommandDTO.class)
                .fields("createdOn", "createdOn", FieldsMappingOptions.oneWay())
                .fields("createdById", "createdById", FieldsMappingOptions.oneWay())
                .fields("verison", "verison", FieldsMappingOptions.oneWay())
                .fields("verison", "verison", FieldsMappingOptions.oneWay())
                .fields("verison", "verison", FieldsMappingOptions.oneWay())
                .exclude("createdOn")
                .exclude("createdById")
                .exclude("createdBy")
                .exclude("lastModifiedOn")
                .exclude("lastModifiedById")
                .exclude("lastModifiedBy");
    }
}
