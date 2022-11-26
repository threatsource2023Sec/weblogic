package weblogic.jdbc.module;

import java.security.AccessController;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.NonDynamicPropertyUpdateException;
import weblogic.application.UpdateListener;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.common.ResourceException;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBean;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCOracleParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.ConnectionPool;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.DataSourceManager;
import weblogic.jdbc.common.internal.HAConnectionPool;
import weblogic.jdbc.common.internal.HASharingConnectionPool;
import weblogic.jdbc.common.internal.JDBCConnectionPool;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JDBCService;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.jdbc.common.internal.MultiPool;
import weblogic.jdbc.common.internal.ProxyDataSourceManager;
import weblogic.jdbc.common.internal.SharingConnectionPool;
import weblogic.jdbc.common.internal.UCPDataSourceManager;
import weblogic.jdbc.wrapper.ProxyDataSource;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.T3Srvr;
import weblogic.t3.srvr.WebLogicServer;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.JDBCModuleService;

public class JDBCModule implements Module, UpdateListener, ModuleLocationInfo {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ApplicationContextInternal appCtx;
   private Context envCtx;
   private String appName;
   private String moduleName;
   private String compName;
   private DataSource dataSource;
   private boolean bound;
   private final String uri;
   private ConnectionPoolManager cpMgr;
   private DataSourceManager dsMgr;
   private UCPDataSourceManager ucpdsMgr;
   private ProxyDataSourceManager proxydsMgr;
   private ProxyDataSource proxyds;
   private JDBCDeploymentHelper deploymentHelper;
   private JDBCConnectionPool pool;
   private MultiPool multipool;
   private ComponentRuntimeMBean compRTMB;
   private int legacyType;
   private JDBCConnectionPoolBean descriptor;
   private JDBCDataSourceBean dsBean;
   private boolean ucpDataSource;
   private boolean proxyDataSource;
   private final int BEAN_TYPE_PROPERTIES;
   private final int BEAN_TYPE_PROPERTY;
   private final int BEAN_TYPE_ORACLE;
   private final int BEAN_TYPE_DRIVER;
   private int COMMIT_CHANGE;
   private int ROLLBACK_CHANGE;
   private String altDD;

   public JDBCModule(String uri) {
      this(uri, (JDBCSystemResourceMBean)null);
   }

   public JDBCModule(String uri, JDBCSystemResourceMBean bean) {
      this.appName = null;
      this.compName = null;
      this.bound = false;
      this.proxyds = null;
      this.pool = null;
      this.multipool = null;
      this.BEAN_TYPE_PROPERTIES = 100;
      this.BEAN_TYPE_PROPERTY = 101;
      this.BEAN_TYPE_ORACLE = 102;
      this.BEAN_TYPE_DRIVER = 103;
      this.COMMIT_CHANGE = 0;
      this.ROLLBACK_CHANGE = 1;
      this.altDD = null;
      this.uri = uri;
      this.deploymentHelper = new JDBCDeploymentHelper(bean);
   }

   public JDBCModule(SystemResourceMBean sbean) {
      this(sbean.getDescriptorFileName(), (JDBCSystemResourceMBean)sbean);
      this.dsBean = ((JDBCSystemResourceMBean)sbean).getJDBCResource();
   }

   public JDBCModule(WeblogicModuleBean dd) {
      this(dd.getPath());
      this.moduleName = dd.getName();
   }

   public JDBCModule(JDBCDataSourceBean dsBean) {
      this((String)null);
      this.dsBean = dsBean;
   }

   public String getId() {
      return this.moduleName != null ? this.moduleName : this.uri;
   }

   public String getModuleURI() {
      return this.uri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_JDBC;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[]{this.compRTMB};
   }

   public DescriptorBean[] getDescriptors() {
      if (this.descriptor != null) {
         return new DescriptorBean[]{(DescriptorBean)this.descriptor};
      } else {
         return this.dsBean != null ? new DescriptorBean[]{(DescriptorBean)this.dsBean} : new DescriptorBean[0];
      }
   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.init(ac, parent, reg);
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.appCtx = (ApplicationContextInternal)appCtx;
      String app = null;
      if (appCtx != null) {
         app = appCtx.getApplicationId();
         this.envCtx = appCtx.getEnvContext();

         try {
            this.envCtx.lookup("/jdbc");
         } catch (NameNotFoundException var8) {
            try {
               this.envCtx.createSubcontext("jdbc");
            } catch (NamingException var7) {
               throw new AssertionError(var7);
            }
         } catch (NamingException var9) {
            throw new AssertionError(var9);
         }
      }

      if (this.uri != null && !this.uri.endsWith("-jdbc.xml")) {
         throw new ModuleException("Data source descriptor filename " + this.uri + " does not have the required suffix \"-jdbc.xml\"");
      } else {
         if (this.dsBean == null || this.appCtx != null && this.deploymentHelper.hasDeploymentPlan(this.appCtx)) {
            this.dsBean = this.deploymentHelper.createJDBCDataSourceDescriptor(this.appCtx, this.uri);
         }

         if (this.dsBean == null) {
            throw new ModuleException("Descriptor " + this.uri + " not found.");
         } else {
            this.legacyType = JDBCUtil.getLegacyType(this.dsBean);
            if (this.legacyType == 0 && JDBCUtil.getInternalProperty(this.dsBean, "LegacyPoolName") != null) {
               throw new ModuleException("Cannot specify pool name for data source " + this.dsBean.getName() + ", URI = " + this.uri);
            } else {
               String version;
               if (this.moduleName == null) {
                  version = this.dsBean.getName();
                  if (version.startsWith("java:") && !version.startsWith("java:global")) {
                     this.dsBean.getJDBCDataSourceParams().setScope("Application");
                  } else if (!this.dsBean.getJDBCDataSourceParams().getScope().equals("Global")) {
                     JDBCLogger.logInvalidApplicationScope(this.dsBean.getName(), this.uri);
                     this.dsBean.getJDBCDataSourceParams().setScope("Global");
                  }
               }

               version = null;
               if (app != null) {
                  version = ApplicationVersionUtils.getVersionId(app);
               }

               if ("Global".equals(this.dsBean.getJDBCDataSourceParams().getScope())) {
                  if (version != null) {
                     throw new ModuleException("An application-scoped data source (" + this.dsBean.getName() + ") cannot specify a scope of Global when application is versioned (" + app + ")");
                  }
               } else {
                  this.appName = app;
               }

               if (reg != null) {
                  reg.addUpdateListener(this);
               }

               this.cpMgr = JDBCService.getConnectionPoolManager();
               this.dsMgr = JDBCService.getDataSourceManager();
               this.ucpdsMgr = JDBCService.getUCPDataSourceManager();
               this.proxydsMgr = JDBCService.getProxyDataSourceManager();
               this.ucpDataSource = this.isUCPDS();
               this.proxyDataSource = this.isProxyDS();
               return parent;
            }
         }
      }
   }

