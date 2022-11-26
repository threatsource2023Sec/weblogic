package org.jboss.weld.bootstrap.events;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jboss.weld.event.ObserverNotifier;
import org.jboss.weld.executor.DaemonThreadFactory;
import org.jboss.weld.util.reflection.ParameterizedTypeImpl;

public class ContainerLifecycleEventPreloader {
   private final ExecutorService executor;
   private final ObserverNotifier notifier;

   public ContainerLifecycleEventPreloader(int threadPoolSize, ObserverNotifier notifier) {
      this.executor = Executors.newFixedThreadPool(threadPoolSize, new DaemonThreadFactory(new ThreadGroup("weld-preloaders"), "weld-preloader-"));
      this.notifier = notifier;
   }

   @SuppressFBWarnings(
      value = {"RV_RETURN_VALUE_IGNORED_BAD_PRACTICE"},
      justification = "We never need to synchronize with the preloader."
   )
   void preloadContainerLifecycleEvent(Class eventRawType, Type... typeParameters) {
      this.executor.submit(new PreloadingTask(new ParameterizedTypeImpl(eventRawType, typeParameters, (Type)null)));
   }

   void shutdown() {
      if (!this.executor.isShutdown()) {
         this.executor.shutdownNow();
      }

   }

   private class PreloadingTask implements Callable {
      private final Type type;

      public PreloadingTask(Type type) {
         this.type = type;
      }

      public Void call() throws Exception {
         ContainerLifecycleEventPreloader.this.notifier.resolveObserverMethods(this.type);
         return null;
      }
   }
}
