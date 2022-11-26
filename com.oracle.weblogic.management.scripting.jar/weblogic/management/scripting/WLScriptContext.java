package weblogic.management.scripting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.management.AttributeNotFoundException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import org.python.core.ArgParser;
import org.python.core.PyObject;
import org.python.util.InteractiveInterpreter;
import weblogic.version;
import weblogic.management.WebLogicMBean;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.scripting.core.ExceptionCoreHandler;
import weblogic.management.scripting.core.NodeManagerCoreService;
import weblogic.management.scripting.utils.ScriptCommandsHelp;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtil;

public class WLScriptContext extends WLSTTreeUtils {
   private static final WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();

   public WLScriptContext() {
      this.browseHandler = new BrowseHandler(this);
      this.newBrowseHandler = new NewBrowseHandler(this);
      this.infoHandler = new InformationHandler(this);
      this.editHandler = new EditHandler(this);
      this.lifeCycleHandler = new LifeCycleHandler(this);
      this.exceptionHandler = new ExceptionCoreHandler(this);
      this.jsr88Handler = new JSR88DeployHandler(this);
      this.rolloutHandler = new RolloutHandler(this);
      this.userFileHandler = new UserFileHandler(this);
      this.findUtil = new FindUtil(this);
      this.watchUtil = new WatchUtil(this);
      this.editService = new EditService(this);
      this.clusterHandler = new ClusterHandler(this);
      this.domainRuntimeHandler = new DomainRuntimeHandler(this);
      this.scriptCmdHelp = new ScriptCommandsHelp(this);
      this.serverRuntimeHandler = new ServerRuntimeHandler(this);
      this.nmService = new NodeManagerService(this);
      this.wlstHelper = new WLSTHelper(this);
      this.diagnosticsHandler = new DiagnosticsHandler(this);
   }

   public void connect(PyObject[] args, String[] kw) throws ScriptException {
      this.wlstHelper.connect(args, kw);
   }

   public void debug(String val) throws ScriptException {
      this.commandType = "debug";
      if (val == null) {
         this.debug = !this.debug;
      } else {
         this.debug = this.getBoolean(val);
      }

      if (this.debug) {
         this.println(txtFmt.getDebugOn());
      } else {
         this.println(txtFmt.getDebugOff());
      }

   }

   public WebLogicMBean getTarget(String path) throws ScriptException {
      this.commandType = "getTarget";
      return this.editHandler.getTarget(path);
   }

   public WebLogicMBean[] getTargetArray(String type, String values) throws ScriptException {
      this.commandType = "getTargetArray";
      return this.editHandler.getTargetArray(type, values);
   }

   public void config2Py(String configPath, String pyPath, String overWrite, String propertiesFile, String createDeploymentScript, String resourcesOnlyConversion, String debug) throws ScriptException {
      this.commandType = "configToScript";
      this.println(txtFmt.getConfigToScriptDeprecated());
      if (configPath == null) {
         configPath = ".";
      }

      File configFile = new File(configPath);

      try {
         FileInputStream is;
         String ver;
         if (configFile.isDirectory()) {
            System.setProperty("weblogic.RootDirectory", configFile.getAbsolutePath());
            configFile = new File(configFile.getAbsolutePath() + "/config/config.xml");
            is = new FileInputStream(configFile);
            if (this.isNewFormat(is)) {
               if (this.getBoolean(debug)) {
                  this.debug = true;
               } else {
                  this.debug = false;
               }

               this.callc2s(configFile, pyPath, overWrite, propertiesFile, createDeploymentScript, resourcesOnlyConversion);
            } else {
               ver = weblogic.version.getBuildVersion();
               this.println(txtFmt.getConfigToScriptOlfFmt(ver));
            }
         } else {
            is = new FileInputStream(configFile);
            if (this.isNewFormat(is)) {
               this.callc2s(configFile, pyPath, overWrite, propertiesFile, createDeploymentScript, resourcesOnlyConversion);
            } else {
               ver = weblogic.version.getBuildVersion();
               this.println(txtFmt.getConfigToScriptOlfFmt(ver));
            }
         }
      } catch (FileNotFoundException var11) {
         this.throwWLSTException(txtFmt.getFileNotFound(configFile.getAbsolutePath()), var11);
      } catch (Throwable var12) {
         this.throwWLSTException(txtFmt.getConfigToScriptProblem(var12.getMessage()), var12);
      }

   }

   private void callc2s(File configFile, String pyPath, String overWrite, String propertiesFile, String createDeploymentScript, String resourcesOnlyConversion) throws Throwable {
      ConfigToScript c2s = new ConfigToScript(configFile, pyPath, overWrite, propertiesFile, createDeploymentScript, resourcesOnlyConversion, this);
      c2s.convert();
   }

   public void pop(PyObject[] args, String[] kw) throws ScriptException {
      new ArgParser("pop", args, kw, "lll");
      this.browseHandler.pop();
   }

