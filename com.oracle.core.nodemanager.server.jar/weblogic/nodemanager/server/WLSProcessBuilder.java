package weblogic.nodemanager.server;

import com.bea.logging.LogFileConfigBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.util.Platform;
import weblogic.nodemanager.util.ProcessControl;

public class WLSProcessBuilder {
   protected ServerManagerI serverMgr;
   protected StartupConfig conf;
   protected List cmdLine;
   protected Map environment;
   protected File directory;
   protected File outFile;
   protected ProcessControl processCtrl;
   protected boolean stopCommand;
   protected LogFileConfigBean logFileRotationConfig;
   private static final String STARTUP_CLASS = "weblogic.Server";
   public static final String CLASSPATH_ENV = "CLASSPATH";
   public static final String ADMIN_URL_ENV = "ADMIN_URL";
   public static final String JAVA_VENDOR_ENV = "JAVA_VENDOR";
   public static final String JAVA_HOME_ENV = "JAVA_HOME";
   public static final String JAVA_OPTIONS_ENV = "JAVA_OPTIONS";
   public static final String SERVER_NAME_ENV = "SERVER_NAME";
   public static final String SERVER_IP_ENV = "SERVER_IP";
   public static final String SECURITY_POLICY_ENV = "SECURITY_POLICY";
   public static final String JAVA_TOOL_OPTIONS_ENV = "JAVA_TOOL_OPTIONS";
   public static final String JAVA_OOM_OPTION = "-XX:OnOutOfMemoryError";
   public static final String PATH_ENV = "PATH";
   private static final String LANG_ENV = "LANG";
   public static final String WL_HOME_ENV = "WL_HOME";
   public static final String BEA_HOME_ENV = "BEA_HOME";
   public static final String MW_HOME_ENV = "MW_HOME";
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   protected WLSProcessBuilder() {
      this.stopCommand = false;
   }

   public WLSProcessBuilder(ServerManagerI sm, StartupConfig conf) {
      this(sm, conf, false);
   }

   public WLSProcessBuilder(ServerManagerI sm, StartupConfig conf, boolean stopCommand) {
      this.stopCommand = false;
      DomainManager dm = sm.getDomainManager();
      NMServerConfig sc = dm.getNMServer().getConfig();
      this.initialize(sm, conf, stopCommand, dm, sc);
      if (!stopCommand && !sc.isStartScriptEnabled()) {
         this.cmdLine = this.getJavaCommandLine();
      } else {
         this.cmdLine = this.getScriptCommandLine();
         this.environment = this.getScriptEnvironment();
      }

   }

   protected void initialize(ServerManagerI sm, StartupConfig conf, boolean stopCommand, DomainManager dm, NMServerConfig sc) {
      this.serverMgr = sm;
      this.conf = conf;
      this.stopCommand = stopCommand;
      this.directory = dm.getDomainDir();
      this.outFile = this.serverMgr.getServerDir().getOutFile();
      this.processCtrl = sc.getProcessControl();
      this.logFileRotationConfig = conf.getLogFileRotationConfig();
      if (this.logFileRotationConfig == null) {
         this.logFileRotationConfig = new LogFileConfigBean();
      }

      this.logFileRotationConfig.setBaseLogFileName(this.outFile.getPath());
      this.logFileRotationConfig.setLogFileRotationDir(this.outFile.getParent());
   }

   public NMProcess createProcess() throws IOException {
      Object theProcess;
      if (this.processCtrl != null) {
         File f = new File((String)this.cmdLine.get(0));
         if (!f.exists() && f.getName().lastIndexOf(46) < 0 && Platform.isWindows()) {
            f = new File((String)this.cmdLine.get(0) + ".exe");
         }

         if (!f.exists()) {
            if (((String)this.cmdLine.get(0)).equals("")) {
               throw new IOException("Executable does not exist");
            }

            throw new IOException("Executable " + (String)this.cmdLine.get(0) + " does not exist");
         }

         theProcess = new NMProcessNativeImpl(this.processCtrl, this.cmdLine, this.environment, this.directory, this.outFile);
      } else {
         theProcess = new NMProcessImpl(this.cmdLine, this.environment, this.directory, this.outFile, this.getLogFileRotationConfig());
      }

      return (NMProcess)theProcess;
   }

