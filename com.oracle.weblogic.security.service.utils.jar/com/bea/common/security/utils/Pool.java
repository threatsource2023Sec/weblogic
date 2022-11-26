package com.bea.common.security.utils;

import java.lang.reflect.InvocationTargetException;
import weblogic.utils.collections.CircularQueue;

public final class Pool {
   private final Factory factory;
   private final CircularQueue queue;

   public Pool(Factory factory, int size) {
      this.factory = factory;
      this.queue = new CircularQueue(size, size);
   }

   public int getCapacity() {
      return this.queue.capacity();
   }

   public Object getInstance() throws InvocationTargetException {
      Object o;
      synchronized(this) {
         o = this.queue.remove();
      }

      return o != null ? o : this.newInstance();
   }

   public Object newInstance() throws InvocationTargetException {
      return this.factory.newInstance();
   }

   public void returnInstance(Object object) {
      synchronized(this) {
         if (this.queue.add(object)) {
            return;
         }
      }

      this.destroyInstance(object);
   }

   public void destroyInstance(Object object) {
      this.factory.destroyInstance(object);
   }

   public synchronized void close() {
      while(!this.queue.isEmpty()) {
         this.destroyInstance(this.queue.remove());
      }

   }

   protected void finalize() throws Throwable {
      this.close();
   }
}
