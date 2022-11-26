package weblogic.nodemanager.server;

import com.bea.logging.LogFileConfigBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.Config;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.util.Platform;
import weblogic.nodemanager.util.ProcessControl;
import weblogic.nodemanager.util.ProcessControlFactory;

public class NMServerConfig extends Config {
   public static final boolean WEBLOGIC_SERVER_ENV = WLSProcessBuilder.isWebLogicServerEnv();
   public static final boolean COHERENCE_ENV = CoherenceProcessBuilder.isCoherenceEnv();
   private String nmHome = System.getProperty("user.dir");
   private String weblogicHome = System.getProperty("user.dir");
   private String listenAddress;
   private int listenPort = 5556;
   private int listenBacklog = 50;
   private boolean secureListener = true;
   private boolean nativeVersionEnabled = true;
   private boolean crashRecoveryEnabled = false;
   private String logFile;
   private int logLimit = 0;
   private int logCount = 1;
   private boolean logAppend = true;
   private boolean logToStderr = false;
   private Level logLevel;
   private Formatter logFormatter;
   private boolean authenticationEnabled;
   private boolean domainsFileEnabled;
   private File domainsFile;
   private long domainsFileModTime;
   private Map domainsMap;
   private ProcessControl processControl;
   private boolean startScriptEnabled;
   private boolean stopScriptEnabled;
   private String startScriptName;
   private String stopScriptName;
   private List wellKnownLocations;
   private long progressTrackerInitialDataTimeout;
   private boolean coherenceStartScriptEnabled;
   private String coherenceStartScriptName;
   private boolean quitEnabled;
   private int stateCheckInterval;
   private final List networkInfoList;
   private String ifConfigDir;
   private long execScriptTimeout;
   private boolean domainRegistrationEnabled;
   private boolean domainsDirRemoteSharingEnabled;
   private boolean useMACBroadcast;
   private LogFileConfigBean procFileRotationConfig;
   private long processDestroyTimeout;
   private String wlsStartupPrependArgs;
   private String wlsStartupArgs;
   private String wlsStartupClasspath;
   private String wlsStartupJavaVendor;
   private String wlsStartupSecurityPolicyFile;
   private String wlsStartupServerGID;
   private String wlsStartupServerUID;
   private String cohStartupJavaHome;
   private String cohStartupMWHome;
   private String wlsStartupMWHome;
   private String wlsStartupJavaHome;
   private String cohStartupArgs;
   private boolean isRestEnabled;
   private boolean isLogLockingEnabled;
   public static final String PROPERTIES_VERSION_PROP = "PropertiesVersion";
   public static final String NM_HOME_PROP = "NodeManagerHome";
   public static final String WEBLOGIC_HOME_PROP = "WeblogicHome";
   public static final String JAVA_HOME_PROP = "JavaHome";
   public static final String LISTEN_ADDRESS_PROP = "ListenAddress";
   public static final String LISTEN_PORT_PROP = "ListenPort";
   public static final String LISTEN_BACKLOG_PROP = "ListenBacklog";
   public static final String LISTENER_TYPE_PROP = "ListenerType";
   public static final String SECURE_LISTENER_PROP = "SecureListener";
   public static final String NATIVE_VERSION_ENABLED_PROP = "NativeVersionEnabled";
   public static final String CRASH_RECOVERY_ENABLED_PROP = "CrashRecoveryEnabled";
   public static final String LOG_LIMIT_PROP = "LogLimit";
   public static final String LOG_COUNT_PROP = "LogCount";
   public static final String LOG_FILE_PROP = "LogFile";
   public static final String LOG_APPEND_PROP = "LogAppend";
   public static final String LOG_TO_STDERR_PROP = "LogToStderr";
   public static final String LOG_LEVEL_PROP = "LogLevel";
   public static final String LOG_FORMATTER_PROP = "LogFormatter";
   public static final String AUTHENTICATION_ENABLED_PROP = "AuthenticationEnabled";
   public static final String KEY_PASSWORD_PROP = "keyPassword";
   public static final String DOMAINS_FILE_PROP = "DomainsFile";
   public static final String DOMAINS_FILE_ENABLED_PROP = "DomainsFileEnabled";
   public static final String DOMAIN_DIR_PROP = "DomainDir.";
   public static final String OLD_WEBLOGIC_START_SCRIPT_ENABLED_PROP = "StartScriptEnabled";
   public static final String WEBLOGIC_START_SCRIPT_ENABLED_PROP = "weblogic.StartScriptEnabled";
   public static final String OLD_WEBLOGIC_STOP_SCRIPT_ENABLED_PROP = "StopScriptEnabled";
   public static final String OLD_WEBLOGIC_START_SCRIPT_NAME_PROP = "StartScriptName";
   public static final String OLD_WEBLOGIC_STOP_SCRIPT_NAME_PROP = "StopScriptName";
   public static final String WEBLOGIC_STOP_SCRIPT_ENABLED_PROP = "weblogic.StopScriptEnabled";
   public static final String WEBLOGIC_START_SCRIPT_NAME_PROP = "weblogic.StartScriptName";
   public static final String WEBLOGIC_STOP_SCRIPT_NAME_PROP = "weblogic.StopScriptName";
   public static final String OLD_COHERENCE_START_SCRIPT_ENABLED_PROP = "CoherenceStartScriptEnabled";
   public static final String OLD_COHERENCE_START_SCRIPT_NAME_PROP = "CoherenceStartScriptName";
   public static final String COHERENCE_START_SCRIPT_ENABLED_PROP = "coherence.StartScriptEnabled";
   public static final String COHERENCE_START_SCRIPT_NAME_PROP = "coherence.StartScriptName";
   public static final String QUIT_ENABLED_PROP = "QuitEnabled";
   public static final String PROPERTIES_FILE_PROP = "PropertiesFile";
   public static final String STATE_CHECK_INTERVAL_PROP = "StateCheckInterval";
   public static final String IF_INTERFACE_NAME = "Interface";
   public static final String IF_NET_MASK_NAME = "NetMask";
   public static final String SCRIPT_TIMEOUT_PROP = "ScriptTimeout";
   public static final String OLD_IF_CONFIG_DIR_PROP = "IfConfigDir";
   public static final String OLD_USE_MAC_BROADCAST_PROP = "UseMACBroadcast";
   public static final String IF_CONFIG_DIR_PROP = "weblogic.IfConfigDir";
   public static final String USE_MAC_BROADCAST_PROP = "weblogic.UseMACBroadcast";
   public static final String DOMAIN_REGISTRATION_ENABLED_PROP = "DomainRegistrationEnabled";
   public static final String DOMAINDIRS_DIR_REMOTE_SHARING_ENABLED_PROP = "DomainsDirRemoteSharingEnabled";
   public static final String OLD_ROTATED_FILE_COUNT = "RotatedFileCount";
   public static final String OLD_FILE_SIZE_KB = "FileSizeKB";
   public static final String OLD_ROTATION_TYPE = "RotationType";
   public static final String OLD_FILE_TIME_SPAN = "FileTimeSpan";
   public static final String OLD_FILE_TIME_SPAN_FACTOR = "FileTimeSpanFactor";
   public static final String OLD_ROTATION_TIME = "RotationTimeStart";
   public static final String OLD_NUM_FILES_LIMITED = "NumberOfFilesLimited";
   public static final String ROTATED_FILE_COUNT = "process.RotatedFileCount";
   public static final String FILE_SIZE_KB = "process.FileSizeKB";
   public static final String ROTATION_TYPE = "process.RotationType";
   public static final String FILE_TIME_SPAN = "process.FileTimeSpan";
   public static final String FILE_TIME_SPAN_FACTOR = "process.FileTimeSpanFactor";
   public static final String ROTATION_TIME = "process.RotationTimeStart";
   public static final String NUM_FILES_LIMITED = "process.NumberOfFilesLimited";
   public static final String PROCESS_DESTROY_TIMEOUT = "ProcessDestroyTimeout";
   public static final String WLS_STARTUP_ARGS_ADDTIONAL = "weblogic.startup.Arguments.prepend";
   public static final String WLS_STARTUP_ARGS = "weblogic.startup.Arguments";
   public static final String WLS_STARTUP_JAVA_VENDOR = "weblogic.startup.JavaVendor";
   public static final String WLS_STARTUP_SECURITY_POLICY_FILE = "weblogic.startup.SecurityPolicyFile";
   public static final String WLS_STARTUP_CLASSPATH = "weblogic.startup.ClassPath";
   public static final String WLS_STARTUP_SERVER_UID = "weblogic.startup.ServerUID";
   public static final String WLS_STARTUP_SERVER_GID = "weblogic.startup.ServerGID";
   public static final String WLS_STARTUP_JAVA_HOME = "weblogic.startup.JavaHome";
   public static final String WLS_STARTUP_MW_HOME = "weblogic.startup.MW_Home";
   public static final String COH_STARTUP_JAVA_HOME = "coherence.startup.JavaHome";
   public static final String COH_STARTUP_MW_HOME = "coherence.startup.MW_Home";
   public static final String COH_STARTUP_ARGS = "coherence.startup.Arguments";
   public static final String PROGRESS_TRACKER_TIMEOUT_PROP = "ProgressTrackerInitialDataTimeout";
   public static final String IS_REST_ENABLED = "RestEnabled";
   public static final String IS_LOG_LOCKING_ENABLED = "isLogLockingEnabled";
   private static final String[] KNOWNPROPS = new String[]{"PropertiesVersion", "AuthenticationEnabled", "LogFile", "LogLimit", "LogCount", "LogAppend", "LogToStderr", "LogLevel", "LogFormatter", "ListenBacklog", "CrashRecoveryEnabled", "SecureListener", "CipherSuite", "weblogic.StartScriptEnabled", "weblogic.StartScriptName", "weblogic.StopScriptEnabled", "weblogic.StopScriptName", "coherence.StartScriptEnabled", "coherence.StartScriptName", "QuitEnabled", "RestartInterval", "RestartMax", "DomainsFile", "DomainsFileEnabled", "StateCheckInterval", "CustomIdentityAlias", "CustomIdentity", "JavaHome", "KeyStores", "ListenAddress", "ListenPort", "NativeVersionEnabled", "NodeManagerHome", "WeblogicHome", "keyFile", "keyPassword", "certificateFile", "NetMask", "Interface", "DomainsDirRemoteSharingEnabled", "CustomTrustKeyStorePassPhrase", "CustomIdentityKeyStoreType", "JavaStandardTrustKeyStorePassPhrase", "DomainRegistrationEnabled", "process.RotatedFileCount", "process.FileSizeKB", "process.RotationTimeStart", "process.RotationType", "process.FileTimeSpan", "process.FileTimeSpanFactor", "process.NumberOfFilesLimited", "weblogic.startup.Arguments.prepend", "CustomIdentityKey", "StoreFileName", "KeyStorePassPhrase", "KeyStoreType", "PrivateKeyPassPhrase", "JavaStandardTrustKey", "StorePassPhrase", "CustomTrustKeyStoreFileName", "CustomTrustKeyPassPhrase", "UseKSSForDemo", "ProcessDestroyTimeout", "StartScriptEnabled", "StopScriptEnabled", "StartScriptName", "StopScriptName", "CoherenceStartScriptEnabled", "CoherenceStartScriptName", "IfConfigDir", "UseMACBroadcast", "RotatedFileCount", "FileSizeKB", "RotationType", "FileTimeSpan", "FileTimeSpanFactor", "RotationTimeStart", "NumberOfFilesLimited", "weblogic.startup.Arguments", "weblogic.startup.ClassPath", "weblogic.startup.JavaVendor", "weblogic.startup.MW_Home", "weblogic.startup.SecurityPolicyFile", "weblogic.startup.ServerGID", "weblogic.startup.ServerUID", "weblogic.startup.JavaHome", "coherence.startup.Arguments", "coherence.startup.JavaHome", "coherence.startup.MW_Home", "ProgressTrackerInitialDataTimeout", "RestEnabled", "isLogLockingEnabled"};
   public static final int LISTEN_BACKLOG = 50;
   public static final String LOG_FILE_NAME = "nodemanager.log";
   public static final String DOMAINS_FILE_NAME = "nodemanager.domains";
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private static final String NETWORK_INFO_FORM = "Interface = IPRange,NetMask";

