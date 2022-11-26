package weblogic.jdbc.common.internal;

import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ObjectLifeCycle;
import weblogic.common.resourcepool.ResourcePermissionsException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCOracleParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.jta.DataSource;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

public final class ConnectionPoolManager implements ObjectLifeCycle, Remote {
   private static HashMap cpList;
   private static HashMap mpList;
   private static HashMap hacpList;
   private static Object lockObject;
   private AuthorizationManager am = null;
   private static final AuthenticatedSubject KERNELID;
   static final long serialVersionUID = 7709752608391143283L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.common.internal.ConnectionPoolManager");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Reserve_Connection_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Reserve_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Release_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Release_Connection_Internal;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public ConnectionPoolManager() {
      if (!KernelStatus.isJ2eeClient() && !KernelStatus.isDeployer()) {
         this.am = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNELID, "weblogicDEFAULT", ServiceType.AUTHORIZE);
         if (this.am == null) {
            throw new RuntimeException("Security Services Unavailable");
         }
      }

   }

   public static ConnectionEnv reserve(String poolName, String appName, String moduleName, String compName, int waitSeconds) throws ResourceException, SQLException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[5];
            Object[] var10000 = var5.args;
            var10000[0] = poolName;
            var10000[1] = appName;
            var10000[2] = moduleName;
            var10000[3] = compName;
            var10000[4] = InstrumentationSupport.convertToObject(waitSeconds);
         }

         var5.resetPostBegin();
      }

      ConnectionEnv var8;
      try {
         var8 = reserve(SecurityServiceManager.getCurrentSubject(KERNELID), poolName, appName, moduleName, compName, waitSeconds, (Properties)null);
      } catch (Throwable var7) {
         if (var5 != null) {
            var5.th = var7;
            var5.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.process(var5);
         }

         throw var7;
      }

      if (var5 != null) {
         var5.ret = var8;
         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.process(var5);
      }

      return var8;
   }

   public static ConnectionEnv reserve(String poolName, String appName, String moduleName, String compName, int waitSeconds, Properties requestedLabels) throws ResourceException, SQLException {
      return reserve(SecurityServiceManager.getCurrentSubject(KERNELID), poolName, appName, moduleName, compName, waitSeconds, requestedLabels);
   }

   public static ConnectionEnv reserve(String poolName, String appName, String moduleName, String compName, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException, SQLException {
      return reserve(SecurityServiceManager.getCurrentSubject(KERNELID), poolName, appName, moduleName, compName, waitSeconds, requestedLabels, username, password);
   }

   public static ConnectionEnv reserve(AuthenticatedSubject user, String poolName, String appName, String moduleName, String compName, int waitSeconds) throws ResourceException, SQLException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[6];
            Object[] var10000 = var6.args;
            var10000[0] = user;
            var10000[1] = poolName;
            var10000[2] = appName;
            var10000[3] = moduleName;
            var10000[4] = compName;
            var10000[5] = InstrumentationSupport.convertToObject(waitSeconds);
         }

         if (var6.monitorHolder[0] != null) {
            var6.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var6);
            InstrumentationSupport.preProcess(var6);
         }

         var6.resetPostBegin();
      }

      ConnectionEnv var9;
      try {
         var9 = reserve(user, poolName, appName, moduleName, compName, waitSeconds, (Properties)null);
      } catch (Throwable var8) {
         if (var6 != null) {
            var6.th = var8;
            var6.ret = null;
            if (var6.monitorHolder[1] != null) {
               var6.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var6);
               InstrumentationSupport.process(var6);
            }

            if (var6.monitorHolder[0] != null) {
               var6.monitorIndex = 0;
               InstrumentationSupport.postProcess(var6);
            }
         }

         throw var8;
      }

      if (var6 != null) {
         var6.ret = var9;
         if (var6.monitorHolder[1] != null) {
            var6.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var6);
            InstrumentationSupport.process(var6);
         }

         if (var6.monitorHolder[0] != null) {
            var6.monitorIndex = 0;
            InstrumentationSupport.postProcess(var6);
         }
      }

      return var9;
   }

   public static ConnectionEnv reserve(AuthenticatedSubject user, String poolName, String appName, String moduleName, String compName, int waitSeconds, Properties requestedLabels) throws ResourceException, SQLException {
      return reserve(user, poolName, appName, moduleName, compName, waitSeconds, (Properties)null, (String)null, (String)null);
   }

   public static ConnectionEnv reserve(AuthenticatedSubject user, String poolName, String appName, String moduleName, String compName, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException, SQLException {
      MultiPool mp = (MultiPool)getPool(mpList, poolName, appName, moduleName, compName);
      ConnectionEnv ret;
      if (mp != null) {
         ret = mp.findPool(user, waitSeconds, username, password);
      } else {
         JDBCConnectionPool cp = (JDBCConnectionPool)getPool(cpList, poolName, appName, moduleName, compName);
         if (cp != null) {
            ret = cp.reserve(user, waitSeconds, requestedLabels, username, password);
         } else {
            JDBCConnectionPool hacp = (JDBCConnectionPool)getPool(hacpList, poolName, appName, moduleName, compName);
            if (hacp == null) {
               throw new SQLException("Data Source " + poolName + " does not exist.");
            }

            ret = hacp.reserve(user, waitSeconds, requestedLabels, username, password);
         }
      }

      if (ret != null) {
         ret.setIdentity((String)null, user);
      }

      return ret;
   }

   public static ConnectionEnv reserve(AuthenticatedSubject user, String poolName, String appName, String moduleName, String compName) throws ResourceException, SQLException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[5];
            Object[] var10000 = var5.args;
            var10000[0] = user;
            var10000[1] = poolName;
            var10000[2] = appName;
            var10000[3] = moduleName;
            var10000[4] = compName;
         }

         var5.resetPostBegin();
      }

      ConnectionEnv var8;
      try {
         var8 = reserve(user, poolName, appName, moduleName, compName, -2);
      } catch (Throwable var7) {
         if (var5 != null) {
            var5.th = var7;
            var5.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.process(var5);
         }

         throw var7;
      }

      if (var5 != null) {
         var5.ret = var8;
         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.process(var5);
      }

      return var8;
   }

   public static ConnectionEnv reserve(AuthenticatedSubject user, String poolName, String appName, String moduleName, String compName, Properties labels) throws ResourceException, SQLException {
      return reserve(user, poolName, appName, moduleName, compName, -2, labels);
   }

   public static ConnectionEnv reserve(AuthenticatedSubject user, String poolName, String appName, String moduleName, String compName, Properties labels, String username, String password) throws ResourceException, SQLException {
      return reserve(user, poolName, appName, moduleName, compName, -2, labels, username, password);
   }

   public static void release(ConnectionEnv cc) throws ResourceException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[1];
            var2.args[0] = cc;
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         var2.resetPostBegin();
      }

      try {
         if (cc == null) {
            throw new ResourceException("No connection specified to be released");
         }

         JDBCConnectionPool cp = (JDBCConnectionPool)getPool(cpList, cc.getPoolName(), cc.getAppName(), cc.getModuleName(), cc.getCompName());
         if (cp == null) {
            cp = (JDBCConnectionPool)getPool(hacpList, cc.getPoolName(), cc.getAppName(), cc.getModuleName(), cc.getCompName());
         }

         if (cp != null) {
            checkAndRemoveUnpooledConnection(cc, cp);
            if (cc.isInfected()) {
               cc.forceDestroy();
               cp.removeResource(cc);
            } else {
               cp.release(cc);
            }
         }
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.process(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.postProcess(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.postProcess(var2);
         }
      }

   }

   private static void checkAndRemoveUnpooledConnection(ConnectionEnv cc, JDBCConnectionPool cp) {
      JDBCDataSourceBean datasourceBean = cc.getConnectionPool().getJDBCDataSource();
      if (datasourceBean != null && datasourceBean.getJDBCDriverParams() != null && datasourceBean.getJDBCDriverParams().getProperties() != null) {
         JDBCPropertiesBean propertiesBean = datasourceBean.getJDBCDriverParams().getProperties();
         if (propertiesBean.getProperties() != null) {
            JDBCPropertyBean[] var4 = propertiesBean.getProperties();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               JDBCPropertyBean propertyBean = var4[var6];
               if (propertyBean.getName().equals("disable-pooling") && "true".equalsIgnoreCase(propertyBean.getValue())) {
                  cc.setInfected(true);
                  cc.setRefreshNeeded(true);
                  cp.removeConnection(cc);
                  break;
               }
            }
         }
      }

   }

   public static Iterator getConnectionPools() {
      ArrayList pools = new ArrayList(getPools(cpList));
      pools.addAll(getPools(hacpList));
      return pools.iterator();
   }

   public static Iterator getMultiPools() {
      return getPools(mpList).iterator();
   }

   public static Object getLockObject() {
      return lockObject;
   }

   public static JDBCConnectionPool[] getConnectionPools(String poolName, String appName, String moduleName, String compName) throws ResourceException {
      JDBCConnectionPool[] pools = null;
      JDBCConnectionPool cp = getPool(poolName, appName, moduleName, compName);
      if (cp != null) {
         pools = new JDBCConnectionPool[]{cp};
      } else {
         MultiPool mp = (MultiPool)getPool(mpList, poolName, appName, moduleName, compName);
         if (mp != null) {
            pools = mp.getConnectionPools();
         }
      }

      if (pools != null && pools.length != 0) {
         return pools;
      } else {
         throw new ResourceException("Unable to locate pool, name = " + poolName + ", applicationName = " + appName);
      }
   }

   public static MultiPool getMultiPool(String poolName, String applicationName, String moduleName, String compName) throws ResourceException {
      return (MultiPool)getPool(mpList, poolName, applicationName, moduleName, compName);
   }

   public static JDBCConnectionPool getPool(String poolName) throws ResourceException {
      return getPool(poolName, (String)null, (String)null, (String)null);
   }

   public static JDBCConnectionPool getPool(String poolName, String appName, String moduleName, String compName) throws ResourceException {
      JDBCConnectionPool cp = (JDBCConnectionPool)getPool(cpList, poolName, appName, moduleName, compName);
      if (cp == null) {
         cp = (JDBCConnectionPool)getPool(hacpList, poolName, appName, moduleName, compName);
      }

      return cp;
   }

   public static JDBCConnectionPool getHAPool(String poolName, String appName, String moduleName, String compName) throws ResourceException {
      return (JDBCConnectionPool)getPool(hacpList, poolName, appName, moduleName, compName);
   }

   public static boolean poolExists(String poolName, String appName, String moduleName, String compName) throws ResourceException {
      return poolExists(cpList, poolName, appName, moduleName, compName) || poolExists(mpList, poolName, appName, moduleName, compName) || poolExists(hacpList, poolName, appName, moduleName, compName);
   }

   public static void addPool(String poolName, String appName, String moduleName, String compName, JDBCConnectionPool pool) throws ResourceException {
      addPool(cpList, poolName, appName, moduleName, compName, pool);
   }

   public static JDBCConnectionPool removePool(String poolName, String appName, String moduleName, String compName) throws ResourceException {
      return (JDBCConnectionPool)removePool(cpList, poolName, appName, moduleName, compName);
   }

   public void start(Object unused) throws ResourceException {
   }

   public void resume() throws ResourceException {
      Iterator cps = getPools(cpList).iterator();

      while(cps.hasNext()) {
         JDBCConnectionPool cp = (JDBCConnectionPool)cps.next();
         cp.start((Object)null);
         cp.resume();
      }

      Iterator mps = getPools(mpList).iterator();

      while(mps.hasNext()) {
         MultiPool mp = (MultiPool)mps.next();
         mp.start((Object)null);
         mp.resume();
      }

   }

   public void suspend(boolean shuttingDown) throws ResourceException {
      Iterator cps = getPools(cpList).iterator();

      while(cps.hasNext()) {
         JDBCConnectionPool cp = (JDBCConnectionPool)cps.next();
         cp.suspend(shuttingDown);
         cp.shutdown();
      }

      Iterator mps = getPools(mpList).iterator();

      while(mps.hasNext()) {
         MultiPool mp = (MultiPool)mps.next();
         mp.suspend(shuttingDown);
         mp.shutdown();
      }

   }

   public void forceSuspend(boolean shuttingDown) throws ResourceException {
      Iterator cps = getPools(cpList).iterator();

      while(cps.hasNext()) {
         JDBCConnectionPool cp = (JDBCConnectionPool)cps.next();
         cp.forceSuspend(shuttingDown);
         cp.shutdown();
      }

      Iterator mps = getPools(mpList).iterator();

      while(mps.hasNext()) {
         MultiPool mp = (MultiPool)mps.next();
         mp.forceSuspend(shuttingDown);
         mp.shutdown();
      }

   }

   public void shutdown() throws ResourceException {
   }

   public Object createAndStartPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException {
      return this.createAndStartPool(dsBean, appName, moduleName, compName, (ClassLoader)null, false, false);
   }

   public Object createAndStartPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, ClassLoader classLoader) throws ResourceException {
      return this.createAndStartPool(dsBean, appName, moduleName, compName, classLoader, false, false);
   }

   public Object createAndStartPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, boolean isMemberDS, boolean isMemberLLR) throws ResourceException {
      return this.createAndStartPool(dsBean, appName, moduleName, compName, (ClassLoader)null, isMemberDS, isMemberLLR);
   }

   public Object createAndStartPool(final JDBCDataSourceBean dsBean, final String appName, final String moduleName, final String compName, final ClassLoader classLoader, boolean isMemberDS, boolean isMemberLLR) throws ResourceException {
      String name = dsBean.getName();
      String sharedPoolJNDIName = JDBCUtil.getSharedPoolJNDIName(dsBean);
      String propsStr;
      Properties sharedPool;
      if (HAUtil.isHADataSource(dsBean)) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            JdbcDebug.JDBCRAC.debug("GridLink Data Source " + name);
         }

         if (poolExists(mpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else if (poolExists(cpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else if (poolExists(hacpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else {
            propsStr = null;
            sharedPool = null;
            Object hacp;
            if (sharedPoolJNDIName != null) {
               JDBCConnectionPool sharedPool = JDBCHelper.getHelper().getConnectionPool(sharedPoolJNDIName);
               if (sharedPool == null) {
                  throw new ResourceException("Shared datasource " + sharedPoolJNDIName + " not found");
               }

               this.validateStaticSharedPoolConfiguration(dsBean, sharedPool.getJDBCDataSource());
               hacp = new HASharingConnectionPool(dsBean, sharedPool, appName, moduleName, compName);
               sharedPool.incrementSharedPoolReferenceCounter();
            } else {
               hacp = new HAConnectionPool(dsBean, appName, moduleName, compName, classLoader);
            }

            addPool(hacpList, name, appName, moduleName, compName, hacp);
            JDBCLogger.logCreatedCP(name);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("Created HA connection pool " + name);
            }

            ((JDBCConnectionPool)hacp).start((Object)null);
            ((JDBCConnectionPool)hacp).resume();
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("Started HA connection pool " + name);
            }

            DataSourceServiceImpl dss = (DataSourceServiceImpl)DataSourceManager.getInstance().getDataSourceService();
            dss.poolDeployed((JDBCConnectionPool)hacp);
            return hacp;
         }
      } else if (JDBCUtil.getDataSourceType(dsBean).equals("MDS")) {
         if (appName != null) {
            JDBCLogger.logCreatingASMP(name, appName, moduleName, dsBean.getJDBCDataSourceParams().getAlgorithmType());
         } else {
            JDBCLogger.logCreatingMPAlg(name, dsBean.getJDBCDataSourceParams().getAlgorithmType());
         }

         boolean needRunAs = false;

         try {
            JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "MultiPool", name, appName, moduleName, compName, "admin", "createAndStart");
         } catch (ResourcePermissionsException var18) {
            if (!JDBCHelper.getHelper().isServerShuttingDown()) {
               String partitionName = JDBCUtil.getPartitionName(dsBean);
               if (!JDBCHelper.getHelper().isPartitionStartingShuttingDown(partitionName)) {
                  throw var18;
               }
            }

            needRunAs = true;
         }

         if (poolExists(cpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else if (poolExists(mpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Multi Data Source " + name + " already exists");
         } else {
            final ConnectionPoolManager cpm = this;
            MultiPool mp;
            if (needRunAs) {
               try {
                  mp = (MultiPool)SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        return new MultiPool(dsBean, appName, moduleName, compName, cpm);
                     }
                  });
               } catch (PrivilegedActionException var16) {
                  throw new ResourceException(var16.toString());
               }
            } else {
               mp = new MultiPool(dsBean, appName, moduleName, compName, this);
            }

            mp.start((Object)null, isMemberLLR);
            mp.resume();
            addPool(mpList, name, appName, moduleName, compName, mp);
            JDBCLogger.logCreatedMP(name);
            return mp;
         }
      } else {
         propsStr = null;
         sharedPool = JDBCUtil.getProperties(dsBean, dsBean.getJDBCDriverParams().getProperties().getProperties(), name);
         if (sharedPool != null) {
            propsStr = JDBCUtil.convertPropertiesToString(sharedPool);
         }

         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            if (appName != null) {
               JDBCLogger.logCreatingASCP(name, appName, moduleName, dsBean.getJDBCDriverParams().getUrl(), propsStr);
            } else {
               JDBCLogger.logCreatingCP(name, dsBean.getJDBCDriverParams().getUrl(), propsStr);
            }
         } else if (appName != null) {
            JDBCLogger.logCreatingASCP(name, appName, moduleName, "xxxxxx", "xxxxxx");
         } else {
            JDBCLogger.logCreatingCP(name, "xxxxxx", "xxxxxx");
         }

         boolean needRunAs = false;

         String sharedPool;
         try {
            JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", name, appName, moduleName, compName, "admin");
         } catch (ResourcePermissionsException var19) {
            if (!JDBCHelper.getHelper().isServerShuttingDown()) {
               sharedPool = JDBCUtil.getPartitionName(dsBean);
               if (!JDBCHelper.getHelper().isPartitionStartingShuttingDown(sharedPool)) {
                  throw var19;
               }
            }

            needRunAs = true;
         }

         if (poolExists(mpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else if (poolExists(cpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else if (poolExists(hacpList, name, appName, moduleName, compName)) {
            throw new ResourceException("Data Source " + name + " already exists");
         } else {
            JDBCConnectionPool cp = null;
            sharedPool = null;
            if (sharedPoolJNDIName != null) {
               JDBCConnectionPool sharedPool = JDBCHelper.getHelper().getConnectionPool(sharedPoolJNDIName);
               if (sharedPool == null) {
                  throw new ResourceException("Shared datasource " + sharedPoolJNDIName + " not found");
               }

               this.validateStaticSharedPoolConfiguration(dsBean, sharedPool.getJDBCDataSource());
               cp = new SharingConnectionPool(dsBean, sharedPool, appName, moduleName, compName);
               sharedPool.incrementSharedPoolReferenceCounter();
            } else if (needRunAs) {
               try {
                  cp = (JDBCConnectionPool)SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        return new GenericConnectionPool(dsBean, appName, moduleName, compName, classLoader);
                     }
                  });
               } catch (PrivilegedActionException var17) {
                  throw new ResourceException(var17.toString());
               }
            } else {
               cp = new GenericConnectionPool(dsBean, appName, moduleName, compName, classLoader);
            }

            ((JDBCConnectionPool)cp).start((Object)null, isMemberDS);
            ((JDBCConnectionPool)cp).resume();
            addPool(cpList, name, appName, moduleName, compName, cp);
            JDBCLogger.logCreatedCP(name);
            return cp;
         }
      }
   }

   public void shutdownAndDestroyPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException {
      this.shutdownAndDestroyPool(dsBean.getName(), appName, moduleName, compName);
   }

   public void shutdownAndDestroyPool(String poolName, String appName, String moduleName, String compName) throws ResourceException {
      final String name = poolName;
      final String applicationName = appName;
      final String modName = moduleName;
      final String cmpName = compName;
      JDBCDataSourceBean dsBean;
      String partitionName;
      if (getPool(mpList, poolName, appName, moduleName, compName) != null) {
         if (appName != null) {
            JDBCLogger.logDestroyingASMP(poolName, appName, moduleName);
         } else {
            JDBCLogger.logDestroyingMP(poolName);
         }

         try {
            JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "MultiPool", name, appName, moduleName, compName, "admin", "shutdownAndDestroy");
         } catch (ResourcePermissionsException var15) {
            if (!JDBCHelper.getHelper().isServerShuttingDown()) {
               MultiPool mp = (MultiPool)getPool(mpList, poolName, appName, moduleName, compName);
               if (mp == null) {
                  throw var15;
               }

               dsBean = mp.getDataSourceBean();
               partitionName = JDBCUtil.getPartitionName(dsBean);
               if (!JDBCHelper.getHelper().isPartitionStartingShuttingDown(partitionName)) {
                  throw var15;
               }
            }

            try {
               SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     ConnectionPoolManager.this.shutdownMultiPool(name, applicationName, modName, cmpName);
                     return null;
                  }
               });
               return;
            } catch (PrivilegedActionException var13) {
               throw new ResourceException(var13.toString());
            }
         }

         this.shutdownMultiPool(poolName, appName, moduleName, compName);
      } else {
         if (appName != null) {
            JDBCLogger.logDestroyingASCP(poolName, appName, moduleName);
         } else {
            JDBCLogger.logDestroyingCP(poolName);
         }

         try {
            JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", name, appName, moduleName, compName, "admin");
         } catch (ResourcePermissionsException var16) {
            if (!JDBCHelper.getHelper().isServerShuttingDown()) {
               JDBCConnectionPool cp = (JDBCConnectionPool)getPool(cpList, poolName, appName, moduleName, compName);
               if (cp == null) {
                  cp = (JDBCConnectionPool)getPool(hacpList, poolName, appName, moduleName, compName);
               }

               if (cp == null) {
                  throw var16;
               }

               dsBean = cp.getJDBCDataSource();
               partitionName = JDBCUtil.getPartitionName(dsBean);
               if (!JDBCHelper.getHelper().isPartitionStartingShuttingDown(partitionName)) {
                  throw var16;
               }
            }

            try {
               SecurityServiceManager.runAs(KERNELID, KERNELID, new PrivilegedExceptionAction() {
                  public Object run() throws Exception {
                     ConnectionPoolManager.this.shutdownConnectionPool(name, applicationName, modName, cmpName);
                     return null;
                  }
               });
               return;
            } catch (PrivilegedActionException var14) {
               throw new ResourceException(var14.toString());
            }
         }

         this.shutdownConnectionPool(poolName, appName, moduleName, compName);
      }

   }

   private void shutdownMultiPool(String name, String appName, String moduleName, String compName) throws ResourceException {
      MultiPool mp = (MultiPool)getPool(mpList, name, appName, moduleName, compName);
      if (mp == null) {
         throw new ResourceException("Unknown Multi Pool " + name);
      } else {
         mp.suspend(true);
         mp.shutdown();
         removePool(mpList, name, appName, moduleName, compName);
         JDBCLogger.logDestroyedMP(name);
      }
   }

   private void shutdownConnectionPool(String name, String appName, String moduleName, String compName) throws ResourceException {
      JDBCConnectionPool cp = (JDBCConnectionPool)getPool(cpList, name, appName, moduleName, compName);
      if (cp == null) {
         cp = (JDBCConnectionPool)getPool(hacpList, name, appName, moduleName, compName);
         if (cp == null) {
            throw new ResourceException("Unknown Data Source " + name);
         }
      }

      ResourceException error = null;

      try {
         this.shutdownAndRemoveConnectionPool(cp);
      } catch (ResourceException var9) {
         error = var9;
      }

      if (cp instanceof SharingConnectionPool) {
         SharingConnectionPool scp = (SharingConnectionPool)cp;
         ConnectionPool sharedPool = (ConnectionPool)scp.getSharedPool();
         if (sharedPool != null) {
            sharedPool.decrementSharedPoolReferenceCounter();
         }
      }

      if (error != null) {
         throw error;
      }
   }

   private void shutdownAndRemoveConnectionPool(JDBCConnectionPool cp) throws ResourceException {
      cp.suspend(true);
      cp.shutdown();
      if (!(cp instanceof HAConnectionPool) && !(cp instanceof HASharingConnectionPool)) {
         removePool(cpList, cp.getName(), cp.getAppName(), cp.getModuleName(), cp.getCompName());
      } else {
         removePool(hacpList, cp.getName(), cp.getAppName(), cp.getModuleName(), cp.getCompName());
         DataSourceServiceImpl dss = (DataSourceServiceImpl)DataSourceManager.getInstance().getDataSourceService();
         dss.poolUndeployed(cp);
      }

      JDBCLogger.logDestroyedCP(cp.getName());
   }

   private static void addPool(HashMap poolList, String poolName, String appName, String moduleName, String compName, Object pool) throws ResourceException {
      poolName = JDBCUtil.getDecoratedName(poolName, appName, moduleName, compName);
      synchronized(lockObject) {
         poolList.put(poolName, pool);
      }
   }

   private static Object removePool(HashMap poolList, String poolName, String appName, String moduleName, String compName) throws ResourceException {
      poolName = JDBCUtil.getDecoratedName(poolName, appName, moduleName, compName);
      synchronized(lockObject) {
         return poolList.remove(poolName);
      }
   }

   private static Object getPool(HashMap poolList, String poolName, String appName, String moduleName, String compName) throws ResourceException {
      poolName = JDBCUtil.getDecoratedName(poolName, appName, moduleName, compName);
      return poolList.get(poolName);
   }

   private static boolean poolExists(HashMap poolList, String poolName, String appName, String moduleName, String compName) throws ResourceException {
      poolName = JDBCUtil.getDecoratedName(poolName, appName, moduleName, compName);
      return poolList.containsKey(poolName);
   }

   private static Collection getPools(HashMap poolList) {
      return poolList.values();
   }

   public static void setDataSource(String poolName, String applicationName, String moduleName, String compName, DataSource ds) throws ResourceException {
      JDBCConnectionPool cp = getPool(poolName, applicationName, moduleName, compName);
      if (cp != null) {
         cp.setDataSource(ds);
      } else {
         MultiPool mp = (MultiPool)getPool(mpList, poolName, applicationName, moduleName, compName);
         if (mp != null) {
            mp.setDataSource(ds);
         }
      }

   }

   private void validateStaticSharedPoolConfiguration(JDBCDataSourceBean sharingBean, JDBCDataSourceBean sharedBean) throws ResourceException {
      if (!JDBCUtil.isSharedPool(sharedBean)) {
         throw new ResourceException("Datasource " + sharingBean.getName() + " specified a non shared datasource " + sharedBean.getName());
      } else if ((!HAUtil.isHADataSource(sharingBean) || HAUtil.isHADataSource(sharedBean)) && (HAUtil.isHADataSource(sharingBean) || !HAUtil.isHADataSource(sharedBean))) {
         String sharingTxProtocol = sharingBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol();
         String sharedTxProtocol = sharedBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol();
         if (!sharingTxProtocol.equals(sharedTxProtocol)) {
            throw new ResourceException("Datasource " + sharingBean.getName() + " must have the same GlobalTransactionsProtocol as shared datasource " + sharedBean.getName());
         }
      } else {
         throw new ResourceException("Datasource " + sharingBean.getName() + " must be the same type as shared datasource " + sharedBean.getName());
      }
   }

   private void validateSharedPoolConfiguration(JDBCDataSourceBean dsBean1, JDBCDataSourceBean dsBean2) throws ResourceException {
      Map diffs = new HashMap();
      diffs.putAll(JDBCUtil.diff(JDBCDataSourceBean.class, dsBean1, dsBean2));
      diffs.remove("Name");
      diffs.putAll(JDBCUtil.diff(JDBCDataSourceParamsBean.class, dsBean1.getJDBCDataSourceParams(), dsBean2.getJDBCDataSourceParams()));
      diffs.remove("JNDINames");
      diffs.putAll(JDBCUtil.diff(JDBCDriverParamsBean.class, dsBean1.getJDBCDriverParams(), dsBean2.getJDBCDriverParams()));
      diffs.putAll(JDBCUtil.diff(JDBCConnectionPoolParamsBean.class, dsBean1.getJDBCConnectionPoolParams(), dsBean2.getJDBCConnectionPoolParams()));
      diffs.putAll(JDBCUtil.diff(JDBCOracleParamsBean.class, dsBean1.getJDBCOracleParams(), dsBean2.getJDBCOracleParams()));
      if (diffs.size() > 0) {
         throw new ResourceException("Shared pool \"" + JDBCUtil.getSharedPoolJNDIName(dsBean1) + "\" datasource descriptors [" + dsBean1 + ", " + dsBean2 + "] are not compatible: " + diffs.toString());
      }
   }

   static {
      _WLDF$INST_FLD_JDBC_After_Reserve_Connection_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Reserve_Connection_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Reserve_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Reserve_Around_Medium");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Release_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Release_Around_Medium");
      _WLDF$INST_FLD_JDBC_After_Release_Connection_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Release_Connection_Internal");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionPoolManager.java", "weblogic.jdbc.common.internal.ConnectionPoolManager", "reserve", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lweblogic/jdbc/common/internal/ConnectionEnv;", 79, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Reserve_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null, null, null, null})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Reserve_Connection_Internal};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionPoolManager.java", "weblogic.jdbc.common.internal.ConnectionPoolManager", "reserve", "(Lweblogic/security/acl/internal/AuthenticatedSubject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lweblogic/jdbc/common/internal/ConnectionEnv;", 117, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Reserve_Around_Medium", "JDBC_After_Reserve_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{null, InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null, null, null, null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{null, InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null, null, null, null})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Reserve_Around_Medium, _WLDF$INST_FLD_JDBC_After_Reserve_Connection_Internal};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionPoolManager.java", "weblogic.jdbc.common.internal.ConnectionPoolManager", "reserve", "(Lweblogic/security/acl/internal/AuthenticatedSubject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lweblogic/jdbc/common/internal/ConnectionEnv;", 165, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Reserve_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{null, InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), null, null, null})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Reserve_Connection_Internal};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ConnectionPoolManager.java", "weblogic.jdbc.common.internal.ConnectionPoolManager", "release", "(Lweblogic/jdbc/common/internal/ConnectionEnv;)V", 191, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Release_Connection_Internal", "JDBC_Diagnostic_Connection_Release_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("cc", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionEnvRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("cc", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionEnvRenderer", false, true)})}), (boolean)1);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Release_Around_Medium, _WLDF$INST_FLD_JDBC_After_Release_Connection_Internal};
      cpList = new HashMap();
      mpList = new HashMap();
      hacpList = new HashMap();
      lockObject = new Object();
      KERNELID = getKernelID();
   }
}
