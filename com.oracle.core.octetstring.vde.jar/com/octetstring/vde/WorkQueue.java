package com.octetstring.vde;

import com.octetstring.vde.util.Logger;
import java.util.LinkedList;

public class WorkQueue {
   private LinkedList wqv = null;

   public WorkQueue() {
      this.wqv = new LinkedList();
   }

   public void addItem(WorkQueueItem wqi) {
      synchronized(this.wqv) {
         this.wqv.add(wqi);
      }

      synchronized(this) {
         this.notify();
      }
   }

   public WorkQueueItem nextItem() {
      synchronized(this) {
         if (this.wqv.isEmpty()) {
            try {
               this.wait();
            } catch (InterruptedException var6) {
               Logger.getInstance().printStackTrace(var6);
            }
         }
      }

      synchronized(this.wqv) {
         if (!this.wqv.isEmpty()) {
            WorkQueueItem wqi = (WorkQueueItem)this.wqv.getFirst();
            this.wqv.removeFirst();
            return wqi;
         } else {
            return null;
         }
      }
   }
}