   public NMServerConfig(Properties props) throws IOException, ConfigException {
      super(props);
      this.logLevel = Level.INFO;
      this.logFormatter = new LogFormatter();
      this.authenticationEnabled = true;
      this.domainsFileEnabled = true;
      this.startScriptEnabled = true;
      this.stopScriptEnabled = false;
      this.startScriptName = Platform.isWindows() ? "startWebLogic.cmd" : "startWebLogic.sh";
      this.stopScriptName = null;
      this.progressTrackerInitialDataTimeout = 10000L;
      this.coherenceStartScriptEnabled = false;
      this.coherenceStartScriptName = null;
      this.quitEnabled = false;
      this.stateCheckInterval = 500;
      this.networkInfoList = new ArrayList();
      this.domainRegistrationEnabled = false;
      this.domainsDirRemoteSharingEnabled = false;
      this.useMACBroadcast = false;
      this.procFileRotationConfig = null;
      this.processDestroyTimeout = 20000L;
      this.wlsStartupPrependArgs = "";
      this.cohStartupJavaHome = System.getProperty("java.home");
      this.wlsStartupJavaHome = System.getProperty("java.home");
      this.isRestEnabled = false;
      this.isLogLockingEnabled = true;
      checkUpgrade(props, false);
      this.nmHome = this.getProperty("NodeManagerHome", this.nmHome);
      this.listenBacklog = this.getIntProperty("ListenBacklog", this.listenBacklog);
      this.listenAddress = this.getProperty("ListenAddress");
      this.secureListener = this.getBooleanProperty("SecureListener", this.secureListener);
      if (this.secureListener) {
         this.listenPort = this.getIntProperty("ListenPort", 5556);
      } else {
         this.listenPort = this.getIntProperty("ListenPort", 5556);
      }

      this.nativeVersionEnabled = this.getBooleanProperty("NativeVersionEnabled", this.nativeVersionEnabled);
      this.crashRecoveryEnabled = this.getBooleanProperty("CrashRecoveryEnabled", this.crashRecoveryEnabled);
      this.authenticationEnabled = this.getBooleanProperty("AuthenticationEnabled", this.authenticationEnabled);
      this.logFile = props.getProperty("LogFile");
      this.isLogLockingEnabled = this.getBooleanProperty("isLogLockingEnabled", true);
      if (this.logFile == null) {
         this.logFile = (new File(this.nmHome, "nodemanager.log")).getPath();
      }

      this.logLevel = this.getLevelProperty("LogLevel", this.logLevel);
      String s;
      if ((s = props.getProperty("LogFormatter")) != null) {
         this.logFormatter = this.loadFormatter(s);
      }

      this.logLimit = this.getIntProperty("LogLimit", this.logLimit);
      this.logCount = this.getIntProperty("LogCount", this.logCount);
      if (this.logCount <= 0) {
         throw new ConfigException(nmText.illegalLogCount(this.logCount));
      } else {
         this.logAppend = this.getBooleanProperty("LogAppend", this.logAppend);
         this.logToStderr = this.getBooleanProperty("LogToStderr", this.logToStderr);
         this.domainsFileEnabled = this.getBooleanProperty("DomainsFileEnabled", this.domainsFileEnabled);
         if ((s = this.getProperty("DomainsFile")) != null) {
            this.domainsFile = new File(s);
         } else {
            this.domainsFile = new File(this.nmHome, "nodemanager.domains");
         }

         this.initLogger(nmLog);
         this.initDomainsMap();
         if (this.nativeVersionEnabled) {
            this.initProcessControl();
         }

         this.isRestEnabled = this.getBooleanProperty("RestEnabled");
         this.startScriptEnabled = this.getDeprecatedBooleanProperty("StartScriptEnabled", "weblogic.StartScriptEnabled", this.startScriptEnabled);
         this.wlsStartupPrependArgs = this.getProperty("weblogic.startup.Arguments.prepend", this.wlsStartupPrependArgs);
         this.stopScriptEnabled = this.getDeprecatedBooleanProperty("StopScriptEnabled", "weblogic.StopScriptEnabled", this.stopScriptEnabled);
         this.startScriptName = this.getDeprecatedProperty("StartScriptName", "weblogic.StartScriptName", this.startScriptName);
         this.stopScriptName = this.getDeprecatedProperty("StopScriptName", "weblogic.StopScriptName", this.stopScriptName);
         this.coherenceStartScriptEnabled = this.getDeprecatedBooleanProperty("CoherenceStartScriptEnabled", "coherence.StartScriptEnabled", this.coherenceStartScriptEnabled);
         this.coherenceStartScriptName = this.getDeprecatedProperty("CoherenceStartScriptName", "coherence.StartScriptName", this.coherenceStartScriptName);
         if (this.coherenceStartScriptEnabled && this.coherenceStartScriptName == null) {
            throw new ConfigException(nmText.msgCoherenceStartupScriptNotSpecified());
         } else {
            this.quitEnabled = this.getBooleanProperty("QuitEnabled", this.quitEnabled);
            this.ifConfigDir = this.getDeprecatedProperty("IfConfigDir", "weblogic.IfConfigDir");
            this.execScriptTimeout = this.getLongProperty("ScriptTimeout", 60000L);
            this.useMACBroadcast = this.getDeprecatedBooleanProperty("UseMACBroadcast", "weblogic.UseMACBroadcast", this.useMACBroadcast);
            if (this.getProperty("DomainRegistrationEnabled") != null) {
               nmLog.warning(nmText.domainRegistrationPropDeprecated("DomainRegistrationEnabled"));
            }

            this.domainRegistrationEnabled = this.getBooleanProperty("DomainRegistrationEnabled", this.domainRegistrationEnabled);
            this.domainsDirRemoteSharingEnabled = this.getBooleanProperty("DomainsDirRemoteSharingEnabled", this.domainsDirRemoteSharingEnabled);
            this.stateCheckInterval = this.getIntProperty("StateCheckInterval", this.stateCheckInterval);
            this.progressTrackerInitialDataTimeout = this.getLongProperty("ProgressTrackerInitialDataTimeout", this.progressTrackerInitialDataTimeout);
            this.checkWLSLogRotationProps();
            this.procFileRotationConfig = new LogFileConfigBean();
            if (this.nativeVersionEnabled) {
               this.checkInvalidPropForNativeVersion("process.NumberOfFilesLimited");
               this.checkInvalidPropForNativeVersion("process.RotatedFileCount");
               this.checkInvalidPropForNativeVersion("process.FileSizeKB");
               this.checkInvalidPropForNativeVersion("process.RotationType");
               this.checkInvalidPropForNativeVersion("process.RotationTimeStart");
               this.checkInvalidPropForNativeVersion("process.FileTimeSpan");
               this.checkInvalidPropForNativeVersion("process.FileTimeSpanFactor");
            } else {
               this.procFileRotationConfig.setRotateLogOnStartupEnabled(false);
               this.procFileRotationConfig.setNumberOfFilesLimited(this.getBooleanProperty("process.NumberOfFilesLimited", this.procFileRotationConfig.isNumberOfFilesLimited()));
               this.procFileRotationConfig.setRotatedFileCount(this.getIntProperty("process.RotatedFileCount", this.procFileRotationConfig.getRotatedFileCount()));
               this.procFileRotationConfig.setRotationSize(this.getIntProperty("process.FileSizeKB", this.procFileRotationConfig.getRotationSize()));
               this.procFileRotationConfig.setRotationType(this.getProperty("process.RotationType", this.procFileRotationConfig.getRotationType()));
               this.procFileRotationConfig.setRotationTime(this.getProperty("process.RotationTimeStart", this.procFileRotationConfig.getRotationTime()));
               this.procFileRotationConfig.setRotationTimeSpan(this.getIntProperty("process.FileTimeSpan", this.procFileRotationConfig.getRotationTimeSpan()));
               this.procFileRotationConfig.setRotationTimeSpanFactor(this.getLongProperty("process.FileTimeSpanFactor", this.procFileRotationConfig.getRotationTimeSpanFactor()));
            }

            this.processDestroyTimeout = this.getLongProperty("ProcessDestroyTimeout", this.processDestroyTimeout);
            this.wlsStartupArgs = this.getProperty("weblogic.startup.Arguments");
            this.wlsStartupClasspath = this.getProperty("weblogic.startup.ClassPath");
            this.wlsStartupJavaVendor = this.getProperty("weblogic.startup.JavaVendor");
            this.wlsStartupSecurityPolicyFile = this.getProperty("weblogic.startup.SecurityPolicyFile");
            this.wlsStartupServerGID = this.getProperty("weblogic.startup.ServerGID");
            this.wlsStartupServerUID = this.getProperty("weblogic.startup.ServerUID");
            this.wlsStartupJavaHome = this.getDeprecatedProperty("JavaHome", "weblogic.startup.JavaHome", this.wlsStartupJavaHome);
            this.wlsStartupMWHome = this.getProperty("weblogic.startup.MW_Home");
            this.cohStartupArgs = this.getProperty("coherence.startup.Arguments");
            this.cohStartupJavaHome = this.getDeprecatedProperty("JavaHome", "coherence.startup.JavaHome", this.cohStartupJavaHome);
            this.cohStartupMWHome = this.getProperty("coherence.startup.MW_Home");
            if (!COHERENCE_ENV) {
               this.checkInvalidPropForEnv("coherence.StartScriptEnabled");
               this.checkInvalidPropForEnv("coherence.StartScriptName");
               this.checkInvalidPropForEnv("coherence.startup.Arguments");
               this.checkInvalidPropForEnv("coherence.startup.JavaHome");
               this.checkInvalidPropForEnv("coherence.startup.MW_Home");
               this.checkInvalidPropForEnv("CoherenceStartScriptEnabled");
               this.checkInvalidPropForEnv("CoherenceStartScriptName");
            }

            if (!WEBLOGIC_SERVER_ENV) {
               this.checkInvalidPropForEnv("weblogic.StartScriptEnabled");
               this.checkInvalidPropForEnv("weblogic.StopScriptEnabled");
               this.checkInvalidPropForEnv("weblogic.StartScriptName");
               this.checkInvalidPropForEnv("weblogic.StopScriptName");
               this.checkInvalidPropForEnv("weblogic.IfConfigDir");
               this.checkInvalidPropForEnv("weblogic.UseMACBroadcast");
               this.checkInvalidPropForEnv("StartScriptEnabled");
               this.checkInvalidPropForEnv("StopScriptEnabled");
               this.checkInvalidPropForEnv("StartScriptName");
               this.checkInvalidPropForEnv("StopScriptName");
               this.checkInvalidPropForEnv("IfConfigDir");
               this.checkInvalidPropForEnv("UseMACBroadcast");
               this.checkInvalidPropForEnv("Interface");
               this.checkInvalidPropForEnv("NetMask");
               this.checkInvalidPropForEnv("weblogic.startup.Arguments");
               this.checkInvalidPropForEnv("weblogic.startup.Arguments.prepend");
               this.checkInvalidPropForEnv("weblogic.startup.ClassPath");
               this.checkInvalidPropForEnv("weblogic.startup.JavaVendor");
               this.checkInvalidPropForEnv("weblogic.startup.SecurityPolicyFile");
               this.checkInvalidPropForEnv("weblogic.startup.ServerGID");
               this.checkInvalidPropForEnv("weblogic.startup.ServerUID");
               this.checkInvalidPropForEnv("weblogic.startup.JavaHome");
               this.checkInvalidPropForEnv("weblogic.startup.MW_Home");
            }

            this.wellKnownLocations = new ArrayList();
            this.wellKnownLocations.add("service_migration");
            this.wellKnownLocations.add("patching");
         }
      }
   }

