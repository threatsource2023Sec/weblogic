package weblogic.nodemanager.common;

import com.bea.logging.LogFileConfigBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

public class StartupConfig extends Config {
   private final String javaVendor;
   private final String javaHome;
   private final String mwHome;
   private final String classPath;
   private final String securityPolicyFile;
   private final String arguments;
   private final String sslArguments;
   private final String adminURL;
   private final String username;
   private final String password;
   private final boolean autoRestart;
   private final int restartMax;
   private final int restartDelaySeconds;
   private final int restartInterval;
   private final String serverIPs;
   private final transient List serverIPList;
   private final String serverUid;
   private final String serverGid;
   private final String trustKeyStore;
   private final String customTrustKeyStoreFileName;
   private final String customTrustKeyStoreType;
   private final String customTrustKeyStorePassPhrase;
   private final String javaStandardTrustKeyStorePassPhrase;
   private final String serverOutFile;
   private final String serverErrFile;
   private final String transientScriptEnv;
   private final String transientServerArgs;
   private final String nmHostName;
   private LogFileConfigBean logFileRotationConfig = null;
   static final boolean DEFAULT_AUTO_RESTART = true;
   static final boolean DEFAULT_AUTO_KILL_IF_FAILED = false;
   static final int DEFAULT_RESTAET_MAX = 2;
   static final int DEFAULT_RESTART_DELAY_SECONDS = 0;
   static final int DEFAULT_RESTART_INTERVAL = 0;
   public static final String JAVA_VENDOR_PROP = "JavaVendor";
   public static final String JAVA_HOME_PROP = "JavaHome";
   public static final String ARGUMENTS_PROP = "Arguments";
   public static final String SSL_ARGUMENTS_PROP = "SSLArguments";
   public static final String SECURITY_POLICY_FILE_PROP = "SecurityPolicyFile";
   public static final String CLASS_PATH_PROP = "ClassPath";
   public static final String MW_HOME_PROP = "MWHome";
   public static final String BEA_HOME_PROP = "BeaHome";
   public static final String ADMIN_URL_PROP = "AdminURL";
   public static final String AUTO_RESTART_PROP = "AutoRestart";
   public static final String RESTART_MAX_PROP = "RestartMax";
   public static final String RESTART_INTERVAL_PROP = "RestartInterval";
   public static final String RESTART_DELAY_SECONDS_PROP = "RestartDelaySeconds";
   public static final String USERNAME_PROP = "username";
   public static final String PASSWORD_PROP = "password";
   public static final String SERVER_IP_PROP = "ServerIP";
   public static final String SERVER_UID_PROP = "ServerUID";
   public static final String SERVER_GID_PROP = "ServerGID";
   public static final String TRANSIENT_SCRIPT_ENV = "TransientScriptEnv";
   public static final String TRANSIENT_SERVER_ARGS_PROP = "TransientServerArgs";
   public static final String NM_HOSTNAME_PROP = "NMHostName";
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
   public static final String RESTART_ONCE = "RestartOnce";
   public static final String ROTATE_LOG_ON_STARTUP = "RotateLogOnStartup";
   public static final String SERVER_CMDLINE_STDOUT_PROPERTY = "-Dweblogic.Stdout=";
   public static final String SERVER_CMDLINE_STDERR_PROPERTY = "-Dweblogic.Stderr=";
   public static final String SERVER_CMDLINE_MGMT_SERVER_PROPERTY = "-Dweblogic.managerment.server=";

