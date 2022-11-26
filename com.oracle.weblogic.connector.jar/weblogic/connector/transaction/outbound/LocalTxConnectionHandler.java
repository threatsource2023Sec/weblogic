package weblogic.connector.transaction.outbound;

import java.security.AccessController;
import javax.resource.ResourceException;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.outbound.ConnectionInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TxHelper;
import weblogic.transaction.nonxa.NonXAResource;
import weblogic.utils.StackTraceUtils;

public class LocalTxConnectionHandler extends TxConnectionHandler {
   private NonXAWrapper localTransactionWrapper;

   public LocalTxConnectionHandler(ManagedConnection mc, ConnectionPool connPool, SecurityContext secCtx, ConnectionInfo connectionInfo) throws ResourceException {
      super(mc, connPool, secCtx, connectionInfo, "LocalTransaction");
      this.initializeNonXAResource();
      this.addConnectionRuntimeMBean();
   }

   public void enListResource() throws ResourceException {
      if (this.transaction == null) {
         Transaction activeTrans = TxHelper.getTransaction();
         if (activeTrans != null) {
            Throwable e = null;
            boolean enlisted = false;
            TxCompletionNotification transNotif = null;
            boolean localTxBegun = false;
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            boolean var14 = false;

            label128: {
               String exMsg;
               label127: {
                  try {
                     Throwable ex;
                     try {
                        var14 = true;
                        this.connPool.getResourceRegistrationManager().enlistResource(this, activeTrans);
                        enlisted = true;
                        TxCompletionNotification.register(activeTrans, this);
                        this.connPool.getRAInstanceManager().getAdapterLayer().begin(this.getLocalTransactionWrapper().localTransaction, kernelId);
                        localTxBegun = true;
                        this.connPool.getConnectionSharingManager().addSharedConnection(super.getConnectionInfo());
                        var14 = false;
                        break label127;
                     } catch (Throwable var16) {
                        ex = var16;
                        e = var16;
                        if (transNotif != null) {
                           ((TxCompletionNotification)transNotif).deregister();
                        }
                     }

                     if (localTxBegun) {
                        this.getLocalTransactionWrapper().localTransaction.rollback();
                     }

                     if (enlisted) {
                        this.localTransactionWrapper.disable();
                        this.destroy();
                     }

                     try {
                        String exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), ex.toString());
                        ((weblogic.transaction.Transaction)activeTrans).setRollbackOnly(exMsg, ex);
                        var14 = false;
                     } catch (Throwable var15) {
                        Debug.localOut(this.connPool, "Failed to setRollbackOnly after enlistment failure:  " + var15 + StackTraceUtils.throwable2StackTrace(var15));
                        var14 = false;
                     }
                  } finally {
                     if (var14) {
                        if (e != null) {
                           if (Debug.isLocalOutEnabled()) {
                              Debug.localOut(this.connPool, "Failed to setup the connection for transaction enlistment:\n" + this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(e, kernelId));
                           }

                           String exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), e.toString());
                           throw new ResourceException(exMsg, e);
                        }

                     }
                  }

                  if (e != null) {
                     if (Debug.isLocalOutEnabled()) {
                        Debug.localOut(this.connPool, "Failed to setup the connection for transaction enlistment:\n" + this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(e, kernelId));
                     }

                     exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), e.toString());
                     throw new ResourceException(exMsg, e);
                  }
                  break label128;
               }

               if (e != null) {
                  if (Debug.isLocalOutEnabled()) {
                     Debug.localOut(this.connPool, "Failed to setup the connection for transaction enlistment:\n" + this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(e, kernelId));
                  }

                  exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), e.toString());
                  throw new ResourceException(exMsg, e);
               }
            }

            this.transaction = activeTrans;
            this.setGlobalTransactionInProgress(true);
         }
      }

   }

   private void initializeNonXAResource() throws ResourceException {
      LocalTransaction localTransaction = null;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      String exString;
      String stackTraceString;
      String msgId;
      String exMsg;
      try {
         localTransaction = this.connPool.getRAInstanceManager().getAdapterLayer().getLocalTransaction(this.managedConnection, kernelId);
      } catch (ResourceException var9) {
         if (Debug.isLocalOutEnabled()) {
            Debug.localOut(this.connPool, "LocalTxConnectionHandler:  Resource Adapter with key = " + this.connPool.getKey() + " threw ResourceException from its implementation of ManagedConnection.getLocalTransaction(), " + this.connPool.getRAInstanceManager().getAdapterLayer().toString(var9, kernelId) + "\n" + this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(var9, kernelId));
         }

         exString = this.connPool.getRAInstanceManager().getAdapterLayer().toString(var9, kernelId);
         Debug.logGetLocalTransactionError(exString, this.connPool.getKey());
         Throwable causeEx = this.connPool.getRAInstanceManager().getAdapterLayer().getCause(var9, kernelId);
         if (causeEx != null && Debug.isLocalOutEnabled()) {
            Debug.localOut(this.connPool, "LocalTxConnectionHandler:  ResourceException has LinkedException:\n" + causeEx + "\n" + this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(causeEx, kernelId));
         }

         throw var9;
      } catch (Throwable var10) {
         exString = this.connPool.getRAInstanceManager().getAdapterLayer().toString(var10, kernelId);
         stackTraceString = this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(var10, kernelId);
         msgId = Debug.getExceptionMCGetLocalTransactionThrewNonResourceException(this.connPool.getKey(), exString);
         exMsg = Debug.logGetLocalTransactionError(exString, this.connPool.getKey());
         Debug.logStackTraceString(exMsg, stackTraceString);
         Utils.throwAsResourceException(msgId, var10);
      }

      if (localTransaction == null) {
         String msg = Debug.getExceptionMCGetLocalTransactionReturnedNull(this.connPool.getKey());
         Debug.logGetLocalTransactionError(msg, this.connPool.getKey());
         throw new ResourceException(msg);
      } else {
         try {
            this.localTransactionWrapper = new NonXAWrapper(localTransaction, this);
            this.connPool.getResourceRegistrationManager().addResource(this);
         } catch (SystemException var8) {
            exString = this.connPool.getRAInstanceManager().getAdapterLayer().toString(var8, kernelId);
            stackTraceString = this.connPool.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(var8, kernelId);
            msgId = Debug.logRegisterNonXAResourceError(this.getPoolName(), exString);
            Debug.logStackTraceString(msgId, stackTraceString);
            exMsg = Debug.getExceptionRegisterNonXAFailed(var8.toString());
            Utils.throwAsResourceException(exMsg, var8);
         }

      }
   }

   void setLocalTransactionWrapper(NonXAWrapper localTransactionXaWrapper) {
      this.localTransactionWrapper = localTransactionXaWrapper;
   }

   NonXAResource getNonXAResource() {
      return this.localTransactionWrapper;
   }

   NonXAWrapper getLocalTransactionWrapper() {
      return this.localTransactionWrapper;
   }

   public LocalTransaction getLocalTransaction() {
      return this.localTransactionWrapper.localTransaction;
   }

   protected void initializeConnectionEventListener() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RAInstanceManager raInstanceManager = this.connPool.getRAInstanceManager();
      raInstanceManager.getAdapterLayer().addConnectionEventListener(this.managedConnection, TxConnectionEventListener.create(this, "LocalTransConnEventListener", raInstanceManager.getPartitionName()), kernelId);
   }
}
