package weblogic.transaction;

import javax.transaction.xa.Xid;
import weblogic.transaction.internal.XidImpl;

public class XIDFactory {
   private static boolean set = false;
   private static int id = 48801;

   public static final Xid createXID(byte[] aGlobalTransactionId, byte[] aBranchQualifier) {
      return aBranchQualifier != null && aBranchQualifier.length != 0 ? new XidImpl(aGlobalTransactionId, aBranchQualifier) : new XidImpl(aGlobalTransactionId);
   }

   public static Xid createXID(int aFormatId, byte[] aGlobalTransactionId, byte[] aBranchQualifier) {
      return new XidImpl(aFormatId, aGlobalTransactionId, aBranchQualifier);
   }

   public static Xid createXID(String branch) {
      if (branch != null && !branch.equals("")) {
         WLXid tmpXid = XidImpl.create();
         return new XidImpl(tmpXid.getFormatId(), tmpXid.getGlobalTransactionId(), tmpXid.getTruncatedBranchQualifier(branch));
      } else {
         throw new AssertionError("Branch should not be empty.");
      }
   }

   public static String xidToString(Xid xid, boolean includeBranchQualifier) {
      return xid instanceof XidImpl ? ((XidImpl)xid).toString(includeBranchQualifier) : xid.toString();
   }

   public static int getFormatId() {
      if (!set) {
         set = true;

         try {
            Class.forName("weblogic.transaction.ClientTxHelper");
            id = 48801;
         } catch (Exception var1) {
            id = 781006;
         }
      }

      return id;
   }
}