   public void start() {
   }

   public void prepare() throws ModuleException {
      Object nds;
      if (this.ucpDataSource) {
         try {
            nds = this.ucpdsMgr.create(this.dsBean, this.appName, this.moduleName, this.compName);
            JDBCService.createUCPDataSourceRuntimeMBean((DataSource)nds, this.appName, this.moduleName, this.compName, this.dsBean);
         } catch (ResourceException var12) {
            try {
               this.ucpdsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var8) {
            }

            throw new ModuleException(var12);
         } catch (SQLException var13) {
            try {
               this.ucpdsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var7) {
            }

            throw new ModuleException(var13);
         } catch (ManagementException var14) {
            try {
               this.ucpdsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var9) {
            }

            throw new ModuleException(var14);
         }
      } else if (this.proxyDataSource) {
         try {
            nds = this.proxydsMgr.create(this.dsBean, this.appName, this.moduleName, this.compName);
            this.proxyds = (ProxyDataSource)nds;
            JDBCService.createProxyDataSourceRuntimeMBean((DataSource)nds, this.appName, this.moduleName, this.compName, this.dsBean);
         } catch (ResourceException var15) {
            try {
               this.proxydsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var10) {
            }

            throw new ModuleException(var15);
         } catch (SQLException var16) {
            try {
               this.proxydsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var6) {
            }

            throw new ModuleException(var16);
         } catch (ManagementException var17) {
            try {
               this.proxydsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var11) {
            }

            throw new ModuleException(var17);
         }
      } else {
         boolean memDS = isMemberDSOfMultiDataSource(this.dsBean.getName(), this.appCtx);
         boolean memDSLLR = isMemberOfMultiDataSourceLLR(this.dsBean.getName(), this.appCtx);

         try {
            if (this.legacyType == 0 || this.legacyType == 1 || this.legacyType == 2) {
               Object ret = this.cpMgr.createAndStartPool(this.dsBean, this.appName, this.moduleName, this.compName, memDS, memDSLLR);
               if (!(ret instanceof HAConnectionPool) && !(ret instanceof HASharingConnectionPool)) {
                  if (!(ret instanceof ConnectionPool) && !(ret instanceof SharingConnectionPool)) {
                     this.multipool = (MultiPool)ret;
                     this.compRTMB = JDBCService.createJDBCMultiDataSourceRuntimeMBean(this.multipool, this.appName, this.moduleName, this.compName, this.dsBean);
                     if (!"Global".equals(this.dsBean.getJDBCDataSourceParams().getScope())) {
                        this.multipool.setModuleNames(getCPModuleNames(this.dsBean));
                     }

                     this.multipool.suspend(false);
                  } else {
                     this.pool = (JDBCConnectionPool)ret;
                     this.pool.suspend(false);
                     if (this.appCtx != null) {
                        this.compRTMB = JDBCService.createDataSourceRuntimeMBean(this.pool, this.pool.getAppName(), this.pool.getModuleName(), this.pool.getCompName(), this.pool.getJDBCDataSource());
                     }
                  }
               } else {
                  this.pool = (JDBCConnectionPool)ret;
                  if (this.appCtx != null) {
                     this.compRTMB = JDBCService.createHADataSourceRuntimeMBean(this.pool, this.pool.getAppName(), this.pool.getModuleName(), this.pool.getCompName(), this.pool.getJDBCDataSource());
                  }
               }
            }

            if (this.legacyType == 0 || this.legacyType == 3 || this.legacyType == 4) {
               JDBCDataSourceBean[] poolBeans = this.getPoolBeans(this.dsBean, this.appCtx);
               this.dsMgr.checkDataSource(this.dsBean, this.appName, this.moduleName, this.compName, poolBeans);
            }

         } catch (Exception var19) {
            Exception e = var19;

            try {
               if (this.dsBean != null) {
                  boolean isLLR = isLLR(this.dsBean.getName(), this.appCtx);
                  if (memDSLLR || isLLR && !memDS) {
                     TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
                     tm.registerFailedLoggingResource(new ResourceException(e.getMessage()));
                  }

                  if (this.cpMgr != null) {
                     this.cpMgr.shutdownAndDestroyPool(this.dsBean, this.appName, this.moduleName, this.compName);
                  }

                  this.unregisterMBeans(this.dsBean);
               }
            } catch (Exception var18) {
            }

            throw new ModuleException(var19);
         }
      }
   }

   private void unregisterMBeans(JDBCDataSourceBean dsBean) throws ManagementException, ResourceException {
      if (dsBean.getJDBCDataSourceParams().getDataSourceList() != null) {
         JDBCService.destroyMultiDataSourceRuntimeMBean(this.appName, this.moduleName, this.compName, dsBean.getName(), JDBCUtil.getPartitionName(dsBean), dsBean);
      } else {
         JDBCService.destroyDataSourceRuntimeMBean(dsBean.getJDBCDriverParams().getDriverName(), this.appName, this.moduleName, this.compName, dsBean.getName(), JDBCUtil.getPartitionName(dsBean), dsBean);
      }

   }

