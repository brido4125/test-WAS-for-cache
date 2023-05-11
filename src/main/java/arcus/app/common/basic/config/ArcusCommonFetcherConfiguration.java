package arcus.app.common.basic.config;

import com.jam2in.arcus.app.common.aop.ArcusCacheJsonAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import com.jam2in.arcus.app.common.item.ArcusCacheItemJsonFetcher;
import com.jam2in.arcus.app.common.item.ArcusCacheItemUpdateScheduler;
import com.jam2in.arcus.app.common.item.ArcusCacheItemUpdater;
import com.jam2in.arcus.app.common.property.ArcusPropertyJsonFetcher;
import com.jam2in.arcus.app.common.property.ArcusPropertyUpdateScheduler;
import com.jam2in.arcus.app.common.property.ArcusPropertyUpdater;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(ArcusConfiguration.class)
@PropertySource("classpath:arcus-common.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class ArcusCommonFetcherConfiguration {
    private final ArcusConfiguration arcusConfiguration;

    private final ArcusPropertyJsonFetcher arcusPropertyJsonFetcher;
    private final ArcusCacheItemJsonFetcher arcusCacheItemJsonFetcher;

    public ArcusCommonFetcherConfiguration(ArcusConfiguration arcusConfiguration, ArcusPropertyJsonFetcher arcusPropertyJsonFetcher, ArcusCacheItemJsonFetcher arcusCacheItemJsonFetcher) {
        this.arcusConfiguration = arcusConfiguration;
        this.arcusPropertyJsonFetcher = arcusPropertyJsonFetcher;
        this.arcusCacheItemJsonFetcher = arcusCacheItemJsonFetcher;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ArcusCacheJsonAspect arcusCacheJsonAspect() {
        return new ArcusCacheJsonAspect(arcusConfiguration);
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
