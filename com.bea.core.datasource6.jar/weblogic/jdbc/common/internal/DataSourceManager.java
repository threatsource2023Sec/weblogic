package weblogic.jdbc.common.internal;

import com.oracle.core.registryhelper.RegistryListener;
import com.oracle.core.registryhelper.utils.MonitorableMap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.transaction.SystemException;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ObjectLifeCycle;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.rmi.internal.RmiDriverSettings;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.collections.ConcurrentProperties;

public final class DataSourceManager implements ObjectLifeCycle {
   private static final DataSourceManager INSTANCE = new DataSourceManager();
   private final MonitorableMap dsList = new MonitorableMap();
   private final HashMap dscpList = new HashMap();
   private final PriorInstances priorInstances = new PriorInstances();
   private static DataSourceService dataSourceService;

   private DataSourceManager() {
      dataSourceService = new DataSourceServiceImpl();
   }

   public static DataSourceManager getInstance() {
      return INSTANCE;
   }

   public void start(Object unused) throws ResourceException {
   }

   public void resume() throws ResourceException {
      Iterator dss = this.getDataSources().iterator();

      while(dss.hasNext()) {
         RmiDataSource ds = (RmiDataSource)dss.next();
         ds.start((Object)null);
      }

   }

   public void suspend(boolean shuttingDown) throws ResourceException {
      Iterator dss = this.getDataSources().iterator();

      while(dss.hasNext()) {
         RmiDataSource ds = (RmiDataSource)dss.next();
         ds.shutdown();
      }

   }

   public void forceSuspend(boolean shuttingDown) throws ResourceException {
      Iterator dss = this.getDataSources().iterator();

      while(dss.hasNext()) {
         RmiDataSource ds = (RmiDataSource)dss.next();
         ds.shutdown();
      }

   }

   public void shutdown() throws ResourceException {
   }

