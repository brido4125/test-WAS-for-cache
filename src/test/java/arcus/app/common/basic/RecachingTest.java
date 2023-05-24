package arcus.app.common.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecachingTest {


    @Autowired
    ItemController itemController;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepository();
        itemService = new ItemService(itemRepository);
        itemController = new ItemController(itemService);

        itemController.home();
    }

//    @Test
//    @DisplayName("캐시 없이 데이터 조회")
//    void testNoCaching() {
//        String item = itemController.getItem(1L);
//        Assertions.assertEquals("pro", item);
//    }
//
//    @Test
//    @DisplayName("캐시 데이터 조회")
//    void testCaching() {
//        String item1 = itemController.getItem(1L);
//        String item2 = itemController.getItem(1L);
//        String item3 = itemController.getItem(1L);
//        Assertions.assertEquals("pro", item1);
//        Assertions.assertEquals("pro", item2);
//        Assertions.assertEquals("pro", item3);
//
//    }
}
