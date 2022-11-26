package com.ziclix.python.sql.util;

import java.util.LinkedList;

public class Queue {
   protected boolean closed;
   protected LinkedList queue;
   protected int capacity;
   protected int threshold;

   public Queue() {
      this(0);
   }

   public Queue(int capacity) {
      this.closed = false;
      this.capacity = capacity;
      this.queue = new LinkedList();
      this.threshold = (int)((float)this.capacity * 0.75F);
   }

   public synchronized void enqueue(Object element) throws InterruptedException {
      if (this.closed) {
         throw new QueueClosedException();
      } else {
         this.queue.addLast(element);
         this.notify();

         while(this.capacity > 0 && this.queue.size() >= this.capacity) {
            this.wait();
            if (this.closed) {
               throw new QueueClosedException();
            }
         }

      }
   }

   public synchronized Object dequeue() throws InterruptedException {
      while(true) {
         if (this.queue.size() <= 0) {
            this.wait();
            if (!this.closed) {
               continue;
            }

            throw new QueueClosedException();
         }

         Object object = this.queue.removeFirst();
         if (this.queue.size() < this.threshold) {
            this.notify();
         }

         return object;
      }
   }

   public synchronized void close() {
      this.closed = true;
      this.notifyAll();
   }
}
