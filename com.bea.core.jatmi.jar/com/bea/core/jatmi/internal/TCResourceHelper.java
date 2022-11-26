package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCResourceService;
import java.io.Serializable;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.TPException;

public class TCResourceHelper {
   private static TCResourceService _res_svc = null;
   public static final String FOREIGN_XID_PROPNAME = "weblogic.transaction.foreignXid";

   public static void initialize(TCResourceService svc) throws TPException {
      if (_res_svc != null) {
         throw new TPException(12, "Resource service already started!");
      } else {
         _res_svc = svc;
         ntrace.doTrace("INFO: TC Resource service instantiated!");
      }
   }

   public static Xid createWLSXid(int formatID, byte[] gtrid, byte[] bqual) {
      return _res_svc.createWLSXid(formatID, gtrid, bqual);
   }

   public static int getWLSFormatID() {
      return _res_svc.getWLSFormatID();
   }

   public static boolean isTightlyCoupledTransactionsEnabled() {
      return _res_svc.isTightlyCoupledTransactionsEnabled();
   }

   public static boolean getParallelXAEnabled() {
      return _res_svc.getParallelXAEnabled();
   }

   public static Serializable getTransactionProperty(Transaction tran, String name) {
      return _res_svc.getTransactionProperty(tran, name);
   }
}
