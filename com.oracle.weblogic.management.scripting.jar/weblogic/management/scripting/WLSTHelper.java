package weblogic.management.scripting;

import java.io.File;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.NotificationFilter;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.ConfigurationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import org.python.core.ArgParser;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.MBeanTypeService;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.security.internal.BootProperties;

class WLSTHelper {
   WLScriptContext wlCtx = null;
   String connectionResult = "";
   ServerRuntimeMBean serverRuntime = null;
   PartitionRuntimeMBean partitionRuntime = null;
   boolean isIIOP = false;
   boolean usingInSecureProtocol = true;
   private static final String SERVER_NAME = "adminServerName";
   private static final String NONE = "None";
   private WLSTMsgTextFormatter txtFmt;
   private boolean addedCompatChangeListener = false;
   private static final String ENV_PREPEND = "wlst_connect_";
   private static final String SYS_PROP_PREPEND = "wlst.connect.";
   private static final String RETRY_ATTEMPTS_ENV = "wlst_connect_retryAttempts";
   private static final String RETRY_DELAY_ENV = "wlst_connect_retryDelay";
   private static final String RETRY_ATTEMPTS_SYS_PROP = "wlst.connect.retryAttempts";
   private static final String RETRY_DELAY_SYS_PROP = "wlst.connect.retryDelay";
   public static final boolean globalMBeansVisibleToPartitions = new Boolean(System.getProperty("_GlobalMBeansVisibleToPartitions", "false"));
   private static final HashSet loggers = new HashSet();

