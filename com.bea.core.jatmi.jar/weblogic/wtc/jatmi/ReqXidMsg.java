package weblogic.wtc.jatmi;

public final class ReqXidMsg {
   private ReqXidOid myReqXidOid;
   private tfmh myReply;

   public ReqXidMsg(ReqXidOid aReqXidOid, tfmh tmmsg) {
      this.myReqXidOid = aReqXidOid;
      this.myReply = tmmsg;
   }

   public ReqXidOid getReqXidOid() {
      return this.myReqXidOid;
   }

   public tfmh getReply() {
      return this.myReply;
   }
}
