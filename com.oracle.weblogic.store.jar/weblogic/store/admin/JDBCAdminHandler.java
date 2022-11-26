package weblogic.store.admin;

import java.util.HashMap;
import javax.sql.DataSource;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.store.PersistentStoreException;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreLogger;
import weblogic.store.admin.util.PersistentStoreUtils;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.jdbc.BasicDataSource;
import weblogic.store.io.jdbc.JDBCStoreIO;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.store.xa.internal.PersistentStoreXAImpl;

public class JDBCAdminHandler extends AdminHandler {
   public void activate(GenericManagedDeployment deployment) throws DeploymentException {
      DeploymentMBean mBean = deployment.getMBean();

      try {
         this.store = makeStore(this.name, this.overrideResourceName, this.instanceName, (JDBCStoreMBean)mBean, (ClearOrEncryptedService)null, new RuntimeHandlerImpl());
      } catch (PersistentStoreException var4) {
         StoreLogger.logStoreDeploymentFailed(this.name, var4.toString(), var4);
         throw new DeploymentException(var4);
      }

      this.prepareConfig((JDBCStoreMBean)mBean);
      super.activate(deployment);
   }

   public static DataSource createDataSource(JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService) throws PersistentStoreException {
      return createDataSource(jBean, encryptionService, true);
   }

   public static DataSource createDataSource(JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService, boolean useJNDI) throws PersistentStoreException {
      return PersistentStoreUtils.createDataSource(jBean, encryptionService, useJNDI);
   }

   public static PersistentStoreXA makeStore(String name, String overrideResourceName, String instanceName, JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      return makeStore(name, overrideResourceName, instanceName, jBean, encryptionService, runtimeHandler, true, true);
   }

   public static PersistentStoreXA makeStore(String name, String overrideResourceName, String instanceName, JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService, RuntimeHandler runtimeHandler, boolean isUseJNDI, boolean flushFailureFatal) throws PersistentStoreException {
      PersistentStoreIO io = makeStoreIO(name, instanceName, jBean, encryptionService, isUseJNDI, flushFailureFatal);
      return new PersistentStoreXAImpl(name, io, overrideResourceName, runtimeHandler);
   }

   private static JDBCStoreIO makeStoreIO(String name, JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService) throws PersistentStoreException {
      return makeStoreIO(name, (String)null, jBean, encryptionService, true, true);
   }

   private static JDBCStoreIO makeStoreIO(String name, String instanceName, JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService, boolean isUseJNDI, boolean flushFailureFatal) throws PersistentStoreException {
      String prefix = null;
      int maxDelBatch = 20;
      int maxInsBatch = 20;
      int maxDelPerStmt = 20;
      int retryPeriod = getRetryPeriod(jBean);
      int retryInterval = getRetryInterval(jBean);
      retryInterval = checkForIntervalPeriodInversion(retryPeriod, retryInterval);
      if (jBean != null) {
         maxDelBatch = jBean.getDeletesPerBatchMaximum();
         maxInsBatch = jBean.getInsertsPerBatchMaximum();
         maxDelPerStmt = jBean.getDeletesPerStatementMaximum();
      }

      DataSource dataSource = PersistentStoreUtils.createDataSource(jBean, encryptionService, isUseJNDI);
      if (dataSource instanceof BasicDataSource && ((BasicDataSource)dataSource).isXADataSource()) {
         throw new PersistentStoreException("XA data sources are not supported for the JDBC store");
      } else {
         return new JDBCStoreIO(name, dataSource, PersistentStoreUtils.computeFullyDecoratedTableName(jBean, instanceName), jBean.getCreateTableDDLFile(), maxDelBatch, maxInsBatch, maxDelPerStmt, retryPeriod, retryInterval, flushFailureFatal);
      }
   }

   public static void deleteStore(String name, JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService) throws PersistentStoreException {
      makeStoreIO(name, jBean, encryptionService).destroy();
   }

   private static String parseJNDIName(String[] names) {
      assert names != null && names.length > 0;

      assert !isEmptyString(names[0]);

      return names[0];
   }

