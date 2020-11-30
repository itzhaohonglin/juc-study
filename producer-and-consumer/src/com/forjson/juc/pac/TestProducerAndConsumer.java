package com.forjson.juc.pac;

public class TestProducerAndConsumer {
    public static void main(String[] args) {

        Clerk clerk = new Clerk();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                clerk.produce();
            }
        }, "生产者A").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                clerk.consume();
            }
        }, "消费者A").start();
    }
}

class Clerk {
    private int product = 0;

    public synchronized void produce() {
        while (product >= 10) {
            try {
                System.out.println("库存已满!");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product++;
        System.out.println(Thread.currentThread().getName() + "开始生产. product=" + product);
        this.notifyAll();
    }

    public synchronized void consume() {
        while (product <= 0) {
            try {
                System.out.println("库存不足!");
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        product--;
        System.out.println(Thread.currentThread().getName() + "开始出货. product=" + product);
        this.notifyAll();
    }
}
