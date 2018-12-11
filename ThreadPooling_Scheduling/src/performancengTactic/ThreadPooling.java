package performancengTactic;

import com.util.concurrent.PriorityExecutorService;

import java.util.concurrent.ThreadLocalRandom;

import static com.util.concurrent.Executors.newPriorityFixedThreadPool;


public class ThreadPooling {

    public static void main(String[] args) {

        PriorityExecutorService executor =  newPriorityFixedThreadPool(10);

        for (int i = 100; i < 1000; i++) {
            int priority = ThreadLocalRandom.current().nextInt(1, 11);
            executor.submit(new MaxValue(i, priority), priority);
        }



    }
}
