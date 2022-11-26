package weblogic.wtc.gwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import weblogic.wtc.config.ConfigObjectBase;

public class WTCMBeanObject implements Serializable {
   static final long serialVersionUID = -5248799212467217180L;
   public static final int WTC_MB_STATE_NEW = 0;
   public static final int WTC_MB_STATE_PREPARED = 1;
   public static final int WTC_MB_STATE_ACTIVATED = 2;
   public static final int WTC_MB_STATE_SUSPENDED = 3;
   public static final int WTC_MB_STATE_DEACTIVATED = 4;
   private int myState = 0;
   protected ReentrantReadWriteLock myLock = new ReentrantReadWriteLock();
   protected Lock r;
   protected Lock w;
   protected ArrayList coArray;
   private int seqno;

   public WTCMBeanObject() {
      this.r = this.myLock.readLock();
      this.w = this.myLock.writeLock();
      this.coArray = null;
      this.seqno = 0;
   }

   public void prepareObject() {
      this.w.lock();
      if (this.myState == 0) {
         this.myState = 1;
      }

      this.w.unlock();
   }

   public void activateObject() {
      this.w.lock();
      if (this.myState == 1) {
         this.myState = 2;
      } else if (this.myState == 3) {
         this.myState = 2;
      }

      this.w.unlock();
   }

   public void suspendObject() {
      this.w.lock();
      if (this.myState == 2) {
         this.myState = 3;
      }

      this.w.unlock();
   }

   public void deactivateObject() {
      this.w.lock();
      if (this.myState == 2) {
         this.myState = 4;
      }

      this.w.unlock();
   }

   public boolean isObjectPrepared() {
      this.r.lock();

      boolean var1;
      try {
         var1 = this.myState == 1;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean isObjectActivated() {
      this.r.lock();

      boolean var1;
      try {
         var1 = this.myState == 2;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean isObjectSuspended() {
      this.r.lock();

      boolean var1;
      try {
         var1 = this.myState == 3;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   public boolean isObjectDeactivated() {
      this.r.lock();

      boolean var1;
      try {
         var1 = this.myState == 4;
      } finally {
         this.r.unlock();
      }

      return var1;
   }

   protected void addConfigObj(ConfigObjectBase co) {
      if (this.coArray == null) {
         this.coArray = new ArrayList();
      }

      this.coArray.add(co);
      co.setLookupId(this.seqno);
      ++this.seqno;
   }

   public void changeConfigObj(ConfigObjectBase fr, ConfigObjectBase co) {
      this.removeConfigObj(fr);
      this.addConfigObj(co);
   }

   protected boolean removeConfigObj(ConfigObjectBase co) {
      return this.coArray != null && co != null ? this.coArray.remove(co) : false;
   }

   public ArrayList getConfigObjList() {
      return this.coArray;
   }
}
