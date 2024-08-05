package com.nikiarsov.app.producers;
/*** Present a number producer
 * @author Nikolai Arsov
 * @version 1.0
 */
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.nikiarsov.app.helpers.ConsolePrint;
import com.nikiarsov.app.helpers.PrintContext;

public class NumbersProducer implements Runnable {
    private BlockingQueue<Integer> numbersQueue;
    private Queue<Integer> concurrentQueue;
    private final int poisonPill;
    private final int MAX_NUMBERS = 20;
    private boolean isConcurent = false;
    private PrintContext print = new PrintContext(new ConsolePrint());
    
    public NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill) {
        this.numbersQueue = numbersQueue;
        this.poisonPill = poisonPill;
    }

    public NumbersProducer(Queue<Integer> concurrentQueue, int poisonPill) {
        this.concurrentQueue = concurrentQueue;
        this.poisonPill = poisonPill;
        this.isConcurent = true;
    }

    @Override
    public void run() {
        try {
            if (this.isConcurent) {
                generateNumbersConcurent();
            } else {
                generateNumbers();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    
    /** 
     * @throws InterruptedException
     */
    private void generateNumbers() throws InterruptedException {
        for (int i = 1; i <= MAX_NUMBERS; i++) {
            TimeUnit.SECONDS.sleep(1);
            print.executePrint("yellow", " produced number: " + i);
            numbersQueue.put(i);
            // numbersQueue.offer(i, 1000, TimeUnit.MILLISECONDS);
        }

        numbersQueue.put(poisonPill);
     }

     private void generateNumbersConcurent() throws InterruptedException {
        for (int i = 1; i <= MAX_NUMBERS; i++) {
            // TimeUnit.SECONDS.sleep(1);
            concurrentQueue.offer(i);
        }

        concurrentQueue.offer(poisonPill);
     }
}