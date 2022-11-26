package org.jboss.weld.manager.api;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import org.jboss.weld.bootstrap.api.Service;

public interface ExecutorServices extends Service {
   ExecutorService getTaskExecutor();

   default ScheduledExecutorService getTimerExecutor() {
      return null;
   }

   List invokeAllAndCheckForExceptions(Collection var1);

   List invokeAllAndCheckForExceptions(TaskFactory var1);

   public interface TaskFactory {
      List createTasks(int var1);
   }
}