   public void activate() throws IllegalStateException, ModuleException {
      Object var17;
      if (this.ucpDataSource) {
         try {
            var17 = this.ucpdsMgr.bind(this.dsBean, this.appName, this.moduleName, this.compName, this.appCtx.getEnvContext());
         } catch (ResourceException var12) {
            try {
               this.ucpdsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var4) {
            }

            throw new ModuleException(var12);
         } catch (NamingException var13) {
            try {
               this.ucpdsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var7) {
            }

            throw new ModuleException(var13);
         }
      } else if (this.proxyDataSource) {
         try {
            var17 = this.proxydsMgr.bind(this.dsBean, this.appName, this.moduleName, this.compName, this.appCtx.getEnvContext());
         } catch (ResourceException var14) {
            try {
               this.proxydsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var5) {
            }

            throw new ModuleException(var14);
         } catch (NamingException var15) {
            try {
               this.proxydsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
            } catch (Exception var6) {
            }

            throw new ModuleException(var15);
         }
      } else {
         boolean memDS = isMemberDSOfMultiDataSource(this.dsBean.getName(), this.appCtx);

         try {
            if (this.pool != null) {
               this.pool.resume();
            } else if (this.multipool != null) {
               this.multipool.setupConnPoolRefs();
               this.multipool.resume();
            }

            if (this.legacyType == 0 || this.legacyType == 3 || this.legacyType == 4) {
               JDBCDataSourceBean[] poolBeans = this.getPoolBeans(this.dsBean, this.appCtx);
               Context context = null;
               if (this.appCtx != null) {
                  context = this.appCtx.getEnvContext();
               }

               this.dsMgr.createAndStartDataSource(this.dsBean, this.appName, this.moduleName, this.compName, context, poolBeans, memDS);
            }

         } catch (Exception var16) {
            if (this.pool != null) {
               try {
                  this.pool.suspend(true);
               } catch (Exception var11) {
               }

               try {
                  this.pool.shutdown();
               } catch (Exception var10) {
               }
            } else if (this.multipool != null) {
               try {
                  this.multipool.suspend(true);
               } catch (Exception var9) {
               }

               try {
                  this.multipool.shutdown();
               } catch (Exception var8) {
               }
            }

            throw new ModuleException(var16);
         }
      }
   }

   public void deactivate() throws IllegalStateException, ModuleException {
      if (this.ucpDataSource) {
         try {
            this.ucpdsMgr.unbind(this.dsBean, this.appName, this.moduleName, this.compName);
            this.ucpdsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
         } catch (ResourceException var3) {
            throw new ModuleException(var3);
         } catch (NamingException var4) {
            throw new ModuleException(var4);
         }
      } else if (this.proxyDataSource) {
         try {
            this.proxydsMgr.unbind(this.dsBean, this.appName, this.moduleName, this.compName);
            this.proxydsMgr.remove(this.dsBean, this.appName, this.moduleName, this.compName);
         } catch (ResourceException var5) {
            throw new ModuleException(var5);
         } catch (NamingException var6) {
            throw new ModuleException(var6);
         }
      } else {
         try {
            if (this.legacyType == 0 || this.legacyType == 3 || this.legacyType == 4) {
               this.dsMgr.shutdownAndDestroyDataSource(this.dsBean, this.appName, this.moduleName, this.compName);
            }

            if (this.pool != null) {
               WebLogicServer server = null;

               try {
                  server = T3Srvr.getT3Srvr();
               } catch (Exception var7) {
               }

               if (server != null && server.getState().equals("FORCE_SHUTTING_DOWN")) {
                  this.pool.forceSuspend(true);
               } else {
                  this.pool.suspend(false);
               }
            } else if (this.multipool != null) {
               this.multipool.suspend(false);
            }

         } catch (Exception var8) {
            throw new ModuleException(var8);
         }
      }
   }

   public void unprepare() throws IllegalStateException, ModuleException {
      if (this.ucpDataSource) {
         try {
            JDBCService.destroyUCPDataSourceRuntimeMBean(this.dsBean.getName(), this.appName, this.moduleName, this.compName, JDBCUtil.getPartitionName(this.dsBean), this.dsBean);
         } catch (ResourceException var2) {
            throw new ModuleException(var2);
         }
      } else if (this.proxyDataSource) {
         try {
            JDBCService.destroyProxyDataSourceRuntimeMBean(this.dsBean.getName(), this.appName, this.moduleName, this.compName, JDBCUtil.getPartitionName(this.dsBean), this.dsBean);
         } catch (ResourceException var3) {
            throw new ModuleException(var3);
         }
      } else {
         try {
            if (this.pool != null) {
               JDBCService.destroyDataSourceRuntimeMBean(this.pool.getDriverVersion(), this.pool.getAppName(), this.pool.getModuleName(), this.pool.getCompName(), this.pool.getName(), JDBCUtil.getPartitionName(this.dsBean), this.dsBean);
            } else {
               if (this.multipool == null) {
                  return;
               }

               JDBCService.destroyMultiDataSourceRuntimeMBean(this.appName, this.moduleName, this.compName, this.multipool.getName(), JDBCUtil.getPartitionName(this.dsBean), this.dsBean);
            }

            this.compRTMB = null;
            if (this.legacyType == 0 || this.legacyType == 1 || this.legacyType == 2) {
               this.cpMgr.shutdownAndDestroyPool(this.dsBean, this.appName, this.moduleName, this.compName);
            }

         } catch (Exception var4) {
            throw new ModuleException(var4);
         }
      }
   }

   public void remove() throws IllegalStateException, ModuleException {
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      reg.removeUpdateListener(this);
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }

   private static HashMap getCPModuleNames(JDBCDataSourceBean dsBean) throws ResourceException {
      HashMap ret = new HashMap();
      if (ApplicationAccess.getApplicationAccess().getCurrentApplicationContext() == null) {
         return null;
      } else {
         Module[] modules = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext().getApplicationModules();
         String dsList = dsBean.getJDBCDataSourceParams().getDataSourceList();
         StringTokenizer st = new StringTokenizer(dsList, ",");

         while(st.hasMoreTokens()) {
            String dsName = st.nextToken();
            Module poolModule = getModule(modules, dsName);
            if (poolModule == null) {
               String app = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext().getApplicationId();
               throw new ResourceException("Unable to find module in application '" + app + "' for connection pool '" + dsName + "' being used by multi pool '" + dsBean.getName() + "'");
            }

            ret.put(dsName, poolModule.getId());
         }

         return ret;
      }
   }