   public void cd(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "cd";
      ArgParser ap = new ArgParser("push", args, kw, "mname");
      String mname = ap.getString(0);
      this.browseHandler.cd(mname);
   }

   public Object findService(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "findService";
      ArgParser ap = new ArgParser(this.commandType, args, kw, "serviceName", "serviceType", "location");
      String serviceName = ap.getString(0);
      String serviceType = ap.getString(1);
      String location = ap.getString(2);
      if (this.domainType == "RuntimeRuntimeServerDomain") {
         return this.runtimeServiceMBean.findService(serviceName, serviceType);
      } else {
         return this.domainType == "RuntimeDomainRuntime" ? this.domainRuntimeServiceMBean.findService(serviceName, serviceType, location) : null;
      }
   }

   public void dc(String force) throws Throwable {
      this.commandType = "disconnect";
      if (!this.getBoolean(force) && this.isEditSessionInProgress) {
         String prompt = txtFmt.getDisconnectWithEditSession();
         String answer = this.promptValue(prompt, true);
         if (answer != null && answer.toLowerCase(Locale.US).startsWith("n")) {
            this.println(txtFmt.getDisconnectCancelled());
            return;
         }
      }

      MBeanServerInvocationHandler.clearCache(this.runtimeMSC);
      MBeanServerInvocationHandler.clearCache(this.domainRTMSC);
      Iterator var4 = this.edits.values().iterator();

      while(var4.hasNext()) {
         WLSTEditVariables curEdit = (WLSTEditVariables)var4.next();
         MBeanServerInvocationHandler.clearCache(curEdit.msc);
      }

      this.wlcmo = null;
      this.mbs = null;
      this.domainName = null;
      this.connected = "false";
      this.atDomainLevel = false;
      this.resetEditSession();
      this.getWLSTInterpreter().setDisconnected(true);
      this.getWLSTInterpreter().setRecordingInProgress(false);
      if (!WLSTHelper.globalMBeansVisibleToPartitions && this.partitionName != null && !this.partitionName.equals("DOMAIN")) {
         this.println(txtFmt.getDisconnectedFromPartition(this.partitionName));
      } else {
         this.println(txtFmt.getDisconnectedFromServer(this.serverName));
      }

      this.partitionName = null;
      this.closeJMXConnectors();
      if (this.msMonitor != null) {
         this.msMonitor.removeDisconnectListener();
      }

      if (this.jmxConnectors != null) {
         this.jmxConnectors.clear();
      }

      this.customMBeanListener = null;
      this.domainCustomMBeanListener = null;
      this.editCustomMBeanListener = null;
      this.domainRuntimeServiceMBean = null;
      this.runtimeServiceMBean = null;
      this.mbeanTypeService = null;
      this.runtimeServerRuntimeMBean = null;
      this.configDomainRuntimeDRMBean = null;
      this.runtimeDomainRuntimeDRMBean = null;
      this.edit = null;
      this.edits.clear();
      this.wlstHelper.serverRuntime = null;
      this.initAll();
   }

   private void closeJMXConnectors() {
      try {
         Iterator iter = this.jmxConnectors.iterator();
         this.printDebug(txtFmt.getClosingAllJMXConnections());

         while(iter.hasNext()) {
            JMXConnector conn = (JMXConnector)iter.next();
            conn.close();
         }

         this.printDebug(txtFmt.getDone());
      } catch (IOException var3) {
      }

   }

   public String evaluatePrompt() {
      Iterator iter = this.prompts.iterator();
      String prom = "";

      while(iter.hasNext()) {
         if (prom == "") {
            prom = (String)iter.next();
         } else {
            prom = prom + "/" + iter.next();
         }
      }

      return prom;
   }

   public Object ls(String attrName, String returnType) throws Throwable {
      return this.ls(attrName, returnType, "false");
   }

