package arcus.app.common.basic;

import arcus.app.common.basic.timelog.Trace;
import com.jam2in.arcus.app.common.aop.ArcusCache;
import com.jam2in.arcus.app.common.aop.ArcusCacheKey;
import com.jam2in.arcus.app.common.key.ArcusCacheKeyDate;
import com.jam2in.arcus.app.common.recaching.ArcusRecachingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Trace
    @ArcusCache(keyDate = ArcusCacheKeyDate.KEY_DATE_DAY, expireTime = "30", recachingType = ArcusRecachingType.SUS)
    public Item findItem(@ArcusCacheKey Long id) {
      return itemRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    @Transactional
    public Item saveItem(Item item) {
        itemRepository.save(item);
        return item;
    }
}
