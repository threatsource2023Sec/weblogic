package weblogic.nodemanager.server;

import com.bea.logging.LogFileConfigBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.CoherenceStartupConfig;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.util.Platform;

class CoherenceProcessBuilder extends WLSProcessBuilder {
   private static final String STARTUP_CLASS = "weblogic.nodemanager.server.provider.WeblogicCacheServer";
   private static final String COHERENCE_ROOT_DIR = "coherence";
   private static final String COHERENCE_JAR = "coherence.jar";
   private static final String COHERENCE_SERVER_JAR_VERSION = "12.1.2.0";
   private static final String COHERENCE_SERVER_JAR = "weblogic.server.modules.coherence.server_12.1.2.0.jar";
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   protected static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   CoherenceProcessBuilder(ServerManagerI serverMgr, StartupConfig conf) {
      this(serverMgr, conf, false);
   }

   CoherenceProcessBuilder(ServerManagerI serverMgr, StartupConfig conf, boolean isStopCommand) {
      assert conf instanceof CoherenceStartupConfig;

      DomainManager dm = serverMgr.getDomainManager();
      NMServerConfig sc = dm.getNMServer().getConfig();
      this.initialize(serverMgr, conf, isStopCommand, dm, sc);
      if (sc.isCoherenceStartScriptEnabled()) {
         this.cmdLine = this.getScriptCommandLine();
         this.environment = this.getScriptEnvironment();
      } else {
         this.cmdLine = this.getJavaCommandLine();
      }

   }

   public LogFileConfigBean getLogFileRotationConfig() {
      return this.serverMgr.getDomainManager().getNMServer().getConfig().getProcFileRotationConfig();
   }

   List getJavaCommandLine(ServerManagerI serverMgr, StartupConfig conf) {
      if (serverMgr == null) {
         throw new NullPointerException();
      } else {
         ArrayList cmd = new ArrayList();
         String s;
         if ((s = conf.getJavaHome()) == null) {
            s = serverMgr.getDomainManager().getNMServer().getConfig().getCohStartupJavaHome();
         }

         cmd.add(s + File.separator + "bin" + File.separator + "java");
         this.addCoherenceJavaOptions(serverMgr, conf, cmd);
         cmd.add("weblogic.nodemanager.server.provider.WeblogicCacheServer");
         return cmd;
      }
   }

   private void addCoherenceJavaOptions(ServerManagerI serverMgr, StartupConfig conf, ArrayList cmd) {
      cmd.add("-Dcoherence.member=" + serverMgr.getServerName());
      CoherenceStartupConfig cConf = (CoherenceStartupConfig)conf;
      String operationalConfigFile = this.getOperationalConfigFile(serverMgr, cConf);
      if (operationalConfigFile != null) {
         cmd.add("-Dcoherence.override=" + operationalConfigFile);
      } else {
         int i = 1;
         CoherenceStartupConfig.WellKnownAddress[] var8 = cConf.getWellKnownAddresses();
         int var9 = var8.length;

         int ttl;
         String unicastListenAddress;
         for(ttl = 0; ttl < var9; ++ttl) {
            CoherenceStartupConfig.WellKnownAddress wka = var8[ttl];
            unicastListenAddress = i == 1 ? "" : String.valueOf(i);
            cmd.add("-Dcoherence.wka" + unicastListenAddress + "=" + wka.getListenAddress());
            ++i;
         }

         int multicastListenPort = cConf.getMulticastListenPort();
         if (multicastListenPort > 0) {
            cmd.add("-Dcoherence.clusterport=" + multicastListenPort);
         }

         String multicastListenAddress = cConf.getMulticastListenAddress();
         if (multicastListenAddress != null) {
            cmd.add("-Dcoherence.clusteraddress=" + multicastListenAddress);
         }

         ttl = cConf.getTimeToLive();
         if (ttl != 4) {
            cmd.add("-Dcoherence.ttl=" + ttl);
         }

         int unicastListenPort = cConf.getUnicastListenPort();
         if (unicastListenPort > 0) {
            cmd.add("-Dcoherence.localport=" + unicastListenPort);
         }

         unicastListenAddress = cConf.getUnicastListenAddress();
         if (unicastListenAddress != null) {
            cmd.add("-Dcoherence.localhost=" + unicastListenAddress);
         }

         if (!cConf.isUnicastPortAutoAdjust()) {
            cmd.add("-Dcoherence.localport.adjust=false");
         }
      }

      String clusterName = cConf.getClusterName();
      if (clusterName != null && clusterName.length() > 0) {
         cmd.add("-Dcoherence.cluster=" + clusterName);
      }

      String systemClassPath = System.getProperty("java.class.path");
      String s = conf.getClassPath();
      if (s == null) {
         s = this.getCoherenceClassPath(conf);
      } else {
         s = Platform.parseClassPath(s, systemClassPath);
         StringBuilder sb = new StringBuilder(s);
         sb.append(File.pathSeparatorChar).append(s);
      }

      cmd.add("-Djava.class.path=" + Platform.preparePathForCommand(s));
      if ((s = conf.getArguments()) != null || (s = serverMgr.getDomainManager().getNMServer().getConfig().getCohStartupArgs()) != null) {
         cmd.addAll(this.toOptionsList(s));
      }

      cmd.add("-Dweblogic.RootDirectory=" + serverMgr.getDomainManager().getDomainDir().getAbsolutePath());
   }

