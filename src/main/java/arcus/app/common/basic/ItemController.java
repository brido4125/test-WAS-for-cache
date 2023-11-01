package arcus.app.common.basic;

import arcus.app.common.basic.timelog.Trace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home() {
        itemService.saveItem(new Item(1L, 1000, "pro"));
        itemService.saveItem(new Item(2L, 2000, "basic"));
        return "data saved";
    }

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Trace
    @GetMapping("/item/{id}")
    public String getItem(@PathVariable("id") Long id) throws InterruptedException {
        Item item = itemService.findItem(id);
        return item.getName();
    }
}
