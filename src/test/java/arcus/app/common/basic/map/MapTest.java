package arcus.app.common.basic.map;

import com.jam2in.arcus.app.common.recaching.ArcusRecachingTask;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

    @Test
    void mapTest() {
        /*
        * 비어 있는 Map으로 작성해야합
        * */
        Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<>(2));

        set.add("a");
        set.add("b");
        set.add("c");

        set.forEach(System.out::println);
    }

    @Test
    void queueTest() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
        queue.offer("a");
        queue.offer("a");
        queue.offer("a");
        boolean res = queue.offer("a");
        Assertions.assertThat(res).isFalse();
    }
}
