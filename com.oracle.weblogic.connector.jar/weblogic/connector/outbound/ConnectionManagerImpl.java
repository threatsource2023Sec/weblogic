package weblogic.connector.outbound;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import javax.resource.ResourceException;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.IllegalStateException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import weblogic.common.ConnectDisabledException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RACollectionManager;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.exception.RetryableApplicationServerInternalException;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.connector.transaction.outbound.TxConnectionHandler;
import weblogic.connector.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class ConnectionManagerImpl implements ConnectionManagerRemote {
   private static final long serialVersionUID = 7744058501606837252L;
   private transient boolean testingProxy;
   private transient Thread testingProxyThread;
   private UniversalResourceKey key;
   private String raJndi;
   private transient ManagedConnection mgdConnForTest;
   private transient SecurityContext secCtx;
   private transient ConnectionPool connPool;

   public ConnectionManagerImpl(ConnectionPool connPool) {
      Debug.println("Constructing the ConnectionManagerImpl : " + connPool);
      this.connPool = connPool;
      this.testingProxy = false;
      this.key = connPool.getUniversalResourceKey();
      this.raJndi = connPool.getRAInstanceManager().getJndiName();
      this.mgdConnForTest = null;
   }

   public Object allocateConnection(ManagedConnectionFactory unused, ConnectionRequestInfo cxReqInfo) throws ResourceException {
      this.checkPartition();
      this.checkIfPoolIsValid("allocateConnection(...)");
      if (this.testingProxy && Thread.currentThread().equals(this.testingProxyThread)) {
         return this.testAllocateConnection(cxReqInfo);
      } else {
         if (Debug.getVerbose(this)) {
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            Debug.println((Object)this, (String)(".allocateConnection() called with cxReqInfo = " + this.connPool.getRAInstanceManager().getAdapterLayer().toString(cxReqInfo, kernelId)));
         }

         return this.getConnection(cxReqInfo);
      }
   }

   public void lazyEnlist(ManagedConnection mc) throws ResourceException {
      Debug.enter(this, "lazyEnlist(...)");
      this.checkPartition();
      this.checkIfPoolIsValid("lazyEnlist(...)");

      try {
         if (mc == null) {
            Debug.logLazyEnlistNullMC();
            Debug.println((Object)this, (String)".lazyEnlist() was passed a null Managed Connection");
            String exMsg = Debug.getLazyEnlistNullMC(this.connPool.getKey());
            throw new ResourceException(exMsg);
         }

         ConnectionHandler ch = this.connPool.getConnectionHandler(mc);
         if (ch instanceof TxConnectionHandler) {
            ((TxConnectionHandler)ch).enListResource();
         }
      } finally {
         Debug.exit(this, "lazyEnlist(...)");
      }

   }

   public void associateConnection(Object connHandle, ManagedConnectionFactory unused, ConnectionRequestInfo cxInfo) throws ResourceException {
      try {
         Debug.enter(this, "associateConnection(...)");
         this.checkPartition();
         this.checkIfPoolIsValid("associateConnection(...)");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         SecurityContext secCtx = this.connPool.createSecurityContext(cxInfo, false, kernelId);
         ConnectionInfo connInfo = this.getConnectionInfo(cxInfo, secCtx);
         ConnectionHandler handler = connInfo.getConnectionHandler();
         synchronized(handler) {
            try {
               connInfo.associateConnectionHandle(connHandle);
               this.connPool.getConnectionSharingManager().reserveFinished(connInfo);
            } catch (Throwable var15) {
               this.connPool.getConnectionSharingManager().clean(connInfo);
               this.connPool.releaseResource(connInfo);
               if (var15 instanceof ResourceException) {
                  throw (ResourceException)var15;
               }

               if (var15 instanceof RuntimeException) {
                  throw (RuntimeException)var15;
               }

               if (var15 instanceof Error) {
                  throw (Error)var15;
               }

               throw new ResourceException("Connection pool " + this + ": associateConnection() failed with exception: " + var15, var15);
            }
         }
      } finally {
         Debug.exit(this, "associateConnection(...)");
      }

   }

   public Object testAllocateConnection(ConnectionRequestInfo cxReqInfo) throws ResourceException {
      this.checkPartition();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (Debug.getVerbose(this)) {
         Debug.println((Object)this, (String)(".testAllocateConnection() called for pool '" + this.connPool.getName() + "' with cxReqInfo=" + this.connPool.getRAInstanceManager().getAdapterLayer().toString(cxReqInfo, kernelId)));
      }

      Object simpleConn = this.mgdConnForTest.getConnection(this.secCtx.getSubject(), this.secCtx.getClientInfo());
      this.connPool.setProxyTestConnectionHandle(simpleConn);
      return ConnectionWrapper.createProxyTestConnectionWrapper(this.connPool, simpleConn);
   }

   void setTestingProxy(boolean testingProxy) {
      this.testingProxy = testingProxy;
   }

   void setMgdConnForTest(ManagedConnection mgdConnForTest) {
      this.mgdConnForTest = mgdConnForTest;
   }

   void setSecurityContext(SecurityContext secCtx) {
      this.secCtx = secCtx;
   }

   void setTestingProxyThread(Thread testingThread) {
      this.testingProxyThread = testingThread;
   }

   boolean isProxyBeingTested() {
      return this.testingProxy;
   }

   private Object getConnection(ConnectionRequestInfo cxReqInfo) throws ResourceException {
      Debug.enter(this, "getConnection(...)");
      ConnectionInfo connectionInfo = null;
      Object connectionHandle = null;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         SecurityContext secCtx = this.connPool.createSecurityContext(cxReqInfo, false, kernelId);
         connectionInfo = this.getConnectionInfo(cxReqInfo, secCtx);
         ConnectionHandler handler = connectionInfo.getConnectionHandler();
         synchronized(handler) {
            try {
               connectionHandle = connectionInfo.createConnectionHandle(secCtx);
               this.connPool.getConnectionSharingManager().reserveFinished(connectionInfo);
            } catch (Throwable var15) {
               this.connPool.getConnectionSharingManager().clean(connectionInfo);
               this.connPool.releaseResource(connectionInfo);
               throw var15;
            }
         }
      } catch (Throwable var17) {
         if (Debug.isPoolingEnabled()) {
            Debug.pooling().debug("Connection pool " + this.key.toKeyString() + ":" + this + ": getConnection() failed with exception: " + var17, var17);
         }

         if (var17 instanceof ResourceException) {
            throw (ResourceException)var17;
         }

         if (var17 instanceof RuntimeException) {
            throw (RuntimeException)var17;
         }

         if (var17 instanceof Error) {
            throw (Error)var17;
         }

         throw new ResourceException("Connection pool " + this + ": getConnection() failed with exception: " + var17, var17);
      } finally {
         Debug.exit(this, "getConnection(...)");
      }

      return connectionHandle;
   }

   private ConnectionInfo getConnectionInfo(ConnectionRequestInfo cxReqInfo, SecurityContext secCtx) throws ResourceException {
      Debug.enter(this, "getConnectionInfo(...)");
      ConnectionInfo connectionInfo = null;

      try {
         ConnectionReqInfo connectionReqInfo = new ConnectionReqInfo(cxReqInfo, secCtx);

         try {
            Debug.println((Object)this, (String)".getConnectionInfo() Check if access is allowed");
            if (!secCtx.isAccessAllowed()) {
               String exMsg = Debug.getExceptionRAAccessDenied(this.connPool.getKey());
               throw new ApplicationServerInternalException(exMsg);
            }

            connectionReqInfo.setShareable(secCtx.isShareable());
            Debug.println((Object)this, (String)".getConnectionInfo() Reserve connection from the pool");
            connectionInfo = (ConnectionInfo)this.connPool.reserveResource(connectionReqInfo);
         } catch (SecurityException var12) {
            throw var12;
         } catch (weblogic.common.ResourceException var13) {
            Throwable rootCause = var13.getNested();
            if (rootCause != null && rootCause instanceof weblogic.common.ResourceException) {
               rootCause = ((weblogic.common.ResourceException)rootCause).getNested();
            }

            if (rootCause != null && rootCause instanceof ResourceException) {
               throw (ResourceException)rootCause;
            }

            String exMsg = Debug.getExceptionGetConnectionFailed(this.connPool.getKey(), var13.toString());
            if (var13 instanceof ConnectDisabledException) {
               throw new RetryableApplicationServerInternalException(exMsg, var13);
            }

            throw new ApplicationServerInternalException(exMsg, var13);
         }

         if (secCtx.isShareable()) {
            connectionInfo.pushConnection();
         }
      } finally {
         Debug.exit(this, "getConnectionInfo(...)");
      }

      return connectionInfo;
   }

   private void checkIfPoolIsValid(String method) throws IllegalStateException {
      if (this.connPool.isShutdown()) {
         throw new IllegalStateException(ConnectorLogger.getExceptionAllocateConnectionOnStaleConnectionFactory(this.connPool.getKey(), method));
      }
   }

   private Object readResolve() throws ObjectStreamException {
      ConnectionPool connectionPool = null;
      if (this.raJndi != null) {
         RAInstanceManager raInstanceManager = RACollectionManager.getRAInstanceManager(this.raJndi);
         if (raInstanceManager != null) {
            connectionPool = raInstanceManager.getRAOutboundManager().getConnectionPool(this.key.getJndi(), this.key.getDefApp(), this.key.getDefModule(), this.key.getDefComp());
         }
      } else {
         List raList = RACollectionManager.getAllRAInstanceManagers();
         Iterator var3 = raList.iterator();

         while(var3.hasNext()) {
            Object item = var3.next();
            RAInstanceManager raInstanceManager = (RAInstanceManager)item;
            if (raInstanceManager.getJndiName() == null) {
               connectionPool = raInstanceManager.getRAOutboundManager().getConnectionPool(this.key.getJndi(), this.key.getDefApp(), this.key.getDefModule(), this.key.getDefComp());
               if (connectionPool != null) {
                  break;
               }
            }
         }
      }

      if (connectionPool == null) {
         throw new InvalidObjectException(ConnectorLogger.getExceptionDeserializeConnectionManager());
      } else {
         return connectionPool.getConnMgr();
      }
   }

   public String toString() {
      return super.toString() + "-" + this.key.toKeyString();
   }

   public void inactiveConnectionClosed(Object connection, ManagedConnectionFactory mcf) {
      PartitionUtils.checkPartition(this.connPool.getRAInstanceManager().getPartitionName());
      Debug.println((Object)this, (String)(".inactiveConnectionClosed(): Connection: " + connection + "; MCF:" + mcf));
   }

   public void checkPartition() throws ResourceException {
      try {
         PartitionUtils.checkPartition(this.connPool.getRAInstanceManager().getPartitionName());
      } catch (java.lang.IllegalStateException var2) {
         throw new ResourceException(var2.getMessage());
      }
   }
}