   public StartupConfig(Properties props) throws ConfigException {
      super(props);
      this.javaVendor = this.getProperty("JavaVendor");
      this.javaHome = this.getProperty("JavaHome");
      this.arguments = this.getProperty("Arguments");
      this.sslArguments = this.getProperty("SSLArguments");
      this.securityPolicyFile = this.getProperty("SecurityPolicyFile");
      this.classPath = this.getProperty("ClassPath");
      this.mwHome = this.getProperty("MWHome", this.getProperty("BeaHome"));
      String mgmtServer = this.getMgmtServerFromArguments();
      this.adminURL = mgmtServer == null ? this.getProperty("AdminURL") : mgmtServer;
      String tempUsername = this.getProperty("username");
      if (tempUsername == null) {
         tempUsername = this.getProperty("Username");
      }

      this.username = tempUsername;
      String tempPassword = this.getProperty("password");
      if (tempPassword == null) {
         tempPassword = this.getProperty("Password");
      }

      this.password = tempPassword;
      this.autoRestart = this.getBooleanProperty("AutoRestart", true);
      this.serverIPs = this.getProperty("ServerIP");
      this.serverIPList = this.serverIPs == null ? null : getIPListFromString(this.serverIPs);
      this.restartMax = this.getIntProperty("RestartMax", 2);
      this.restartInterval = this.getIntProperty("RestartInterval", 0);
      this.restartDelaySeconds = this.getIntProperty("RestartDelaySeconds", 0);
      this.serverUid = this.getProperty("ServerUID");
      this.serverGid = this.getProperty("ServerGID");
      this.nmHostName = this.getProperty("NMHostName");
      this.trustKeyStore = this.getProperty("TrustKeyStore");
      this.customTrustKeyStoreFileName = this.getProperty("CustomTrustKeyStoreFileName");
      this.customTrustKeyStoreType = this.getProperty("CustomTrustKeyStoreType");
      this.customTrustKeyStorePassPhrase = this.getProperty("CustomTrustKeyStorePassPhrase");
      this.javaStandardTrustKeyStorePassPhrase = this.getProperty("JavaStandardTrustKeyStorePassPhrase");
      this.transientScriptEnv = this.getProperty("TransientScriptEnv");
      if (this.transientScriptEnv != null) {
         props.remove("TransientScriptEnv");
      }

      this.transientServerArgs = this.getProperty("TransientServerArgs");
      if (this.transientServerArgs != null) {
         props.remove("TransientServerArgs");
      }

      this.serverOutFile = this.getServerOutFileFromArguments();
      this.serverErrFile = this.getServerErrFileFromArguments();
      this.logFileRotationConfig = new LogFileConfigBean();
      if (props.containsKey("RotatedFileCount") || props.containsKey("FileSizeKB") || props.containsKey("RotationType") || props.containsKey("RotationTimeStart") || props.containsKey("FileTimeSpan") || props.containsKey("FileTimeSpanFactor") || props.containsKey("NumberOfFilesLimited")) {
         this.logFileRotationConfig.setRotatedFileCount(this.getIntProperty("RotatedFileCount", this.logFileRotationConfig.getRotatedFileCount()));
         this.logFileRotationConfig.setRotationSize(this.getIntProperty("FileSizeKB", this.logFileRotationConfig.getRotationSize()));
         this.logFileRotationConfig.setRotationType(this.getProperty("RotationType", this.logFileRotationConfig.getRotationType()));
         this.logFileRotationConfig.setRotationTime(this.getProperty("RotationTimeStart", this.logFileRotationConfig.getRotationTime()));
         this.logFileRotationConfig.setRotationTimeSpan(this.getIntProperty("FileTimeSpan", this.logFileRotationConfig.getRotationTimeSpan()));
         this.logFileRotationConfig.setRotationTimeSpanFactor(this.getLongProperty("FileTimeSpanFactor", this.logFileRotationConfig.getRotationTimeSpanFactor()));
         this.logFileRotationConfig.setNumberOfFilesLimited(this.getBooleanProperty("NumberOfFilesLimited", this.logFileRotationConfig.isNumberOfFilesLimited()));
      }

      if (props.containsKey("RotateLogOnStartup") || props.containsKey("process.RotatedFileCount") || props.containsKey("process.FileSizeKB") || props.containsKey("process.RotationType") || props.containsKey("process.RotationTimeStart") || props.containsKey("process.FileTimeSpan") || props.containsKey("process.FileTimeSpanFactor") || props.containsKey("process.NumberOfFilesLimited")) {
         this.logFileRotationConfig.setRotateLogOnStartupEnabled(this.getBooleanProperty("RotateLogOnStartup", this.logFileRotationConfig.isRotateLogOnStartupEnabled()));
         this.logFileRotationConfig.setNumberOfFilesLimited(this.getBooleanProperty("process.NumberOfFilesLimited", this.logFileRotationConfig.isNumberOfFilesLimited()));
         this.logFileRotationConfig.setRotatedFileCount(this.getIntProperty("process.RotatedFileCount", this.logFileRotationConfig.getRotatedFileCount()));
         this.logFileRotationConfig.setRotationSize(this.getIntProperty("process.FileSizeKB", this.logFileRotationConfig.getRotationSize()));
         this.logFileRotationConfig.setRotationType(this.getProperty("process.RotationType", this.logFileRotationConfig.getRotationType()));
         this.logFileRotationConfig.setRotationTime(this.getProperty("process.RotationTimeStart", this.logFileRotationConfig.getRotationTime()));
         this.logFileRotationConfig.setRotationTimeSpan(this.getIntProperty("process.FileTimeSpan", this.logFileRotationConfig.getRotationTimeSpan()));
         this.logFileRotationConfig.setRotationTimeSpanFactor(this.getLongProperty("process.FileTimeSpanFactor", this.logFileRotationConfig.getRotationTimeSpanFactor()));
      }

   }

