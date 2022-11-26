package weblogic.connector.outbound;

import com.bea.connector.diagnostic.ManagedConnectionType;
import com.bea.connector.diagnostic.TransactionInfoType;
import com.bea.connector.diagnostic.TransactionType;
import java.security.AccessController;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.resource.spi.DissociatableManagedConnection;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ValidatingManagedConnectionFactory;
import javax.transaction.SystemException;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.connector.common.ConnectorDiagnosticImageSource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.Utils;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.connector.transaction.outbound.LocalTxConnectionHandler;
import weblogic.connector.transaction.outbound.TxConnectionHandler;
import weblogic.connector.transaction.outbound.XATxConnectionHandler;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.j2ee.TrackableConnection;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.Transaction;
import weblogic.utils.StackTraceUtils;

public class ConnectionInfo implements PooledResource, TrackableConnection {
   AtomicBoolean physicallyDestroyed = new AtomicBoolean(false);
   ConnectionHandler connectionHandler;
   private long creationTime = 0L;
   private boolean connectionUsed = false;
   private boolean enabled = true;
   private long lastUsedTime = 0L;
   private int pushCount = 0;
   private String allocationCallStack;
   private boolean shareable;
   private static final String CLASS_NAME = "weblogic.connector.outbound.ConnectionInfo";
   private long creationDurationTime = 0L;
   private long reserveDurationTime = 0L;
   private long reserveTime = 0L;
   boolean hasError = false;
   PooledResourceInfo prInfo = null;
   private boolean destroyAfterRelease;

   ConnectionInfo() {
   }

   public void initialize() {
      this.creationTime = System.currentTimeMillis();
   }

   public void setup() {
      if (Debug.concurrentlogger.isDebugEnabled()) {
         this.setAllocationCallStack(StackTraceUtils.throwable2StackTrace(new Throwable("ConnectionInfo.setup()" + this + "; connectionHandler:" + this.connectionHandler + "; in thread:" + Thread.currentThread().getName() + "; at " + new Date())));
      }

   }

   public void enable() {
      this.enabled = true;
   }

   public PooledResourceInfo getPooledResourceInfo() {
      return this.prInfo;
   }

   public void setPooledResourceInfo(PooledResourceInfo info) {
      this.prInfo = info;
   }

   public void disable() {
      this.enabled = false;
   }

   public void connectionClosed() {
      if (Debug.verbose) {
         Debug.enter(this, "connectionClosed()");
      }

      Utils.startManagement();
      int count = this.decrementPushCount();

      try {
         if (count <= 0) {
            this.connectionHandler.dissociateHandles();
         }
      } finally {
         Utils.stopManagement();
         if (Debug.verbose) {
            Debug.exit(this, "connectionClosed()");
         }

      }

   }

   public void cleanup() throws ResourceException {
      if (Debug.verbose) {
         Debug.enter(this, "cleanup()");
      }

      if (Debug.concurrentlogger.isDebugEnabled()) {
         this.setAllocationCallStack((String)null);
      }

      try {
         this.connectionHandler.cleanup();
      } catch (javax.resource.ResourceException var5) {
         throw new ResourceException(var5);
      } finally {
         if (Debug.verbose) {
            Debug.exit(this, "cleanup()");
         }

      }

   }

   public void destroy() {
      if (Debug.verbose) {
         Debug.enter(this, "destroy()");
      }

      Utils.startManagement();

      try {
         this.connectionHandler.destroy();
      } finally {
         Utils.stopManagement();
         if (Debug.verbose) {
            Debug.exit(this, "destroy()");
         }

      }

   }

   public void forceDestroy() {
      this.destroy();
   }

   public int test() throws ResourceException {
      Utils.startManagement();

      byte result;
      try {
         ManagedConnectionFactory mcf = this.connectionHandler.getPool().getManagedConnectionFactory();
         if (!(mcf instanceof ValidatingManagedConnectionFactory)) {
            String exMsg = Debug.getExceptionMCFNotImplementValidatingMCF();
            if (Debug.isConnectionsEnabled()) {
               Debug.connections(exMsg);
            }

            throw new ResourceException(exMsg);
         }

         Set testSet = new HashSet();
         Set invalidSet = null;
         testSet.add(this.connectionHandler.getManagedConnection());
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         String exMsg;
         try {
            invalidSet = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().getInvalidConnections((ValidatingManagedConnectionFactory)mcf, testSet, kernelId);
         } catch (javax.resource.ResourceException var12) {
            exMsg = Debug.getExceptionTestResourceException(this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(var12, kernelId));
            if (Debug.isConnectionsEnabled()) {
               Debug.connections("Connection test failed: " + exMsg, var12);
            }

            throw new ResourceException(exMsg, var12);
         } catch (Throwable var13) {
            exMsg = Debug.getExceptionTestNonResourceException(this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(var13, kernelId));
            if (Debug.isConnectionsEnabled()) {
               Debug.connections("Connection test failed: " + exMsg, var13);
            }

            throw new ResourceException(exMsg, var13);
         }

         if (invalidSet != null && !invalidSet.isEmpty()) {
            result = -1;
            this.hasError = true;
            if (Debug.isConnectionsEnabled()) {
               Debug.connections("Managed Connection " + this.connectionHandler.getManagedConnection() + " is not valid as reported by Adapter");
            }
         } else {
            result = 0;
         }
      } finally {
         Utils.stopManagement();
      }

      return result;
   }

