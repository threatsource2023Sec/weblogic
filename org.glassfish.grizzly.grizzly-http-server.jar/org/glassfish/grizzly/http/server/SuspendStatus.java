package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.ThreadCache;

public final class SuspendStatus {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(SuspendStatus.class, 4);
   private State state;
   private final Thread initThread = Thread.currentThread();

   public static SuspendStatus create() {
      SuspendStatus status = (SuspendStatus)ThreadCache.takeFromCache(CACHE_IDX);
      if (status == null) {
         status = new SuspendStatus();
      }

      assert status.initThread == Thread.currentThread();

      status.state = SuspendStatus.State.NOT_SUSPENDED;
      return status;
   }

   private SuspendStatus() {
   }

   public void suspend() {
      assert Thread.currentThread() == this.initThread;

      if (this.state != SuspendStatus.State.NOT_SUSPENDED) {
         throw new IllegalStateException("Can not suspend. Expected suspend state='" + SuspendStatus.State.NOT_SUSPENDED + "' but was '" + this.state + "'");
      } else {
         this.state = SuspendStatus.State.SUSPENDED;
      }
   }

   boolean getAndInvalidate() {
      assert Thread.currentThread() == this.initThread;

      boolean wasSuspended = this.state == SuspendStatus.State.SUSPENDED;
      this.state = SuspendStatus.State.INVALIDATED;
      ThreadCache.putToCache(this.initThread, CACHE_IDX, this);
      return wasSuspended;
   }

   public void reset() {
      assert Thread.currentThread() == this.initThread;

      this.state = SuspendStatus.State.NOT_SUSPENDED;
   }

   private static enum State {
      NOT_SUSPENDED,
      SUSPENDED,
      INVALIDATED;
   }
}
