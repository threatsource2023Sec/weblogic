package weblogic.rmi.internal;

import java.rmi.MarshalException;
import java.rmi.RemoteException;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.extensions.server.HeartbeatHelper;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.Work;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class HeartbeatHelperImpl implements HeartbeatHelper, Insecure {
   public static HeartbeatHelper getHeartbeatHelper() {
      return HeartbeatHelperImpl.SingletonMaker.singleton;
   }

   public void ping() throws RemoteException {
   }

   public void ping(FutureResponse response) throws RemoteException {
      ScheduledResponse sr = new ScheduledResponse(response);
      TimerManagerFactory factory = TimerManagerFactory.getTimerManagerFactory();
      factory.getDefaultTimerManager().schedule(sr, (long)this.min(60000, RMIEnvironment.getEnvironment().getHeartbeatPeriodLength()));
   }

   private int min(int a, int b) {
      return a < b ? a : b;
   }

   private class ScheduledResponse implements NakedTimerListener {
      private final FutureResponse response;

      ScheduledResponse(FutureResponse response) {
         this.response = response;
      }

      public final void timerExpired(Timer t) {
         Work work = new WorkAdapter() {
            public void run() {
               try {
                  ScheduledResponse.this.response.send();
               } catch (MarshalException var2) {
                  RMILogger.logHeartbeatPeerClosed();
               } catch (Exception var3) {
                  ScheduledResponse.this.response.sendThrowable(var3);
               }

            }
         };
         WorkManagerFactory.getInstance().getSystem().schedule(work);
      }
   }

   private static class SingletonMaker {
      private static HeartbeatHelperImpl singleton = new HeartbeatHelperImpl();
   }
}
