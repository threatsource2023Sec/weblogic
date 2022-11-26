package weblogic.security.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import weblogic.utils.collections.CircularQueue;

public final class Pool {
   private static boolean DEBUG = false;
   public static final String PROP_IDLE_TIMEOUT = "weblogic.security.pool.idle.timeout";
   private static final long NO_IDLE_TIMEOUT = -1L;
   private static final long DEFAULT_IDLE_TIMEOUT = 500000L;
   private static long idleTimeout = -1L;
   private final Factory factory;
   private final CircularQueue queue;
   private final Delegate delegate;

   private static void debug(String message) {
      System.out.println("" + new Date() + ", DEBUG 25192833: " + message);
   }

   public Pool(Factory factory, int size) {
      this.factory = factory;
      this.queue = new CircularQueue(size, size);
      this.delegate = (Delegate)(idleTimeout == -1L ? new NoIdleTimeoutDelegate() : new IdleTimeoutDelegate());
      if (DEBUG) {
         debug("Pool created " + this);
      }

   }

   public int getCapacity() {
      return this.queue.capacity();
   }

   public Object getInstance() throws InvocationTargetException {
      Object o = this.delegate.getValidInstanceFromQueue();
      return o != null ? o : this.newInstance();
   }

   public Object newInstance() throws InvocationTargetException {
      return this.factory.newInstance();
   }

   public void returnInstance(Object object) {
      if (!this.delegate.returnInstanceToQueue(object)) {
         this.destroyInstance(object);
      }

   }

   public void destroyInstance(Object object) {
      this.factory.destroyInstance(object);
   }

   public void close() {
      synchronized(this.delegate) {
         for(Object obj = this.delegate.getInstanceFromQueue(); obj != null; obj = this.delegate.getInstanceFromQueue()) {
            this.destroyInstance(obj);
         }

      }
   }

   protected void finalize() throws Throwable {
      this.close();
   }

   static {
      idleTimeout = Long.getLong("weblogic.security.pool.idle.timeout", -1L);
      if (DEBUG) {
         debug("ldap connection idle timeout is " + idleTimeout + " milliseconds.");
      }

   }

   private class Wrapper {
      private long timestamp;
      private Object obj;

      Wrapper(Object obj) {
         this.obj = obj;
         this.timestamp = System.currentTimeMillis();
      }

      Object getObject() {
         return this.obj;
      }

      boolean expired() {
         if (Pool.idleTimeout == -1L) {
            return false;
         } else {
            long curentTimestamp = System.currentTimeMillis();
            return curentTimestamp - this.timestamp > Pool.idleTimeout;
         }
      }

      public String toString() {
         return "Object " + this.obj + ", timestamp " + new Date(this.timestamp);
      }
   }

   private class NoIdleTimeoutDelegate implements Delegate {
      private NoIdleTimeoutDelegate() {
      }

      public Object getInstanceFromQueue() {
         synchronized(this) {
            Object o = Pool.this.queue.remove();
            return o;
         }
      }

      public Object getValidInstanceFromQueue() {
         return this.getInstanceFromQueue();
      }

      public boolean returnInstanceToQueue(Object object) {
         synchronized(this) {
            return Pool.this.queue.add(object);
         }
      }

      // $FF: synthetic method
      NoIdleTimeoutDelegate(Object x1) {
         this();
      }
   }

   private class IdleTimeoutDelegate implements Delegate {
      private IdleTimeoutDelegate() {
      }

      public Object getInstanceFromQueue() {
         Wrapper w = null;
         synchronized(this) {
            w = (Wrapper)Pool.this.queue.remove();
         }

         return w != null ? w.getObject() : null;
      }

      public Object getValidInstanceFromQueue() {
         Wrapper w = null;
         synchronized(this) {
            w = (Wrapper)Pool.this.queue.remove();
         }

         while(w != null && w.expired()) {
            if (Pool.DEBUG) {
               Pool.debug("Destroy " + w + " Pool: " + Pool.this);
            }

            Pool.this.destroyInstance(w.getObject());
            synchronized(this) {
               w = (Wrapper)Pool.this.queue.remove();
            }
         }

         if (w != null) {
            if (Pool.DEBUG) {
               Pool.debug("Found " + w + " Pool: " + Pool.this);
            }

            return w.getObject();
         } else {
            return null;
         }
      }

      public boolean returnInstanceToQueue(Object object) {
         synchronized(this) {
            return Pool.this.queue.add(Pool.this.new Wrapper(object));
         }
      }

      // $FF: synthetic method
      IdleTimeoutDelegate(Object x1) {
         this();
      }
   }

   private interface Delegate {
      Object getInstanceFromQueue();

      Object getValidInstanceFromQueue();

      boolean returnInstanceToQueue(Object var1);
   }
}
