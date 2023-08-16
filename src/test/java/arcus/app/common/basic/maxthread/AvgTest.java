package arcus.app.common.basic.maxthread;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AvgTest {
    @Test
    void avgTest() {
        long[] arr = {832, 234, 2000, 323, 255};
        double avg1 = Arrays.stream(arr).average().getAsDouble();
        long[] arr2 = {432, 332, 543, 204, 1800};
        double sum = Arrays.stream(arr2).sum() + avg1;

        double avg2 = sum / 6;

        long l = Arrays.stream(arr).sum() + Arrays.stream(arr2).sum();
        double avg3 = l / 10;

        System.out.println("avg2 = " + avg2);
        System.out.println("avg3 = " + avg3);
    }

    @Test
    void accAvgTest() {
        long[] arr = {832, 234, 2000, 323, 255};
        double avg1 = Arrays.stream(arr).average().getAsDouble();

        long[] arr2 = {432, 332, 543, 204, 1800};

        double avg2 = ((avg1 * 5) + Arrays.stream(arr2).sum()) / 10;

        System.out.println("avg2 = " + avg2);
    }
}
