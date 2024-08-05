package com.nikiarsov.app.consumers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.nikiarsov.app.producers.NumbersProducer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NumbersConsumerTest {

    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(4);

    @BeforeAll
    public void setUp() throws Exception {

        for (int i = 1; i < 4; i++) {
            new Thread(new NumbersProducer(queue, i)).start();
        }
    }

    @Test
    public void run() {
        NumbersConsumer n = new NumbersConsumer(queue, 123);
        n.run();
    }
}