   public LogFileConfigBean getLogFileRotationConfig() {
      return this.logFileRotationConfig;
   }

   public NMProcess createProcess(String pid) throws IOException {
      if (this.processCtrl != null) {
         return new NMProcessNativeImpl(this.processCtrl, pid);
      } else {
         throw new IllegalStateException(nmText.noProcessControl());
      }
   }

   public List getCommandLine() {
      ArrayList tmpCommand = new ArrayList();
      tmpCommand.addAll(this.cmdLine);
      return tmpCommand;
   }

   public Map getEnvironment() {
      return this.environment;
   }

   public File getDirectory() {
      return this.directory;
   }

   public File getOutFile() {
      return this.outFile;
   }

   public ProcessControl getProcessControl() {
      return this.processCtrl;
   }

   public boolean isNative() {
      return this.processCtrl != null;
   }

   List getJavaCommandLine() {
      return this.getJavaCommandLine(this.serverMgr, this.conf);
   }

   private String getJavaHome(StartupConfig conf, NMServerConfig nmConf) {
      String javaHome;
      if ((javaHome = conf.getJavaHome()) == null) {
         javaHome = nmConf.getWlsStartupJavaHome();
      }

      return javaHome;
   }

   private boolean checkJavaVersion8(String javaHome) {
      boolean isJavaVersion8 = false;
      String javaPath = javaHome + File.separator + "bin" + File.separator + "java";

      try {
         ProcessBuilder pb = new ProcessBuilder(new String[]{javaPath, "-version"});
         pb.redirectErrorStream(true);
         Process process = pb.start();
         String line = null;
         BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

         while((line = in.readLine()) != null) {
            if (line.contains("1.8.")) {
               isJavaVersion8 = true;
               break;
            }
         }

         process.waitFor();
      } catch (IOException var8) {
         this.serverMgr.log(Level.WARNING, nmText.checkJavaVersionFailed(javaPath + " -version", var8.getMessage()), var8);
      } catch (InterruptedException var9) {
         this.serverMgr.log(Level.WARNING, nmText.checkJavaVersionFailed(javaPath + " -version", var9.getMessage()), var9);
      }

      return isJavaVersion8;
   }

   private String getSecPolicyFile(StartupConfig conf, NMServerConfig nmConf) {
      String secPolicyFile;
      if ((secPolicyFile = conf.getSecurityPolicyFile()) == null) {
         secPolicyFile = nmConf.getWlsStartupSecurityPolicyFile();
      }

      return secPolicyFile;
   }

   private String getArguments(StartupConfig conf, NMServerConfig nmConf) {
      String args;
      if ((args = conf.getArguments()) == null) {
         args = nmConf.getWlsStartupArgs();
      }

      return args;
   }