   protected StartupConfig(ValuesHolder holder) {
      super(new Properties());
      this.javaVendor = holder.javaVendor;
      this.javaHome = holder.javaHome;
      this.arguments = holder.arguments;
      this.sslArguments = holder.sslArguments;
      this.securityPolicyFile = holder.securityPolicyFile;
      this.classPath = holder.classPath;
      this.mwHome = holder.mwHome;
      String mgmtServer = this.getMgmtServerFromArguments();
      this.adminURL = mgmtServer == null ? holder.adminURL : mgmtServer;
      this.username = holder.username;
      this.password = holder.password;
      this.autoRestart = holder.autoRestart;
      this.serverIPs = holder.serverIPs;
      this.serverIPList = this.serverIPs == null ? null : getIPListFromString(this.serverIPs);
      this.restartMax = holder.restartMax;
      this.restartInterval = holder.restartInterval;
      this.restartDelaySeconds = holder.restartDelaySeconds;
      this.serverUid = holder.serverUid;
      this.serverGid = holder.serverGid;
      this.nmHostName = holder.nmHostName;
      this.trustKeyStore = holder.trustKeyStore;
      this.customTrustKeyStoreFileName = holder.customTrustKeyStoreFileName;
      this.customTrustKeyStoreType = holder.customTrustKeyStoreType;
      this.customTrustKeyStorePassPhrase = holder.customTrustKeyStorePassPhrase;
      this.javaStandardTrustKeyStorePassPhrase = holder.javaStandardTrustKeyStorePassPhrase;
      this.transientScriptEnv = holder.transientScriptEnv;
      this.transientServerArgs = holder.transientServerArgs;
      this.serverOutFile = this.getServerOutFileFromArguments();
      this.serverErrFile = this.getServerErrFileFromArguments();
      this.logFileRotationConfig = holder.logFileRotationConfig;
   }

   public LogFileConfigBean getLogFileRotationConfig() {
      return this.logFileRotationConfig;
   }

   public String getJavaVendor() {
      return this.javaVendor;
   }

   public String getJavaHome() {
      return this.javaHome;
   }

   public String getMwHome() {
      return this.mwHome;
   }

   public String getClassPath() {
      return this.classPath;
   }

   public String getSecurityPolicyFile() {
      return this.securityPolicyFile;
   }

   public String getArguments() {
      return this.arguments;
   }

   public String getSSLArguments() {
      return this.sslArguments;
   }

   public String getAdminURL() {
      return this.adminURL;
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }

   public boolean isAutoRestart() {
      return this.autoRestart;
   }

   public int getRestartMax() {
      return this.restartMax;
   }

   public int getRestartInterval() {
      return this.restartInterval;
   }

   public int getRestartDelaySeconds() {
      return this.restartDelaySeconds;
   }

   public List getServerIPList() {
      return this.serverIPList;
   }

   private static List getIPListFromString(String serverIPs) {
      List ipList = new ArrayList();
      if (serverIPs != null) {
         String[] ips = serverIPs.split(",");
         String[] var3 = ips;
         int var4 = ips.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String tmpIP = var3[var5];
            ipList.add(tmpIP);
         }
      }

