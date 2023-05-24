package arcus.app.common.basic.config;

import arcus.app.common.basic.config.aspect.ArcusJsonAspect;
import com.jam2in.arcus.app.common.config.ArcusConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import(ArcusConfiguration.class)
@PropertySource("classpath:arcus.properties")
@EnableAspectJAutoProxy
public class ArcusJsonConfiguration {
    private final ArcusConfiguration arcusConfiguration;

    public ArcusJsonConfiguration(ArcusConfiguration arcusConfiguration) {
        this.arcusConfiguration = arcusConfiguration;
    }

    @Bean
    public ArcusJsonAspect arcusJsonAspect() {
        return new ArcusJsonAspect(arcusConfiguration);
    }
}
