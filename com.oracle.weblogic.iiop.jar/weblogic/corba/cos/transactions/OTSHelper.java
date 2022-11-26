package weblogic.corba.cos.transactions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import org.omg.CORBA.Object;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.Status;
import weblogic.corba.j2ee.transaction.Utils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.IIOPService;
import weblogic.iiop.contexts.PropagationContextImpl;
import weblogic.iiop.ior.IOR;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.TransactionSystemException;
import weblogic.transaction.TxHelper;
import weblogic.transaction.internal.XidImpl;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class OTSHelper {
   public static final String OTS_INBOUND_CONTEXT = "weblogic.transaction.ots.inboundContext";
   public static final String OTS_OUTBOUND_CONTEXT = "weblogic.transaction.ots.outboundContext";
   public static final String OTS_MISSING_RESOURCES = "weblogic.transaction.ots.failedResources";
   private static final boolean DEBUG = false;
   private static final DebugCategory debugOTS = Debug.getCategory("weblogic.iiop.ots");
   private static final DebugLogger debugIIOPOTS = DebugLogger.getDebugLogger("DebugIIOPOTS");

   public static final boolean isDebugEnabled() {
      return debugOTS.isEnabled() || debugIIOPOTS.isDebugEnabled();
   }

   public static Transaction importTransaction(PropagationContextImpl pc, int type) throws XAException {
      if ((!pc.isNull() || !pc.isForeign()) && IIOPService.txMechanism != 0) {
         PropagationContext ctx = pc.getPropagationContext();
         Xid xid = pc.getXid();
         InterposedTransactionManager tm = TxHelper.getServerInterposedTransactionManager();
         weblogic.transaction.Transaction tx = null;

         try {
            tx = (weblogic.transaction.Transaction)tm.getTransaction(xid);
            if (tx == null) {
               if (isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("importing " + xid);
               }

               ResourceImpl res = ResourceImpl.getResource(xid);
               new TransactionRegistrar(pc, res);
               tm.getXAResource().setTransactionTimeout(ctx.timeout);
               tm.getXAResource().start(xid, 0);
               tx = (weblogic.transaction.Transaction)tm.getTransaction(xid);
               tm.getXAResource().setTransactionTimeout(0);
               tx.setLocalProperty(type == 0 ? "weblogic.transaction.ots.inboundContext" : "weblogic.transaction.ots.outboundContext", pc);
               if (isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("imported " + xid + " -> " + tx.getXid());
               }
            } else {
               if (isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("re-importing " + xid);
               }

               if (type == 0 && tx.getProperty("weblogic.transaction.foreignXid") != null) {
                  tm.getXAResource().start(xid, 0);
               }

               if (tx.getLocalProperty(type == 0 ? "weblogic.transaction.ots.inboundContext" : "weblogic.transaction.ots.outboundContext") == null) {
                  tx.setLocalProperty(type == 0 ? "weblogic.transaction.ots.inboundContext" : "weblogic.transaction.ots.outboundContext", pc);
               }
            }
         } catch (XAException var7) {
            if (var7.errorCode == -8) {
               tm.getXAResource().start(xid, 2097152);
               tx = (weblogic.transaction.Transaction)tm.getTransaction(xid);
            }

            IIOPLogger.logOTSError("couldn't import transaction", var7);
         }

         return tx;
      } else {
         if (isDebugEnabled()) {
            IIOPLogger.logDebugOTS("received null tx");
         }

         return null;
      }
   }

   public static void forceLocalCoordinator() {
      weblogic.transaction.Transaction tx = TxHelper.getTransaction();
      if (tx != null) {
         tx.setLocalProperty("weblogic.transaction.otsTransactionExport", "true");
      }

   }

   public static PropagationContextImpl exportTransaction(weblogic.transaction.internal.PropagationContext pc, int type) throws Throwable {
      try {
         weblogic.transaction.Transaction tx = pc.getTransaction();
         return exportTransaction((weblogic.transaction.Transaction)tx, type);
      } catch (TransactionSystemException var3) {
         throw new XAException(-3);
      }
   }

   static PropagationContextImpl exportTransaction(weblogic.transaction.Transaction tx, int type) throws Throwable {
      if (IIOPService.txMechanism == 0) {
         return PropagationContextImpl.NULL_CTX;
      } else {
         PropagationContextImpl otspc = (PropagationContextImpl)tx.getLocalProperty(type == 0 ? "weblogic.transaction.ots.outboundContext" : "weblogic.transaction.ots.inboundContext");
         if (otspc != null && isDebugEnabled()) {
            IIOPLogger.logDebugOTS("re-exporting " + tx.getXID());
         }

         if (otspc == null) {
            otspc = new PropagationContextImpl(tx, new CoordinatorImpl(tx.getXID()));
            tx.setLocalProperty(type == 0 ? "weblogic.transaction.ots.outboundContext" : "weblogic.transaction.ots.inboundContext", otspc);
            tx.setLocalProperty("weblogic.transaction.otsTransactionExport", "true");
         } else if (type == 1 && tx.getProperty("weblogic.transaction.foreignXid") != null) {
            try {
               if (otspc.waitForRegistration() != null) {
                  tx.setRollbackOnly(otspc.getRegistrationError());
                  throw otspc.getRegistrationError();
               }
            } finally {
               if (isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("ending " + otspc.getXid());
               }

               TxHelper.getServerInterposedTransactionManager().getXAResource().end(otspc.getXid(), 67108864);
            }
         }

         return otspc;
      }
   }

   public static final Status jta2otsStatus(int status) {
      Status ret;
      switch (status) {
         case 0:
            ret = Status.StatusActive;
            break;
         case 1:
            ret = Status.StatusMarkedRollback;
            break;
         case 2:
            ret = Status.StatusPrepared;
            break;
         case 3:
            ret = Status.StatusCommitted;
            break;
         case 4:
            ret = Status.StatusRolledBack;
            break;
         case 5:
         default:
            ret = Status.StatusUnknown;
            break;
         case 6:
            ret = Status.StatusNoTransaction;
            break;
         case 7:
            ret = Status.StatusPreparing;
            break;
         case 8:
            ret = Status.StatusCommitting;
            break;
         case 9:
            ret = Status.StatusRollingBack;
      }

      return ret;
   }

   public static final int ots2jtaStatus(Status status) {
      return Utils.ots2jtaStatus(status);
   }

   private static final void p(String msg) {
      System.err.println("<OTSHelper> " + msg);
   }

   static final void writeBytes(DataOutput out, byte[] bytes) throws IOException {
      if (bytes == null) {
         out.writeInt(0);
      } else {
         out.writeInt(bytes.length);
         out.write(bytes);
      }

   }

   static final byte[] readBytes(DataInput in) throws IOException {
      int len = in.readInt();
      if (len == 0) {
         return null;
      } else {
         byte[] bytes = new byte[len];
         in.readFully(bytes);
         return bytes;
      }
   }

   static final void writeXid(DataOutput out, Xid xid) throws IOException {
      out.writeInt(xid.getFormatId());
      writeBytes(out, xid.getGlobalTransactionId());
      writeBytes(out, xid.getBranchQualifier());
   }

   static final Xid readXid(DataInput in) throws IOException {
      int fid = in.readInt();
      byte[] gtrid = readBytes(in);
      byte[] bqual = readBytes(in);
      return new XidImpl(fid, gtrid, bqual);
   }

   static final void writeObject(DataOutput out, Object obj) throws IOException {
      Debug.assertion(obj != null);
      IOR ior = (IOR)IIOPReplacer.getReplacer().replaceObject(obj);
      out.writeUTF(ior.stringify());
   }

   static final Object readObject(DataInput in) throws IOException {
      IOR ior = IOR.destringify(in.readUTF());
      return (Object)IIOPReplacer.getReplacer().resolveObject(ior);
   }
}
