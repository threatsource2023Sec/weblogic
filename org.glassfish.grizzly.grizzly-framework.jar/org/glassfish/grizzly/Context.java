package org.glassfish.grizzly;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.logging.Logger;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class Context implements AttributeStorage, Cacheable {
   private static final Logger LOGGER = Grizzly.logger(Context.class);
   private static final Processor NULL_PROCESSOR = new NullProcessor();
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(Context.class, 4);
   private Connection connection;
   protected IOEvent ioEvent;
   private Processor processor;
   private final AttributeHolder attributes;
   protected final MinimalisticArrayList lifeCycleListeners;
   protected boolean wasSuspended;
   protected boolean isManualIOEventControl;

   public static Context create(Connection connection) {
      Context context = (Context)ThreadCache.takeFromCache(CACHE_IDX);
      if (context == null) {
         context = new Context();
      }

      context.setConnection(connection);
      return context;
   }

   public static Context create(Connection connection, Processor processor, IOEvent ioEvent, IOEventLifeCycleListener lifeCycleListener) {
      Context context;
      if (processor != null) {
         context = processor.obtainContext(connection);
      } else {
         context = NULL_PROCESSOR.obtainContext(connection);
      }

      context.setIoEvent(ioEvent);
      if (lifeCycleListener != null) {
         context.addLifeCycleListener(lifeCycleListener);
      }

      return context;
   }

   public Context() {
      this.ioEvent = IOEvent.NONE;
      this.lifeCycleListeners = new MinimalisticArrayList(IOEventLifeCycleListener.class, 2);
      this.attributes = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createUnsafeAttributeHolder();
   }

   public void suspend() {
      try {
         int sz = this.lifeCycleListeners.size;
         IOEventLifeCycleListener[] array = (IOEventLifeCycleListener[])this.lifeCycleListeners.array;

         for(int i = 0; i < sz; ++i) {
            array[i].onContextSuspend(this);
         }
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }

      this.wasSuspended = true;
   }

   public void resume() {
      try {
         int sz = this.lifeCycleListeners.size;
         IOEventLifeCycleListener[] array = (IOEventLifeCycleListener[])this.lifeCycleListeners.array;

         for(int i = 0; i < sz; ++i) {
            array[i].onContextResume(this);
         }

      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public void complete(ProcessorResult result) {
      ProcessorExecutor.complete(this, result);
   }

   public boolean wasSuspended() {
      return this.wasSuspended;
   }

   public void setManualIOEventControl() {
      try {
         int sz = this.lifeCycleListeners.size;
         IOEventLifeCycleListener[] array = (IOEventLifeCycleListener[])this.lifeCycleListeners.array;

         for(int i = 0; i < sz; ++i) {
            array[i].onContextManualIOEventControl(this);
         }
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }

      this.isManualIOEventControl = true;
   }

   public boolean isManualIOEventControl() {
      return this.isManualIOEventControl;
   }

   public IOEvent getIoEvent() {
      return this.ioEvent;
   }

   public void setIoEvent(IOEvent ioEvent) {
      this.ioEvent = ioEvent;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public Processor getProcessor() {
      return this.processor;
   }

   public void setProcessor(Processor processor) {
      this.processor = processor;
   }

   public boolean hasLifeCycleListener(IOEventLifeCycleListener listener) {
      return this.lifeCycleListeners.contains(listener);
   }

   public void addLifeCycleListener(IOEventLifeCycleListener listener) {
      this.lifeCycleListeners.add(listener);
   }

   public boolean removeLifeCycleListener(IOEventLifeCycleListener listener) {
      return this.lifeCycleListeners.remove(listener);
   }

   public void removeAllLifeCycleListeners() {
      this.lifeCycleListeners.clear();
   }

   public AttributeHolder getAttributes() {
      return this.attributes;
   }

   public void reset() {
      this.attributes.recycle();
      this.processor = null;
      this.lifeCycleListeners.clear();
      this.connection = null;
      this.ioEvent = IOEvent.NONE;
      this.wasSuspended = false;
      this.isManualIOEventControl = false;
   }

   public void recycle() {
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   protected void release() {
   }

   protected static final class MinimalisticArrayList {
      private Object[] array;
      private int size;

      private MinimalisticArrayList(Class clazz, int initialCapacity) {
         this.array = (Object[])((Object[])Array.newInstance(clazz, initialCapacity));
      }

      public void add(Object listener) {
         this.ensureCapacity();
         this.array[this.size++] = listener;
      }

      public boolean contains(Object listener) {
         return this.indexOf(listener) != -1;
      }

      private boolean remove(Object listener) {
         int idx = this.indexOf(listener);
         if (idx == -1) {
            return false;
         } else {
            if (idx < this.size - 1) {
               System.arraycopy(this.array, idx + 1, this.array, idx, this.size - idx - 1);
            }

            this.array[--this.size] = null;
            return true;
         }
      }

      public void copyFrom(MinimalisticArrayList list) {
         if (list.size > this.array.length) {
            this.array = Arrays.copyOf(list.array, list.size);
            this.size = list.size;
         } else {
            System.arraycopy(list.array, 0, this.array, 0, list.size);

            for(int i = list.size; i < this.size; ++i) {
               this.array[i] = null;
            }

            this.size = list.size;
         }

      }

      public int size() {
         return this.size;
      }

      public Object[] array() {
         return this.array;
      }

      public void clear() {
         for(int i = 0; i < this.size; ++i) {
            this.array[i] = null;
         }

         this.size = 0;
      }

      private int indexOf(Object listener) {
         for(int i = 0; i < this.size; ++i) {
            if (this.array[i].equals(listener)) {
               return i;
            }
         }

         return -1;
      }

      private void ensureCapacity() {
         if (this.size == this.array.length) {
            this.array = Arrays.copyOf(this.array, this.size + 2);
         }

      }

      // $FF: synthetic method
      MinimalisticArrayList(Class x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class NullProcessor implements Processor {
      private NullProcessor() {
      }

      public Context obtainContext(Connection connection) {
         Context context = Context.create(connection);
         context.setProcessor(this);
         return context;
      }

      public ProcessorResult process(Context context) {
         return ProcessorResult.createNotRun();
      }

      public void read(Connection connection, CompletionHandler completionHandler) {
         throw new UnsupportedOperationException("Not supported.");
      }

      public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler) {
         throw new UnsupportedOperationException("Not supported.");
      }

      public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, MessageCloner messageCloner) {
         throw new UnsupportedOperationException("Not supported yet.");
      }

      /** @deprecated */
      @Deprecated
      public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
         throw new UnsupportedOperationException("Not supported.");
      }

      public boolean isInterested(IOEvent ioEvent) {
         return true;
      }

      public void setInterested(IOEvent ioEvent, boolean isInterested) {
      }

      // $FF: synthetic method
      NullProcessor(Object x0) {
         this();
      }
   }
}
