package org.python.google.common.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.j2objc.annotations.Weak;

class Subscriber {
   @Weak
   private EventBus bus;
   @VisibleForTesting
   final Object target;
   private final Method method;
   private final Executor executor;

   static Subscriber create(EventBus bus, Object listener, Method method) {
      return (Subscriber)(isDeclaredThreadSafe(method) ? new Subscriber(bus, listener, method) : new SynchronizedSubscriber(bus, listener, method));
   }

   private Subscriber(EventBus bus, Object target, Method method) {
      this.bus = bus;
      this.target = Preconditions.checkNotNull(target);
      this.method = method;
      method.setAccessible(true);
      this.executor = bus.executor();
   }

   final void dispatchEvent(final Object event) {
      this.executor.execute(new Runnable() {
         public void run() {
            try {
               Subscriber.this.invokeSubscriberMethod(event);
            } catch (InvocationTargetException var2) {
               Subscriber.this.bus.handleSubscriberException(var2.getCause(), Subscriber.this.context(event));
            }

         }
      });
   }

   @VisibleForTesting
   void invokeSubscriberMethod(Object event) throws InvocationTargetException {
      try {
         this.method.invoke(this.target, Preconditions.checkNotNull(event));
      } catch (IllegalArgumentException var3) {
         throw new Error("Method rejected target/argument: " + event, var3);
      } catch (IllegalAccessException var4) {
         throw new Error("Method became inaccessible: " + event, var4);
      } catch (InvocationTargetException var5) {
         if (var5.getCause() instanceof Error) {
            throw (Error)var5.getCause();
         } else {
            throw var5;
         }
      }
   }

   private SubscriberExceptionContext context(Object event) {
      return new SubscriberExceptionContext(this.bus, event, this.target, this.method);
   }

   public final int hashCode() {
      return (31 + this.method.hashCode()) * 31 + System.identityHashCode(this.target);
   }

   public final boolean equals(@Nullable Object obj) {
      if (!(obj instanceof Subscriber)) {
         return false;
      } else {
         Subscriber that = (Subscriber)obj;
         return this.target == that.target && this.method.equals(that.method);
      }
   }

   private static boolean isDeclaredThreadSafe(Method method) {
      return method.getAnnotation(AllowConcurrentEvents.class) != null;
   }

   // $FF: synthetic method
   Subscriber(EventBus x0, Object x1, Method x2, Object x3) {
      this(x0, x1, x2);
   }

   @VisibleForTesting
   static final class SynchronizedSubscriber extends Subscriber {
      private SynchronizedSubscriber(EventBus bus, Object target, Method method) {
         super(bus, target, method, null);
      }

      void invokeSubscriberMethod(Object event) throws InvocationTargetException {
         synchronized(this) {
            super.invokeSubscriberMethod(event);
         }
      }

      // $FF: synthetic method
      SynchronizedSubscriber(EventBus x0, Object x1, Method x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