   private void checkWLSLogRotationProps() {
      this.warnDeprecatedLogRotationProp("NumberOfFilesLimited");
      this.warnDeprecatedLogRotationProp("RotatedFileCount");
      this.warnDeprecatedLogRotationProp("FileSizeKB");
      this.warnDeprecatedLogRotationProp("RotationType");
      this.warnDeprecatedLogRotationProp("RotationTimeStart");
      this.warnDeprecatedLogRotationProp("FileTimeSpan");
      this.warnDeprecatedLogRotationProp("FileTimeSpanFactor");
   }

   private void warnDeprecatedLogRotationProp(String deprecatedProp) {
      String deprecatedValue = this.getProperty(deprecatedProp);
      if (deprecatedValue != null) {
         nmLog.warning(nmText.rotationPropertyDeprecated(deprecatedProp));
      }

   }

   private String getDeprecatedProperty(String deprecatedProp, String replacementProp, String defaultVal) throws ConfigException {
      String val = this.getDeprecatedProperty(deprecatedProp, replacementProp);
      return val == null ? defaultVal : val;
   }

   private String getDeprecatedProperty(String deprecatedProp, String replacementProp) throws ConfigException {
      String deprecatedValue = this.getProperty(deprecatedProp);
      String value = this.getProperty(replacementProp);
      if (deprecatedValue != null) {
         if (value != null && !value.equals(deprecatedValue)) {
            throw new ConfigException(nmText.cannotSpecifyBoth(deprecatedProp, replacementProp));
         } else {
            return deprecatedValue;
         }
      } else {
         return value;
      }
   }