   public WLSTHelper(WLScriptContext ctx) {
      this.wlCtx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public synchronized void connect(PyObject[] args, String[] kw) throws ScriptException {
      this.wlCtx.commandType = "connect";

      try {
         ArgParser ap = new ArgParser("connect", args, kw, "username", "password", "url");
         String username = null;
         if (ap.getString(0) != null) {
            username = ap.getString(0);
         }

         char[] pwd = null;
         if (ap.getString(1) != null) {
            pwd = ap.getString(1).toCharArray();
         }

         boolean usingBootProps = false;
         if (username.length() == 0 && pwd.length == 0) {
            usingBootProps = true;
            this.wlCtx.printDebug("Will check if userConfig and userKeyFile should be used to connect to the server");
         }

         String url = ap.getString(2);
         PyDictionary objs = null;

         try {
            objs = (PyDictionary)ap.getPyObject(4);
         } catch (PyException var21) {
            objs = (PyDictionary)ap.getPyObject(3);
         }

         String userConfig = null;
         String userKey = null;
         String adminServerName = null;
         String idd = null;
         this.wlCtx.retryAttempts = 0;
         this.wlCtx.retryDelay = 0L;
         this.wlCtx.commandType = "connect";
         this.wlCtx.connectTimeout = 0;
         WLScriptContext var10002 = this.wlCtx;
         PyString pyConnectTimeout = new PyString("timeout");
         if (objs.has_key(pyConnectTimeout)) {
            String connectTimeout = objs.get(pyConnectTimeout).toString();
            this.wlCtx.printDebug("The connect timeout is " + connectTimeout);
            if (connectTimeout != null && !connectTimeout.equals("None")) {
               this.wlCtx.connectTimeout = Integer.parseInt(connectTimeout);
            }
         }

         var10002 = this.wlCtx;
         PyString pyIdd = new PyString("idd");
         if (objs.has_key(pyIdd)) {
            idd = objs.get(pyIdd).toString();
            this.wlCtx.printDebug("The idd is " + idd);
            if (idd != null && idd.equals("None")) {
               idd = null;
            }
         }

         this.setRetryValues(objs);
         PyString pwd2;
         if (usingBootProps) {
            var10002 = this.wlCtx;
            PyString pyuserConfigFile = new PyString("userConfigFile");
            var10002 = this.wlCtx;
            pwd2 = new PyString("userKeyFile");
            PyString pyserverName = new PyString("adminServerName");
            if (objs.has_key(pyuserConfigFile)) {
               userConfig = objs.get(pyuserConfigFile).toString();
               this.wlCtx.printDebug("The userConfig file location is " + userConfig);
            }

            if (objs.has_key(pwd2)) {
               userKey = objs.get(pwd2).toString();
               this.wlCtx.printDebug("The user key location is " + userKey);
            }

            UsernameAndPassword UAndP = UserConfigFileManager.getUsernameAndPassword(userConfig, userKey, "weblogic.management");
            if (UAndP != null && UAndP.isUsernameSet() && UAndP.isPasswordSet()) {
               username = UAndP.getUsername();
               this.wlCtx.printDebug("The username is " + new String(username));
               pwd = UAndP.getPassword();
               this.wlCtx.printDebug("The password is ******");
               String msg;
               if (userConfig == null) {
                  msg = "The username and password are read from the default user config file: ";
                  this.wlCtx.printDebug("The username and password are read from the default user config file: " + UserConfigFileManager.getDefaultConfigFileName());
               }

               if (userKey == null) {
                  msg = "The default key file location is: ";
                  this.wlCtx.printDebug("The default key file location is: " + UserConfigFileManager.getDefaultKeyFileName());
               }
            }

            if (username.length() == 0 && pwd.length == 0) {
               adminServerName = objs.get(pyserverName).toString();
               if (adminServerName.equals("None")) {
                  adminServerName = null;
               }
            }
         }

         if (username.length() == 0 && pwd.length == 0 && this.wlCtx.isExecutingFromDomainDir()) {
            this.wlCtx.printDebug("wlst is invoked from a domain directory, hence we will try to load username and password from boot.properties file");
            HashMap uap = this.getUsernameAndPassword(adminServerName);
            if (uap != null) {
               if (uap.get("username") != null) {
                  username = (String)uap.get("username");
               }

               if (uap.get("password") != null) {
                  pwd = ((String)uap.get("password")).toCharArray();
               }

               if (uap.get("idd") != null) {
                  idd = (String)uap.get("idd");
               }

               this.wlCtx.printDebug("loaded username and pwd from the boot.properties file");
            }
         }

         String msg;
         if (username.length() == 0 && pwd.length == 0) {
            if (!this.wlCtx.getWLSTInterpreter().getScriptMode()) {
               msg = this.wlCtx.promptValue(this.txtFmt.getEnterUsername(), true);
               pwd2 = null;
               String pwd2 = this.wlCtx.promptValue(this.txtFmt.getEnterPassword(), false);
               if (msg.length() == 0) {
                  this.wlCtx.throwWLSTException(this.txtFmt.getEmptyUsername());
               } else {
                  username = msg;
               }

               if (pwd2.trim().length() != 0 && !this.hasUnicodeCharacters(pwd2)) {
                  pwd = pwd2.toCharArray();
               } else {
                  this.wlCtx.throwWLSTException(this.txtFmt.getEmptyPassword());
               }

               if (url.length() == 0) {
                  var10002 = this.wlCtx;
                  url = this.wlCtx.promptValue(this.txtFmt.getEnterURL("t3://localhost:7001"), true);
               }
            } else {
               this.wlCtx.throwWLSTException(this.txtFmt.getInvalidUsernamePasswd());
            }
         }

         this.wlCtx.username_bytes = username.getBytes();
         this.wlCtx.password_bytes = (new String(pwd)).getBytes();
         if (idd != null) {
            this.wlCtx.idd_bytes = (new String(idd)).getBytes();
         }

         if (url.length() == 0) {
            WLScriptContext var10000 = this.wlCtx;
            url = "t3://localhost:7001";
            this.wlCtx.printDebug(this.txtFmt.getUseDefaultURL(url));
         }

         url = this.checkUrlSanity(url);
         if (this.isIIOP) {
         }

         this.wlCtx.url = url;
         msg = this.txtFmt.getConnectingToURL(this.wlCtx.url, new String(this.wlCtx.username_bytes));
         this.wlCtx.println(msg);
         this.populateInitialContext();

         try {
            this.wlCtx.partitionName = (String)this.wlCtx.iContext.lookup("weblogic.partitionName");
         } catch (NamingException var20) {
         }

         this.initConnections();
         this.wlCtx.newBrowseHandler.configRuntimeNavigatedBefore = true;
         this.wlCtx.newBrowseHandler.configRuntime();
         if (globalMBeansVisibleToPartitions) {
            this.wlCtx.println("");
            this.wlCtx.println("Warning: Use of the GlobalMBeansVisibleToPartitions system property is temporary. It serves only as a transitional aid to the new MBean visibility model. This warning is generated when we detect the old MBean visibility model is in use. Please switch to the new model by eliminating the use of the -DGlobalMBeansVisibleToPartitions property. This property will be removed prior to August 2015.");
         }

         if (!globalMBeansVisibleToPartitions && this.wlCtx.partitionName != null && !this.wlCtx.partitionName.equals("DOMAIN") && this.wlCtx.lastPlaceInConfigRuntime.isEmpty()) {
            this.wlCtx.browseHandler.cd("Partitions/" + this.wlCtx.partitionName);
         }
      } catch (Throwable var22) {
         if (var22 instanceof ScriptException) {
            throw (ScriptException)var22;
         }

         if (var22 instanceof IllegalArgumentException) {
            this.wlCtx.throwWLSTException(this.txtFmt.getAuthenticationFailed(), var22);
         } else {
            this.wlCtx.throwWLSTException(this.txtFmt.getErrorConnectingToServer(), var22);
         }
      }

   }

   private void setRetryValues(PyDictionary objs) {
      this.setRetryValuesDefault();
      this.setRetryValuesFromEnvironment();
      this.setRetryValuesFromSystemProperties();
      this.setRetryValuesFromCommandArgs(objs);
   }

   private void setRetryValuesDefault() {
      this.wlCtx.retryAttempts = 0;
      this.wlCtx.retryDelay = 0L;
   }

   private void setRetryValuesFromEnvironment() {
      String attempts = null;
      String delay = null;
      Iterator var3 = System.getenv().entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         if (attempts == null && "wlst_connect_retryAttempts".equalsIgnoreCase((String)entry.getKey())) {
            attempts = (String)entry.getValue();
         } else if (delay == null && "wlst_connect_retryDelay".equalsIgnoreCase((String)entry.getKey())) {
            delay = (String)entry.getValue();
         }

         if (delay != null && attempts != null) {
            break;
         }
      }

      this.setRetryValuesFromStrings(attempts, delay, "the Environmental Variable");
   }

