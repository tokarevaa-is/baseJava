package com.tokarevaa.webapp;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;

    public static void main(String[] args) {
        out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        out.println(mainConcurrency.counter);

        final String o1 = "Object 1";
        final String o2 = "Object 2";
        deadLock(o1, o2);
        deadLock(o2, o1);
    }

    private static void deadLock(Object o1, Object o2) {
        new Thread(() -> {

            synchronized (o1) {
                out.println("Object " + o1 + " locked.");
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    out.println("Object " + o2 + " locked.");
                }
            }
        }).start();
    }

    private synchronized void inc() {
        counter++;
    }
}
