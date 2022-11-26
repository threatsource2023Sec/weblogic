package weblogic.wtc.jatmi;

import com.bea.core.jatmi.internal.TCTaskHelper;
import java.util.TimerTask;

class rdtimer extends TimerTask {
   private rdsession myRdsession;
   private gwatmi myGwatmi;
   private SessionAcallDescriptor myReqid;
   private ReplyQueue myRplyObj;

   public rdtimer(rdsession aRdsession, gwatmi aGwatmi, SessionAcallDescriptor aReqid, ReplyQueue aRplyObj) {
      this.myRdsession = aRdsession;
      this.myGwatmi = aGwatmi;
      this.myReqid = aReqid;
      this.myRplyObj = aRplyObj;
   }

   public void run() {
      TdomTcb fail_tmmsg_tdom = new TdomTcb(3, this.myReqid.getCd(), 4194304, (String)null);
      fail_tmmsg_tdom.set_diagnostic(13);
      tfmh fail_tmmsg = new tfmh(1);
      fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
      Object[] myValue;
      if ((myValue = this.myRdsession.removeReplyObj(this.myReqid)) != null) {
         GatewayTpacallAsyncReply callBack = (GatewayTpacallAsyncReply)myValue[2];
         if (this.myReqid.hasCallBack() && callBack != null) {
            TpacallAsyncExecute tae = new TpacallAsyncExecute(this.myGwatmi, fail_tmmsg, this.myReqid, callBack);
            TCTaskHelper.schedule(tae);
         } else {
            this.myRplyObj.add_reply(this.myGwatmi, this.myReqid, fail_tmmsg);
         }
      }

   }
}