   private boolean getDeprecatedBooleanProperty(String deprecatedProp, String replacementProp, boolean defaultValue) throws ConfigException {
      String val = this.getDeprecatedProperty(deprecatedProp, replacementProp);
      return val == null ? defaultValue : Boolean.parseBoolean(val);
   }

   private void checkInvalidPropForEnv(String propName) {
      if (this.getProperty(propName) != null) {
      }

   }

   private void checkInvalidPropForNativeVersion(String propName) {
      if (this.getProperty(propName) != null) {
         nmLog.warning(nmText.propertyNotAppliedWithNativeVersion(propName));
      }

   }

   private Level getLevelProperty(String name, Level defaultValue) throws ConfigException {
      String s = this.getProperty(name);
      if (s != null) {
         try {
            return Level.parse(s);
         } catch (IllegalArgumentException var5) {
            throw new ConfigException(nmText.getInvalidLogLevel(s, name));
         }
      } else {
         return defaultValue;
      }
   }

   private Formatter loadFormatter(String name) throws ConfigException {
      try {
         Object obj = Class.forName(name).newInstance();
         return (Formatter)obj;
      } catch (Throwable var3) {
         throw new ConfigException(nmText.getLogFormatterError(name), var3);
      }
   }

   private void initProcessControl() throws ConfigException {
      try {
         this.processControl = ProcessControlFactory.getProcessControl();
      } catch (UnsatisfiedLinkError var2) {
         throw new ConfigException(nmText.getNativeLibraryLoadError(), var2);
      }

      if (this.processControl == null) {
         throw new ConfigException(nmText.getNativeLibraryNA());
      }
   }

