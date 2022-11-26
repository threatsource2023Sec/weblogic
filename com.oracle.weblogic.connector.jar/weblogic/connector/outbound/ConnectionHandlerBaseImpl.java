package weblogic.connector.outbound;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.resource.spi.DissociatableManagedConnection;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ResourceAdapterInternalException;
import weblogic.common.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.monitoring.outbound.ConnectionPoolRuntimeMBeanImpl;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.StackTraceUtils;

public abstract class ConnectionHandlerBaseImpl implements ConnectionHandler {
   public volatile boolean connectionErrorOccurred;
   protected ConnectionPool connPool;
   protected ManagedConnection managedConnection;
   protected final ConnectionInfo connectionInfo;
   protected volatile boolean isDestroyed;
   private AtomicBoolean mcDestroyed = new AtomicBoolean(false);
   private Throwable destroyStacktrace;
   private int numHandlesCreated;
   private int activeHighCount;
   private volatile int numActiveHandles;
   private Hashtable connectionStates;
   private String transSupport;
   private Hashtable objectTable = new Hashtable();
   private Hashtable refTable = new Hashtable();
   static final long serialVersionUID = -9164876384696248548L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.outbound.ConnectionHandlerBaseImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Destroy_Connection_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   protected ConnectionHandlerBaseImpl(ManagedConnection mc, ConnectionPool connPool, SecurityContext secCtx, ConnectionInfo connectionInfo, String transSupport) {
      this.transSupport = transSupport;
      this.managedConnection = mc;
      this.connPool = connPool;
      this.numHandlesCreated = 0;
      this.activeHighCount = 0;
      this.connectionInfo = connectionInfo;
      this.numActiveHandles = 0;
      this.connectionStates = new Hashtable();
      this.connectionErrorOccurred = false;
      this.isDestroyed = false;
      this.initializeConnectionEventListener();
   }

   public synchronized void forcedCleanup() {
      if (Debug.isConnectionsEnabled()) {
         Debug.connections("For pool '" + this.connPool.getName() + "' a connection has timed out.  Closing all handles and releasing back to the available pool");
      }

      Utils.startManagement();

      try {
         this.connPool.trackIdle(this.connectionInfo.getAllocationCallStack());
         Iterator stateIterator = this.connectionStates.keySet().iterator();

         while(stateIterator.hasNext()) {
            WeakReference connRef = (WeakReference)((WeakReference)stateIterator.next());

            try {
               this.closeConnection(connRef);
            } catch (ResourceException var7) {
               if (Debug.isConnectionsEnabled()) {
                  Debug.connections("For pool '" + this.connPool.getName() + "' a ResourceException was thrown while trying to force cleanup on idle connection handle:  " + var7);
               }
            }
         }
      } finally {
         Utils.stopManagement();
      }

   }

   public void closeConnection(Object handle) throws ResourceException {
      if (!this.mcDestroyed.get()) {
         synchronized(this) {
            WeakReference handleRef = (WeakReference)this.refTable.get(handle);
            this.closeConnection(handleRef);
         }
      }
   }

   private void closeConnection(WeakReference handleRef) throws ResourceException {
      if (handleRef != null) {
         ConnectionState connState = (ConnectionState)this.connectionStates.get(handleRef);
         if (connState != null) {
            connState.setConnectionClosed(true);
            this.untrackObject(handleRef);
            if (!connState.isConnectionFinalized()) {
               this.decrementNumActiveHandles();
               this.connPool.releaseOnConnectionClosed(this.connectionInfo);
            }
         } else if (!this.connPool.getConnMgr().isProxyBeingTested()) {
            Debug.logConnectionAlreadyClosed(this.connPool.getName());
         }
      } else if (Debug.isConnectionsEnabled()) {
         Debug.connections(" Connection " + handleRef + " already garbage collected for pool " + this.connPool.getName());
      }

   }

   public synchronized void connectionFinalized(Reference connRef) {
      if (Debug.isConnectionsEnabled()) {
         Debug.connections("connectionFinalized() for pool '" + this.connPool.getName() + "' connRef " + connRef);
      }

      try {
         ConnectionState connState = (ConnectionState)this.connectionStates.get(connRef);
         if (connState != null) {
            Utils.startManagement();

            try {
               if (this.untrackObject(connRef) && !connState.isConnectionClosed() && !this.isConnectionErrorOccurred()) {
                  connState.setConnectionFinalized(true);
                  this.connPool.trackLeak(this.connectionInfo.getAllocationCallStack());
                  this.decrementNumActiveHandles();
                  this.connPool.releaseOnConnectionClosed(this.connectionInfo);
               }
            } finally {
               Utils.stopManagement();
            }
         }
      } finally {
         if (Debug.isConnectionsEnabled()) {
            Debug.connections("connectionFinalized() completed for pool '" + this.connPool.getName() + "'");
         }

      }

   }

