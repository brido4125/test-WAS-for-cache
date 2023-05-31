package arcus.app.common.basic.threadpool;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoreTest {
    @Test
    void coreTest() {
        int core = Runtime.getRuntime().availableProcessors();
        new Thread(() -> {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        int coreAfter = Runtime.getRuntime().availableProcessors();
        Assertions.assertThat(core).isEqualTo(coreAfter);
    }
}
