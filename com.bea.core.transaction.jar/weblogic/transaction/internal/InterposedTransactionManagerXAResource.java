package weblogic.transaction.internal;

import java.util.Arrays;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.TMXAResourceInterface;
import weblogic.transaction.TransactionHelper;

public class InterposedTransactionManagerXAResource implements TMXAResourceInterface, weblogic.transaction.InterposedTransactionManagerXAResource {
   private static final boolean DEBUG = false;
   private final InterposedTransactionManager itm;
   private final TMXAResourceInterface itmxares;
   private final ClientTransactionManager ctm;
   private Transaction savedSuspendedTransaction;
   private static final String MSG_ALREADY_ASSOCIATED = "The resource already has an active association with a transaction.";
   private static final String MSG_BAD_GTID = "The global transaction id must be a non-null byte array between 1 and 64 bytes in length.";
   private static final String MSG_BAD_BQ = "The branch qualifier must be a non-null byte array between 1 and 64 bytes in length.";
   private static final String MSG_DUP_XID = "A transaction with the provided Xid already exists.";
   private static final String MSG_INSIDE = "The resource can not be dissociated from an XA transaction while the resource is performing an XA operation.";
   private static final String MSG_OUTSIDE = "The resource can neither be associated with nor dissociated from an XA transaction while non-XA work is being performed and/or a non-XA transaction is in progress.";
   private static final String MSG_INVALID_FLAGS = "Invalid flags value: ";
   private static final String MSG_NO_NEW_WORK = "The transaction is already preparing, prepared, committing or comitted.";
   private static final String MSG_NO_SUCH_XID = "The Xid is not known by the resource manager.";
   private static final String MSG_NOT_ACTIVE = "The resource is not actively associated with the specified transaction.";
   private static final String MSG_NULL_XID = "The Xid is null (either the reference is null or the formatId is -1).";
   private static final String MSG_ROLLBACK_ONLY = "The transaction is rollback-only.";
   private static final String MSG_XID_THREW = "Unexpected exception from Xid implementation.";
   private static final String MSG_STATUS_UNKNOWN = "Unable to determine status of transaction.";
   private static final String MSG_INVALID_STATUS = "Invalid transaction status: ";
   private static final String MSG_RESOURCE_CLOSED = "The resource is closed.";
   private static final String MSG_START_FAIL = "Unable to start transaction.";
   private static final String MSG_UNEXPECTED_TRANSACTION_STATE = "Some other resource has left WL XA transaction state on the thread.";
   private static final int NULLXID = -1;
   private StableXid activeXid;
   Transaction activeTransaction;
   private int state;
   private boolean closed;
   private static final int START = 0;
   private static final int OUTSIDE_ALL = 6;
   private static Runnable afterStartActionRunnable;

   public InterposedTransactionManagerXAResource(InterposedTransactionManager itm, TMXAResourceInterface xaResource) {
      this.itm = itm;
      this.itmxares = xaResource;
      this.ctm = TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      TransactionImpl transaction = (TransactionImpl)this.itm.getTransaction();
      if (transaction == null) {
         transaction = (TransactionImpl)this.getSavedSuspendedTransaction();
      }

      if (transaction != null) {
         transaction.check("ITMXAResourceBeforeCommit");
      }

      this.itmxares.commit(xid, onePhase);
      transaction = (TransactionImpl)this.itm.getTransaction();
      if (transaction == null) {
         transaction = (TransactionImpl)this.getSavedSuspendedTransaction();
      }

      if (transaction != null) {
         transaction.check("ITMXAResourceAfterCommit");
      }

   }

   public void forget(Xid xid) throws XAException {
      this.itmxares.forget(xid);
   }

   public int prepare(Xid xid) throws XAException {
      TransactionImpl transaction = (TransactionImpl)this.itm.getTransaction();
      if (transaction == null) {
         transaction = (TransactionImpl)this.getSavedSuspendedTransaction();
      }

      if (transaction != null) {
         transaction.check("ITMXAResourceBeforePrepare");
      }

      int vote = this.itmxares.prepare(xid);
      transaction = (TransactionImpl)this.itm.getTransaction();
      if (transaction == null) {
         transaction = (TransactionImpl)this.getSavedSuspendedTransaction();
      }

      if (transaction != null) {
         transaction.check("ITMXAResourceAfterPrepare");
      }

      return vote;
   }

