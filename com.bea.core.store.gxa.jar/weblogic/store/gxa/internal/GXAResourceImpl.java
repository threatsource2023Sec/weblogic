package weblogic.store.gxa.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.StoreLogger;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAResource;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.GXid;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.transaction.OptimisticPrepare;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.work.WorkManagerFactory;

@Service
public final class GXAResourceImpl implements XAResource, OptimisticPrepare, GXAResource, FastThreadLocalMarker {
   private static final AuditableThreadLocal threadGXAProp = AuditableThreadLocalFactory.createThreadLocal();
   private static final AuditableThreadLocal threadLocalTranProp = AuditableThreadLocalFactory.createThreadLocal();
   private static TransactionManager tranManager;
   private String resourceName;
   private boolean isShutdown;
   private boolean isOpen;
   private static final String GXA_RM_PREFIX = "WLStore_";
   private static final String GXA_STORE_PREFIX = "weblogic.store.xa.";
   private PersistentStoreXA persistentStore;
   private PersistentStoreConnection storeConnection;
   private TransactionMap tranTable = new TransactionMap();
   private HashSet failedGXids = new HashSet();
   private HashSet committedRecoveredGXids = new HashSet();
   private int timeout;
   private long abandonTimeoutMillis;
   private TransactionResolver transactionResolver = new TransactionResolver();
   private static final HashMap jmsEnlistMentProperties = new HashMap();
   private static final String FN_GXARESOURCE = "RM-construct";
   private static final String FN_OPEN = "RM-open";
   private static final String FN_ENLIST = "RM-enlist";
   private static final String FN_STARTLOCAL = "RM-beginLocalTransaction";
   private static final String FN_SETTHREADGXATRAN = "RM-setThreadGXATransaction";
   private static final String FN_GETTHREADGXATRAN = "RM-getThreadGXATransaction";
   private static final String FN_SETTRANSACTIONTIMEOUT = "RM-setTransactionTimeout";
   private static final String FN_GETTRANSACTIONTIMEOUT = "RM-getTransactionTimeout";
   private static final String FN_ADDNEWOPERATION = "RM-addNewOperation";
   private static final String FN_ADDRECOVEREDOPERATION = "RM-addRecoveredOperation";
   private static final String FN_RECOVER = "RM-recover";
   private static final String FN_START = "RM-start";
   private static final String FN_END = "RM-end";
   private static final String FN_PREPARE = "RM-prepare";
   private static final String FN_ROLLBACK = "RM-rollback";
   private static final String FN_COMMIT = "RM-commit";
   private static final String FN_FORGET = "RM-forget";
   private static final String FN_SHUTDOWN = "RM-shutdown";
   private static final String FN_RESOLVEOP = "RM-scheduleResolve";
   static final DebugLogger storeXA;
   static final DebugLogger storeXAVerbose;