   List getJavaCommandLine(ServerManagerI serverMgr, StartupConfig conf) {
      ArrayList cmd = new ArrayList();
      NMServerConfig nmConf = serverMgr.getDomainManager().getNMServer().getConfig();
      String javaHome;
      String s = javaHome = this.getJavaHome(conf, nmConf);
      cmd.add(s + File.separator + "bin" + File.separator + "java");
      String prePendOps = serverMgr.getDomainManager().getNMServer().getConfig().getWlsStartupPrependArgs();
      if (prePendOps != null && !prePendOps.isEmpty()) {
         cmd.add(prePendOps);
      }

      cmd.add("-Dweblogic.Name=" + serverMgr.getServerName());
      if ((s = conf.getMwHome()) == null && (s = nmConf.getWlsStartupMWHome()) == null) {
         s = System.getProperty("bea.home");
      }

      if (s != null) {
         cmd.add("-Dbea.home=" + s);
      }

      if ((s = this.getSecPolicyFile(conf, nmConf)) == null) {
         s = System.getProperty("java.security.policy");
      }

      cmd.add("-Djava.security.policy=" + s);
      if ((s = conf.getAdminURL()) != null) {
         cmd.add("-Dweblogic.management.server=" + s);
      }

      if ((s = System.getProperty("java.library.path")) != null) {
         cmd.add("-Djava.library.path=" + Platform.preparePathForCommand(s));
      }

      String systemClassPath;
      if (this.checkJavaVersion8(javaHome)) {
         systemClassPath = System.getenv("JAVA_HOME") + File.separator + "jre" + File.separator + "lib" + File.separator + "endorsed";
         String MW_Endorsed = System.getenv("MW_HOME") + File.separator + "oracle_common" + File.separator + "modules" + File.separator + "endorsed";
         String WL_Endorsed = System.getenv("WL_HOME") + File.separator + "modules" + File.separator + "endorsed";
         String endorsedDir = systemClassPath + File.pathSeparator + MW_Endorsed + File.pathSeparator + WL_Endorsed;
         File f = new File(MW_Endorsed);
         if (!f.isDirectory() || !f.exists()) {
            serverMgr.log(Level.WARNING, "WebLogic endorsed Directory '" + f + "' does not exist.", (Throwable)null);
         }

         f = new File(WL_Endorsed);
         if (!f.isDirectory() || !f.exists()) {
            serverMgr.log(Level.WARNING, "WebLogic endorsed Directory '" + f + "' does not exist.", (Throwable)null);
         }

         cmd.add("-Djava.endorsed.dirs=" + Platform.preparePathForCommand(endorsedDir));
      }

      systemClassPath = System.getProperty("java.class.path");
      if ((s = conf.getClassPath()) == null && (s = nmConf.getWlsStartupClasspath()) == null) {
         s = systemClassPath;
      } else {
         s = Platform.parseClassPath(s, systemClassPath);
      }

      cmd.add("-Djava.class.path=" + Platform.preparePathForCommand(s));
      cmd.addAll(this.getJavaOptions());
      if ((s = conf.getSSLArguments()) != null) {
         cmd.addAll(this.toOptionsList(s));
      }

      if ((s = this.getArguments(conf, nmConf)) != null) {
         cmd.addAll(this.toOptionsList(s));
      }

      if ((s = conf.getTransientServerArgs()) != null) {
         cmd.addAll(this.toOptionsList(s));
      }

      cmd.add("weblogic.Server");
      return cmd;
   }

   List getScriptCommandLine() {
      ArrayList cmd = new ArrayList();
      DomainManager dm = this.serverMgr.getDomainManager();
      DomainDir dir = dm.getDomainDir();
      String scriptName;
      if (this.stopCommand) {
         scriptName = dm.getNMServer().getConfig().getStopScriptName();
      } else {
         scriptName = dm.getNMServer().getConfig().getStartScriptName();
      }

      File scriptFile = new File(scriptName);
      if (!scriptFile.isAbsolute()) {
         scriptFile = new File(new File(dir, "bin"), scriptName);
      }

      cmd.add(scriptFile.getPath());
      return cmd;
   }

   Map getScriptEnvironment() {
      Map env = this.getSystemEnvironment();
      String s;
      if ((s = this.conf.getAdminURL()) != null) {
         env.put("ADMIN_URL", s);
      }

      if ((s = this.getSecPolicyFile(this.conf, this.serverMgr.getDomainManager().getNMServer().getConfig())) != null) {
         env.put("SECURITY_POLICY", s);
      }

      this.addServerNameToEnv(env);
      return env;
   }

   private String getClassPath(StartupConfig conf, NMServerConfig nmConf) {
      String s = conf.getClassPath();
      if (s == null) {
         s = nmConf.getWlsStartupClasspath();
      }

      return s;
   }

