package weblogic.management.scripting.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.python.core.ArgParser;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import weblogic.management.scripting.NMConstants;
import weblogic.management.scripting.ScriptException;
import weblogic.management.scripting.WLCoreScriptContext;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.nodemanager.NMException;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.client.NMClient;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;

public class NodeManagerCoreService implements NMConstants {
   WLCoreScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   private NMClient nmc = null;
   private String domainName = "mydomain";
   private boolean connectedToNM = false;
   private String nmVersion = "";
   private String domainDir = ".";
   private final String DUMMY_DOMAIN_NAME = "<NO_DOMAIN_NAME>";
   private String nmType = null;
   private String nmHost = null;
   private int nmPort = -1;
   protected UsernameAndPassword nmCredential = new UsernameAndPassword();
   private String verbose = "false";
   private int timeout = 0;
   private static final String userHome = System.getProperty("user.home");
   private static final String defaultFilePrefix;

   public NodeManagerCoreService() {
   }

   public NodeManagerCoreService(WLCoreScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   private void initSystemProperties(String nmType) {
      if (nmType.equalsIgnoreCase("ssl")) {
         String keyStore = System.getProperty("weblogic.security.TrustKeyStore");
         if (keyStore == null) {
            System.setProperty("weblogic.security.TrustKeyStore", "DemoTrust");
         }

         String igSSL = System.getProperty("weblogic.security.SSL.ignoreHostnameVerification");
         if (igSSL == null) {
            System.setProperty("weblogic.security.SSL.ignoreHostnameVerification", "true");
         }

         if (this.ctx.isStandalone()) {
            String trustStore = System.getProperty("javax.net.ssl.trustStore");
            String mwHome = System.getProperty("MW_HOME");
            if (trustStore == null && mwHome != null) {
               String trustStorePath = mwHome + File.separator + "wlserver" + File.separator + "server" + File.separator + "lib" + File.separator + "DemoTrust.jks";
               if ((new File(trustStorePath)).exists()) {
                  System.setProperty("javax.net.ssl.trustStore", trustStorePath);
               }
            }
         }
      }

   }

   private String getDefaultConfigFilePath(String domain) {
      return defaultFilePrefix + "nm-cfg-" + domain + ".props";
   }

   private String getDefaultKeyFilePath(String domain) {
      return defaultFilePrefix + "nm-key-" + domain + ".props";
   }

   public void nmConnect(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "connect";
      ArgParser ap = new ArgParser("nmConnect", args, kw, "username", "password");
      if (ap.getString(0) != null) {
         this.nmCredential.setUsername(ap.getString(0));
      }

      String passStr = ap.getString(1);
      if (passStr != null && passStr.length() != 0) {
         this.nmCredential.setPassword(passStr.toCharArray());
      }

      this.nmHost = ap.getString(2, "localhost");

      try {
         this.nmPort = ap.getInt(3);
      } catch (PyException var16) {
         this.nmPort = Integer.parseInt(ap.getString(3));
      }

      this.domainName = ap.getString(4);
      if (this.domainName == null) {
         this.domainName = "mydomain";
      }

      this.domainDir = ap.getString(5);
      this.nmType = ap.getString(6, "ssl");
      this.verbose = ap.getString(7, "false");
      this.timeout = ap.getInt(8, 0);
      this.initSystemProperties(this.nmType);
      boolean usingStoreUserConfig = false;
      boolean usingBootProps = false;
      if (!this.nmCredential.isUsernameSet() || !this.nmCredential.isPasswordSet()) {
         usingBootProps = true;
         this.ctx.printDebug("Will check if userConfig and userKeyFile should be used to connect to the server");
      }

      if (this.nmPort == -1) {
         this.nmPort = this.determinePort(this.nmType);
      }

      PyDictionary objs = (PyDictionary)ap.getPyObject(9);
      String userConfig = null;
      String userKey = null;
      this.ctx.commandType = "nmConnect";
      PyString pyStoreUserConfig;
      PyString pyStoreUserConfig;
      if (usingBootProps) {
         pyStoreUserConfig = new PyString("userConfigFile");
         pyStoreUserConfig = new PyString("userKeyFile");
         if (objs.has_key(pyStoreUserConfig)) {
            userConfig = objs.get(pyStoreUserConfig).toString();
            this.ctx.printDebug("The userConfig file location is " + userConfig);
         }

         if (objs.has_key(pyStoreUserConfig)) {
            userKey = objs.get(pyStoreUserConfig).toString();
            this.ctx.printDebug("The user key location is " + userKey);
         }

         if (userConfig == null && userKey == null) {
            String configFilePath = this.getDefaultConfigFilePath(this.domainName);
            File defaultUserConfigFile = new File(configFilePath);
            if (defaultUserConfigFile.exists()) {
               userConfig = configFilePath;
            }

            String keyFilePath = this.getDefaultKeyFilePath(this.domainName);
            File defaultUserKeyFile = new File(keyFilePath);
            if (defaultUserKeyFile.exists()) {
               userKey = keyFilePath;
            }
         }

         UsernameAndPassword UAndP = UserConfigFileManager.getUsernameAndPassword(userConfig, userKey, "weblogic.management");
         if (UAndP != null && UAndP.isUsernameSet() && UAndP.isPasswordSet()) {
            this.ctx.printDebug("Found userConfig and userKeyFile.");
            this.nmCredential = UAndP;
            this.ctx.printDebug("The username is " + this.nmCredential.getUsername());
            this.ctx.printDebug("The password is ******");
         }
      }

      String pwd2;
      if (!this.nmCredential.isUsernameSet()) {
         this.ctx.printDebug("There is no username or password, and no userConfigFile and userKeyFile, so prompt for user to type the username and the password from the console.");
         pwd2 = this.ctx.promptValue(this.txtFmt.getEnterUsername(), true);
         if (pwd2 != null && pwd2.length() != 0) {
            this.nmCredential.setUsername(pwd2);
         } else {
            this.ctx.throwWLSTException(this.txtFmt.getEmptyUsername());
         }
      }

      if (!this.nmCredential.isPasswordSet()) {
         pyStoreUserConfig = null;
         pwd2 = this.ctx.promptValue(this.txtFmt.getEnterNMPassword(), false);
         if (pwd2 != null && pwd2.trim().length() != 0 && !this.hasUnicodeCharacters(pwd2)) {
            this.nmCredential.setPassword(pwd2.toCharArray());
         } else {
            this.ctx.throwWLSTException(this.txtFmt.getEmptyPassword());
         }
      }

      if (this.nmCredential.isUsernameSet() || this.nmCredential.isPasswordSet()) {
         pyStoreUserConfig = new PyString("storeUserConfig");
         if (objs.has_key(pyStoreUserConfig)) {
            pyStoreUserConfig = (PyString)objs.get(pyStoreUserConfig);
            if (pyStoreUserConfig.equals(new PyString("true"))) {
               usingStoreUserConfig = true;
               this.ctx.printDebug("storeUserConfig is true. Save the user config to " + this.getDefaultConfigFilePath(this.domainName));
               this.storeUserConfig(this.domainName, this.nmCredential.getUsername(), this.nmCredential.getPassword());
            }
         }
      }

      if (!this.nmCredential.isUsernameSet()) {
         this.ctx.throwWLSTException(this.txtFmt.getNMUserNotSpecified());
      }

      if (!this.nmCredential.isPasswordSet()) {
         this.ctx.throwWLSTException(this.txtFmt.getNMPasswordNotSpecified());
      }

      this.ctx.println(this.txtFmt.getConnectingToNodeManager());
      this.nmc = NMClient.getInstance(this.nmType);
      this.nmc.setHost(this.nmHost);
      this.nmc.setPort(this.nmPort);
      this.nmc.setConnectionCreationTimeout(this.timeout);
      this.nmc.setDomainName(this.domainName);
      if (this.domainDir != null) {
         this.nmc.setDomainDir(this.domainDir);
      }

      if (this.ctx.getBoolean(this.verbose)) {
         this.nmc.setVerbose(true);
      }

      this.nmc.setNMUser(this.nmCredential.getUsername());
      this.nmc.setNMPass(new String(this.nmCredential.getPassword()));

      try {
         this.verifyConnection();
      } catch (ScriptException var17) {
         if (usingStoreUserConfig) {
            pyStoreUserConfig = new PyString("storeUserConfig");
            if (objs.has_key(pyStoreUserConfig)) {
               this.restoreUserConfig(this.domainName);
            }
         }

         throw var17;
      }

      this.connectedToNM = true;
   }

   private void storeUserConfig(String domainName, String uname, char[] pwd) throws ScriptException {
      String configFilePath = this.getDefaultConfigFilePath(domainName);
      File defaultUserConfigFile = new File(configFilePath);
      String keyFilePath = this.getDefaultKeyFilePath(domainName);
      File defaultUserKeyFile = new File(keyFilePath);
      if (defaultUserConfigFile.getParentFile() != null && !defaultUserConfigFile.getParentFile().exists()) {
         defaultUserConfigFile.getParentFile().mkdirs();
      }

      if (defaultUserKeyFile.getParentFile() != null && !defaultUserKeyFile.getParentFile().exists()) {
         defaultUserKeyFile.getParentFile().mkdirs();
      }

      System.setProperty("weblogic.management.confirmKeyfileCreation", "true");
      UsernameAndPassword UandP = new UsernameAndPassword(uname, pwd);
      UserConfigFileManager.setUsernameAndPassword(UandP, configFilePath, keyFilePath, "weblogic.management");
      if (defaultUserKeyFile.exists()) {
         String msg = this.ctx.getWLSTMsgFormatter().getUsernamePasswordStored("WebLogic NodeManager", configFilePath, keyFilePath);
         this.ctx.println(msg);
      }

   }

   private void restoreUserConfig(String domainName) {
      String configFilePath = this.getDefaultConfigFilePath(domainName);
      File defaultUserConfigFile = new File(configFilePath);
      String keyFilePath = this.getDefaultKeyFilePath(domainName);
      File defaultUserKeyFile = new File(keyFilePath);
      if (defaultUserKeyFile.exists()) {
         defaultUserKeyFile.delete();
      }

      if (defaultUserConfigFile.exists()) {
         defaultUserConfigFile.delete();
      }

      if (defaultUserConfigFile.getParentFile() != null && defaultUserConfigFile.getParentFile().listFiles().length == 0) {
         defaultUserConfigFile.getParentFile().delete();
      }

      if (defaultUserKeyFile.getParentFile() != null && defaultUserKeyFile.getParentFile().exists() && defaultUserKeyFile.getParentFile().listFiles().length == 0) {
         defaultUserKeyFile.getParentFile().delete();
      }

   }

   private boolean hasUnicodeCharacters(String s) {
      char[] cs = s.toCharArray();

      for(int i = 0; i < cs.length; ++i) {
         if (Character.isUnicodeIdentifierPart(cs[i]) && cs.length == 4) {
            this.ctx.printDebug("Found a Unicode character in the string specified, hence this string will be considered empty");
            return true;
         }
      }

      return false;
   }

   private void verifyConnection() throws ScriptException {
      try {
         this.ctx.printDebug("checking the username & password for NM");
         this.nmVersion = this.nmc.getVersion();
         this.ctx.printDebug("got connected to NM and got the version " + this.nmVersion);
         this.ctx.println(this.txtFmt.getConnectedToNodeManager());
      } catch (IOException var2) {
         this.ctx.throwWLSTException(this.txtFmt.getCouldNotConnectToNodeManager(), var2);
      }

   }

   private int determinePort(String nmtype) {
      if (!nmtype.equalsIgnoreCase("plain") && !nmtype.equalsIgnoreCase("ssl")) {
         if (nmtype.equalsIgnoreCase("ssh")) {
            return 22;
         } else {
            return nmtype.equalsIgnoreCase("rsh") ? 514 : 5556;
         }
      } else {
         return 5556;
      }
   }

   public NMClient getNMClient() {
      return this.nmc;
   }

   public void setNMClient(NMClient nmclient) {
      this.nmc = nmclient;
   }

   public boolean isConnectedToNM() {
      if (this.connectedToNM && this.nmc != null) {
         try {
            this.nmc.getVersion();
         } catch (Exception var4) {
            if (this.ctx.debug) {
               var4.printStackTrace();
            }

            try {
               this.connectedToNM = false;
               this.nmDisconnect();
            } catch (Exception var3) {
               if (this.ctx.debug) {
                  var3.printStackTrace();
               }
            }

            return false;
         }

         return this.connectedToNM;
      } else {
         return false;
      }
   }

   public void setConnectedToNM(boolean connToNM) {
      this.connectedToNM = connToNM;
   }

   public void nmDisconnect() throws ScriptException {
      this.ctx.commandType = "nmDisconnect";
      boolean wasConnected = this.connectedToNM;
      if (this.nmc != null) {
         this.nmc.done();
      }

      this.nmc = null;
      this.connectedToNM = false;
      this.nmCredential.setUsername((String)null);
      this.nmCredential.setPassword((char[])null);
      if (wasConnected) {
         this.ctx.println(this.txtFmt.getDisconnectedFromNodeManager());
      } else {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      }

   }

   public boolean nm() throws ScriptException {
      this.ctx.commandType = "nm";
      if (this.connectedToNM) {
         this.ctx.println(this.txtFmt.getCurrentlyConnectedNM(this.domainName));
      } else {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      }

      return this.connectedToNM;
   }

   public boolean nmKill(String serverName) throws ScriptException {
      return this.nmKill(serverName, "WebLogic", (Properties)null);
   }

   public boolean nmKill(String serverName, String serverType, Properties runtimeProperties) throws ScriptException {
      this.ctx.commandType = "nmKill";
      boolean result = false;
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return result;
      } else {
         this.ctx.println(this.txtFmt.getKillingServer(serverName));
         this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), this.domainDir, this.domainName);

