package arcus.app.common.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String home() {
        itemService.saveItem(new Item(1L, 1000, "pro"));
        itemService.saveItem(new Item(2L, 2000, "basic"));
        return "data saved";
    }

    @GetMapping("/item")
    public String getItem(@RequestParam("id") Long id, @RequestParam("recaching") boolean recaching) {
        if (recaching) {
            Item itemWithRecaching = itemService.findItemWithRecaching(id);
            System.out.println("itemWithRecaching = " + itemWithRecaching.toString());
            return itemWithRecaching.getName();
        }
        Item item = itemService.findItem(id);
        System.out.println("item = " + item.toString());
        return item.getName();
    }
}
