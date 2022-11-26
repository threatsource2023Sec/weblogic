package weblogic.jdbc.common.internal;

import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileStreamHandler;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.naming.Context;
import javax.sql.DataSource;
import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.jdbc.oracle.DataBasedConnectionAffinityCallback;
import weblogic.common.ResourceException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.jdbc.extensions.DataAffinityCallback;
import weblogic.jdbc.utils.JDBCConnectionMetaDataParser;
import weblogic.jdbc.utils.JDBCDriverInfo;
import weblogic.jdbc.utils.JDBCDriverInfoFactory;
import weblogic.jdbc.utils.JDBCURLHelper;
import weblogic.jdbc.utils.JDBCURLHelperFactory;
import weblogic.kernel.KernelStatus;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class DataSourceServiceImpl implements DataSourceService {
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DataAffinityCallback dataAffinityCallback;
   private List affinityPolicyListeners = new ArrayList();
   private Set deployedPools = new HashSet();
   private boolean sessionPolicyDeployed;
   private boolean dataPolicyDeployed;
   OutputStream logFileOutputStream;
   RotatingFileStreamHandler rotatingFileStreamHandler;
   private boolean DEBUG = Boolean.getBoolean("weblogic.debug.datasource.javaee6");
   private static volatile ConnectionPoolManager myCPMgr;
   private static volatile DataSourceManager myDSMgr;
   private static JDBCDriverInfoFactory factory = null;
   private static volatile JDBCConnectionMetaDataParser parser = null;

   DataSourceServiceImpl() {
   }

   DataAffinityCallback getDataAffinityCallback() {
      return this.dataAffinityCallback;
   }

   void poolDeployed(JDBCConnectionPool pool) {
      synchronized(this.deployedPools) {
         this.deployedPools.add(pool);
         this.processDeployedAffinityPolicyChange();
      }
   }

   void poolUndeployed(JDBCConnectionPool pool) {
      synchronized(this.deployedPools) {
         this.deployedPools.remove(pool);
         this.processDeployedAffinityPolicyChange();
      }
   }

   private void processDeployedAffinityPolicyChange() {
      boolean session = false;
      boolean data = false;
      Iterator var3 = this.deployedPools.iterator();

      JDBCConnectionPool cp;
      while(var3.hasNext()) {
         cp = (JDBCConnectionPool)var3.next();
         session = this.isSessionPolicy(cp);
         if (session) {
            break;
         }
      }

      if (this.sessionPolicyDeployed != session) {
         this.sessionPolicyDeployed = session;
         this.invokeAffinityPolicyListeners(ConnectionAffinityCallback.AffinityPolicy.WEBSESSION_BASED_AFFINITY, this.sessionPolicyDeployed);
      }

      var3 = this.deployedPools.iterator();

      while(var3.hasNext()) {
         cp = (JDBCConnectionPool)var3.next();
         data = this.isDataPolicy(cp);
         if (data) {
            break;
         }
      }

      if (this.dataPolicyDeployed != data) {
         this.dataPolicyDeployed = data;
         this.invokeAffinityPolicyListeners(ConnectionAffinityCallback.AffinityPolicy.DATA_BASED_AFFINITY, this.dataPolicyDeployed);
      }

   }

   private boolean isSessionPolicy(JDBCConnectionPool cp) {
      return "Session".equals(cp.getJDBCDataSource().getJDBCOracleParams().getAffinityPolicy());
   }

   private boolean isDataPolicy(JDBCConnectionPool cp) {
      return "Data".equals(cp.getJDBCDataSource().getJDBCOracleParams().getAffinityPolicy());
   }

   private void invokeAffinityPolicyListeners(ConnectionAffinityCallback.AffinityPolicy policy, boolean deployed) {
      PolicyListenerHolder[] listeners = (PolicyListenerHolder[])this.affinityPolicyListeners.toArray(new PolicyListenerHolder[this.affinityPolicyListeners.size()]);
      WorkManagerFactory.getInstance().getSystem().schedule(new AffinityPolicyListenerAdapter(listeners, policy, deployed));
   }

   public boolean isAffinityPolicyDeployed(ConnectionAffinityCallback.AffinityPolicy policy) {
      switch (policy) {
         case TRANSACTION_BASED_AFFINITY:
            return true;
         case WEBSESSION_BASED_AFFINITY:
            return this.sessionPolicyDeployed;
         case DATA_BASED_AFFINITY:
            return this.dataPolicyDeployed;
         default:
            throw new UnsupportedOperationException("Unknown affinity policy " + policy);
      }
   }

   public void registerAffinityCallback(AffinityCallback callback) {
      switch (callback.getAffinityPolicy()) {
         case TRANSACTION_BASED_AFFINITY:
            throw new UnsupportedOperationException("cannot register Transaction Affinity callback");
         case WEBSESSION_BASED_AFFINITY:
            throw new UnsupportedOperationException("cannot register Session Affinity callback");
         case DATA_BASED_AFFINITY:
            this.dataAffinityCallback = (DataAffinityCallback)callback;
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("registerAffinityCallback() data affinity callback=" + callback);
            }

            return;
         default:
            throw new UnsupportedOperationException("Unknown policy for callback " + callback);
      }
   }

   public void registerAffinityPolicyListener(AffinityPolicyListener listener, ConnectionAffinityCallback.AffinityPolicy policy) {
      synchronized(this) {
         Iterator var4 = this.affinityPolicyListeners.iterator();

         PolicyListenerHolder l;
         do {
            if (!var4.hasNext()) {
               this.affinityPolicyListeners.add(new PolicyListenerHolder(listener, policy));
               return;
            }

            l = (PolicyListenerHolder)var4.next();
         } while(l.listener != listener);

      }
   }

   public boolean unregisterAffinityCallback(AffinityCallback callback) {
      if (callback == null) {
         return false;
      } else {
         switch (callback.getAffinityPolicy()) {
            case TRANSACTION_BASED_AFFINITY:
               return false;
            case WEBSESSION_BASED_AFFINITY:
               return false;
            case DATA_BASED_AFFINITY:
               if (callback != this.dataAffinityCallback && (this.dataAffinityCallback == null || !(this.dataAffinityCallback instanceof DataAffinityCallbackImpl) || ((DataAffinityCallbackImpl)this.dataAffinityCallback).callback != callback)) {
                  return false;
               } else {
                  this.dataAffinityCallback = null;
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("unregisterAffinityCallback() data affinity callback=" + callback);
                  }

                  return true;
               }
            default:
               throw new UnsupportedOperationException("Unknown policy for callback " + callback);
         }
      }
   }

   public boolean unregisterAffinityPolicyListener(AffinityPolicyListener listener) {
      synchronized(this) {
         Iterator lit = this.affinityPolicyListeners.iterator();

         PolicyListenerHolder l;
         do {
            if (!lit.hasNext()) {
               return false;
            }

            l = (PolicyListenerHolder)lit.next();
         } while(l.listener != listener);

         lit.remove();
         return true;
      }
   }

   public JDBCDataSourceBean createJDBCDataSourceBean(DataSourceBean bean) throws ResourceException {
      Descriptor d;
      if (bean == null) {
         d = (new DescriptorManager()).createDescriptorRoot(DataSourceBean.class);
         bean = (DataSourceBean)d.getRootBean();
         bean.setName("java:comp/DefaultDataSource");
         bean.setClassName("org.apache.derby.jdbc.ClientDataSource");
         bean.setPortNumber(1527);
         bean.setServerName("localhost");
         bean.setDatabaseName("DefaultDataSource");
         bean.setInitialPoolSize(0);
         bean.setMaxPoolSize(15);
         bean.setMinPoolSize(0);
         bean.setMaxStatements(0);
         bean.setTransactional(false);
      }

      d = (new DescriptorManager()).createDescriptorRoot(JDBCDataSourceBean.class);
      JDBCDataSourceBean root = (JDBCDataSourceBean)d.getRootBean();
      if (this.DEBUG) {
         this.dumpParams(bean, "DataSourceBean");
      }

      if (bean.getName() != null && !bean.getName().equals("")) {
         root.getJDBCDataSourceParams().addJNDIName(bean.getName());
         root.setName(bean.getName());
         if (bean.getClassName() != null && !bean.getClassName().equals("")) {
            root.getJDBCDriverParams().setDriverName(bean.getClassName());
            this.getUrlAndProperties(bean, root);
            if (bean.isSet("InitialPoolSize") && bean.getInitialPoolSize() >= 0) {
               root.getJDBCConnectionPoolParams().setInitialCapacity(bean.getInitialPoolSize());
            }

            if (bean.isSet("MaxPoolSize") && bean.getMaxPoolSize() >= 0) {
               root.getJDBCConnectionPoolParams().setMaxCapacity(bean.getMaxPoolSize());
            }

            if (bean.isSet("MinPoolSize") && bean.getMinPoolSize() >= 0) {
               root.getJDBCConnectionPoolParams().setMinCapacity(bean.getMinPoolSize());
            }

            if (bean.isSet("MaxIdleTime") && bean.getMaxIdleTime() >= 0) {
               root.getJDBCConnectionPoolParams().setShrinkFrequencySeconds(bean.getMaxIdleTime());
            }

            if (bean.isSet("MaxStatements") && bean.getMaxStatements() >= 0) {
               root.getJDBCConnectionPoolParams().setStatementCacheSize(bean.getMaxStatements());
            }

            if (!bean.isTransactional()) {
               root.getJDBCDataSourceParams().setGlobalTransactionsProtocol("None");
            }

            if (KernelStatus.isJ2eeClient()) {
               root.getJDBCDataSourceParams().setGlobalTransactionsProtocol("None");
            }

            JDBCPropertiesBean properties = root.getJDBCDriverParams().getProperties();
            JDBCPropertyBean prop;
            if (bean.getPassword() != null && !bean.getPassword().equals("")) {
               if (KernelStatus.isJ2eeClient()) {
                  if (properties.lookupProperty("password") == null) {
                     prop = properties.createProperty("password");
                     prop.setValue(bean.getPassword());
                  }
               } else {
                  root.getJDBCDriverParams().setPassword(bean.getPassword());
               }
            }

            if (bean.getUser() != null && !bean.getUser().equals("") && properties.lookupProperty("user") == null) {
               prop = properties.createProperty("user");
               prop.setValue(bean.getUser());
            }

            if (bean.getDatabaseName() != null && !bean.getDatabaseName().equals("") && properties.lookupProperty("databaseName") == null) {
               prop = properties.createProperty("databaseName");
               prop.setValue(bean.getDatabaseName());
            }

            if (bean.isSet("PortNumber") && bean.getPortNumber() >= 0 && properties.lookupProperty("portNumber") == null) {
               prop = properties.createProperty("portNumber");
               prop.setValue("" + bean.getPortNumber());
            }

            if (bean.getServerName() != null && !bean.getServerName().equals("") && !bean.getServerName().equals("localhost") && properties.lookupProperty("serverName") == null) {
               prop = properties.createProperty("serverName");
               prop.setValue(bean.getServerName());
            }

            if (bean.getIsolationLevel() != null && !bean.getIsolationLevel().equals("")) {
               prop = properties.createProperty("desiredtxisolevel");
               prop.setValue(bean.getIsolationLevel());
            }

            JavaEEPropertyBean[] props = bean.getProperties();
            if (props != null) {
               for(int i = 0; i < props.length; ++i) {
                  if (this.DEBUG) {
                     System.out.println("JavaEEPropertyBean." + props[i].getName() + "=" + props[i].getValue());
                  }

                  if (props[i].getName().startsWith("weblogic") && !DataSourceUtil.isInternalProperty(props[i].getName())) {
                     this.setWeblogicAttribute(root, props[i].getName(), props[i].getValue());
                  } else {
                     if (props[i].getName().equals("weblogic.jdbc.sharedPoolJNDIName")) {
                        throw new ResourceException("DataSource Definition " + bean.getName() + " contains unsupported internal driver property [" + props[i].getName() + "=" + props[i].getValue() + "]");
                     }

                     if (properties.lookupProperty(props[i].getName()) == null && properties.lookupProperty(props[i].getName()) == null) {
                        prop = properties.createProperty(props[i].getName());
                        prop.setValue(props[i].getValue());
                     }
                  }
               }
            }

            ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();
            ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
            if (cic != null && !cic.isGlobalRuntime()) {
               String partitionName = cic.getPartitionName();
               if (partitionName != null) {
                  JDBCPropertiesBean internalProperties = root.getInternalProperties();
                  if (internalProperties.lookupProperty("PartitionName") == null) {
                     internalProperties.createProperty("PartitionName", partitionName);
                  }
               }
            }

            if (bean.isSet("LoginTimeout") && bean.getLoginTimeout() > 0 && this.DEBUG) {
               System.out.println("WARNING: LoginTimeout not supported");
            }

            if (this.DEBUG) {
               this.dump(root);
            }

            return root;
         } else {
            throw new ResourceException("ClassName must be provided");
         }
      } else {
         throw new ResourceException("Name must be provided");
      }
   }

   private boolean setAttribute(Object o, String prop, String value) throws ResourceException {
      String type = "";
      Method[] m1 = o.getClass().getMethods();
      Method m = null;
      String name = null;
      Class[] c = null;
      Object[] args = new Object[1];

      for(int i = 0; i < m1.length; ++i) {
         m = m1[i];
         name = m.getName();
         if (name.equals("set" + prop)) {
            c = m.getParameterTypes();
            if (c[0] == Integer.TYPE) {
               args[0] = Integer.valueOf(value);

               try {
                  m.invoke(o, args);
                  return true;
               } catch (Exception var12) {
                  throw new ResourceException("Failed to set attribute weblogic." + name);
               }
            }

            if (c[0] == String.class) {
               if (value != null && value.equals("")) {
                  value = null;
               }

               args[0] = value;

               try {
                  m.invoke(o, args);
                  return true;
               } catch (Exception var13) {
                  throw new ResourceException("Failed to set attribute weblogic." + name);
               }
            }

            if (c[0] == Boolean.TYPE) {
               args[0] = Boolean.valueOf(value);

               try {
                  m.invoke(o, args);
                  return true;
               } catch (Exception var14) {
                  throw new ResourceException("Failed to set attribute weblogic." + name);
               }
            }

            if (c[0] != byte[].class) {
               throw new ResourceException("Failed to find correct type for weblogic." + name);
            }

            if (value != null && !value.equals("")) {
               args[0] = value.getBytes();
            } else {
               args[0] = null;
            }

            try {
               m.invoke(o, args);
               return true;
            } catch (Exception var15) {
               throw new ResourceException("Failed to set attribute weblogic." + name);
            }
         }
      }

      return false;
   }

   private void setWeblogicAttribute(JDBCDataSourceBean root, String prop, String val) throws ResourceException {
      prop = prop.substring(9);
      if (!prop.equals("MetaData") && !prop.equals("InstanceId") && !prop.endsWith("PasswordEncryptedAsString") && !prop.endsWith("WalletPasswordEncrypted") && !prop.equals("DataSourceList") && !prop.equals("Name") && !prop.equals("Version") && !prop.equals("JNDINames")) {
         if (!prop.equals("JndiName") && !prop.equals("DriverName") && !prop.equals("Url") && !prop.equals("Password") && !prop.equals("InitialCapacity") && !prop.equals("MaxCapacity") && !prop.equals("MinCapacity") && !prop.equals("ShrinkFrequencySeconds") && !prop.equals("StatementCacheSize") && !prop.equals("PropertiesBean") && !prop.equals("User") && !prop.equals("IsolationLevel") && !prop.equals("LoginTimeout")) {
            if (!this.setAttribute(root, prop, val)) {
               if (!this.setAttribute(root.getJDBCConnectionPoolParams(), prop, val)) {
                  if (!this.setAttribute(root.getJDBCDataSourceParams(), prop, val)) {
                     if (!this.setAttribute(root.getJDBCDriverParams(), prop, val)) {
                        if (!this.setAttribute(root.getJDBCOracleParams(), prop, val)) {
                           if (!this.setAttribute(root.getJDBCXAParams(), prop, val)) {
                              throw new ResourceException("Failed to set weblogic." + prop + "=" + val);
                           }
                        }
                     }
                  }
               }
            }
         } else {
            throw new ResourceException("Invalid attribute: weblogic." + prop);
         }
      } else {
         throw new ResourceException("Invalid attribute: weblogic." + prop);
      }
   }

   public DataSource createDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException {
      ConnectionPoolManager cpMgr = null;
      DataSourceManager dsMgr = null;
      UCPDataSourceManager ucpdsMgr = null;
      ProxyDataSourceManager proxydsMgr = null;
      ConnectionPool pool = null;

      try {
         String type = dsBean.getDatasourceType();
         if (type == null) {
            JDBCPropertyBean prop = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.type");
            if (prop != null) {
               type = prop.getValue();
            }
         }

         if (type != null) {
            DataSource uds;
            if (type.equals("PROXY")) {
               proxydsMgr = ProxyDataSourceManager.getInstance();
               uds = (DataSource)proxydsMgr.create(dsBean, appName, moduleName, compName);
               createProxyDataSourceRuntimeMBean(uds, appName, moduleName, compName, dsBean);
               return uds;
            }

            if (type.equals("UCP")) {
               ucpdsMgr = UCPDataSourceManager.getInstance();
               uds = (DataSource)ucpdsMgr.create(dsBean, appName, moduleName, compName);
               createUCPDataSourceRuntimeMBean(uds, appName, moduleName, compName, dsBean);
               return uds;
            }
         }

         cpMgr = this.getConnectionPoolManager();
         dsMgr = this.getDataSourceManager();
         Object ret = cpMgr.createAndStartPool(dsBean, appName, moduleName, compName);
         if (ret instanceof HAConnectionPool) {
            pool = (HAConnectionPool)ret;
            this.createHADataSourceRuntimeMBean((HAConnectionPool)pool, appName, moduleName, compName, ((ConnectionPool)pool).getJDBCDataSource());
         } else {
            pool = (ConnectionPool)ret;
            ((ConnectionPool)pool).suspend(false);
            this.createDataSourceRuntimeMBean((ConnectionPool)pool, appName, moduleName, compName, ((ConnectionPool)pool).getJDBCDataSource());
         }

         JDBCDataSourceBean[] poolBeans = new JDBCDataSourceBean[]{dsBean};
         dsMgr.checkDataSource(dsBean, appName, moduleName, compName, poolBeans);
         ((ConnectionPool)pool).resume();
         dsMgr.createAndStartDataSource(dsBean, appName, moduleName, compName, (Context)null, poolBeans, false);
      } catch (Exception var14) {
         try {
            if (dsBean != null) {
               if (cpMgr != null) {
                  cpMgr.shutdownAndDestroyPool(dsBean, appName, moduleName, compName);
                  this.destroyDataSourceRuntimeMBean(dsBean.getJDBCDriverParams().getDriverName(), appName, moduleName, compName, dsBean.getName(), dsBean);
               } else if (proxydsMgr != null) {
                  this.destroyProxyDataSourceRuntimeMBean(dsBean.getName(), appName, moduleName, compName);
               } else if (ucpdsMgr != null) {
                  this.destroyProxyDataSourceRuntimeMBean(dsBean.getName(), appName, moduleName, compName);
               }
            }
         } catch (Exception var13) {
         }

         if (var14 instanceof ResourceException) {
            throw (ResourceException)var14;
         }

         throw new ResourceException(var14);
      }

      RmiDataSource rds = dsMgr.getDataSource(dsBean.getName(), appName, moduleName, compName);
      return rds;
   }

   public void destroyDataSource(String poolName, String appName, String moduleName, String compName) throws IllegalStateException, ResourceException {
      DataSourceManager dsMgr = this.getDataSourceManager();
      ConnectionPoolManager cpMgr = this.getConnectionPoolManager();
      JDBCConnectionPool pool = ConnectionPoolManager.getPool(poolName, appName, moduleName, compName);
      Descriptor d = (new DescriptorManager()).createDescriptorRoot(JDBCDataSourceBean.class);
      JDBCDataSourceBean dsBean = (JDBCDataSourceBean)d.getRootBean();
      dsBean.setName(poolName);
      dsMgr.shutdownAndDestroyDataSource(dsBean, appName, moduleName, compName);
      if (pool != null) {
         pool.suspend(false);

         try {
            this.destroyDataSourceRuntimeMBean(pool.getDriverVersion(), appName, moduleName, compName, pool.getName(), dsBean);
         } catch (Exception var11) {
         }
      }

      try {
         cpMgr.shutdownAndDestroyPool(poolName, appName, moduleName, compName);
      } catch (ResourceException var12) {
         if (var12.getMessage() == null || !var12.getMessage().startsWith("Unknown Data")) {
            throw var12;
         }
      }

   }

   private void getUrlAndProperties(DataSourceBean bean, JDBCDataSourceBean root) throws ResourceException {
      if ((bean.getDatabaseName() == null || bean.getDatabaseName().equals("")) && (!bean.isSet("PortNumber") || bean.getPortNumber() < 0) && (bean.getServerName() == null || bean.getServerName().equals("") || bean.getServerName().equals("localhost"))) {
         if (bean.getUrl() != null && !bean.getUrl().equals("")) {
            root.getJDBCDriverParams().setUrl(bean.getUrl());
         } else {
            root.getJDBCDriverParams().setUrl("");
         }

      } else {
         if (parser == null) {
            try {
               parser = new JDBCConnectionMetaDataParser();
            } catch (Exception var14) {
               throw new ResourceException("Failed to get parser");
            }
         }

         if (factory == null) {
            factory = parser.getJDBCDriverInfoFactory();
         }

         JDBCDriverInfo driverInfo = factory.getDriverInfoByClass(bean.getClassName(), bean.isTransactional());
         if (driverInfo == null) {
            driverInfo = factory.getDriverInfoByClass(bean.getClassName(), !bean.isTransactional());
         }

         if (driverInfo == null) {
            throw new ResourceException("WebLogic Server does not know about the driver class=" + bean.getClassName() + ".  Plase remove any description for serverName, portNumber, and databaseName, and just specify a url.");
         } else {
            JDBCURLHelper helper = null;

            try {
               JDBCURLHelperFactory helperFactory = JDBCURLHelperFactory.newInstance();
               helper = helperFactory.getJDBCURLHelper(driverInfo);
            } catch (Exception var13) {
               throw new ResourceException(var13.getMessage());
            }

            if (bean.getDatabaseName() != null && !bean.getDatabaseName().equals("")) {
               driverInfo.setDbmsName(bean.getDatabaseName());
            } else {
               driverInfo.setDbmsName(driverInfo.getDbmsNameDefault());
            }

            if (bean.isSet("PortNumber") && bean.getPortNumber() >= 0) {
               driverInfo.setDbmsPort("" + bean.getPortNumber());
            } else {
               driverInfo.setDbmsPort(driverInfo.getDbmsPortDefault());
            }

            if (bean.getServerName() != null && !bean.getServerName().equals("")) {
               driverInfo.setDbmsHost(bean.getServerName());
            } else {
               driverInfo.setDbmsHost(driverInfo.getDbmsHostDefault());
            }

            if (bean.getPassword() != null && !bean.getPassword().equals("")) {
               driverInfo.setPassword(bean.getPassword());
            }

            if (bean.getUser() != null && !bean.getUser().equals("")) {
               driverInfo.setUserName(bean.getUser());
            }

            JavaEEPropertyBean[] jeeprops = bean.getProperties();
            if (jeeprops != null) {
               for(int i = 0; i < jeeprops.length; ++i) {
                  if (!jeeprops[i].getName().startsWith("weblogic")) {
                     try {
                        driverInfo.setUknownAttribute(jeeprops[i].getName(), jeeprops[i].getValue());
                     } catch (AssertionError var12) {
                     }
                  }
               }
            }

            try {
               root.getJDBCDriverParams().setUrl(helper.getURL());
            } catch (Exception var11) {
               throw new ResourceException(var11.getMessage());
            }

            try {
               Properties props = helper.getProperties();
               JDBCPropertiesBean properties = root.getJDBCDriverParams().getProperties();
               Enumeration e = props.propertyNames();

               while(e.hasMoreElements()) {
                  String propName = (String)e.nextElement();
                  JDBCPropertyBean prop = properties.createProperty(propName);
                  prop.setValue(props.getProperty(propName));
                  if (this.DEBUG) {
                     System.out.println("DEBUG:" + propName + "=" + props.getProperty(propName));
                  }
               }
            } catch (Exception var15) {
               throw new ResourceException(var15.getMessage());
            }

            if (this.DEBUG) {
               System.out.println("DEBUG: generated url=" + root.getJDBCDriverParams().getUrl());
            }

         }
      }
   }

   private void dumpParams(Object o, String type) {
      Method[] m1 = o.getClass().getMethods();
      Method m = null;
      String name = null;
      Class c = null;

      for(int i = 0; i < m1.length; ++i) {
         m = m1[i];
         name = m.getName();
         if ((name.startsWith("get") || name.startsWith("is")) && !name.equals("isEditable") && !name.equals("getHashValue") && !name.equals("getInstanceId") && !name.equals("getClass") && !name.equals("getDescriptor") && !name.equals("getInherited") && !name.equals("getInheritedProperties") && !name.equals("getJNDINames") && !name.equals("getMetaData") && !name.equals("getParentBean") && !name.equals("getPasswordEncrypted") && !name.equals("getProperties") && !name.equals("getXMLComments") && !name.equals("isChildProperty") && !name.equals("getOnsWalletPasswordEncrypted") && !name.endsWith("PasswordEncryptedAsString") && !name.endsWith("Set") && !name.endsWith("Inherited")) {
            c = m.getReturnType();
            if (c != Integer.TYPE && c != String.class && c != Boolean.TYPE) {
               System.out.println("ERROR: failed to print " + type + "." + name);
            } else {
               try {
                  System.out.println(type + "." + name + "=" + m.invoke(o));
               } catch (Exception var9) {
                  System.out.println("ERROR: failed to print " + type + "." + name);
               }
            }
         }
      }

   }

   private void dump(JDBCDataSourceBean root) {
      System.out.println("Name=" + root.getName());
      this.dumpParams(root.getJDBCConnectionPoolParams(), "JDBCConnectionPoolParams");
      this.dumpParams(root.getJDBCDataSourceParams(), "JDBCDataSourceParams");
      this.dumpParams(root.getJDBCDriverParams(), "JDBCDriverParams");
      this.dumpParams(root.getJDBCOracleParams(), "JDBCOracleParams");
      this.dumpParams(root.getJDBCXAParams(), "JDBCXAParams");
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("DataSourceServiceImpl: " + msg);
   }

   public void registerDataAffinityCallback(DataBasedConnectionAffinityCallback callback) {
      this.dataAffinityCallback = new DataAffinityCallbackImpl(callback);
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("registerDataAffinityCallback() data affinity callback=" + callback);
      }

   }

   public OutputStream getLogFileOutputStream() throws Exception {
      if (this.rotatingFileStreamHandler == null) {
         LogFileConfigBean logFileConfigBean = new LogFileConfigBean();
         logFileConfigBean.setBaseLogFileName("datasource.log");
         logFileConfigBean.setLogFileRotationDir(".");
         this.rotatingFileStreamHandler = new RotatingFileStreamHandler(logFileConfigBean);
      }

      return this.rotatingFileStreamHandler != null ? this.rotatingFileStreamHandler.getRotatingFileOutputStream() : null;
   }

   public ConnectionPoolManager getConnectionPoolManager() {
      if (myCPMgr == null) {
         myCPMgr = new ConnectionPoolManager();
      }

      return myCPMgr;
   }

   public DataSourceManager getDataSourceManager() {
      if (myDSMgr == null) {
         myDSMgr = DataSourceManager.getInstance();
      }

      return myDSMgr;
   }

   public void createHADataSourceRuntimeMBean(HAConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws Exception {
   }

   public void createDataSourceRuntimeMBean(ConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws Exception {
   }

   public void destroyDataSourceRuntimeMBean(String driverName, String appName, String moduleName, String compName, String poolName, JDBCDataSourceBean dsBean) throws Exception {
   }

   public static void createProxyDataSourceRuntimeMBean(DataSource proxyds, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws Exception {
   }

   public void destroyProxyDataSourceRuntimeMBean(String name, String appName, String moduleName, String compName) throws Exception {
   }

   public static void createUCPDataSourceRuntimeMBean(DataSource ucpds, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws Exception {
   }

   public static void destroyUCPDataSourceRuntimeMBean(String name, String appName, String moduleName, String compName, String poolName) throws Exception {
   }

   class DataAffinityCallbackImpl implements DataAffinityCallback {
      DataBasedConnectionAffinityCallback callback;

      DataAffinityCallbackImpl(DataBasedConnectionAffinityCallback callback) {
         this.callback = callback;
      }

      public boolean isApplicationContextAvailable() {
         return true;
      }

      public ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy() {
         return this.callback.getAffinityPolicy();
      }

      public Object getConnectionAffinityContext() {
         return this.callback.getConnectionAffinityContext();
      }

      public void setAffinityPolicy(ConnectionAffinityCallback.AffinityPolicy policy) {
         this.callback.setAffinityPolicy(policy);
      }

      public boolean setConnectionAffinityContext(Object affinityContext) {
         return this.callback.setConnectionAffinityContext(affinityContext);
      }

      public int getPartitionId() {
         return this.callback.getPartitionId();
      }

      public boolean setDataKey(Object keyObject) {
         return this.callback.setDataKey(keyObject);
      }
   }

   private class PolicyListenerHolder {
      AffinityPolicyListener listener;
      ConnectionAffinityCallback.AffinityPolicy policy;

      PolicyListenerHolder(AffinityPolicyListener listener, ConnectionAffinityCallback.AffinityPolicy policy) {
         this.listener = listener;
         this.policy = policy;
      }
   }

   private class AffinityPolicyListenerAdapter extends WorkAdapter {
      private PolicyListenerHolder[] listeners;
      private ConnectionAffinityCallback.AffinityPolicy policy;
      private boolean deployed;

      AffinityPolicyListenerAdapter(PolicyListenerHolder[] listeners, ConnectionAffinityCallback.AffinityPolicy policy, boolean deployed) {
         this.listeners = listeners;
         this.policy = policy;
         this.deployed = deployed;
      }

      public void run() {
         for(int i = 0; i < this.listeners.length; ++i) {
            if (this.policy.equals(this.listeners[i].policy)) {
               final AffinityPolicyListener flistener = this.listeners[i].listener;
               SecurityServiceManager.runAs(DataSourceServiceImpl.KERNELID, SubjectUtils.getAnonymousSubject(), new PrivilegedAction() {
                  public Object run() {
                     flistener.affinityPolicyNotification(AffinityPolicyListenerAdapter.this.policy, AffinityPolicyListenerAdapter.this.deployed);
                     return null;
                  }
               });
            }
         }

      }
   }
}