   public void destroyConnection() {
      this.isDestroyed = true;
      if (!this.isInTransaction() || this.isConnectionErrorOccurred()) {
         this.connPool.destroyConnection(this.connectionInfo);
      }

   }

   public Object createConnectionHandle(SecurityContext secCtx) throws javax.resource.ResourceException {
      this.enListResource();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      Object connHandle = this.getRAiM().getAdapterLayer().getConnection(this.managedConnection, secCtx.getSubject(), secCtx.getClientInfo(), kernelId);
      if (connHandle == null) {
         String exMsg = Debug.getExceptionMCGetConnectionReturnedNull(this.managedConnection.getClass().getName());
         throw new ResourceAdapterInternalException(exMsg);
      } else {
         this.incrementNumHandlesCreated();
         return this.prepareHandle(connHandle);
      }
   }

   public void associateConnectionHandle(Object connHandle) throws javax.resource.ResourceException {
      if (Debug.isConnectionsEnabled()) {
         Debug.enter(this, "associateConnectionHandle()");
      }

      this.enListResource();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.getRAiM().getAdapterLayer().associateConnection(this.managedConnection, connHandle, kernelId);
      this.prepareHandle(connHandle);
      if (Debug.isConnectionsEnabled()) {
         Debug.exit(this, "associateConnectionHandle()");
      }

   }

   public abstract void enListResource() throws javax.resource.ResourceException;

   public void cleanup() throws javax.resource.ResourceException {
      synchronized(this) {
         this.numHandlesCreated = 0;
         this.activeHighCount = 0;
         this.numActiveHandles = 0;
         this.connectionStates = new Hashtable();
         this.objectTable = new Hashtable();
         this.refTable = new Hashtable();
         this.connectionErrorOccurred = false;
      }

      try {
         if (!this.isDestroyed && !this.mcDestroyed.get()) {
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            this.getRAiM().getAdapterLayer().cleanup(this.managedConnection, kernelId);
         }

      } catch (javax.resource.ResourceException var3) {
         Debug.logCloseConnectionError(this.transSupport, this.connectionInfo, "cleanup", var3);
         throw var3;
      } catch (Throwable var4) {
         Debug.logCloseConnectionError(this.transSupport, this.connectionInfo, "cleanup", var4);
         throw new javax.resource.ResourceException("Failed to cleanup ManagedConnection " + this.managedConnection, var4);
      }
   }

