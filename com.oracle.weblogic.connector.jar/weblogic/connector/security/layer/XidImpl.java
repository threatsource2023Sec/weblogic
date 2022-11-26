package weblogic.connector.security.layer;

import javax.transaction.xa.Xid;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class XidImpl extends SecureBaseImpl implements Xid {
   public XidImpl(Xid xid, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      super(xid, adapterLayer, "Xid", kernelId);
   }

   public int getFormatId() {
      this.push();

      int var1;
      try {
         var1 = ((Xid)this.myObj).getFormatId();
      } finally {
         this.pop();
      }

      return var1;
   }

   public byte[] getGlobalTransactionId() {
      this.push();

      byte[] var1;
      try {
         var1 = ((Xid)this.myObj).getGlobalTransactionId();
      } finally {
         this.pop();
      }

      return var1;
   }

   public byte[] getBranchQualifier() {
      this.push();

      byte[] var1;
      try {
         var1 = ((Xid)this.myObj).getBranchQualifier();
      } finally {
         this.pop();
      }

      return var1;
   }
}