   public static boolean checkUpgrade(Properties props, boolean log) throws ConfigException {
      boolean changed = false;
      String s;
      if ((s = props.getProperty("ListenerType")) != null) {
         props.remove("ListenerType");
         if (log) {
            Upgrader.log(Level.INFO, nmText.getRemovingProp("ListenerType"));
         }

         changed = true;
         if (!props.contains("SecureListener")) {
            if (s.equalsIgnoreCase("plainSocket")) {
               if (log) {
                  Upgrader.log(Level.INFO, nmText.getAddingProp("SecureListener", "false"));
               }

               props.setProperty("SecureListener", "false");
            } else {
               if (!s.equalsIgnoreCase("secureSocket")) {
                  throw new ConfigException(nmText.getInvalidPropValue("ListenerType", s));
               }

               if (log) {
                  Upgrader.log(Level.INFO, nmText.getAddingProp("SecureListener", "true"));
               }

               props.setProperty("SecureListener", "true");
            }
         }
      }

      s = props.getProperty("PropertiesVersion");
      String nmVersion = "14.1.1.0.0";

      try {
         nmVersion = NMServer.getWLSVersion();
      } catch (Throwable var6) {
      }

      if (!nmVersion.equals(s)) {
         props.setProperty("PropertiesVersion", nmVersion);
         if (log) {
            Upgrader.log(Level.INFO, nmText.getSettingVersion(nmVersion));
         }

         changed = true;
      }

      return changed;
   }