   public Object createConnectionHandle(SecurityContext secCtx) throws javax.resource.ResourceException {
      Object connectionHandle = this.connectionHandler.createConnectionHandle(secCtx);
      return connectionHandle;
   }

   static ConnectionInfo createConnectionInfo(ConnectionPool pool, String transSupport, ManagedConnection mc, SecurityContext secCtx) throws javax.resource.ResourceException {
      if (Debug.verbose) {
         Debug.enter("weblogic.connector.outbound.ConnectionInfo", "createConnectioInfo() for pool '" + pool.getName() + "' creating connection info/handler with " + transSupport + " transaction support");
      }

      ConnectionInfo connectionInfo = new ConnectionInfo();
      ConnectionHandler connectionHandler = null;

      try {
         if (transSupport.equals("NoTransaction")) {
            connectionHandler = new NoTxConnectionHandler(mc, pool, secCtx, connectionInfo);
         } else if (transSupport.equals("LocalTransaction")) {
            connectionHandler = new LocalTxConnectionHandler(mc, pool, secCtx, connectionInfo);
         } else if (transSupport.equals("XATransaction")) {
            connectionHandler = new XATxConnectionHandler(mc, pool, secCtx, connectionInfo);
         } else {
            String errMsg = "Illegal value of transSupport passed to ConnectionHandlerImpl.createConnectionHandler(): " + transSupport;
            Debug.println((Object)"weblogic.connector.outbound.ConnectionInfo", (String)(".createConnectionInfo() " + errMsg));
            Debug.throwAssertionError(errMsg);
         }

         connectionInfo.setConnectionHandler((ConnectionHandler)connectionHandler);
      } catch (javax.resource.ResourceException var10) {
         Debug.println((Object)"weblogic.connector.outbound.ConnectionInfo", (String)(".createConnectionInfo() Failed to create " + transSupport + " Connection Handler"));
         throw var10;
      } finally {
         if (Debug.verbose) {
            Debug.exit("weblogic.connector.outbound.ConnectionInfo", "createConnectionInfo()");
         }

      }

      return connectionInfo;
   }

   void associateConnectionHandle(Object connectionHandle) throws javax.resource.ResourceException {
      this.connectionHandler.associateConnectionHandle(connectionHandle);
   }

   public void setUsed(boolean newValue) {
      this.connectionUsed = newValue;
   }

   public void setResourceCleanupHandler(ResourceCleanupHandler unused) {
   }

   public void setConnectionHandler(ConnectionHandler connectionHandler) {
      this.connectionHandler = connectionHandler;
   }

   public void setLastUsedTime(long lastUsedTime) {
      this.lastUsedTime = lastUsedTime;
   }

   public void setAllocationCallStack(String callStack) {
      this.allocationCallStack = callStack;
   }

   void setShareable(boolean shareable) {
      this.shareable = shareable;
   }

   public boolean isLocalTransactionInProgress() {
      boolean isLocalTxInProgress;
      if (this.connectionHandler instanceof TxConnectionHandler) {
         isLocalTxInProgress = ((TxConnectionHandler)this.connectionHandler).isLocalTransactionInProgress();
      } else {
         isLocalTxInProgress = false;
      }

      return isLocalTxInProgress;
   }

   public int getNumActiveConns() {
      return this.connectionHandler.getNumActiveConns();
   }

   public long getCreationTime() throws ResourceException {
      return this.creationTime;
   }

   public boolean getUsed() {
      return this.connectionUsed;
   }

   public ResourceCleanupHandler getResourceCleanupHandler() {
      return this.connectionHandler;
   }

   public ConnectionHandler getConnectionHandler() {
      return this.connectionHandler;
   }

   public long getLastUsedTime() {
      return this.lastUsedTime;
   }

   public String getLastUsageString() {
      String lastUsedString;
      if (this.lastUsedTime > 0L) {
         Date lastUsageDate = new Date(this.lastUsedTime);
         lastUsedString = lastUsageDate.toString();
      } else {
         lastUsedString = Debug.getStringNever();
      }

      return lastUsedString;
   }

   public String getAllocationCallStack() {
      return this.allocationCallStack;
   }

