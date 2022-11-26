package weblogic.diagnostics.accessor;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.accessor.parser.LogRecordParser;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFDataRetirementByAgeMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLSAccessorConfigurationProviderImpl implements AccessorConfigurationProvider, AccessorConstants {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Set deploymentLogs = new HashSet() {
      {
         this.add("ConnectorLog");
         this.add("WebAppLog");
         this.add("JMSMessageLog");
         this.add("JMSSAFMessageLog");
         this.add("HTTPAccessLog/");
      }
   };
   private static final RuntimeAccess runtimeAccess;
   private AccessorMBeanFactory accessorMBeanFactory;
   private Map accessorMap = new HashMap();
   private static final String[] EDITABLE_ACCESSOR_TYPES;
   private static final String[] SIZE_BASED_RETIREMENT_PARTICIPANTS;

   private static WLDFServerDiagnosticMBean getWLDFConfiguration() {
      ServerMBean serverConfig = runtimeAccess.getServer();
      return serverConfig.getServerDiagnosticConfig();
   }

   public WLSAccessorConfigurationProviderImpl(AccessorMBeanFactory accessorMBeanFactory) {
      this.accessorMBeanFactory = accessorMBeanFactory;
   }

   public boolean isDataRetirementTestModeEnabled() {
      return getWLDFConfiguration().isDataRetirementTestModeEnabled();
   }

   public String getStoreDirectory() {
      return AccessorUtils.getDiagnosticStoreDirectory();
   }

   public boolean isDataRetirementEnabled() {
      return getWLDFConfiguration().isDataRetirementEnabled();
   }

   public int getPreferredStoreSizeLimit() {
      return getWLDFConfiguration().getPreferredStoreSizeLimit();
   }

   public int getStoreSizeCheckPeriod() {
      return getWLDFConfiguration().getStoreSizeCheckPeriod();
   }

   public AccessorConfiguration getAccessorConfiguration(String accessorName) throws UnknownLogTypeException {
      Map params = this.getAccessorProperties(accessorName);
      if (params.get("logFilePath") != null) {
         return new LogAccessorConfigurationImpl(accessorName, params);
      } else {
         return (AccessorConfiguration)(isEditableAccessor(accessorName) ? new EditableAccessorConfigurationImpl(accessorName, params) : new AccessorConfigurationImpl(accessorName, params));
      }
   }

   public synchronized String[] getAccessorNames() {
      HashSet set = new HashSet();

      String[] accNames;
      try {
         accNames = this.accessorMBeanFactory.getAvailableDiagnosticDataAccessorNames();
         int count = accNames != null ? accNames.length : 0;

         for(int i = 0; i < count; ++i) {
            set.add(accNames[i]);
         }
      } catch (ManagementException var5) {
         UnexpectedExceptionHandler.handle(var5.getMessage(), var5);
      }

      set.addAll(this.accessorMap.keySet());
      accNames = new String[set.size()];
      accNames = (String[])((String[])set.toArray(accNames));
      return accNames;
   }

   public synchronized void addAccessor(AccessorConfiguration accessor) {
      String name = accessor.getName();
      if (this.accessorMap.get(name) == null) {
         this.accessorMap.put(name, accessor);
      }

   }

   public synchronized void removeAccessor(String name) {
      boolean validLogicalName = false;
      Iterator var3 = this.deploymentLogs.iterator();

      while(var3.hasNext()) {
         String prefix = (String)var3.next();
         if (name.startsWith(prefix)) {
            validLogicalName = true;
            break;
         }
      }

      if (!validLogicalName) {
         throw new IllegalArgumentException(DiagnosticsTextTextFormatter.getInstance().getInvalidAccessorNameForRemoval(name));
      } else {
         this.accessorMap.remove(name);
      }
   }

   private static String getAccessorType(String accessorName) {
      String[] tokens = accessorName.split("/");
      return tokens[0];
   }

   private static boolean isEditableAccessor(String accessorName) {
      String logType = getAccessorType(accessorName);

      for(int i = 0; i < EDITABLE_ACCESSOR_TYPES.length; ++i) {
         if (logType.equals(EDITABLE_ACCESSOR_TYPES[i])) {
            return true;
         }
      }

      return false;
   }

   private Map getAccessorProperties(String accessorName) throws UnknownLogTypeException {
      String[] tokens = accessorName.split("/");
      String logType = tokens[0];
      Map params = null;
      boolean userDefined = false;
      boolean isAdmin = AccessorUtils.isAdminServer();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Admin server = " + isAdmin);
      }

      if (logType.equals("ServerLog")) {
         params = AccessorUtils.getParamsForServerLog();
      } else if (logType.equals("DomainLog") && isAdmin) {
         params = AccessorUtils.getParamsForDomainLog();
      } else if (logType.equals("HarvestedDataArchive")) {
         params = AccessorUtils.getParamsForDiagnosticDataArchive();
      } else if (logType.equals("EventsDataArchive")) {
         params = AccessorUtils.getParamsForDiagnosticDataArchive();
      } else if (logType.equals("DataSourceLog")) {
         params = AccessorUtils.getParamsForDataSourceLog();
      } else if (logType.equals("HTTPAccessLog")) {
         params = AccessorUtils.getParamsForHTTPAccessLog(tokens);
      } else if (logType.equals("WebAppLog")) {
         params = AccessorUtils.getParamsForWebAppLog(tokens);
      } else if (logType.equals("ConnectorLog")) {
         params = AccessorUtils.getParamsForConnectorLog(accessorName);
      } else if (logType.equals("JMSMessageLog")) {
         params = AccessorUtils.getParamsForJMSMessageLog(accessorName);
      } else if (logType.equals("JMSSAFMessageLog")) {
         params = AccessorUtils.getParamsForJMSSAFMessageLog(accessorName);
      } else {
         if (!logType.equals("CUSTOM")) {
            Loggable l = DiagnosticsLogger.logUnknownLogTypeLoggable(logType);
            throw new UnknownLogTypeException(l.getMessageBody());
         }

         userDefined = true;
         params = AccessorUtils.getParamsForGenericDataArchive();
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Config parameters for accessor " + accessorName + ":" + this.getConfigurationParamString(params));
      }

      return params;
   }

   private String getConfigurationParamString(Map params) {
      StringBuffer buf = new StringBuffer();
      Iterator it = params.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         Object val = params.get(key);
         buf.append("\n   " + key + "=" + val);
      }

      return buf.toString();
   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(KERNELID);
      EDITABLE_ACCESSOR_TYPES = new String[]{"CUSTOM", "EventsDataArchive", "HarvestedDataArchive"};
      SIZE_BASED_RETIREMENT_PARTICIPANTS = new String[]{"EventsDataArchive", "HarvestedDataArchive"};
   }

   private static class EditableAccessorConfigurationImpl extends AccessorConfigurationImpl implements EditableAccessorConfiguration {
      EditableAccessorConfigurationImpl(String accessorName, Map params) {
         super(accessorName, params);
      }

      private WLDFDataRetirementByAgeMBean lookupWLDFDataRetirementByAge() {
         WLDFServerDiagnosticMBean wldf = WLSAccessorConfigurationProviderImpl.getWLDFConfiguration();
         String name = this.getName();
         WLDFDataRetirementByAgeMBean[] rmbs = wldf.getWLDFDataRetirementByAges();
         int size = rmbs != null ? rmbs.length : 0;

         for(int i = 0; i < size; ++i) {
            WLDFDataRetirementByAgeMBean mb = rmbs[i];
            if (name.equals(mb.getArchiveName())) {
               return mb;
            }
         }

         return null;
      }

      public int getRetirementAge() {
         WLDFDataRetirementByAgeMBean retirementMBean = this.lookupWLDFDataRetirementByAge();
         return retirementMBean != null ? retirementMBean.getRetirementAge() : 72;
      }

      public int getRetirementPeriod() {
         WLDFDataRetirementByAgeMBean retirementMBean = this.lookupWLDFDataRetirementByAge();
         return retirementMBean != null ? retirementMBean.getRetirementPeriod() : 24;
      }

      public int getRetirementTime() {
         WLDFDataRetirementByAgeMBean retirementMBean = this.lookupWLDFDataRetirementByAge();
         return retirementMBean != null ? retirementMBean.getRetirementTime() : 0;
      }

      public boolean isAgeBasedDataRetirementEnabled() {
         WLDFDataRetirementByAgeMBean retirementMBean = this.lookupWLDFDataRetirementByAge();
         return retirementMBean != null && retirementMBean.isEnabled();
      }

      public boolean isParticipantInSizeBasedDataRetirement() {
         WLDFServerDiagnosticMBean wldfConf = WLSAccessorConfigurationProviderImpl.getWLDFConfiguration();
         String archiveType = wldfConf.getDiagnosticDataArchiveType();
         if ("FileStoreArchive".equals(archiveType)) {
            for(int i = 0; i < WLSAccessorConfigurationProviderImpl.SIZE_BASED_RETIREMENT_PARTICIPANTS.length; ++i) {
               if (WLSAccessorConfigurationProviderImpl.SIZE_BASED_RETIREMENT_PARTICIPANTS[i].equals(this.getName())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static class LogAccessorConfigurationImpl extends AccessorConfigurationImpl implements LogAccessorConfiguration {
      private String logFile;
      private String rotationDir;

      LogAccessorConfigurationImpl(String accessorName, Map params) {
         super(accessorName, params);
         this.logFile = (String)params.get("logFilePath");
         this.rotationDir = (String)params.get("logRotationDir");
      }

      public LogRecordParser getRecordParser() {
         return null;
      }

      public String getLogFilePath() {
         return this.logFile;
      }

      public String getLogFileRotationDirectory() {
         return this.rotationDir;
      }
   }

   private static class AccessorConfigurationImpl implements AccessorConfiguration {
      private String accessorName;
      private Map params;
      private boolean isModifiable;

      AccessorConfigurationImpl(String accessorName, Map params) {
         this.accessorName = accessorName;
         this.params = params;
         String logType = WLSAccessorConfigurationProviderImpl.getAccessorType(accessorName);
         if (logType.equals("WebAppLog")) {
            this.isModifiable = true;
         } else if (logType.equals("ConnectorLog")) {
            this.isModifiable = true;
         } else if (logType.equals("JMSMessageLog") || logType.equals("JMSSAFMessageLog")) {
            this.isModifiable = true;
         }

      }

      public String getName() {
         return this.accessorName;
      }

      public boolean isModifiableConfiguration() {
         return this.isModifiable;
      }

      public ColumnInfo[] getColumns() {
         return null;
      }

      public Map getAccessorParameters() {
         return this.params;
      }
   }
}
