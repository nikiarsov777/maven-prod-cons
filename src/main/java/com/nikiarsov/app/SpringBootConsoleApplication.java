package com.nikiarsov.app;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nikiarsov.app.consumers.NumbersConsumer;
import com.nikiarsov.app.producers.NumbersProducer;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner{

    private static Logger LOG = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }
 
    @Override
    public void run(String... args) {
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int poisonPill = Integer.MAX_VALUE;

        BlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<>(BOUND);
        Queue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

        String arg = null;
        if (args.length > 0) {
            arg = args[0];
            System.out.println("arg: " + args[0]);
        }

        if (arg == null || arg.equals("-l") ) {
            for (int i = 1; i < N_PRODUCERS; i++) {
                new Thread(new NumbersProducer(linkedQueue, poisonPill)).start();
            }
            for (int j = 0; j < N_CONSUMERS; j++) {
                new Thread(new NumbersConsumer(linkedQueue, poisonPill)).start();
            }
        } else if(arg.equals("-c")) {
            for (int i = 1; i < N_PRODUCERS; i++) {
                new Thread(new NumbersProducer(concurrentLinkedQueue, poisonPill)).start();
            }

            for (int j = 0; j < N_CONSUMERS; j++) {
                new Thread(new NumbersConsumer(concurrentLinkedQueue, poisonPill)).start();
            }
        }
    }
}