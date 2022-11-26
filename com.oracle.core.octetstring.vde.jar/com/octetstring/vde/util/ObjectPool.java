package com.octetstring.vde.util;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ObjectPool {
   private long expirationTime = 30000L;
   private long lastCheckOut = System.currentTimeMillis();
   private Hashtable locked = new Hashtable();
   private Hashtable unlocked = new Hashtable();
   private CleanUpThread cleaner;

   public synchronized void checkIn(Object o) {
      if (o != null) {
         this.locked.remove(o);
         this.unlocked.put(o, new Long(System.currentTimeMillis()));
      }

   }

   public synchronized Object checkOut() throws Exception {
      long now = System.currentTimeMillis();
      this.lastCheckOut = now;
      Object o;
      if (this.unlocked.size() > 0) {
         for(Enumeration e = this.unlocked.keys(); e.hasMoreElements(); o = null) {
            o = e.nextElement();
            if (this.validate(o)) {
               this.unlocked.remove(o);
               this.locked.put(o, new Long(now));
               return o;
            }

            this.unlocked.remove(o);
            this.expire(o);
         }
      }

      o = this.create();
      this.locked.put(o, new Long(now));
      return o;
   }

   public synchronized void cleanUp() {
      long now = System.currentTimeMillis();
      Enumeration e = this.unlocked.keys();

      while(e.hasMoreElements()) {
         Object o = e.nextElement();
         if (now - (Long)this.unlocked.get(o) > this.expirationTime) {
            this.unlocked.remove(o);
            this.expire(o);
            o = null;
         }
      }

   }

   public abstract Object create() throws Exception;

   public abstract void expire(Object var1);

   public void finalize() {
      this.expireAll();
   }

   public void expireAll() {
      Enumeration oe = this.unlocked.keys();

      Object o;
      while(oe.hasMoreElements()) {
         o = oe.nextElement();
         this.expire(o);
      }

      oe = this.locked.keys();

      while(oe.hasMoreElements()) {
         o = oe.nextElement();
         this.expire(o);
      }

   }

   public abstract boolean validate(Object var1);
}
