package weblogic.jdbc.common.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.diagnostics.image.ImageManager;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.module.JDBCDeploymentFactory;
import weblogic.jdbc.module.JDBCDeploymentListener;
import weblogic.jdbc.module.JDBCModuleFactory;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCProxyDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCUCPDataSourceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.cmm.MemoryPressureListener;
import weblogic.utils.cmm.MemoryPressureService;

@Service
@Named
@RunLevel(10)
public final class JDBCService extends AbstractServerService {
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   @Named("TransactionService")
   private ServerService dependencyOnTransactionService;
   private boolean isResumed = false;
   private ServerMBean serverMBean;
   private static ConnectionPoolManager cpMgr;
   private static DataSourceManager dsMgr;
   private static UCPDataSourceManager ucpdsMgr;
   private static ProxyDataSourceManager proxydsMgr;
   private ServiceRuntimeMBeanImpl serviceRTMBean;
   private static HashMap dsRTMBeans;
   private static HashMap cpRTMBeans;
   private static HashMap mdsRTMBeans;
   private static HashMap driverRTMBeans;
   private JDBCDeploymentListener deploymentListener;
   @Inject
   private MemoryPressureService memoryPressureService;
   private static final AuthenticatedSubject KERNELID = getKernelID();
   private MemoryPressureListener pressureListener;

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private void initialize() throws ServiceFailureException {
      JDBCLogger.logInit();

      try {
         dsInitialize();
         RuntimeMBeanHelper.setHelper(new RuntimeMBeanHelperImpl());
         this.serverMBean = ManagementService.getRuntimeAccess(KERNELID).getServer();
         JDBCHelper.setHelper(new JDBCServerHelperImpl());
         DataSourceManager.setDataSourceService(new DataSourceServiceFullImpl());
         HAUtil.setInstance(new HAUtilImpl());
         this.createRuntimeMBean();
         int timeout = this.serverMBean.getJDBCLoginTimeoutSeconds();
         if (timeout > 0) {
            DriverManager.setLoginTimeout(timeout);
            JDBCLogger.logSetLoginTimeout(timeout);
         }

         ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).registerImageSource("JDBC", new JDBCImageSource());
         this.initFactories();
         this.setupConfigurationHandlers();
         HealthMonitorService.register("JDBC", this.serviceRTMBean, true);
      } catch (Exception var3) {
         String id = JDBCLogger.logInitFailed2();
         JDBCLogger.logStackTraceId(id, var3);
         throw new ServiceFailureException(id, var3);
      }