   protected Map getSystemEnvironment() {
      Map env = new HashMap();
      env.putAll(System.getenv());
      NMHelper.scrubEnvironmentValues(env);
      NMServerConfig nmConf = this.serverMgr.getDomainManager().getNMServer().getConfig();
      String s = this.conf.getJavaVendor();
      if (s == null) {
         s = nmConf.getWlsStartupJavaVendor();
      }

      if (s != null) {
         env.put("JAVA_VENDOR", s);
      }

      if ((s = this.getJavaHome(this.conf, nmConf)) != null) {
         env.put("JAVA_HOME", s);
      }

      String opts = this.getPrependOpts();
      if (opts == null) {
         opts = "";
      }

      opts = opts + ' ' + this.toOptionsString(this.getJavaOptions());
      if ((s = this.conf.getTransientServerArgs()) != null) {
         opts = opts + ' ' + s;
      }

      if ((s = this.conf.getSSLArguments()) != null) {
         opts = opts + ' ' + s;
      }

      if ((s = this.getArguments(this.conf, nmConf)) != null) {
         opts = opts + ' ' + s;
      }

      String transientScriptEnv = this.conf.getTransientScriptEnv();
      if (transientScriptEnv != null) {
         env.putAll(this.getTransientScriptMap(transientScriptEnv));
      }

      int idxOOMOptionInOpts = opts.indexOf("-XX:OnOutOfMemoryError");
      if (idxOOMOptionInOpts != -1) {
         char ch = opts.charAt(idxOOMOptionInOpts + "-XX:OnOutOfMemoryError".length() + 1);
         boolean bIsOOMOptionUsingQuotes = ch == '"';
         String strToSearch = bIsOOMOptionUsingQuotes ? "\"" : " -";
         int endIdxOOMOptionInOpts = opts.indexOf(strToSearch, idxOOMOptionInOpts + "-XX:OnOutOfMemoryError".length() + 2);
         String javaToolOptions = null;
         if (endIdxOOMOptionInOpts != -1) {
            javaToolOptions = opts.substring(idxOOMOptionInOpts, endIdxOOMOptionInOpts + (bIsOOMOptionUsingQuotes ? 1 : 0));
            if (endIdxOOMOptionInOpts < opts.length() - 1) {
               opts = opts.substring(0, idxOOMOptionInOpts - 1) + opts.substring(endIdxOOMOptionInOpts + 1);
            } else {
               opts = opts.substring(0, idxOOMOptionInOpts - 1);
            }
         } else {
            javaToolOptions = opts.substring(idxOOMOptionInOpts);
            opts = opts.substring(0, idxOOMOptionInOpts - 1);
         }

         env.put("JAVA_TOOL_OPTIONS", javaToolOptions);
      }

      env.put("JAVA_OPTIONS", opts.trim());
      if ((s = this.getClassPath(this.conf, nmConf)) != null) {
         env.put("CLASSPATH", s);
      }

      return env;
   }

   private String getPrependOpts() {
      return this.serverMgr.getDomainManager().getNMServer().getConfig().getWlsStartupPrependArgs();
   }

   private void addServerNameToEnv(Map env) {
      env.put("SERVER_NAME", this.serverMgr.getServerName());
   }

   protected Map getTransientScriptMap(String scriptEnv) {
      Map env = new HashMap();
      StringTokenizer st = new StringTokenizer(scriptEnv, ",");

      while(st.hasMoreTokens()) {
         String nameValueString = st.nextToken();
         int indexOfEquals = nameValueString.indexOf("=");
         if (indexOfEquals >= 1 && indexOfEquals <= nameValueString.length() - 1) {
            String name = nameValueString.substring(0, nameValueString.indexOf("="));
            String value = nameValueString.substring(nameValueString.indexOf("=") + 1, nameValueString.length());
            if (name != null && name.length() != 0 && value != null && value.length() != 0) {
               env.put(name, value);
               continue;
            }

            throw new AssertionError("Missing either a name or a value for: " + name + " : " + value);
         }

         throw new AssertionError("This property is not formed correctly and will be ignored: " + nameValueString);
      }

      return env;
   }