   public boolean isInTransaction() {
      return this.connectionHandler.isInTransaction();
   }

   public boolean isBeingShared() {
      return this.getNumActiveConns() > 1;
   }

   public ManagedConnectionType getXMLBean(ConnectorDiagnosticImageSource src) {
      ManagedConnection mc = this.connectionHandler.getManagedConnection();
      ManagedConnectionType mcXBean = ManagedConnectionType.Factory.newInstance();
      mcXBean.setHashcode(mc.hashCode());
      mcXBean.setId(mc.toString());
      boolean timedout = src != null ? src.timedout() : false;
      if (timedout) {
         return mcXBean;
      } else {
         TransactionInfoType txInfoXBean;
         if (this.connectionHandler instanceof TxConnectionHandler) {
            Transaction tx = (Transaction)((TxConnectionHandler)this.connectionHandler).getTransaction();
            if (tx != null) {
               txInfoXBean = mcXBean.getTransactionInfo();
               if (txInfoXBean == null) {
                  txInfoXBean = mcXBean.addNewTransactionInfo();
               }

               String txStatus;
               try {
                  txStatus = String.valueOf(tx.getStatus());
               } catch (SystemException var9) {
                  txStatus = "Not Available";
               }

               TransactionType txXBean = txInfoXBean.addNewTransaction();
               txXBean.setId(tx.getXid().toString());
               txXBean.setStatus(txStatus);
               txXBean.setTransactionType("XATransaction");
               txXBean.setEnlistmentTime("Not Available");
            }
         }

         if (this.connectionHandler instanceof LocalTxConnectionHandler) {
            LocalTransaction localTx = ((LocalTxConnectionHandler)this.connectionHandler).getLocalTransaction();
            if (localTx != null) {
               txInfoXBean = mcXBean.getTransactionInfo();
               if (txInfoXBean == null) {
                  txInfoXBean = mcXBean.addNewTransactionInfo();
               }

               TransactionType txXBean = txInfoXBean.addNewTransaction();
               txXBean.setId(localTx.toString());
               txXBean.setStatus("Started");
               txXBean.setTransactionType("LocalTransaction");
               txXBean.setEnlistmentTime("Not Available");
            }
         }

         return mcXBean;
      }
   }

   boolean isShareable() {
      return this.shareable;
   }

   public String toString() {
      return "ConnectionInfo@" + this.hashCode() + " of pool " + this.connectionHandler.getPoolName();
   }

   public long getCreationDurationTime() {
      return this.creationDurationTime;
   }

   public long getReserveDurationTime() {
      return this.reserveDurationTime;
   }

   public long getReserveTime() {
      return this.reserveTime;
   }

   public void setCreationDurationTime(long creationDurationTime) {
      this.creationDurationTime = creationDurationTime;
   }

   public void setReserveDurationTime(long reserveDurationTime) {
      this.reserveDurationTime = reserveDurationTime;
   }

   public void setReserveTime(long reserveTime) {
      this.reserveTime = reserveTime;
   }

   public String getTransactionId() {
      String returnTransactionId = "";
      if (this.connectionHandler instanceof TxConnectionHandler) {
         Transaction tx = (Transaction)((TxConnectionHandler)this.connectionHandler).getTransaction();
         if (tx != null) {
            returnTransactionId = tx.getXid().toString();
         }
      }

      return returnTransactionId;
   }

   public boolean hasError() {
      return this.hasError;
   }

   protected void pushConnection() {
      ManagedConnection mc = this.getConnectionHandler().getManagedConnection();
      if (!this.getConnectionHandler().getPool().getCanUseProxy() && mc instanceof DissociatableManagedConnection) {
         MethodInvocationHelper.pushConnectionObject(this);
         this.incrementPushCount();
      }

   }

   private int incrementPushCount() {
      synchronized(this) {
         if (Debug.verbose) {
            Debug.println("incrementPushCount(): increase from " + this.pushCount + " to " + (this.pushCount + 1));
         }

         return ++this.pushCount;
      }
   }

   private int decrementPushCount() {
      synchronized(this) {
         if (Debug.verbose) {
            Debug.println("decrementPushCount(): decrease from " + this.pushCount + " to " + (this.pushCount - 1));
         }

         return --this.pushCount;
      }
   }

   public int getPushCount() {
      return this.pushCount;
   }

   public boolean needDestroyAfterRelease() {
      return this.destroyAfterRelease || this.hasError || this.connectionHandler != null && this.connectionHandler.shouldBeDiscard();
   }

   public void setDestroyAfterRelease() {
      this.destroyAfterRelease = true;
   }

   public ResourcePoolGroup getPrimaryGroup() {
      return null;
   }

   public Collection getGroups() {
      return null;
   }

   public ResourcePoolGroup getGroup(String category) {
      return null;
   }
}
