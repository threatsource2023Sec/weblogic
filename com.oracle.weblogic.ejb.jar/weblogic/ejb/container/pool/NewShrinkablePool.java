package weblogic.ejb.container.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;

public class NewShrinkablePool {
   private static final DebugLogger debugLogger;
   private final AtomicReference head = new AtomicReference();
   private final int initialObjectsInPool;
   private final AtomicInteger capacity;
   private final AtomicInteger currentSize;
   private final AtomicInteger watermark;

   public NewShrinkablePool(int maxsize, int minsize) {
      this.capacity = new AtomicInteger(maxsize);
      this.initialObjectsInPool = minsize;
      this.watermark = new AtomicInteger(minsize);
      this.currentSize = new AtomicInteger(0);
   }

   public void setCapacity(int maxiSize) {
      this.capacity.set(maxiSize);
   }

   public int getCapacity() {
      return this.capacity.get();
   }

   public boolean add(Object item) {
      Node newHead = new Node(item);

      while(this.currentSize.get() < this.capacity.get()) {
         Node oldHead = (Node)this.head.get();
         newHead.next = oldHead;
         if (this.head.compareAndSet(oldHead, newHead)) {
            this.currentSize.getAndIncrement();
            return true;
         }
      }

      return false;
   }

   public Object remove() {
      Node oldHead;
      Node newHead;
      do {
         oldHead = (Node)this.head.get();
         if (oldHead == null) {
            return null;
         }

         newHead = oldHead.next;
      } while(!this.head.compareAndSet(oldHead, newHead));

      int currSize = this.currentSize.decrementAndGet();
      if (currSize < this.watermark.get()) {
         this.watermark.set(currSize);
      }

      return oldHead.item;
   }

   public int size() {
      return this.currentSize.get();
   }

   public List trim(int removeNumber) {
      List toRemove = new ArrayList();
      if (removeNumber > 0) {
         for(int i = 0; i < removeNumber && this.currentSize.get() > this.initialObjectsInPool; ++i) {
            Object element = this.remove();
            if (element == null) {
               break;
            }

            toRemove.add(element);
         }
      }

      return toRemove;
   }

   public boolean isEmpty() {
      return this.head.get() == null;
   }

   List trim(boolean idleTimeout) {
      if (debugLogger.isDebugEnabled()) {
         debug("trimAndResetMark entered.  initialObjectsInPool = " + this.initialObjectsInPool + ", currentSize = " + this.currentSize.get() + ", watermark = " + this.watermark.get());
      }

      if (this.currentSize.get() <= this.initialObjectsInPool) {
         return null;
      } else {
         int currSize = this.currentSize.get();
         int newSize = this.initialObjectsInPool;
         if (idleTimeout) {
            newSize = currSize - this.watermark.get();
         }

         if (newSize < this.initialObjectsInPool) {
            newSize = this.initialObjectsInPool;
         }

         List l = this.trim(currSize - newSize);
         this.watermark.set(newSize);
         if (debugLogger.isDebugEnabled()) {
            debug("trimAndResetMark exiting. new pointer = " + newSize);
         }

         return l;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[FencedPool] " + s);
   }

   static {
      debugLogger = EJBDebugService.poolingLogger;
   }

   static final class Node {
      final Object item;
      Node next;

      public Node(Object item) {
         this.item = item;
      }
   }
}
