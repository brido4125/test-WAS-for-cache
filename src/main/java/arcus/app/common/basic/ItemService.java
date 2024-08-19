package arcus.app.common.basic;

import com.jam2in.arcus.app.common.aop.ArcusCache;
import com.jam2in.arcus.app.common.aop.ArcusCacheKey;
import com.jam2in.arcus.app.common.key.ArcusCacheKeyDate;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @ArcusCache(keyDate = ArcusCacheKeyDate.KEY_DATE_DAY, expireTime = "10")
    public Item findItem(@ArcusCacheKey Long id) {
      return itemRepository.findById(id).get();
    }

    public Item saveItem(Item item) {
        itemRepository.save(item);
        return item;
    }
}