   private static Module getModule(Module[] modules, String dsName) {
      int i = dsName.indexOf("@");
      if (i != -1) {
         String moduleName = dsName.substring(i + 1);
         i = moduleName.indexOf("@");
         if (i == -1) {
            i = dsName.indexOf("@");
            moduleName = dsName.substring(0, i);
         } else {
            moduleName = moduleName.substring(0, i);
         }

         for(int lcv = 0; lcv < modules.length; ++lcv) {
            if ("jdbc".equals(modules[lcv].getType()) && moduleName.equals(modules[lcv].getId())) {
               return modules[lcv];
            }
         }

         return null;
      } else {
         for(int lcv = 0; lcv < modules.length; ++lcv) {
            if ("jdbc".equals(modules[lcv].getType())) {
               DescriptorBean[] beans = modules[lcv].getDescriptors();
               if (beans != null && beans.length == 1 && beans[0] instanceof JDBCDataSourceBean) {
                  JDBCDataSourceBean currBean = (JDBCDataSourceBean)beans[0];
                  if (dsName.equals(currBean.getName())) {
                     return modules[lcv];
                  }
               }
            }
         }

         return null;
      }
   }

   public boolean acceptURI(String updateUri) {
      return ".".equals(updateUri) ? true : this.uri.equals(updateUri);
   }

   public void prepareUpdate(String newUri) throws ModuleException {
      this.processUpdate(newUri, this.COMMIT_CHANGE);
   }

   public void prepareUpdate(JDBCDataSourceBean proposedBean) throws ModuleException {
      this.processUpdate(proposedBean, this.COMMIT_CHANGE);
   }

   public void rollbackUpdate(String newUri) {
      try {
         this.processUpdate(newUri, this.ROLLBACK_CHANGE);
      } catch (Exception var3) {
      }

   }

   public void rollbackUpdate(JDBCDataSourceBean proposedBean) {
      try {
         this.processUpdate(proposedBean, this.ROLLBACK_CHANGE);
      } catch (Exception var3) {
      }

   }

   public void activateUpdate(String newUri) throws ModuleException {
      this.dsBean = this.deploymentHelper.createJDBCDataSourceDescriptor(this.appCtx, newUri);
      if (this.pool != null) {
         this.pool.setJDBCDataSource(this.dsBean);
      }

   }

   public String toString() {
      return "JDBCModule(" + this.dsBean.getName() + ")";
   }

   public void checkDependencies() throws DeploymentVetoException {
      if (this.pool != null) {
         String poolName = this.pool.getName();
         Iterator iter = ConnectionPoolManager.getMultiPools();

         while(iter.hasNext()) {
            MultiPool mp = (MultiPool)iter.next();
            if (this.isGloballyScoped() && mp.isGloballyScoped() && mp.hasMember(poolName)) {
               throw new DeploymentVetoException("Cannot undeploy JDBC Data Source " + this.pool.getName() + ", it is currently being used by the JDBC Multi Data Source " + mp.getName());
            }
         }

         if (this.pool.isSharedPool() && this.pool instanceof ConnectionPool && ((ConnectionPool)this.pool).getSharedPoolReferenceCounter() > 0) {
            throw new DeploymentVetoException("Cannot undeploy shared pool JDBC Data Source " + this.pool.getName() + ", it is currently in use");
         }
      }

   }

   public void setAltDD(String newValue) {
      this.altDD = newValue;
   }

   public String getAltDD() {
      return this.altDD;
   }

   private void bindDataSource() throws ModuleException {
      if (!this.bound) {
         try {
            Context jdbcctx = (Context)this.envCtx.lookup("jdbc");
            String dsName = this.descriptor.getDataSourceJNDIName();
            if (dsName == null) {
               throw new ModuleException("data-source-name not defined in jdbc-connection-pool");
            }

            jdbcctx.bind(dsName, this.dataSource);
         } catch (NamingException var3) {
            throw new ModuleException(var3.toString(), var3);
         }

         this.bound = true;
      }
   }

   private void unbindDataSource() throws ModuleException {
      if (this.bound) {
         try {
            Context jdbcctx = (Context)this.envCtx.lookup("jdbc");
            jdbcctx.unbind(this.descriptor.getDataSourceJNDIName());
         } catch (NamingException var2) {
            throw new ModuleException(var2.toString(), var2);
         }

         this.bound = false;
      }
   }

   private void processUpdate(String newUri, int action) throws ModuleException {
      JDBCDataSourceBean proposedBean = null;

      try {
         proposedBean = this.deploymentHelper.createJDBCDataSourceDescriptor(this.appCtx, newUri);
      } catch (Exception var5) {
         if (action == this.COMMIT_CHANGE) {
            throw new ModuleException("prepareUpdate failed for JDBC Module " + this.dsBean.getName() + ": " + var5.getMessage(), var5);
         }

         throw new ModuleException("rollbackUpdate failed for JDBC Module " + this.dsBean.getName() + ": " + var5.getMessage(), var5);
      }

      this.processUpdate(proposedBean, action);
   }

   private void processUpdate(JDBCDataSourceBean proposedBean, int action) throws ModuleException {
      try {
         Iterator updates = ((DescriptorBean)this.dsBean).getDescriptor().computeDiff(((DescriptorBean)proposedBean).getDescriptor()).iterator();

         while(updates.hasNext()) {
            this.processBeanUpdateEvent((BeanUpdateEvent)updates.next(), action);
         }

      } catch (Exception var4) {
         if (action == this.COMMIT_CHANGE) {
            throw new ModuleException("prepareUpdate failed for JDBC Module " + this.dsBean.getName() + ": " + var4.getMessage(), var4);
         } else {
            throw new ModuleException("rollbackUpdate failed for JDBC Module " + this.dsBean.getName() + ": " + var4.getMessage(), var4);
         }
      }
   }