   private void prepareConfig(JDBCStoreMBean jBean) {
      if (this.config == null) {
         this.config = new HashMap();
      }

      this.config.put("StoreConfigName", this.getConfiguredName());
      this.config.put("ThreeStepThreshold", jBean.getThreeStepThreshold());
      this.config.put("WorkerCount", jBean.getWorkerCount());
      this.config.put("WorkerPreferredBatchSize", jBean.getWorkerPreferredBatchSize());
      this.config.put("OraclePiggybackCommitEnabled", jBean.isOraclePiggybackCommitEnabled());
      this.config.put("ConnectionCachingPolicy", jBean.getConnectionCachingPolicy());
   }

   private static int getRetryPeriod(JDBCStoreMBean jBean) {
      String propString = null;
      String propVal = null;
      String[] retryProps = new String[]{"weblogic.store.jdbc.ReconnectRetryPeriodMillis", "weblogic.store.jdbc.IORetryDelayMillis", "weblogic.jms.store.JMSJDBCIORetryDelay"};
      int[] propScaleFactor = new int[]{1, 1, 1000};
      int retryPeriod = jBean != null ? jBean.getReconnectRetryPeriodMillis() : 1000;

      try {
         for(int i = 0; i < retryProps.length; ++i) {
            propString = retryProps[i];
            propVal = System.getProperty(propString);
            if (propVal != null && propVal.trim().length() > 0) {
               retryPeriod = Integer.parseInt(propVal) * propScaleFactor[i];
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  debugStaticDeprecated(propString, propVal);
               }
               break;
            }
         }
      } catch (NumberFormatException var7) {
         debugBadPropValue(propString, propVal, retryPeriod, var7);
      }

      retryPeriod = constrainToRange(retryPeriod, 200, 300000);
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("Using ").append(retryPeriod).append(" as the retry period.");
         debugStatic(sb.toString());
      }

      return retryPeriod;
   }

   private static int getRetryInterval(JDBCStoreMBean jBean) {
      int retryInterval = jBean != null ? jBean.getReconnectRetryIntervalMillis() : 200;
      String propString = "weblogic.store.jdbc.ReconnectRetryIntervalMillis";
      String propVal = System.getProperty(propString);

      try {
         if (propVal != null && propVal.trim().length() > 0) {
            retryInterval = Integer.parseInt(propVal);
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               debugStaticDeprecated(propString, propVal);
            }

            retryInterval = constrainToRange(retryInterval, 100, 10000);
         }
      } catch (NumberFormatException var5) {
         debugBadPropValue(propString, propVal, retryInterval, var5);
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("Using ").append(retryInterval).append(" as the retry interval.");
         debugStatic(sb.toString());
      }

      return retryInterval;
   }

   private static int checkForIntervalPeriodInversion(int retryPeriod, int retryInterval) {
      if (retryInterval >= retryPeriod) {
         int oldInterval = retryInterval;
         retryInterval = retryPeriod / 2;
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Retry interval ").append(oldInterval);
            sb.append("msec is greater that the retry period ").append(retryPeriod).append("msec");
            sb.append("; using ").append(retryInterval).append("msec as the interval.");
            debugStatic(sb.toString());
         }
      }

      return retryInterval;
   }

   private static void debugStatic(String s) {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug(s);
      }

   }

   private static void debugStaticDeprecated(String propString, String propVal) {
      StringBuilder sb = new StringBuilder();
      sb.append("The value ").append(propVal).append(" was retrieved from the system property ");
      sb.append(propString).append(". ").append(propVal);
      sb.append(" has been deprecated.");
      debugStatic(sb.toString());
   }

   private static void debugBadPropValue(String propString, String propValue, int usedValue, Exception e) {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("Bad value \"").append(propValue).append("\" for ").append(propString);
         sb.append(". Using the value of ").append(usedValue);
         StoreDebug.storeIOPhysical.debug(sb.toString(), e);
      }

   }

   private static int constrainToRange(int value, int min, int max) {
      int oldValue = value;
      value = Math.max(value, min);
      value = Math.min(value, max);
      if (oldValue != value && StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("Retrieved value ").append(oldValue).append(" is out of range. ");
         sb.append("Valid range is ").append(min).append(" to ").append(max).append(". ");
         sb.append("Using ").append(value);
         debugStatic(sb.toString());
      }

      return value;
   }
}