   public void rollback(Xid xid) throws XAException {
      this.itmxares.rollback(xid);
   }

   public Xid[] recover(int flags) throws XAException {
      return this.itmxares.recover(flags);
   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      return this.itmxares.setTransactionTimeout(seconds);
   }

   public int getTransactionTimeout() throws XAException {
      return this.itmxares.getTransactionTimeout();
   }

   public boolean isSameRM(XAResource xares) throws XAException {
      if (!(xares instanceof InterposedTransactionManagerXAResource)) {
         return false;
      } else {
         InterposedTransactionManager itm1 = ((InterposedTransactionManagerXAResource)xares).getItm();
         TransactionImpl tx1 = (TransactionImpl)itm1.getTransaction();
         if (tx1 == null) {
            tx1 = (TransactionImpl)((TransactionImpl)((InterposedTransactionManagerXAResource)xares).getSavedSuspendedTransaction());
         }

         CoordinatorDescriptor cd1 = null;
         String domainName1 = null;
         if (tx1 != null) {
            cd1 = tx1.getCoordinatorDescriptor();
            domainName1 = cd1.getDomainName();
         }

         TransactionImpl tx2 = (TransactionImpl)this.itm.getTransaction();
         if (tx2 == null) {
            tx2 = (TransactionImpl)this.getSavedSuspendedTransaction();
         }

         CoordinatorDescriptor cd2 = null;
         String domainName2 = null;
         if (tx2 != null) {
            cd2 = tx2.getCoordinatorDescriptor();
            domainName2 = cd2.getDomainName();
         }

         return domainName1 == domainName2;
      }
   }

   InterposedTransactionManager getItm() {
      return this.itm;
   }

   public void add(Xid foreignXid, TransactionImpl tx) {
      this.itmxares.add(foreignXid, tx);
   }

   public TransactionImpl get(Xid foreignXid) {
      return this.itmxares.get(foreignXid);
   }

   public Xid[] getIndoubtXids() {
      return this.itmxares.getIndoubtXids();
   }

   public void remove(Xid foreignXid) {
      this.itmxares.remove(foreignXid);
   }

   public synchronized void start(Xid tmXid, int flags) throws XAException {
      this.doStart(tmXid, flags);
   }

   public synchronized void doStart(Xid tmXid, int flags) throws XAException {
      StableXid xid = new StableXid(tmXid);
      XAException e;
      if (this.activeXid != null) {
         e = new XAException("The resource already has an active association with a transaction.");
         e.errorCode = -6;
         throw e;
      } else if (this.closed) {
         e = new XAException("The resource is closed.");
         e.errorCode = -6;
         throw e;
      } else if (this.state != 0) {
         e = new XAException("The resource can neither be associated with nor dissociated from an XA transaction while non-XA work is being performed and/or a non-XA transaction is in progress.");
         e.errorCode = -9;
         throw e;
      } else {
         Transaction transaction = null;
         int status = 5;

         try {
            transaction = this.itm.getTransaction(tmXid);
         } catch (Throwable var8) {
         }

         if (transaction != null) {
            try {
               status = transaction.getStatus();
            } catch (Throwable var7) {
            }

            if (status == 6) {
               transaction = null;
            }
         }

         XAException e;
         label57:
         switch (flags) {
            case 0:
               if (transaction != null) {
                  e = new XAException("A transaction with the provided Xid already exists.");
                  e.errorCode = -8;
                  throw e;
               }

               if (this.suspend() != null) {
                  e = new XAException("Some other resource has left WL XA transaction state on the thread.");
                  e.errorCode = -3;
                  throw e;
               }

               this.itmxares.start(tmXid, 0);
               if (afterStartActionRunnable != null) {
                  afterStartActionRunnable.run();
               }

               transaction = this.suspend();
               if (transaction == null) {
                  e = new XAException("Unable to start transaction.");
                  e.errorCode = -3;
                  throw e;
               }
               break;
            case 2097152:
               if (transaction == null) {
                  e = new XAException("The Xid is not known by the resource manager.");
                  e.errorCode = -4;
                  throw e;
               }
            case 134217728:
               if (transaction == null) {
                  e = new XAException("The Xid is not known by the resource manager.");
                  e.errorCode = -4;
                  throw e;
               }

               switch (status) {
                  case 0:
                     break label57;
                  case 1:
                  case 4:
                  case 9:
                     e = new XAException("The transaction is rollback-only.");
                     e.errorCode = 100;
                     throw e;
                  case 2:
                  case 3:
                  case 7:
                  case 8:
                     e = new XAException("The transaction is already preparing, prepared, committing or comitted.");
                     e.errorCode = -6;
                     throw e;
                  case 5:
                     e = new XAException("Unable to determine status of transaction.");
                     e.errorCode = -3;
                     throw e;
                  case 6:
                  default:
                     e = new XAException("Invalid transaction status: " + status);
                     e.errorCode = -3;
                     throw e;
               }
            default:
               e = new XAException("Invalid flags value: " + flags);
               e.errorCode = -5;
               throw e;
         }

         this.activeXid = new StableXid(xid);
         this.activeTransaction = transaction;
      }
   }

