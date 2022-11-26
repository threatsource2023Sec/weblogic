package weblogic.messaging.runtime;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public abstract class CursorDelegate implements TimerListener {
   protected String handle;
   protected CursorRuntimeImpl runtimeDelegate;
   protected OpenDataConverter openDataConverter;
   protected long startPosition;
   protected long endPosition;
   private long timeout;
   private long lastAccessTime;
   private static long counter;
   private TimerManager timerManager;

   public CursorDelegate(CursorRuntimeImpl runtimeDelegate, OpenDataConverter converter, int timeout) {
      this.runtimeDelegate = runtimeDelegate;
      this.openDataConverter = converter;
      this.timeout = (long)timeout * 1000L;
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      if (timeout > 0) {
         this.timerManager.schedule(this, this.timeout);
      }

   }

   public Long getCursorStartPosition() {
      this.updateAccessTime();
      return new Long(this.startPosition);
   }

   public Long getCursorEndPosition() {
      this.updateAccessTime();
      return new Long(this.endPosition);
   }

   public abstract CompositeData[] getNext(int var1) throws OpenDataException;

   public abstract CompositeData[] getPrevious(int var1) throws OpenDataException;

   public abstract CompositeData[] getItems(long var1, int var3) throws OpenDataException;

   public abstract Long getCursorSize();

   public void close() {
      this.runtimeDelegate.removeCursorDelegate(this.handle);
   }

   protected void setHandle(String handle) {
      this.handle = handle;
   }

   public String getHandle() {
      return this.handle;
   }

   protected synchronized void updateAccessTime() {
      this.lastAccessTime = System.currentTimeMillis();
   }

   protected synchronized long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public void timerExpired(Timer timer) {
      long now = System.currentTimeMillis();
      long diff = now - this.getLastAccessTime();
      if (diff > this.timeout) {
         this.close();
      } else {
         long newDelay = this.timeout - diff;
         this.timerManager.schedule(this, newDelay);
      }

   }
}
