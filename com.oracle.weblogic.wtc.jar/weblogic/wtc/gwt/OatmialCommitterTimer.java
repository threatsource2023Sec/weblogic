package weblogic.wtc.gwt;

import com.bea.core.jatmi.internal.TCTaskHelper;
import java.util.TimerTask;

class OatmialCommitterTimer extends TimerTask {
   private OatmialCommitter myCommitter;

   public OatmialCommitterTimer(OatmialCommitter c) {
      this.myCommitter = c;
   }

   public void run() {
      TCTaskHelper.schedule(this.myCommitter);
   }
}