      JDBCLogger.logInitDone();
   }

   public static void initializeCollections() {
      dsRTMBeans = new HashMap();
      cpRTMBeans = new HashMap();
      mdsRTMBeans = new HashMap();
      driverRTMBeans = new HashMap();
   }

   public static void dsInitialize() {
      initializeCollections();
      cpMgr = new ConnectionPoolManager();
      dsMgr = DataSourceManager.getInstance();
      ucpdsMgr = UCPDataSourceManager.getInstance();
      proxydsMgr = ProxyDataSourceManager.getInstance();
   }

   public String getVersion() {
      return "JSR-221, JDBC 4.3";
   }

   public void start() throws ServiceFailureException {
      this.initialize();
      JDBCLogger.logResume();
      this.registerMemoryPressureListener();
      if (this.isResumed) {
         JDBCLogger.logResumeOpInvalid();
      } else {
         this.isResumed = true;

         try {
            cpMgr.resume();
            ucpdsMgr.resume();
            proxydsMgr.resume();
            Iterator cps = ConnectionPoolManager.getConnectionPools();

            while(true) {
               if (!cps.hasNext()) {
                  dsMgr.resume();
                  break;
               }

               ConnectionPool cp = (ConnectionPool)cps.next();
               createDataSourceRuntimeMBean(cp, cp.getAppName(), cp.getModuleName(), cp.getCompName(), cp.getJDBCDataSource());
            }
         } catch (Exception var3) {
            String id = JDBCLogger.logResumeFailed();
            JDBCLogger.logStackTraceId(id, var3);
            throw new ServiceFailureException(var3);
         }

         JDBCLogger.logResumeDone();
      }
   }

   public void stop() throws ServiceFailureException {
      JDBCLogger.logSuspend();

      try {
         if (dsMgr != null) {
            dsMgr.suspend(true);
         }

         if (cpMgr != null) {
            cpMgr.suspend(true);
         }

         if (ucpdsMgr != null) {
            ucpdsMgr.suspend(true);
         }

         if (proxydsMgr != null) {
            proxydsMgr.suspend(true);
         }

         this.isResumed = false;
         this.destroyRuntimeMBeans();
      } catch (Exception var3) {
         String id = JDBCLogger.logSuspendFailed();
         JDBCLogger.logStackTraceId(id, var3);
         throw new ServiceFailureException(id, var3);
      }

      JDBCLogger.logSuspendDone();
      this.shutdown();
   }

   public void halt() throws ServiceFailureException {
      JDBCLogger.logFSuspend();

      try {
         if (dsMgr != null) {
            dsMgr.forceSuspend(true);
         }

         if (cpMgr != null) {
            cpMgr.forceSuspend(true);
         }

         if (ucpdsMgr != null) {
            ucpdsMgr.forceSuspend(true);
         }

         if (proxydsMgr != null) {
            proxydsMgr.forceSuspend(true);
         }

         this.isResumed = false;
      } catch (Exception var3) {
         String id = JDBCLogger.logFSuspendFailed();
         JDBCLogger.logStackTraceId(id, var3);
      }

      JDBCLogger.logFSuspendDone();
      this.shutdown();
   }

   public void shutdown() throws ServiceFailureException {
      JDBCLogger.logShutdown2();

      try {
         HealthMonitorService.unregister("JDBC");
         this.removeConfigurationHandlers();
         if (dsMgr != null) {
            dsMgr.shutdown();
         }

         if (cpMgr != null) {
            cpMgr.shutdown();
         }

         if (ucpdsMgr != null) {
            ucpdsMgr.shutdown();
         }

         if (proxydsMgr != null) {
            proxydsMgr.shutdown();
         }

         this.destroyRuntimeMBeans();
      } catch (Exception var3) {
         String id = JDBCLogger.logShutdownFailed();
         JDBCLogger.logStackTraceId(id, var3);
         throw new ServiceFailureException(var3);
      }

      JDBCLogger.logShutdownDone();
   }

   public static ConnectionPoolManager getConnectionPoolManager() {
      return cpMgr;
   }

   public static DataSourceManager getDataSourceManager() {
      return dsMgr;
   }

   public static UCPDataSourceManager getUCPDataSourceManager() {
      return ucpdsMgr;
   }

   public static ProxyDataSourceManager getProxyDataSourceManager() {
      return proxydsMgr;
   }

   public static synchronized JDBCDataSourceRuntimeMBean[] getJDBCDataSourceRuntimeMBeans() {
      return (JDBCDataSourceRuntimeMBean[])((JDBCDataSourceRuntimeMBean[])dsRTMBeans.values().toArray(new JDBCDataSourceRuntimeMBean[dsRTMBeans.size()]));
   }

   public static synchronized JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name) {
      return (JDBCDataSourceRuntimeMBean)dsRTMBeans.get(name);
   }

   public static synchronized JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      String qname = JDBCUtil.getDecoratedName(name, appName, moduleName, componentName);
      return (JDBCDataSourceRuntimeMBean)dsRTMBeans.get(qname);
   }

   public static synchronized JDBCMultiDataSourceRuntimeMBean[] getJDBCMultiDataSourceRuntimeMBeans() {
      return (JDBCMultiDataSourceRuntimeMBean[])((JDBCMultiDataSourceRuntimeMBean[])mdsRTMBeans.values().toArray(new JDBCMultiDataSourceRuntimeMBean[mdsRTMBeans.size()]));
   }

   public static synchronized JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name) {
      return (JDBCMultiDataSourceRuntimeMBean)mdsRTMBeans.get(name);
   }

   public static synchronized JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      String qname = JDBCUtil.getDecoratedName(name, appName, moduleName, componentName);
      return (JDBCMultiDataSourceRuntimeMBean)mdsRTMBeans.get(qname);
   }

   public static synchronized void addJDBCDriverRuntimeMBean(JDBCDriverRuntimeMBean mbean, String name) {
      driverRTMBeans.put(name, mbean);
   }

   public static synchronized void removeJDBCDriverRuntimeMBean(String name) {
      driverRTMBeans.remove(name);
   }

   public static synchronized JDBCDriverRuntimeMBean getJDBCDriverRuntimeMBean(String name) {
      return (JDBCDriverRuntimeMBean)((JDBCDriverRuntimeMBean)driverRTMBeans.get(name));
   }

   public static synchronized JDBCDriverRuntimeMBean[] getJDBCDriverRuntimeMBeans() {
      Object[] list = driverRTMBeans.values().toArray();
      JDBCDriverRuntimeMBean[] ret = new JDBCDriverRuntimeMBean[list.length];

      for(int lcv = 0; lcv < list.length; ++lcv) {
         ret[lcv] = (JDBCDriverRuntimeMBean)list[lcv];
      }

      return ret;
   }

   public HealthState getHealthState() {
      int healthState = 0;
      float cnt = 0.0F;
      float unhealthyCnt = 0.0F;
      float overloadedCnt = 0.0F;
      Iterator iter = ConnectionPoolManager.getConnectionPools();
      List symptoms = new ArrayList();

      while(iter.hasNext()) {
         ++cnt;
         JDBCConnectionPool pool = (JDBCConnectionPool)iter.next();
         String state = pool.getDerivedState();
         if (state.equals("Unhealthy")) {
            ++unhealthyCnt;
            symptoms.add(new Symptom(SymptomType.CONNECTION_POOL_UNHEALTHY, Severity.HIGH, this.getPresentationName(pool), ""));
         } else if (state.equals("Overloaded")) {
            ++overloadedCnt;
            symptoms.add(new Symptom(SymptomType.CONNECTION_POOL_OVERLOADED, Severity.HIGH, this.getPresentationName(pool), ""));
         }
      }

      if (unhealthyCnt > 0.0F) {
         if ((double)Math.abs(unhealthyCnt - cnt) < 1.0E-7) {
            healthState = 3;
         } else if (unhealthyCnt >= cnt / 2.0F) {
            healthState = 2;
         } else {
            healthState = 1;
         }
      } else if (overloadedCnt > 0.0F) {
         healthState = 4;
      }

      Symptom[] arr = new Symptom[symptoms.size()];
      arr = (Symptom[])symptoms.toArray(arr);
      HealthState state = new HealthState(healthState, arr);
      return state;
   }

   private void createRuntimeMBean() throws Exception {
      this.serviceRTMBean = new ServiceRuntimeMBeanImpl(this);
      ManagementService.getRuntimeAccess(KERNELID).getServerRuntime().setJDBCServiceRuntime(this.serviceRTMBean);
   }

   private void destroyRuntimeMBeans() throws ManagementException {
      RuntimeMBeanHelper.getHelper().unregister(dsRTMBeans.values());
      RuntimeMBeanHelper.getHelper().unregister(cpRTMBeans.values());
      RuntimeMBeanHelper.getHelper().unregister(mdsRTMBeans.values());
      RuntimeMBeanHelper.getHelper().unregister((RuntimeMBean)this.serviceRTMBean);
   }

   private void initFactories() {
      RuntimeAccess rta = ManagementService.getRuntimeAccess(KERNELID);
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addWblogicModuleFactory(new JDBCModuleFactory());
      afm.addDeploymentFactory(new JDBCDeploymentFactory());
   }

   private void setupConfigurationHandlers() throws DeploymentException {
      this.deploymentListener = new JDBCDeploymentListener();
      DeploymentEventManager.addVetoableDeploymentListener(this.deploymentListener);
      DeploymentEventManager.addVetoableSystemResourceDeploymentListener(this.deploymentListener);
   }

   private void removeConfigurationHandlers() {
      if (this.deploymentListener != null) {
         DeploymentEventManager.removeVetoableDeploymentListener(this.deploymentListener);
         DeploymentEventManager.removeVetoableSystemResourceDeploymentListener(this.deploymentListener);
      }

   }

   public static synchronized ComponentRuntimeMBean createJDBCMultiDataSourceRuntimeMBean(MultiPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws ManagementException {
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      JDBCPartition jdbcPartition = null;
      if (partitionName != null) {
         jdbcPartition = JDBCPartition.getOrCreate(partitionName);
      }

      String qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(pool.getDataSourceBean()), appName, moduleName, compName);
      JDBCMultiDataSourceRuntimeMBean runtimeMBean = RuntimeMBeanHelper.getHelper().createMultiDataSourceRuntimeMBean(pool, qname, dsBean, appName);
      if (jdbcPartition != null) {
         jdbcPartition.addMultiDataSourceRuntime(qname, runtimeMBean);
         return runtimeMBean;
      } else {
         mdsRTMBeans.put(qname, runtimeMBean);
         return runtimeMBean;
      }
   }

   public static synchronized void destroyMultiDataSourceRuntimeMBean(String appName, String moduleName, String compName, String name, String partitionName, JDBCDataSourceBean dsBean) throws ManagementException {
      String qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      if (partitionName != null) {
         JDBCPartition jdbcPartition = JDBCPartition.get(partitionName);
         if (jdbcPartition != null) {
            jdbcPartition.removeMultiDataSourceRuntime(qname);
         }

      } else {
         MultiDataSourceRuntimeMBeanImpl runtimeMBean = (MultiDataSourceRuntimeMBeanImpl)mdsRTMBeans.remove(qname);
         if (runtimeMBean != null) {
            unregister(runtimeMBean);
         }

      }
   }

   public static void createUCPDataSourceRuntimeMBean(DataSource ucpds, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws ManagementException {
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      JDBCPartition jdbcPartition = null;
      if (partitionName != null) {
         jdbcPartition = JDBCPartition.getOrCreate(partitionName);
      }

      String qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      JDBCUCPDataSourceRuntimeMBean ucpdsRTMBean = RuntimeMBeanHelper.getHelper().createUCPDataSourceRuntimeMBean(ucpds, qname, dsBean, appName);
      if (jdbcPartition != null) {
         jdbcPartition.addDataSourceRuntime(qname, ucpdsRTMBean);
      } else {
         dsRTMBeans.put(qname, ucpdsRTMBean);
      }
   }

   public static void destroyUCPDataSourceRuntimeMBean(String name, String appName, String moduleName, String compName, String partitionName, JDBCDataSourceBean dsBean) throws ResourceException {
      String qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      if (partitionName != null) {
         JDBCPartition jdbcPartition = JDBCPartition.get(partitionName);
         if (jdbcPartition != null) {
            try {
               jdbcPartition.removeDataSourceRuntime(qname);
            } catch (ManagementException var9) {
               throw new ResourceException(var9);
            }
         }

      } else {
         DataSourceRuntimeMBeanImpl dsRTMBean = (DataSourceRuntimeMBeanImpl)dsRTMBeans.remove(name);
         if (dsRTMBean != null) {
            try {
               unregister(dsRTMBean);
            } catch (ManagementException var10) {
               throw new ResourceException(var10);
            }
         }

      }
   }

   public static void createProxyDataSourceRuntimeMBean(DataSource proxyds, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws ManagementException {
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      JDBCPartition jdbcPartition = null;
      if (partitionName != null) {
         jdbcPartition = JDBCPartition.getOrCreate(partitionName);
      }

      String qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      JDBCProxyDataSourceRuntimeMBean proxydsRTMBean = RuntimeMBeanHelper.getHelper().createProxyDataSourceRuntimeMBean(proxyds, qname, dsBean, appName);
      if (jdbcPartition != null) {
         jdbcPartition.addDataSourceRuntime(qname, proxydsRTMBean);
      } else {
         dsRTMBeans.put(qname, proxydsRTMBean);
      }
   }

   public static void destroyProxyDataSourceRuntimeMBean(String name, String appName, String moduleName, String compName, String partitionName, JDBCDataSourceBean dsBean) throws ResourceException {
      String qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      if (partitionName != null) {
         JDBCPartition jdbcPartition = JDBCPartition.get(partitionName);
         if (jdbcPartition != null) {
            try {
               jdbcPartition.removeDataSourceRuntime(qname);
            } catch (ManagementException var9) {
               throw new ResourceException(var9);
            }
         }

      } else {
         DataSourceRuntimeMBeanImpl dsRTMBean = (DataSourceRuntimeMBeanImpl)dsRTMBeans.remove(qname);
         if (dsRTMBean != null) {
            try {
               unregister(dsRTMBean);
            } catch (ManagementException var10) {
               throw new ResourceException(var10);
            }
         }

      }
   }

   public static JDBCOracleDataSourceRuntimeMBean createHADataSourceRuntimeMBean(JDBCConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws ManagementException {
      return createHADataSourceRuntimeMBean(pool, appName, moduleName, compName, dsBean, (String)null);
   }

   public static JDBCOracleDataSourceRuntimeMBean createHADataSourceRuntimeMBean(JDBCConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean, String sharedPoolName) throws ManagementException {
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      JDBCPartition jdbcPartition = null;
      if (partitionName != null) {
         jdbcPartition = JDBCPartition.getOrCreate(partitionName);
      }

      String qname = null;
      ResourcePoolGroup group = null;
      String instanceGroupCategory = null;
      if (sharedPoolName != null) {
         qname = JDBCUtil.getDecoratedName(sharedPoolName, appName, moduleName, compName);
      } else {
         qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
         if (JDBCUtil.usesSharedPool(dsBean)) {
            group = pool.getOrCreateGroup("service_pdbname", JDBCUtil.getServicePDBGroupName(JDBCUtil.getPDBServiceName(dsBean), JDBCUtil.getPDBName(dsBean)));
            instanceGroupCategory = "service_pdbname_instance";
         }
      }

      JDBCOracleDataSourceRuntimeMBean dsRTMBean = RuntimeMBeanHelper.getHelper().createHADataSourceRuntimeMBean(pool, group, instanceGroupCategory, qname, dsBean, appName, sharedPoolName);
      if (jdbcPartition != null && sharedPoolName == null) {
         jdbcPartition.addDataSourceRuntime(qname, dsRTMBean);
         return dsRTMBean;
      } else {
         dsRTMBeans.put(qname, dsRTMBean);
         return dsRTMBean;
      }
   }

   public static JDBCDataSourceRuntimeMBean createDataSourceRuntimeMBean(JDBCConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws ManagementException {
      return createDataSourceRuntimeMBean(pool, appName, moduleName, compName, dsBean, (String)null);
   }

   public static JDBCDataSourceRuntimeMBean createDataSourceRuntimeMBean(JDBCConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean, String sharedPoolName) throws ManagementException {
      String partitionName = JDBCUtil.getPartitionName(dsBean);
      JDBCPartition jdbcPartition = null;
      if (partitionName != null) {
         jdbcPartition = JDBCPartition.getOrCreate(partitionName);
      }

      String qname = null;
      ResourcePoolGroup group = null;
      if (sharedPoolName != null) {
         qname = JDBCUtil.getDecoratedName(sharedPoolName, appName, moduleName, compName);
      } else {
         qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      }

      JDBCDataSourceRuntimeMBean dsRTMBean = RuntimeMBeanHelper.getHelper().createDataSourceRuntimeMBean(pool, (ResourcePoolGroup)group, qname, dsBean, appName, sharedPoolName);
      if (jdbcPartition != null && sharedPoolName == null) {
         jdbcPartition.addDataSourceRuntime(qname, dsRTMBean);
         return dsRTMBean;
      } else {
         dsRTMBeans.put(qname, dsRTMBean);
         String driverMBeanName = RuntimeMBeanHelper.getHelper().getDriverRuntimeMBeanName(pool.getDriverVersion());
         synchronized(driverRTMBeans) {
            JDBCDriverRuntimeMBean driverRuntime = getJDBCDriverRuntimeMBean(driverMBeanName);
            if (driverRuntime == null) {
               driverRuntime = RuntimeMBeanHelper.getHelper().createDriverRuntimeMBean(driverMBeanName);
               addJDBCDriverRuntimeMBean(driverRuntime, driverMBeanName);
            }

            dsRTMBean.setJDBCDriverRuntime(driverRuntime);
            return dsRTMBean;
         }
      }
   }

   public static void destroyDataSourceRuntimeMBean(String driverName, String appName, String moduleName, String compName, String name, String partitionName, JDBCDataSourceBean dsBean) throws ResourceException {
      destroyDataSourceRuntimeMBean(driverName, appName, moduleName, compName, name, partitionName, dsBean, (String)null);
   }

   public static void destroyDataSourceRuntimeMBean(String driverName, String appName, String moduleName, String compName, String name, String partitionName, JDBCDataSourceBean dsBean, String sharedPoolName) throws ResourceException {
      String qname = null;
      if (sharedPoolName != null) {
         qname = JDBCUtil.getDecoratedName(sharedPoolName, appName, moduleName, compName);
      } else {
         qname = JDBCUtil.getDecoratedName(RuntimeMBeanHelper.getHelper().getRuntimeMBeanNameAttributeValue(dsBean), appName, moduleName, compName);
      }

      if (partitionName != null && sharedPoolName == null) {
         JDBCPartition jdbcPartition = JDBCPartition.get(partitionName);
         if (jdbcPartition != null) {
            try {
               jdbcPartition.removeDataSourceRuntime(qname);
            } catch (ManagementException var13) {
               throw new ResourceException(var13);
            }
         }

      } else {
         String driverMBeanName = RuntimeMBeanHelper.getHelper().getDriverRuntimeMBeanName(driverName);
         RuntimeMBeanDelegate driverRTMBean = (RuntimeMBeanDelegate)getJDBCDriverRuntimeMBean(driverMBeanName);
         if (driverRTMBean != null) {
            removeJDBCDriverRuntimeMBean(driverMBeanName);

            try {
               driverRTMBean.unregister();
            } catch (ManagementException var15) {
               throw new ResourceException(var15);
            }
         }

         RuntimeMBeanDelegate dsRTMBean = (RuntimeMBeanDelegate)dsRTMBeans.remove(qname);
         if (dsRTMBean != null) {
            try {
               unregister(dsRTMBean);
            } catch (ManagementException var14) {
               throw new ResourceException(var14);
            }
         }

      }
   }

   private String getPresentationName(JDBCConnectionPool pool) {
      return (pool.getAppName() != null ? pool.getAppName() + ":" : "") + (pool.getModuleName() != null ? pool.getModuleName() + ":" : "") + pool.getName();
   }

   private static void unregister(RuntimeMBeanDelegate runtimeMBean) throws ManagementException {
      RuntimeMBeanHelper.getHelper().unregister((RuntimeMBean)runtimeMBean);
   }

   private synchronized void registerMemoryPressureListener() {
      this.pressureListener = MemoryPressureManager.getInstance();
      this.memoryPressureService.registerListener("datasource", this.pressureListener);
   }

   private synchronized void unregisterMemoryPressureListener() {
      if (this.pressureListener != null) {
         this.memoryPressureService.unregisterListener(this.pressureListener);
         this.pressureListener = null;
      }
   }
}
