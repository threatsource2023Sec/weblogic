package weblogic.timers.internal.commonj;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.timers.TimerManager;
import java.util.Date;

public final class TimerManagerImpl implements TimerManager {
   private weblogic.timers.TimerManager tm;

   public TimerManagerImpl(weblogic.timers.TimerManager tm) {
      this.tm = tm;
   }

   public void suspend() {
      this.tm.suspend();
   }

   public boolean isSuspending() {
      return this.tm.isSuspending();
   }

   public boolean isSuspended() {
      return this.tm.isSuspended();
   }

   public boolean waitForSuspend(long timeout_ms) throws InterruptedException {
      return this.tm.waitForSuspend(timeout_ms);
   }

   public void resume() {
      this.tm.resume();
   }

   public void stop() {
      this.tm.stop();
   }

   public boolean isStopped() {
      return this.tm.isStopped();
   }

   public boolean isStopping() {
      return this.tm.isStopping();
   }

   public boolean waitForStop(long timeout_ms) throws InterruptedException {
      return this.tm.waitForStop(timeout_ms);
   }

   public Timer schedule(TimerListener listener, Date time) {
      return new TimerWrap(listener, this.tm.schedule(new ListenerWrap(listener), (Date)time));
   }

   public Timer schedule(TimerListener listener, long delay) {
      return new TimerWrap(listener, this.tm.schedule(new ListenerWrap(listener), delay));
   }

   public Timer schedule(TimerListener listener, Date firstTime, long period) {
      return new TimerWrap(listener, this.tm.schedule(new ListenerWrap(listener), firstTime, period));
   }

   public Timer schedule(TimerListener listener, long delay, long period) {
      return new TimerWrap(listener, this.tm.schedule(new ListenerWrap(listener), delay, period));
   }

   public Timer scheduleAtFixedRate(TimerListener listener, Date firstTime, long period) {
      return new TimerWrap(listener, this.tm.scheduleAtFixedRate(new ListenerWrap(listener), firstTime, period));
   }

   public Timer scheduleAtFixedRate(TimerListener listener, long delay, long period) {
      return new TimerWrap(listener, this.tm.scheduleAtFixedRate(new ListenerWrap(listener), delay, period));
   }

   public String toString() {
      return this.tm.toString();
   }
}
