package org.jboss.weld.event;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.enterprise.event.NotificationOptions;
import javax.enterprise.event.ObserverException;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.weld.Container;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.events.WeldNotificationOptions;
import org.jboss.weld.events.WeldNotificationOptions.NotificationMode;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.resolution.QualifierInstance;
import org.jboss.weld.resolution.Resolvable;
import org.jboss.weld.resolution.ResolvableBuilder;
import org.jboss.weld.resolution.TypeSafeObserverResolver;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.security.spi.SecurityServices;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.reflection.Reflections;

public class ObserverNotifier {
   private static final RuntimeException NO_EXCEPTION_MARKER = new RuntimeException();
   private final TypeSafeObserverResolver resolver;
   private final SharedObjectCache sharedObjectCache;
   private final boolean strict;
   protected final CurrentEventMetadata currentEventMetadata;
   private final ComputingCache eventTypeCheckCache;
   private final Executor asyncEventExecutor;
   private final ScheduledExecutorService timerExecutor;
   private final SecurityServices securityServices;
   private final LazyValueHolder requestContextHolder;

   protected ObserverNotifier(String contextId, TypeSafeObserverResolver resolver, ServiceRegistry services, boolean strict) {
      this.resolver = resolver;
      this.sharedObjectCache = (SharedObjectCache)services.get(SharedObjectCache.class);
      this.strict = strict;
      this.currentEventMetadata = (CurrentEventMetadata)services.get(CurrentEventMetadata.class);
      if (strict) {
         this.eventTypeCheckCache = ComputingCacheBuilder.newBuilder().build(new EventTypeCheck());
      } else {
         this.eventTypeCheckCache = null;
      }

      this.asyncEventExecutor = (Executor)services.getOptional(ExecutorServices.class).map((e) -> {
         return e.getTaskExecutor();
      }).orElse(ForkJoinPool.commonPool());
      this.timerExecutor = (ScheduledExecutorService)services.getOptional(ExecutorServices.class).map((e) -> {
         return e.getTimerExecutor();
      }).orElse((Object)null);
      this.securityServices = (SecurityServices)services.getRequired(SecurityServices.class);
      this.requestContextHolder = LazyValueHolder.forSupplier(() -> {
         return (RequestContext)Container.instance(contextId).deploymentManager().instance().select(RequestContext.class, new Annotation[]{UnboundLiteral.INSTANCE}).get();
      });
   }

   public ResolvedObservers resolveObserverMethods(Type eventType, Annotation... qualifiers) {
      this.checkEventObjectType(eventType);
      return this.resolveObserverMethods(this.buildEventResolvable(eventType, qualifiers));
   }

   public ResolvedObservers resolveObserverMethods(Type eventType, Set qualifiers) {
      this.checkEventObjectType(eventType);
      return this.resolveObserverMethods(this.buildEventResolvable(eventType, qualifiers));
   }

   public ResolvedObservers resolveObserverMethods(Resolvable resolvable) {
      return (ResolvedObservers)Reflections.cast(this.resolver.resolve(resolvable, true));
   }

   public void fireEvent(Object event, EventMetadata metadata, Annotation... qualifiers) {
      this.fireEvent(event.getClass(), event, metadata, qualifiers);
   }

   public void fireEvent(Type eventType, Object event, Annotation... qualifiers) {
      this.fireEvent(eventType, event, (EventMetadata)null, qualifiers);
   }

   public void fireEvent(Type eventType, Object event, EventMetadata metadata, Annotation... qualifiers) {
      this.checkEventObjectType(eventType);
      this.notify(this.resolveObserverMethods(this.buildEventResolvable(eventType, qualifiers)), event, metadata);
   }

   public void fireEvent(Object event, Resolvable resolvable) {
      this.checkEventObjectType(event);
      this.notify(this.resolveObserverMethods(resolvable), event, (EventMetadata)null);
   }

   protected Resolvable buildEventResolvable(Type eventType, Set qualifiers) {
      Set typeClosure = (Set)this.sharedObjectCache.getTypeClosureHolder(eventType).get();
      return (new ResolvableBuilder(this.resolver.getMetaAnnotationStore())).addTypes(typeClosure).addType(Object.class).addQualifiers((Collection)qualifiers).addQualifierUnchecked(QualifierInstance.ANY).create();
   }

   protected Resolvable buildEventResolvable(Type eventType, Annotation... qualifiers) {
      return (new ResolvableBuilder(this.resolver.getMetaAnnotationStore())).addTypes((Set)this.sharedObjectCache.getTypeClosureHolder(eventType).get()).addType(Object.class).addQualifiers(qualifiers).addQualifierUnchecked(QualifierInstance.ANY).create();
   }

