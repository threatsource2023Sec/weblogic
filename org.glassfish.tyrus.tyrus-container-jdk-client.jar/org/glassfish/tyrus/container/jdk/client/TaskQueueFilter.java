package org.glassfish.tyrus.container.jdk.client;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.tyrus.spi.CompletionHandler;

class TaskQueueFilter extends Filter {
   private final Queue taskQueue = new ConcurrentLinkedQueue();
   private final AtomicBoolean taskLock = new AtomicBoolean(false);

   TaskQueueFilter(Filter downstreamFilter) {
      super(downstreamFilter);
   }

   void write(ByteBuffer data, CompletionHandler completionHandler) {
      this.taskQueue.offer(new WriteTask(data, completionHandler));
      if (this.taskLock.compareAndSet(false, true)) {
         this.processTask();
      }

   }

   private void processTask() {
      Task task = (Task)this.taskQueue.poll();
      if (task == null) {
         this.taskLock.set(false);
      } else {
         task.execute(this);
      }
   }

   void close() {
      this.taskQueue.offer(new Task() {
         public void execute(TaskQueueFilter queueFilter) {
            if (TaskQueueFilter.this.downstreamFilter != null) {
               TaskQueueFilter.this.downstreamFilter.close();
               TaskQueueFilter.this.upstreamFilter = null;
            }

            TaskQueueFilter.this.processTask();
         }
      });
      if (this.taskLock.compareAndSet(false, true)) {
         this.processTask();
      }

   }

   void startSsl() {
      this.taskQueue.offer(new Task() {
         public void execute(TaskQueueFilter queueFilter) {
            TaskQueueFilter.this.downstreamFilter.startSsl();
         }
      });
      if (this.taskLock.compareAndSet(false, true)) {
         this.processTask();
      }

   }

   void processSslHandshakeCompleted() {
      this.processTask();
   }

   static class WriteTask implements Task {
      private final ByteBuffer data;
      private final CompletionHandler completionHandler;

      WriteTask(ByteBuffer data, CompletionHandler completionHandler) {
         this.data = data;
         this.completionHandler = completionHandler;
      }

      public void execute(final TaskQueueFilter queueFilter) {
         queueFilter.downstreamFilter.write(this.getData(), new CompletionHandler() {
            public void failed(Throwable throwable) {
               WriteTask.this.getCompletionHandler().failed(throwable);
               queueFilter.processTask();
            }

            public void completed(ByteBuffer result) {
               if (result.hasRemaining()) {
                  WriteTask.this.execute(queueFilter);
               } else {
                  WriteTask.this.getCompletionHandler().completed(WriteTask.this.getData());
                  queueFilter.processTask();
               }
            }
         });
      }

      ByteBuffer getData() {
         return this.data;
      }

      CompletionHandler getCompletionHandler() {
         return this.completionHandler;
      }

      public String toString() {
         return "WriteTask{data=" + this.data + ", completionHandler=" + this.completionHandler + '}';
      }
   }

   interface Task {
      void execute(TaskQueueFilter var1);
   }
}
