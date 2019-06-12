package org.itech.iframework.service.config;

import com.github.dozermapper.core.config.SettingsKeys;
import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import org.itech.iframework.service.mapping.BaseMappingProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * DozerConfig
 *
 * @author liuqiang
 */
public class DozerConfig {
    @Bean
    @Scope(value = "singleton")
    public DozerBeanMapperFactoryBean dozerMapper(ResourcePatternResolver resourcePatternResolver) throws IOException {
        DozerBeanMapperFactoryBean factoryBean = new DozerBeanMapperFactoryBean();

        factoryBean.setMappingFiles(resourcePatternResolver.getResources("classpath*:/*.dozer.xml"));

        System.setProperty(SettingsKeys.PROXY_RESOLVER_BEAN, "com.github.dozermapper.core.util.HibernateProxyResolver");

        return factoryBean;
    }

    @Bean
    public BaseMappingProfile baseMappingProfile() {
        return new BaseMappingProfile();
    }
}