   private void processBeanUpdateEvent(BeanUpdateEvent event, int action) throws Exception {
      JDBCConnectionPoolParamsBean poolParamsBean = null;
      JDBCDataSourceParamsBean dsParamsBean = null;
      JDBCPropertiesBean propsBean = null;
      JDBCPropertyBean propBean = null;
      JDBCOracleParamsBean oracleParamsBean = null;
      JDBCDriverParamsBean driverParamsBean = null;
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      if (updates != null) {
         DescriptorBean updateBean = event.getProposedBean();
         byte beanType;
         if (updateBean instanceof JDBCConnectionPoolParamsBean) {
            beanType = 1;
            if (action == this.COMMIT_CHANGE) {
               poolParamsBean = (JDBCConnectionPoolParamsBean)updateBean;
            } else {
               poolParamsBean = this.dsBean.getJDBCConnectionPoolParams();
            }
         } else if (updateBean instanceof JDBCDataSourceParamsBean) {
            beanType = 3;
            if (action == this.COMMIT_CHANGE) {
               dsParamsBean = (JDBCDataSourceParamsBean)updateBean;
            } else {
               dsParamsBean = this.dsBean.getJDBCDataSourceParams();
            }
         } else if (updateBean instanceof JDBCPropertiesBean) {
            beanType = 100;
            if (action == this.COMMIT_CHANGE) {
               propsBean = (JDBCPropertiesBean)updateBean;
            } else {
               propsBean = this.dsBean.getInternalProperties();
            }
         } else if (updateBean instanceof JDBCPropertyBean) {
            beanType = 101;
            if (action == this.COMMIT_CHANGE) {
               propBean = (JDBCPropertyBean)updateBean;
            } else {
               propBean = this.dsBean.getInternalProperties().lookupProperty(((JDBCPropertyBean)updateBean).getName());
            }
         } else if (updateBean instanceof JDBCOracleParamsBean) {
            beanType = 102;
            if (action == this.COMMIT_CHANGE) {
               oracleParamsBean = (JDBCOracleParamsBean)updateBean;
            } else {
               oracleParamsBean = this.dsBean.getJDBCOracleParams();
            }
         } else {
            if (!(updateBean instanceof JDBCDriverParamsBean)) {
               JDBCLogger.logUnexpectedUpdateBeanType(updateBean.toString());
               return;
            }

            beanType = 103;
            if (action == this.COMMIT_CHANGE) {
               driverParamsBean = (JDBCDriverParamsBean)updateBean;
            } else {
               driverParamsBean = this.dsBean.getJDBCDriverParams();
            }
         }

         int retry = -1;

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            switch (updates[lcv].getUpdateType()) {
               case 1:
                  if (beanType == 1) {
                     try {
                        this.processCPUpdate(updates[lcv].getPropertyName(), poolParamsBean);
                     } catch (Exception var19) {
                        if (!updates[lcv].getPropertyName().equals("InitialCapacity")) {
                           throw var19;
                        }

                        retry = lcv;
                     }
                  } else if (beanType == 3) {
                     this.processDSUpdate(updates[lcv].getPropertyName(), dsParamsBean);
                  } else if (beanType == 102) {
                     this.processOracleUpdate(updates[lcv].getPropertyName(), oracleParamsBean);
                  } else if (beanType == 101) {
                     String s = propBean.getSysPropValue();
                     if (s == null) {
                        s = propBean.getValue();
                     } else {
                        try {
                           s = System.getProperty(s);
                        } catch (Exception var18) {
                           s = null;
                        }
                     }

                     this.processInternalPropertyUpdate(propBean.getName(), s);
                  } else if (beanType == 103) {
                     this.processDriverUpdate(updates[lcv].getPropertyName(), driverParamsBean);
                  } else {
                     JDBCLogger.logUnexpectedBeanChangeType(updateBean.toString(), updates[lcv].toString());
                  }
                  break;
               case 2:
                  if (beanType == 100) {
                     JDBCPropertyBean newBean = (JDBCPropertyBean)((JDBCPropertyBean)updates[lcv].getAddedObject());
                     String s = newBean.getSysPropValue();
                     if (s == null) {
                        s = newBean.getValue();
                     } else {
                        try {
                           s = System.getProperty(s);
                        } catch (Exception var17) {
                           s = null;
                        }
                     }

                     this.processInternalPropertyUpdate(newBean.getName(), s);
                  } else {
                     JDBCLogger.logUnexpectedBeanAddType(updateBean.toString(), updates[lcv].toString());
                  }
                  break;
               default:
                  JDBCLogger.logUnexpectedUpdateType(updateBean.toString(), updates[lcv].toString());
            }
         }

         if (retry >= 0) {
            this.processCPUpdate(updates[retry].getPropertyName(), poolParamsBean);
         }

      }
   }

   private void processCPUpdate(String propName, JDBCConnectionPoolParamsBean updateBean) throws Exception {
      if (propName.equals("MaxCapacity") && this.pool != null) {
         this.pool.setMaximumCapacity(updateBean.getMaxCapacity());
      } else if (propName.equals("MinCapacity") && this.pool != null) {
         this.pool.setMinimumCapacity(updateBean.getMinCapacity());
      } else if (propName.equals("InitialCapacity") && this.pool != null) {
         this.pool.setInitialCapacity(updateBean.getInitialCapacity());
      } else if (propName.equals("CapacityIncrement") && this.pool != null) {
         this.pool.setCapacityIncrement(updateBean.getCapacityIncrement());
      } else if (propName.equals("HighestNumWaiters") && this.pool != null) {
         this.pool.setHighestNumWaiters(updateBean.getHighestNumWaiters());
      } else if (propName.equals("InactiveConnectionTimeoutSeconds") && this.pool != null) {
         this.pool.setInactiveResourceTimeoutSeconds(updateBean.getInactiveConnectionTimeoutSeconds());
      } else if (propName.equals("ConnectionReserveTimeoutSeconds") && this.pool != null) {
         this.pool.setResourceReserveTimeoutSeconds(updateBean.getConnectionReserveTimeoutSeconds());
      } else if (propName.equals("ConnectionCreationRetryFrequencySeconds") && this.pool != null) {
         this.pool.setResourceCreationRetrySeconds(updateBean.getConnectionCreationRetryFrequencySeconds());
      } else if (propName.equals("ShrinkFrequencySeconds") && this.pool != null) {
         this.pool.setShrinkFrequencySeconds(updateBean.getShrinkFrequencySeconds());
      } else if (propName.equals("TestFrequencySeconds")) {
         if (this.pool != null) {
            this.pool.setTestFrequencySeconds(updateBean.getTestFrequencySeconds());
         } else if (this.multipool != null) {
            this.multipool.setHealthCheckFrequencySeconds(updateBean.getTestFrequencySeconds());
         }
      } else if (propName.equals("TestConnectionsOnReserve") && this.pool != null) {
         this.pool.setTestOnReserve(updateBean.isTestConnectionsOnReserve());
      } else if (propName.equals("StatementCacheSize") && this.pool != null) {
         this.pool.setStatementCacheSize(updateBean.getStatementCacheSize());
      } else if (propName.equals("TestTableName") && this.pool != null) {
         this.pool.setTestTableName(updateBean.getTestTableName());
      } else if (propName.equals("ProfileType") && this.pool != null) {
         this.pool.setProfileType(updateBean.getProfileType());
      } else if (propName.equals("ProfileHarvestFrequencySeconds") && this.pool != null) {
         this.pool.setProfileHarvestFrequencySeconds(updateBean.getProfileHarvestFrequencySeconds());
      } else if (propName.equals("IgnoreInUseConnsEnabled") && this.pool != null) {
         this.pool.setIgnoreInUseResources(updateBean.isIgnoreInUseConnectionsEnabled());
      } else if (propName.equals("SecondsToTrustAnIdlePoolConnection") && this.pool != null) {
         this.pool.setSecondsToTrustAnIdlePoolConnection(updateBean.getSecondsToTrustAnIdlePoolConnection());
      } else if (propName.equals("ConnectionHarvestMaxCount") && this.pool != null) {
         this.pool.setConnectionHarvestMaxCount(updateBean.getConnectionHarvestMaxCount());
      } else if (propName.equals("ConnectionHarvestTriggerCount") && this.pool != null) {
         this.pool.setConnectionHarvestTriggerCount(updateBean.getConnectionHarvestTriggerCount());
      } else if (propName.equals("CountOfRefreshFailuresTillDisable") && this.pool != null) {
         this.pool.setCountOfRefreshFailuresTillDisable(updateBean.getCountOfRefreshFailuresTillDisable());
      } else if (propName.equals("CountOfTestFailuresTillFlush") && this.pool != null) {
         this.pool.setCountOfTestFailuresTillFlush(updateBean.getCountOfTestFailuresTillFlush());
      } else {
         if (!propName.equals("ProfileConnectionLeakTimeoutSeconds") || this.pool == null) {
            throw new NonDynamicPropertyUpdateException(propName + " is not dynamically updatable. Please redeploy the updated JDBC DataSource for the changes to take effect.");
         }

         this.pool.setProfileConnectionLeakTimeoutSeconds(updateBean.getProfileConnectionLeakTimeoutSeconds());
      }

   }

   private void processDSUpdate(String propName, JDBCDataSourceParamsBean updateBean) throws Exception {
      if (propName.equals("FailoverIfBusy")) {
         this.multipool.setFailoverRequestIfBusy(updateBean.isFailoverRequestIfBusy());
      } else if (propName.equals("DataSourceList")) {
         this.multipool.setDataSourceList(updateBean.getDataSourceList());
      } else if (propName.equals("ProxySwitchingProperties")) {
         if (this.proxyds == null) {
            throw new NonDynamicPropertyUpdateException(propName + " is not dynamically updatable except for proxy datasource. Please redeploy the updated JDBC DataSource for the changes to take effect.");
         }

         this.proxyds.setProxySwitchingProperties(updateBean.getProxySwitchingProperties());
      } else if (propName.equals("ProxySwitchingCallback")) {
         if (this.proxyds == null) {
            throw new NonDynamicPropertyUpdateException(propName + " is not dynamically updatable except for proxy datasource. Please redeploy the updated JDBC DataSource for the changes to take effect.");
         }

         this.proxyds.setProxySwitchingCallback(updateBean.getProxySwitchingCallback());
      }

   }

   private void processOracleUpdate(String propName, JDBCOracleParamsBean updateBean) throws Exception {
      if (!propName.equals("OracleEnableJavaNetFastPath")) {
         if (propName.equals("OracleOptimizeUtf8Conversion")) {
            this.pool.setOracleOptimizeUtf8Conversion(updateBean.isOracleOptimizeUtf8Conversion());
         } else if (propName.equals("FanEnabled")) {
            if (this.pool instanceof HAConnectionPool) {
               HAConnectionPool hacp = (HAConnectionPool)this.pool;
               hacp.setFanEnabled(updateBean.isFanEnabled());
            }
         } else if (propName.equals("ReplayInitiationTimeout")) {
            this.pool.setReplayInitiationTimeout(updateBean.getReplayInitiationTimeout());
         }
      }

   }

   private void processInternalPropertyUpdate(String propName, String propValue) throws Exception {
      if (propName.equals("TestConnectionsOnRelease")) {
         this.pool.setTestOnRelease(Boolean.valueOf(propValue));
      } else if (propName.equals("TestConnectionsOnCreate")) {
         this.pool.setTestOnCreate(Boolean.valueOf(propValue));
      } else if (propName.equals("HighestNumUnavailable")) {
         this.pool.setHighestNumUnavailable(Integer.parseInt(propValue));
      } else if (propName.equals("CountOfTestFailuresTillFlush")) {
         this.pool.setCountOfTestFailuresTillFlush(Integer.parseInt(propValue));
      } else {
         if (!propName.equals("CountOfRefreshFailuresTillDisable")) {
            throw new NonDynamicPropertyUpdateException(propName + " is not dynamically updatable. Please redeploy the updated JDBC DataSource for the changes to take effect.");
         }

         this.pool.setCountOfRefreshFailuresTillDisable(Integer.parseInt(propValue));
      }

   }

   private void processDriverUpdate(String propName, JDBCDriverParamsBean updateBean) throws Exception {
      if (propName.equals("PasswordEncrypted")) {
         this.pool.updateCredential(updateBean.getPassword());
      } else {
         throw new NonDynamicPropertyUpdateException(propName + " is not dynamically updatable. Please redeploy the updated JDBC DataSource for the changes to take effect.");
      }
   }

   private boolean isGloballyScoped() {
      return this.appCtx.getSystemResourceMBean() != null;
   }

   private JDBCDataSourceBean[] getPoolBeans(JDBCDataSourceBean dsBean, ApplicationContext appCtx) throws ResourceException {
      String name = dsBean.getName();
      String legacyPoolName = JDBCUtil.getInternalProperty(dsBean, "LegacyPoolName");
      String poolName = legacyPoolName != null ? legacyPoolName : name;
      JDBCDataSourceBean[] poolBeans = new JDBCDataSourceBean[1];
      if (JDBCUtil.getLegacyType(dsBean) == 0) {
         if (dsBean.getJDBCDataSourceParams().getDataSourceList() != null) {
            poolBeans = getSystemResourceBeans(dsBean.getJDBCDataSourceParams().getDataSourceList(), 0, appCtx);
            if (poolBeans == null) {
               poolBeans = getAppDeploymentBeans(dsBean.getJDBCDataSourceParams().getDataSourceList(), appCtx);
            }

            if (poolBeans == null) {
               poolBeans = getApplicationBeans(dsBean.getJDBCDataSourceParams().getDataSourceList());
            }

            if (poolBeans == null) {
               poolBeans = getSystemResourceBeans(dsBean.getJDBCDataSourceParams().getDataSourceList(), 1, appCtx);
            }

            if (poolBeans == null) {
               poolBeans = getJDBCDataSourceBean(dsBean.getJDBCDataSourceParams().getDataSourceList());
            }

            this.checkMDSConfig(dsBean, poolBeans);
         } else {
            poolBeans[0] = dsBean;
         }
      } else {
         JDBCDataSourceBean poolBean = null;
         JDBCSystemResourceMBean[] resources = getDomainMBean(appCtx).getJDBCSystemResources();

         for(int lcv = 0; lcv < resources.length; ++lcv) {
            JDBCDataSourceBean currBean = resources[lcv].getJDBCResource();
            if (currBean != null) {
               int legacyType = JDBCUtil.getLegacyType(currBean);
               if ((legacyType == 1 || legacyType == 2) && poolName.equals(currBean.getName())) {
                  poolBean = currBean;
                  break;
               }
            }
         }

         if (poolBean != null) {
            if (poolBean.getJDBCDataSourceParams().getDataSourceList() != null) {
               poolBeans = getSystemResourceBeans(poolBean.getJDBCDataSourceParams().getDataSourceList(), 1, appCtx);
            } else {
               poolBeans[0] = poolBean;
            }
         } else {
            poolBeans = null;
         }
      }

      return poolBeans;
   }

   private static boolean isLLR(String dsName, ApplicationContext appCtx) {
      if (appCtx == null) {
         return false;
      } else {
         JDBCSystemResourceMBean[] resources = getDomainMBean(appCtx).getJDBCSystemResources();

         for(int i = 0; i < resources.length; ++i) {
            JDBCDataSourceBean currBean = resources[i].getJDBCResource();
            if (currBean != null && currBean.getName().equals(dsName)) {
               JDBCDataSourceParamsBean params = currBean.getJDBCDataSourceParams();
               return "LoggingLastResource".equals(params.getGlobalTransactionsProtocol());
            }
         }

         return false;
      }
   }

   private static boolean isMemberDSOfMultiDataSource(String dsName, ApplicationContext appCtx) {
      if (appCtx == null) {
         return false;
      } else {
         JDBCSystemResourceMBean[] resources = getDomainMBean(appCtx).getJDBCSystemResources();

         for(int i = 0; i < resources.length; ++i) {
            JDBCDataSourceBean currBean = resources[i].getJDBCResource();
            if (currBean != null) {
               String dsList = currBean.getJDBCDataSourceParams().getDataSourceList();
               if (dsList != null) {
                  List listOfDS = JDBCHelper.getHelper().dsToList(dsList);
                  Iterator it = listOfDS.iterator();

                  while(listOfDS.size() > 0 && it.hasNext()) {
                     String memberName = (String)it.next();
                     if (memberName.equals(dsName)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private static boolean isMemberOfMultiDataSourceLLR(String dsName, ApplicationContext appCtx) {
      if (appCtx == null) {
         return false;
      } else {
         JDBCSystemResourceMBean[] resources = getDomainMBean(appCtx).getJDBCSystemResources();

         for(int i = 0; i < resources.length; ++i) {
            JDBCDataSourceBean currBean = resources[i].getJDBCResource();
            if (currBean != null && currBean.getName() != null && currBean.getName().equals(dsName)) {
               String dsList = currBean.getJDBCDataSourceParams().getDataSourceList();
               if (dsList != null) {
                  List listOfDS = JDBCHelper.getHelper().dsToList(dsList);
                  Iterator it = listOfDS.iterator();
                  if (!it.hasNext()) {
                     return false;
                  }

                  String memberName = (String)it.next();

                  for(int j = 0; j < resources.length; ++j) {
                     JDBCDataSourceBean DSBean = resources[j].getJDBCResource();
                     if (DSBean.getName().equals(memberName)) {
                        return DSBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals("LoggingLastResource");
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private void checkMDSConfig(JDBCDataSourceBean mdsBean, JDBCDataSourceBean[] dsBeans) throws ResourceException {
      if (dsBeans == null) {
         throw new ResourceException("Unable to locate configuration of data sources (" + mdsBean.getJDBCDataSourceParams().getDataSourceList() + ") being used by multi data source " + mdsBean.getName());
      } else {
         for(int lcv = 0; lcv < dsBeans.length; ++lcv) {
            if (dsBeans[lcv] == null) {
               throw new ResourceException("Unable to locate complete configuration of data sources (" + mdsBean.getJDBCDataSourceParams().getDataSourceList() + ") being used by multi data source " + mdsBean.getName());
            }
         }

      }
   }

   public static JDBCDataSourceBean[] getJDBCDataSourceBean(String poolNames) {
      JDBCDataSourceBean[] poolBeans = getApplicationBeans(poolNames);
      if (poolBeans == null) {
         poolBeans = getSystemResourceBeans(poolNames, 0, (ApplicationContext)null);
      }

      if (poolBeans == null) {
         poolBeans = getAppDeploymentBeans(poolNames, (ApplicationContext)null);
      }

      if (poolBeans == null) {
         poolBeans = getSystemResourceBeans(poolNames, 1, (ApplicationContext)null);
      }

      return poolBeans;
   }

   private static JDBCDataSourceBean[] getSystemResourceBeans(String poolNames, int reqdLegacyType, ApplicationContext appCtx) {
      StringTokenizer st = new StringTokenizer(poolNames, ",");
      JDBCDataSourceBean[] poolBeans = new JDBCDataSourceBean[st.countTokens()];
      int found = 0;
      JDBCSystemResourceMBean[] resources = getDomainMBean(appCtx).getJDBCSystemResources();

      while(true) {
         while(st.hasMoreTokens()) {
            String poolName = st.nextToken();

            for(int lcv = 0; lcv < resources.length; ++lcv) {
               JDBCDataSourceBean currBean = resources[lcv].getJDBCResource();
               if (currBean != null && JDBCUtil.getLegacyType(currBean) == reqdLegacyType && poolName.equals(currBean.getName())) {
                  poolBeans[found++] = currBean;
                  break;
               }
            }
         }

         if (found == 0) {
            return null;
         }

         return poolBeans;
      }
   }

   private static JDBCDataSourceBean[] getAppDeploymentBeans(String poolNames, ApplicationContext appCtx) {
      StringTokenizer st = new StringTokenizer(poolNames, ",");
      JDBCDataSourceBean[] poolBeans = new JDBCDataSourceBean[st.countTokens()];
      int found = 0;
      AppDeploymentMBean[] resources = getDomainMBean(appCtx).getAppDeployments();

      while(true) {
         while(st.hasMoreTokens()) {
            String poolName = st.nextToken();

            for(int lcv = 0; lcv < resources.length; ++lcv) {
               String uri = resources[lcv].getSourcePath();
               if (uri != null && uri.endsWith("-jdbc.xml")) {
                  String memberDSName = resources[lcv].getApplicationName();
                  ApplicationContextInternal applicationContext = ApplicationAccess.getApplicationAccess().getApplicationContext(memberDSName);
                  if (applicationContext == null && appCtx != null) {
                     JDBCLogger.logStandaloneMultiDataSourceMemberNotFound(appCtx.getApplicationId(), memberDSName);
                     return null;
                  }

                  if (applicationContext != null) {
                     JDBCDataSourceBean currBean = getJDBCModuleFromAppModules(applicationContext.getApplicationModules(), poolName);
                     if (currBean != null) {
                        poolBeans[found++] = currBean;
                        break;
                     }
                  }
               }
            }
         }

         if (found == 0) {
            return null;
         }

         return poolBeans;
      }
   }

   private static JDBCDataSourceBean[] getApplicationBeans(String poolNames) {
      StringTokenizer st = new StringTokenizer(poolNames, ",");
      JDBCDataSourceBean[] poolBeans = new JDBCDataSourceBean[st.countTokens()];
      int found = 0;
      if (ApplicationAccess.getApplicationAccess().getCurrentApplicationContext() == null) {
         return null;
      } else {
         Module[] modules = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext().getApplicationModules();

         while(st.hasMoreTokens()) {
            String poolName = st.nextToken();
            JDBCDataSourceBean currBean = getJDBCModuleFromAppModules(modules, poolName);
            if (currBean != null) {
               poolBeans[found++] = currBean;
            }
         }

         if (found == 0) {
            return null;
         } else {
            return poolBeans;
         }
      }
   }

   private static JDBCDataSourceBean getJDBCModuleFromAppModules(Module[] modules, String dsName) {
      int i = dsName.indexOf("@");
      if (i != -1) {
         String moduleName = dsName.substring(i + 1);
         i = moduleName.indexOf("@");
         if (i == -1) {
            i = dsName.indexOf("@");
            moduleName = dsName.substring(0, i);
         } else {
            moduleName = moduleName.substring(0, i);
         }

         for(int lcv = 0; lcv < modules.length; ++lcv) {
            if ("jdbc".equals(modules[lcv].getType()) && moduleName.equals(modules[lcv].getId())) {
               DescriptorBean[] beans = modules[lcv].getDescriptors();
               JDBCDataSourceBean currBean = (JDBCDataSourceBean)beans[0];
               return currBean;
            }
         }

         return null;
      } else {
         for(int lcv = 0; lcv < modules.length; ++lcv) {
            if ("jdbc".equals(modules[lcv].getType())) {
               DescriptorBean[] beans = modules[lcv].getDescriptors();
               if (beans != null && beans.length == 1 && beans[0] instanceof JDBCDataSourceBean) {
                  JDBCDataSourceBean currBean = (JDBCDataSourceBean)beans[0];
                  if (dsName.equals(currBean.getName())) {
                     return currBean;
                  }
               }
            }
         }

         return null;
      }
   }

   private static DomainMBean getDomainMBean(ApplicationContext appCtx) {
      DomainMBean ret = null;
      if (appCtx != null) {
         try {
            ret = ((ApplicationContextInternal)appCtx).getProposedDomain();
         } catch (Throwable var3) {
         }
      }

      if (ret == null) {
         ret = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      }

      return ret;
   }

   private boolean isUCPDS() {
      String dtype = this.dsBean.getDatasourceType();
      if (dtype == null) {
         JDBCPropertyBean prop = this.dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.type");
         if (prop != null) {
            dtype = prop.getValue();
         }
      }

      return dtype != null && dtype.equals("UCP");
   }

   private boolean isProxyDS() {
      String dtype = this.dsBean.getDatasourceType();
      if (dtype == null) {
         JDBCPropertyBean prop = this.dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.type");
         if (prop != null) {
            dtype = prop.getValue();
         }
      }

      return dtype != null && dtype.equals("PROXY");
   }

   @Service
   private static class JDBCModuleServiceImpl implements JDBCModuleService {
      public JDBCDataSourceBean[] getJDBCDataSourceBean(String poolNames) {
         return JDBCModule.getJDBCDataSourceBean(poolNames);
      }
   }
}
