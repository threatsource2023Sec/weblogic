package weblogic.wtc.jatmi;

public final class ReqXidOid {
   private Txid myGlobalXid;
   private gwatmi myAtmiUsedToGetIt;

   public ReqXidOid(Txid txid, gwatmi AtmiUsedToGetIt) {
      this.myGlobalXid = txid;
      this.myAtmiUsedToGetIt = AtmiUsedToGetIt;
   }

   public Txid getReqXidReturn() {
      return this.myGlobalXid;
   }

   public gwatmi getAtmiObject() {
      return this.myAtmiUsedToGetIt;
   }

   public boolean equals(Object ct) {
      if (ct == null) {
         return false;
      } else {
         ReqXidOid rct = (ReqXidOid)ct;
         return rct.getAtmiObject().equals(this.myAtmiUsedToGetIt) && rct.getReqXidReturn().equals(this.myGlobalXid);
      }
   }

   public int hashCode() {
      return this.myGlobalXid.hashCode() * this.myAtmiUsedToGetIt.hashCode();
   }

   public String toString() {
      return this.myAtmiUsedToGetIt.toString();
   }
}
