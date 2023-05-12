package arcus.app.common.basic.config;

import arcus.app.common.basic.config.aspect.ArcusAnnotationAspect;
import arcus.app.common.basic.config.aspect.ArcusJsonAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import com.jam2in.arcus.app.common.item.ArcusCacheItemJsonFetcher;
import com.jam2in.arcus.app.common.item.ArcusCacheItemUpdateScheduler;
import com.jam2in.arcus.app.common.item.ArcusCacheItemUpdater;
import com.jam2in.arcus.app.common.property.ArcusPropertyJsonFetcher;
import com.jam2in.arcus.app.common.property.ArcusPropertyUpdateScheduler;
import com.jam2in.arcus.app.common.property.ArcusPropertyUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(ArcusConfiguration.class)
@PropertySource("classpath:arcus-common.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class ArcusCommonConfiguration {
    private final ArcusConfiguration arcusConfiguration;

    private final ArcusPropertyJsonFetcher arcusPropertyJsonFetcher;
    private final ArcusCacheItemJsonFetcher arcusCacheItemJsonFetcher;

    public ArcusCommonConfiguration(ArcusConfiguration arcusConfiguration, ArcusPropertyJsonFetcher arcusPropertyJsonFetcher, ArcusCacheItemJsonFetcher arcusCacheItemJsonFetcher) {
        this.arcusConfiguration = arcusConfiguration;
        this.arcusPropertyJsonFetcher = arcusPropertyJsonFetcher;
        this.arcusCacheItemJsonFetcher = arcusCacheItemJsonFetcher;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ArcusAnnotationAspect arcusAnnotationAspect() {
        return new ArcusAnnotationAspect(arcusConfiguration);
    }

    @Bean
    public ArcusJsonAspect arcusJsonAspect() {
        return new ArcusJsonAspect(arcusConfiguration);
    }

    @Bean
    public ArcusPropertyUpdater arcusPropertyUpdater() {
        return new ArcusPropertyUpdater(arcusPropertyJsonFetcher);
    }

    @Bean
    public ArcusPropertyUpdateScheduler arcusPropertyUpdateScheduler() {
        return new ArcusPropertyUpdateScheduler(arcusPropertyUpdater());
    }

    @Bean
    public ArcusCacheItemUpdater arcusCacheItemUpdater() {
        return new ArcusCacheItemUpdater(arcusConfiguration.arcusCacheItemManager(),arcusCacheItemJsonFetcher);
    }

    @Bean
    public ArcusCacheItemUpdateScheduler arcusCacheItemUpdateScheduler() {
        return new ArcusCacheItemUpdateScheduler(arcusCacheItemUpdater());
    }
}