   public Map getDomainsMap() throws ConfigException {
      if (this.domainsFileEnabled) {
         long mtime = this.domainsFile.lastModified();
         if (mtime != this.domainsFileModTime) {
            NMProperties map = new NMProperties();
            nmLog.info(nmText.getReloadingDomainsFile(this.domainsFile.toString()));

            try {
               map.load(this.domainsFile);
               this.domainsFileModTime = mtime;
            } catch (IllegalArgumentException var5) {
               nmLog.warning(nmText.getInvalidDomainsFile(this.domainsFile.toString()));
            } catch (FileNotFoundException var6) {
               nmLog.warning(nmText.getDomainsFileNotFound(this.domainsFile.toString()));
            } catch (IOException var7) {
               nmLog.log(Level.WARNING, nmText.getErrorReadingDomainsFile(this.domainsFile.toString()), var7);
            }

            this.loadDomainsProps(this.props, map);
            this.domainsMap = map;
            this.printDomainsMap(new PrintWriter(new OutputStreamWriter(System.err), true));
         }
      }

      return this.domainsMap;
   }

   private void initDomainsMap() throws ConfigException {
      NMProperties map = new NMProperties();
      nmLog.info(nmText.getLoadingDomainsFile(this.domainsFile.toString()));

      String msg;
      try {
         long mtime = this.domainsFile.lastModified();
         map.load(this.domainsFile);
         this.domainsFileModTime = mtime;
      } catch (IllegalArgumentException var4) {
         msg = nmText.getInvalidDomainsFile(this.domainsFile.toString());
         nmLog.severe(msg);
         throw new ConfigException(msg);
      } catch (FileNotFoundException var5) {
         nmLog.warning(nmText.getDomainsFileNotFound(this.domainsFile.toString()));
      } catch (IOException var6) {
         msg = nmText.getErrorReadingDomainsFile(this.domainsFile.toString());
         nmLog.log(Level.SEVERE, msg, var6);
         throw new ConfigException(msg, var6);
      }

      this.loadDomainsProps(this.props, map);
      this.domainsMap = map;
   }

   private void loadDomainsProps(Properties props, Map domains) {
      Iterator it = props.keySet().iterator();

      while(it.hasNext()) {
         String name = (String)it.next();
         if (name.startsWith("DomainDir.")) {
            String path = props.getProperty(name);
            domains.put(name.substring("DomainDir.".length()), path);
         }
      }

   }

   public String getNMHome() {
      return this.nmHome;
   }

   public String getWeblogicHome() {
      return this.weblogicHome;
   }

   public int getListenPort() {
      return this.listenPort;
   }

   public int getListenBacklog() {
      return this.listenBacklog;
   }

   public String getListenAddress() {
      return this.listenAddress;
   }

   public boolean isSecureListener() {
      return this.secureListener;
   }

   public boolean isNativeVersionEnabled() {
      return this.nativeVersionEnabled;
   }

   public boolean isCrashRecoveryEnabled() {
      return this.crashRecoveryEnabled;
   }

   public boolean isAuthenticationEnabled() {
      return this.authenticationEnabled;
   }

   public boolean isStartScriptEnabled() {
      return this.startScriptEnabled;
   }

   public boolean isStopScriptEnabled() {
      return this.stopScriptEnabled;
   }

   public String getStartScriptName() {
      return this.startScriptName;
   }

   public String getStopScriptName() {
      return this.stopScriptName;
   }

   public boolean isCoherenceStartScriptEnabled() {
      return this.coherenceStartScriptEnabled;
   }

   public String getCoherenceStartScriptName() {
      return this.coherenceStartScriptName;
   }

   public String getLogFile() {
      return this.logFile;
   }

   public Formatter getLogFormatter() {
      return this.logFormatter;
   }

   public ProcessControl getProcessControl() {
      return this.processControl;
   }

   public boolean getQuitEnabled() {
      return this.quitEnabled;
   }

   public int getStateCheckInterval() {
      return this.stateCheckInterval;
   }

   public long getProgressTrackerInitialDataTimeout() {
      return this.progressTrackerInitialDataTimeout;
   }

