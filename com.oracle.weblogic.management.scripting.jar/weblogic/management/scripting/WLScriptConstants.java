package weblogic.management.scripting;

import java.util.List;
import java.util.Locale;
import java.util.Stack;
import javax.management.Descriptor;
import javax.management.MBeanServerConnection;
import javax.naming.NamingException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.MBeanTypeService;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.scripting.core.NodeManagerCoreService;

public class WLScriptConstants extends WLSTScriptVariables {
   public WLScriptConstants() {
      this.wlInstanceObjName = null;
      this.wlInstanceObjName_name = null;
      this.prompt = "";
      this.connected = "false";
      this.domainName = "";
      this.domainType = "RuntimeConfigServerDomain";
      this.username_bytes = (new String("")).getBytes();
      this.password_bytes = (new String("")).getBytes();
      this.idd_bytes = null;
      this.url = "";
      this.atDomainLevel = false;
      this.inMBeanType = false;
      this.inMBeanTypes = false;
      this.atBeanLevel = true;
      this.debug = false;
      this.isAdminServer = true;
      this.serverName = "";
      this.wlInstanceObjNames = null;
      this.wlInstanceObjNames_names = null;
      this.prompts = new Stack();
      this.beans = new Stack();
      this.stackTrace = null;
      this.errorInfo = null;
      this.version = "";
      this.browseHandler = null;
      this.infoHandler = null;
      this.editHandler = null;
      this.lifeCycleHandler = null;
      this.exceptionHandler = null;
      this.jsr88Handler = null;
      this.clusterHandler = null;
      this.domainRuntimeHandler = null;
      this.wlstHelper = null;
      this.findUtil = null;
      this.watchUtil = null;
      this.editService = null;
      this.nmService = null;
      this.interp = null;
      this.shutdownSuccessful = false;
      this.commandType = "";
      this.errorMsg = null;
      this.lastPlaceInRuntime = "";
      this.lastPlaceInConfig = "";
      this.lastPlaceInAdminConfig = "";
      this.iContext = null;
      this.skipSingletons = this.getBoolean(System.getProperty("wlst.skipSingletonCd"));
   }

   static List getLoggersList() {
      return loggersList;
   }

   public void setCmo(Object bean) {
      this.wlcmo = bean;
   }

   public byte[] getUsername_bytes() {
      return this.username_bytes;
   }

   public byte[] getPassword_bytes() {
      return this.password_bytes;
   }

   public Object getCmo() {
      return this.wlcmo;
   }

   public ConfigurationManagerMBean getConfigManager() {
      return this.edit == null ? null : this.edit.configurationManager;
   }

   public PortablePartitionManagerMBean getPortablePartitionManager() {
      return this.portablePartitionManager;
   }

   public ActivationTaskMBean getActivationTask() {
      return this.activationTask;
   }

   public MBeanServerConnection getMBeanServer() {
      return this.mbs;
   }

   public String getVersion() {
      return this.version;
   }

   public boolean isConnected() {
      return this.connected.equalsIgnoreCase("true");
   }

   public String getServerName() {
      return this.serverName;
   }

   public boolean isShutdownSuccessful() {
      return this.shutdownSuccessful;
   }

   public EditService getEditService() {
      return this.editService;
   }

   public NodeManagerCoreService getNodeManagerService() {
      return this.nmService;
   }

   public String getScriptMode() {
      return this.getWLSTInterpreter().getScriptMode() ? "true" : "false";
   }

   boolean booleanValue(Descriptor descr, String descriptorName) {
      String valueAsString = (String)descr.getFieldValue(descriptorName);
      return valueAsString != null && valueAsString.toLowerCase(Locale.US).startsWith("t");
   }

