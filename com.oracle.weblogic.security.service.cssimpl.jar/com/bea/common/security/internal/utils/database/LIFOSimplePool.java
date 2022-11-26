package com.bea.common.security.internal.utils.database;

import java.util.Collection;
import java.util.Stack;

public class LIFOSimplePool {
   private Stack _members = new Stack();
   private int _checkedOut = 0;

   public void fill(Collection objects) {
      synchronized(this._members) {
         Object[] objs = objects.toArray();
         int size = objs.length;

         for(int i = size - 1; i >= 0; --i) {
            this._members.push(objs[i]);
         }

         this._members.notifyAll();
      }
   }

   public void drain() {
      synchronized(this._members) {
         this._members.clear();
      }
   }

   public int getMaxCapacity() {
      synchronized(this._members) {
         return this._members.size() + this._checkedOut;
      }
   }

   public int getCurrentCapacity() {
      synchronized(this._members) {
         return this._members.size();
      }
   }

   public synchronized Object checkout(long timeout_ms) throws InterruptedException {
      if (!this._members.isEmpty()) {
         ++this._checkedOut;
         Object obj = this._members.pop();
         return obj;
      } else {
         while(this._members.isEmpty()) {
            long endTm = System.currentTimeMillis() + timeout_ms;
            this.wait(timeout_ms);
            if (!this._members.isEmpty()) {
               ++this._checkedOut;
               Object obj = this._members.pop();
               return obj;
            }

            long remainingTm = endTm - System.currentTimeMillis();
            if (remainingTm > 0L) {
               timeout_ms = remainingTm;
            } else if (timeout_ms != 0L) {
               return null;
            }
         }

         this.notifyAll();
         return null;
      }
   }

   public Object checkout() throws InterruptedException {
      return this.checkout(0L);
   }

   public synchronized void checkin(Object object) {
      if (object != null) {
         this._members.push(object);
         --this._checkedOut;
         this.notifyAll();
      }
   }
}
