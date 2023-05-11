package arcus.app.common.basic.config;

import com.jam2in.arcus.app.common.aop.ArcusCacheJsonAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(ArcusConfiguration.class)
@PropertySource("classpath:arcus-common.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class ArcusCommonConfiguration {
    private final ArcusConfiguration arcusConfiguration;

    public ArcusCommonConfiguration(ArcusConfiguration arcusConfiguration) {
        this.arcusConfiguration = arcusConfiguration;
    }


    /*
    * For Using Arcus Properties
    * */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ArcusCacheJsonAspect arcusCacheJsonAspect() {
        return new ArcusCacheJsonAspect(arcusConfiguration);
    }



}