         try {
            if (runtimeProperties != null && !runtimeProperties.isEmpty()) {
               this.nmc.setRuntimeProperties(runtimeProperties);
            }

            this.nmc.kill();
         } catch (NMException var6) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorKillingServer(serverName), var6);
         } catch (IOException var7) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var7);
         }

         this.ctx.println(this.txtFmt.getKilledServer(serverName));
         return true;
      }
   }

   public boolean nmPrintThreadDump(String serverName) throws ScriptException {
      return this.nmPrintThreadDump(serverName, "WebLogic", (Properties)null);
   }

   public boolean nmPrintThreadDump(String serverName, String serverType, Properties runtimeProperties) throws ScriptException {
      this.ctx.commandType = "nmPrintThreadDump";
      boolean result = false;
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return result;
      } else {
         this.ctx.println(this.txtFmt.getPrintThreadDumpServer(serverName));
         this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), this.domainDir, this.domainName);

         try {
            if (runtimeProperties != null && !runtimeProperties.isEmpty()) {
               this.nmc.setRuntimeProperties(runtimeProperties);
            }

            this.nmc.printThreadDump();
            this.ctx.println(this.txtFmt.getDonePrintThreadDumpServer(serverName));
         } catch (NMException var6) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorPrintThreadDumpServer(serverName), var6);
         } catch (IOException var7) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var7);
         }

         return true;
      }
   }

   public boolean nmSoftRestart(String serverName, String serverType, Properties runtimeProperties) throws ScriptException {
      this.ctx.commandType = "nmSoftRestart";
      boolean result = false;
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return result;
      } else {
         this.ctx.println(this.txtFmt.getRestartingServer(serverName));
         this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), this.domainDir, this.domainName);

         try {
            if (runtimeProperties != null && !runtimeProperties.isEmpty()) {
               this.nmc.setRuntimeProperties(runtimeProperties);
            }

            this.nmc.softRestart();
         } catch (NMException var6) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorRestartingServer(serverName), var6);
         } catch (IOException var7) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var7);
         }

         this.ctx.println(this.txtFmt.getRestartedServer(serverName));
         return true;
      }
   }

   public boolean nmStart(String serverName, String domainDir, Properties props, Writer outWriter) throws ScriptException, IOException {
      return this.nmStart(serverName, "WebLogic", domainDir, props, (Properties)null, outWriter);
   }

   public boolean nmStart(String serverName, String serverType, String domainDir, Properties props, Properties runtimeProperties, Writer outWriter) throws ScriptException, IOException {
      this.ctx.commandType = "nmStart";
      boolean result = false;
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return result;
      } else {
         this.ctx.println(this.txtFmt.getStartingServer(serverName));
         if (domainDir == null) {
            domainDir = this.domainDir;
         }

         if (this.domainName.equals("<NO_DOMAIN_NAME>")) {
            this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), domainDir, "mydomain");
            this.domainName = "mydomain";
            this.domainDir = domainDir;
         } else {
            this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), domainDir, this.domainName);
         }

         try {
            if (runtimeProperties != null && !runtimeProperties.isEmpty()) {
               this.nmc.setRuntimeProperties(runtimeProperties);
            }

            if (props == null) {
               this.nmc.start();
            } else {
               this.nmc.start(props);
            }
         } catch (NMException var9) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorStartingServer(serverName), var9);
         } catch (IOException var10) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var10);
         }

         this.ctx.println(this.txtFmt.getStartedServer(serverName));
         return true;
      }
   }

   public void nmQuit() throws ScriptException, IOException {
      this.ctx.commandType = "stopNodeManager";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getCanNotStopNodeManager());
      } else {
         if (this.nmc != null) {
            try {
               this.nmc.quit();
               this.ctx.println(this.txtFmt.getNodeManagerStopped());
               this.nmc = null;
               this.connectedToNM = false;
            } catch (NMException var2) {
               this.ctx.throwWLSTException(this.txtFmt.getProblemStoppingNodeManager(), var2);
            } catch (IOException var3) {
               this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var3);
            }
         }

      }
   }

   public String nmVersion() throws ScriptException {
      this.ctx.commandType = "nmVersion";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return "";
      } else {
         this.ctx.println(this.txtFmt.getNMVersion(this.nmVersion));
         return this.nmVersion;
      }
   }

   public String nmServerStatus(String serverName) throws ScriptException, IOException {
      return this.nmServerStatus(serverName, "WebLogic");
   }

   public String nmServerStatus(String serverName, String serverType) throws ScriptException, IOException {
      this.ctx.commandType = "nmServerStatus";
      String result = "";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return result;
      } else {
         this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), this.domainDir, this.domainName);

         try {
            result = this.nmc.getState(0);
         } catch (Exception var5) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var5);
         }

         this.ctx.println("\n" + result + "\n");
         return result;
      }
   }

   public void nmServerLog(String serverName, Writer outWriter) throws ScriptException, IOException {
      this.nmServerLog(serverName, outWriter, "WebLogic");
   }

   public void nmServerLog(String serverName, Writer outWriter, String serverType) throws ScriptException, IOException {
      this.ctx.commandType = "nmServerLog";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      } else {
         this.nmc = this.getNewNMClient(serverName, this.getServerType(serverType), this.domainDir, this.domainName);
         if (outWriter == null) {
            outWriter = this.getWriter();
         }

         try {
            this.nmc.getLog(outWriter);
         } catch (Exception var5) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var5);
         }

         outWriter.flush();
      }
   }

   public void nmLog(Writer outWriter) throws ScriptException, IOException {
      this.ctx.commandType = "nmLog";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      } else {
         if (outWriter == null) {
            outWriter = this.getWriter();
         }

         this.nmc = this.getNewNMClient((String)null, "WebLogic", this.domainDir, this.domainName);

         try {
            this.nmc.getNMLog(outWriter);
         } catch (Exception var3) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var3);
         }

         outWriter.flush();
      }
   }

   private Writer getWriter() {
      Writer outWriter = null;
      Object stdOutputMedium = this.ctx.getStandardOutputMedium();
      if (stdOutputMedium == null) {
         outWriter = new PrintWriter(System.out);
      } else if (stdOutputMedium instanceof OutputStream) {
         outWriter = new PrintWriter((OutputStream)stdOutputMedium);
      } else if (stdOutputMedium instanceof Writer) {
         outWriter = (Writer)stdOutputMedium;
      }

      return (Writer)outWriter;
   }

   private String getServerType(String serverType) throws ScriptException {
      return serverType != null && serverType.length() != 0 ? serverType : "WebLogic";
   }

   private NMClient getNewNMClient(String serverName, String serverType, String domainDir, String domain_name) throws ScriptException {
      if (this.nmc != null && !this.nmType.equalsIgnoreCase("ssh")) {
         if (serverName != null) {
            this.nmc.setServerName(serverName);
         }

         if (serverType != null) {
            this.nmc.setServerType(serverType);
         }

         return this.nmc;
      } else {
         try {
            this.nmc = NMClient.getInstance(this.nmType);
            this.nmc.setHost(this.nmHost);
            this.nmc.setPort(this.nmPort);
            this.nmc.setDomainName(domain_name);
            this.nmc.setNMUser(this.nmCredential.getUsername());
            this.nmc.setNMPass(new String(this.nmCredential.getPassword()));
            if (domainDir != null) {
               this.nmc.setDomainDir(domainDir);
            }

            if (serverName != null) {
               this.nmc.setServerName(serverName);
            }

            if (serverType != null) {
               this.nmc.setServerType(serverType);
            }

            if (this.ctx.getBoolean(this.verbose)) {
               this.nmc.setVerbose(true);
            }
         } catch (Exception var6) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorGettingNMClient(), var6);
         }

         return this.nmc;
      }
   }

   public void nmDiagnostics(String diagnosticType, String[] command, Writer outWriter, String serverType) throws ScriptException, IOException {
      this.ctx.commandType = "nmDiagnostics";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      } else {
         if (outWriter == null) {
            outWriter = this.getWriter();
         }

         this.nmc = this.getNewNMClient((String)null, this.getServerType(serverType), this.domainDir, this.domainName);

         try {
            this.nmc.diagnosticRequest(diagnosticType, command, outWriter);
         } catch (Exception var6) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var6);
         }

         outWriter.flush();
      }
   }

   public void nmInvocation(String pluginType, String[] command, Writer outWriter, String serverType) throws ScriptException, IOException {
      this.ctx.commandType = "nmInvocation";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      } else {
         if (outWriter == null) {
            outWriter = this.getWriter();
         }

         this.nmc = this.getNewNMClient((String)null, this.getServerType(serverType), this.domainDir, this.domainName);

         try {
            this.nmc.invocationRequest(pluginType, command, outWriter);
         } catch (Exception var6) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var6);
         }

         outWriter.flush();
      }
   }

   public String getDomainName() {
      return this.domainName;
   }

   public int nmExecScript(String scriptName, String scriptDir, Properties scriptProps, Writer writer, long timeoutMillis) throws ScriptException, IOException {
      this.ctx.commandType = "nmExecScript";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
         return -1;
      } else {
         if (writer == null) {
            writer = this.getWriter();
         }

         Map scriptEnv = null;
         if (scriptProps != null && !scriptProps.isEmpty()) {
            scriptEnv = new HashMap();
            Iterator var8 = scriptProps.keySet().iterator();

            while(var8.hasNext()) {
               Object key = var8.next();
               scriptEnv.put((String)key, (String)scriptProps.get(key));
            }
         }

         this.nmc = this.getNewNMClient((String)null, (String)null, this.domainDir, this.domainName);
         int rc = -1;

         try {
            rc = this.nmc.executeScript(scriptName, scriptDir, scriptEnv, writer, timeoutMillis);
         } catch (ScriptExecutionFailureException var10) {
            this.ctx.throwWLSTException(var10.toString());
            return var10.getExitCode();
         } catch (IOException var11) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var11);
         }

         writer.flush();
         return rc;
      }
   }

   public void nmRestart(long timeoutMillis) throws ScriptException, IOException {
      this.ctx.commandType = "nmRestart";
      if (!this.connectedToNM) {
         this.ctx.println(this.txtFmt.getNotConnectedNM());
      } else {
         this.nmc = this.getNewNMClient((String)null, (String)null, this.domainDir, this.domainName);

         try {
            this.nmc.restart(timeoutMillis, false);
            this.ctx.println(this.txtFmt.getNodeManagerRestarted());
            this.nmc = null;
            this.connectedToNM = false;
         } catch (NMException var4) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorRestartingNM(), var4);
         } catch (IOException var5) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorFromNodeManager(), var5);
         }

      }
   }

   static {
      defaultFilePrefix = userHome + File.separator + ".wlst" + File.separator;
   }
}
