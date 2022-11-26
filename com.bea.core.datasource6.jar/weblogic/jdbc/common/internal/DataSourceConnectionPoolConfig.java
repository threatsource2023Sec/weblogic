package weblogic.jdbc.common.internal;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Driver;
import java.util.Properties;
import javax.resource.spi.security.PasswordCredential;
import javax.sql.DataSource;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.JDBCResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;

public class DataSourceConnectionPoolConfig implements ConnectionPoolConfig {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String DD_XA_TX_GROUP_NAME = "XATransactionGroup";
   protected JDBCDataSourceBean dsBean;
   private final String name;
   private String driver;
   private String url;
   private Properties driverProperties;
   private int defaultCountOfTestFailuresTillFlush = 2;
   private boolean removeInfectedConn = true;
   boolean credentialMappingEnabled;
   private boolean pinnedToThread;
   private boolean createConnectionInline;
   private ConnectionInfo defaultConnectionInfo;
   private boolean onePinnedConnectionOnly;
   boolean identityBasedConnectionPoolingEnabled;
   private int securityCacheTimeoutSeconds;
   private int harvestingFrequencySeconds;
   private boolean nativeXA;
   private int profileType;
   private int profileConnectionLeakTimeoutSeconds;
   private boolean useConfiguredCredentialsAsProxyUserDefault;
   private boolean invokeBeginEndRequest;
   private int labelingHighCost = Integer.MAX_VALUE;
   private boolean labelingHighCostSet;
   private int labelingHighCostReuseThreshold = 0;
   private boolean ucpDataSource;
   private boolean proxyDataSource;
   private ClassLoader classLoader;
   private String appName;
   private String moduleName;
   private String compName;
   static Class csfClass = null;
   static Method csfMethod = null;

   public DataSourceConnectionPoolConfig(JDBCDataSourceBean dsBean, ClassLoader classLoader, String appName, String moduleName, String compName) {
      this.dsBean = dsBean;
      this.name = JDBCUtil.getConnectionPoolName(dsBean);
      this.classLoader = classLoader;
      this.appName = appName;
      this.moduleName = moduleName;
      this.compName = compName;
      if (dsBean.getJDBCDriverParams() != null) {
         this.driver = dsBean.getJDBCDriverParams().getDriverName();
      } else {
         this.driver = null;
      }

   }

