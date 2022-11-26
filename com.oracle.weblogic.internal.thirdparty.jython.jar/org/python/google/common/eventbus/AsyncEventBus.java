package org.python.google.common.eventbus;

import java.util.concurrent.Executor;
import org.python.google.common.annotations.Beta;

@Beta
public class AsyncEventBus extends EventBus {
   public AsyncEventBus(String identifier, Executor executor) {
      super(identifier, executor, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
   }

   public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
      super("default", executor, Dispatcher.legacyAsync(), subscriberExceptionHandler);
   }

   public AsyncEventBus(Executor executor) {
      super("default", executor, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
   }
}
