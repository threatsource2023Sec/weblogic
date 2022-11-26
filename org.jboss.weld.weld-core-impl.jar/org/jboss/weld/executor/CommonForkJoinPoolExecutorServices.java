package org.jboss.weld.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import org.jboss.weld.Container;
import org.jboss.weld.bootstrap.api.Environments;

public class CommonForkJoinPoolExecutorServices extends AbstractExecutorServices {
   public ExecutorService getTaskExecutor() {
      return ForkJoinPool.commonPool();
   }

   public void cleanup() {
   }

   protected int getThreadPoolSize() {
      return ForkJoinPool.getCommonPoolParallelism();
   }

   public Collection wrap(Collection tasks) {
      if (!Container.getEnvironment().equals(Environments.SE)) {
         return tasks;
      } else {
         List wrapped = new ArrayList(tasks.size());
         Iterator var3 = tasks.iterator();

         while(var3.hasNext()) {
            Callable task = (Callable)var3.next();
            wrapped.add(() -> {
               ClassLoader oldTccl = Thread.currentThread().getContextClassLoader();
               Thread.currentThread().setContextClassLoader((ClassLoader)null);

               Object var2;
               try {
                  var2 = task.call();
               } finally {
                  Thread.currentThread().setContextClassLoader(oldTccl);
               }

               return var2;
            });
         }

         return wrapped;
      }
   }
}
