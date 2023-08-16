package arcus.app.common.basic.service;


import arcus.app.common.basic.Item;
import arcus.app.common.basic.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemServiceTest {

  @Autowired
  private ItemService itemService;

  @Test
  void updateData() {
    //given
    itemService.saveItem(new Item(1L, 1000, "pro"));

    //when
    Item item1 = itemService.findItem(1L);
    Item item2 = itemService.findItem(1L);

    //then
    assertThat(item1.getName()).isEqualTo("pro");
    assertThat(item1.getName()).isEqualTo(item2.getName());
  }
}
