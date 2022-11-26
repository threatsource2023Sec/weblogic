package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.util.TimerTask;

class rdXtimer extends TimerTask {
   private rdsession myRdsession;
   private gwatmi myGwatmi;
   private Txid myTxid;
   private TuxXidRply myRplyObj;

   public rdXtimer(rdsession aRdsession, gwatmi aGwatmi, Txid aTxid, TuxXidRply aRplyObj) {
      this.myRdsession = aRdsession;
      this.myGwatmi = aGwatmi;
      this.myTxid = aTxid;
      this.myRplyObj = aRplyObj;
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      TdomTcb fail_tmmsg_tdom = new TdomTcb(12, 0, 0, (String)null);
      fail_tmmsg_tdom.set_diagnostic(13);
      TdomTranTcb fail_tmmsg_tdom_tran = new TdomTranTcb(this.myTxid);
      tfmh fail_tmmsg = new tfmh(1);
      fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
      fail_tmmsg.tdomtran = new tcm((short)10, fail_tmmsg_tdom_tran);
      if (traceEnabled) {
         ntrace.doTrace("/rdsession/rdXtimer/return ROLLBACK message");
      }

      if (this.myRdsession.remove_rplyXidObj(this.myTxid)) {
         this.myRplyObj.add_reply(this.myGwatmi, this.myTxid, fail_tmmsg);
      }

   }
}
