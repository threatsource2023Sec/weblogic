package weblogic.wtc.jatmi;

import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.CallDescriptorUtil;

public final class ReqOid implements CallDescriptor {
   private CallDescriptor myInternalReqReturn;
   private gwatmi myAtmiUsedToGetIt;
   private Xid myXID;
   private transient int myHashCode;

   public ReqOid(CallDescriptor InternalReqReturn, gwatmi AtmiUsedToGetIt) {
      this.myInternalReqReturn = InternalReqReturn;
      this.myAtmiUsedToGetIt = AtmiUsedToGetIt;
      int reqHash = this.myInternalReqReturn.hashCode();
      int atmiHash = this.myAtmiUsedToGetIt.hashCode();
      if (reqHash <= 65535 && atmiHash <= 65535) {
         this.myHashCode = (atmiHash << 16) + reqHash;
      } else {
         this.myHashCode = atmiHash + reqHash;
      }

   }

   public ReqOid(CallDescriptor InternalReqReturn, gwatmi AtmiUsedToGetIt, Xid currXID) {
      this(InternalReqReturn, AtmiUsedToGetIt);
      this.myXID = currXID;
   }

   public CallDescriptor getReqReturn() {
      return this.myInternalReqReturn;
   }

   public gwatmi getAtmiObject() {
      return this.myAtmiUsedToGetIt;
   }

   public Xid getXID() {
      return this.myXID;
   }

   public boolean equals(Object ct) {
      if (ct == null) {
         return false;
      } else {
         return ct instanceof CallDescriptor ? CallDescriptorUtil.isCallDescriptorEqual(this, (CallDescriptor)ct) : false;
      }
   }

   public int hashCode() {
      return this.myHashCode;
   }

   public String toString() {
      return this.myInternalReqReturn.toString() + "/" + this.myAtmiUsedToGetIt.toString();
   }
}
