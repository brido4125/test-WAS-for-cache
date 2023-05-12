package arcus.app.common.basic.fetcher;

import com.jam2in.arcus.app.common.property.ArcusPropertyJsonFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ArcusPropertyJsonFetcherImpl implements ArcusPropertyJsonFetcher {

    /*
    * 아래의 프로퍼티들은 반영되지 않는다.그러므로 arcus.properties 파일에 설정이 필요하다.
    * address,
    * serviceCode,
    * poolSize,
    * cacheItemsJsonFilePath,
    * expireTimePropertiesFilePath,
    * cronCacheItem,
    * cronProperty
    * */
    @Override
    public String getProperty() {
        return "{“asyncOperationTimeout”:700, " +
                "“globalPrefix”: “RELEASE”, " +
                "“enableCache”:true;}";
    }
}
