package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCTransactionService;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.TPException;

public final class TCTransactionHelper {
   private static TCTransactionService _tx_svc = null;
   public static final int WTC_TX_PROVIDER_WLS = 0;
   public static final int WTC_TX_PROVIDER_CE = 1;
   public static final int XID_FORMAT_ID = 48802;
   public static final int WTC_TX_SHUTDOWN_NORMAL = 0;
   public static final int WTC_TX_SHUTDOWN_FORCE = 1;

   public static void initialize(TCTransactionService svc) throws TPException {
      if (_tx_svc != null) {
         throw new TPException(12, "Transaction service already started!");
      } else {
         _tx_svc = svc;
         ntrace.doTrace("INFO: TC Transaction service instantiated!");
      }
   }

   public static TCTransactionService getTransactionService() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCTransactionHelper/getTransactionService()");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCSecurityManager/getSecurityService/" + _tx_svc);
      }

      return _tx_svc;
   }

   public static void shutdown(int type) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCSecurityManager/shutdown(" + type + ")");
      }

      if (_tx_svc != null) {
         if (type < 0 || type > 1) {
            type = 1;
         }

         _tx_svc.shutdown(type);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCSecurityManager/shutdown/10");
      }

   }

   public static int getRealTransactionTimeout() {
      if (_tx_svc != null) {
         int ret;
         return (ret = _tx_svc.getRealTransactionTimeout()) <= 0 ? -1 : ret;
      } else {
         return -1;
      }
   }

   public static void registerResource(XAResource res) throws SystemException {
      if (_tx_svc != null) {
         _tx_svc.registerResource(new String("OatmialResource"), res);
      }

   }

   public static void registerResource(XAResource res, boolean isDummy) throws SystemException {
      if (_tx_svc != null) {
         if (isDummy) {
            _tx_svc.registerResource(new String("OatmialDummyResource"), res);
         } else {
            _tx_svc.registerResource(new String("OatmialResource"), res);
         }
      }

   }

   public static void unregisterResource() {
      if (_tx_svc != null) {
         _tx_svc.unregisterResource(new String("OatmialResource"));
      }

   }

   public static void unregisterResource(boolean isDummy) {
      if (_tx_svc != null) {
         if (isDummy) {
            _tx_svc.unregisterResource(new String("OatmialDummyResource"));
         } else {
            _tx_svc.unregisterResource(new String("OatmialResource"));
         }
      }

   }

   public static TuxedoLoggable createTuxedoLoggable() {
      return _tx_svc != null ? _tx_svc.createTuxedoLoggable() : null;
   }

   public static TuxedoLoggable createTuxedoLoggable(Xid xid, int type) {
      return _tx_svc != null ? _tx_svc.createTuxedoLoggable(xid, type) : null;
   }

   public static XAResource getXAResource() {
      return _tx_svc != null ? _tx_svc.getXAResource() : null;
   }

   public static Transaction getTransaction() {
      return _tx_svc != null ? _tx_svc.getTransaction() : null;
   }

   public static Transaction getTransaction(Xid xid) {
      return _tx_svc != null ? _tx_svc.getTransaction(xid) : null;
   }

   public static Xid getXidFromTransaction(Transaction tran) {
      return _tx_svc != null ? _tx_svc.getXidFromTransaction(tran) : null;
   }

   public static Xid getXidFromThread() {
      return _tx_svc != null ? _tx_svc.getXidFromThread() : null;
   }

   public static int getXidFormatId() {
      return _tx_svc != null ? _tx_svc.getXidFormatId() : '뺢';
   }

   public static void resumeTransaction(Transaction tran) throws Exception {
      if (_tx_svc != null) {
         _tx_svc.resumeTransaction(tran);
      }

   }
}
