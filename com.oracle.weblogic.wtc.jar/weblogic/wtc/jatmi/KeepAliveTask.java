package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.util.TimerTask;

class KeepAliveTask extends TimerTask {
   private dsession myDSession;

   KeepAliveTask(dsession ds) {
      this.myDSession = ds;
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);

      try {
         this.myDSession.sendKeepAliveRequest();
      } catch (Exception var3) {
         if (traceEnabled) {
            var3.printStackTrace();
         }

         ntrace.doTrace("WARN: Keep Alive Task got exception: " + var3.getMessage());
      }

   }
}