   public void destroy() {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[1];
            var3.args[0] = this;
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.preProcess(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[3] != null) {
            var3.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      try {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         try {
            this.destroyConnectionRuntimeMBean();
            if (this.mcDestroyed.compareAndSet(false, true)) {
               this.getRAiM().getAdapterLayer().destroy(this.managedConnection, kernelId);
               this.destroyStacktrace = new Exception("ManagedConnection [" + this.managedConnection + "] of " + this.getConnectionInfo() + " is destoyed at " + new Date() + " in thread " + Thread.currentThread());
            } else {
               Exception newStacktrace = new Exception("ManagedConnection [" + this.managedConnection + "] of " + this.getConnectionInfo() + " is destoyed again at " + new Date() + " in thread " + Thread.currentThread());
               ConnectorLogger.logMCDestroyedAlready(this.managedConnection.toString(), this.getConnectionInfo().toString(), this.getPoolName(), this.destroyStacktrace == null ? "previous stacktrace was not recorded" : StackTraceUtils.throwable2StackTrace(this.destroyStacktrace), StackTraceUtils.throwable2StackTrace(newStacktrace));
            }
         } catch (Throwable var6) {
            Debug.logCloseConnectionError(this.transSupport, this.connectionInfo, "destroy", var6);
         }
      } catch (Throwable var7) {
         if (var3 != null) {
            var3.th = var7;
            if (var3.monitorHolder[1] != null) {
               var3.monitorIndex = 1;
               InstrumentationSupport.postProcess(var3);
            }

            if (var3.monitorHolder[0] != null) {
               var3.monitorIndex = 0;
               InstrumentationSupport.process(var3);
            }
         }

         throw var7;
      }

      if (var3 != null) {
         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.postProcess(var3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.process(var3);
         }
      }

   }

   public boolean shouldBeDiscard() {
      return this.isDestroyed || this.mcDestroyed.get() || this.connectionErrorOccurred || this.connectionInfo.physicallyDestroyed.get();
   }

   public synchronized void dissociateHandles() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         if (Debug.isConnectionsEnabled()) {
            Debug.enter(this, "dissociateHandles()");
         }

         if (!this.isInTransaction() && this.managedConnection instanceof DissociatableManagedConnection && this.numActiveHandles > 0 && this.connPool.isShareAllowed() && this.connectionInfo.isShareable()) {
            this.connectionStates.clear();
            this.refTable.clear();
            this.numActiveHandles = 0;
            this.getRAiM().getAdapterLayer().dissociateConnections((DissociatableManagedConnection)this.managedConnection, kernelId);
            this.connPool.releaseOnConnectionClosed(this.connectionInfo);
         }
      } catch (javax.resource.ResourceException var7) {
         String msgId = Debug.logDissociateHandlesFailed(this.connPool.getKey(), StackTraceUtils.throwable2StackTrace(var7) + "\n" + (this.mcDestroyed.get() ? "ManagedConnection was destroyed previously at: " + (this.destroyStacktrace == null ? "previous stacktrace was not recorded" : StackTraceUtils.throwable2StackTrace(this.destroyStacktrace)) : "ManagedConnection was not destroyed yet"));
         Debug.logStackTrace(msgId, var7);
         Debug.println((Object)this.getRAiM().getAdapterLayer().toString(var7, kernelId), (String)"dissociateHandles failed");
      } finally {
         if (Debug.isConnectionsEnabled()) {
            Debug.exit(this, "dissociateHandles()");
         }

      }

   }

   protected synchronized void updateActiveHighCount() {
      if (this.numActiveHandles > this.activeHighCount) {
         this.activeHighCount = this.numActiveHandles;
      }

   }

   private synchronized void trackObject(Reference connRef) {
      Integer count = (Integer)this.objectTable.get(connRef);
      if (count == null) {
         count = new Integer(1);
      } else {
         count = new Integer(count + 1);
      }

      this.objectTable.put(connRef, count);
   }

   private synchronized boolean untrackObject(Reference connRef) {
      Integer count = (Integer)this.objectTable.get(connRef);
      boolean removed;
      if (count != null) {
         count = new Integer(count - 1);
         if (count > 0) {
            removed = false;
            this.objectTable.put(connRef, count);
         } else {
            removed = true;
            this.objectTable.remove(connRef);
         }
      } else {
         removed = false;
      }

      return removed;
   }

   protected synchronized void decrementNumActiveHandles() {
      --this.numActiveHandles;
   }

   protected synchronized void incrementNumActiveHandles() {
      ++this.numActiveHandles;
      this.updateActiveHighCount();
   }

   protected synchronized void incrementNumHandlesCreated() {
      ++this.numHandlesCreated;
   }

   protected void addConnectionRuntimeMBean() {
      if (this.connPool == null) {
         Debug.throwAssertionError("ConnectionPool == null");
      }

      ConnectionPoolRuntimeMBeanImpl poolMBean = this.connPool.getRuntimeMBean();
      if (poolMBean == null) {
         Debug.throwAssertionError("ConnectionPool has a null runtime mbean");
      }

      if (this.connectionInfo == null) {
         Debug.throwAssertionError("connectionInfo == null");
      }

      poolMBean.addConnectionRuntimeMBean(this.connectionInfo);
   }

   private void destroyConnectionRuntimeMBean() {
      if (this.connPool == null) {
         Debug.throwAssertionError("ConnectionPool == null");
      }

      ConnectionPoolRuntimeMBeanImpl poolMBean = this.connPool.getRuntimeMBean();
      if (poolMBean == null) {
         Debug.throwAssertionError("ConnectionPool has a null runtime mbean");
      }

      if (this.connectionInfo == null) {
         Debug.throwAssertionError("connectionInfo == null");
      }

      poolMBean.removeConnectionRuntimeMBean(this.connectionInfo);
   }

   private Object prepareHandle(Object connHandle) throws javax.resource.ResourceException {
      Object connWrapper = this.getConnectionOrProxy(connHandle);
      WeakHandleReference connRef = new WeakHandleReference(connWrapper, this);
      boolean containsHandle = false;
      synchronized(this) {
         containsHandle = this.refTable.containsKey(connHandle);
      }

      if (containsHandle) {
         String exMsg = Debug.getExceptionDuplicateHandle();
         throw new ResourceAdapterInternalException(exMsg);
      } else {
         this.trackObject(connRef);
         synchronized(this) {
            this.connectionStates.put(connRef, new ConnectionState());
            this.refTable.put(connRef, connRef);
         }

         this.incrementNumActiveHandles();
         return connWrapper;
      }
   }

   public void setConnectionErrorOccurred(boolean val) {
      this.connectionErrorOccurred = val;
   }

   public boolean isConnectionErrorOccurred() {
      return this.connectionErrorOccurred;
   }

   public synchronized int getActiveHandlesHighCount() {
      return this.activeHighCount;
   }

   public boolean isInTransaction() {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public int getHandlesCreatedTotalCount() {
      return this.getNumHandlesCreated();
   }

   public String getPoolName() {
      return this.connPool.getName();
   }

   public ManagedConnection getManagedConnection() {
      return this.managedConnection;
   }

   public ConnectionInfo getConnectionInfo() {
      return this.connectionInfo;
   }

   public boolean isCallingTransactionLocal() {
      Transaction t = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      if (t != null) {
         return t.getProperty("LOCAL_ENTITY_TX") != null;
      } else {
         return false;
      }
   }

   public int getNumActiveConns() {
      return this.numActiveHandles;
   }

   public ConnectionPool getPool() {
      return this.connPool;
   }

   protected synchronized int getNumHandlesCreated() {
      return this.numHandlesCreated;
   }

   private Object getConnectionOrProxy(Object connHandle) {
      if (this.connPool.getCanUseProxy()) {
         Object connWrapper = ConnectionWrapper.createConnectionWrapper(this.connPool, this.connectionInfo, connHandle);
         return connWrapper;
      } else {
         return connHandle;
      }
   }

   protected abstract void initializeConnectionEventListener();

   protected RAInstanceManager getRAiM() {
      return this.connPool.getRAInstanceManager();
   }

   public Map dumpState() {
      Map map = new HashMap();
      map.put("activeHighCount", this.activeHighCount);
      map.put("connectionErrorOccurred", this.connectionErrorOccurred);
      map.put("connectionInfo", this.connectionInfo);
      map.put("connectionStates", this.connectionStates);
      map.put("pool", this.connPool);
      map.put("poolName", this.getPoolName());
      map.put("destroyStacktrace", this.destroyStacktrace);
      map.put("isDestroyed", this.isDestroyed);
      map.put("managedConnection", this.managedConnection);
      map.put("mcDestroyed", this.mcDestroyed);
      map.put("numActiveHandles", this.numActiveHandles);
      map.put("numHandlesCreated", this.numHandlesCreated);
      map.put("objectTable", this.objectTable);
      map.put("physicallyDestroyed", this.connectionInfo.physicallyDestroyed);
      map.put("refTable", this.refTable);
      map.put("transSupport", this.transSupport);
      map.put("RAiM", this.getRAiM());
      return map;
   }

   static {
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_FLD_Connector_Destroy_Connection_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Destroy_Connection_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionHandlerBaseImpl.java", "weblogic.connector.outbound.ConnectionHandlerBaseImpl", "destroy", "()V", 479, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Destroy_Connection_Low", "Connector_After_Outbound", "Connector_Around_Outbound", "Connector_Before_Outbound"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionHandlerPoolRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), null, null, null}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Destroy_Connection_Low};
   }

   private class WeakHandleReference extends WeakReference {
      private ConnectionHandler connHandler;
      private int hash;

      public WeakHandleReference(Object handle, ConnectionHandler connHandler) {
         super(handle, (ReferenceQueue)null);
         this.connHandler = connHandler;
         this.hash = handle.hashCode();
         if (Proxy.isProxyClass(handle.getClass()) && Proxy.getInvocationHandler(handle) instanceof ConnectionWrapper) {
            ((ConnectionWrapper)Proxy.getInvocationHandler(handle)).setWeakReference(this);
         }

      }

      public int hashCode() {
         return this.hash;
      }

      public boolean equals(Object obj) {
         boolean result;
         if (obj == null) {
            result = false;
         } else if (obj instanceof WeakHandleReference) {
            result = obj == this;
         } else {
            Object handle = this.get();
            if (handle != null && Proxy.isProxyClass(handle.getClass()) && Proxy.getInvocationHandler(handle) instanceof ConnectionWrapper) {
               ConnectionWrapper wrapper = (ConnectionWrapper)Proxy.getInvocationHandler(handle);
               handle = wrapper.getConnectionInstance();
            }

            result = handle == obj;
         }

         return result;
      }
   }
}