   public NetworkInfo getNetworkInfoFor(String ipAddress) throws IOException {
      InetAddress ip = InetAddress.getByName(ipAddress);
      Iterator var3 = this.getNetworkInfoList().iterator();

      NetworkInfo netInfo;
      do {
         if (!var3.hasNext()) {
            throw new InvalidPropertiesFormatException("Missing an appropriate entry for " + ipAddress);
         }

         netInfo = (NetworkInfo)var3.next();
      } while(!netInfo.isNetworkInfoFor(ip));

      return netInfo;
   }

   private List getNetworkInfoList() throws IOException {
      if (this.networkInfoList.isEmpty()) {
         this.initNetworkInfoList();
      }

      return this.networkInfoList;
   }

   private void initNetworkInfoList() throws IOException {
      List knownProps = Arrays.asList(KNOWNPROPS);
      Iterator iter = this.props.keySet().iterator();

      String netMask;
      while(iter.hasNext()) {
         netMask = (String)iter.next();
         if (!knownProps.contains(netMask)) {
            try {
               this.networkInfoList.add(NetworkInfo.convertConfEntry(netMask, this.props.getProperty(netMask)));
            } catch (IOException var5) {
            }
         }
      }

      String interfaceName = this.getProperty("Interface");
      if (interfaceName != null) {
         nmLog.warning(nmText.propertyDeprecated("Interface", "Interface = IPRange,NetMask"));
      }

      netMask = this.getProperty("NetMask");
      if (netMask != null) {
         nmLog.warning(nmText.propertyDeprecated("NetMask", "Interface = IPRange,NetMask"));
      }

      if (interfaceName != null) {
         if (netMask == null && Platform.isWindows()) {
            throw new InvalidPropertiesFormatException(nmText.missingNetMaskProp(interfaceName));
         }

         this.networkInfoList.add(new NetworkInfo(interfaceName, netMask));
      }

      if (this.networkInfoList.isEmpty()) {
         throw new InvalidPropertiesFormatException(nmText.missingSrvrMigProp());
      }
   }

   private String valueOfProp(String prop) {
      return prop != null ? prop : "";
   }

   public NMProperties getConfigProperties() {
      NMProperties props = new NMProperties();
      props.clear();
      props.setProperty("ListenAddress", this.valueOfProp(this.listenAddress));
      props.setProperty("ListenPort", String.valueOf(this.listenPort));
      props.setProperty("ListenBacklog", String.valueOf(this.listenBacklog));
      props.setProperty("SecureListener", String.valueOf(this.secureListener));
      props.setProperty("AuthenticationEnabled", String.valueOf(this.authenticationEnabled));
      props.setProperty("NativeVersionEnabled", String.valueOf(this.nativeVersionEnabled));
      props.setProperty("CrashRecoveryEnabled", String.valueOf(this.crashRecoveryEnabled));
      props.setProperty("LogFile", this.valueOfProp(this.logFile));
      props.setProperty("LogLevel", String.valueOf(this.logLevel));
      props.setProperty("LogLimit", String.valueOf(this.logLimit));
      props.setProperty("LogCount", String.valueOf(this.logCount));
      props.setProperty("LogAppend", String.valueOf(this.logAppend));
      props.setProperty("LogToStderr", String.valueOf(this.logToStderr));
      props.setProperty("LogFormatter", this.logFormatter.getClass().getName());
      props.setProperty("DomainsFile", String.valueOf(this.domainsFile));
      props.setProperty("DomainsFileEnabled", String.valueOf(this.domainsFileEnabled));
      if (this.quitEnabled) {
         props.setProperty("QuitEnabled", String.valueOf(this.quitEnabled));
      }

      props.setProperty("StateCheckInterval", String.valueOf(this.stateCheckInterval));
      props.setProperty("ProcessDestroyTimeout", String.valueOf(this.processDestroyTimeout));

      try {
         Iterator var2 = this.getNetworkInfoList().iterator();

         while(var2.hasNext()) {
            NetworkInfo netInfo = (NetworkInfo)var2.next();
            props.setProperty(netInfo.getInterfaceName(), netInfo.getPropertyValueString());
         }
      } catch (IOException var4) {
      }

      if (this.domainRegistrationEnabled) {
         props.setProperty("DomainRegistrationEnabled", String.valueOf(this.domainRegistrationEnabled));
      }

      props.setProperty("DomainsDirRemoteSharingEnabled", String.valueOf(this.domainsDirRemoteSharingEnabled));
      if (COHERENCE_ENV) {
         props.setProperty("coherence.StartScriptEnabled", String.valueOf(this.coherenceStartScriptEnabled));
         if (this.coherenceStartScriptName != null) {
            props.setProperty("coherence.StartScriptName", this.valueOfProp(this.coherenceStartScriptName));
         }

         if (this.cohStartupArgs != null) {
            props.setProperty("coherence.startup.Arguments", this.valueOfProp(this.cohStartupArgs));
         }
      }

      if (WEBLOGIC_SERVER_ENV) {
         props.setProperty("weblogic.StartScriptEnabled", String.valueOf(this.startScriptEnabled));
         props.setProperty("weblogic.StopScriptEnabled", String.valueOf(this.stopScriptEnabled));
         props.setProperty("weblogic.StartScriptName", this.valueOfProp(this.startScriptName));
         if (this.stopScriptName != null) {
            props.setProperty("weblogic.StopScriptName", this.valueOfProp(this.stopScriptName));
         }

         if (this.wlsStartupArgs != null) {
            props.setProperty("weblogic.startup.Arguments", this.valueOfProp(this.wlsStartupArgs));
         }

         if (this.wlsStartupPrependArgs != null) {
            props.setProperty("weblogic.startup.Arguments.prepend", this.valueOfProp(this.wlsStartupPrependArgs));
         }

         if (this.wlsStartupClasspath != null) {
            props.setProperty("weblogic.startup.ClassPath", this.valueOfProp(this.wlsStartupClasspath));
         }

         if (this.wlsStartupJavaVendor != null) {
            props.setProperty("weblogic.startup.JavaVendor", this.valueOfProp(this.wlsStartupJavaVendor));
         }
      }

      return props;
   }

