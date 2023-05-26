package arcus.app.common.basic;

import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable("id") Long id) {
        Item item = itemService.findItem(id);
        System.out.println("item = " + item.toString());
        return item.getName();
    }
}
