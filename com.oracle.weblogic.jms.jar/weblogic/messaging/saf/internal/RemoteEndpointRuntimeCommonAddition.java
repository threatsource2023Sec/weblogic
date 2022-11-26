package weblogic.messaging.saf.internal;

import java.util.Date;
import weblogic.messaging.saf.OperationState;

public final class RemoteEndpointRuntimeCommonAddition {
   private long downtimeHigh;
   private long downtimeTotal;
   private long uptimeHigh;
   private long uptimeTotal;
   private long lastTimeDisconnected;
   private long lastTimeConnected;
   private boolean connected;
   private Exception lastException;
   private OperationState expireAllState;

   public RemoteEndpointRuntimeCommonAddition() {
      long now = System.currentTimeMillis();
      this.lastTimeConnected = now;
      this.lastTimeDisconnected = now - 1L;
      this.connected = true;
      this.expireAllState = OperationState.COMPLETED;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public synchronized void updateLastTimeConnected(long now) {
      if (this.lastTimeConnected == 0L) {
         this.lastTimeConnected = now;
      } else {
         if (this.lastTimeConnected <= this.lastTimeDisconnected) {
            this.lastTimeConnected = now;
         }

      }
   }

   public synchronized void updateLastTimeDisconnected(long now, Exception exception) {
      this.lastException = exception;
      if (this.lastTimeDisconnected == 0L) {
         this.lastTimeDisconnected = now;
      } else {
         if (this.lastTimeDisconnected <= this.lastTimeConnected) {
            this.lastTimeDisconnected = now;
         }

      }
   }

   public void connected() {
      long now = System.currentTimeMillis();
      synchronized(this) {
         if (!this.connected) {
            this.lastException = null;
            this.connected = true;
            this.updateLastTimeConnected(now);
            if (this.downtimeHigh < this.lastTimeConnected - this.lastTimeDisconnected) {
               this.downtimeHigh = this.lastTimeConnected - this.lastTimeDisconnected;
            }

            this.downtimeTotal += this.lastTimeConnected - this.lastTimeDisconnected;
         }
      }
   }

   public void disconnected(Exception exception) {
      long now = System.currentTimeMillis();
      synchronized(this) {
         if (this.connected) {
            this.connected = false;
            this.updateLastTimeDisconnected(now, exception);
            if (this.uptimeHigh < this.lastTimeDisconnected - this.lastTimeConnected) {
               this.uptimeHigh = this.lastTimeDisconnected - this.lastTimeConnected;
            }

            this.uptimeTotal += this.lastTimeDisconnected - this.lastTimeConnected;
         }
      }
   }

   public long getDowntimeHigh() {
      long now = System.currentTimeMillis();
      synchronized(this) {
         return !this.connected && this.downtimeHigh < now - this.lastTimeDisconnected ? (now - this.lastTimeDisconnected) / 1000L : this.downtimeHigh / 1000L;
      }
   }

   public long getDowntimeTotal() {
      long now = System.currentTimeMillis();
      synchronized(this) {
         return !this.connected ? (this.downtimeTotal + now - this.lastTimeDisconnected) / 1000L : this.downtimeTotal / 1000L;
      }
   }

   public long getUptimeHigh() {
      long now = System.currentTimeMillis();
      synchronized(this) {
         return this.connected && this.uptimeHigh < now - this.lastTimeConnected ? (now - this.lastTimeConnected) / 1000L : this.uptimeHigh / 1000L;
      }
   }

   public long getUptimeTotal() {
      long now = System.currentTimeMillis();
      synchronized(this) {
         return this.connected ? (this.uptimeTotal + now - this.lastTimeConnected) / 1000L : this.uptimeTotal / 1000L;
      }
   }

   public synchronized Date getLastTimeConnected() {
      return new Date(this.lastTimeConnected);
   }

   public synchronized Date getLastTimeFailedToConnect() {
      return new Date(this.lastTimeDisconnected);
   }

   public synchronized Exception getLastException() {
      return this.lastException;
   }

   public OperationState getOperationState() {
      return this.expireAllState;
   }

   public void setOperationState(OperationState state) {
      this.expireAllState = state;
   }
}
