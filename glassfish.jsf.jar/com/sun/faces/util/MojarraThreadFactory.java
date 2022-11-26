package com.sun.faces.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MojarraThreadFactory implements ThreadFactory {
   static final AtomicInteger poolNumber = new AtomicInteger(1);
   final ThreadGroup group;
   final AtomicInteger threadNumber = new AtomicInteger(1);
   final String namePrefix;

   public MojarraThreadFactory(String factoryName) {
      SecurityManager s = System.getSecurityManager();
      this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
      this.namePrefix = "Mojarra-" + factoryName + '-' + poolNumber.getAndIncrement() + "-thread-";
   }

   public Thread newThread(Runnable r) {
      Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement());
      t.setDaemon(true);
      if (t.getPriority() != 5) {
         t.setPriority(5);
      }

      return t;
   }
}