   public void clear() {
      this.resolver.clear();
      if (this.eventTypeCheckCache != null) {
         this.eventTypeCheckCache.clear();
      }

   }

   protected void checkEventObjectType(Object event) {
      this.checkEventObjectType((Type)event.getClass());
   }

   public void checkEventObjectType(Type eventType) {
      if (this.strict) {
         RuntimeException exception = (RuntimeException)this.eventTypeCheckCache.getValue(eventType);
         if (exception != NO_EXCEPTION_MARKER) {
            throw exception;
         }
      }

   }

   public void notify(ResolvedObservers observers, Object event, EventMetadata metadata) {
      if (!observers.isMetadataRequired()) {
         metadata = null;
      }

      this.notifySyncObservers(observers.getImmediateSyncObservers(), event, metadata, ObserverNotifier.ObserverExceptionHandler.IMMEDIATE_HANDLER);
      this.notifyTransactionObservers(observers.getTransactionObservers(), event, metadata, ObserverNotifier.ObserverExceptionHandler.IMMEDIATE_HANDLER);
   }

   protected void notifySyncObservers(List observers, Object event, EventMetadata metadata, ObserverExceptionHandler handler) {
      if (!observers.isEmpty()) {
         ThreadLocalStack.ThreadLocalStackReference stack = this.currentEventMetadata.pushIfNotNull(metadata);

         try {
            Iterator var6 = observers.iterator();

            while(var6.hasNext()) {
               ObserverMethod observer = (ObserverMethod)var6.next();

               try {
                  Observers.notify(observer, event, metadata);
               } catch (Throwable var12) {
                  handler.handle(var12);
               }
            }
         } finally {
            stack.pop();
         }

      }
   }

   protected void notifyTransactionObservers(List observers, Object event, EventMetadata metadata, ObserverExceptionHandler handler) {
      this.notifySyncObservers(observers, event, metadata, ObserverNotifier.ObserverExceptionHandler.IMMEDIATE_HANDLER);
   }

   public CompletionStage notifyAsync(ResolvedObservers observers, Object event, EventMetadata metadata, NotificationOptions options) {
      if (!observers.isMetadataRequired()) {
         metadata = null;
      }

      return this.notifyAsyncObservers(observers.getAsyncObservers(), event, metadata, options.getExecutor(), options);
   }

   protected CompletionStage notifyAsyncObservers(List observers, Object event, EventMetadata metadata, Executor executor, NotificationOptions options) {
      if (executor == null) {
         executor = this.asyncEventExecutor;
      }

      if (observers.isEmpty()) {
         return AsyncEventDeliveryStage.completed(event, executor);
      } else {
         WeldNotificationOptions.NotificationMode mode = this.initModeOption(options.get("weld.async.notification.mode"));
         Long timeout = this.initTimeoutOption(options.get("weld.async.notification.timeout"));
         Consumer securityContextActionConsumer = this.securityServices.getSecurityContextAssociator();
         CollectingExceptionHandler exceptionHandler;
         CompletableFuture completableFuture;
         if (observers.size() > 1 && NotificationMode.PARALLEL.equals(mode)) {
            exceptionHandler = new CollectingExceptionHandler(new CopyOnWriteArrayList());
            List completableFutures = new ArrayList(observers.size());
            Iterator var12 = observers.iterator();

            while(var12.hasNext()) {
               ObserverMethod observer = (ObserverMethod)var12.next();
               completableFutures.add(CompletableFuture.supplyAsync(this.createSupplier(securityContextActionConsumer, event, metadata, exceptionHandler, false, () -> {
                  this.notifyAsyncObserver(observer, event, metadata, exceptionHandler);
               }), executor));
            }

            completableFuture = CompletableFuture.allOf((CompletableFuture[])completableFutures.toArray(new CompletableFuture[0])).thenApply((ignoredVoid) -> {
               this.handleExceptions(exceptionHandler);
               return event;
            });
         } else {
            exceptionHandler = new CollectingExceptionHandler();
            completableFuture = CompletableFuture.supplyAsync(this.createSupplier(securityContextActionConsumer, event, metadata, exceptionHandler, true, () -> {
               Iterator var5 = observers.iterator();

               while(var5.hasNext()) {
                  ObserverMethod observer = (ObserverMethod)var5.next();
                  this.notifyAsyncObserver(observer, event, metadata, exceptionHandler);
               }

            }), executor);
         }

         if (timeout != null) {
            completableFuture = CompletableFuture.anyOf(completableFuture, this.startTimer(timeout)).thenApply((ignoredObject) -> {
               return event;
            });
         }

         return new AsyncEventDeliveryStage(completableFuture, executor);
      }
   }

