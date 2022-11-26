package weblogic.wtc.jatmi;

import weblogic.kernel.ExecuteRequest;
import weblogic.kernel.ExecuteThread;

public final class MuxableExecute implements ExecuteRequest {
   private rdsession myTuxReadSession;
   private tfmh myTmmsg;

   public MuxableExecute(rdsession readSession) {
      this.myTuxReadSession = readSession;
   }

   public MuxableExecute(rdsession readSession, tfmh tmmsg) {
      this.myTuxReadSession = readSession;
      this.myTmmsg = tmmsg;
   }

   private void prepareForCache() {
      this.myTmmsg = null;
   }

   public void execute(ExecuteThread thd) throws Exception {
      if (this.myTuxReadSession != null && this.myTmmsg != null) {
         this.myTuxReadSession.dispatch(this.myTmmsg);
         this.prepareForCache();
         this.myTuxReadSession.restoreExecuteRequestToCache(this);
      }
   }

   public void setTmmsg(tfmh tmmsg) {
      this.myTmmsg = tmmsg;
   }
}
