package org.python.google.common.eventbus;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.util.concurrent.MoreExecutors;

@Beta
public class EventBus {
   private static final Logger logger = Logger.getLogger(EventBus.class.getName());
   private final String identifier;
   private final Executor executor;
   private final SubscriberExceptionHandler exceptionHandler;
   private final SubscriberRegistry subscribers;
   private final Dispatcher dispatcher;

   public EventBus() {
      this("default");
   }

   public EventBus(String identifier) {
      this(identifier, MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), EventBus.LoggingHandler.INSTANCE);
   }

   public EventBus(SubscriberExceptionHandler exceptionHandler) {
      this("default", MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), exceptionHandler);
   }

   EventBus(String identifier, Executor executor, Dispatcher dispatcher, SubscriberExceptionHandler exceptionHandler) {
      this.subscribers = new SubscriberRegistry(this);
      this.identifier = (String)Preconditions.checkNotNull(identifier);
      this.executor = (Executor)Preconditions.checkNotNull(executor);
      this.dispatcher = (Dispatcher)Preconditions.checkNotNull(dispatcher);
      this.exceptionHandler = (SubscriberExceptionHandler)Preconditions.checkNotNull(exceptionHandler);
   }

   public final String identifier() {
      return this.identifier;
   }

   final Executor executor() {
      return this.executor;
   }

   void handleSubscriberException(Throwable e, SubscriberExceptionContext context) {
      Preconditions.checkNotNull(e);
      Preconditions.checkNotNull(context);

      try {
         this.exceptionHandler.handleException(e, context);
      } catch (Throwable var4) {
         logger.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", var4, e), var4);
      }

   }

   public void register(Object object) {
      this.subscribers.register(object);
   }

   public void unregister(Object object) {
      this.subscribers.unregister(object);
   }

   public void post(Object event) {
      Iterator eventSubscribers = this.subscribers.getSubscribers(event);
      if (eventSubscribers.hasNext()) {
         this.dispatcher.dispatch(event, eventSubscribers);
      } else if (!(event instanceof DeadEvent)) {
         this.post(new DeadEvent(this, event));
      }

   }

   public String toString() {
      return MoreObjects.toStringHelper((Object)this).addValue(this.identifier).toString();
   }

   static final class LoggingHandler implements SubscriberExceptionHandler {
      static final LoggingHandler INSTANCE = new LoggingHandler();

      public void handleException(Throwable exception, SubscriberExceptionContext context) {
         Logger logger = logger(context);
         if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, message(context), exception);
         }

      }

      private static Logger logger(SubscriberExceptionContext context) {
         return Logger.getLogger(EventBus.class.getName() + "." + context.getEventBus().identifier());
      }

      private static String message(SubscriberExceptionContext context) {
         Method method = context.getSubscriberMethod();
         return "Exception thrown by subscriber method " + method.getName() + '(' + method.getParameterTypes()[0].getName() + ')' + " on subscriber " + context.getSubscriber() + " when dispatching event: " + context.getEvent();
      }
   }
}
