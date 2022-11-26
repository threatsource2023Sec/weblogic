package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.EXid;
import com.bea.core.jatmi.internal.TuxedoXA;
import com.bea.core.jatmi.intf.TCTask;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import java.util.Timer;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import weblogic.wtc.WTCLogger;

class OatmialCommitter implements TCTask {
   private Xid myXid;
   private TuxedoXA myResource;
   private Timer myTimeService;
   private long myTimeLeft;
   private long myLastTime;
   private boolean myRetry;
   private OatmialCommitterTimer myCommitterTimer;
   private String myName;

   public OatmialCommitter(Xid toCommit, TuxedoXA resource, int abandonTimeoutSeconds, Timer aTimer) {
      this.myXid = toCommit;
      this.myResource = resource;
      this.myTimeService = aTimer;
      this.myTimeLeft = (long)(abandonTimeoutSeconds * 1000);
      this.myLastTime = System.currentTimeMillis();
      this.myCommitterTimer = new OatmialCommitterTimer(this);
      this.myRetry = false;
   }

   public int execute() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean commitFailed = false;
      if (traceEnabled) {
         ntrace.doTrace("[/OatmialCommitter/execute/" + Thread.currentThread());
      }

      TuxedoLoggable tl = null;
      if (this.myXid != null && this.myResource != null) {
         try {
            if (this.myXid instanceof EXid) {
               this.myResource.recoveryCommit(((EXid)this.myXid).getXid(), false);
            } else {
               this.myResource.recoveryCommit(this.myXid, false);
            }
         } catch (XAException var6) {
            commitFailed = true;
            WTCLogger.logXAEcommitXid(var6);
         }

         if (this.myXid instanceof EXid) {
            tl = ConfigHelper.removeXidTLogMap(((EXid)this.myXid).getXid(), -1);
         } else {
            tl = ConfigHelper.removeXidTLogMap(this.myXid, -1);
         }

         if (tl != null) {
            tl.forget();
         }

         if (commitFailed) {
            long currentTime = System.currentTimeMillis();
            this.myTimeLeft -= currentTime - this.myLastTime;
            this.myLastTime = currentTime;
            if (this.myTimeLeft < 0L) {
               this.myCommitterTimer.cancel();
               if (traceEnabled) {
                  ntrace.doTrace("/OatmialCommitter/execute/cancelled timer commit retry timer");
               }
            }

            if (!this.myRetry) {
               this.myRetry = true;
               this.myTimeService.schedule(this.myCommitterTimer, 60000L, 60000L);
               if (traceEnabled) {
                  ntrace.doTrace("/OatmialCommitter/execute/scheduled next commit retry");
               }
            }
         } else {
            this.myCommitterTimer.cancel();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/OatmialCommitter/execute/30");
         }

         return 0;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/OatmialCommitter/execute/10");
         }

         return 0;
      }
   }

   public void setTaskName(String tname) {
      this.myName = new String("OatmialCommitter$" + tname);
   }

   public String getTaskName() {
      return this.myName == null ? "OatmialCommitter$unknown" : this.myName;
   }
}
