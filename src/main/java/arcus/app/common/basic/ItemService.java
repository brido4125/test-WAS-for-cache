package arcus.app.common.basic;

import com.jam2in.arcus.app.common.aop.ArcusCache;
import com.jam2in.arcus.app.common.aop.ArcusCacheKey;
import com.jam2in.arcus.app.common.key.ArcusCacheKeyDate;
import com.jam2in.arcus.app.common.recaching.ArcusRecachingType;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @ArcusCache(keyDate = ArcusCacheKeyDate.KEY_DATE_DAY, expireTime = "10", recachingType = ArcusRecachingType.SUS)
    public Item findItem(@ArcusCacheKey Long id) {
        System.out.println("ItemService.findItem 로직 호출");
        Item find = itemRepository.findById(id);
        System.out.println("find.getName() = " + find.getName());
        return find;
    }


    public Item saveItem(Item item) {
        itemRepository.save(item);
        return item;
    }
}