   void initAll() {
      this.wlcmo = null;
      this.wlInstanceObjName = null;
      this.wlInstanceObjName_name = null;
      this.mbs = null;
      this.prompt = "";
      this.connected = "false";
      this.domainName = "";
      this.domainType = "";
      this.username_bytes = (new String("")).getBytes();
      this.password_bytes = (new String("")).getBytes();
      this.idd_bytes = null;
      this.url = "";
      this.atDomainLevel = false;
      this.inMBeanType = false;
      this.inMBeanTypes = false;
      this.atBeanLevel = true;
      this.isAdminServer = true;
      this.serverName = "";
      this.wlInstanceObjNames = null;
      this.wlInstanceObjNames_names = null;
      this.prompts = new Stack();
      this.beans = new Stack();
      this.stackTrace = null;
      this.errorInfo = null;
      this.version = "";
      this.interp = null;
      this.shutdownSuccessful = false;
      this.commandType = "";
      this.errorMsg = null;
      this.lastPlaceInConfig = "";
      this.lastPlaceInRuntime = "";
      this.lastPlaceInConfigRuntime = "";
      this.lastPlaceInRuntimeRuntime = "";
      this.lastPlaceInConfigDomainRuntime = "";
      this.lastPlaceInRuntimeDomainRuntime = "";
      this.lastPlaceInJNDI = "";
      this.lastPlaceInCustom = "";
      if (this.edit != null) {
         this.edit.lastPlaceInEdit = "";
      }

      this.lastPlaceInJSR77 = "";
      this.skipSingletons = false;

      try {
         if (Thread.currentThread().getName().equals("main") && this.iContext != null) {
            this.iContext.close();
         }
      } catch (NamingException var2) {
      }

      this.runtimeMSC = null;
      this.domainRTMSC = null;
      this.edit = null;
      this.jsr77MSC = null;
      this.isEditSessionInProgress = false;
      this.isEditSessionExclusive = false;
      this.isRestartRequired = false;
   }

   public String isAdminServer() {
      Boolean bool = new Boolean(this.isAdminServer);
      return bool.toString();
   }

   DomainRuntimeMBean getDomainRuntimeDomainRuntimeMBean() {
      return this.runtimeDomainRuntimeDRMBean;
   }

   DomainMBean getDomainRuntimeDomainMBean() {
      return this.configDomainRuntimeDRMBean;
   }

   DomainMBean getServerRuntimeDomainMBean() {
      return this.runtimeDomainMBean;
   }

   ServerRuntimeMBean getServerRuntimeServerRuntimeMBean() {
      return this.runtimeServerRuntimeMBean;
   }

   DomainRuntimeMBean getCompatabilityDomainRuntimeMBean() {
      return this.compatDomainRuntimeMBean;
   }

   DomainMBean getCompatabilityDomainMBean() {
      return this.compatDomainMBean;
   }

   ServerRuntimeMBean getCompatabilityServerRuntimeMBean() {
      return this.compatServerRuntimeMBean;
   }

   DomainMBean getEditServerDomainMBean() {
      return this.edit == null ? null : this.edit.domainMBean;
   }

   public EditServiceMBean getEditServiceMBean() {
      return this.edit == null ? null : this.edit.serviceMBean;
   }

   public RuntimeServiceMBean getRuntimeServiceMBean() {
      return this.runtimeServiceMBean;
   }

   public DomainRuntimeServiceMBean getDomainRuntimeServiceMBean() {
      return this.domainRuntimeServiceMBean;
   }

   public MBeanTypeService getMBeanTypeService() {
      return this.mbeanTypeService;
   }

   String calculateThreadDumpFileName() {
      return "Thread_Dump";
   }

   public boolean isEditSessionInProgress() {
      return this.isEditSessionInProgress;
   }

   public String getEditSessionNameForPrompt() {
      return this.edit != null && !this.edit.isGlobalSession() ? '(' + this.edit.name + ')' : "";
   }

   public boolean inMBeanType() {
      return this.inMBeanType;
   }

   public boolean inMBeanTypes() {
      return this.inMBeanTypes;
   }

   public boolean atBeanLevel() {
      return this.atBeanLevel;
   }

   public String getErrorMessage() {
      return this.theErrorMessage;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   static {
      loggersList.add("javax.management.remote.misc");
      loggersList.add("javax.management.remote.rmi");
   }
}
