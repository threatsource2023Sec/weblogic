package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.ReceivedOneWay;
import weblogic.jms.dotnet.transport.SendHandlerOneWay;
import weblogic.jms.dotnet.transport.ServiceOneWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

class HeartbeatService implements ServiceOneWay, TimerListener {
   static final boolean debug = false;
   private long heartbeatInterval;
   private int allowedMissedBeats;
   private int sentHeartbeatNumber = 0;
   private int recvHeartbeatNumber = -1;
   private int recvHeartbeatNumberOld = -1;
   private int missedHeartbeatCount = 0;
   private Transport transport;
   private Timer heartbeatTimer;
   private RunningState runningState;
   private HeartbeatNumberLock heartbeatNumberLock;
   private MissedHeartbeatLock missedHeartbeatLock;
   private HeartbeatStateLock stateLock;
   private TimerManager timerManager;

   HeartbeatService(int interval, int allowedMiss, Transport transp) {
      this.runningState = HeartbeatService.RunningState.INIT;
      this.heartbeatNumberLock = new HeartbeatNumberLock();
      this.missedHeartbeatLock = new MissedHeartbeatLock();
      this.stateLock = new HeartbeatStateLock();
      this.heartbeatInterval = (long)interval;
      if (this.heartbeatInterval < 1000L) {
         this.heartbeatInterval = 1000L;
      }

      this.allowedMissedBeats = allowedMiss;
      if (this.allowedMissedBeats < 1) {
         this.allowedMissedBeats = 1;
      }

      this.transport = transp;
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   }

   void startHeartbeat() {
      synchronized(this.stateLock) {
         if (this.runningState == HeartbeatService.RunningState.INIT) {
            try {
               this.heartbeatTimer = this.timerManager.scheduleAtFixedRate(this, 0L, this.heartbeatInterval);
            } catch (IllegalStateException var4) {
               this.runningState = HeartbeatService.RunningState.SHUTDOWN;
               return;
            }

            this.runningState = HeartbeatService.RunningState.RUNNING;
         }
      }
   }

   void stopHeartbeat() {
      synchronized(this.stateLock) {
         if (this.runningState == HeartbeatService.RunningState.RUNNING) {
            this.heartbeatTimer.cancel();
         }

         this.runningState = HeartbeatService.RunningState.SHUTDOWN;
      }
   }

   void resetMissCounter() {
      synchronized(this.missedHeartbeatLock) {
         this.missedHeartbeatCount = 0;
      }
   }

   public void timerExpired(Timer t) {
      synchronized(this.heartbeatNumberLock) {
         this.sendHeartbeatMessage();
         this.checkReceivedRemoteHeartbeat();
      }
   }

   private void sendHeartbeatMessage() {
      SendHandlerOneWay sender = this.transport.createOneWay(10001L);
      HeartbeatRequest msg = new HeartbeatRequest(this.sentHeartbeatNumber);
      sender.send(msg);
      ++this.sentHeartbeatNumber;
   }

   private void checkReceivedRemoteHeartbeat() {
      if (this.recvHeartbeatNumber != this.recvHeartbeatNumberOld) {
         this.resetMissCounter();
         this.recvHeartbeatNumberOld = this.recvHeartbeatNumber;
      } else {
         synchronized(this.missedHeartbeatLock) {
            ++this.missedHeartbeatCount;
            if (this.missedHeartbeatCount > this.allowedMissedBeats) {
               TransportError te = new TransportError(new Exception("Closing stale connection:  Missed " + this.missedHeartbeatCount + " heartbeat messages  with a heartbeat interval of " + this.heartbeatInterval + "ms."));
               ((TransportImpl)this.transport).shutdown(te);
            }
         }
      }
   }

   public void invoke(ReceivedOneWay row) {
      MarshalReadable mr = row.getRequest();
      HeartbeatRequest recvMsg = (HeartbeatRequest)mr;
      synchronized(this.heartbeatNumberLock) {
         this.recvHeartbeatNumber = recvMsg.getHeartbeatNumber();
      }
   }

   public void onPeerGone(TransportError te) {
      this.stopHeartbeat();
   }

   public void onShutdown() {
      this.stopHeartbeat();
   }

   public void onUnregister() {
      this.stopHeartbeat();
   }

   private void debug(String str) {
   }

   private static enum RunningState {
      INIT,
      RUNNING,
      SHUTDOWN;
   }
}