   public Properties getPoolProperties() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):doStart (10) oldAppScopedPool = false");
      }

      this.checkPasswordSettings();

      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               DataSourceConnectionPoolConfig.this.initJDBCParameters();
               return null;
            }
         });
      } catch (PrivilegedActionException var2) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):doStart (15) throws pae.toString()");
         }

         throw new ResourceException(var2.toString(), var2);
      }

      return this.getPoolParameters();
   }

   protected Properties getPoolParameters() {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP:getPoolParameters (10)");
      }

      int val = false;
      String strVal = null;
      Properties props = new Properties();
      if ((strVal = JDBCUtil.getConnectionPoolName(this.dsBean)) != null) {
         props.setProperty("name", strVal);
      }

      props.setProperty("maxCapacity", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getMaxCapacity()));
      if (this.dsBean.getJDBCConnectionPoolParams().isSet("MinCapacity")) {
         props.setProperty("minCapacity", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getMinCapacity()));
      } else {
         props.setProperty("minCapacity", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getInitialCapacity()));
      }

      props.setProperty("initialCapacity", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getInitialCapacity()));
      props.setProperty("capacityIncrement", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getCapacityIncrement()));
      if ((strVal = this.dsBean.getJDBCConnectionPoolParams().getTestTableName()) != null) {
         props.setProperty("testName", strVal);
      }

      if ((strVal = this.dsBean.getJDBCConnectionPoolParams().getInitSql()) != null) {
         props.setProperty("initName", strVal);
      }

      if ((strVal = this.dsBean.getJDBCConnectionPoolParams().getFatalErrorCodes()) != null) {
         props.setProperty("FatalErrorCodes", strVal);
      }

      props.setProperty("testOnReserve", Boolean.toString(this.dsBean.getJDBCConnectionPoolParams().isTestConnectionsOnReserve()));
      int val = this.dsBean.getJDBCConnectionPoolParams().getTestFrequencySeconds();
      if (val > 0) {
         props.setProperty("testFrequencySeconds", Integer.toString(val));
      } else {
         this.defaultCountOfTestFailuresTillFlush = JDBCConstants.computeCountTillFlush(val, this.dsBean.getJDBCConnectionPoolParams().getMaxCapacity());
      }

      val = this.dsBean.getJDBCConnectionPoolParams().getShrinkFrequencySeconds();
      if (val > 0) {
         props.setProperty("shrinkEnabled", "true");
         props.setProperty("shrinkFrequencySeconds", Integer.toString(val));
      } else {
         props.setProperty("shrinkEnabled", "false");
         props.setProperty("shrinkFrequencySeconds", "0");
      }

      props.setProperty("resvTimeoutSeconds", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getConnectionReserveTimeoutSeconds()));
      props.setProperty("resCreationRetrySeconds", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getConnectionCreationRetryFrequencySeconds()));
      props.setProperty("inactiveResTimeoutSeconds", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getInactiveConnectionTimeoutSeconds()));
      props.setProperty("maxWaiters", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getHighestNumWaiters()));
      strVal = this.dsBean.getJDBCDriverParams().getUrl();
      if (strVal != null) {
         props.setProperty("Url", strVal);
      }

      strVal = this.driverProperties.getProperty("Url");
      if (strVal != null) {
         props.setProperty("Url", strVal);
      }

      props.setProperty("UseXAInterface", Boolean.toString(this.dsBean.getJDBCDriverParams().isUseXaDataSourceInterface()));
      JDBCPropertyBean attachNetworkTimeout = this.dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.attachNetworkTimeout");
      if (attachNetworkTimeout != null) {
         strVal = attachNetworkTimeout.getValue();

         try {
            Integer i = Integer.valueOf(strVal);
            props.setProperty("weblogic.jdbc.attachNetworkTimeout", i.toString());
         } catch (Exception var12) {
         }
      }

      if ((val = this.dsBean.getJDBCConnectionPoolParams().getStatementCacheSize()) >= 0) {
         props.setProperty("PSCacheSize", Integer.toString(val));
      }

      props.setProperty("PSCacheType", this.dsBean.getJDBCConnectionPoolParams().getStatementCacheType());
      props.setProperty("createDelay", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getLoginDelaySeconds()));
      props.setProperty("secondsToTrustAnIdlePoolConnection", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getSecondsToTrustAnIdlePoolConnection()));
      props.setProperty("DebugLevel", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getJDBCXADebugLevel()));
      this.removeInfectedConn = this.dsBean.getJDBCConnectionPoolParams().isRemoveInfectedConnections();
      this.profileType = this.dsBean.getJDBCConnectionPoolParams().getProfileType();
      this.profileConnectionLeakTimeoutSeconds = this.dsBean.getJDBCConnectionPoolParams().getProfileConnectionLeakTimeoutSeconds();
      this.invokeBeginEndRequest = this.dsBean.getJDBCConnectionPoolParams().isInvokeBeginEndRequest();
      JDBCPropertyBean profileConnectionLeakTimeoutSecondsBean = this.dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.profileConnectionLeakTimeoutSeconds");
      if (profileConnectionLeakTimeoutSecondsBean != null) {
         strVal = profileConnectionLeakTimeoutSecondsBean.getValue();

         try {
            int i = Integer.parseInt(strVal);
            if (i >= 0) {
               this.profileConnectionLeakTimeoutSeconds = i;
            } else {
               JDBCLogger.logErrorMessage("Datasource " + this.name + " gets a negative value for an expected integer for " + "weblogic.jdbc.profileConnectionLeakTimeoutSeconds" + ": '" + i + "'. Setting will remain " + this.profileConnectionLeakTimeoutSeconds);
            }
         } catch (Exception var11) {
            JDBCLogger.logErrorMessage("Datasource " + this.name + " gets invalid value for an expected integer for " + "weblogic.jdbc.profileConnectionLeakTimeoutSeconds" + ": '" + strVal + "'. Setting will remain " + this.profileConnectionLeakTimeoutSeconds);
         }
      }

      props.setProperty("harvestFreqSecsonds", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getProfileHarvestFrequencySeconds()));
      props.setProperty("ignoreInUseResources", Boolean.toString(this.dsBean.getJDBCConnectionPoolParams().isIgnoreInUseConnectionsEnabled()));
      Properties internalProps = JDBCUtil.getProperties(this.dsBean, this.dsBean.getInternalProperties().getProperties(), (String)null);
      if ((strVal = (String)internalProps.get("TestConnectionsOnCreate")) != null) {
         props.setProperty("testOnCreate", strVal);
      } else {
         props.setProperty("testOnCreate", "true");
      }

      if ((strVal = (String)internalProps.get("TestConnectionsOnRelease")) != null) {
         props.setProperty("testOnRelease", strVal);
      }

      if ((strVal = (String)internalProps.get("HighestNumUnavailable")) != null) {
         props.setProperty("maxUnavl", strVal);
      }

      if ((strVal = (String)internalProps.get("CountOfTestFailuresTillFlush")) != null) {
         props.setProperty("countOfTestFailuresTillFlush", strVal);
      } else if (this.dsBean.getJDBCConnectionPoolParams().isSet("CountOfTestFailuresTillFlush")) {
         props.setProperty("countOfTestFailuresTillFlush", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getCountOfTestFailuresTillFlush()));
      } else {
         props.setProperty("countOfTestFailuresTillFlush", Integer.toString(this.defaultCountOfTestFailuresTillFlush));
      }

      if ((strVal = (String)internalProps.get("CountOfRefreshFailuresTillDisable")) != null) {
         props.setProperty("countOfRefreshFailuresTillDisable", strVal);
      } else {
         props.setProperty("countOfRefreshFailuresTillDisable", Integer.toString(this.dsBean.getJDBCConnectionPoolParams().getCountOfRefreshFailuresTillDisable()));
      }

      props.setProperty("maintenanceFrequencySeconds", "5");
      if ((strVal = (String)internalProps.get("SecurityCacheTimeoutSeconds")) != null) {
         this.securityCacheTimeoutSeconds = Integer.parseInt(strVal);
      } else if ((strVal = System.getProperty("weblogic.jdbc.securityCacheTimeoutSeconds")) != null) {
         try {
            this.securityCacheTimeoutSeconds = Integer.parseInt(strVal);
         } catch (NumberFormatException var10) {
            JDBCLogger.logErrorMessage("Invalid system property value weblogic.jdbc.securityCacheTimeoutSeconds=" + strVal);
            this.securityCacheTimeoutSeconds = Integer.parseInt("30");
         }
      } else if ((strVal = System.getProperty("weblogic.jdbc.maintenanceFrequencySeconds")) != null) {
         try {
            this.securityCacheTimeoutSeconds = Integer.parseInt(strVal);
         } catch (NumberFormatException var9) {
            JDBCLogger.logErrorMessage("Invalid system property value weblogic.jdbc.maintenanceFrequencySeconds=" + strVal);
            this.securityCacheTimeoutSeconds = Integer.parseInt("30");
         }
      } else {
         this.securityCacheTimeoutSeconds = Integer.parseInt("30");
      }

      if (this.securityCacheTimeoutSeconds > 600) {
         JDBCLogger.logErrorMessage("Invalid value for security cache timeout seconds.  Exceeds maximum of 600 seconds.");
         this.securityCacheTimeoutSeconds = 600;
      }

      if ((strVal = System.getProperty("weblogic.jdbc.harvestingFrequencySeconds")) != null) {
         try {
            this.harvestingFrequencySeconds = Integer.parseInt(strVal);
         } catch (NumberFormatException var8) {
            JDBCLogger.logErrorMessage("Invalid system property value weblogic.jdbc.harvestingFrequencySeconds=" + strVal);
            this.harvestingFrequencySeconds = Integer.parseInt("30");
         }
      } else {
         this.harvestingFrequencySeconds = Integer.parseInt("30");
      }

      if (this.pinnedToThread) {
         props.setProperty("maxCapacity", Integer.toString(Integer.MAX_VALUE));
      }

      if (this.createConnectionInline) {
         props.setProperty("capacityIncrement", "1");
         props.setProperty("initialCapacity", "0");
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP:getPoolParameters (100)");
      }

      return props;
   }

   private void checkPasswordSettings() throws ResourceException {
      if (JDBCHelper.getHelper().isProductionModeEnabled()) {
         JDBCPropertyBean[] props = this.dsBean.getJDBCDriverParams().getProperties().getProperties();
         if (props != null) {
            for(int lcv = 0; lcv < props.length; ++lcv) {
               if ("password".equals(props[lcv].getName()) && !Boolean.getBoolean("weblogic.management.allowClearTextPasswords")) {
                  throw new ResourceException("Security Violation: Data Source '" + this.dsBean.getName() + "' configured with clear-text password specified as a driver property, cannot be deployed in Production mode. To override the security check, set command line property 'weblogic.management.allowClearTextPasswords' to true.");
               }
            }

         }
      }
   }

   private void initJDBCParameters() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):initJDBCParameters (10)");
      }

      this.setCredentialMappingEnabled(this.dsBean.getJDBCConnectionPoolParams().isCredentialMappingEnabled());
      this.identityBasedConnectionPoolingEnabled = this.dsBean.getJDBCConnectionPoolParams().isIdentityBasedConnectionPoolingEnabled();
      this.pinnedToThread = this.dsBean.getJDBCConnectionPoolParams().isPinnedToThread();
      this.driver = this.dsBean.getJDBCDriverParams().getDriverName();
      this.url = this.dsBean.getJDBCDriverParams().getUrl();
      if (this.driver == null) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):initJDBCParameters (20)");
         }

         throw new ResourceException("No JDBC Driver name specified for pool " + this.name);
      } else {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug("  CP(" + this.name + "):initJDBCParameters (25) driver = " + this.driver);
         }

         if (this.dsBean.getJDBCDriverParams().getUrl() == null && (!DataSourceUtil.isXADataSource(this.driver, this.classLoader) || DataSourceUtil.isXADataSource(this.driver, this.classLoader) && !this.dsBean.getJDBCDriverParams().isUseXaDataSourceInterface())) {
            Object obj = null;

            try {
               obj = DataSourceUtil.loadDriver(this.driver);
            } catch (Exception var6) {
            }

            if (!(obj instanceof DataSource)) {
               if (obj instanceof Driver) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):initJDBCParameters (30)");
                  }

                  throw new ResourceException("No JDBC Driver URL specified for pool " + this.name);
               }

               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):initJDBCParameters (30)");
               }

               String foo = "Object (" + this.driver + ") specified for for pool " + this.name + " is not a Driver or a plain DataSource.\nThis object implements:\n";
               if (obj != null) {
                  foo = foo + "This object implements:\n";
                  Class[] clz = obj.getClass().getInterfaces();

                  for(int ii = 0; ii < clz.length; ++ii) {
                     foo = foo + clz[ii].getName();
                  }
               } else {
                  foo = foo + "Failed to load driver.";
               }

               throw new ResourceException(foo);
            }
         }

         try {
            this.driverProperties = (Properties)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  return DataSourceConnectionPoolConfig.this.getDriverProperties();
               }
            });
         } catch (PrivilegedActionException var7) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):initJDBCParameters (35) throws pae.toString()");
            }

            throw new ResourceException(var7.toString(), var7);
         }

         if (this.driver.equals("oracle.jdbc.xa.client.OracleXADataSource") && ("true".equalsIgnoreCase(this.driverProperties.getProperty("nativeXA")) || "true".equalsIgnoreCase(this.driverProperties.getProperty("NativeXA")))) {
            this.pinnedToThread = true;
            this.createConnectionInline = true;
            this.nativeXA = true;
         } else {
            if ("true".equalsIgnoreCase((String)this.driverProperties.remove("PinnedToThread")) || "true".equalsIgnoreCase((String)this.driverProperties.remove("pinnedToThread"))) {
               this.pinnedToThread = true;
            }

            if ("true".equalsIgnoreCase((String)this.driverProperties.remove("CreateConnectionInline")) || "true".equalsIgnoreCase((String)this.driverProperties.remove("createConnectionInline"))) {
               this.createConnectionInline = true;
            }

            if ("true".equalsIgnoreCase((String)this.driverProperties.remove("onePinnedConnectionOnly"))) {
               this.onePinnedConnectionOnly = true;
            }
         }

         String drivername = this.driverProperties.getProperty("drivername");
         int vid;
         if (drivername == null || (vid = VendorId.get(drivername)) == -1) {
            vid = VendorId.get(this.driver);
         }

         String clhc;
         if (vid == 8) {
            clhc = JDBCHelper.getHelper().getDomainName() + ":" + JDBCHelper.getHelper().getServerName() + ":" + this.dsBean.getName();
            this.driverProperties.put("XATransactionGroup", clhc);
         }

         this.defaultConnectionInfo = new ConnectionInfo(this.driverProperties.getProperty("user"), this.driverProperties.getProperty("password"));
         clhc = this.driverProperties.getProperty("ConnectionLabelingHighCost");
         if (clhc != null) {
            this.labelingHighCost = Integer.parseInt(clhc);
            this.labelingHighCostSet = true;
         }

         String hccrt = this.driverProperties.getProperty("HighCostConnectionReuseThreshold");
         if (hccrt != null) {
            this.labelingHighCostReuseThreshold = Integer.parseInt(hccrt);
         }

         String dtype = this.dsBean.getDatasourceType();
         if (dtype == null) {
            dtype = this.driverProperties.getProperty("weblogic.jdbc.type");
         }

         if (dtype != null) {
            if (dtype.equals("UCP")) {
               this.ucpDataSource = true;
            } else if (dtype.equals("PROXY")) {
               this.proxyDataSource = true;
            }
         }

         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):initJDBCParameters (100)");
         }

      }
   }

   public Properties getDriverProperties() throws ResourceException {
      Properties props = JDBCUtil.getProperties(this.dsBean, this.dsBean.getJDBCDriverParams().getProperties().getProperties(), this.name);
      Properties clonedProps;
      if (props == null) {
         clonedProps = new Properties();
      } else {
         clonedProps = (Properties)props.clone();
      }

      String pwd = null;
      Properties csfprops;
      if ((csfprops = getCsfProps(this.url)) != null) {
         clonedProps.setProperty("user", csfprops.getProperty("user"));
         pwd = csfprops.getProperty("password");
         clonedProps.setProperty("Url", csfprops.getProperty("url"));
      } else if (!this.dsBean.getJDBCDriverParams().isUsePasswordIndirection()) {
         if (KernelStatus.isJ2eeClient()) {
            pwd = props.getProperty("password");
         } else {
            pwd = this.dsBean.getJDBCDriverParams().getPassword();
         }
      } else {
         String key = props.getProperty("user");
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):getDriverProperties (10): IndirectPassword lookup: " + key);
         }

         CredentialManager cm = (CredentialManager)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.CREDENTIALMANAGER);
         String m = this.moduleName;
         if (this.compName != null) {
            m = m + "@" + this.compName;
         }

         Resource jdbcResource = new JDBCResource(this.appName, m, "ConnectionPool", this.name, "reserve");
         Object[] creds = cm.getCredentials(KERNEL_ID, key, jdbcResource, (ContextHandler)null, "weblogic.UserPassword");
         if (creds == null || creds.length <= 0) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):getDriverProperties (40): no mapping for password indirection: " + key);
            }

            JDBCLogger.logNoPasswordIndirectionCredentials(this.name, key);
            throw new ResourceException("No credential mapper entry found for password indirection user=" + key + " for data source " + this.name);
         }

         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):getDriverProperties (20): found mapping for password indirection : " + key);
         }

         PasswordCredential pc = (PasswordCredential)creds[0];
         String userName = pc.getUserName();
         if (userName != null && userName.length() > 0) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):getDriverProperties (30): mapped user name: " + userName);
            }

            clonedProps.setProperty("user", userName);
         }

         pwd = new String(pc.getPassword());
      }

      if (pwd != null) {
         clonedProps.setProperty("password", pwd);
      }

      return clonedProps;
   }

   public static Properties getCsfProps(String Url) throws ResourceException {
      if (Url == null) {
         return null;
      } else {
         int i = Url.indexOf(":");
         if (i != -1 && Url.substring(0, i + 1).toLowerCase().equals("csf:")) {
            if (csfClass == null) {
               try {
                  csfClass = Class.forName("weblogic.jdbc.common.security.Csf");
                  csfMethod = csfClass.getMethod("getCredential", Properties.class);
               } catch (Exception var5) {
                  throw new ResourceException("Failed to find getCredential method");
               }
            }

            Properties csfprops = new Properties();
            csfprops.setProperty("url", Url);

            try {
               csfMethod.invoke(csfClass, csfprops);
               return csfprops;
            } catch (Throwable var4) {
               throw new ResourceException("getting CSF credential failed", var4);
            }
         } else {
            return null;
         }
      }
   }

   public void setCredentialMappingEnabled(boolean val) {
      this.credentialMappingEnabled = val;
   }

   public boolean isCredentialMappingEnabled() {
      return this.credentialMappingEnabled;
   }

   public boolean isPinnedToThread() {
      return this.pinnedToThread;
   }

   public boolean isCreateConnectionInline() {
      return this.createConnectionInline;
   }

   public boolean isRemoveInfectedConnectionEnabled() {
      return this.removeInfectedConn;
   }

   public ConnectionInfo getDefaultConnectionInfo() {
      return this.defaultConnectionInfo;
   }

   public String getDriver() {
      return this.driver;
   }

   public boolean isNativeXA() {
      return this.nativeXA;
   }

   public int getSecurityCacheTimeoutSeconds() {
      return this.securityCacheTimeoutSeconds;
   }

   public int getHarvestingFrequencySeconds() {
      return this.harvestingFrequencySeconds;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public String getURL() {
      return this.url;
   }

   public boolean isOnePinnedConnectionOnly() {
      return this.onePinnedConnectionOnly;
   }

   public int getProfileType() {
      return this.profileType;
   }

   public boolean isIdentityBasedConnectionPoolingEnabled() {
      return this.identityBasedConnectionPoolingEnabled;
   }

   public boolean isOracleOptimizeUtf8Conversion() {
      return this.dsBean.getJDBCOracleParams() != null ? this.dsBean.getJDBCOracleParams().isOracleOptimizeUtf8Conversion() : false;
   }

   public boolean isWrapTypes() {
      if (this.dsBean.getJDBCConnectionPoolParams() == null) {
         return true;
      } else {
         return !this.dsBean.getJDBCConnectionPoolParams().isWrapJdbc() ? false : this.dsBean.getJDBCConnectionPoolParams().isWrapTypes();
      }
   }

   public int getXaRetryDurationSeconds() {
      return this.dsBean.getJDBCXAParams().getXaRetryDurationSeconds();
   }

   public void setJDBCDataSourceBean(JDBCDataSourceBean dsBean) {
      this.dsBean = dsBean;
   }

   public boolean isWrapJdbc() {
      return this.dsBean.getJDBCConnectionPoolParams() == null ? true : this.dsBean.getJDBCConnectionPoolParams().isWrapJdbc();
   }

   public int getConnectionLabelingHighCost() {
      return this.labelingHighCost;
   }

   public boolean isConnectionLabelingHighCostSet() {
      return this.labelingHighCostSet;
   }

   public int getHighCostConnectionReuseThreshold() {
      return this.labelingHighCostReuseThreshold;
   }

   public boolean isUCPDataSource() {
      return this.ucpDataSource;
   }

   public boolean isProxyDataSource() {
      return this.proxyDataSource;
   }

   public int getProfileConnectionLeakTimeoutSeconds() {
      return this.profileConnectionLeakTimeoutSeconds;
   }

   public boolean isSharedPool() {
      return JDBCUtil.isSharedPool(this.dsBean);
   }

   public boolean isStartupCritical() {
      return JDBCUtil.isStartupCritical(this.dsBean);
   }

   public boolean isStartupRetryEnabled() {
      return this.getStartupRetryCount() > 0;
   }

   public int getStartupRetryCount() {
      return JDBCUtil.getStartupRetryCount(this.dsBean);
   }

   public int getStartupRetryDelaySeconds() {
      return JDBCUtil.getStartupRetryDelaySeconds(this.dsBean);
   }

   public boolean isContinueMakeResourceAttemptsAfterFailure() {
      return JDBCUtil.isContinueMakeResourceAttemptsAfterFailure(this.dsBean);
   }

   public boolean isInvokeBeginEndRequest() {
      return this.invokeBeginEndRequest;
   }
}