   public synchronized void end(Xid tmXid, int flags) throws XAException {
      StableXid xid = new StableXid(tmXid);
      boolean xidMatch = this.activeXid != null && xid.equals(this.activeXid);
      if (xidMatch && this.state != 0) {
         XAException e = new XAException("The resource can not be dissociated from an XA transaction while the resource is performing an XA operation.");
         e.errorCode = -6;
         throw e;
      } else {
         Transaction transaction;
         XAException e;
         switch (flags) {
            case 33554432:
               if (!xidMatch) {
                  e = new XAException("The resource is not actively associated with the specified transaction.");
                  e.errorCode = -6;
                  throw e;
               }

               transaction = this.activeTransaction;
               this.activeXid = null;
               this.activeTransaction = null;
               break;
            case 67108864:
            case 536870912:
               if (xidMatch) {
                  transaction = this.activeTransaction;
                  this.activeXid = null;
                  this.activeTransaction = null;
               } else {
                  transaction = null;

                  try {
                     transaction = this.itm.getTransaction(tmXid);
                  } catch (Throwable var10) {
                  }

                  if (transaction == null) {
                     e = new XAException("The Xid is not known by the resource manager.");
                     e.errorCode = -4;
                     throw e;
                  }
               }
               break;
            default:
               e = new XAException("Invalid flags value: " + flags);
               e.errorCode = -5;
               throw e;
         }

         int status = 5;

         try {
            status = transaction.getStatus();
         } catch (Throwable var9) {
         }

         XAException e;
         switch (status) {
            case 0:
               if (!this.closed && flags != 536870912) {
                  return;
               }

               try {
                  transaction.setRollbackOnly();
               } catch (Throwable var8) {
               }

               e = new XAException("The transaction is rollback-only.");
               e.errorCode = 100;
               throw e;
            case 1:
            case 4:
            case 9:
               e = new XAException("The transaction is rollback-only.");
               e.errorCode = 100;
               throw e;
            case 2:
            case 3:
            case 7:
            case 8:
               e = new XAException("The transaction is already preparing, prepared, committing or comitted.");
               e.errorCode = -6;
               throw e;
            case 5:
               e = new XAException("Unable to determine status of transaction.");
               e.errorCode = -3;
               throw e;
            case 6:
               e = new XAException("The Xid is not known by the resource manager.");
               e.errorCode = -4;
               throw e;
            default:
               e = new XAException("Invalid transaction status: " + status);
               e.errorCode = -3;
               throw e;
         }
      }
   }