   public void createAndStartDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, Context jdbcCtx, ClassLoader classLoader) throws ResourceException {
      this.createAndStartDataSource(dsBean, appName, moduleName, compName, jdbcCtx, new JDBCDataSourceBean[]{dsBean}, classLoader, false);
   }

   public void createAndStartDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, Context jdbcCtx, JDBCDataSourceBean[] poolBeans, boolean isDSMemberOfMDS) throws ResourceException {
      this.createAndStartDataSource(dsBean, appName, moduleName, compName, jdbcCtx, poolBeans, (ClassLoader)null, isDSMemberOfMDS);
   }

   public void createAndStartDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, Context jdbcCtx, JDBCDataSourceBean[] poolBeans, ClassLoader classLoader, boolean isDSMemberOfMDS) throws ResourceException {
      String name = dsBean.getName();
      String type = JDBCUtil.getDataSourceType(dsBean);
      boolean typeMDS = type.equals("MDS");
      String s;
      if (!typeMDS) {
         if (type != null && !type.equals("GENERIC") && !type.equals("AGL")) {
            throw new ResourceException("Invalid datasource type '" + type + "' for datasource '" + name + "' with no members.");
         }

         if (appName == null) {
            JDBCLogger.logCreatingDS(name, getJNDINamesAsString(dsBean.getJDBCDataSourceParams().getJNDINames()));
         } else {
            s = moduleName;
            if (compName != null) {
               s = moduleName + "@" + compName;
            }

            JDBCLogger.logCreatingASDS(name, appName, s, getJNDINamesAsString(dsBean.getJDBCDataSourceParams().getJNDINames()));
         }
      } else {
         if (type != null && !type.equals("MDS")) {
            throw new ResourceException("Invalid datasource type '" + type + "' for multi datasource '" + name + "'.");
         }

         if (appName == null) {
            JDBCLogger.logCreatingMDS(name, getJNDINamesAsString(dsBean.getJDBCDataSourceParams().getJNDINames()));
         } else {
            s = moduleName;
            if (compName != null) {
               s = moduleName + "@" + compName;
            }

            JDBCLogger.logCreatingASMDS(name, appName, s, getJNDINamesAsString(dsBean.getJDBCDataSourceParams().getJNDINames()));
         }
      }

      if (this.dataSourceExists(name, appName, moduleName, compName)) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* DSM:createAndStartDS (30)");
         }

         throw new ResourceException("DataSource " + name + " already exists");
      } else {
         RmiDataSource ds = this.createDataSource(dsBean, appName, moduleName, compName, jdbcCtx, poolBeans, classLoader, isDSMemberOfMDS);
         ds.start((Object)null);
         this.addDataSource(name, appName, moduleName, compName, ds);
         if (!typeMDS) {
            JDBCLogger.logCreatedDS2(name);
         } else {
            JDBCLogger.logCreatedMDS(name);
         }

         JDBCHelper jdbcHelper = JDBCHelper.getHelper();
         boolean isLLRDataSource = jdbcHelper.isLLRPool(dsBean);

         try {
            if (isLLRDataSource) {
               if (!isDSMemberOfMDS) {
                  ds.recoverLoggingResourceTransactions();
               } else if (!jdbcHelper.isRACPool(name, appName, moduleName, compName)) {
                  ds.recoverLoggingResourceTransactions();
               }
            }
         } catch (SystemException var17) {
            if (isLLRDataSource && ds.getPartitionName() != null) {
               throw new ResourceException("Exception while deploying logging resource in partition " + ds.getPartitionName(), var17);
            }

            if (isLLRDataSource && (!isDSMemberOfMDS || jdbcHelper.isLLRTablePerDataSource(name))) {
               TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
               tm.registerFailedLoggingResource(var17);
               throw new ResourceException("Exception while recovering database transaction table", var17);
            }
         }

      }
   }

   public void shutdownAndDestroyDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException {
      String name = dsBean.getName();
      boolean typeMDS = JDBCUtil.getDataSourceType(dsBean).equals("MDS");
      if (!typeMDS) {
         if (appName == null) {
            JDBCLogger.logDestroyingDS(name);
         } else {
            JDBCLogger.logDestroyingASDS(name, appName, moduleName);
         }
      } else if (appName == null) {
         JDBCLogger.logDestroyingMDS(name);
      } else {
         JDBCLogger.logDestroyingASMDS(name, appName, moduleName);
      }

      RmiDataSource ds = this.getDataSource(name, appName, moduleName, compName);
      if (ds == null) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <*  DSM:shutdownAndDestroyDS (20)");
         }

         throw new ResourceException("Unknown DataSource " + name);
      } else {
         ds.shutdown();
         this.removeDataSource(name, appName, moduleName, compName);
         if (!typeMDS) {
            JDBCLogger.logDestroyedDS2(name);
         } else {
            JDBCLogger.logDestroyedMDS(name);
         }

      }
   }

   public void checkDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, JDBCDataSourceBean[] poolBeans) throws ResourceException {
      this.checkDataSource(dsBean, appName, moduleName, compName, poolBeans, (ClassLoader)null);
   }

   public void checkDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, JDBCDataSourceBean[] poolBeans, ClassLoader classLoader) throws ResourceException {
      String name = dsBean.getName();
      if (this.dataSourceExists(name, appName, moduleName, compName)) {
         throw new ResourceException("DataSource " + name + " already exists");
      } else {
         String globalTxProt = dsBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol();
         boolean participatesInGlobalTXS = !globalTxProt.equals("None");
         String legacyPoolName = JDBCUtil.getInternalProperty(dsBean, "LegacyPoolName");
         String poolName = legacyPoolName != null ? legacyPoolName : JDBCUtil.getConnectionPoolName(dsBean);

         try {
            this.checkDSConfig(dsBean, poolName, appName, moduleName, compName, poolBeans, participatesInGlobalTXS, classLoader);
         } catch (Exception var14) {
            ResourceException re = new ResourceException(var14.toString());
            re.initCause(var14);
            throw re;
         }
      }
   }

   private RmiDataSource createDataSource(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, Context jdbcCtx, JDBCDataSourceBean[] poolBeans, ClassLoader classLoader, boolean isDSMemberOfMDS) throws ResourceException {
      String scope = dsBean.getJDBCDataSourceParams().getScope();
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > DSM:createDS (10)");
      }

      String name = dsBean.getName();
      String[] jndiNames = dsBean.getJDBCDataSourceParams().getJNDINames();
      String globalTxProt = dsBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol();
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug("  DSM:createDS globalTxProt =" + globalTxProt);
      }

      boolean participatesInGlobalTXS = !globalTxProt.equals("None");
      JDBCHelper jdbcHelper = JDBCHelper.getHelper();
      boolean isLLRDataSource = jdbcHelper.isLLRPool(dsBean);
      String legacyPoolName = JDBCUtil.getInternalProperty(dsBean, "LegacyPoolName");
      String poolName = legacyPoolName != null ? legacyPoolName : name;
      RmiDataSource ret = null;

      try {
         this.checkDSConfig(dsBean, poolName, appName, moduleName, compName, poolBeans, participatesInGlobalTXS, classLoader);
         RmiDriverSettings rmi_settings = null;
         if (appName == null) {
            rmi_settings = this.createRmiDriverSettings(dsBean.getJDBCDataSourceParams().isRowPrefetch(), dsBean.getJDBCDataSourceParams().getRowPrefetchSize(), dsBean.getJDBCDataSourceParams().getStreamChunkSize());
         }

         boolean useDatabaseCredentials = dsBean.getJDBCOracleParams().isUseDatabaseCredentials();
         if (JDBCUtil.getDataSourceType(dsBean).equals("MDS") && poolBeans != null) {
            for(int i = 0; i < poolBeans.length; ++i) {
               if (poolBeans[i].getJDBCOracleParams().isUseDatabaseCredentials()) {
                  useDatabaseCredentials = true;
                  break;
               }
            }
         }

         String partitionName = JDBCUtil.getPartitionName(dsBean);
         boolean crossPartitionEnabled = JDBCUtil.isCrossPartitionEnabled(dsBean);
         boolean useBI_UserName = poolBeans != null && poolBeans[0] != null && poolBeans[0].getJDBCDriverParams().getProperties().lookupProperty("IMPERSONATE") != null;
         if (!participatesInGlobalTXS) {
            WLDataSourceImpl delegate = new WLDataSourceImpl(poolName, appName, moduleName, compName, rmi_settings, jndiNames, jdbcCtx, scope, useDatabaseCredentials, classLoader, useBI_UserName, partitionName, crossPartitionEnabled);
            Iterator var38 = this.priorInstances.get(JDBCUtil.getDecoratedName(name, appName, moduleName, compName)).iterator();

            while(var38.hasNext()) {
               RmiDataSource rmids = (RmiDataSource)var38.next();
               rmids.setDelegate(delegate);
            }

            RmiDataSource var39 = ret = new RmiDataSource(delegate);
            return var39;
         } else {
            boolean isXADataSource = DataSourceUtil.isXADataSource(poolBeans[0].getJDBCDriverParams().getDriverName(), classLoader) && poolBeans[0].getJDBCDriverParams().isUseXaDataSourceInterface();
            ConcurrentProperties props = new ConcurrentProperties();
            props.put("connectionPoolID", poolName);
            props.put("jdbcTxDataSource", "true");
            this.getDataSourceProps(props, dsBean, poolBeans[0], appName, moduleName, compName, isXADataSource, isLLRDataSource);
            if (useDatabaseCredentials) {
               props.put("useDatabaseCredentials", "true");
            }

            if (partitionName != null) {
               props.put("PartitionName", partitionName);
            }

            WLDataSourceImpl delegate;
            Iterator var27;
            RmiDataSource rmids;
            RmiDataSource var41;
            if (isXADataSource) {
               delegate = new WLDataSourceImpl(poolName, appName, moduleName, "weblogic.jdbc.jta.DataSource", "jdbc:weblogic:jta:" + poolName, props, true, rmi_settings, jndiNames, jdbcCtx, scope, useDatabaseCredentials, classLoader, useBI_UserName, crossPartitionEnabled);
               var27 = this.priorInstances.get(JDBCUtil.getDecoratedName(name, appName, moduleName, compName)).iterator();

               while(var27.hasNext()) {
                  rmids = (RmiDataSource)var27.next();
                  rmids.setDelegate(delegate);
               }

               var41 = ret = new RmiDataSource(delegate);
               return var41;
            } else {
               delegate = new WLDataSourceImpl(poolName, appName, moduleName, "weblogic.jdbc.jts.Driver", "jdbc:weblogic:jts:" + poolName, props, true, rmi_settings, jndiNames, jdbcCtx, scope, isLLRDataSource, useDatabaseCredentials, classLoader, useBI_UserName, crossPartitionEnabled);
               var27 = this.priorInstances.get(JDBCUtil.getDecoratedName(name, appName, moduleName, compName)).iterator();

               while(var27.hasNext()) {
                  rmids = (RmiDataSource)var27.next();
                  rmids.setDelegate(delegate);
               }

               var41 = ret = new RmiDataSource(delegate);
               return var41;
            }
         }
      } catch (Exception var32) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* DSM:createDS (60)");
         }

         if (isLLRDataSource && (!isDSMemberOfMDS || jdbcHelper.isLLRTablePerDataSource(poolName))) {
            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            tm.registerFailedLoggingResource(var32);
         }

         ResourceException re = new ResourceException(var32.toString());
         re.initCause(var32);
         throw re;
      } finally {
         if (ret != null && ret.getPoolName() == null) {
            ret.setPoolName(poolName);
         }

         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < DSM:createDS (100)");
         }

      }
   }

   private RmiDriverSettings createRmiDriverSettings(boolean rowPrefetchEnabled, int rowPrefetchSize, int streamChunkSize) {
      RmiDriverSettings rmiDriverSettings = new RmiDriverSettings();
      if (!rowPrefetchEnabled) {
         rowPrefetchSize = 0;
      }

      rmiDriverSettings.setRowCacheSize(rowPrefetchSize);
      rmiDriverSettings.setChunkSize(streamChunkSize);
      return rmiDriverSettings;
   }

   private void checkDSConfig(JDBCDataSourceBean dsBean, String poolName, String appName, String moduleName, String compName, JDBCDataSourceBean[] poolBeans, boolean participatesInGlobalTXS, ClassLoader classLoader) throws Exception {
      if (dsBean.getJDBCDataSourceParams().getJNDINames() != null && dsBean.getJDBCDataSourceParams().getJNDINames().length != 0) {
         if ("Application".equals(dsBean.getJDBCDataSourceParams().getScope())) {
            this.validateAppScopedConfig(dsBean, participatesInGlobalTXS);
         }

         this.verifyPoolDeployment(dsBean.getName(), poolName, appName, moduleName, compName, poolBeans);
         this.checkPoolConfig(dsBean, poolBeans, participatesInGlobalTXS, classLoader);
      } else {
         throw new ResourceException("Cannot create data source with null JNDI Name");
      }
   }

   private void verifyPoolDeployment(String dsName, String poolName, String appName, String moduleName, String compName, JDBCDataSourceBean[] poolBeans) throws Exception {
      if (poolBeans == null || !ConnectionPoolManager.poolExists(poolName, appName, moduleName, compName)) {
         throw new ResourceException("DataSource(" + dsName + ") can't be created with non-existent Pool (connection or multi) (" + poolName + ")");
      }
   }

   private void validateAppScopedConfig(JDBCDataSourceBean dsBean, boolean participatesInGlobalTXS) throws Exception {
      if (participatesInGlobalTXS && dsBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals("LoggingLastResource")) {
         throw new ResourceException("Cannot specify global transaction protocol LoggingLastResource for application-scoped data sources");
      } else if (JDBCUtil.usesSharedPool(dsBean)) {
         throw new ResourceException("Shared pools are not supported with application-scoped data sources");
      }
   }

   private void checkPoolConfig(JDBCDataSourceBean dsBean, JDBCDataSourceBean[] poolBeans, boolean participatesInGlobalTXS, ClassLoader classLoader) throws Exception {
      String dsName = dsBean.getName();
      if (participatesInGlobalTXS) {
         this.checkConnPoolsAttributes(poolBeans, dsBean, classLoader);
      } else {
         for(int lcv = 0; lcv < poolBeans.length; ++lcv) {
            if (DataSourceUtil.isXADataSource(poolBeans[lcv].getJDBCDriverParams().getDriverName(), classLoader) && poolBeans[lcv].getJDBCDriverParams().isUseXaDataSourceInterface()) {
               throw new ResourceException("DataSource '" + dsName + "' can not be created. It is non-transactional and so can not use XA-aware pool '" + poolBeans[lcv].getName() + "'");
            }
         }
      }

   }

   private void checkConnPoolsAttributes(JDBCDataSourceBean[] poolBeans, JDBCDataSourceBean dsBean, ClassLoader classLoader) throws ResourceException {
      String dsName = dsBean.getName();
      JDBCDataSourceBean firstPool = poolBeans[0];
      String firstPoolTxProt = firstPool.getJDBCDataSourceParams().getGlobalTransactionsProtocol();

      for(int index = 1; index < poolBeans.length; ++index) {
         JDBCDataSourceBean currPool = poolBeans[index];
         if (DataSourceUtil.isXADataSource(currPool.getJDBCDriverParams().getDriverName(), classLoader) != DataSourceUtil.isXADataSource(firstPool.getJDBCDriverParams().getDriverName(), classLoader) || currPool.getJDBCDriverParams().isUseXaDataSourceInterface() != firstPool.getJDBCDriverParams().isUseXaDataSourceInterface() || currPool.getJDBCXAParams().isKeepXaConnTillTxComplete() != firstPool.getJDBCXAParams().isKeepXaConnTillTxComplete() || currPool.getJDBCDataSourceParams().isKeepConnAfterLocalTx() != firstPool.getJDBCDataSourceParams().isKeepConnAfterLocalTx() || currPool.getJDBCDataSourceParams().isKeepConnAfterGlobalTx() != firstPool.getJDBCDataSourceParams().isKeepConnAfterGlobalTx() || currPool.getJDBCXAParams().isNeedTxCtxOnClose() != firstPool.getJDBCXAParams().isNeedTxCtxOnClose() || currPool.getJDBCXAParams().isNewXaConnForCommit() != firstPool.getJDBCXAParams().isNewXaConnForCommit() || currPool.getJDBCXAParams().isRollbackLocalTxUponConnClose() != firstPool.getJDBCXAParams().isRollbackLocalTxUponConnClose() || !currPool.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals(firstPoolTxProt) || currPool.getJDBCXAParams().isRecoverOnlyOnce() != firstPool.getJDBCXAParams().isRecoverOnlyOnce() || currPool.getJDBCXAParams().getXaRetryDurationSeconds() != firstPool.getJDBCXAParams().getXaRetryDurationSeconds() || currPool.getJDBCXAParams().getXaRetryIntervalSeconds() != firstPool.getJDBCXAParams().getXaRetryIntervalSeconds() || currPool.getJDBCDataSourceParams().isKeepConnAfterLocalTx() != firstPool.getJDBCDataSourceParams().isKeepConnAfterLocalTx() || currPool.getJDBCXAParams().isKeepLogicalConnOpenOnRelease() != firstPool.getJDBCXAParams().isKeepLogicalConnOpenOnRelease()) {
            throw new ResourceException("Cannot create Multi Data Source '" + dsName + "'. Connection Pools '" + firstPool.getName() + "' and '" + currPool.getName() + "' underneath this multi data source are not configured correctly, their XA and tx protocol related attributes need to be identical.");
         }
      }

      if (!this.isLegacyDataSource(dsBean)) {
         dsBean.getJDBCDataSourceParams().setGlobalTransactionsProtocol(firstPoolTxProt);
      }

   }

   private void getDataSourceProps(ConcurrentProperties props, JDBCDataSourceBean dsBean, JDBCDataSourceBean poolBean, String appName, String moduleName, String compName, boolean isXADriver, boolean isLLRDataSource) {
      String name = dsBean.getName();
      if (name.startsWith("java:global")) {
         props.put("connectionPoolScope", "Global");
      } else if (name.startsWith("java:app")) {
         props.put("connectionPoolScope", "Application");
         props.put("applicationName", appName);
      } else if (name.startsWith("java:module")) {
         props.put("connectionPoolScope", "Application");
         props.put("applicationName", appName);
         props.put("moduleName", moduleName);
      } else if (name.startsWith("java:comp")) {
         props.put("connectionPoolScope", "Application");
         props.put("applicationName", appName);
         props.put("moduleName", moduleName);
         props.put("compName", compName);
      }

      if ("Application".equals(dsBean.getJDBCDataSourceParams().getScope())) {
         props.put("connectionPoolScope", "Application");
         props.put("applicationName", appName);
         props.put("moduleName", moduleName);
      }

      if (isXADriver) {
         if (poolBean.getJDBCConnectionPoolParams().getTestTableName() != null) {
            props.put("testTableName", "" + poolBean.getJDBCConnectionPoolParams().getTestTableName());
         }

         props.put("jdbcxaDebugLevel", "" + poolBean.getJDBCConnectionPoolParams().getJDBCXADebugLevel());
         if ("true".equalsIgnoreCase(System.getProperty("weblogic.xa.allowReleaseXAConnBeforeTxComplete"))) {
            props.put("keepXAConnTillTxComplete", Boolean.toString(poolBean.getJDBCXAParams().isKeepXaConnTillTxComplete()));
         } else {
            props.put("keepXAConnTillTxComplete", "true");
         }

         props.put("keepConnAfterLocalTx", "" + poolBean.getJDBCDataSourceParams().isKeepConnAfterLocalTx());
         props.put("keepConnAfterGlobalTx", "" + poolBean.getJDBCDataSourceParams().isKeepConnAfterGlobalTx());
         props.put("needTxCtxOnClose", "" + poolBean.getJDBCXAParams().isNeedTxCtxOnClose());
         props.put("newXAConnForCommit", "" + poolBean.getJDBCXAParams().isNewXaConnForCommit());
         props.put("keepLogicalConnOpenOnRelease", "" + poolBean.getJDBCXAParams().isKeepLogicalConnOpenOnRelease());
         props.put("enableResourceHealthMonitoring", "" + poolBean.getJDBCXAParams().isResourceHealthMonitoring());
         props.put("rollbackLocalTxUponConnClose", "" + poolBean.getJDBCXAParams().isRollbackLocalTxUponConnClose());
         props.put("callRecoverOnlyOnce", "" + poolBean.getJDBCXAParams().isRecoverOnlyOnce());
         props.put("callXASetTransactionTimeout", "" + poolBean.getJDBCXAParams().isXaSetTransactionTimeout());
         props.put("xaTransactionTimeout", "" + poolBean.getJDBCXAParams().getXaTransactionTimeout());
         props.put("xaRetryDurationSeconds", "" + poolBean.getJDBCXAParams().getXaRetryDurationSeconds());
         props.put("xaRetryIntervalSeconds", "" + poolBean.getJDBCXAParams().getXaRetryIntervalSeconds());
      } else {
         boolean emulate2PC = dsBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals("EmulateTwoPhaseCommit");
         props.put("EmulateTwoPhaseCommit", emulate2PC ? "true" : "false");
         props.put("LoggingLastResource", isLLRDataSource ? "true" : "false");
         props.put("dataSourceName", "" + dsBean.getName());
      }

      JDBCPropertyBean commitFirstProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.firstResourceCommit");
      if (commitFirstProp != null && "true".equalsIgnoreCase(commitFirstProp.getValue())) {
         props.put("FirstResourceCommit", "true");
      }

      JDBCPropertyBean commitOutcomeEnabled = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.commitOutcomeEnabled");
      if (commitOutcomeEnabled != null && "true".equalsIgnoreCase(commitOutcomeEnabled.getValue())) {
         props.put("weblogic.jdbc.commitOutcomeEnabled", "true");
      }

      JDBCPropertyBean commitOutcomeRetrySeconds = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.commitOutcomeRetryMaxSeconds");
      if (commitOutcomeRetrySeconds != null) {
         try {
            props.put("weblogic.jdbc.commitOutcomeRetryMaxSeconds", Integer.parseInt(commitOutcomeRetrySeconds.getValue()));
         } catch (NumberFormatException var14) {
            JdbcDebug.JDBCCONN.debug("Non-integer value provided for weblogic.jdbc.commitOutcomeRetryMaxSeconds driver property. Value:" + commitOutcomeEnabled.getValue(), var14);
         }
      }

      JDBCPropertyBean localResourceAssignmentEnabledProp = dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.localResourceAssignmentEnabled");
      if (localResourceAssignmentEnabledProp != null && "false".equalsIgnoreCase(localResourceAssignmentEnabledProp.getValue())) {
         props.put("weblogic.jdbc.localResourceAssignmentEnabled", "false");
      }

   }

   private void addDataSource(String dsName, String appName, String moduleName, String compName, RmiDataSource ds) throws ResourceException {
      dsName = JDBCUtil.getDecoratedName(dsName, appName, moduleName, compName);
      synchronized(this.dsList) {
         this.dsList.put(dsName, ds);
      }

      synchronized(this.dscpList) {
         this.dscpList.put(ds.getPoolName(), ds);
      }
   }

   private Object removeDataSource(String dsName, String appName, String moduleName, String compName) throws ResourceException {
      dsName = JDBCUtil.getDecoratedName(dsName, appName, moduleName, compName);
      RmiDataSource ret = null;
      synchronized(this.dsList) {
         ret = (RmiDataSource)this.dsList.remove(dsName);
      }

      if (ret != null) {
         synchronized(this.dscpList) {
            this.dscpList.remove(ret.getPoolName());
         }

         this.priorInstances.add(dsName, ret);
      }

      return ret;
   }

   private boolean dataSourceExists(String dsName, String appName, String moduleName, String compName) throws ResourceException {
      return this.dsList.containsKey(JDBCUtil.getDecoratedName(dsName, appName, moduleName, compName));
   }

   private Collection getDataSources() {
      return this.dsList.values();
   }

   public RmiDataSource getDataSource(String dsName) throws ResourceException {
      return this.getDataSource(dsName, (String)null, (String)null, (String)null);
   }

   public RmiDataSource getDataSource(String dsName, String appName, String moduleName, String compName) throws ResourceException {
      dsName = JDBCUtil.getDecoratedName(dsName, appName, moduleName, compName);
      return (RmiDataSource)this.dsList.get(dsName);
   }

   public static String getJNDINamesAsString(String[] names) {
      StringBuffer sbuf = new StringBuffer();

      for(int lcv = 0; lcv < names.length; ++lcv) {
         sbuf.append(names[lcv]);
         sbuf.append(",");
      }

      if (names.length > 0) {
         sbuf.deleteCharAt(sbuf.lastIndexOf(","));
      }

      return sbuf.toString();
   }

   public String getJNDINameFromPoolName(String poolName) {
      String ret = null;

      try {
         RmiDataSource ds = (RmiDataSource)this.dscpList.get(poolName);
         if (ds != null) {
            ret = ds.getJNDINames()[0];
         }
      } catch (Exception var4) {
      }

      return ret;
   }

   private boolean isLegacyDataSource(JDBCDataSourceBean dsBean) {
      int legacyType = 0;
      String val = JDBCUtil.getInternalProperty(dsBean, "LegacyType");
      if (val != null) {
         legacyType = Integer.parseInt(val);
      }

      return legacyType != 0;
   }

   public static void setDataSourceService(DataSourceService service) {
      dataSourceService = service;
   }

   public DataSourceService getDataSourceService() {
      return dataSourceService;
   }

   public void addRegistryListener(RegistryListener listener) {
      synchronized(this.dsList) {
         this.dsList.addListener(listener);
      }
   }

   public void removeRegistryListener(RegistryListener listener) {
      synchronized(this.dsList) {
         this.dsList.removeListener(listener);
      }
   }

   class PriorInstances {
      Map instances = new HashMap();

      synchronized void add(String name, RmiDataSource rmids) {
         List list = (List)this.instances.get(name);
         if (list == null) {
            list = new ArrayList();
            this.instances.put(name, list);
         }

         WeakReference ref = new WeakReference(rmids);
         ((List)list).add(ref);
      }

      synchronized List get(String name) {
         List list = new ArrayList();
         List refList = (List)this.instances.get(name);
         if (refList != null) {
            Iterator var4 = refList.iterator();

            while(var4.hasNext()) {
               WeakReference ref = (WeakReference)var4.next();
               RmiDataSource rmids = (RmiDataSource)ref.get();
               if (rmids != null) {
                  list.add(rmids);
               }
            }
         }

         return list;
      }
   }
}
