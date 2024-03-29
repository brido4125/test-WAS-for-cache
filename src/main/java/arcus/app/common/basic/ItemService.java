package arcus.app.common.basic;

import arcus.app.common.basic.timelog.Trace;
import com.jam2in.arcus.app.common.aop.ArcusCache;
import com.jam2in.arcus.app.common.aop.ArcusCacheKey;
import com.jam2in.arcus.app.common.key.ArcusCacheKeyDate;
import com.jam2in.arcus.app.common.recaching.ArcusRecachingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Trace
    @ArcusCache(keyDate = ArcusCacheKeyDate.KEY_DATE_DAY, expireTime = "60", recachingType = ArcusRecachingType.NONE)
    public Item findItem(@ArcusCacheKey Long id) throws InterruptedException {
        Thread.sleep(100);
        return itemRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    @Transactional
    public Item saveItem(Item item) {
        itemRepository.save(item);
        return item;
    }
}