   public synchronized int begin(int flags) {
      switch (this.state) {
         case 0:
            if (this.activeXid != null) {
               if ((flags & 1) != 0) {
                  this.verifyNoTransactionOnThread();

                  try {
                     this.ctm.forceResume(this.activeTransaction);
                  } catch (Throwable var3) {
                  }
               }

               this.state = flags & 1;
            } else {
               if ((flags & 2) != 0) {
                  this.verifyNoTransactionOnThread();
               }

               this.state = flags & 6;
            }
            break;
         case 4:
            this.verifyNoTransactionOnThread();
            this.state = 6;
      }

      return this.state;
   }

   public synchronized void finish(int flags) {
      switch (this.state) {
         case 1:
            this.state = 0;
            this.setSavedSuspendedTransaction(this.suspend());
            break;
         case 2:
         case 6:
            this.state &= ~flags;
         case 3:
         case 5:
         default:
            break;
         case 4:
            this.state = 0;
      }

   }

   public synchronized void close() {
      if (this.activeTransaction != null) {
         try {
            this.activeTransaction.setRollbackOnly();
         } catch (Throwable var2) {
         }
      }

      this.closed = true;
   }

   private void verifyNoTransactionOnThread() {
      if (this.suspend() != null) {
         throw new UnexpectedTransactionStateException("Some other resource has left WL XA transaction state on the thread.");
      }
   }

   private Transaction suspend() {
      Transaction result = null;

      try {
         result = this.ctm.forceSuspend();
      } catch (Throwable var3) {
      }

      return result;
   }

   static void setAfterStartAction(Runnable runnable) {
      afterStartActionRunnable = runnable;
   }

   Transaction getSavedSuspendedTransaction() {
      return this.savedSuspendedTransaction;
   }

   void setSavedSuspendedTransaction(Transaction tx) {
      this.savedSuspendedTransaction = tx;
   }

   public static class UnexpectedTransactionStateException extends RuntimeException {
      UnexpectedTransactionStateException(String msg) {
         super(msg);
      }
   }

   private static class StableXid {
      final int formatId;
      final byte[] globalTransactionId;
      final byte[] branchQualifier;

      StableXid(Xid tmXid) throws XAException {
         String msg;
         if (tmXid == null) {
            msg = "The Xid is null (either the reference is null or the formatId is -1).";
         } else {
            try {
               this.formatId = tmXid.getFormatId();
               this.globalTransactionId = tmXid.getGlobalTransactionId();
               this.branchQualifier = tmXid.getBranchQualifier();
            } catch (Throwable var5) {
               XAException e = new XAException("Unexpected exception from Xid implementation.");
               e.initCause(var5);
               e.errorCode = -5;
               throw e;
            }

            if (this.formatId == -1) {
               msg = "The Xid is null (either the reference is null or the formatId is -1).";
            } else {
               label34: {
                  if (this.globalTransactionId != null) {
                     int bqLength = this.globalTransactionId.length;
                     if (bqLength >= 1 && bqLength <= 64) {
                        if (this.branchQualifier != null) {
                           bqLength = this.branchQualifier.length;
                           if (bqLength >= 1 && bqLength <= 64) {
                              return;
                           }
                        }

                        msg = "The branch qualifier must be a non-null byte array between 1 and 64 bytes in length.";
                        break label34;
                     }
                  }

                  msg = "The global transaction id must be a non-null byte array between 1 and 64 bytes in length.";
               }
            }
         }

         XAException e = new XAException(msg);
         e.errorCode = -5;
         throw e;
      }

      StableXid(StableXid xid) {
         this.formatId = xid.formatId;
         int length = xid.globalTransactionId.length;
         this.globalTransactionId = new byte[length];
         System.arraycopy(xid.globalTransactionId, 0, this.globalTransactionId, 0, length);
         length = xid.branchQualifier.length;
         this.branchQualifier = new byte[length];
         System.arraycopy(xid.branchQualifier, 0, this.branchQualifier, 0, length);
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof StableXid)) {
            return false;
         } else {
            StableXid other = (StableXid)obj;
            return this.formatId == other.formatId && Arrays.equals(this.globalTransactionId, other.globalTransactionId) && Arrays.equals(this.branchQualifier, other.branchQualifier);
         }
      }
   }
}
