package weblogic.wtc.jatmi;

public final class ReqMsg {
   private ReqOid myReqOid;
   private tfmh myReply;

   public ReqMsg(ReqOid aReqOid, tfmh tmmsg) {
      this.myReqOid = aReqOid;
      this.myReply = tmmsg;
   }

   public ReqOid getReqOid() {
      return this.myReqOid;
   }

   public tfmh getReply() {
      return this.myReply;
   }
}
