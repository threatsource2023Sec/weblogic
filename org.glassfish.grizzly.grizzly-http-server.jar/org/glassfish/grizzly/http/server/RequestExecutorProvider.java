package org.glassfish.grizzly.http.server;

import java.util.concurrent.Executor;
import org.glassfish.grizzly.threadpool.Threads;

public interface RequestExecutorProvider {
   Executor getExecutor(Request var1);

   public static class WorkerThreadProvider implements RequestExecutorProvider {
      public Executor getExecutor(Request request) {
         return !Threads.isService() ? null : request.getContext().getConnection().getTransport().getWorkerThreadPool();
      }
   }

   public static class SameThreadProvider implements RequestExecutorProvider {
      public Executor getExecutor(Request request) {
         return null;
      }
   }
}