      return ipList;
   }

   public static String getServerIPsFromList(List ipList) {
      StringBuffer sb = new StringBuffer();
      Iterator ips = ipList.iterator();

      while(ips.hasNext()) {
         String serverIP = (String)ips.next();
         sb.append(serverIP);
         if (ips.hasNext()) {
            sb.append(",");
         }
      }

      return sb.toString();
   }

   public String getUid() {
      return this.serverUid;
   }

   public String getGid() {
      return this.serverGid;
   }

   public String getTrustKeyStore() {
      return this.trustKeyStore;
   }

   public String getCustomTrustKeyStoreFileName() {
      return this.customTrustKeyStoreFileName;
   }

   public String getCustomTrustKeyStoreType() {
      return this.customTrustKeyStoreType;
   }

   public String getCustomTrustKeyStorePassPhrase() {
      return this.customTrustKeyStorePassPhrase;
   }

   public String getJavaStandardTrustKeyStorePassPhrase() {
      return this.javaStandardTrustKeyStorePassPhrase;
   }

   public Properties getBootProperties() {
      Properties props = new Properties();
      if (this.username != null) {
         props.setProperty("username", this.username);
      }

      if (this.password != null) {
         props.setProperty("password", this.password);
      }

      if (this.trustKeyStore != null) {
         props.setProperty("TrustKeyStore", this.trustKeyStore);
      }

      if (this.customTrustKeyStoreFileName != null) {
         props.setProperty("CustomTrustKeyStoreFileName", this.customTrustKeyStoreFileName);
      }

      if (this.customTrustKeyStoreType != null) {
         props.setProperty("CustomTrustKeyStoreType", this.customTrustKeyStoreType);
      }

      if (this.customTrustKeyStorePassPhrase != null) {
         props.setProperty("CustomTrustKeyStorePassPhrase", this.customTrustKeyStorePassPhrase);
      }

      if (this.javaStandardTrustKeyStorePassPhrase != null) {
         props.setProperty("JavaStandardTrustKeyStorePassPhrase", this.javaStandardTrustKeyStorePassPhrase);
      }

      return props;
   }

   public Properties getStartupProperties() {
      Properties props = new Properties();
      if (this.javaVendor != null) {
         props.setProperty("JavaVendor", this.javaVendor);
      }

      if (this.javaHome != null) {
         props.setProperty("JavaHome", this.javaHome);
      }

      if (this.mwHome != null) {
         props.setProperty("MWHome", this.mwHome);
      }

      if (this.classPath != null) {
         props.setProperty("ClassPath", this.classPath);
      }

      if (this.securityPolicyFile != null) {
         props.setProperty("SecurityPolicyFile", this.securityPolicyFile);
      }

      if (this.arguments != null) {
         props.setProperty("Arguments", this.arguments);
      }

      if (this.sslArguments != null) {
         props.setProperty("SSLArguments", this.sslArguments);
      }

      if (this.adminURL != null) {
         props.setProperty("AdminURL", this.adminURL);
      }

      if (this.serverIPs != null) {
         props.setProperty("ServerIP", this.serverIPs);
      }

      if (this.nmHostName != null) {
         props.setProperty("NMHostName", this.nmHostName);
      }

      props.setProperty("AutoRestart", Boolean.toString(this.autoRestart));
      props.setProperty("RestartMax", Integer.toString(this.restartMax));
      props.setProperty("RestartInterval", Integer.toString(this.restartInterval));
      props.setProperty("RestartDelaySeconds", Integer.toString(this.restartDelaySeconds));
      if (this.serverUid != null) {
         props.setProperty("ServerUID", this.serverUid);
      }

      if (this.serverGid != null) {
         props.setProperty("ServerGID", this.serverGid);
      }

      if (this.logFileRotationConfig != null) {
         props.setProperty("RotateLogOnStartup", Boolean.toString(this.logFileRotationConfig.isRotateLogOnStartupEnabled()));
         props.setProperty("process.NumberOfFilesLimited", Boolean.toString(this.logFileRotationConfig.isNumberOfFilesLimited()));
         props.setProperty("process.RotatedFileCount", Integer.toString(this.logFileRotationConfig.getRotatedFileCount()));
         props.setProperty("process.FileSizeKB", Integer.toString(this.logFileRotationConfig.getRotationSize()));
         props.setProperty("process.RotationType", this.logFileRotationConfig.getRotationType());
         props.setProperty("process.RotationTimeStart", this.logFileRotationConfig.getRotationTime());
         props.setProperty("process.FileTimeSpan", Integer.toString(this.logFileRotationConfig.getRotationTimeSpan()));
         props.setProperty("process.FileTimeSpanFactor", Long.toString(this.logFileRotationConfig.getRotationTimeSpanFactor()));
      }

      return props;
   }

   public Properties getProperties() {
      Properties props = this.getBootProperties();
      props.putAll(this.getStartupProperties());
      return props;
   }

   private String getServerOutFileFromArguments() {
      return this.getPropertyFromArguments("-Dweblogic.Stdout=");
   }

   private String getServerErrFileFromArguments() {
      return this.getPropertyFromArguments("-Dweblogic.Stderr=");
   }

   private String getMgmtServerFromArguments() {
      return this.getPropertyFromArguments("-Dweblogic.managerment.server=");
   }

   private String getPropertyFromArguments(String prop) {
      if (this.arguments != null) {
         List optList = Arrays.asList((Object[])this.arguments.trim().split("\\s"));

         for(int i = 0; i < optList.size(); ++i) {
            String s = ((String)optList.get(i)).trim();
            if (s.length() > 0 && s.startsWith(prop)) {
               String value = s.substring(s.indexOf("=") + 1);
               if (value.startsWith("\"") && value.endsWith("\"")) {
                  return value.substring(value.indexOf("\"") + 1, value.lastIndexOf("\""));
               }

               return value;
            }
         }
      }

      return null;
   }

   public String getNMHostName() {
      return this.nmHostName;
   }

   public String getServerOutFile() {
      return this.serverOutFile;
   }

   public String getServerErrFile() {
      return this.serverErrFile;
   }

   public String getTransientScriptEnv() {
      return this.transientScriptEnv;
   }

   public String getTransientServerArgs() {
      return this.transientServerArgs;
   }

   public static class ValuesHolder {
      private String javaVendor;
      private String javaHome;
      private String mwHome;
      private String classPath;
      private String securityPolicyFile;
      private String arguments;
      private String sslArguments;
      private String adminURL;
      private String username;
      private String password;
      private boolean autoRestart = true;
      private int restartMax = 2;
      private int restartDelaySeconds = 0;
      private int restartInterval = 0;
      private String serverIPs;
      private String serverUid;
      private String serverGid;
      private String trustKeyStore;
      private String customTrustKeyStoreFileName;
      private String customTrustKeyStoreType;
      private String customTrustKeyStorePassPhrase;
      private String javaStandardTrustKeyStorePassPhrase;
      private String transientScriptEnv;
      private String transientServerArgs;
      private String nmHostName;
      private LogFileConfigBean logFileRotationConfig = new LogFileConfigBean();

      public void setLogFileConfig(LogFileConfigBean logFileConfig) {
         this.logFileRotationConfig = logFileConfig;
      }

      public LogFileConfigBean getLogFileConfig() {
         return this.logFileRotationConfig;
      }

      public String getJavaVendor() {
         return this.javaVendor;
      }

      public void setJavaVendor(String javaVendor) {
         this.javaVendor = javaVendor;
      }

      public String getJavaHome() {
         return this.javaHome;
      }

      public void setJavaHome(String javaHome) {
         this.javaHome = javaHome;
      }

      public String getMwHome() {
         return this.mwHome;
      }

      public void setMwHome(String mwHome) {
         this.mwHome = mwHome;
      }

      public String getClassPath() {
         return this.classPath;
      }

      public void setClassPath(String classPath) {
         this.classPath = classPath;
      }

      public String getSecurityPolicyFile() {
         return this.securityPolicyFile;
      }

      public void setSecurityPolicyFile(String securityPolicyFile) {
         this.securityPolicyFile = securityPolicyFile;
      }

      public String getArguments() {
         return this.arguments;
      }

      public void setArguments(String arguments) {
         this.arguments = arguments;
      }

      public String getSSLArguments() {
         return this.sslArguments;
      }

      public void setSSLArguments(String sslArguments) {
         this.sslArguments = sslArguments;
      }

      public String getAdminURL() {
         return this.adminURL;
      }

      public void setAdminURL(String adminURL) {
         this.adminURL = adminURL;
      }

      public String getUsername() {
         return this.username;
      }

      public void setUsername(String username) {
         this.username = username;
      }

      public String getPassword() {
         return this.password;
      }

      public void setPassword(String password) {
         this.password = password;
      }

      public boolean isAutoRestart() {
         return this.autoRestart;
      }

      public void setAutoRestart(boolean autoRestart) {
         this.autoRestart = autoRestart;
      }

      public int getRestartMax() {
         return this.restartMax;
      }

      public void setRestartMax(int restartMax) {
         this.restartMax = restartMax;
      }

      public int getRestartDelaySeconds() {
         return this.restartDelaySeconds;
      }

      public void setRestartDelaySeconds(int restartDelaySeconds) {
         this.restartDelaySeconds = restartDelaySeconds;
      }

      public int getRestartInterval() {
         return this.restartInterval;
      }

      public void setRestartInterval(int restartInterval) {
         this.restartInterval = restartInterval;
      }

      public String getServerIPs() {
         return this.serverIPs;
      }

      public void setServerIPs(String serverIPs) {
         this.serverIPs = serverIPs;
      }

      public String getUid() {
         return this.serverUid;
      }

      public void setUid(String serverUid) {
         this.serverUid = serverUid;
      }

      public String getGid() {
         return this.serverGid;
      }

      public void setGid(String serverGid) {
         this.serverGid = serverGid;
      }

      public String getTrustKeyStore() {
         return this.trustKeyStore;
      }

      public void setTrustKeyStore(String trustKeyStore) {
         this.trustKeyStore = trustKeyStore;
      }

      public String getCustomTrustKeyStoreFileName() {
         return this.customTrustKeyStoreFileName;
      }

      public void setCustomTrustKeyStoreFileName(String customTrustKeyStoreFileName) {
         this.customTrustKeyStoreFileName = customTrustKeyStoreFileName;
      }

      public String getCustomTrustKeyStoreType() {
         return this.customTrustKeyStoreType;
      }

      public void setCustomTrustKeyStoreType(String customTrustKeyStoreType) {
         this.customTrustKeyStoreType = customTrustKeyStoreType;
      }

      public String getCustomTrustKeyStorePassPhrase() {
         return this.customTrustKeyStorePassPhrase;
      }

      public void setCustomTrustKeyStorePassPhrase(String customTrustKeyStorePassPhrase) {
         this.customTrustKeyStorePassPhrase = customTrustKeyStorePassPhrase;
      }

      public String getJavaStandardTrustKeyStorePassPhrase() {
         return this.javaStandardTrustKeyStorePassPhrase;
      }

      public void setJavaStandardTrustKeyStorePassPhrase(String javaStandardTrustKeyStorePassPhrase) {
         this.javaStandardTrustKeyStorePassPhrase = javaStandardTrustKeyStorePassPhrase;
      }

      public String getTransientScriptEnv() {
         return this.transientScriptEnv;
      }

      public void setTransientScriptEnv(String transientScriptEnv) {
         this.transientScriptEnv = transientScriptEnv;
      }

      public String getTransientServerArgs() {
         return this.transientServerArgs;
      }

      public void setTransientServerArgs(String transientServerArgs) {
         this.transientServerArgs = transientServerArgs;
      }

      public String getNMHostName() {
         return this.nmHostName;
      }

      public void setNMHostName(String nmHostName) {
         this.nmHostName = nmHostName;
      }

      public void setKeyStoreProperties(Properties props, ClearOrEncryptedService ces) {
         this.trustKeyStore = props.getProperty("TrustKeyStore");
         this.customTrustKeyStoreFileName = props.getProperty("CustomTrustKeyStoreFileName");
         this.customTrustKeyStoreType = props.getProperty("CustomTrustKeyStoreType");
         String v;
         if ((v = props.getProperty("CustomTrustKeyStorePassPhrase")) != null) {
            this.customTrustKeyStorePassPhrase = ces.encrypt(v);
         }

         if ((v = props.getProperty("JavaStandardTrustKeyStorePassPhrase")) != null) {
            this.javaStandardTrustKeyStorePassPhrase = ces.encrypt(v);
         }

      }

      public StartupConfig toStartupConfig() {
         return new StartupConfig(this);
      }
   }
}