   protected static Map inheritedEnv() {
      Map env = new HashMap();
      if (Platform.isUnix()) {
         String path = System.getenv("PATH");
         if (path != null && path.length() > 0) {
            String[] maskedPath = new String[]{System.getenv("WL_HOME"), System.getenv("BEA_HOME"), System.getenv("JAVA_HOME")};
            String sep = String.valueOf(File.pathSeparatorChar);
            StringTokenizer strTok = new StringTokenizer(path, sep);
            StringBuilder parsedPath = new StringBuilder();

            while(true) {
               label46:
               while(strTok.hasMoreTokens()) {
                  String tok = strTok.nextToken();

                  for(int i = 0; i < maskedPath.length; ++i) {
                     if (maskedPath[i] != null && maskedPath[i].length() > 0 && tok.startsWith(maskedPath[i])) {
                        continue label46;
                     }
                  }

                  if (parsedPath.length() > 0) {
                     parsedPath.append(sep);
                  }

                  parsedPath.append(tok.trim());
               }

               if (parsedPath.length() > 0) {
                  env.put("PATH", parsedPath.toString());
               }
               break;
            }
         }

         String lang = System.getenv("LANG");
         if (lang != null && lang.length() > 0) {
            env.put("LANG", lang);
         }
      }

      return env;
   }

   protected List getJavaOptions() {
      ArrayList opts = new ArrayList();
      ServerDir dir = this.serverMgr.getServerDir();
      File bootFile = dir.getNMBootIdentityFile();
      if (!bootFile.exists()) {
         bootFile = dir.getBootIdentityFile();
      }

      if (bootFile.exists()) {
         opts.add("-Dweblogic.system.BootIdentityFile=" + bootFile);
      }

      opts.add("-Dweblogic.nodemanager.ServiceEnabled=true");
      if (this.processCtrl != null) {
         opts.add("-Dweblogic.nmservice.RotationEnabled=true");
      }

      return opts;
   }

   protected String toOptionsString(List opts) {
      StringBuffer sb = new StringBuffer();

      String opt;
      for(Iterator it = opts.iterator(); it.hasNext(); sb.append(' ').append(opt)) {
         opt = (String)it.next();
         if (opt.startsWith("-D")) {
            int i = opt.indexOf(61);
            if (i != -1) {
               String name = opt.substring(0, i);
               String value = opt.substring(i);
               if (value.indexOf(32) != -1 || value.indexOf(9) != -1) {
                  opt = name + "=\"" + value.substring(1) + '"';
               }
            }
         }
      }

      return sb.substring(1);
   }

   protected List toOptionsList(String opts) {
      int startIndex = 0;
      List filteredList = new ArrayList();
      boolean done = false;

      while(!done) {
         int index = opts.indexOf(" -", startIndex);
         int indexStartQuote = opts.indexOf("\"", startIndex);
         if (indexStartQuote != -1 && indexStartQuote < index) {
            int indexEndQuote = opts.indexOf("\"", indexStartQuote + 1);
            if (indexEndQuote == -1) {
               index = opts.indexOf(" -", index + 1);
            } else {
               index = opts.indexOf(" -", indexEndQuote + 1);
            }
         }

         String s;
         if (index == -1) {
            s = opts.substring(startIndex, opts.length());
            done = true;
         } else {
            s = opts.substring(startIndex, index);
            startIndex = index + 1;
         }

         s = s.trim();
         if (s.length() != 0) {
            filteredList.add(s);
         }
      }

      return filteredList;
   }

   public static boolean isWebLogicServerEnv() {
      boolean wlsEnv = true;

      try {
         Class var1 = Class.forName("weblogic.Server");
      } catch (Exception var2) {
         wlsEnv = false;
      }

      return wlsEnv;
   }
}
