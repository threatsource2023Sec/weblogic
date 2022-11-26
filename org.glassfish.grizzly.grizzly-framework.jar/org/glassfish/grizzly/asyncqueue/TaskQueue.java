package org.glassfish.grizzly.asyncqueue;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.glassfish.grizzly.WriteHandler;

public final class TaskQueue {
   private volatile boolean isClosed;
   private final Queue queue;
   private static final AtomicReferenceFieldUpdater currentElementUpdater = AtomicReferenceFieldUpdater.newUpdater(TaskQueue.class, AsyncQueueRecord.class, "currentElement");
   private volatile AsyncQueueRecord currentElement;
   private static final AtomicIntegerFieldUpdater spaceInBytesUpdater = AtomicIntegerFieldUpdater.newUpdater(TaskQueue.class, "spaceInBytes");
   private volatile int spaceInBytes;
   private final MutableMaxQueueSize maxQueueSizeHolder;
   private static final AtomicIntegerFieldUpdater writeHandlersCounterUpdater = AtomicIntegerFieldUpdater.newUpdater(TaskQueue.class, "writeHandlersCounter");
   private volatile int writeHandlersCounter;
   protected final Queue writeHandlersQueue = new ConcurrentLinkedQueue();

   protected TaskQueue(MutableMaxQueueSize maxQueueSizeHolder) {
      this.maxQueueSizeHolder = maxQueueSizeHolder;
      this.queue = new ConcurrentLinkedQueue();
   }

   public static TaskQueue createTaskQueue(MutableMaxQueueSize maxQueueSizeHolder) {
      return new TaskQueue(maxQueueSizeHolder);
   }

   public int size() {
      return this.spaceInBytes;
   }

   public AsyncQueueRecord poll() {
      AsyncQueueRecord current = (AsyncQueueRecord)currentElementUpdater.getAndSet(this, (Object)null);
      return current != null ? current : (AsyncQueueRecord)this.queue.poll();
   }

   public AsyncQueueRecord peek() {
      AsyncQueueRecord current = this.currentElement;
      if (current == null) {
         current = (AsyncQueueRecord)this.queue.poll();
         if (current != null) {
            this.currentElement = current;
         }
      }

      if (current != null && this.isClosed && currentElementUpdater.compareAndSet(this, current, (Object)null)) {
         current.notifyFailure(new IOException("Connection closed"));
         return null;
      } else {
         return current;
      }
   }

   public int reserveSpace(int amount) {
      return spaceInBytesUpdater.addAndGet(this, amount);
   }

   public int releaseSpace(int amount) {
      return spaceInBytesUpdater.addAndGet(this, -amount);
   }

   public int releaseSpaceAndNotify(int amount) {
      int space = this.releaseSpace(amount);
      this.doNotify();
      return space;
   }

   public int spaceInBytes() {
      return this.spaceInBytes;
   }

   public Queue getQueue() {
      return this.queue;
   }

   public void notifyWritePossible(WriteHandler writeHandler) {
      this.notifyWritePossible(writeHandler, this.maxQueueSizeHolder.getMaxQueueSize());
   }

   public void notifyWritePossible(WriteHandler writeHandler, int maxQueueSize) {
      if (writeHandler != null) {
         if (this.isClosed) {
            writeHandler.onError(new IOException("Connection is closed"));
         } else if (maxQueueSize >= 0 && this.spaceInBytes() >= maxQueueSize) {
            this.offerWriteHandler(writeHandler);
            if (this.spaceInBytes() < maxQueueSize && this.removeWriteHandler(writeHandler)) {
               try {
                  writeHandler.onWritePossible();
               } catch (Throwable var4) {
                  writeHandler.onError(var4);
               }
            } else {
               this.checkWriteHandlerOnClose(writeHandler);
            }

         } else {
            try {
               writeHandler.onWritePossible();
            } catch (Throwable var5) {
               writeHandler.onError(var5);
            }

         }
      }
   }

   public final boolean forgetWritePossible(WriteHandler writeHandler) {
      return this.removeWriteHandler(writeHandler);
   }

   private void checkWriteHandlerOnClose(WriteHandler writeHandler) {
      if (this.isClosed && this.removeWriteHandler(writeHandler)) {
         writeHandler.onError(new IOException("Connection is closed"));
      }

   }

   public void doNotify() {
      if (this.maxQueueSizeHolder != null && this.writeHandlersCounter != 0) {
         int maxQueueSize = this.maxQueueSizeHolder.getMaxQueueSize();

         while(this.spaceInBytes() < maxQueueSize) {
            WriteHandler writeHandler = this.pollWriteHandler();
            if (writeHandler == null) {
               return;
            }

            try {
               writeHandler.onWritePossible();
            } catch (Throwable var4) {
               writeHandler.onError(var4);
            }
         }

      }
   }

   public void setCurrentElement(AsyncQueueRecord task) {
      this.currentElement = task;
      if (task != null && this.isClosed && currentElementUpdater.compareAndSet(this, task, (Object)null)) {
         task.notifyFailure(new IOException("Connection closed"));
      }

   }

   public boolean compareAndSetCurrentElement(AsyncQueueRecord expected, AsyncQueueRecord newValue) {
      if (currentElementUpdater.compareAndSet(this, expected, newValue)) {
         if (newValue != null && this.isClosed && currentElementUpdater.compareAndSet(this, newValue, (Object)null)) {
            newValue.notifyFailure(new IOException("Connection closed"));
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean remove(AsyncQueueRecord task) {
      return this.queue.remove(task);
   }

   public void offer(AsyncQueueRecord task) {
      this.queue.offer(task);
      if (this.isClosed && this.queue.remove(task)) {
         task.notifyFailure(new IOException("Connection closed"));
      }

   }

   public boolean isEmpty() {
      return this.spaceInBytes == 0;
   }

   public void onClose() {
      this.onClose((Throwable)null);
   }

   public void onClose(Throwable cause) {
      this.isClosed = true;
      IOException error = null;
      if (!this.isEmpty()) {
         if (error == null) {
            error = new IOException("Connection closed", cause);
         }

         AsyncQueueRecord record;
         while((record = this.poll()) != null) {
            record.notifyFailure(error);
         }
      }

      WriteHandler writeHandler;
      for(; (writeHandler = this.pollWriteHandler()) != null; writeHandler.onError(error)) {
         if (error == null) {
            error = new IOException("Connection closed", cause);
         }
      }

   }

   private void offerWriteHandler(WriteHandler writeHandler) {
      writeHandlersCounterUpdater.incrementAndGet(this);
      this.writeHandlersQueue.offer(writeHandler);
   }

   private boolean removeWriteHandler(WriteHandler writeHandler) {
      if (this.writeHandlersQueue.remove(writeHandler)) {
         writeHandlersCounterUpdater.decrementAndGet(this);
         return true;
      } else {
         return false;
      }
   }

   private WriteHandler pollWriteHandler() {
      WriteHandler record = (WriteHandler)this.writeHandlersQueue.poll();
      if (record != null) {
         writeHandlersCounterUpdater.decrementAndGet(this);
         return record;
      } else {
         return null;
      }
   }

   public interface MutableMaxQueueSize {
      int getMaxQueueSize();
   }
}
