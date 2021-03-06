package com.github.shang.generator;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class InstaIdGeneratorTest {

	 
    static class IdWorkThread implements Runnable {
        private Set<Long> set;
 
        public IdWorkThread(Set<Long> set) {
			super();
			this.set = set;
		}

		@Override
        public void run() {
            while (true) {
                long id=IdGenerator.nextId(ThreadLocalRandom.current().nextLong());
                if (!set.add(id)) {
                    System.out.println("duplicate2:" + id);
                    break;
                }
            }
        }
    }
 
    @Test
    public void test30s() {
        Set<Long> set = new HashSet<Long>();
        Thread t1 = new Thread(new IdWorkThread(set));
        Thread t2 = new Thread(new IdWorkThread(set));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
