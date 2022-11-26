package weblogic.connector.outbound;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Map;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ResourceAdapterInternalException;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.TempResourceException;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConnectionFactory implements PooledResourceFactory {
   private ConnectionPool connectionPool;
   private boolean connectionProxyChecked;
   static final long serialVersionUID = 3702342262897055916L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.outbound.ConnectionFactory");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public ConnectionFactory(ConnectionPool aConnectionPool) throws ResourceException {
      this.connectionPool = aConnectionPool;
      this.connectionProxyChecked = false;
   }

   public PooledResource createResource(PooledResourceInfo connectionReqInfo) throws ResourceException {
      LocalHolder var15;
      if ((var15 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var15.argsCapture) {
            var15.args = InstrumentationSupport.toSensitive(2);
         }

         if (var15.monitorHolder[1] != null) {
            var15.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var15);
            InstrumentationSupport.preProcess(var15);
         }

         if (var15.monitorHolder[2] != null) {
            var15.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var15);
            InstrumentationSupport.process(var15);
         }

         var15.resetPostBegin();
      }

      ConnectionInfo var10000;
      try {
         Debug.enter(this, "createResource(...)");
         SecurityContext secCtx = null;
         String transSupport = this.connectionPool.getRuntimeTransactionSupportLevel().toString();
         ManagedConnection managedConnection = null;
         ConnectionInfo connectionInfo = null;
         long creationTime = 0L;
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         String msgId;
         String exMsg;
         String exMsg;
         try {
            Debug.println("Create the Security Context");
            if (connectionReqInfo == null) {
               secCtx = this.connectionPool.createSecurityContext((ConnectionRequestInfo)null, true, kernelId);
            } else {
               secCtx = ((ConnectionReqInfo)connectionReqInfo).getSecurityContext();
            }

            Debug.println("Create the managed connection");
            long startTime = System.currentTimeMillis();
            managedConnection = this.connectionPool.getRAInstanceManager().getAdapterLayer().createManagedConnection(this.connectionPool.getManagedConnectionFactory(), secCtx.getSubject(), secCtx.getClientInfo(), kernelId);
            if (this.connectionPool.isWLSMessagingBridgeConnection()) {
               synchronized(this.connectionPool) {
                  if (this.connectionPool.getRecoveryWrapper() == null) {
                     this.connectionPool.setupForXARecovery();
                  }
               }
            }

            long endTime = System.currentTimeMillis();
            creationTime = endTime - startTime;
         } catch (TempResourceException var33) {
            exMsg = this.connectionPool.getRAInstanceManager().getAdapterLayer().toString(var33, kernelId);
            if (Debug.isPoolingEnabled()) {
               msgId = Debug.logCreateManagedConnectionException(this.connectionPool.getName(), exMsg);
               Debug.logStackTrace(msgId, var33);
            }

            msgId = Debug.getExceptionCreateMCFailed(exMsg);
            throw new ResourceException(msgId, var33);
         } catch (javax.resource.ResourceException var34) {
            exMsg = this.connectionPool.getRAInstanceManager().getAdapterLayer().toString(var34, kernelId);
            msgId = Debug.logCreateManagedConnectionException(this.connectionPool.getName(), exMsg);
            if (Debug.isPoolingEnabled()) {
               Debug.logStackTrace(msgId, var34);
            }

            exMsg = Debug.getExceptionCreateMCFailed(var34.toString());
            throw new ResourceException(exMsg, var34);
         } catch (Throwable var35) {
            exMsg = Debug.getExceptionCreateMCFailed(var35.toString());
            ResourceAdapterInternalException javaResEx = new ResourceAdapterInternalException(exMsg, var35);
            exMsg = this.connectionPool.getRAInstanceManager().getAdapterLayer().toString(javaResEx, kernelId);
            String msgId = Debug.logCreateManagedConnectionException(this.connectionPool.getName(), exMsg);
            if (Debug.isConnectionsEnabled()) {
               Debug.logStackTrace(msgId, javaResEx);
            }

            throw new ResourceException(exMsg, javaResEx);
         }

         if (managedConnection == null) {
            Debug.logCreateManagedConnectionError(this.connectionPool.getName());
            String exMsg = Debug.getExceptionMCFCreateManagedConnectionReturnedNull();
            ResourceAdapterInternalException javaResEx = new ResourceAdapterInternalException(exMsg);
            throw new ResourceException(exMsg, javaResEx);
         }

         try {
            connectionInfo = ConnectionInfo.createConnectionInfo(this.connectionPool, transSupport, managedConnection, secCtx);
            connectionInfo.setCreationDurationTime(creationTime);
         } catch (javax.resource.ResourceException var31) {
            this.cleanupManagedConnectionAfterFailure(managedConnection);
            exMsg = Debug.getExceptionFailedMCSetup();
            throw new ResourceException(exMsg, var31);
         }

         if (managedConnection != null && !this.connectionProxyChecked) {
            try {
               if (this.connectionPool.getUseConnectionProxies() == null) {
                  this.testConnectionProxyViability(managedConnection, secCtx);
               } else {
                  this.connectionPool.setCanUseProxy(this.connectionPool.getUseConnectionProxies());
                  if (Debug.isPoolingEnabled()) {
                     Debug.pooling("For pool '" + this.connectionPool.getName() + "', the user has specified that use-connection-proxies is '" + this.connectionPool.getUseConnectionProxies() + "'");
                  }
               }
            } finally {
               this.connectionProxyChecked = true;
               Debug.exit(this, "createResource(...)");
            }
         }

         var10000 = connectionInfo;
      } catch (Throwable var36) {
         if (var15 != null) {
            var15.th = var36;
            var15.ret = null;
            if (var15.monitorHolder[1] != null) {
               var15.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var15);
               InstrumentationSupport.postProcess(var15);
            }

            if (var15.monitorHolder[0] != null) {
               var15.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var15);
               InstrumentationSupport.process(var15);
            }
         }

         throw var36;
      }

      if (var15 != null) {
         var15.ret = var10000;
         if (var15.monitorHolder[1] != null) {
            var15.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var15);
            InstrumentationSupport.postProcess(var15);
         }

         if (var15.monitorHolder[0] != null) {
            var15.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var15);
            InstrumentationSupport.process(var15);
         }
      }

      return var10000;
   }

   public void refreshResource(PooledResource unused) throws ResourceException {
   }

   private void cleanupManagedConnectionAfterFailure(ManagedConnection mc) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         this.connectionPool.getRAInstanceManager().getAdapterLayer().destroy(mc, kernelId);
      } catch (javax.resource.ResourceException var6) {
         if (Debug.isConnectionsEnabled()) {
            Debug.connections("WARNING: For pool '" + this.connectionPool.getName() + "':  Failed to destroy managed connection after createConnectionInfo failed:  " + this.connectionPool.getRAInstanceManager().getAdapterLayer().toString(var6, kernelId));
            Throwable causeEx = this.connectionPool.getRAInstanceManager().getAdapterLayer().getCause(var6, kernelId);
            if (causeEx != null) {
               String causeString = this.connectionPool.getRAInstanceManager().getAdapterLayer().toString(causeEx, kernelId);
               Debug.connections("LinkedException:  " + causeString);
            }
         }
      }

   }

   private synchronized void testConnectionProxyViability(ManagedConnection managedConnection, SecurityContext secCtx) {
      if (!this.connectionProxyChecked) {
         String key = this.connectionPool.getKey();
         Debug.logProxyTestStarted(key);
         Object connectionFactory = null;
         Throwable testEx = null;
         ConnectionManagerImpl connMgr = this.connectionPool.getConnMgr();
         connMgr.setTestingProxy(true);
         connMgr.setTestingProxyThread(Thread.currentThread());
         connMgr.setMgdConnForTest(managedConnection);
         connMgr.setSecurityContext(secCtx);

         try {
            connectionFactory = this.connectionPool.getConnectionFactory();
            Class cfClass = connectionFactory.getClass();
            Method getConnectionMethod = cfClass.getMethod("getConnection", (Class[])null);
            if (getConnectionMethod != null) {
               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               Object conn = this.connectionPool.getRAInstanceManager().getAdapterLayer().invoke(getConnectionMethod, connectionFactory, (Object[])null, kernelId);
               this.closeProxyTestConnection(conn);
            }
         } catch (IllegalAccessException var17) {
            testEx = var17;
         } catch (IllegalArgumentException var18) {
            testEx = var18;
         } catch (InvocationTargetException var19) {
            testEx = var19;
         } catch (Throwable var20) {
            Throwable cause = var20.getCause();
            testEx = cause != null ? cause : var20;
            if (cause instanceof ClassCastException) {
               this.closeProxyTestConnection(this.connectionPool.getProxyTestConnectionHandle());
            }
         } finally {
            connMgr.setTestingProxy(false);
            connMgr.setMgdConnForTest((ManagedConnection)null);
            connMgr.setTestingProxyThread((Thread)null);
            this.connectionProxyChecked = true;
            this.connectionPool.setCanUseProxy(testEx == null);
            if (testEx == null) {
               Debug.logProxyTestSuccess(key);
            } else {
               this.logProxyTestFailure(key, (Throwable)testEx);
            }

         }
      }

   }

   private void logProxyTestFailure(String key, Throwable ex) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      String exString = this.connectionPool.getRAInstanceManager().getAdapterLayer().toString(ex, kernelId);
      if (Debug.isConnectionsEnabled()) {
         Debug.logProxyTestError(key, ex);
      } else {
         Debug.logProxyTestFailureInfo(key, exString);
      }

   }

   private void closeProxyTestConnection(Object conn) {
      Class connClass = conn.getClass();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         Method closeMethod = connClass.getMethod("close", (Class[])null);
         this.connectionPool.getRAInstanceManager().getAdapterLayer().invoke(closeMethod, conn, (Object[])null, kernelId);
      } catch (NoSuchMethodException var10) {
         Debug.logCloseNotFoundOnHandle(this.connectionPool.getName());
      } catch (Exception var11) {
         this.logProxyTestFailure(this.connectionPool.getKey(), var11);
      } finally {
         this.connectionPool.setProxyTestConnectionHandle((Object)null);
      }

   }

   static {
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionFactory.java", "weblogic.connector.outbound.ConnectionFactory", "createResource", "(Lweblogic/common/resourcepool/PooledResourceInfo;)Lweblogic/common/resourcepool/PooledResource;", 60, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound};
   }
}
