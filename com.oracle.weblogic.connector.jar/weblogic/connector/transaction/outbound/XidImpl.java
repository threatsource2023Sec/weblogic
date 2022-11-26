package weblogic.connector.transaction.outbound;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.transaction.xa.Xid;

public class XidImpl implements Xid, Externalizable {
   private static final long serialVersionUID = -7374798779597471467L;
   private Xid tmXid;

   public XidImpl() {
   }

   XidImpl(Xid tmXid) {
      this.tmXid = tmXid;
   }

   public boolean equals(Object xid) {
      boolean result = this == xid || xid != null && xid instanceof Xid && this.matchingFormatId((Xid)xid) && this.matchingGlobalTransactionId((Xid)xid) && this.matchingBranchQualifier((Xid)xid);
      return result;
   }

   public int hashCode() {
      byte[] gtrid = this.getGlobalTransactionId();
      byte[] bqual = this.getBranchQualifier();
      int hashcode = 0;
      int i;
      if (gtrid != null) {
         for(i = 0; i < gtrid.length; ++i) {
            hashcode += gtrid[i];
         }
      }

      if (bqual != null) {
         for(i = 0; i < bqual.length; ++i) {
            hashcode += bqual[i];
         }
      }

      return hashcode;
   }

   public byte[] getBranchQualifier() {
      return this.tmXid.getBranchQualifier();
   }

   public int getFormatId() {
      return this.tmXid.getFormatId();
   }

   public byte[] getGlobalTransactionId() {
      return this.tmXid.getGlobalTransactionId();
   }

   public String toString() {
      return this.tmXid.toString();
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      ((weblogic.transaction.internal.XidImpl)this.tmXid).writeExternal(oo);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.tmXid = new weblogic.transaction.internal.XidImpl();
      ((weblogic.transaction.internal.XidImpl)this.tmXid).readExternal(oi);
   }

   private boolean matchingFormatId(Xid xid) {
      return this.getFormatId() == xid.getFormatId();
   }

   private boolean matchingGlobalTransactionId(Xid xid) {
      byte[] gtrid1 = this.getGlobalTransactionId();
      byte[] gtrid2 = xid.getGlobalTransactionId();
      return matchByteArrays(gtrid1, gtrid2);
   }

   private boolean matchingBranchQualifier(Xid xid) {
      byte[] bqual1 = this.getBranchQualifier();
      byte[] bqual2 = xid.getBranchQualifier();
      return matchByteArrays(bqual1, bqual2);
   }

   private static boolean matchByteArrays(byte[] barr1, byte[] barr2) {
      if (barr1 == barr2) {
         return true;
      } else if (barr1 != null && barr2 != null) {
         if (barr1.length != barr2.length) {
            return false;
         } else {
            for(int idx = 0; idx < barr1.length; ++idx) {
               if (barr1[idx] != barr2[idx]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
