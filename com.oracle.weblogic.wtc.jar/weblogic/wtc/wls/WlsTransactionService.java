package weblogic.wtc.wls;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCTransactionService;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import java.io.Serializable;
import java.util.Hashtable;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.wtc.gwt.WTCService;

public final class WlsTransactionService implements TCTransactionService, Serializable {
   private static TransactionLogger tlg = null;
   private static TransactionManager tm = null;
   private static InterposedTransactionManager itm = null;
   private transient WTCXAResource xares = null;
   private static final long serialVersionUID = 4923375075579153141L;

   public WlsTransactionService() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[ WlsTransactionService()");
         if (tlg == null) {
            ntrace.doTrace("tlg == null");
            tm = TxHelper.getTransactionManager();
            tlg = ((ServerTransactionManager)tm).getTransactionLogger();
            itm = TxHelper.getServerInterposedTransactionManager();
         }

         ntrace.doTrace("] WlsTransactionService()/10");
      } else if (tlg == null) {
         tm = TxHelper.getTransactionManager();
         tlg = ((ServerTransactionManager)tm).getTransactionLogger();
         itm = TxHelper.getServerInterposedTransactionManager();
      }

      this.xares = new WTCXAResource(itm.getXAResource());
   }

   public void shutdown(int type) {
   }

   public int getRealTransactionTimeout() {
      Transaction tx = TxHelper.getTransaction();
      return tx != null ? (int)(tx.getTimeToLiveMillis() / 1000L) : -1;
   }

   public void registerResource(String name, XAResource res) throws SystemException {
      String unique_name = name;
      if (WTCService.getRMNameSuffix() != null) {
         unique_name = name + WTCService.getRMNameSuffix();
      }

      Hashtable regProperties;
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[ /WlsTransactionService/registerResource(name = " + unique_name + ", resource = " + res + ")");
         if (unique_name != null) {
            regProperties = new Hashtable();
            regProperties.put("weblogic.transaction.registration.type", "standard");
            regProperties.put("weblogic.transaction.registration.settransactiontimeout", "true");
            regProperties.put("weblogic.transaction.registration.localassignment", "false");
            tm.registerResource(unique_name, res, regProperties);
         }

         ntrace.doTrace("] /WlsTransactionService/registerResource/10");
      } else if (unique_name != null) {
         regProperties = new Hashtable();
         regProperties.put("weblogic.transaction.registration.type", "standard");
         regProperties.put("weblogic.transaction.registration.settransactiontimeout", "true");
         regProperties.put("weblogic.transaction.registration.localassignment", "false");
         tm.registerResource(unique_name, res, regProperties);
      }

   }

   public void unregisterResource(String name) {
      String unique_name = name;
      if (WTCService.getRMNameSuffix() != null) {
         unique_name = name + WTCService.getRMNameSuffix();
      }

      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[ /WlsTransactionService/unregisterResource(name = " + unique_name + ")");
         if (unique_name != null) {
            try {
               tm.unregisterResource(unique_name);
            } catch (SystemException var5) {
            }
         }

         ntrace.doTrace("] /WlsTransactionService/unregisterResource/10");
      } else if (unique_name != null) {
         try {
            tm.unregisterResource(unique_name);
         } catch (SystemException var4) {
         }
      }

   }

   public TuxedoLoggable createTuxedoLoggable() {
      return new WlsTuxedoLoggable();
   }

   public TuxedoLoggable createTuxedoLoggable(Xid xid, int type) {
      return new WlsTuxedoLoggable(xid, type);
   }

   public XAResource getXAResource() {
      this.xares.setXAResource(itm.getXAResource());
      return this.xares;
   }

   public javax.transaction.Transaction getTransaction() {
      return TxHelper.getTransaction();
   }

   public javax.transaction.Transaction getTransaction(Xid xid) {
      return tm.getTransaction(xid);
   }

   public Xid getXidFromTransaction(javax.transaction.Transaction tran) {
      if (tran instanceof Transaction) {
         Transaction myTran = (Transaction)tran;
         return myTran.getXID();
      } else {
         return null;
      }
   }

   public Xid getXidFromThread() {
      Transaction myTran = TxHelper.getTransaction();
      return myTran != null ? myTran.getXID() : null;
   }

   public int getXidFormatId() {
      return 48802;
   }

   public void resumeTransaction(javax.transaction.Transaction tran) throws Exception {
      tm.resume(tran);
   }
}