   private void setRetryValuesFromSystemProperties() {
      String attempts = this.getCaseInsensitiveSystemProperty("wlst.connect.retryAttempts", "wlst_connect_retryAttempts");
      String delay = this.getCaseInsensitiveSystemProperty("wlst.connect.retryDelay", "wlst_connect_retryDelay");
      this.setRetryValuesFromStrings(attempts, delay, "System Property");
   }

   private String getCaseInsensitiveSystemProperty(String key1, String key2) {
      String value = this.getCaseInsensitiveSystemProperty(key1);
      return value != null ? value : this.getCaseInsensitiveSystemProperty(key2);
   }

   private String getCaseInsensitiveSystemProperty(String key) {
      if (key != null && key.length() >= 1) {
         Iterator var2 = System.getProperties().entrySet().iterator();

         Map.Entry entry;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            entry = (Map.Entry)var2.next();
         } while(!key.equalsIgnoreCase(entry.getKey().toString()));

         return entry.getValue().toString();
      } else {
         return null;
      }
   }

   private void setRetryValuesFromCommandArgs(PyDictionary objs) {
      String attempts = null;
      WLScriptContext var10002 = this.wlCtx;
      PyString retryAttemptsKey = new PyString("retryAttempts");
      if (objs.has_key(retryAttemptsKey)) {
         attempts = objs.get(retryAttemptsKey).toString();
      }

      String delay = null;
      var10002 = this.wlCtx;
      PyString retryDelayKey = new PyString("retryDelay");
      if (objs.has_key(retryDelayKey)) {
         delay = objs.get(retryDelayKey).toString();
      }

      this.setRetryValuesFromStrings(attempts, delay, "Command Args");
   }

   private void setRetryValuesFromStrings(String attempts, String delay, String where) {
      if (attempts != null && !attempts.equals("None")) {
         try {
            int prev = this.wlCtx.retryAttempts;
            this.wlCtx.retryAttempts = Integer.parseInt(attempts);
            String extra = "";
            if (prev > 0) {
               extra = " -- Overriding the previous value of " + prev;
            }

            this.wlCtx.printDebug("The retryAttempts from " + where + " is " + this.wlCtx.retryAttempts + extra);
         } catch (NumberFormatException var8) {
         }
      }

      if (delay != null && !delay.equals("None")) {
         try {
            long prev = this.wlCtx.retryDelay;
            String extra = "";
            if (prev > 0L) {
               extra = " -- Overriding the previous value of " + prev;
            }

            this.wlCtx.retryDelay = Long.parseLong(delay);
            this.wlCtx.printDebug("The retryDelay from " + where + " is " + this.wlCtx.retryDelay + extra);
         } catch (NumberFormatException var7) {
         }
      }

   }

   public synchronized void disconnect() {
      this.wlCtx.printDebug("OnDisconnect event occurred");
   }

   private boolean hasUnicodeCharacters(String s) {
      char[] cs = s.toCharArray();

      for(int i = 0; i < cs.length; ++i) {
         if (Character.isUnicodeIdentifierPart(cs[i]) && cs.length == 4) {
            this.wlCtx.printDebug("Found a Unicode character in the string specified, hence this string will be considered empty");
            return true;
         }
      }

      return false;
   }

   private HashMap getUsernameAndPassword(String adminServerName) {
      HashMap map = null;
      File bootProps;
      if (adminServerName == null) {
         bootProps = new File("./boot.properties");
         if (bootProps.exists()) {
            map = this.loadUsernameAndPasswordFromBootProperties(bootProps);
            return map;
         } else {
            File serverDir = new File("./servers/myserver/security/boot.properties");
            if (serverDir.exists()) {
               map = this.loadUsernameAndPasswordFromBootProperties(serverDir);
               return map;
            } else {
               return null;
            }
         }
      } else {
         bootProps = new File("./servers/" + adminServerName + "/security/boot.properties");
         if (bootProps.exists()) {
            map = this.loadUsernameAndPasswordFromBootProperties(bootProps);
            return map;
         } else {
            return null;
         }
      }
   }

   private HashMap loadUsernameAndPasswordFromBootProperties(File bootProps) {
      return loadUsernameAndPasswordFromBootProperties(bootProps, ".");
   }

   public static HashMap loadUsernameAndPasswordFromBootProperties(File bootProps, String domainDir) {
      File saltFile = new File(domainDir + File.separator + "security" + File.separator + "SerializedSystemIni.dat");
      if (saltFile.exists()) {
         BootProperties.load(bootProps.getAbsolutePath(), false);
         BootProperties props = BootProperties.getBootProperties();
         if (props.getOneClient().length() == 0) {
            return null;
         } else {
            HashMap map = new HashMap();
            map.put("username", props.getOneClient());
            map.put("password", props.getTwoClient());
            String idd = props.getIdentityDomainClient();
            if (idd != null && idd.length() > 0) {
               map.put("idd", idd);
            }

            BootProperties.unload(false);
            return map;
         }
      } else {
         return null;
      }
   }

   private void initConnections() throws Throwable {
      this.initRuntimeServerConnection();
      if (this.wlCtx.isAdminServer) {
         this.initDomainRuntimeServerConnection();
         this.initEditServerConnection();
      }

      this.initJsr77ServerConnection();
      this.determineServerInfo(this.serverRuntime);
      this.verifyServerConections();
      this.setLoggingLevel();
   }

   public void addEditChangeListener() throws Throwable {
      if (this.wlCtx.edit != null && this.wlCtx.edit.msc != null && !this.wlCtx.edit.addedEditChangeListener && this.wlCtx.isAdminServer && !this.isIIOP) {
         this.wlCtx.edit.addedEditChangeListener = true;
         this.wlCtx.printDebug("Adding the edit change listener for session " + this.wlCtx.edit + " ...");
         ChangeListener clForEditTree = new ChangeListener(this.wlCtx);
         ObjectName on = new ObjectName("JMImplementation:type=MBeanServerDelegate");
         this.wlCtx.edit.msc.addNotificationListener(on, clForEditTree, (NotificationFilter)null, (Object)null);
         this.wlCtx.printDebug("Done adding the edit change listener for session " + this.wlCtx.edit + " ...");
      }
   }

   public void addCompatChangeListener() throws Throwable {
      if (this.wlCtx.isAdminServer && !this.isIIOP && !this.addedCompatChangeListener) {
         this.addedCompatChangeListener = true;
         this.wlCtx.printDebug("Adding the compatibility change listener ...");
         ChangeListener clForDepTree = new ChangeListener(this.wlCtx);
         ObjectName on = new ObjectName("JMImplementation:type=MBeanServerDelegate");
         if (this.wlCtx.getMBSConnection("Domain") != null) {
            this.wlCtx.getMBSConnection("Domain").addNotificationListener(on, clForDepTree, (NotificationFilter)null, (Object)null);
         }

         this.wlCtx.printDebug("Done adding the compatibility change listener ...");
      }
   }

   private void verifyServerConections() throws Throwable {
      this.wlCtx.println(this.txtFmt.getConnected(this.connectionResult));
      if (this.usingInSecureProtocol) {
         this.wlCtx.println(this.txtFmt.getInsecureProtocol());
      }

   }

   private String checkUrlSanity(String url) {
      if (!url.startsWith("t3s") && !url.startsWith("https")) {
         if (url.startsWith("iiops")) {
            this.usingInSecureProtocol = false;
         }
      } else {
         this.usingInSecureProtocol = false;
      }

      if (!url.startsWith("t3") && !url.startsWith("http")) {
         if (url.startsWith("iiop")) {
            this.isIIOP = true;
            return url;
         } else {
            url = "t3://" + url;
            return url;
         }
      } else {
         return url;
      }
   }

   void dumpAllMBeans(MBeanServerConnection connection, String server) throws Throwable {
      System.out.println("\n\n############ DUMPING ALL MBEANS FOR " + server + " ############\n\n");
      ObjectName pattern = new ObjectName("*:*");
      Set set = connection.queryNames(pattern, (QueryExp)null);
      System.out.println("There are " + set.size() + " MBeans in this MBeanServer");
      Iterator i = set.iterator();

      while(i.hasNext()) {
         System.out.println(i.next());
      }

      System.out.println("\n\n############ DONE DUMPING ALL MBEANS FOR " + server + " ############\n\n");
   }

   void initRuntimeServerConnection() throws Throwable {
      this.wlCtx.printDebug("Initing the RuntimeServer Connection");

      try {
         WLScriptContext var10002 = this.wlCtx;
         this.wlCtx.runtimeMSC = this.lookupMBeanServerConnection("weblogic.management.mbeanservers.runtime");
         ObjectName rserviceON = new ObjectName(RuntimeServiceMBean.OBJECT_NAME);
         this.wlCtx.runtimeServiceMBean = (RuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.wlCtx.runtimeMSC, rserviceON);
         if (!this.isIIOP) {
            Object remoteObj = this.wlCtx.iContext.lookup("weblogic.management.mbeanservers.runtime");
            this.addPeerMonitor(this.wlCtx.serverName, remoteObj, 9);
         }

         this.wlCtx.runtimeDomainMBean = this.wlCtx.runtimeServiceMBean.getDomainConfiguration();
         this.wlCtx.runtimeServerRuntimeMBean = this.wlCtx.runtimeServiceMBean.getServerRuntime();
         this.serverRuntime = this.wlCtx.runtimeServerRuntimeMBean;
         ObjectName typeON = new ObjectName(MBeanTypeService.OBJECT_NAME);
         this.wlCtx.mbeanTypeService = (MBeanTypeService)MBeanServerInvocationHandler.newProxyInstance(this.wlCtx.runtimeMSC, typeON);
         this.wlCtx.printDebug("Got the RuntimeServiceMBean, the Domain Configuration and the ServerRuntime MBean");
         this.wlCtx.printDebug("Initialized the Runtime Server information");
         this.wlCtx.isRuntimeServerEnabled = true;
         this.wlCtx.connected = "true";
         this.wlCtx.atDomainLevel = true;
         this.wlCtx.prompt = "";
         this.wlCtx.prompts = new Stack();
         this.wlCtx.domainName = this.wlCtx.runtimeDomainMBean.getName();
         this.wlCtx.serverName = this.serverRuntime.getName();
         this.wlCtx.newBrowseHandler.configRuntimeNavigateFirstTime = true;
         this.wlCtx.newBrowseHandler.runtimeRuntimeNavigateFirstTime = true;
         if (this.wlCtx.serverName.equals(this.wlCtx.runtimeDomainMBean.getAdminServerName())) {
            this.wlCtx.isAdminServer = true;
         } else {
            this.wlCtx.isAdminServer = false;
         }

         this.wlCtx.version = this.serverRuntime.getWeblogicVersion();
         this.wlCtx.getWLSTInterpreter().setDisconnected(false);
      } catch (Exception var3) {
         this.wlCtx.println(this.txtFmt.getRuntimeMBSNotEnabled());
         this.wlCtx.stackTrace = var3;
         this.wlCtx.throwWLSTException(this.txtFmt.getFailedToConnect(), var3);
      }

   }

   void initDomainRuntimeServerConnection() throws Throwable {
      try {
         WLScriptContext var10002 = this.wlCtx;
         this.wlCtx.domainRTMSC = this.lookupMBeanServerConnection("weblogic.management.mbeanservers.domainruntime");
         ObjectName drsOn = new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME);
         this.wlCtx.domainRuntimeServiceMBean = (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.wlCtx.domainRTMSC, drsOn);
         this.wlCtx.configDomainRuntimeDRMBean = this.wlCtx.domainRuntimeServiceMBean.getDomainConfiguration();
         this.wlCtx.runtimeDomainRuntimeDRMBean = this.wlCtx.domainRuntimeServiceMBean.getDomainRuntime();
         this.wlCtx.isDomainRuntimeServerEnabled = true;
         if (this.serverRuntime == null) {
            this.wlCtx.printDebug("Only the DomainRuntimeServer is enabled.");
         }

         this.wlCtx.newBrowseHandler.configDomainRuntimeNavigateFirstTime = true;
         this.wlCtx.newBrowseHandler.runtimeDomainRuntimeNavigateFirstTime = true;
         this.wlCtx.printDebug("Initialized the Domain Runtime connection");
      } catch (Exception var2) {
         var2.printStackTrace();
         this.wlCtx.println(this.txtFmt.getDomainRuntimeMBSNotEnabled());
         this.wlCtx.stackTrace = var2;
         this.wlCtx.throwWLSTException(this.txtFmt.getFailedToConnect(), var2);
      }

   }

   void initEditServerConnection() throws Throwable {
      this.wlCtx.printDebug("Initing the EditServer Connection");
      WLSTEditVariables edit = this.createEditServerConnection((String)null);
      if (edit != null) {
         if (this.wlCtx.isNamedEditSessionAvailable) {
            this.wlCtx.portablePartitionManager = edit.serviceMBean.getPortablePartitionManager();
         } else {
            this.wlCtx.portablePartitionManager = null;
         }

         this.wlCtx.edit = edit;
         this.wlCtx.edits.put((Object)null, edit);
         this.wlCtx.edits.put("default", edit);
         this.wlCtx.newBrowseHandler.editNavigateFirstTime = true;
         this.wlCtx.printDebug("Initialized the Edit Server information");
      }

   }

   WLSTEditVariables createEditServerConnection(String name) throws Throwable {
      WLSTEditVariables edit = new WLSTEditVariables(name);
      if (name == null) {
         name = "default";
      }

      EditSessionConfigurationManagerMBean sessionManager = this.wlCtx.getEditSessionConfigurationManager();
      String jndiName;
      if (sessionManager == null && !this.wlCtx.isNamedEditSessionAvailable) {
         jndiName = "weblogic.management.mbeanservers.edit";
      } else {
         EditSessionConfigurationRuntimeMBean config = this.wlCtx.getEditSessionConfigurationManager().lookupEditSessionConfiguration(name);
         if (config == null) {
            if (!"default".equals(name)) {
               throw new IllegalArgumentException(this.txtFmt.getNamedEditSessionDoesNotExist());
            }

            jndiName = "weblogic.management.mbeanservers.edit";
         } else {
            jndiName = config.getEditSessionServerJndi();
         }
      }

      try {
         edit.msc = this.lookupMBeanServerConnection(jndiName);
         ObjectName editServiceON = new ObjectName(EditServiceMBean.OBJECT_NAME);
         edit.serviceMBean = (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(edit.msc, editServiceON);
         edit.domainMBean = edit.serviceMBean.getDomainConfiguration();
         edit.configurationManager = edit.serviceMBean.getConfigurationManager();
         this.wlCtx.printDebug("Got the EditServiceMBean, the Domain Configuration and the Configuration manager MBean");
         return edit;
      } catch (Exception var6) {
         this.wlCtx.print(this.txtFmt.getEditMBSNotEnabled());
         this.wlCtx.stackTrace = var6;
         return null;
      }
   }

   void initJsr77ServerConnection() throws Throwable {
   }

   private void populateInitialContext() throws ScriptException {
      Hashtable h = new Hashtable();
      h.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      h.put("java.naming.provider.url", this.wlCtx.url);
      h.put("java.naming.security.principal", new String(this.wlCtx.username_bytes));
      h.put("java.naming.security.credentials", new String(this.wlCtx.password_bytes));
      h.put("weblogic.jndi.createContextRetry.time", new Integer(this.wlCtx.retryAttempts));
      h.put("weblogic.jndi.createContextRetry.interval", new Long(this.wlCtx.retryDelay));
      if (this.wlCtx.idd_bytes != null) {
         h.put("weblogic.jndi.identityDomain", new String(this.wlCtx.idd_bytes));
      }

      if (this.wlCtx.connectTimeout > 0) {
         h.put("weblogic.jndi.responseReadTimeout", new Long((long)this.wlCtx.connectTimeout));
         h.put("weblogic.jndi.connectTimeout", new Long((long)this.wlCtx.connectTimeout));
      }

      try {
         this.wlCtx.iContext = new InitialContext(h);
      } catch (NoInitialContextException var5) {
         this.wlCtx.errorMsg = this.getRightErrorMessage(var5.getRootCause());
         this.wlCtx.throwWLSTException(this.wlCtx.errorMsg, var5);
      } catch (CommunicationException var6) {
         this.wlCtx.throwWLSTException(this.getRightErrorMessage(var6), var6);
      } catch (NamingException var7) {
         if (var7 instanceof CommunicationException) {
            Throwable th = var7.getRootCause();
            this.wlCtx.errorMsg = this.getRightErrorMessage(th);
         } else if (var7 instanceof AuthenticationException) {
            AuthenticationException ae = (AuthenticationException)var7;
            SecurityException se = (SecurityException)ae.getRootCause();
            if (se.getMessage() != null) {
               this.wlCtx.errorMsg = se.getMessage();
            } else {
               this.wlCtx.errorMsg = this.txtFmt.getUsernameOrPasswordIncorrect();
            }
         } else if (var7 instanceof ConfigurationException) {
            this.wlCtx.errorMsg = this.txtFmt.getURLIsMalformed();
         }

         this.wlCtx.throwWLSTException(this.wlCtx.errorMsg, var7);
      }

   }

   private String getRightErrorMessage(Throwable th) {
      String result = "";
      if (th.getMessage() != null && th.getMessage().indexOf("Tunneling result unspecified - is the HTTP server at host") != -1 && this.wlCtx.url.startsWith("http")) {
         result = this.txtFmt.getCanNotConnectViaHTTP();
      } else if (th.getMessage() != null && th.getMessage().indexOf("javax.net.ssl.SSLKeyException") != -1) {
         result = this.txtFmt.getCanNotConnectViaSSL();
      } else if (th.getCause() == null || !(th.getCause() instanceof ConnectException) || !this.wlCtx.url.startsWith("t3s") && !this.wlCtx.url.startsWith("https")) {
         result = this.txtFmt.getErrorGettingInitialContext(this.wlCtx.url);
      } else {
         result = this.txtFmt.getCanNotConnectViaT3s();
      }

      return result;
   }

   private void determineServerInfo(ServerRuntimeMBean srbean) {
      if (!globalMBeansVisibleToPartitions && this.wlCtx.partitionName != null && !this.wlCtx.partitionName.equals("DOMAIN")) {
         this.connectionResult = this.txtFmt.getConnectToPartition(this.wlCtx.partitionName);
      } else if (this.wlCtx.isAdminServer) {
         if (this.wlCtx.domainName == null) {
            this.wlCtx.domainName = this.serverRuntime.getObjectName().getDomain();
         }

         this.connectionResult = this.txtFmt.getConnectToAdminServer(this.wlCtx.serverName, this.wlCtx.domainName);
      } else {
         if (this.wlCtx.domainName == null) {
            this.wlCtx.domainName = this.serverRuntime.getObjectName().getDomain();
         }

         this.connectionResult = this.txtFmt.getConnectToManaged(this.wlCtx.serverName, this.wlCtx.domainName);
         this.wlCtx.printDebug("Checking if this server belongs to any cluster");
      }

   }

   private MBeanServerConnection lookupMBeanServerConnection(String jndiName) throws Exception {
      String host = this.wlCtx.getListenAddress(this.wlCtx.url);
      int port = Integer.parseInt(this.wlCtx.getListenPort(this.wlCtx.url));
      String remainingPath = this.findPath(this.wlCtx.url);
      String protocol = this.wlCtx.getProtocol(this.wlCtx.url);
      String ctxPath;
      StringBuilder var10000;
      WLScriptContext var10001;
      if (remainingPath == null) {
         var10000 = new StringBuilder();
         var10001 = this.wlCtx;
         ctxPath = var10000.append("/jndi/").append(jndiName).toString();
      } else {
         var10000 = (new StringBuilder()).append("/").append(remainingPath);
         var10001 = this.wlCtx;
         ctxPath = var10000.append("/jndi/").append(jndiName).toString();
      }

      JMXServiceURL serviceURL = new JMXServiceURL(protocol, host, port, ctxPath);
      Hashtable h = new Hashtable();
      h.put("java.naming.security.principal", new String(this.wlCtx.username_bytes));
      h.put("java.naming.security.credentials", new String(this.wlCtx.password_bytes));
      if (this.wlCtx.idd_bytes != null) {
         h.put("weblogic.management.remote.identitydomain", new String(this.wlCtx.idd_bytes));
      }

      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      h.put("weblogic.jndi.createContextRetry.time", new Integer(this.wlCtx.retryAttempts));
      h.put("weblogic.jndi.createContextRetry.interval", new Long(this.wlCtx.retryDelay));
      if (this.wlCtx.connectTimeout > 0) {
         h.put("jmx.remote.x.request.waiting.timeout", new Long((long)this.wlCtx.connectTimeout));
      } else {
         h.put("jmx.remote.x.request.waiting.timeout", new Long(0L));
      }

      Locale locale = Locale.getDefault();
      if (!locale.getLanguage().equals(Locale.ENGLISH.getLanguage()) && !locale.getLanguage().equals(Locale.JAPANESE.getLanguage())) {
         h.put("weblogic.management.remote.locale", Locale.ENGLISH);
      }

      JMXConnector connector = JMXConnectorFactory.connect(serviceURL, h);
      this.wlCtx.jmxConnectors.add(connector);
      return connector.getMBeanServerConnection();
   }

   private String findPath(String url) {
      int lastColon = url.lastIndexOf(58);
      int slashFollowingPort = url.indexOf(47, lastColon);
      return slashFollowingPort != -1 ? url.substring(slashFollowingPort + 1, url.length()) : null;
   }

   private void addPeerMonitor(String serverName, Object remoteRef, int ver) {
      try {
         this.wlCtx.ep = RemoteHelper.getEndPoint(remoteRef);
         this.wlCtx.msMonitor = new ManagedServerMonitor(serverName, this.wlCtx.ep, this.wlCtx);
         this.wlCtx.msMonitor.initialize(serverName, this.wlCtx.ep, this.wlCtx);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private void setLoggingLevel() {
      WLScriptContext var10000 = this.wlCtx;
      Iterator logList = WLScriptContext.getLoggersList().iterator();

      while(logList.hasNext()) {
         String log = (String)logList.next();
         Logger logger = Logger.getLogger(log);
         loggers.add(logger);
         logger.setLevel(Level.OFF);
      }

   }
}
