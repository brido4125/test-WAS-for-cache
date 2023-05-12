package arcus.app.common.basic.fetcher;

import com.jam2in.arcus.app.common.item.ArcusCacheItemJsonFetcher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArcusCacheItemJsonFetcherImpl implements ArcusCacheItemJsonFetcher {
    @Override
    public List<String> getArcusCacheItems() {
        String cacheItemsJson1 = "{ \"target\": \"org.jam2in.arcus.test.ArcusCacheService.getProduct\", \"expireTime\": \"30\", \"keyParams\": [\"*\"]}";
        String cacheItemsJson2 = "{ \"target\": \"org.jam2in.arcus.test.ArcusCacheService.getDelivery\", \"expireTime\": \"30\", \"keyParams\": [\"*\"]}";
        return new LinkedList<>(Arrays.asList(cacheItemsJson1, cacheItemsJson2));
    }
}
