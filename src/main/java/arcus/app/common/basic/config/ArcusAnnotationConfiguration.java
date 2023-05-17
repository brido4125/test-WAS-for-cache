package arcus.app.common.basic.config;

import arcus.app.common.basic.config.aspect.ArcusAnnotationAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//
@Configuration
@Import(ArcusConfiguration.class)
@PropertySource("classpath:arcus.properties")
@EnableAspectJAutoProxy
public class ArcusAnnotationConfiguration {
    private final ArcusConfiguration arcusConfiguration;

    public ArcusAnnotationConfiguration(ArcusConfiguration arcusConfiguration) {
        this.arcusConfiguration = arcusConfiguration;
    }

    @Bean
    public ArcusAnnotationAspect arcusAnnotationAspect() {
        return new ArcusAnnotationAspect(arcusConfiguration);
    }
}