   public Object ls(String attrName, String returnType, String inheritance) throws Throwable {
      this.commandType = "ls";
      return ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).ls(attrName, returnType, inheritance);
   }

   public void setShowLSResult(boolean show) {
      super.showLSResult = show;
   }

   public boolean isConciseOutput() {
      return this.isConciseOutput;
   }

   public void setConciseOutput(boolean val) {
      this.isConciseOutput = val;
   }

   public void cdToRuntime() throws Throwable {
      this.commandType = "runtime";
      this.browseHandler.cdToRuntime();
   }

   public void cdToConfig() throws Throwable {
      this.commandType = "config";
      this.browseHandler.cdToConfig();
   }

   public void reset() throws Throwable {
      this.commandType = "reset";
      this.browseHandler.reset();
   }

   public Object get(String attrName) throws Throwable {
      this.commandType = "get";
      return this.editHandler.get(attrName);
   }

   public Object getMBean(String mbeanPath) {
      this.commandType = "getMBean";
      return this.editHandler.getMBean(mbeanPath);
   }

   public boolean set(String attrName, Object value) throws Throwable {
      this.commandType = "set";
      return this.editHandler.set(attrName, value);
   }

   public void setEncrypted(String attrName, String propertyName, String configFile, String secretFile) throws Throwable {
      this.commandType = "setEncrypted";
      this.editHandler.setEncrypted(attrName, propertyName, configFile, secretFile);
   }

   public Object invoke(String methodName, Object[] parameters, String[] signatures) throws Throwable {
      this.commandType = "invoke";
      return this.editHandler.invoke(methodName, parameters, signatures);
   }

   public Object create(String mbeanName, String mbeanType, String providerType) throws Throwable {
      this.commandType = "create";
      Object ret = this.editHandler.create(mbeanName, mbeanType, providerType);
      if (ret != null && this.inMBeanTypes) {
         ObjectName objName = this.getObjectName(ret);
         if (objName != null) {
            this.addInstanceObjectName(objName);
         }
      }

      return ret;
   }

   public Object lookup(String mbeanName, String mbeanType) throws ScriptException {
      this.commandType = "lookup";
      return this.editHandler.lookup(mbeanName, mbeanType);
   }

   public String startSvr(String domainName, String serverName, String username, String password, String url, String domainDir, String genDefaultConfig, String overWriteDomDir, String block, int timeOut, String useNM, String serverLog, String sysProps, String jvmArgs, String spaceAsJvmArgsDelimiter) throws Throwable {
      this.commandType = "startServer";
      return this.lifeCycleHandler.startSvr(serverName, domainName, url, username, password, domainDir, genDefaultConfig, overWriteDomDir, block, timeOut, useNM, serverLog, sysProps, jvmArgs, spaceAsJvmArgsDelimiter);
   }

   public boolean startServerNM() throws Throwable {
      return false;
   }

   public void help(String cmd) throws Throwable {
      this.commandType = "help";
      this.infoHandler.help(cmd);
   }

   public boolean shutdown(String name, String entityType, String ignoreSessions, int timeOut, String force, String block, Properties props, String waitForAllSessions) throws Throwable {
      this.commandType = "shutdown";
      return this.lifeCycleHandler.shutdown(name, entityType, ignoreSessions, timeOut, force, block, props, waitForAllSessions);
   }

   public Object softRestart(String name, String block, Properties props, String type) throws Throwable {
      this.commandType = "softRestart";
      return this.lifeCycleHandler.softRestart(name, block, props, type);
   }

   public Object scaleUp(String dynamicClusterName, int numServers, boolean updateConfiguration, boolean block, int timeoutSeconds, String type) throws Throwable {
      this.commandType = "scaleUp";
      return this.lifeCycleHandler.scaleUp(dynamicClusterName, numServers, updateConfiguration, block, timeoutSeconds, type);
   }

   public Object scaleDown(String dynamicClusterName, int numServers, boolean updateConfiguration, boolean block, int timeoutSeconds, String type) throws Throwable {
      this.commandType = "scaleDown";
      return this.lifeCycleHandler.scaleDown(dynamicClusterName, numServers, updateConfiguration, block, timeoutSeconds, type);
   }

   public Object rolloutUpdate(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "rolloutUpdate";
      return this.rolloutHandler.rolloutUpdate(args, kw);
   }

   public Object rolloutOracleHome(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "rolloutOracleHome";
      return this.rolloutHandler.rolloutOracleHome(args, kw);
   }

   public Object rolloutJavaHome(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "rolloutJavaHome";
      return this.rolloutHandler.rolloutJavaHome(args, kw);
   }

   public Object rolloutApplications(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "rolloutApplications";
      return this.rolloutHandler.rolloutApplications(args, kw);
   }

   public Object rollingRestart(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "rollingRestart";
      return this.rolloutHandler.rollingRestart(args, kw);
   }

   public Object deploy(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "deploy";
      return this.jsr88Handler.deploy(args, kw);
   }

   public String uploadUserFileWait(String dirPath, String filePath, String partitionName, boolean overwrite) throws Throwable {
      this.commandType = "uploadUserFileWait";
      return this.userFileHandler.uploadUserFileWait(dirPath, filePath, partitionName, overwrite);
   }

   public void exportDiagnosticDataFromServer(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "exportDiagnosticDataFromServer";
      this.diagnosticsHandler.exportDiagnosticDataFromServer(args, kw);
   }

   public void exportHarvestedTimeSeriesData(String moduleName, String serverName, String partitionName, long beginTimestamp, long endTimestamp, String exportFileName, String dateTimePattern, String last) throws Throwable {
      this.commandType = "exportHarvestedTimeSeriesData";
      this.diagnosticsHandler.exportHarvestedTimeSeriesData(moduleName, serverName, partitionName, beginTimestamp, endTimestamp, exportFileName, dateTimePattern, last);
   }

   public String[] getAvailableDiagnosticDataAccessorNames(String server, String partitionName) throws Throwable {
      this.commandType = "getAvailableDiagnosticDataAccessorNames";
      return this.diagnosticsHandler.getAvailableDiagnosticDataAccessorNames(server, partitionName);
   }

   public String[] getAvailableCapturedImages(String server, String partitionName) throws Throwable {
      this.commandType = "getAvailableCapturedImages";
      return this.diagnosticsHandler.getAvailableCapturedImages(server, partitionName);
   }

   public void purgeCapturedImages(String server, String partitionName, String age) throws Throwable {
      this.commandType = "purgeCapturedImages";
      this.diagnosticsHandler.purgeCapturedImages(server, partitionName, age);
   }

   public void saveDiagnosticImageCaptureFile(String imageName, String server, String destFile, String partitionName) throws Throwable {
      this.commandType = "saveDiagnosticImageCaptureFile";
      this.diagnosticsHandler.saveDiagnosticImageCaptureFile(imageName, server, destFile, partitionName);
   }

   public void saveDiagnosticImageCaptureEntryFile(String imageName, String imageEntryName, String server, String destFile, String partitionName) throws Throwable {
      this.commandType = "saveDiagnosticImageCaptureEntryFile";
      this.diagnosticsHandler.saveDiagnosticImageCaptureEntryFile(imageName, imageEntryName, server, destFile, partitionName);
   }

   public void captureAndSaveDiagnosticImage(String server, String target, String outputFile, String outputDir, String partitionName) throws Throwable {
      this.commandType = "captureAndSaveDiagnosticImage";
      this.diagnosticsHandler.captureAndSaveDiagnosticImage(server, target, outputFile, outputDir, partitionName);
   }

   public void listSystemResourceControls(String server, String target) throws Throwable {
      this.commandType = "listSystemResourceControls";
      this.diagnosticsHandler.listSystemResourceControls(server, target);
   }

   public void enableSystemResource(String resourceName, String server, String target) throws Throwable {
      this.commandType = "enableSystemResource";
      this.diagnosticsHandler.enableSystemResource(resourceName, server, target);
   }

   public void disableSystemResource(String resourceName, String server, String target) throws Throwable {
      this.commandType = "disableSystemResource";
      this.diagnosticsHandler.disableSystemResource(resourceName, server, target);
   }

   public void createSystemResourceControl(String resourceName, String descriptorFile, String server, String target, String enabled) throws Throwable {
      this.commandType = "createSystemResourceControl";
      this.diagnosticsHandler.createSystemResourceControl(resourceName, descriptorFile, server, target, enabled);
   }

   public void destroySystemResourceControl(String resourceName, String server, String target) throws Throwable {
      this.commandType = "destroySystemResourceControl";
      this.diagnosticsHandler.destroySystemResourceControl(resourceName, server, target);
   }

   public void dumpDiagnosticData(String resourceName, String server, String file, long freq, long duration, String dateFormat) throws Throwable {
      this.commandType = "dumpDiagnosticData";
      this.diagnosticsHandler.dumpDiagnosticData(resourceName, server, file, freq, duration, dateFormat);
   }

   public void mergeDiagnosticData(String inputDir, String toFile) throws Throwable {
      this.commandType = "dumpDiagnosticData";
      this.diagnosticsHandler.mergeDumpedDiagnosticData(inputDir, toFile);
   }

   public String captureDiagnosticImage(String server, String partitionName) throws Throwable {
      this.commandType = "captureAndSaveDiagnosticImage";
      return this.diagnosticsHandler.captureDiagnosticImage(server, partitionName);
   }

   public void listDebugPatches(String target) throws Throwable {
      this.commandType = "listDebugPatches";
      this.diagnosticsHandler.listDebugPatches(target);
   }

   public void showDebugPatchInfo(String patch, String target) throws Throwable {
      this.commandType = "showDebugPatchInfo";
      this.diagnosticsHandler.showDebugPatchInfo(patch, target);
   }

   public WebLogicMBean[] activateDebugPatch(String patch, String app, String module, String partition, String target) throws Throwable {
      this.commandType = "activateDebugPatch";
      return this.diagnosticsHandler.activateDebugPatch(patch, app, module, partition, target);
   }

   public WebLogicMBean[] deactivateDebugPatches(String patch, String app, String module, String partition, String target) throws Throwable {
      this.commandType = "deactivateDebugPatches";
      return this.diagnosticsHandler.deactivateDebugPatches(patch, app, module, partition, target);
   }

   public WebLogicMBean[] listDebugPatchTasks(String target) throws Throwable {
      this.commandType = "listDebugPatchTasks";
      return this.diagnosticsHandler.listDebugPatchTasks(target);
   }

   public void purgeDebugPatchTasks(String target) throws Throwable {
      this.commandType = "purgeDebugPatchTasks";
      this.diagnosticsHandler.purgeDebugPatchTasks(target);
   }

   public WebLogicMBean[] deactivateAllDebugPatches(String target) throws Throwable {
      this.commandType = "deactivateAllDebugPatches";
      return this.diagnosticsHandler.deactivateAllDebugPatches(target);
   }

   public Object redeploy(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "redeploy";
      return this.jsr88Handler.redeploy(args, kw);
   }

   public Object undeploy(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "undeploy";
      return this.jsr88Handler.undeploy(args, kw);
   }

   public Object start(String name, String type, String listenAddress, int port, String block, Properties props, String disableMsiMode) throws Throwable {
      this.commandType = "start";
      if (listenAddress != null || port != -1) {
         this.println(txtFmt.getOverrideAddressPortNotSupported());
      }

      type = this.lifeCycleHandler.determineType(name, type);
      if (type.equals("Server")) {
         return this.lifeCycleHandler.startServer(name, listenAddress, port, block, disableMsiMode);
      } else if (type.equals("Cluster")) {
         return this.lifeCycleHandler.startCluster(name, block);
      } else if (type.equals("SystemComponent")) {
         return this.lifeCycleHandler.startComponent(name, block, props);
      } else if (type.equals("SystemComponentConfiguration")) {
         return this.lifeCycleHandler.startComponentConfiguration(name, block, props);
      } else {
         this.throwWLSTException(txtFmt.getSpecifyValidType("[Server|Cluster|SystemComponent|SystemComponentConfiguration]"));
         return null;
      }
   }

   public void startRecording(String filePath, String recordAll) throws Throwable {
      this.commandType = "startRecording";
      this.infoHandler.startRecording(filePath, recordAll);
   }

   public void stopRecording() throws Throwable {
      this.commandType = "stopRecording";
      this.infoHandler.stopRecording();
   }

   public InformationHandler getInfoHandler() {
      return (InformationHandler)InformationHandler.class.cast(this.infoHandler);
   }

   public void easeSyntax() {
      if (this.getWLSTInterpreter().getEaseSyntax()) {
         this.getWLSTInterpreter().setEaseSyntax(false);
         this.println(txtFmt.getEasySyntaxOff());
      } else {
         this.getWLSTInterpreter().setEaseSyntax(true);
         this.println(txtFmt.getEasySyntaxOn());
      }

   }

   public void record(String s) throws Throwable {
      this.commandType = "startRecording";
      this.commandType = "record";
      ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).writeCommand("\n" + s);
   }

   public void dumpVariables() throws Throwable {
      this.commandType = "dumpVariables";
      ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).dumpVariables();
   }

   public HashMap state(String name, String type) throws Throwable {
      this.commandType = "state";
      return this.lifeCycleHandler.state(name, type);
   }

   public String progress(String name) throws Throwable {
      this.commandType = "progress";
      return this.lifeCycleHandler.progress(name);
   }

   public Object suspend(String name, String ignoreSessions, int timeOut, String force, String block) throws ScriptException {
      this.commandType = "suspend";
      return this.lifeCycleHandler.suspend(name, ignoreSessions, timeOut, force, block);
   }

   public Object resume(String name, String block) throws Throwable {
      this.commandType = "resume";
      return this.lifeCycleHandler.resume(name, block);
   }

   public void storeUserConfig(String userConfigFile, String userKeyFile, String nm) throws Throwable {
      this.commandType = "storeUserConfig";
      ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).storeUserConfig(userConfigFile, userKeyFile, nm);
   }

   public void delete(String name, String mbeanType) throws Throwable {
      this.commandType = "delete";
      this.editHandler.delete(name, mbeanType);
   }

   public void exit(String defaultAnswer, int exitcode) throws Throwable {
      this.commandType = "exit";
      if (this.isEditSessionInProgress) {
         if (defaultAnswer == null) {
            defaultAnswer = this.promptValue(txtFmt.getExitWithEditSession(), true);
         }

         if (defaultAnswer.toLowerCase(Locale.US).startsWith("n")) {
            this.println(txtFmt.getExitCancelled());
            return;
         }

         try {
            this.editService.stopEdit();
         } catch (Throwable var4) {
         }
      }

      this.println(txtFmt.getExitingWLST());
      System.exit(exitcode);
   }

   public void loadProperties(String fileName, InteractiveInterpreter interp) throws Throwable {
      this.commandType = "loadProperties";
      WLSTUtil.setProperties(fileName, interp);
   }

   public void adminConfig() throws Throwable {
      this.commandType = "adminconfig";
      this.browseHandler.adminConfig();
   }

   public void cdToCustom(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "custom";
      ArgParser ap = new ArgParser(this.commandType, args, kw, "objectNamePattern");
      String objectNamePattern = ap.getString(0);
      this.browseHandler.custom(objectNamePattern);
   }

   public void cdToDomainCustom(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "domainCustom";
      ArgParser ap = new ArgParser(this.commandType, args, kw, "objectNamePattern");
      String objectNamePattern = ap.getString(0);
      this.browseHandler.domainCustom(objectNamePattern);
   }

   public void cdToEditCustom(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "editCustom";
      ArgParser ap = new ArgParser(this.commandType, args, kw, "objectNamePattern");
      String objectNamePattern = ap.getString(0);
      this.browseHandler.editCustom(objectNamePattern);
   }

   public String man(String attributeName) throws Throwable {
      this.commandType = "man";
      return ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).man(attributeName);
   }

   public Object threadDump(String writeToFile, String fileName, String serverName) throws ScriptException {
      this.commandType = "threadDump";
      return ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).threadDump(writeToFile, fileName, serverName);
   }

   public void startServer(String serverName, String domainName, String url, String username, String password, String rootDirectory, String generateDefaultConfig, String overWriteRootDirectory, String block, int timeout) throws Throwable {
      this.commandType = "startServer";
      this.lifeCycleHandler.startSvr(serverName, domainName, url, username, password, rootDirectory, generateDefaultConfig, overWriteRootDirectory, block, timeout, "true", (String)null, (String)null, (String)null, "false");
   }

   public List listChildTypes(String parent) throws Throwable {
      this.commandType = "listChildTypes";
      return ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).listChildrenTypes(parent);
   }

   public void runtimeServer() throws Throwable {
      this.commandType = "serverConfig";
      this.newBrowseHandler.configRuntime();
   }

   public void configRuntime() throws Throwable {
      this.commandType = "serverConfig";
      this.newBrowseHandler.configRuntime();
   }

   public void runtimeRuntime() throws Throwable {
      this.commandType = "serverRuntime";
      this.newBrowseHandler.runtimeRuntime();
   }

   public void configDomainRuntime() throws Throwable {
      this.commandType = "domainConfig";
      this.newBrowseHandler.configDomainRuntime();
   }

   public void jndi(String serverName) throws Throwable {
      this.commandType = "jndi";
      this.browseHandler.jndi(serverName);
   }

   public void runtimeDomainRuntime() throws Throwable {
      this.commandType = "domainRuntime";
      this.newBrowseHandler.runtimeDomainRuntime();
   }

   public void configEdit(String name) throws Throwable {
      this.commandType = "edit";
      this.newBrowseHandler.configEdit(name);
   }

   public void jsr77() throws Throwable {
      this.commandType = "jsr77";
      this.newBrowseHandler.jsr77();
   }

   public List find(String name, String type, String tree) throws Throwable {
      this.commandType = "find";
      if (!this.domainType.equals("RuntimeDomainRuntime") && !this.domainType.equals("Custom_Domain") && !this.domainType.equals("DomainCustom_Domain")) {
         if (type != null) {
            return this.findUtil.findMBean(name, type, tree);
         } else if (name != null) {
            return this.findUtil.findAttribute(name, tree);
         } else {
            this.println(txtFmt.getSpecifyNameOrType());
            return new ArrayList();
         }
      } else {
         this.println(txtFmt.getFindIsNotSupported());
         return new ArrayList();
      }
   }

   public Object loadApplication(String appPath, String planPath, String createPlan) throws Throwable {
      this.commandType = "loadApplication";
      return this.jsr88Handler.loadApplication(appPath, planPath, createPlan);
   }

   public void migrateServer(String serverName, String machineName, String sourceDown, String destinationDown) throws Exception {
      this.commandType = "migrateServer";
      this.clusterHandler.doManualMigration(serverName, machineName, sourceDown, destinationDown);
   }

   public void migrateAll(String serverName, String distinationName, String sourceDown, String destinationDown) throws Exception {
      this.commandType = "migrateAll";
      this.clusterHandler.doMigrateAll(serverName, distinationName, sourceDown, destinationDown);
   }

   public void migrate(String serverName, String distinationName, String sourceDown, String destinationDown, String migrationType) throws Exception {
      this.commandType = "migrate";
      this.clusterHandler.migrate(serverName, distinationName, sourceDown, destinationDown, migrationType);
   }

   public Object distributeApplication(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "distributeApplication";
      return this.jsr88Handler.distributeApplication(args, kw);
   }

   public Object appendToExtensionLoader(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "appendToExtensionLoader";
      return this.jsr88Handler.appendToExtensionLoader(args, kw);
   }

   public void listApplications() throws ScriptException {
      this.commandType = "listApplications";
      this.jsr88Handler.listApplications();
   }

   public Object startApplication(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "startApplication";
      return this.jsr88Handler.startApplication(args, kw);
   }

   public Object stopApplication(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "stopApplication";
      return this.jsr88Handler.stopApplication(args, kw);
   }

   public Object updateApplication(PyObject[] args, String[] kw) throws Throwable {
      this.commandType = "updateApplication";
      return this.jsr88Handler.updateApplication(args, kw);
   }

   public Object getWLDM() throws Throwable {
      this.commandType = "getWLDM";
      return this.jsr88Handler.getWLDM();
   }

   public Object getMBI(String mbeanType) throws Exception {
      this.commandType = "getMBI";
      return ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).getMBI(mbeanType);
   }

   public void redirect(String outputFile, String toStdOut) throws ScriptException {
      this.commandType = "redirect";
      this.infoHandler.redirect(outputFile, toStdOut);
   }

   public void stopRedirect() throws ScriptException {
      this.commandType = "stopRedirect";
      this.infoHandler.stopRedirect();
   }

   public void watch(Object mbean, String attributeNames, String logFile, String watchName) throws ScriptException {
      this.commandType = "addListener";
      this.watchUtil.watch(mbean, attributeNames, logFile, watchName);
   }

   public void removeWatch(Object mbean, String watchName) throws ScriptException {
      this.commandType = "removeListener";
      this.watchUtil.removeWatch(mbean, watchName);
   }

   public void showWatches() throws ScriptException {
      this.commandType = "showListeners";
      this.watchUtil.showWatches();
   }

   public Object encrypt(Object obj, String domainDir) throws ScriptException {
      this.commandType = "encrypt";
      return this.editHandler.encrypt(obj, domainDir);
   }

   public void viewMBean(Object obj) throws ScriptException {
      this.commandType = "viewMBean";
      ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).viewMBean(obj);
   }

   public String getPath(Object obj) throws ScriptException {
      this.commandType = "getPath";
      return ((InformationHandler)InformationHandler.class.cast(this.infoHandler)).getPath(obj);
   }

   public void skipSingletonCd(String val) throws ScriptException {
      this.skipSingletons = this.getBoolean(val);
   }

   public void dumpMBeans(String val) throws Throwable {
      if (val != null) {
         if (val.toLowerCase(Locale.US).equals("all")) {
            this.wlstHelper.dumpAllMBeans(this.runtimeMSC, this.getMBeanServerNameFromTree("RuntimeConfigServerDomain"));
            this.wlstHelper.dumpAllMBeans(this.domainRTMSC, this.getMBeanServerNameFromTree("ConfigDomainRuntime"));
            this.wlstHelper.dumpAllMBeans(this.getMBSConnection("DomainConfig"), this.getMBeanServerNameFromTree("DomainConfig"));
            if (this.edit != null) {
               this.wlstHelper.dumpAllMBeans(this.edit.msc, this.getMBeanServerNameFromTree("ConfigEdit"));
            }
         }
      } else {
         this.wlstHelper.dumpAllMBeans(this.getMBSConnection((String)null), this.getMBeanServerNameFromTree(this.domainType));
      }

   }

   public WLSTMsgTextFormatter getWLSTMsgFormatter() {
      return txtFmt;
   }

   public NodeManagerCoreService getNodeManagerService() {
      return this.nmService;
   }

   public synchronized void setInstanceObjectName(ObjectName wlsObjectName) {
      this.wlInstanceObjName = wlsObjectName;
      if (wlsObjectName == null) {
         this.wlInstanceObjName_name = null;
      } else {
         this.wlInstanceObjName_name = wlsObjectName.getKeyProperty("Name");
      }

   }

   public synchronized void addInstanceObjectName(ObjectName wlsObjectName) {
      if (this.wlInstanceObjNames != null) {
         for(int i = 0; i < this.wlInstanceObjNames.length; ++i) {
            if (this.wlInstanceObjNames[i].equals(wlsObjectName)) {
               return;
            }
         }

         ObjectName[] objName = new ObjectName[this.wlInstanceObjNames.length + 1];

         for(int i = 0; i < this.wlInstanceObjNames.length; ++i) {
            objName[i] = this.wlInstanceObjNames[i];
         }

         objName[objName.length - 1] = wlsObjectName;
         this.wlInstanceObjNames = objName;
         String[] _objName = new String[this.wlInstanceObjNames_names.length + 1];

         for(int j = 0; j < this.wlInstanceObjNames_names.length; ++j) {
            _objName[j] = this.wlInstanceObjNames_names[j];
         }

         _objName[_objName.length - 1] = wlsObjectName.getKeyProperty("Name");
         this.wlInstanceObjNames_names = _objName;
      } else {
         this.wlInstanceObjNames = new ObjectName[1];
         this.wlInstanceObjNames[0] = wlsObjectName;
         this.wlInstanceObjNames_names = new String[1];
         this.wlInstanceObjNames_names[0] = wlsObjectName.getKeyProperty("Name");
      }

   }

   public synchronized void removeInstanceObjectName(ObjectName wlsObjectName) {
      if (this.wlInstanceObjNames != null) {
         ObjectName[] objName = new ObjectName[this.wlInstanceObjNames.length - 1];

         for(int i = 0; i < this.wlInstanceObjNames.length; ++i) {
            if (!this.wlInstanceObjNames[i].getKeyProperty("Name").equals(wlsObjectName.getKeyProperty("Name"))) {
               objName[i] = this.wlInstanceObjNames[i];
            }
         }

         this.wlInstanceObjNames = objName;
         String[] _objName = new String[this.wlInstanceObjNames_names.length - 1];

         for(int j = 0; j < this.wlInstanceObjNames.length; ++j) {
            if (!this.wlInstanceObjNames_names[j].equals(wlsObjectName.getKeyProperty("Name"))) {
               _objName[j] = this.wlInstanceObjNames_names[j];
            }
         }

         this.wlInstanceObjNames_names = _objName;
      }

   }

   public void setHideDumpStack(String bool) {
      if (this.getBoolean(bool)) {
         this.hideDumpStack = true;
      } else {
         this.hideDumpStack = false;
      }

   }

   public void addEditChangeListener() throws ScriptException {
      try {
         this.wlstHelper.addEditChangeListener();
      } catch (Throwable var2) {
         if (var2 instanceof ScriptException) {
            throw (ScriptException)var2;
         }

         this.throwWLSTException(txtFmt.getErrorAddingEditListener(), var2);
      }

   }

   public void addCompatChangeListener() throws ScriptException {
      try {
         this.wlstHelper.addCompatChangeListener();
      } catch (Throwable var2) {
         if (var2 instanceof ScriptException) {
            throw (ScriptException)var2;
         }

         this.throwWLSTException(txtFmt.getErrorAddingCompatibilityChangeListener(), var2);
      }

   }

   public void resetEditSession() {
      if (this.edit != null) {
         this.edit.isEditSessionInProgress = false;
         this.edit.isEditSessionExclusive = false;
      }

      this.isEditSessionInProgress = false;
      this.isEditSessionExclusive = false;
   }

   public void addHelpCommandGroup(String groupName, String resourceBundleName) throws Throwable {
      this.commandType = "addHelpCommandGroup";
      this.infoHandler.addHelpCommandGroup(groupName, resourceBundleName);
   }

   public void addHelpCommand(String commandName, String groupName, String offline, String online) throws Throwable {
      this.commandType = "addHelpCommand";
      this.infoHandler.addHelpCommand(commandName, groupName, offline, online);
   }

   public void getCommandAutoCompletions(String cmd, List list) {
      this.scriptCmdHelp.getCommandAutoCompletions(cmd, list);
   }

   public boolean isDebug() {
      return this.debug;
   }

   WLSTEditVariables getEditByName(String name, boolean createIfNotExists) throws Throwable {
      WLSTEditVariables result = (WLSTEditVariables)this.edits.get(name);
      if (result == null && name != null) {
         this.printDebug("Edit session " + name + " is not yet connected. Try to connect.");

         try {
            result = this.wlstHelper.createEditServerConnection(name);
         } catch (IllegalArgumentException var5) {
            if (!createIfNotExists) {
               throw var5;
            }

            this.println(txtFmt.getNamedEditSessionDoesNotExistCreate(name));
            this.editService.createEditSession(name, (String)null);
            result = this.wlstHelper.createEditServerConnection(name);
         }

         if (result != null) {
            this.edits.put(name, result);
         }
      }

      if (this.debug && result == null) {
         this.printDebug("Edit session " + name + " was not located.");
      }

      return result;
   }

   void setEdit(WLSTEditVariables editVars) {
      if (this.edit != editVars) {
         this.edit = editVars;
         if (editVars != null) {
            this.isEditSessionInProgress = this.edit.isEditSessionInProgress;
            this.isEditSessionExclusive = this.edit.isEditSessionExclusive;
         }
      }

   }

   public boolean isDomainEditSession() {
      return this.partitionName == null || "DOMAIN".equals(this.partitionName);
   }

   EditSessionConfigurationManagerMBean getEditSessionConfigurationManager() {
      try {
         if (this.isDomainEditSession()) {
            this.printDebug("edit sessions from DOMAIN partition");
            return this.runtimeDomainRuntimeDRMBean.getEditSessionConfigurationManager();
         } else {
            this.printDebug("edit sessions from partition " + this.partitionName);
            return this.runtimeDomainRuntimeDRMBean.lookupDomainPartitionRuntime(this.partitionName).getEditSessionConfigurationManager();
         }
      } catch (Exception var2) {
         if (var2.getCause() instanceof AttributeNotFoundException) {
            this.isNamedEditSessionAvailable = false;
            return null;
         } else {
            throw var2;
         }
      }
   }
}