   public void print(PrintStream ps) {
      this.print(new PrintWriter(new OutputStreamWriter(ps), true));
   }

   public void print(PrintWriter pw) {
      NMProperties nmProps = this.getConfigProperties();
      pw.println("Configuration settings:");
      pw.println();
      Iterator var3 = nmProps.stringPropertyNames().iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();
         pw.println(name + "=" + nmProps.get(name));
      }

      pw.println("NodeManagerHome=" + this.valueOfProp(this.nmHome));
      pw.println("RestEnabled=" + this.isRestEnabled);
      pw.println("isLogLockingEnabled=" + this.isLogLockingEnabled);
      pw.println("weblogic.startup.JavaHome=" + this.valueOfProp(this.wlsStartupJavaHome));
      pw.println("weblogic.startup.MW_Home=" + this.valueOfProp(this.wlsStartupMWHome));
      pw.println("coherence.startup.JavaHome=" + this.valueOfProp(this.cohStartupJavaHome));
      pw.println("coherence.startup.MW_Home=" + this.valueOfProp(this.cohStartupMWHome));
      pw.println();
      this.printDomainsMap(pw);
   }

   public void printDomainsMap(PrintWriter pw) {
      pw.println("Domain name mappings:");
      pw.println();
      Iterator it = this.domainsMap.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry e = (Map.Entry)it.next();
         pw.println(e.getKey() + " -> " + e.getValue());
      }

      pw.println();
   }

   public void initLogger(Logger logger) throws IOException {
      Level curLevel = logger.getLevel();
      if (curLevel != null && curLevel.equals(this.logLevel) && logger.getHandlers().length > 0) {
         if (Level.ALL.equals(nmLog.getLevel())) {
            nmLog.info("Logger is already initialized");
         }

      } else {
         logger.setLevel(this.logLevel);
         if (this.isLogLockingEnabled) {
            FileHandler fh;
            try {
               fh = new FileHandler(this.logFile, this.logLimit, this.logCount, this.logAppend);
            } catch (NullPointerException var6) {
               var6.printStackTrace();
               throw var6;
            }

            fh.setFormatter(this.logFormatter);
            fh.setLevel(this.logLevel);
            logger.addHandler(fh);
         } else {
            NoLockFileHandler fh;
            try {
               fh = new NoLockFileHandler(this.logFile, this.logLimit, this.logCount, this.logAppend);
            } catch (NullPointerException var5) {
               var5.printStackTrace();
               throw var5;
            }

            fh.setFormatter(this.logFormatter);
            fh.setLevel(this.logLevel);
            logger.addHandler(fh);
         }

         if (this.logToStderr) {
            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(this.logFormatter);
            ch.setLevel(this.logLevel);
            logger.addHandler(ch);
         }

         logger.setUseParentHandlers(false);
      }
   }

   public boolean isRestEnabled() {
      return this.isRestEnabled;
   }

   public boolean isLogLockingEnabled() {
      return this.isLogLockingEnabled;
   }

   public String getIfConfigDir() {
      return this.ifConfigDir;
   }

   public long getExecScriptTimeout() {
      return this.execScriptTimeout;
   }

   public boolean useMACBroadcast() {
      return this.useMACBroadcast;
   }

   public boolean isDomainRegistrationEnabled() {
      return this.domainRegistrationEnabled;
   }

   public boolean isDomainsDirRemoteSharingEnabled() {
      return this.domainsDirRemoteSharingEnabled;
   }

   public LogFileConfigBean getProcFileRotationConfig() {
      return this.procFileRotationConfig;
   }

   public long getProcessDestroyTimeout() {
      return this.processDestroyTimeout;
   }

   public String getWlsStartupPrependArgs() {
      return this.wlsStartupPrependArgs;
   }

   public String getWlsStartupServerUID() {
      return this.wlsStartupServerUID;
   }

   public String getWlsStartupServerGID() {
      return this.wlsStartupServerGID;
   }

   public String getWlsStartupSecurityPolicyFile() {
      return this.wlsStartupSecurityPolicyFile;
   }

   public String getWlsStartupMWHome() {
      return this.wlsStartupMWHome;
   }

   public String getWlsStartupJavaVendor() {
      return this.wlsStartupJavaVendor;
   }

   public String getWlsStartupClasspath() {
      return this.wlsStartupClasspath;
   }

   public String getWlsStartupArgs() {
      return this.wlsStartupArgs;
   }

   public String getWlsStartupJavaHome() {
      return this.wlsStartupJavaHome;
   }

   public String getCohStartupArgs() {
      return this.cohStartupArgs;
   }

   public String getCohStartupJavaHome() {
      return this.cohStartupJavaHome;
   }

   public String getCohStartupMWHome() {
      return this.cohStartupMWHome;
   }

   public List getWellKnownScriptLocations() {
      return this.wellKnownLocations;
   }
}
