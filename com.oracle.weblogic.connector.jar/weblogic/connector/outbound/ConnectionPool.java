package weblogic.connector.outbound;

import com.bea.connector.diagnostic.ManagedConnectionType;
import com.bea.connector.diagnostic.OutboundAdapterType;
import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.resource.NotSupportedException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.SecurityException;
import javax.resource.spi.TransactionSupport;
import javax.resource.spi.ValidatingManagedConnectionFactory;
import javax.resource.spi.TransactionSupport.TransactionSupportLevel;
import javax.transaction.SystemException;
import weblogic.common.ConnectDisabledException;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourcePoolImpl;
import weblogic.common.resourcepool.ResourcePoolMaintainer;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.ConnectorDiagnosticImageSource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.extensions.Unshareable;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.monitoring.ConnectorComponentRuntimeMBeanImpl;
import weblogic.connector.monitoring.outbound.ConnectionPoolRuntimeMBeanImpl;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.connector.transaction.outbound.RecoveryOnlyXAWrapper;
import weblogic.connector.transaction.outbound.ResourceRegistrationManager;
import weblogic.connector.transaction.outbound.XATxConnectionHandler;
import weblogic.connector.utils.PartitionUtils;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.health.HealthState;
import weblogic.logging.LogFileConfigUtil;
import weblogic.logging.LoggingOutputStream;
import weblogic.logging.WLLevel;
import weblogic.logging.j2ee.LoggingBeanAdapter;
import weblogic.management.ManagementException;
import weblogic.management.logging.LogRuntime;
import weblogic.management.runtime.ConnectionLeakProfile;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

