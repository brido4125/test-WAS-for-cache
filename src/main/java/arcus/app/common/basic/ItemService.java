package arcus.app.common.basic;

import com.jam2in.arcus.app.common.aop.ArcusCache;
import com.jam2in.arcus.app.common.key.ArcusCacheKeyDate;

public class ItemService {
    @ArcusCache(keyDate = ArcusCacheKeyDate.KEY_DATE_DAY)
    public Item getItem() {
        return new Item();
    }


}
