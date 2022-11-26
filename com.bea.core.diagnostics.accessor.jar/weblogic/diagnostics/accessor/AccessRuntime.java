package weblogic.diagnostics.accessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.accessor.config.DefaultEditableAccessorConfiguration;
import weblogic.diagnostics.accessor.config.DefaultLogAccessorConfiguration;
import weblogic.diagnostics.accessor.parser.LogRecordParser;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class AccessRuntime extends RuntimeMBeanDelegate implements AccessRuntimeMBean, AccessorConstants {
   private static final DebugLogger ACCESSOR_DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private static volatile AccessRuntime singleton;
   private Map dataAccessors = new HashMap();
   private AccessorMBeanFactory accessorHelper;
   private AccessorConfigurationProvider confProvider;
   private AccessorSecurityProvider securityProvider;

   public static void initialize(AccessorEnvironment env, RuntimeMBean parent) throws ManagementException {
      if (singleton != null) {
         if (ACCESSOR_DEBUG.isDebugEnabled()) {
            ACCESSOR_DEBUG.debug("AccessRuntime already initialized");
         }

      } else {
         try {
            AccessorConfigurationProvider confProvider = env.getConfigurationProvider();
            AccessorMBeanFactory accHelper = env.getAccessorMBeanFactory();
            AccessorSecurityProvider secProvider = env.getSecurityProvider();
            if (accHelper == null) {
               accHelper = createDefaultAccessorHelper(confProvider, parent);
            }

            DataAccessRuntime.initialize(accHelper, confProvider, secProvider);
            singleton = (AccessRuntime)accHelper.createDiagnosticAccessRuntime(confProvider, secProvider, parent);
         } catch (Exception var5) {
            throw new ManagementException(var5.getMessage(), var5);
         }
      }
   }

   public static boolean isInitialized() {
      return singleton != null;
   }

   private static AccessorMBeanFactory createDefaultAccessorHelper(AccessorConfigurationProvider confProvider, RuntimeMBean parent) throws ManagementException {
      return new DefaultAccessorMBeanFactory(confProvider, parent);
   }

   public static synchronized AccessRuntime getAccessorInstance() throws ManagementException {
      if (singleton == null) {
         throw new ManagementException("AccessRuntime not initialized");
      } else {
         return singleton;
      }
   }

   public AccessorConfigurationProvider getAccessorConfigurationProvider() {
      return this.confProvider;
   }

   public AccessorMBeanFactory getAccessorMBeanFactory() {
      return this.accessorHelper;
   }

   public synchronized LogAccessorConfiguration createLogAccessor(String name, String logFile, String rotationDir, LogRecordParser recordParser, boolean isModifiableConfig) throws ManagementException {
      LogAccessorConfiguration logAccessConfig = new DefaultLogAccessorConfiguration(name, recordParser, logFile, rotationDir, isModifiableConfig);
      this.createAccessor(logAccessConfig);
      return logAccessConfig;
   }

   public synchronized EditableAccessorConfiguration createEditableAccessor(String name, ColumnInfo[] columnInfos, boolean participantInSizeBasedDataRetirement) throws ManagementException {
      EditableAccessorConfiguration editableAccessConfig = new DefaultEditableAccessorConfiguration(name, columnInfos, participantInSizeBasedDataRetirement);
      this.createAccessor(editableAccessConfig);
      return editableAccessConfig;
   }

   private synchronized void createAccessor(AccessorConfiguration accConf) throws ManagementException {
      this.ensureUserAuthorized(1);
      String name = accConf.getName();
      String[] accNames = this.confProvider.getAccessorNames();
      boolean found = false;
      int size = accNames != null ? accNames.length : 0;

      for(int i = 0; !found && i < size; ++i) {
         if (name.equals(accNames[i])) {
            found = true;
         }
      }

      if (!found) {
         this.confProvider.addAccessor(accConf);
         this.lookupDataAccessRuntime(name);
      }

   }

   public synchronized void removeAccessor(String logicalName) throws ManagementException {
      this.ensureUserAuthorized(1);
      this.confProvider.removeAccessor(logicalName);
      DataAccessRuntimeMBean dar = (DataAccessRuntimeMBean)this.dataAccessors.remove(logicalName);
      if (dar != null && dar instanceof RuntimeMBeanDelegate) {
         RuntimeMBeanDelegate rmb = (RuntimeMBeanDelegate)dar;
         rmb.unregister();
      }

   }

   AccessRuntime(AccessorMBeanFactory accessorFactory, AccessorConfigurationProvider confProvider, AccessorSecurityProvider securityProvider, RuntimeMBean parent) throws ManagementException {
      super("Accessor", parent);
      this.accessorHelper = accessorFactory;
      this.confProvider = confProvider;
      this.securityProvider = securityProvider;
   }

   private void ensureUserAuthorized(int action) throws ManagementException {
      try {
         if (this.securityProvider != null) {
            this.securityProvider.ensureUserAuthorized(action);
         }

      } catch (Exception var3) {
         throw new ManagementException(var3.getMessage(), var3);
      }
   }

   public DataAccessRuntimeMBean lookupDataAccessRuntime(String logicalName) throws ManagementException {
      return this.lookupDataAccessRuntime(logicalName, (ColumnInfo[])null);
   }

   public DataAccessRuntimeMBean lookupDataAccessRuntime(String logicalName, ColumnInfo[] columns) throws ManagementException {
      this.ensureUserAuthorized(2);
      DataAccessRuntimeMBean ddar = null;
      synchronized(this.dataAccessors) {
         if (this.dataAccessors.containsKey(logicalName)) {
            ddar = (DataAccessRuntimeMBean)this.dataAccessors.get(logicalName);

            DataAccessRuntimeMBean var10000;
            try {
               if (!this.isCacheStale(logicalName)) {
                  if (ACCESSOR_DEBUG.isDebugEnabled()) {
                     ACCESSOR_DEBUG.debug("Returning cached instance of runtime mbean " + ddar);
                  }

                  var10000 = ddar;
                  return var10000;
               }

               ACCESSOR_DEBUG.debug("The cache is stale for logical name " + logicalName);
               this.refreshCache(logicalName, ddar);
               var10000 = ddar;
            } catch (UnknownLogTypeException var8) {
               Loggable l = DiagnosticsLogger.logErrorCreatingDiagnosticDataRuntimeLoggable(logicalName, var8);
               l.log();
               throw new ManagementException(l.getMessageBody());
            }

            return var10000;
         } else {
            this.ensureUserAuthorized(1);
            ddar = this.accessorHelper.createDiagnosticDataAccessRuntime(logicalName, columns, this);
            this.dataAccessors.put(logicalName, ddar);
            return ddar;
         }
      }
   }

   private void refreshCache(String logicalName, DataAccessRuntimeMBean ddar) throws ManagementException {
      try {
         ddar.closeArchive();
         DataAccessRuntime.DiagnosticDataAccessServiceStruct st = DataAccessRuntime.createDiagnosticDataAccessService(logicalName);
         ddar.setDiagnosticDataAccessService(st.getDiagnosticDataAccessService());
         ddar.setDataArchiveParameters(st.getCreateParams());
      } catch (UnknownLogTypeException var4) {
         throw new ManagementException(var4);
      } catch (DataAccessServiceCreateException var5) {
         throw new ManagementException(var5);
      }
   }

   public static boolean compareMaps(Map a, Map b) {
      if (a.size() != b.size()) {
         return false;
      } else {
         Iterator var2 = a.entrySet().iterator();

         Object currentValue;
         Object oldValue;
         label34:
         do {
            do {
               if (!var2.hasNext()) {
                  return true;
               }

               Object e = var2.next();
               Map.Entry entry = (Map.Entry)e;
               Object key = entry.getKey();
               currentValue = entry.getValue();
               oldValue = b.get(key);
               if (currentValue == null || oldValue == null) {
                  continue label34;
               }
            } while(currentValue.equals(oldValue));

            return false;
         } while(currentValue == null && oldValue == null);

         return false;
      }
   }

   private boolean isCacheStale(String logicalName) throws UnknownLogTypeException {
      DataAccessRuntimeMBean ddar = (DataAccessRuntimeMBean)this.dataAccessors.get(logicalName);
      Map oldParams = ddar.getDataArchiveParameters();
      AccessorConfiguration accConf = this.confProvider.getAccessorConfiguration(logicalName);
      if (!accConf.isModifiableConfiguration()) {
         return false;
      } else {
         Map currentParams = getAccessorConfiguration(logicalName, this.confProvider);
         if (currentParams != null) {
            return !compareMaps(currentParams, oldParams);
         } else {
            return false;
         }
      }
   }

   static Map getAccessorConfiguration(String logicalName, AccessorConfigurationProvider confProvider) throws UnknownLogTypeException {
      AccessorConfiguration accConf = confProvider.getAccessorConfiguration(logicalName);
      Map currentParams = accConf.getAccessorParameters();
      Map currentParams = currentParams != null ? new HashMap(currentParams) : new HashMap();
      LogAccessorConfiguration logConf = null;
      if (accConf instanceof LogAccessorConfiguration) {
         logConf = (LogAccessorConfiguration)accConf;
      }

      if (logConf != null) {
         String val = logConf.getLogFilePath();
         if (val != null) {
            currentParams.put("logFilePath", val);
         }

         val = logConf.getLogFileRotationDirectory();
         if (val != null) {
            currentParams.put("logRotationDir", val);
         }
      }

      currentParams.put("storeDir", confProvider.getStoreDirectory());
      return currentParams;
   }

   public String[] getAvailableDiagnosticDataAccessorNames() throws ManagementException {
      this.ensureUserAuthorized(2);
      return this.accessorHelper.getAvailableDiagnosticDataAccessorNames();
   }

   public DataAccessRuntimeMBean[] getDataAccessRuntimes() throws ManagementException {
      ArrayList accessors = new ArrayList();
      String[] logicalNames = this.getAvailableDiagnosticDataAccessorNames();

      for(int i = 0; i < logicalNames.length; ++i) {
         DataAccessRuntimeMBean accessor = this.lookupDataAccessRuntime(logicalNames[i]);
         accessors.add(accessor);
      }

      DataAccessRuntimeMBean[] result = new DataAccessRuntimeMBean[accessors.size()];
      accessors.toArray(result);
      return result;
   }
}
