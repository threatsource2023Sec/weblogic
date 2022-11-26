package weblogic.corba.cos.transactions;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CosTransactions.NotPrepared;
import org.omg.CosTransactions.Status;
import weblogic.iiop.IIOPLogger;
import weblogic.server.RunningStateListener;
import weblogic.t3.srvr.SrvrUtilities;
import weblogic.t3.srvr.T3Srvr;
import weblogic.transaction.Transaction;
import weblogic.transaction.TxHelper;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class RecoveryRegistrar extends WorkAdapter implements RunningStateListener {
   private static final boolean DEBUG = false;
   private ResourceImpl res;
   private static final int MAX_RETRIES = 5;
   private int retries = 0;

   RecoveryRegistrar(ResourceImpl res) {
      this.res = res;
   }

   public void onRunning() {
      WorkManagerFactory.getInstance().getSystem().schedule(this);
      WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
         public void run() {
            SrvrUtilities.removeRunningStateListener(RecoveryRegistrar.this);
         }
      });
   }

   public void run() {
      if (T3Srvr.getT3Srvr().getRunState() != 2) {
         SrvrUtilities.addRunningStateListener(this);
      } else {
         synchronized(this.res) {
            ResourceImpl.ResourceActivationID aid = (ResourceImpl.ResourceActivationID)this.res.getActivationID();
            if (OTSHelper.isDebugEnabled()) {
               IIOPLogger.logDebugOTS("recovering tx: " + aid.getXid());
            }

            Transaction tx = null;

            try {
               tx = (Transaction)TxHelper.getTransactionManager().getTransaction(aid.getXid());
               if (tx == null) {
                  ResourceImpl.releaseResource(this.res);
               } else {
                  switch (tx.getStatus()) {
                     case 3:
                     case 8:
                        ResourceImpl.releaseLogRecord(this.res);
                        tx.setLocalProperty("weblogic.transaction.otsReplayCompletionExecuteRequest", (Object)null);
                        return;
                     case 6:
                        ResourceImpl.releaseResource(this.res);
                        tx.setLocalProperty("weblogic.transaction.otsReplayCompletionExecuteRequest", (Object)null);
                        return;
                     default:
                        Status status = aid.getRecoveryCoordinator().replay_completion(this.res);
                        if (OTSHelper.isDebugEnabled()) {
                           IIOPLogger.logDebugOTS("tx: " + aid.getXid() + " status is: " + status.value());
                        }

                        switch (status.value()) {
                           case 2:
                           case 7:
                              return;
                           case 3:
                           case 8:
                              getTMResource().commit(aid.getXid(), false);
                              ResourceImpl.releaseResource(this.res);
                              return;
                           case 4:
                           case 5:
                           case 6:
                           default:
                              try {
                                 getTMResource().rollback(aid.getXid());
                              } catch (XAException var35) {
                                 if (var35.errorCode != -4) {
                                    throw var35;
                                 }
                              } finally {
                                 ResourceImpl.releaseResource(this.res);
                              }
                        }
                  }
               }
            } catch (NotPrepared var37) {
               ResourceImpl.releaseResource(this.res);
            } catch (COMM_FAILURE var38) {
               if (OTSHelper.isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("couldn't contact coordinator, retrying");
               }

               tx.setLocalProperty("weblogic.transaction.otsReplayCompletionExecuteRequest", this);
               tx.setLocalProperty("weblogic.transaction.otsLogRecord", this.res);
            } catch (OBJECT_NOT_EXIST var39) {
               if (OTSHelper.isDebugEnabled()) {
                  IIOPLogger.logDebugOTS("coordinator hasn't exported the RecoveryCoordinator, retrying");
               }

               tx.setLocalProperty("weblogic.transaction.otsReplayCompletionExecuteRequest", this);
               tx.setLocalProperty("weblogic.transaction.otsLogRecord", this.res);

               try {
                  try {
                     getTMResource().rollback(aid.getXid());
                  } catch (XAException var33) {
                     if (var33.errorCode != -4) {
                        IIOPLogger.logOTSError("rollback of " + this.res + " failed", (Throwable)null);

                        try {
                           ResourceImpl.releaseResource(this.res);
                        } catch (RuntimeException var32) {
                        }

                        return;
                     }
                  }

               } finally {
                  ResourceImpl.releaseResource(this.res);
               }
            } catch (Exception var40) {
               IIOPLogger.logOTSError("recovery of " + this.res + " failed", var40);

               try {
                  ResourceImpl.releaseResource(this.res);
               } catch (RuntimeException var31) {
               }

            }
         }
      }
   }

   private static final XAResource getTMResource() {
      return TxHelper.getServerInterposedTransactionManager().getXAResource();
   }

   private static final void p(String msg) {
      System.err.println("<RecoveryRegistrar> " + msg);
   }
}