public class ConnectionPool extends ResourcePoolImpl {
   public static final String CP_MATCH_CONNECTIONS_SUPORTED = "matchConnectionsSupported";
   private ConnectionPoolRuntimeMBeanImpl rMBean = null;
   private String poolName;
   private ManagedConnectionFactory managedConnectionFactory;
   private ConnectionSharingManager connectionSharingManager;
   private AtomicInteger numReserves;
   private AtomicInteger numReleases;
   private int numLeaks;
   private AtomicInteger numMatchSuccesses;
   private AtomicInteger numRequestsRejected;
   private boolean canUseProxy;
   private ConnectionManagerImpl connMgr;
   private Object connectionFactory;
   private Vector leakProfiles;
   private Vector idleProfiles;
   private int numRecycled;
   private ResourceRegistrationManager resRegManager;
   private Object proxyTestConnectionHandle;
   private OutboundInfo initOutboundInfo;
   private OutboundInfo pendingOutboundInfo;
   private RAOutboundManager raOutboundManager;
   private RecoveryOnlyXAWrapper recoveryWrapper;
   private ResourcePoolMaintainer maintainer = null;
   private volatile boolean rmHealthy = true;
   private ReentrantReadWriteLock rwLock4ReregisterXAResource = new ReentrantReadWriteLock();
   private AtomicInteger alternateCount;
   private AtomicLong closeCount;
   long freePoolSizeHighWaterMark;
   long freePoolSizeLowWaterMark;
   long poolSizeHighWaterMark;
   long poolSizeLowWaterMark;
   ConnectorComponentRuntimeMBean connRuntimeMbean;
   AtomicInteger connectionsDestroyedByErrorCount;
   boolean useFirstAvailable;
   ResourcePoolProfiler profiler;
   private volatile boolean shutdown = false;
   private LoggingBeanAdapter loggingBeanAdapter = null;
   private LogRuntime logRuntime = null;
   private String applicationName;
   private String componentName;
   private UniversalResourceKey key;
   private boolean isShareAllowed;
   private TransactionSupport.TransactionSupportLevel runtimeTransactionSupportLevel;
   private boolean isWLSMessagingBridgeConnection;
   private int origShrinkSecs;
   private boolean origAllowShrinking;
   static final long serialVersionUID = 2923026828493361427L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.outbound.ConnectionPool");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Reserve_Connection_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Release_Connection_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   protected ConnectionPool() {
      this.runtimeTransactionSupportLevel = TransactionSupportLevel.NoTransaction;
      this.isWLSMessagingBridgeConnection = false;
   }

   public ConnectionPool(UniversalResourceKey key, ManagedConnectionFactory mcf, OutboundInfo outboundInfo, String applicationName, String componentName, RAOutboundManager raOutboundManager) {
      this.runtimeTransactionSupportLevel = TransactionSupportLevel.NoTransaction;
      this.isWLSMessagingBridgeConnection = false;
      this.resRegManager = new ResourceRegistrationManager();
      this.key = key;
      this.numReserves = new AtomicInteger();
      this.numReleases = new AtomicInteger();
      this.numMatchSuccesses = new AtomicInteger();
      this.numRequestsRejected = new AtomicInteger();
      this.alternateCount = new AtomicInteger();
      this.numLeaks = 0;
      this.numRecycled = 0;
      this.closeCount = new AtomicLong();
      this.freePoolSizeHighWaterMark = 0L;
      this.freePoolSizeLowWaterMark = 0L;
      this.poolSizeHighWaterMark = 0L;
      this.poolSizeLowWaterMark = 0L;
      this.connectionsDestroyedByErrorCount = new AtomicInteger();
      this.leakProfiles = new Vector();
      this.idleProfiles = new Vector();
      this.profiler = new ConnectionPoolProfiler(this);
      this.applicationName = applicationName;
      this.componentName = componentName;
      this.managedConnectionFactory = mcf;
      this.initOutboundInfo = outboundInfo;
      this.raOutboundManager = raOutboundManager;
      this.setPoolName();
      if (mcf instanceof TransactionSupport) {
         this.runtimeTransactionSupportLevel = ((TransactionSupport)mcf).getTransactionSupport();
         ConnectorLogger.logRuntimeTransactionSupportLevel(this.getKey(), outboundInfo.getTransactionSupport(), this.runtimeTransactionSupportLevel.toString());
      } else {
         this.runtimeTransactionSupportLevel = TransactionSupportLevel.valueOf(outboundInfo.getTransactionSupport());
      }

      this.connectionSharingManager = new ConnectionSharingManager(this.poolName);
      this.connMgr = new ConnectionManagerImpl(this);
      if (Debug.isPoolingEnabled()) {
         Debug.pooling("Constructed the connection pool : '" + this.poolName + "' with Key '" + this.getKey() + "'");
      }

      this.useFirstAvailable = outboundInfo.isUseFirstAvailable();
      this.isShareAllowed = TransactionSupportLevel.NoTransaction != this.runtimeTransactionSupportLevel;
      if (isUnshareableMCF(mcf.getClass())) {
         this.isShareAllowed = false;
         if (Debug.isPoolingEnabled()) {
            Debug.pooling("The MCF has @Unshareable annotation on it, so it doesn't support share.");
         }
      }

      if (this.managedConnectionFactory.getClass().getName().startsWith("weblogic.jms.adapter.JMSManagedConnectionFactory")) {
         this.isWLSMessagingBridgeConnection = true;
      } else {
         this.isWLSMessagingBridgeConnection = false;
      }

      if (this.runtimeTransactionSupportLevel == TransactionSupportLevel.XATransaction) {
         this.setMaintenanceFrequencySeconds(5);
      } else {
         this.setMaintenanceFrequencySeconds(0);
      }

   }

   public void shutdown() throws ResourceException {
      this.undoSetupForXARecovery();
      super.shutdown();
      this.unregisterConnectionPoolRuntimeMBean();
      this.setLoggingBeanAdapter((LoggingBeanAdapter)null);
      this.shutdown = true;
   }

   public PooledResourceFactory initPooledResourceFactory(Properties unused) throws ResourceException {
      return new ConnectionFactory(this);
   }

   public synchronized PooledResource matchResource(PooledResourceInfo info) throws ResourceException {
      if (info == null) {
         if (Debug.verbose) {
            Debug.enter(this, "matchResource(): called with null. delegate to super's default behavior");
         }

         return super.matchResource(info);
      } else {
         PooledResource returnPooledResource = null;
         HashSet resourcesFree = new HashSet();
         Hashtable mcfToConnectionInfoMap = new Hashtable();
         Object[] pooledResources = null;
         ManagedConnection managedConnection = null;
         ConnectionInfo connectionInfo = null;
         SecurityContext secCtx = null;
         if (Debug.verbose) {
            Debug.enter(this, "matchResource()");
         }

         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("matchResource() called with PoolResourceInfo: " + info.toString());
            this.dumpPool("at start of matchResource");
         }

         try {
            pooledResources = super.available.toArray();
            int numEntries = super.available.size();
            if (!this.useFirstAvailable) {
               secCtx = ((ConnectionReqInfo)info).getSecurityContext();
               if (Debug.isPoolVerboseEnabled()) {
                  this.debugVerbose("There are " + numEntries + " in the available list");
               }

               AuthenticatedSubject kernelId = null;
               if (pooledResources != null && numEntries > 0) {
                  if (kernelId == null) {
                     kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                  }

                  for(int i = 0; i < numEntries; ++i) {
                     PooledResource pooledResource = (PooledResource)pooledResources[i];
                     ConnectionInfo tmpConnectionInfo = (ConnectionInfo)pooledResources[i];
                     resourcesFree.add(tmpConnectionInfo.getConnectionHandler().getManagedConnection());
                     this.getRAInstanceManager().getAdapterLayer().htPut(mcfToConnectionInfoMap, tmpConnectionInfo.getConnectionHandler().getManagedConnection(), pooledResource, kernelId);
                  }
               }

               if (resourcesFree.size() > 0) {
                  if (kernelId == null) {
                     kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                  }

                  try {
                     managedConnection = this.getRAInstanceManager().getAdapterLayer().matchManagedConnection(this.managedConnectionFactory, resourcesFree, secCtx.getSubject(), secCtx.getClientInfo(), kernelId);
                  } catch (NotSupportedException var18) {
                     super.matchSupported = false;
                  } catch (javax.resource.ResourceException var19) {
                     throw new ResourceException(var19);
                  }
               }

               if (managedConnection != null) {
                  if (kernelId == null) {
                     kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                  }

                  connectionInfo = (ConnectionInfo)this.getRAInstanceManager().getAdapterLayer().htGet(mcfToConnectionInfoMap, managedConnection, kernelId);
                  if (connectionInfo != null) {
                     if (Debug.isPoolVerboseEnabled()) {
                        this.debugVerbose("removing " + connectionInfo.toString() + " from available");
                     }

                     if (super.available.remove(connectionInfo)) {
                        returnPooledResource = connectionInfo;
                     }

                     this.incrementNumMatchSuccesses();
                  }
               }
            } else if (numEntries > 0 && super.available.remove(pooledResources[0])) {
               returnPooledResource = (PooledResource)pooledResources[0];
            }
         } finally {
            if (Debug.isPoolVerboseEnabled()) {
               this.dumpPool("on exiting matchResource()");
               this.debugVerbose("exiting ConnectionPool.matchResource() and returning " + (returnPooledResource == null ? "null" : returnPooledResource.toString()));
            }

            if (Debug.verbose) {
               Debug.exit(this, "matchResource()");
            }

         }

         return (PooledResource)returnPooledResource;
      }
   }

   public PooledResource reserveResource(int waitSecs, PooledResourceInfo resInfo) throws ResourceException {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var8.argsCapture) {
            var8.args = new Object[3];
            Object[] var10000 = var8.args;
            var10000[0] = this;
            var10000[1] = InstrumentationSupport.convertToObject(waitSecs);
            var10000[2] = resInfo;
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.preProcess(var8);
         }

         if (var8.monitorHolder[3] != null) {
            var8.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         var8.resetPostBegin();
      }

      ConnectionInfo var20;
      try {
         if (Debug.verbose) {
            Debug.enter(this, "reserveResource()");
         }

         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Entering reserveResource( " + waitSecs + ", " + resInfo.toString() + " )");
            this.debugVerbose("reserveResource() current capacity = " + this.getCurrCapacity() + ", max capacity = " + this.getMaxCapacity());
            this.dumpPool("on entering reserveResource");
         }

         ConnectionInfo returnConnectionInfo = null;
         long startTime = System.currentTimeMillis();

         try {
            try {
               if (super.state != 101 && super.state != 103) {
                  String exMsg = Debug.getExceptionPoolDisabled(this.poolName);
                  throw new ConnectDisabledException(exMsg);
               }

               boolean shareable = ((ConnectionReqInfo)resInfo).isShareable();
               if (this.isShareAllowed && shareable) {
                  returnConnectionInfo = this.connectionSharingManager.reserveSharedConnection();
               }

               if (returnConnectionInfo == null) {
                  returnConnectionInfo = (ConnectionInfo)super.reserveResource(waitSecs, resInfo);
                  returnConnectionInfo.setShareable(shareable);
                  this.incrementNumReserves();
               }
            } catch (ResourceException var15) {
               this.incrementNumRequestsRejected();
               throw var15;
            }

            if (Debug.isPoolVerboseEnabled()) {
               this.dumpPool("on exiting reserveResource");
               this.debugVerbose("Returning " + returnConnectionInfo + " from reserveResource(  " + waitSecs + ", " + resInfo.toString() + " )");
            }

            Debug.assertion(returnConnectionInfo != null, "returnConnectionInfo != null");
            this.updatePoolStats();
         } finally {
            if (Debug.verbose) {
               Debug.exit(this, "reserveResource()");
            }

         }

         long endTime = System.currentTimeMillis();
         returnConnectionInfo.setReserveDurationTime(endTime - startTime);
         returnConnectionInfo.setReserveTime(endTime);
         var20 = returnConnectionInfo;
      } catch (Throwable var17) {
         if (var8 != null) {
            var8.th = var17;
            var8.ret = null;
            if (var8.monitorHolder[2] != null) {
               var8.monitorIndex = 2;
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.postProcess(var8);
            }

            if (var8.monitorHolder[1] != null) {
               var8.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.process(var8);
            }
         }

         throw var17;
      }

      if (var8 != null) {
         var8.ret = var20;
         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.postProcess(var8);
         }

         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }
      }

      return var20;
   }

   public void releaseResource(PooledResource res) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[2];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = res;
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[3] != null) {
            var4.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         var4.resetPostBegin();
      }

      try {
         if (Debug.verbose) {
            Debug.enter(this, "releaseResource()");
         }

         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Entering releaseResource( " + res + ")");
            this.dumpPool("on entering releaseResource");
         }

         try {
            try {
               if (this.reserved.contains(res)) {
                  if (Debug.isPoolVerboseEnabled()) {
                     this.debugVerbose("calling super.releaseResource()");
                  }

                  super.releaseResource(res);
                  this.incrementNumReleases();
               } else {
                  Debug.logReReleasingResource(this.poolName);
               }
            } catch (ResourceException var11) {
               ((ConnectionInfo)res).getConnectionHandler().destroyConnection();
               if (Debug.isPoolVerboseEnabled()) {
                  this.debugVerbose("Exception/exiting releaseResource( " + res + ")", var11);
               }
            }

            if (Debug.isPoolVerboseEnabled()) {
               this.dumpPool("on exiting releaseResource");
            }

            this.updatePoolStats();
         } finally {
            if (Debug.isPoolVerboseEnabled()) {
               this.debugVerbose("Exiting releaseResource( " + res + ")");
            }

            if (Debug.verbose) {
               Debug.exit(this, "releaseResource()");
            }

         }
      } catch (Throwable var13) {
         if (var4 != null) {
            var4.th = var13;
            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.postProcess(var4);
            }

            if (var4.monitorHolder[0] != null) {
               var4.monitorIndex = 0;
               InstrumentationSupport.process(var4);
            }
         }

         throw var13;
      }

      if (var4 != null) {
         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.postProcess(var4);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.process(var4);
         }
      }

   }

   public void releaseOnTransactionCompleted(ConnectionInfo connectionInfo) {
      if (Debug.verbose) {
         Debug.enter(this, "releaseOnTransactionCompleted()");
      }

      try {
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Entering releaseOnTransactionCompleted() with ConnectionInfo = " + connectionInfo);
         }

         this.releaseResource(connectionInfo, true);
      } finally {
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Exiting releaseOnTransactionCompleted() with ConnectionInfo = " + connectionInfo);
         }

         if (Debug.verbose) {
            Debug.exit(this, "releaseOnTransactionCompleted()");
         }

      }

   }

   public void releaseOnConnectionClosed(ConnectionInfo connectionInfo) {
      if (Debug.verbose) {
         Debug.enter(this, "releaseOnConnectionClosed()");
      }

      try {
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Entering releaseOnConnectionClosed() with ConnectionInfo = " + connectionInfo);
         }

         this.releaseResource(connectionInfo, false);
      } finally {
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Exiting releaseOnConnectionClosed() with ConnectionInfo = " + connectionInfo);
         }

         if (Debug.verbose) {
            Debug.exit(this, "releaseOnConnectionClosed()");
         }

      }

   }

   public void destroyConnection(ConnectionInfo connectionInfo) {
      if (connectionInfo.physicallyDestroyed.compareAndSet(false, true)) {
         Debug.enter(this, "destroyConnection()");
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Entering ConnectionPool.destroyConnection() with ConnectionInfo  " + connectionInfo);
            this.dumpPool("on entering destroyConnection");
         }

         try {
            synchronized(this) {
               if (super.reserved.remove(connectionInfo)) {
                  if (Debug.isPoolVerboseEnabled()) {
                     Debug.poolVerbose("Removed " + connectionInfo + " from the reserved ");
                  }
               } else if (super.available.remove(connectionInfo) && Debug.isPoolVerboseEnabled()) {
                  Debug.poolVerbose("Removed " + connectionInfo + " from the available ");
               }
            }

            super.destroyResource(connectionInfo);
         } finally {
            if (Debug.isPoolVerboseEnabled()) {
               this.dumpPool("on exiting destroyConnection");
               this.debugVerbose("Exiting ConnectionPool.destroyConnection() with ConnectionInfo  " + connectionInfo.toString());
            }

            if (Debug.verbose) {
               Debug.exit(this, "destroyConnection(");
            }

         }

      }
   }

   public void dumpPool(String msg) {
      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose(" DUMP of ConnectionPool[ " + msg + " ]");

         try {
            synchronized(this) {
               this.debugVerbose(" currCapacity = " + this.getCurrCapacity() + " maxCapacity = " + this.getMaxCapacity() + " numReserves = " + this.numReserves + " numReleases = " + this.numReleases);
               this.dumpPoolLists();
            }
         } catch (Exception var5) {
            this.debugVerbose("Exception occurred attempting to dump the connection pool", var5);
         }
      }

   }

   public SecurityContext createSecurityContext(ConnectionRequestInfo cxInfo, boolean isInitialConnection, AuthenticatedSubject kernelId) throws SecurityException {
      String appId = this.raOutboundManager.getRA().getApplicationId();
      return new SecurityContext(this.initOutboundInfo, appId, this.componentName, this.poolName, this.isShareAllowed, this.managedConnectionFactory, cxInfo, isInitialConnection, kernelId);
   }

   public void incrementNumReserves() {
      this.numReserves.incrementAndGet();
   }

   public void incrementNumReleases() {
      this.numReleases.incrementAndGet();
   }

   public void suspend() throws ResourceException {
      super.suspend(false);
   }

   public void resume() throws ResourceException {
      super.resume();
   }

   protected PooledResource refreshOldestAvailResource(PooledResourceInfo resInfo) throws ResourceException {
      PooledResource res = null;
      if (super.available.size() > 0) {
         res = this.getOldestUnreservedResource();
         if (res != null) {
            super.available.remove(res);
            res.destroy();
            res = null;
            PooledResourceInfo[] infoList = new PooledResourceInfo[1];
            Arrays.fill(infoList, resInfo);
            Vector successfullyCreatedResources = new Vector();
            super.makeResources(1, infoList, successfullyCreatedResources, false);
            if (successfullyCreatedResources.size() > 0) {
               res = (PooledResource)successfullyCreatedResources.firstElement();
               if (res != null) {
                  super.available.remove(res);
               }

               ++this.numRecycled;
            }
         }
      }

      return res;
   }

   protected void initParameters(Properties poolConfig) {
      super.initParameters(poolConfig);
      String val;
      if ((val = poolConfig.getProperty("matchConnectionsSupported")) != null) {
         super.matchSupported = Boolean.parseBoolean(val);
      }

      super.returnNewlyCreatedResource = true;
      this.origShrinkSecs = this.shrinkSecs;
      this.origAllowShrinking = this.allowShrinking;
   }

   protected void incrementNumMatchSuccesses() {
      this.numMatchSuccesses.getAndIncrement();
   }

   protected void incrementNumRequestsRejected() {
      this.numRequestsRejected.getAndIncrement();
   }

   protected synchronized void trackLeak(String leakStack) {
      ++this.numLeaks;
      if (this.getConnectionProfilingEnabled()) {
         this.leakProfiles.add(new ConnectionLeakProfile(this.poolName, leakStack));
      }

   }

   protected synchronized void trackIdle(String idleStack) {
      if (this.getConnectionProfilingEnabled()) {
         this.idleProfiles.add(new ConnectionLeakProfile(this.poolName, idleStack));
      }

   }

   void debugXAout(String msg) {
      if (Debug.isXAoutEnabled()) {
         Debug.xaOut("For pool '" + this.poolName + "' " + msg);
      }

   }

   void debugXAout(String msg, Throwable ex) {
      if (Debug.isXAoutEnabled()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         String stackTrace = this.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(ex, kernelId);
         Debug.xaOut("For pool '" + this.poolName + "' " + msg + "\n" + stackTrace);
      }

   }

   void debugVerbose(String msg) {
      if (Debug.isPoolVerboseEnabled()) {
         Debug.poolVerbose("For pool '" + this.poolName + "' " + msg);
      }

   }

   void debugVerbose(String msg, Throwable ex) {
      if (Debug.isPoolVerboseEnabled()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         String stackTrace = this.getRAInstanceManager().getAdapterLayer().throwable2StackTrace(ex, kernelId);
         Debug.poolVerbose("For pool '" + this.poolName + "' " + msg + ":\n" + stackTrace);
      }

   }

   private void releaseResource(PooledResource res, boolean transactionCompleted) {
      Debug.enter(this, "releaseResource()");
      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose("on entering releaseResource() PooledResource = " + res.toString() + " & transCompleted = " + transactionCompleted);
         this.dumpPool("on entering releaseResource");
      }

      ConnectionInfo connectionInfo = (ConnectionInfo)res;

      try {
         if (super.state != 100) {
            if (this.connectionSharingManager.releaseSharedConnection(connectionInfo)) {
               if (Debug.isPoolVerboseEnabled()) {
                  this.debugVerbose("calling releaseResource( " + res + " )");
               }

               try {
                  this.releaseResource(res);
               } finally {
                  this.connectionSharingManager.releaseFinished(connectionInfo);
               }
            } else if (Debug.isPoolVerboseEnabled()) {
               this.debugVerbose("Not calling releaseResource( " + res + " )");
            }
         }
      } finally {
         if (Debug.isPoolVerboseEnabled()) {
            this.dumpPool("on exiting releaseResource");
         }

         if (Debug.verbose) {
            Debug.exit(this, "releaseResource()");
         }

      }

   }

   protected void unregisterConnectionPoolRuntimeMBean() {
      Utils.unregisterRuntimeMBean(this.rMBean);
      ((ConnectorComponentRuntimeMBeanImpl)this.connRuntimeMbean).removeConnPoolRuntime(this.rMBean);
   }

   private void dumpPoolLists() {
      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose(" DUMPING available list, #entries = " + this.available.size());
      }

      int i = 0;
      ListIterator listIter = this.available.listIterator(0);

      while(listIter.hasNext()) {
         ConnectionInfo info = (ConnectionInfo)listIter.next();
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose(" available list[" + i++ + "].connectionInfo = " + info + ", number of active connection = " + info.getNumActiveConns());
         }
      }

      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose(" DUMPING reserved list, #entries = " + this.reserved.size());
      }

      i = 0;
      Iterator iter = this.reserved.iterator();

      while(iter.hasNext()) {
         ConnectionInfo info = (ConnectionInfo)iter.next();
         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose(" reserved list[" + i++ + "].connectionInfo = " + info + ", number of active connection = " + info.getNumActiveConns());
         }
      }

      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose(" DUMPING dead list, #entries = " + this.dead.size());
      }

   }

   public void setupForXARecovery() throws ResourceException {
      if (TransactionSupportLevel.XATransaction == this.getRuntimeTransactionSupportLevel()) {
         try {
            this.recoveryWrapper = RecoveryOnlyXAWrapper.initializeRecoveryOnlyXAWrapper(this);
            this.setRmHealthy(true);
            this.debugXAout("Registered XAResource for recovery:  " + this.recoveryWrapper);
         } catch (SystemException var3) {
            String msgId = Debug.logRegisterForXARecoveryFailed(var3.toString());
            Debug.logStackTrace(msgId, var3);
            throw new ResourceException(var3.toString(), var3);
         }
      }

   }

   public void undoSetupForXARecovery() {
      if (TransactionSupportLevel.XATransaction == this.getRuntimeTransactionSupportLevel()) {
         try {
            if (this.recoveryWrapper != null) {
               this.recoveryWrapper.cleanupRecoveryOnlyXAWrapper();
            }

            this.debugXAout("Unregistered XAResource for recovery");
         } catch (SystemException var6) {
            String msgId = Debug.logUnregisterForXARecoveryFailed(var6.toString());
            Debug.logStackTrace(msgId, var6);
         } finally {
            this.recoveryWrapper = null;
         }
      }

   }

   public RecoveryOnlyXAWrapper getRecoveryWrapper() {
      return this.recoveryWrapper;
   }

   public void setProxyTestConnectionHandle(Object obj) {
      this.proxyTestConnectionHandle = obj;
   }

   void setCanUseProxy(boolean canUseProxy) {
      this.canUseProxy = canUseProxy;
   }

   private void setPoolName() {
      this.poolName = this.getKey();
      Debug.println((Object)this, (String)(".setNames() - poolName = " + this.poolName));
   }

   public ManagedConnectionFactory getManagedConnectionFactory() {
      return this.managedConnectionFactory;
   }

   public String getJNDIName() {
      return this.initOutboundInfo.getJndiName();
   }

   public UniversalResourceKey getUniversalResourceKey() {
      return this.key;
   }

   public String getKey() {
      return this.key == null ? this.getJNDIName() : this.key.toKeyString();
   }

   public String getName() {
      return this.poolName;
   }

   public String getNameWithPartitionName() {
      return PartitionUtils.appendPartitionName(this.poolName, this.raOutboundManager.getRA().getPartitionName());
   }

   public ConnectionSharingManager getConnectionSharingManager() {
      return this.connectionSharingManager;
   }

   public ConnectionPoolRuntimeMBeanImpl getRuntimeMBean() {
      return this.rMBean;
   }

   public String getConnectionFactoryName() {
      return this.initOutboundInfo.getConnectionFactoryName();
   }

   public RAInstanceManager getRAInstanceManager() {
      return this.raOutboundManager.getRA();
   }

   public String getRALinkRefName() {
      return this.initOutboundInfo.getRaLinkRef();
   }

   public String getConfiguredTransactionSupport() {
      return this.initOutboundInfo.getTransactionSupport();
   }

   public TransactionSupport.TransactionSupportLevel getRuntimeTransactionSupportLevel() {
      return this.runtimeTransactionSupportLevel;
   }

   public boolean isLoggingEnabled() {
      return this.getDynamicOutboundInfo().isLoggingEnabled();
   }

   public String getLogFileName() {
      return this.getDynamicOutboundInfo().getLogFilename();
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this.initOutboundInfo.getInactiveConnectionTimeoutSeconds();
   }

   public boolean getConnectionProfilingEnabled() {
      return this.initOutboundInfo.getConnectionProfilingEnabled();
   }

   public int getDetectedLeakCount() {
      return this.numLeaks;
   }

   public int getInitialCapacity() {
      return this.initialCapacity;
   }

   public int getCapacityIncrement() {
      return this.capacityIncrement;
   }

   public int getShrinkPeriodMinutes() {
      return this.getInactiveResourceTimeoutSeconds() / 60;
   }

   public int getInactiveResourceTimeoutSeconds() {
      return this.inactiveSecs;
   }

   public int getResourceCreationRetrySeconds() {
      return this.retryIntervalSecs;
   }

   public int getResourceReserveTimeoutSeconds() {
      return this.reserveTimeoutSecs;
   }

   public boolean isShrinkingEnabled() {
      return this.allowShrinking;
   }

   public int getShrinkFrequencySeconds() {
      return this.shrinkSecs;
   }

   public void setShrinkFrequencySeconds(int newVal) {
      if (newVal <= this.origShrinkSecs || this.origShrinkSecs <= 0) {
         super.setShrinkFrequencySeconds(newVal);
      }
   }

   public void recoverShrinkFrequency() {
      this.setShrinkFrequencySeconds(this.origShrinkSecs);
      this.setShrinkEnabled(this.origAllowShrinking);
   }

   public int getTestFrequencySeconds() {
      return this.testSecs;
   }

   public boolean getTestOnReserve() {
      return this.testOnReserve;
   }

   public boolean getTestOnRelease() {
      return this.testOnRelease;
   }

   public boolean getTestOnCreate() {
      return this.testOnCreate;
   }

   public PooledResource getOldestUnreservedResource() {
      Object[] pooledResources = null;
      pooledResources = super.available.toArray();
      ConnectionInfo returnPooledResource = null;
      int numEntries = super.available.size();
      long lastUsedTime = -1L;
      if (pooledResources != null && numEntries > 0) {
         for(int i = 0; i < numEntries; ++i) {
            ConnectionInfo tmpConnectionInfo = (ConnectionInfo)pooledResources[i];
            if (lastUsedTime == -1L || returnPooledResource.getLastUsedTime() > tmpConnectionInfo.getLastUsedTime()) {
               returnPooledResource = tmpConnectionInfo;
               lastUsedTime = tmpConnectionInfo.getLastUsedTime();
            }
         }
      }

      return returnPooledResource;
   }

   public int getConnectionsMatchedTotalCount() {
      return this.numMatchSuccesses.get();
   }

   public int getConnectionsRejectedTotalCount() {
      return this.numRequestsRejected.get();
   }

   public int getNumRecycled() {
      return this.numRecycled;
   }

   public int getNumLeaked() {
      return this.numLeaks;
   }

   public int getNumReserves() {
      return this.numReserves.get();
   }

   public int getNumReleases() {
      return this.numReleases.get();
   }

   public int getLeakProfileCount() {
      return this.leakProfiles.size();
   }

   public ConnectionLeakProfile[] getConnectionLeakProfiles() {
      return (ConnectionLeakProfile[])((ConnectionLeakProfile[])this.leakProfiles.toArray(new ConnectionLeakProfile[this.leakProfiles.size()]));
   }

   public ConnectionLeakProfile[] getConnectionLeakProfiles(int startIndex, int count) {
      return this.getArray(this.leakProfiles, startIndex, count);
   }

   public int getIdleProfileCount() {
      return this.idleProfiles.size();
   }

   public ConnectionLeakProfile[] getConnectionIdleProfiles() {
      return (ConnectionLeakProfile[])((ConnectionLeakProfile[])this.idleProfiles.toArray(new ConnectionLeakProfile[this.idleProfiles.size()]));
   }

   public ConnectionLeakProfile[] getConnectionIdleProfiles(int startIndex, int count) {
      return this.getArray(this.idleProfiles, startIndex, count);
   }

   public Object getProxyTestConnectionHandle() {
      return this.proxyTestConnectionHandle;
   }

   public OutboundInfo getOutboundInfo() {
      return this.initOutboundInfo;
   }

   public ResourceRegistrationManager getResourceRegistrationManager() {
      return this.resRegManager;
   }

   ConnectionHandler getConnectionHandler(ManagedConnection mc) {
      ConnectionHandler connHandler = null;
      synchronized(this) {
         Iterator iter = this.reserved.iterator();

         while(iter.hasNext()) {
            ConnectionHandler ch = ((ConnectionInfo)((ConnectionInfo)iter.next())).getConnectionHandler();
            ManagedConnection mcReserved = ch.getManagedConnection();
            if (mcReserved == mc) {
               connHandler = ch;
               break;
            }
         }

         return connHandler;
      }
   }

   boolean getCanUseProxy() {
      return this.canUseProxy;
   }

   ConnectionManagerImpl getConnMgr() {
      return this.connMgr;
   }

   Object getConnectionFactoryNoCreate() {
      return this.connectionFactory;
   }

   Object getConnectionFactory() throws javax.resource.ResourceException, ResourceAdapterInternalException {
      if (Debug.verbose) {
         Debug.enter(this, "getConnectionFactory()");
      }

      Object var6;
      try {
         if (this.connectionFactory == null) {
            if (Debug.verbose) {
               Debug.println("Creating new connection factory using ConnectionManager = " + this.getConnMgr());
            }

            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            Debug.showClassLoaders(this, this.managedConnectionFactory);
            this.connectionFactory = this.getRAInstanceManager().getAdapterLayer().createConnectionFactory(this.managedConnectionFactory, this.getConnMgr(), kernelId);
            if (this.connectionFactory == null) {
               Debug.logCreateCFReturnedNull(this.poolName);
               String exMsg = Debug.getExceptionMCFCreateCFReturnedNull();
               throw new ResourceAdapterInternalException(exMsg);
            }

            Debug.showClassLoaders(this, this.connectionFactory);
         }

         var6 = this.connectionFactory;
      } finally {
         if (Debug.verbose) {
            Debug.exit(this, "getConnectionFactory()");
         }

      }

      return var6;
   }

   private ConnectionLeakProfile[] getArray(Vector ConnectionLeakProfileList, int startIndex, int count) {
      Vector tmpVector = new Vector();

      Object profile;
      try {
         for(int i = 0; i < count && startIndex + i < ConnectionLeakProfileList.size() && (profile = ConnectionLeakProfileList.get(startIndex + i)) != null; ++i) {
            tmpVector.add((ConnectionLeakProfile)profile);
         }
      } catch (ArrayIndexOutOfBoundsException var7) {
      }

      return (ConnectionLeakProfile[])tmpVector.toArray(new ConnectionLeakProfile[tmpVector.size()]);
   }

   public String getDisplayName() {
      return this.initOutboundInfo.getDisplayName();
   }

   public XATxConnectionHandler findXATxConnectionHandler() {
      if (TransactionSupportLevel.XATransaction != this.getRuntimeTransactionSupportLevel()) {
         return null;
      } else {
         XATxConnectionHandler handler = null;
         PooledResource[] resources = this.getResources();
         if (resources != null && resources.length > 0) {
            ConnectionInfo info = (ConnectionInfo)((ConnectionInfo)resources[0]);
            handler = (XATxConnectionHandler)info.getConnectionHandler();
         }

         return handler;
      }
   }

   public XATxConnectionHandler reserveInternal(ConnectionHandler connectionHandler) throws ResourceException {
      PooledResourceInfo pooledResInfo = null;
      ConnectionInfo info;
      if (this.isWLSMessagingBridgeConnection()) {
         info = connectionHandler.getConnectionInfo();
         if (info != null) {
            pooledResInfo = info.getPooledResourceInfo();
         }
      }

      info = (ConnectionInfo)super.reserveResource(-1, pooledResInfo);
      if (info != null) {
         XATxConnectionHandler handler = (XATxConnectionHandler)info.getConnectionHandler();
         return handler;
      } else {
         return null;
      }
   }

   public OutboundAdapterType getXMLBean(ConnectorDiagnosticImageSource src) {
      OutboundAdapterType outboundXBean = OutboundAdapterType.Factory.newInstance();
      outboundXBean.setJndiName(this.getJNDIName());
      outboundXBean.setState(this.getState());
      outboundXBean.setMaxCapacity(this.getMaxCapacity());
      outboundXBean.setConnectionsInFreePool(this.getNumAvailable());
      outboundXBean.setConnectionsInUse(this.getNumReserved());
      HealthState poolHealth = this.getRuntimeMBean().getHealthState();
      outboundXBean.addNewHealth();
      outboundXBean.getHealth().setState(HealthState.mapToString(poolHealth.getState()));
      if (poolHealth.getReasonCode() != null) {
         outboundXBean.getHealth().setReasonArray(poolHealth.getReasonCode());
      }

      boolean timedout = src != null ? src.timedout() : false;
      if (timedout) {
         return outboundXBean;
      } else {
         ManagedConnectionType[] mcXBeans = null;
         PooledResource[] resources = this.getResources();
         if (resources != null) {
            mcXBeans = new ManagedConnectionType[resources.length];

            for(int idx = 0; idx < resources.length; ++idx) {
               ConnectionInfo info = (ConnectionInfo)((ConnectionInfo)resources[idx]);
               mcXBeans[idx] = info.getXMLBean(src);
            }
         }

         outboundXBean.setManagedConnectionArray(mcXBeans);
         return outboundXBean;
      }
   }

   public void incrementCloseCount() {
      this.closeCount.getAndIncrement();
   }

   public long getCloseCount() {
      return this.closeCount.get();
   }

   public synchronized void updatePoolStats() {
      this.updateFreePoolStats();
      this.updatePoolSizeStats();
   }

   public synchronized void updateFreePoolStats() {
      long freePoolSize = (long)this.getNumAvailable();
      if (freePoolSize < this.freePoolSizeLowWaterMark) {
         this.freePoolSizeLowWaterMark = freePoolSize;
      } else if (freePoolSize > this.freePoolSizeHighWaterMark) {
         this.freePoolSizeHighWaterMark = freePoolSize;
      }

   }

   public long getFreePoolSizeHighWaterMark() {
      return this.freePoolSizeHighWaterMark;
   }

   public long getFreePoolSizeLowWaterMark() {
      return this.freePoolSizeLowWaterMark;
   }

   public synchronized void updatePoolSizeStats() {
      long currentCapacity = (long)this.getCurrCapacity();
      if (currentCapacity < this.freePoolSizeLowWaterMark) {
         this.poolSizeLowWaterMark = currentCapacity;
      } else if (currentCapacity > this.poolSizeHighWaterMark) {
         this.poolSizeHighWaterMark = currentCapacity;
      }

   }

   public long getPoolSizeHighWaterMark() {
      return this.poolSizeHighWaterMark;
   }

   public long getPoolSizeLowWaterMark() {
      return this.poolSizeLowWaterMark;
   }

   public String getManagedConnectionFactoryClassName() throws RAOutboundException {
      return this.initOutboundInfo.getMCFClass();
   }

   public String getConnectionFactoryClassName() throws RAOutboundException {
      return this.initOutboundInfo.getCFImpl();
   }

   public boolean isTestable() {
      return this.getManagedConnectionFactory() instanceof ValidatingManagedConnectionFactory;
   }

   public boolean isProxyOn() {
      return this.canUseProxy;
   }

   public void incrementConnectionsDestroyedByErrorCount() {
      this.connectionsDestroyedByErrorCount.getAndIncrement();
   }

   public int getConnectionsDestroyedByErrorCount() {
      return this.connectionsDestroyedByErrorCount.get();
   }

   public boolean testPool() {
      boolean noErrors = true;
      synchronized(this) {
         ConnectionInfo connectionInfo = null;
         Object[] pooledResources = super.available.toArray();
         int numEntries = super.available.size();
         if (pooledResources != null && numEntries > 0) {
            for(int i = 0; i < numEntries; ++i) {
               connectionInfo = (ConnectionInfo)pooledResources[i];

               try {
                  connectionInfo.test();
                  if (connectionInfo.hasError()) {
                     noErrors = false;
                  }
               } catch (Exception var9) {
                  noErrors = false;
               }
            }
         }

         return noErrors;
      }
   }

   public void applyPoolParamChanges(Properties props) {
      if (props != null && props.size() > 0) {
         Enumeration propNames = props.propertyNames();

         while(propNames.hasMoreElements()) {
            String propName = (String)propNames.nextElement();
            String propValue = props.getProperty(propName);

            try {
               this.applyPoolParamChange(propName, propValue);
            } catch (ResourceException var7) {
               String msgId = Debug.logFailedToApplyPoolChanges(var7.toString());
               Debug.logStackTrace(msgId, var7);
            }
         }
      }

   }

   public void applyPoolParamChange(String name, String value) throws ResourceException {
      if (name.equalsIgnoreCase("initialCapacity")) {
         this.setInitialCapacity(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("maxCapacity")) {
         this.setMaximumCapacity(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("capacityIncrement")) {
         this.setCapacityIncrement(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("shrinkFrequencySeconds")) {
         this.setShrinkFrequencySeconds(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("inactiveResTimeoutSeconds")) {
         this.setInactiveResourceTimeoutSeconds(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("maxWaiters")) {
         this.setHighestNumWaiters(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("maxUnavl")) {
         this.setHighestNumUnavailable(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("resCreationRetrySeconds")) {
         this.setResourceCreationRetrySeconds(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("resvTimeoutSeconds")) {
         this.setResourceReserveTimeoutSeconds(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("testFrequencySeconds")) {
         this.setTestFrequencySeconds(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("harvestFreqSecsonds")) {
         this.setProfileHarvestFrequencySeconds(Integer.parseInt(value));
      } else if (name.equalsIgnoreCase("shrinkEnabled")) {
         this.setShrinkEnabled(Boolean.parseBoolean(value));
      } else if (name.equalsIgnoreCase("testOnCreate")) {
         this.setTestOnCreate(Boolean.parseBoolean(value));
      } else if (name.equalsIgnoreCase("testOnRelease")) {
         this.setTestOnRelease(Boolean.parseBoolean(value));
      } else if (name.equalsIgnoreCase("testOnReserve")) {
         this.setTestOnReserve(Boolean.parseBoolean(value));
      }

   }

   public void applyLoggingChanges(Properties props, OutboundInfo updateOutboundInfo) {
      if (props != null && props.size() > 0) {
         this.pendingOutboundInfo = updateOutboundInfo;
         this.setLogger();
      }

   }

   protected void setLogger() {
      OutboundInfo outboundInfo = this.getDynamicOutboundInfo();

      String mcfClass;
      try {
         mcfClass = outboundInfo.getMCFClass();
      } catch (RAOutboundException var6) {
         mcfClass = "[unknown MCF class]";
      }

      try {
         if (Debug.isRALifecycleEnabled()) {
            Debug.raLifecycle("Creating logfile '" + outboundInfo.getLogFilename() + "' for ManagedConnectionFactory '" + mcfClass + "' of RA module '" + outboundInfo.getRAInfo().getModuleName() + "'");
         }

         OutputStream logOutStream = null;
         if (outboundInfo.isLoggingEnabled()) {
            this.setLoggingBeanAdapter((LoggingBeanAdapter)null);
            logOutStream = null;
         }

         if (outboundInfo.getLogFilename() != null && outboundInfo.getLogFilename().length() != 0) {
            this.setLoggingBeanAdapter(this.createLoggingBeanAdapter(outboundInfo));
            logOutStream = this.loggingBeanAdapter.getOutputStream();
         } else {
            this.setLoggingBeanAdapter((LoggingBeanAdapter)null);
            logOutStream = new LoggingOutputStream(this.getKey(), WLLevel.TRACE);
         }

         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         PrintWriter out;
         if (logOutStream != null) {
            out = new PrintWriter((OutputStream)logOutStream, true);
         } else {
            out = null;
         }

         this.getRAInstanceManager().getAdapterLayer().setLogWriter(this.managedConnectionFactory, out, kernelId);
      } catch (javax.resource.ResourceException var7) {
         Throwable cause = var7.getCause();
         String msgId = Debug.logSetLogWriterErrorWithCause(mcfClass, var7.toString(), cause != null && cause != var7 ? cause.toString() : "");
         Debug.logStackTrace(msgId, var7);
         if (cause != null && cause != var7) {
            Debug.logStackTrace(msgId, cause);
         }
      } catch (Throwable var8) {
         String msgId = Debug.logSetLogWriterError(mcfClass);
         Debug.logStackTrace(msgId, var8);
      }

   }

   private String getLogPath(String path) {
      return path != null && !path.isEmpty() ? LogFileConfigUtil.computePathRelativeServersLogsDir(path) : null;
   }

   private void setLoggingBeanAdapter(LoggingBeanAdapter newLoggingBeanAdapter) {
      if (this.loggingBeanAdapter != null) {
         OutputStream os = this.loggingBeanAdapter.getOutputStream();

         try {
            os.flush();
            os.close();
         } catch (IOException var5) {
            Debug.logFailedToCloseLog(this.getKey(), var5.toString());
            Debug.println("WARNING:  Failed to flush and close the logging OutputStream for pool:  " + this.getKey() + ":  " + var5);
         }
      }

      this.loggingBeanAdapter = newLoggingBeanAdapter;
      if (this.loggingBeanAdapter != null) {
         try {
            LogFileConfigBean logFileConfig = LogFileConfigUtil.getLogFileConfig(this.loggingBeanAdapter);
            logFileConfig.setBaseLogFileName(this.getLogPath(this.loggingBeanAdapter.getFileName()));
            logFileConfig.setLogFileRotationDir(this.getLogPath(this.loggingBeanAdapter.getLogFileRotationDir()));
            RotatingFileOutputStream logOutStream = new RotatingFileOutputStream(logFileConfig);
            this.loggingBeanAdapter.setOutputStream(logOutStream);
         } catch (IOException var4) {
            Debug.logFailedToCreateLogStream(this.getKey(), var4.toString());
            Debug.println("Failed to create the logging OutputStream for pool:  " + this.getKey() + ":  " + var4);
         }
      }

   }

   public Boolean getUseConnectionProxies() {
      return this.initOutboundInfo.getUseConnectionProxies();
   }

   public int getAlternateCount() {
      return this.alternateCount.incrementAndGet();
   }

   public ResourcePoolProfiler getProfiler() {
      return this.profiler;
   }

   private LoggingBeanAdapter createLoggingBeanAdapter(OutboundInfo outboundInfo) {
      Debug.enter(this, ".createLoggingBeanAdapter()");
      LoggingBeanAdapter logBeanAdapter = new LoggingBeanAdapter(outboundInfo.getLoggingBean());
      logBeanAdapter.setFileCount(outboundInfo.getFileCount());
      logBeanAdapter.setFileMinSize(outboundInfo.getFileSizeLimit());
      logBeanAdapter.setFileTimeSpan(outboundInfo.getFileTimeSpan());
      logBeanAdapter.setLogFileRotationDir(outboundInfo.getLogFileRotationDir());
      logBeanAdapter.setFileName(outboundInfo.getLogFilename());
      logBeanAdapter.setNumberOfFilesLimited(outboundInfo.isNumberOfFilesLimited());
      logBeanAdapter.setRotateLogOnStartup(outboundInfo.isRotateLogOnStartup());
      logBeanAdapter.setRotationTime(outboundInfo.getRotationTime());
      logBeanAdapter.setRotationType(outboundInfo.getRotationType());

      try {
         this.logRuntime = new LogRuntime(logBeanAdapter, this.rMBean);
      } catch (ManagementException var7) {
      } finally {
         Debug.exit(this, ".createLoggingBeanAdapter()");
      }

      return logBeanAdapter;
   }

   public void forceLogRotation() throws ManagementException {
      if (this.loggingBeanAdapter == null) {
         String msg = Debug.getFailedToForceLogRotation(this.getKey());
         throw new ManagementException(msg);
      } else {
         this.logRuntime.forceLogRotation();
      }
   }

   public void ensureLogOpened() throws ManagementException {
      if (this.loggingBeanAdapter != null) {
         this.logRuntime.ensureLogOpened();
      }

   }

   public void flushLog() throws ManagementException {
      if (this.loggingBeanAdapter != null) {
         this.logRuntime.flushLog();
      }

   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public SortedSet getRotatedLogFiles() {
      return this.logRuntime.getRotatedLogFiles();
   }

   public String getCurrentLogFile() {
      return this.logRuntime.getCurrentLogFile();
   }

   public String getLogRotationDir() {
      return this.logRuntime.getLogRotationDir();
   }

   public void setupRuntime(ConnectorComponentRuntimeMBeanImpl parent, RAOutboundManager poolsManager) {
      try {
         this.connRuntimeMbean = parent;
         String appId = this.raOutboundManager.getRA().getApplicationId();
         this.rMBean = this.createConnectionPoolRuntimeMBean(parent, poolsManager, appId, this.componentName);
         parent.addConnPoolRuntime(this.rMBean);
      } catch (Exception var4) {
         Debug.logInitCPRTMBeanError(this.poolName, StackTraceUtils.throwable2StackTrace(var4));
      }

   }

   protected ConnectionPoolRuntimeMBeanImpl createConnectionPoolRuntimeMBean(ConnectorComponentRuntimeMBeanImpl parent, RAOutboundManager poolsManager, String applicationId, String componentName) throws ManagementException {
      return new ConnectionPoolRuntimeMBeanImpl(applicationId, componentName, this, parent, poolsManager);
   }

   private OutboundInfo getDynamicOutboundInfo() {
      return this.pendingOutboundInfo != null ? this.pendingOutboundInfo : this.initOutboundInfo;
   }

   public boolean isShutdown() {
      return this.shutdown;
   }

   public String toString() {
      return super.toString() + "-" + this.poolName;
   }

   public boolean isWLSMessagingBridgeConnection() {
      return this.isWLSMessagingBridgeConnection;
   }

   public boolean isRmHealthy() {
      return this.rmHealthy;
   }

   public void setRmHealthy(boolean rmHealthy) {
      this.rmHealthy = rmHealthy;
   }

   public ResourcePoolMaintainer getMaintainer() {
      if (this.maintainer == null) {
         this.maintainer = new ConnectionPoolMaintainer(this);
      }

      return this.maintainer;
   }

   public ReentrantReadWriteLock getRwLock4ReregisterXAResource() {
      return this.rwLock4ReregisterXAResource;
   }

   public boolean isShareAllowed() {
      return this.isShareAllowed;
   }

   public static boolean isUnshareableMCF(Class mcfClass) {
      boolean isUnshareable = mcfClass.isAnnotationPresent(Unshareable.class);
      if (isUnshareable && Debug.isSecurityCtxEnabled()) {
         Debug.securityCtx("Find Unshareable annotation on MCF class: " + mcfClass);
      }

      return isUnshareable;
   }

   protected void destroyResource(PooledResource res) {
      if (Debug.isPoolVerboseEnabled()) {
         Debug.poolVerbose("Entering ConnectionPool.destroyResource() with ConnectionInfo  " + res);
      }

      ConnectionInfo info = (ConnectionInfo)res;
      if (info.physicallyDestroyed.compareAndSet(false, true)) {
         super.destroyResource(res);
      }

   }

   protected void forceDestroyResource(PooledResource res) {
      if (Debug.isPoolVerboseEnabled()) {
         Debug.poolVerbose("Entering ConnectionPool.forceDestroyResource() with ConnectionInfo  " + res);
      }

      ConnectionInfo info = (ConnectionInfo)res;
      if (info.physicallyDestroyed.compareAndSet(false, true)) {
         super.forceDestroyResource(res);
      }

   }

   static {
      _WLDF$INST_FLD_Connector_Reserve_Connection_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Reserve_Connection_Low");
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_FLD_Connector_Release_Connection_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Release_Connection_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionPool.java", "weblogic.connector.outbound.ConnectionPool", "reserveResource", "(ILweblogic/common/resourcepool/PooledResourceInfo;)Lweblogic/common/resourcepool/PooledResource;", 666, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_After_Outbound", "Connector_Around_Outbound", "Connector_Before_Outbound", "Connector_Reserve_Connection_Low"}, new PointcutHandlingInfo[]{null, null, null, InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionPoolRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Reserve_Connection_Low, _WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionPool.java", "weblogic.connector.outbound.ConnectionPool", "releaseResource", "(Lweblogic/common/resourcepool/PooledResource;)V", 774, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Release_Connection_Low", "Connector_After_Outbound", "Connector_Around_Outbound", "Connector_Before_Outbound"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionPoolRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), null, null, null}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Release_Connection_Low};
   }
}
