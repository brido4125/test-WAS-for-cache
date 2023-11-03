package arcus.app.common.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ItemInitializer {

  private final ItemService itemService;

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
  private static final Random RANDOM = new Random();


//  @PostConstruct
  public void init() {
    for (long i = 1; i < 10000; i++) {
      int price = RANDOM.nextInt(1000) + 10;
      String name = generateRandomString(8);
      itemService.saveItem(new Item(i, price, name));
    }
  }

  private static String generateRandomString(int length) {
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    }
    return builder.toString();
  }
}
