package weblogic.connector.transaction.outbound;

import java.security.AccessController;
import java.util.Hashtable;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.NoEnlistXAResourceException;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.connector.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public final class RecoveryOnlyXAWrapper extends XAWrapper implements ConnectionEventListener {
   private ManagedConnection mc = null;

   public static RecoveryOnlyXAWrapper initializeRecoveryOnlyXAWrapper(ConnectionPool pool) throws SystemException {
      RecoveryOnlyXAWrapper wrapper = new RecoveryOnlyXAWrapper(pool);
      Hashtable props = new Hashtable();
      props.put("weblogic.transaction.registration.type", "standard");
      props.put("weblogic.transaction.registration.settransactiontimeout", "true");
      ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerResource(pool.getNameWithPartitionName(), wrapper, props);
      return wrapper;
   }

   public void cleanupRecoveryOnlyXAWrapper() throws SystemException {
      try {
         ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(this.pool.getNameWithPartitionName(), false);
      } finally {
         this.destroy();
      }

   }

   private RecoveryOnlyXAWrapper(ConnectionPool pool) {
      super((XAResource)null, (XATxConnectionHandler)null, pool);
   }

   public boolean isSameRM(XAResource xares2) throws XAException {
      Utils.startManagement();

      boolean var5;
      try {
         this.debug(" - isSameRM request");
         boolean isSameObject = this == xares2;
         boolean isSamePool = xares2 instanceof XAWrapper && ((XAWrapper)xares2).getConnectionPool().getNameWithPartitionName().equals(this.pool.getNameWithPartitionName());
         boolean isSameRM = isSameObject || isSamePool;
         this.debug(" - isSameRM returning " + isSameRM);
         var5 = isSameRM;
      } finally {
         Utils.stopManagement();
      }

      return var5;
   }

   public void connectionClosed(ConnectionEvent unused) {
      PartitionUtils.checkPartition(this.pool.getRAInstanceManager().getPartitionName());
      this.debug(" - connectionClosed event on RecoveryOnlyXAWrapper");
      Debug.logInvalidRecoveryEvent("connectionClosed");
      if (this.mc != null) {
         try {
            this.mc.cleanup();
         } catch (Throwable var4) {
            this.debug(" - exception while trying to call cleanup on  ManagedConnection for RecoveryOnlyXAWrapper, " + var4);
            String msgId = Debug.logCleanupFailure(var4.toString());
            Debug.logStackTrace(msgId, var4);
         }
      }

   }

   public void connectionErrorOccurred(ConnectionEvent event) {
      PartitionUtils.checkPartition(this.pool.getRAInstanceManager().getPartitionName());
      this.debug(" - connectionErrorOccurred event on RecoveryOnlyXAWrapper", event.getException());
      String errorMessage = event.getException() != null ? event.getException().toString() : "";
      String msgId = Debug.logConnectionError(errorMessage);
      if (event.getException() != null) {
         Debug.logStackTrace(msgId, event.getException());
      }

      this.destroy();
   }

   public void localTransactionStarted(ConnectionEvent unused) {
      PartitionUtils.checkPartition(this.pool.getRAInstanceManager().getPartitionName());
      this.debug(" - localTransactionStarted");
      Debug.logInvalidRecoveryEvent("localTransactionStarted");
   }

   public void localTransactionRolledback(ConnectionEvent unused) {
      PartitionUtils.checkPartition(this.pool.getRAInstanceManager().getPartitionName());
      this.debug(" - localTransactionRolledback");
      Debug.logInvalidRecoveryEvent("localTransactionRolledback");
   }

   public void localTransactionCommitted(ConnectionEvent unused) {
      PartitionUtils.checkPartition(this.pool.getRAInstanceManager().getPartitionName());
      this.debug(" - localTransactionCommitted");
      Debug.logInvalidRecoveryEvent("localTransactionCommitted");
   }

   void destroy() {
      this.debug(" - destroy called on RecoveryOnlyXAWrapper");
      if (this.mc != null) {
         try {
            if (Debug.isXAoutEnabled()) {
               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               this.debug(" - destroying ManagedConnection for RecoveryOnlyXAWrapper:  " + this.getRAiM().getAdapterLayer().toString(this.mc, kernelId));
            }

            this.mc.destroy();
         } catch (Throwable var6) {
            this.debug(" - exception while trying to call destroy on  ManagedConnection for RecoveryOnlyXAWrapper, " + var6);
            String msgId = Debug.logDestroyFailed(var6.toString());
            Debug.logStackTrace(msgId, var6);
         } finally {
            this.xares = null;
            this.mc = null;
         }
      }

   }

   private XAResource getRecoveryXAResource() throws ResourceException {
      AuthenticatedSubject kernelId;
      if (this.mc == null) {
         this.mc = this.getRecoveryManagedConnection();
         if (Debug.isXAoutEnabled()) {
            kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            this.debug(" - ManagedConnection for recovery:  " + this.getRAiM().getAdapterLayer().toString(this.mc, kernelId));
         }
      }

      XAResource recXARes;
      String exMsg;
      try {
         kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         recXARes = this.getRAiM().getAdapterLayer().getXAResource(this.mc, kernelId);
         if (recXARes == null) {
            Debug.logNullXAResource();
            exMsg = Debug.getExceptionMCGetXAResourceReturnedNull();
            throw new ResourceException(exMsg);
         }
      } catch (NoEnlistXAResourceException var4) {
         this.debug(" - ManagedConnection.getXAResource() threw NoEnlistXAResourceException, recovery resource will not be created or enlisted for this connection pool");
         recXARes = null;
         this.enlistableXARes = false;
         throw var4;
      } catch (ResourceException var5) {
         throw var5;
      } catch (Throwable var6) {
         exMsg = Debug.getExceptionMCGetXAResourceThrewNonResourceException(var6.toString());
         throw new ResourceException(exMsg, var6);
      }

      this.debug(" - XAResource for recovery:  " + recXARes);
      return recXARes;
   }

   private ManagedConnection getRecoveryManagedConnection() throws ResourceException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         SecurityContext secCtx = this.pool.createSecurityContext((ConnectionRequestInfo)null, true, kernelId);
         Subject subject = secCtx.getSubject();
         ManagedConnectionFactory mcf = this.pool.getManagedConnectionFactory();
         ManagedConnection mc = null;
         if (!this.pool.isWLSMessagingBridgeConnection()) {
            mc = mcf.createManagedConnection(subject, (ConnectionRequestInfo)null);
         } else {
            XATxConnectionHandler handler = this.pool.findXATxConnectionHandler();
            if (handler != null) {
               mc = handler.getManagedConnection();
            }
         }

         if (mc == null) {
            String exMsg = Debug.getExceptionMCFCreateManagedConnectionReturnedNull();
            throw new ResourceException(exMsg);
         } else {
            mc.addConnectionEventListener(this);
            return mc;
         }
      } catch (ResourceException var7) {
         throw var7;
      } catch (Throwable var8) {
         String exMsg = Debug.getExceptionInitializeForRecoveryFailed(var8.toString());
         throw new ResourceException(exMsg, var8);
      }
   }

   protected void validate(String method) throws XAException {
      boolean isRecoverMethod = method.startsWith("recover");
      if (this.enlistableXARes || !isRecoverMethod) {
         if (this.xares == null) {
            try {
               this.xares = this.getRecoveryXAResource();
            } catch (ResourceException var5) {
               this.debug("Failed to get an XAResource for recovery purposes during call " + method + " :  " + var5 + ", will attempt to use one from the pool if available, enlistable " + this.enlistableXARes);
               this.useXAResourceFromPool();
               if (!this.enlistableXARes && isRecoverMethod) {
                  return;
               }

               if (this.xares == null) {
                  this.debug("Failed to obtain XAResource from the pool during " + method + ", throwing XAException with XAER_RMERR");
                  XAException xaex = new XAException(-3);
                  xaex.initCause(var5);
                  throw xaex;
               }
            }
         }

      }
   }

   protected XAResource getUsableXAResource(String method) throws XAException {
      this.validate(method);
      return this.xares;
   }

   private void useXAResourceFromPool() {
      XATxConnectionHandler handler = this.pool.findXATxConnectionHandler();
      if (handler != null) {
         handler.setXARecoveryWrapper(this);
         this.xares = handler.getXAResource();
         this.enlistableXARes = handler.isEnlistableXARes();
      }

   }
}