   private Long initTimeoutOption(Object timeoutOptionValue) {
      if (timeoutOptionValue != null) {
         if (this.timerExecutor == null) {
            throw EventLogger.LOG.noScheduledExecutorServicesProvided();
         } else {
            try {
               String inputValue = timeoutOptionValue.toString();
               return Long.parseLong(inputValue);
            } catch (NumberFormatException var3) {
               throw EventLogger.LOG.invalidInputValueForTimeout(var3);
            }
         }
      } else {
         return null;
      }
   }

   private WeldNotificationOptions.NotificationMode initModeOption(Object value) {
      if (value != null) {
         WeldNotificationOptions.NotificationMode mode = NotificationMode.of(value);
         if (mode == null) {
            throw EventLogger.LOG.invalidNotificationMode(value);
         } else {
            return mode;
         }
      } else {
         return null;
      }
   }

   private CompletableFuture startTimer(Long timeout) {
      CompletableFuture timeoutFuture = new CompletableFuture();
      this.timerExecutor.schedule(() -> {
         return timeoutFuture.completeExceptionally(new TimeoutException());
      }, timeout, TimeUnit.MILLISECONDS);
      return timeoutFuture;
   }

   private void notifyAsyncObserver(ObserverMethod observer, Object event, EventMetadata metadata, ObserverExceptionHandler exceptionHandler) {
      try {
         Observers.notify(observer, event, metadata);
      } catch (Throwable var6) {
         exceptionHandler.handle(var6);
      }

   }

   private Supplier createSupplier(Consumer securityContextActionConsumer, Object event, EventMetadata metadata, ObserverExceptionHandler exceptionHandler, boolean handleExceptions, Runnable notifyAction) {
      return () -> {
         ThreadLocalStack.ThreadLocalStackReference stack = this.currentEventMetadata.pushIfNotNull(metadata);
         RequestContext requestContext = (RequestContext)this.requestContextHolder.get();
         securityContextActionConsumer.accept(() -> {
            try {
               requestContext.activate();
               notifyAction.run();
            } finally {
               stack.pop();
               requestContext.invalidate();
               requestContext.deactivate();
            }

         });
         if (handleExceptions) {
            this.handleExceptions(exceptionHandler);
         }

         return event;
      };
   }

   @SuppressFBWarnings(
      value = {"NP_NONNULL_PARAM_VIOLATION"},
      justification = "https://github.com/findbugsproject/findbugs/issues/79"
   )
   private void handleExceptions(ObserverExceptionHandler handler) {
      List handledExceptions = handler.getHandledExceptions();
      if (!handledExceptions.isEmpty()) {
         CompletionException exception = null;
         if (handledExceptions.size() == 1) {
            exception = new CompletionException((Throwable)handledExceptions.get(0));
         } else {
            exception = new CompletionException((Throwable)null);
         }

         Iterator var4 = handledExceptions.iterator();

         while(var4.hasNext()) {
            Throwable handledException = (Throwable)var4.next();
            exception.addSuppressed(handledException);
         }

         throw exception;
      }
   }

   static class CollectingExceptionHandler implements ObserverExceptionHandler {
      private List throwables;

      CollectingExceptionHandler() {
         this(new LinkedList());
      }

      CollectingExceptionHandler(List throwables) {
         this.throwables = throwables;
      }

      public void handle(Throwable throwable) {
         this.throwables.add(throwable);
      }

      public List getHandledExceptions() {
         return this.throwables;
      }
   }

   protected interface ObserverExceptionHandler {
      ObserverExceptionHandler IMMEDIATE_HANDLER = (throwable) -> {
         if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
         } else if (throwable instanceof Error) {
            throw (Error)throwable;
         } else {
            throw new ObserverException(throwable);
         }
      };

      void handle(Throwable var1);

      default List getHandledExceptions() {
         return Collections.emptyList();
      }
   }

   private static class EventTypeCheck implements Function {
      private EventTypeCheck() {
      }

      public RuntimeException apply(Type eventType) {
         Type resolvedType = Types.getCanonicalType(eventType);
         if (Types.containsTypeVariable(resolvedType)) {
            return UtilLogger.LOG.typeParameterNotAllowedInEventType(eventType);
         } else {
            Class resolvedClass = Reflections.getRawType(eventType);
            Iterator var4 = Observers.CONTAINER_LIFECYCLE_EVENT_CANONICAL_SUPERTYPES.iterator();

            Class containerEventType;
            do {
               if (!var4.hasNext()) {
                  return ObserverNotifier.NO_EXCEPTION_MARKER;
               }

               containerEventType = (Class)var4.next();
            } while(!containerEventType.isAssignableFrom(resolvedClass));

            return UtilLogger.LOG.eventTypeNotAllowed(eventType);
         }
      }

      // $FF: synthetic method
      EventTypeCheck(Object x0) {
         this();
      }
   }
}