   public GXAResourceImpl(String domainName, PersistentStoreXA persistentStore, String overrideResourceName) {
      if (overrideResourceName == null) {
         this.resourceName = "WLStore_" + domainName + "_" + persistentStore.getName();
      } else {
         this.resourceName = overrideResourceName;
      }

      this.persistentStore = persistentStore;
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.trace("RM-construct", "OK");
      }

   }

   public void setAbandonTimeoutMillis(long timeout) {
      this.abandonTimeoutMillis = timeout;
   }

   public void open() throws PersistentStoreException {
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.traceIn("RM-open", "");
      }

      if (this.isShutdown) {
         throw new AssertionError("Resource can't be opened twice " + this.resourceName);
      } else {
         this.storeConnection = this.persistentStore.createConnection("weblogic.store.xa." + this.resourceName, new GXAObjectHandler());
         PersistentStoreConnection.Cursor cursor = this.storeConnection.createCursor(0);
         ArrayList recordsToDelete = new ArrayList();

         GXATwoPhaseRecord tpr;
         for(PersistentStoreRecord psr = cursor.next(); psr != null; psr = cursor.next()) {
            tpr = (GXATwoPhaseRecord)psr.getData();
            tpr.setPersistentHandle(psr.getHandle());
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.trace("RM-open", (GXid)tpr.getGXid(), (String)("found resolved-tx-record, committed=" + tpr.isCommitted()));
            }

            if (tpr.isCommitted()) {
               this.committedRecoveredGXids.add(tpr.getGXid());
            }

            if (tpr.getTimeStamp() + this.abandonTimeoutMillis < System.currentTimeMillis()) {
               recordsToDelete.add(tpr);
            } else if (!tpr.isCommitted()) {
               GXATransactionImpl gxaTran = new GXATransactionImpl(this, tpr);
               gxaTran.setStatus(3);
               this.tranTable.put(gxaTran);
            }
         }

         for(int i = 0; i < recordsToDelete.size(); ++i) {
            tpr = (GXATwoPhaseRecord)recordsToDelete.get(i);
            PersistentStoreTransaction pst = this.persistentStore.begin();
            this.storeConnection.delete(pst, tpr.getPersistentHandle(), 0);
            pst.commit();
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.trace("RM-open", (GXid)tpr.getGXid(), (String)"deleted old resolved-tx-record ");
            }
         }

         try {
            tranManager = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
         } catch (ClassCastException var6) {
            throw new IllegalStateException("Transactions cannot be used in a client that uses both T3 and IIOP.");
         }

         try {
            tranManager.registerDynamicResource(this.resourceName, this);
         } catch (SystemException var7) {
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-open", (String)"ERROR", (Throwable)var7);
            }

            tranManager = null;
            this.shutdown();
            throw new GXAException(var7);
         }

         this.isOpen = true;
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceOut("RM-open", "OK");
         }

      }
   }

   public String getName() {
      return this.resourceName;
   }

   public PersistentStoreXA getPersistentStore() {
      return this.persistentStore;
   }

   PersistentStoreConnection getStoreConnection() {
      return this.storeConnection;
   }

   public void shutdown() {
      this.isShutdown = true;
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.traceIn("RM-shutdown", "");
      }

      if (tranManager == null) {
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceOut("RM-shutdown", "tran manager never registered or already shutdown");
         }

      } else {
         try {
            tranManager.unregisterResource(this.resourceName);
         } catch (SystemException var2) {
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-shutdown", (String)"ERROR", (Throwable)var2);
            }

            return;
         }

         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceOut("RM-shutdown", "OK");
         }

      }
   }

   private static void ensureNotPreparedOrCommitted(GXATransaction gxaTran) {
      switch (gxaTran.getStatus()) {
         case 2:
         case 3:
         case 6:
            throw new IllegalStateException("Illegal state " + gxaTran.getStatusAsString() + ".  Transaction already complete.");
         case 4:
         case 5:
         default:
      }
   }

   public GXATransaction enlist() throws GXAException {
      return this.enlist(false);
   }

   public GXATransaction enlist(boolean isJMS) throws GXAException {
      GXALocalTransactionImpl localTran = getThreadLocalTransaction();
      if (localTran != null) {
         return localTran;
      } else {
         Transaction threadTx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
         Throwable err = null;
         if (storeXAVerbose.isDebugEnabled()) {
            this.traceIn("RM-enlist", "isTransact=" + (threadTx != null));
         }

         if (threadTx == null) {
            if (storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-enlist", "ret = null");
            }

            return null;
         } else {
            try {
               this.setThreadGXATransaction((GXATransactionImpl)null);
               if (!isJMS) {
                  tranManager.getTransaction().enlistResource(this);
               } else {
                  ((Transaction)tranManager.getTransaction()).enlistResourceWithProperties(this, jmsEnlistMentProperties);
               }

               GXATransactionImpl gxaTran = this.getThreadGXATransaction();
               if (gxaTran == null) {
                  if (storeXAVerbose.isDebugEnabled()) {
                     this.trace("RM-enlist", "silently succeeded without calling xa_start");
                  }

                  Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
                  if (tx != null) {
                     synchronized(this.tranTable) {
                        gxaTran = this.tranTable.get(new GXidImpl(tx.getXID()));
                     }

                     this.setThreadGXATransaction(gxaTran);
                  }
               }

               if (gxaTran != null) {
                  try {
                     synchronized(gxaTran) {
                        ensureNotPreparedOrCommitted(gxaTran);
                     }
                  } catch (IllegalStateException var12) {
                     if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                        this.traceOut("RM-enlist", (String)"illegal state", (Throwable)var12);
                     }

                     throw var12;
                  }

                  if (storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-enlist", "ret = " + gxaTran);
                  }

                  return gxaTran;
               }

               if (storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-enlist", "ret = " + gxaTran);
               }
            } catch (RollbackException var13) {
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-enlist", (String)"rollback detected", (Throwable)var13);
               }

               err = var13;
            } catch (SystemException var14) {
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-enlist", (String)" SYSTEM ERROR!! ", (Throwable)var14);
               }

               err = var14;
            } catch (Exception var15) {
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-enlist", (String)" Unexpected exception!! ", (Throwable)var15);
               }

               err = var15;
            }

            threadTx.setRollbackOnly("Resource " + this.resourceName + " failed to participate in the current transaction.", (Throwable)err);
            throw new GXAException("Transaction already rolled back, or the transaction manager has timed out this resource.", (Throwable)err);
         }
      }
   }

   public GXALocalTransaction beginLocalTransaction() throws GXAException {
      if (getThreadLocalTransaction() != null) {
         throw new GXAException("Thread is already associated with a local GXA transaction");
      } else {
         if (storeXAVerbose.isDebugEnabled()) {
            this.trace("RM-beginLocalTransaction", "Beginning new local GXA transaction");
         }

         GXALocalTransactionImpl tran = new GXALocalTransactionImpl(this);
         setThreadLocalTransaction(tran);
         return tran;
      }
   }

   private void setThreadGXATransaction(GXATransactionImpl gxaTran) {
      if (storeXAVerbose.isDebugEnabled()) {
         if (gxaTran == null) {
            this.trace("RM-setThreadGXATransaction", "null");
         } else {
            this.trace("RM-setThreadGXATransaction", gxaTran.getGXid(), "set to gxaTran=" + gxaTran.hashCode());
         }
      }

      threadGXAProp.set(gxaTran);
   }

   static void setThreadLocalTransaction(GXALocalTransactionImpl localTran) {
      threadLocalTranProp.set(localTran);
   }

   private GXATransactionImpl getThreadGXATransaction() {
      GXATransactionImpl gxaTran = (GXATransactionImpl)threadGXAProp.get();
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         if (gxaTran == null) {
            this.trace("RM-getThreadGXATransaction", "return null");
         } else {
            this.trace("RM-getThreadGXATransaction", gxaTran.getGXid(), " return gxaTran=" + gxaTran.hashCode());
         }
      }

      return gxaTran;
   }

   private static GXALocalTransactionImpl getThreadLocalTransaction() {
      return (GXALocalTransactionImpl)threadLocalTranProp.get();
   }

   void registerFailedTransaction(String traceName, int pass, GXATransactionImpl gxaTran, Throwable t) {
      GXidImpl gxid = gxaTran.getGXidImpl();
      synchronized(this.tranTable) {
         this.failedGXids.add(gxid);
      }

      GXAException exc = new GXAException(t);
      StoreLogger.logUnresolvableTransaction(this.persistentStore.getName(), gxid.toString(false), exc);
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.trace(traceName, (GXid)gxid, (String)("Failed tx pass=" + pass));
      }

   }

   private void throwRMERRIfFailedTransaction(String traceName, GXidImpl gxid) throws XAException {
      synchronized(this.tranTable) {
         if (this.failedGXids.contains(gxid)) {
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut(traceName, (GXid)gxid, (String)"RMERR - tx in indeterminate state, reboot to resolve");
            }

            throw new XAException(-3);
         }
      }
   }

   public boolean isTracingEnabled() {
      return storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled();
   }

   public void addNewOperation(GXATransaction gxaTran, GXAOperation operation) throws GXAException {
      if (!this.isOpen) {
         throw new AssertionError();
      } else {
         if (storeXAVerbose.isDebugEnabled()) {
            this.traceIn("RM-addNewOperation", "operation =" + operation.hashCode());
         }

         GXAOperationWrapperImpl opWrapper = new GXAOperationWrapperImpl(this, (GXAAbstractTransaction)gxaTran, operation);
         synchronized(gxaTran) {
            if (gxaTran.getStatus() == 1) {
               ((GXAAbstractTransaction)gxaTran).addOperation(opWrapper);

               try {
                  opWrapper.init();
               } catch (Error var7) {
                  opWrapper.removeFromTransaction();
                  throw var7;
               } catch (RuntimeException var8) {
                  opWrapper.removeFromTransaction();
                  throw var8;
               }

               if (storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-addNewOperation", gxaTran.getGXid(), "op =" + opWrapper.hashCode() + ", wrapper =" + opWrapper.hashCode());
               }

               return;
            }

            try {
               ensureNotPreparedOrCommitted(gxaTran);
            } catch (IllegalStateException var9) {
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-addNewOperation", gxaTran.getGXid(), "illegal state op =" + opWrapper.hashCode() + ", wrapper =" + opWrapper.hashCode(), var9);
               }

               throw var9;
            }
         }

         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceOut("RM-addNewOperation", gxaTran.getGXid(), "tran already rolled back op =" + opWrapper.hashCode() + ", wrapper =" + opWrapper.hashCode());
         }

         throw new GXAException("Transaction already rolled back.");
      }
   }

   public void addRecoveredOperation(GXAOperation operation) {
      if (!this.isOpen) {
         throw new AssertionError();
      } else {
         GXidImpl gxid = (GXidImpl)operation.getGXid();
         if (gxid == null) {
            throw new IllegalStateException();
         } else {
            synchronized(this.tranTable) {
               GXATransactionImpl gxaTran = this.tranTable.get(gxid);
               if (gxaTran != null && gxaTran.getStatus() == 3) {
                  GXAOperationWrapperImpl opWrapper = new GXAOperationWrapperImpl(this, gxaTran, operation);
                  opWrapper.init();
                  gxaTran.addOperation(opWrapper);
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-addRecoveredOperation", (GXid)gxid, (String)("prepared state, operation=" + operation));
                  }

                  return;
               }

               this.transactionResolver.scheduleOperation(operation);
            }

            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.trace("RM-addRecoveredOperation", (GXid)gxid, (String)("op=" + operation));
            }

         }
      }
   }

   public void addRecoveredOperations(List list) throws ClassCastException {
      ArrayList sortableOps = new ArrayList();
      Iterator iter = list.iterator();

      while(iter.hasNext()) {
         GXAOperation op = (GXAOperation)iter.next();
         if (op instanceof Comparable) {
            sortableOps.add(op);
         } else {
            this.addRecoveredOperation(op);
         }
      }

      GXAOperation[] sortedOps = (GXAOperation[])((GXAOperation[])sortableOps.toArray(new GXAOperation[0]));
      Arrays.sort(sortedOps);

      for(int i = 0; i < sortedOps.length; ++i) {
         this.addRecoveredOperation(sortedOps[i]);
      }

   }

   public int getTransactionTimeout() {
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.trace("RM-getTransactionTimeout", "ret = " + this.timeout);
      }

      return this.timeout;
   }

   public boolean setTransactionTimeout(int t) {
      if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
         this.trace("RM-setTransactionTimeout", " to " + this.timeout);
      }

      this.timeout = t;
      return true;
   }

   public boolean isSameRM(XAResource xar) {
      if (xar == this) {
         return true;
      } else {
         return xar instanceof GXAResourceImpl && ((GXAResourceImpl)xar).resourceName.equals(this.resourceName);
      }
   }

   public synchronized Xid[] recover(int flags) throws XAException {
      javax.transaction.Transaction suspendedTx = tranManager.forceSuspend();

      try {
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceIn("RM-recover", flagsToString(flags));
         }

         Xid[] recoveredXids = new Xid[0];
         if ((flags & 16777216) == 16777216) {
            synchronized(this.tranTable) {
               int numPrepared = 0;
               Iterator iter = this.tranTable.iterator();

               while(iter.hasNext()) {
                  GXATransaction tran = (GXATransaction)iter.next();
                  if (tran.getStatus() == 3) {
                     ++numPrepared;
                  }
               }

               recoveredXids = new Xid[numPrepared];
               int i = 0;
               Iterator iter = this.tranTable.iterator();

               label205:
               while(true) {
                  GXATransaction tran;
                  do {
                     do {
                        if (!iter.hasNext()) {
                           break label205;
                        }

                        tran = (GXATransaction)iter.next();
                     } while(tran.getStatus() != 3);

                     recoveredXids[i++] = tran.getGXid().getXAXid();
                  } while(!storeXA.isDebugEnabled() && !storeXAVerbose.isDebugEnabled());

                  this.trace("RM-recover", tran.getGXid(), "unresolved prepared tx");
               }
            }
         }

         Xid[] var4;
         switch (flags) {
            case 0:
            case 8388608:
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-recover", " num xids=0");
               }

               var4 = new Xid[0];
               return var4;
            case 16777216:
            case 25165824:
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-recover", " num xids=" + recoveredXids.length);
               }

               var4 = recoveredXids;
               return var4;
            default:
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-recover", "ERROR, throw INVAL");
               }

               throw new XAException(-5);
         }
      } finally {
         if (suspendedTx != null) {
            tranManager.forceResume(suspendedTx);
         }

      }
   }

   public void start(Xid xxid, int flag) throws XAException {
      GXidImpl xid = new GXidImpl(xxid);
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceIn("RM-start", (GXid)xid, (String)flagsToString(flag));
      }

      switch (flag) {
         case 0:
         case 2097152:
         case 134217728:
            if (this.getThreadGXATransaction() != null) {
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-start", (GXid)xid, (String)"ERROR xa_start called without intervening xa_end, throw PROTO");
               }

               throw new XAException(-6);
            } else {
               GXATransactionImpl gxaTran;
               if ((flag & 136314880) != 0) {
                  synchronized(this.tranTable) {
                     gxaTran = this.tranTable.get(xid);
                  }

                  if (gxaTran == null) {
                     this.throwRMERRIfFailedTransaction("RM-start", xid);
                     if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                        this.traceOut("RM-start", (GXid)xid, (String)"xid not found, must have rolled back");
                     }

                     throw new XAException(-4);
                  }

                  synchronized(gxaTran) {
                     switch (gxaTran.getStatus()) {
                        case 1:
                           break;
                        case 2:
                        case 3:
                        case 6:
                        default:
                           if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                              this.traceOut("RM-start", (GXid)xid, (String)"ERROR can't associate with committed/prepared tran, throw PROTO");
                           }

                           throw new XAException(-6);
                        case 4:
                        case 5:
                           if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                              this.traceOut("RM-start", (GXid)xid, (String)"gxaTran marked ROLLBACK, already rolling back");
                           }

                           throw new XAException(-4);
                     }
                  }

                  if (storeXAVerbose.isDebugEnabled()) {
                     this.trace("RM-start", (GXid)xid, (String)"found old gxaTran");
                  }
               } else {
                  gxaTran = new GXATransactionImpl(this, xid, false);
                  gxaTran.setStatus(1);
                  synchronized(this.tranTable) {
                     if (this.tranTable.get(xid) != null) {
                        if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                           this.traceOut("RM-start", (GXid)xid, (String)"ERROR asked to create already known xid, throw DUPID");
                        }

                        throw new XAException(-8);
                     }

                     this.tranTable.put(gxaTran);
                  }

                  if (storeXAVerbose.isDebugEnabled()) {
                     this.trace("RM-start", (GXid)xid, (String)"created new gxaTran");
                  }
               }

               this.setThreadGXATransaction(gxaTran);
               if (storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-start", (GXid)xid, (String)"OK");
               }

               return;
            }
         default:
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-start", (GXid)xid, (String)"ERROR throw INVAL");
            }

            throw new XAException(-5);
      }
   }

   public void end(Xid xxid, int flag) throws XAException {
      GXidImpl xid = new GXidImpl(xxid);
      if (storeXAVerbose.isDebugEnabled()) {
         this.traceIn("RM-end", (GXid)xid, (String)flagsToString(flag));
      }

      switch (flag) {
         case 33554432:
         case 67108864:
         case 536870912:
            GXATransactionImpl threadTran = this.getThreadGXATransaction();
            this.setThreadGXATransaction((GXATransactionImpl)null);
            if (threadTran != null && !threadTran.getGXid().equals(xid)) {
               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-end", (GXid)xid, (String)"ERROR, xa_end xid differs from xa_start xid throw PROTO");
               }

               throw new XAException(-6);
            } else {
               GXATransactionImpl gxaTran;
               synchronized(this.tranTable) {
                  gxaTran = this.tranTable.get(xid);
               }

               if (gxaTran == null) {
                  this.throwRMERRIfFailedTransaction("RM-end", xid);
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-end", (GXid)xid, (String)"gxaTran not found, assume already rolled back, throw NOTA");
                  }

                  throw new XAException(-4);
               } else {
                  synchronized(gxaTran) {
                     switch (gxaTran.getStatus()) {
                        case 1:
                           if (flag == 536870912) {
                              gxaTran.setStatus(5);
                           }
                           break;
                        case 2:
                        case 3:
                        case 6:
                        default:
                           if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                              this.traceOut("RM-end", (GXid)xid, (String)"unexpected gxaTran state, throw PROTO");
                           }

                           throw new XAException(-6);
                        case 4:
                        case 5:
                           if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                              this.traceOut("RM-end", (GXid)xid, (String)"gxaTran marked ROLLBACK, throw NOTA");
                           }

                           throw new XAException(-4);
                     }
                  }

                  switch (flag) {
                     case 33554432:
                        if (storeXAVerbose.isDebugEnabled()) {
                           this.traceOut("RM-end", (GXid)xid, (String)"");
                        }

                        return;
                     case 67108864:
                        if (storeXAVerbose.isDebugEnabled()) {
                           this.traceOut("RM-end", (GXid)xid, (String)"");
                        }

                        return;
                     case 536870912:
                        if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                           this.traceOut("RM-end", (GXid)xid, (String)"scheduling rollback");
                        }

                        WorkManagerFactory.getInstance().getSystem().schedule(new RollbackRequest(xid));
                        return;
                     default:
                        return;
                  }
               }
            }
         default:
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-end", (GXid)xid, (String)"ERROR throw INVAL");
            }

            throw new XAException(-5);
      }
   }

   public int prepare(Xid xxid) throws XAException {
      javax.transaction.Transaction suspendedTx = tranManager.forceSuspend();

      byte var6;
      try {
         GXidImpl xid = new GXidImpl(xxid);
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceIn("RM-prepare", (GXid)xid, (String)"");
         }

         GXATransactionImpl gxaTran;
         synchronized(this.tranTable) {
            gxaTran = this.tranTable.get(xid);
         }

         if (gxaTran == null) {
            this.throwRMERRIfFailedTransaction("RM-prepare", xid);
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-prepare", (GXid)xid, (String)"gxaTran not found, must have rolled back, throw NOTA");
            }

            throw new XAException(-4);
         }

         synchronized(gxaTran) {
            switch (gxaTran.getStatus()) {
               case 1:
                  gxaTran.setStatus(2);
                  break;
               case 2:
               case 3:
               case 6:
               default:
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-prepare", (GXid)xid, (String)"ERROR, unexpected gxaTran state 1, throw PROTO");
                  }

                  throw new XAException(-6);
               case 4:
               case 5:
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-prepare", (GXid)xid, (String)"gxaTran marked ROLLBACK, throw NOTA");
                  }

                  throw new XAException(-4);
            }
         }

         boolean prepareOK = gxaTran.doOperationCallbacks("RM-prepare", 2);
         synchronized(gxaTran) {
            switch (gxaTran.getStatus()) {
               case 1:
               case 3:
               case 5:
               case 6:
               default:
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-prepare", (GXid)xid, (String)"ERROR, unexpected gxaTran state 2, throw PROTO");
                  }

                  throw new XAException(-6);
               case 2:
                  if (!prepareOK) {
                     if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                        this.traceOut("RM-prepare", (GXid)xid, (String)"prepare failed, schedule rollback, throw RBROLLBACK");
                     }

                     gxaTran.setStatus(5);
                     WorkManagerFactory.getInstance().getSystem().schedule(new RollbackRequest(xid));
                     throw new XAException(100);
                  }

                  gxaTran.setStatus(3);
                  break;
               case 4:
                  gxaTran.setStatus(5);
                  WorkManagerFactory.getInstance().getSystem().schedule(new RollbackRequest(xid));
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.trace("RM-prepare", (GXid)xid, (String)"ROLLBACK during prepare, schedule rollback, throw RBROLLBACK");
                  }

                  throw new XAException(100);
            }
         }

         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceOut("RM-prepare", (GXid)xid, (String)"OK");
         }

         var6 = 0;
      } finally {
         if (suspendedTx != null) {
            tranManager.forceResume(suspendedTx);
         }

      }

      return var6;
   }

   public void commit(Xid xxid, boolean onePhase) throws XAException {
      javax.transaction.Transaction suspendedTx = tranManager.forceSuspend();

      try {
         GXidImpl xid = new GXidImpl(xxid);
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceIn("RM-commit", (GXid)xid, (String)("onePhase=" + onePhase));
         }

         GXATransactionImpl gxaTran;
         synchronized(this.tranTable) {
            gxaTran = this.tranTable.remove(xid);
            if (gxaTran != null && gxaTran.isRecovered()) {
               this.committedRecoveredGXids.add(xid);
            }
         }

         if (gxaTran == null) {
            this.throwRMERRIfFailedTransaction("RM-commit", xid);
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-commit", (GXid)xid, (String)"gxaTran not found, throw NOTA");
            }

            throw new XAException(-4);
         }

         synchronized(gxaTran) {
            switch (gxaTran.getStatus()) {
               case 1:
                  if (!onePhase) {
                     if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                        this.traceOut("RM-commit", (GXid)xid, (String)"ERROR, not one phase, but gxaTran status ACTIVE, throw PROTO");
                     }

                     throw new XAException(-6);
                  }
                  break;
               case 2:
               case 4:
               case 5:
               case 6:
               default:
                  if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                     this.traceOut("RM-commit", (GXid)xid, (String)("ERROR, unexpected gxaTran state " + gxaTran.getStatus() + " throw PROTO"));
                  }

                  throw new XAException(-6);
               case 3:
                  if (onePhase) {
                     onePhase = false;
                  }
            }

            gxaTran.setStatus(6);
         }

         if (onePhase) {
            boolean ok = gxaTran.doOperationCallbacks("RM-commit", 1);
            if (!ok) {
               gxaTran.setStatus(5);
               synchronized(this.tranTable) {
                  this.tranTable.put(gxaTran);
               }

               if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                  this.traceOut("RM-commit", (GXid)xid, (String)"one-phase failed normally, scheduling rollback, throw RBROLLBACK");
               }

               WorkManagerFactory.getInstance().getSystem().schedule(new RollbackRequest(xid));
               throw new XAException(100);
            }

            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-commit", (GXid)xid, (String)"OK-1PC-Prepare");
            }
         }

         if (onePhase) {
            gxaTran.doOperationCallbacks("RM-commit", 3);
         } else {
            gxaTran.doOperationCallbacks("RM-commit", 4);
         }

         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceOut("RM-commit", (GXid)xid, (String)"OK");
         }
      } finally {
         if (suspendedTx != null) {
            tranManager.forceResume(suspendedTx);
         }

      }

   }

   public void rollback(Xid xxid) throws XAException {
      javax.transaction.Transaction suspendedTx = tranManager.forceSuspend();

      try {
         GXidImpl xid = new GXidImpl(xxid);
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.traceIn("RM-rollback", (GXid)xid, (String)"");
         }

         GXATransactionImpl gxaTran;
         synchronized(this.tranTable) {
            gxaTran = this.tranTable.get(xid);
         }

         if (gxaTran == null) {
            this.throwRMERRIfFailedTransaction("RM-rollback", xid);
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-rollback", (GXid)xid, (String)"gxaTran not found, assume rolled back, succeed silently");
            }

         } else {
            synchronized(gxaTran) {
               switch (gxaTran.getStatus()) {
                  case 1:
                  case 3:
                  case 5:
                     gxaTran.setStatus(4);
                     break;
                  case 2:
                     if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                        this.traceOut("RM-rollback", (GXid)xid, (String)"rollback called during prepare, prepare will do rollback");
                     }

                     gxaTran.setStatus(4);
                     return;
                  case 4:
                     if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
                        this.traceOut("RM-rollback", (GXid)xid, (String)"gxaTran not found, assume rolled back, succeed silently");
                     }

                     return;
                  case 6:
                  default:
                     throw new XAException(-6);
               }
            }

            synchronized(this.tranTable) {
               this.tranTable.remove(xid);
            }

            gxaTran.doOperationCallbacks("RM-rollback", 5);
            if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
               this.traceOut("RM-rollback", "OK");
            }

         }
      } finally {
         if (suspendedTx != null) {
            tranManager.forceResume(suspendedTx);
         }

      }
   }

   public void forget(Xid xxid) {
      javax.transaction.Transaction suspendedTx = tranManager.forceSuspend();

      try {
         if (storeXA.isDebugEnabled() || storeXAVerbose.isDebugEnabled()) {
            this.trace("RM-forget", "ERROR, heuristics not supported.");
         }
      } finally {
         if (suspendedTx != null) {
            tranManager.forceResume(suspendedTx);
         }

      }

   }

   public Xid[] getXIDs(int state) {
      ArrayList xids = new ArrayList();
      synchronized(this.tranTable) {
         if (this.tranTable.isEmpty()) {
            return null;
         } else {
            Iterator it = this.tranTable.iterator();

            while(it.hasNext()) {
               GXATransaction gxatran = (GXATransaction)it.next();
               if (state == -1 || gxatran.getStatus() == state) {
                  xids.add(gxatran.getGXid().getXAXid());
               }
            }

            return (Xid[])((Xid[])xids.toArray(new Xid[xids.size()]));
         }
      }
   }

   public GXATransaction getGXATransaction(Xid xxid) {
      GXid gxid = new GXidImpl(xxid);
      synchronized(this.tranTable) {
         return this.tranTable.get(gxid);
      }
   }

   public int getStatus(Xid xxid) {
      GXATransaction gxaTran = this.getGXATransaction(xxid);
      return gxaTran == null ? -1 : gxaTran.getStatus();
   }

   public String toString() {
      return this.resourceName + System.identityHashCode(this);
   }

   private static String append(String x, String y) {
      return x.length() != 7 ? x + "|" + y : x + y;
   }

   private static String flagsToString(int flags) {
      if (flags == 0) {
         return "flags=(TMNOFLAGS)";
      } else {
         String ret = "flags=(";
         if ((flags & 8388608) != 0) {
            ret = append(ret, "TMENDRSCAN");
         }

         if ((flags & 536870912) != 0) {
            ret = append(ret, "TMFAIL");
         }

         if ((flags & 2097152) != 0) {
            ret = append(ret, "TMJOIN");
         }

         if ((flags & 1073741824) != 0) {
            ret = append(ret, "TMONEPHASE");
         }

         if ((flags & 134217728) != 0) {
            ret = append(ret, "TMRESUME");
         }

         if ((flags & 16777216) != 0) {
            ret = append(ret, "TMSTARTRSCAN");
         }

         if ((flags & 67108864) != 0) {
            ret = append(ret, "TMSUCCESS");
         }

         if ((flags & 33554432) != 0) {
            ret = append(ret, "TMSUSPEND");
         }

         return ret + ")";
      }
   }

   private void trace(char dir, String fn, GXid xid, String msg, Throwable t) {
      String xidStr = " ";
      if (xid != null) {
         xidStr = " XID=" + ((GXidImpl)xid).toString(false) + " ";
      }

      String message = "RM=" + this.resourceName + xidStr + dir + fn + " " + msg;
      storeXA.debug(message, t);
   }

   void trace(String fn, String msg) {
      this.trace(' ', fn, (GXid)null, msg, (Throwable)null);
   }

   void trace(String fn, String msg, Throwable t) {
      this.trace(' ', fn, (GXid)null, msg, t);
   }

   void trace(String fn, GXid xid, String msg) {
      this.trace(' ', fn, xid, msg, (Throwable)null);
   }

   void trace(String fn, GXid xid, String msg, Throwable t) {
      this.trace(' ', fn, xid, msg, t);
   }

   void traceIn(String fn, String msg) {
      this.trace('>', fn, (GXid)null, msg, (Throwable)null);
   }

   void traceIn(String fn, String msg, Throwable t) {
      this.trace('>', fn, (GXid)null, msg, t);
   }

   void traceIn(String fn, GXid xid, String msg) {
      this.trace('>', fn, xid, msg, (Throwable)null);
   }

   void traceIn(String fn, GXid xid, String msg, Throwable t) {
      this.trace('>', fn, xid, msg, t);
   }

   void traceOut(String fn, String msg) {
      this.trace('<', fn, (GXid)null, msg, (Throwable)null);
   }

   void traceOut(String fn, String msg, Throwable t) {
      this.trace('<', fn, (GXid)null, msg, t);
   }

   void traceOut(String fn, GXid xid, String msg) {
      this.trace('<', fn, xid, msg, (Throwable)null);
   }

   void traceOut(String fn, GXid xid, String msg, Throwable t) {
      this.trace('<', fn, xid, msg, t);
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }

   static {
      jmsEnlistMentProperties.put("transaction.enlistment.resource.type", "transaction.enlistment.resource.type.weblogic.jms");
      storeXA = DebugLogger.getDebugLogger("DebugStoreXA");
      storeXAVerbose = DebugLogger.getDebugLogger("DebugStoreXAVerbose");
   }

   private final class TransactionResolver implements Runnable {
      private boolean isRunning;
      private TransactionMap resolvedTransactions;

      private TransactionResolver() {
         this.isRunning = false;
         this.resolvedTransactions = new TransactionMap();
      }

      void scheduleOperation(GXAOperation operation) {
         GXidImpl gxid = (GXidImpl)operation.getGXid();
         synchronized(GXAResourceImpl.this.tranTable) {
            GXATransactionImpl gxaTran = this.resolvedTransactions.get(gxid);
            if (GXAResourceImpl.storeXA.isDebugEnabled() || GXAResourceImpl.storeXAVerbose.isDebugEnabled()) {
               GXAResourceImpl.this.trace("RM-scheduleResolve", (GXid)gxid, (String)("op=" + operation + " gxaTran=" + gxaTran));
            }

            if (gxaTran == null) {
               gxaTran = new GXATransactionImpl(GXAResourceImpl.this, (GXidImpl)operation.getGXid(), true);
               this.resolvedTransactions.put(gxaTran);
               if (GXAResourceImpl.this.committedRecoveredGXids.contains(gxid)) {
                  GXAResourceImpl.this.trace("RM-scheduleResolve", (GXid)gxid, (String)"PREPARED");
                  gxaTran.setStatus(3);
               } else {
                  GXAResourceImpl.this.trace("RM-scheduleResolve", (GXid)gxid, (String)"ROLLBACKONLY");
                  gxaTran.setStatus(5);
               }
            }

            if (GXAResourceImpl.storeXA.isDebugEnabled() || GXAResourceImpl.storeXAVerbose.isDebugEnabled()) {
               GXAResourceImpl.this.trace("RM-scheduleResolve", (GXid)gxid, (String)("op=" + operation + " gxaTran=" + gxaTran));
            }

            GXAOperationWrapperImpl opWrapper = new GXAOperationWrapperImpl(GXAResourceImpl.this, gxaTran, operation);
            opWrapper.init();
            gxaTran.addOperation(opWrapper);
            if (GXAResourceImpl.this.failedGXids.contains(gxaTran.getGXid())) {
               GXAResourceImpl.this.trace("RM-scheduleResolve", gxaTran.getGXid(), "found in failedGXids, remove from resolvedTransaction list");
               this.resolvedTransactions.remove(gxaTran.getGXid());
            }

            if (!this.isRunning && !this.resolvedTransactions.isEmpty()) {
               this.isRunning = true;
               WorkManagerFactory.getInstance().getSystem().schedule(this);
            }

         }
      }

      public void run() {
         GXATransactionImpl gxaTran;
         int state;
         synchronized(GXAResourceImpl.this.tranTable) {
            gxaTran = this.resolvedTransactions.removeOne();

            while(GXAResourceImpl.this.tranTable.containsKey(gxaTran.getGXid())) {
               try {
                  GXAResourceImpl.this.tranTable.wait(100L);
               } catch (InterruptedException var22) {
               }
            }

            GXAResourceImpl.this.tranTable.put(gxaTran);
            state = gxaTran.getStatus();
         }

         Xid xid = gxaTran.getGXid().getXAXid();
         boolean var18 = false;

         label185: {
            try {
               label167: {
                  try {
                     var18 = true;
                     switch (state) {
                        case 3:
                           GXAResourceImpl.this.commit(xid, false);
                           var18 = false;
                           break label185;
                        case 5:
                           GXAResourceImpl.this.rollback(xid);
                           var18 = false;
                           break label185;
                        default:
                           throw new IllegalStateException();
                     }
                  } catch (XAException var23) {
                     if (!GXAResourceImpl.storeXA.isDebugEnabled() && !GXAResourceImpl.storeXAVerbose.isDebugEnabled()) {
                        var18 = false;
                        break label167;
                     }
                  }

                  GXAResourceImpl.this.trace("RM-scheduleResolve", gxaTran.getGXid(), "resolve failure", var23);
                  var18 = false;
               }
            } finally {
               if (var18) {
                  synchronized(GXAResourceImpl.this.tranTable) {
                     if (!this.resolvedTransactions.isEmpty()) {
                        WorkManagerFactory.getInstance().getSystem().schedule(this);
                     } else {
                        this.isRunning = false;
                     }

                  }
               }
            }

            synchronized(GXAResourceImpl.this.tranTable) {
               if (!this.resolvedTransactions.isEmpty()) {
                  WorkManagerFactory.getInstance().getSystem().schedule(this);
               } else {
                  this.isRunning = false;
               }

               return;
            }
         }

         synchronized(GXAResourceImpl.this.tranTable) {
            if (!this.resolvedTransactions.isEmpty()) {
               WorkManagerFactory.getInstance().getSystem().schedule(this);
            } else {
               this.isRunning = false;
            }
         }

      }

      // $FF: synthetic method
      TransactionResolver(Object x1) {
         this();
      }
   }

   private static class TransactionMap {
      private HashMap hashMap;

      private TransactionMap() {
         this.hashMap = new HashMap();
      }

      void put(GXATransaction tran) {
         this.hashMap.put(tran.getGXid(), tran);
      }

      GXATransactionImpl get(GXid key) {
         return (GXATransactionImpl)this.hashMap.get(key);
      }

      GXATransactionImpl remove(GXid key) {
         return (GXATransactionImpl)this.hashMap.remove(key);
      }

      boolean containsKey(GXid key) {
         return this.hashMap.containsKey(key);
      }

      GXATransactionImpl removeOne() {
         return this.isEmpty() ? null : this.remove((GXid)this.hashMap.keySet().iterator().next());
      }

      Iterator iterator() {
         return this.hashMap.values().iterator();
      }

      boolean isEmpty() {
         return this.hashMap.isEmpty();
      }

      // $FF: synthetic method
      TransactionMap(Object x0) {
         this();
      }
   }

   private final class RollbackRequest implements Runnable {
      final Xid xid;

      RollbackRequest(GXidImpl gxid) {
         this.xid = gxid.getXAXid();
      }

      public void run() {
         try {
            GXAResourceImpl.this.rollback(this.xid);
         } catch (XAException var2) {
         }

      }
   }
}
