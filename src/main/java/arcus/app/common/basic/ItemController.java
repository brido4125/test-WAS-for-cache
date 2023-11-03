package arcus.app.common.basic;

import arcus.app.common.basic.timelog.Trace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private static final Random RANDOM = new Random();

    @Trace
    @GetMapping("/item/{id}")
    public String getItem(@PathVariable("id") Long id) throws InterruptedException {
        Item item = itemService.findItem(id);
        return item.getName();
    }

    @GetMapping("/item/high")
    public Item getItemHigh() throws InterruptedException {
        long twenty = (long) RANDOM.nextInt(2000) + 1;
        return itemService.findItem(twenty);
    }

    @GetMapping("/item/low")
    public Item getItemLow() throws InterruptedException {
        long randomId = (long) RANDOM.nextInt(9999) + 1;
        return itemService.findItem(randomId);
    }
}