   private String getOperationalConfigFile(ServerManagerI serverMgr, CoherenceStartupConfig cConf) {
      String clusterConfigurationFileName = cConf.getCustomClusterConfigurationFileName();
      String clusterName = cConf.getClusterName();
      if (clusterConfigurationFileName != null && clusterName != null) {
         StringBuilder sb = new StringBuilder(serverMgr.getDomainManager().getDomainDir().getAbsolutePath());
         sb.append(File.separator).append("config");
         sb.append(File.separator).append("coherence");
         sb.append(File.separator).append(clusterName);
         sb.append(File.separator).append(clusterConfigurationFileName);
         String fileName = sb.toString();
         File file = new File(fileName);
         if (file.canRead()) {
            return fileName;
         } else {
            nmLog.log(Level.INFO, nmText.msgInvalidCoherenceOperationalConfigFile(fileName));
            return null;
         }
      } else {
         return null;
      }
   }

   private String getCoherenceClassPath(StartupConfig conf) {
      StringBuilder sb = new StringBuilder();
      String mwHome = conf.getMwHome();
      if (mwHome == null) {
         mwHome = this.serverMgr.getDomainManager().getNMServer().getConfig().getCohStartupMWHome();
      }

      sb.append(getCoherenceServerJarPath(mwHome));
      sb.append(File.pathSeparator);
      sb.append(getCoherenceJarPath(mwHome));
      return sb.toString();
   }

   private static String getCoherenceJarPath() {
      return getCoherenceJarPath((String)null);
   }

   private static String getCoherenceJarPath(String mwHome) {
      StringBuilder sb = new StringBuilder();
      String coherenceHome = System.getProperty("coherence.home");
      if (mwHome == null) {
         mwHome = getBeaHomePath();
      }

      if (coherenceHome != null) {
         sb.append(coherenceHome);
         sb.append(File.separatorChar).append("lib");
         sb.append(File.separatorChar);
      } else if (mwHome != null) {
         sb.append(mwHome);
         sb.append(File.separatorChar).append("oracle_common");
         sb.append(File.separatorChar).append("coherence");
         sb.append(File.separatorChar).append("lib");
         sb.append(File.separatorChar);
      }

      sb.append("coherence.jar");
      return sb.toString();
   }

   private static String getBeaHomePath() {
      return System.getProperty("bea.home");
   }

   private static String getCoherenceServerJarPath() {
      return getCoherenceServerJarPath((String)null);
   }

   private static String getCoherenceServerJarPath(String mwHome) {
      StringBuilder sb = new StringBuilder();
      if (mwHome == null) {
         mwHome = getBeaHomePath();
      }

      if (mwHome != null) {
         sb.append(mwHome);
         sb.append(File.separatorChar).append("oracle_common");
         sb.append(File.separatorChar).append("modules");
         sb.append(File.separatorChar).append("features");
         sb.append(File.separatorChar);
      }

      sb.append("weblogic.server.modules.coherence.server_12.1.2.0.jar");
      return sb.toString();
   }

   List getScriptCommandLine() {
      ArrayList cmd = new ArrayList();
      DomainManager dm = this.serverMgr.getDomainManager();
      DomainDir dir = dm.getDomainDir();
      String scriptName = dm.getNMServer().getConfig().getCoherenceStartScriptName();
      File scriptFile = new File(scriptName);
      if (!scriptFile.isAbsolute()) {
         scriptFile = new File(new File(dir, "bin"), scriptName);
      }

      cmd.add(scriptFile.getPath());
      return cmd;
   }

   public Map getScriptEnvironment() {
      ArrayList cmd = new ArrayList();
      this.addCoherenceJavaOptions(this.serverMgr, this.conf, cmd);
      String coherenceOpts = this.toOptionsString(cmd);
      Map env = this.getSystemEnvironment();
      String javaOptionsEnv = (String)env.get("JAVA_OPTIONS");
      if (javaOptionsEnv != null) {
         javaOptionsEnv = javaOptionsEnv + ' ' + coherenceOpts;
      }

      env.put("JAVA_OPTIONS", javaOptionsEnv);
      String javaHomeEnv = this.conf.getJavaHome();
      if (javaHomeEnv != null || (javaHomeEnv = this.serverMgr.getDomainManager().getNMServer().getConfig().getCohStartupJavaHome()) != null) {
         env.put("JAVA_HOME", javaHomeEnv);
      }

      return env;
   }

   public static boolean isCoherenceEnv() {
      File cohServerJar = new File(getCoherenceServerJarPath());
      File cohJar = new File(getCoherenceJarPath());
      return cohServerJar.exists() || cohJar.exists();
   }
}
