package weblogic.nodemanager.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.BindException;
import java.nio.channels.Channel;
import java.nio.channels.FileLock;
import java.nio.channels.ServerSocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.NMException;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.util.DomainInfo;
import weblogic.nodemanager.util.Platform;
import weblogic.nodemanager.util.ProcessControl;
import weblogic.nodemanager.util.ProcessControlFactory;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.Home;

public class NMServer {
   private NMServerConfig config;
   private SSLConfig sslConfig;
   private Map domains = new HashMap();
   private boolean verbose;
   private boolean debug;
   private static final String DOMAIN_PATH_SEP = ";";
   private FileLock nmLock;
   private static final String NATIVE_LIBRARY_UNAVAILABLE = "-1";
   private static final int RESTART_CODE = 88;
   private static final int RESTART_AND_UPDATE_CODE = 86;
   private static final int SHUTDOWN_CODE = 0;
   private static NMServer _instance;
   private static Channel inheritedChannel;
   public static final String VERSION = "14.1.1.0.0";
   public static final String FULL_VERSION = "Node manager v14.1.1.0.0";
   public static final String CONFIG_FILE_NAME = "nodemanager.properties";
   private static final String LOCK_FILE_NAME = "nodemanager.process.lck";
   private static final String PID_FILE_NAME = "nodemanager.process.id";
   private static final long LOCK_TIMEOUT = 10000L;
   public static final Logger nmLog;
   private static final NodeManagerTextTextFormatter nmText;
   private static final String NM_PROP = "weblogic.nodemanager.";
   private static final String WLS_PROP = "weblogic.";
   private static final String[] usageMsg;

   public boolean isDebugEnabled() {
      return this.debug;
   }

   public boolean shouldUseInheritedChannel() {
      return inheritedChannel != null && inheritedChannel instanceof ServerSocketChannel;
   }

   public static void redirectStandardStreams(String inFile, String outFile, String errFile) {
      PrintStream psOut = null;
      PrintStream psErr = null;
      InputStream is = null;

      try {
         psOut = new PrintStream(new FileOutputStream(outFile, true));
         if (Platform.isWindows() && outFile.equalsIgnoreCase(errFile)) {
            psErr = psOut;
         } else if (Platform.isUnix() && outFile.equals(errFile)) {
            psErr = psOut;
         } else {
            psErr = new PrintStream(new FileOutputStream(errFile, true));
         }

         System.setOut(psOut);
         System.setErr(psErr);
      } catch (Exception var8) {
         nmLog.warning(nmText.getStdOutErrStreams(outFile.toString(), errFile.toString()));
      }

      try {
         is = new FileInputStream(inFile);
         System.setIn(is);
      } catch (Exception var7) {
         nmLog.warning(nmText.getInputStream(inFile.toString()));
      }

   }

   public static NMServer getInstance() {
      return _instance;
   }

   private static NMServer getInstance(String[] args) throws IOException, ConfigException {
      _instance = new NMServer(args);
      return _instance;
   }

   private static NMServer getInstance(Properties props) throws IOException, ConfigException {
      _instance = new NMServer(props);
      return _instance;
   }

   public NMServer(String[] args) throws IOException, ConfigException {
      if (inheritedChannel != null) {
         redirectStandardStreams("/dev/null", "nodemanager.out", "nodemanager.out");
      }

      NMProperties props = this.initProperties(args);
      this.getNMFileLock(props);
      this.writePidFile(props);
      this.config = new NMServerConfig(props);
      if (Upgrader.upgrade(this.config)) {
         props = this.initProperties(args);
         this.config = new NMServerConfig(props);
      }

      if (this.config.isSecureListener()) {
         this.sslConfig = new SSLConfig(props, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
      }

      props.save(this.config);
      if (this.verbose) {
         String printToErr = props.getProperty("LogToStderr");
         if (printToErr == null || "true".equals(printToErr)) {
            System.err.println("Node manager v14.1.1.0.0");
            System.err.println();
            this.config.print(System.err);
         }
      }

   }

   private NMProperties initProperties(String[] args) throws IOException {
      NMProperties props = new NMProperties(System.getProperties());
      this.parseArguments(args, props);
      Properties sysProps = System.getProperties();
      Iterator it = sysProps.keySet().iterator();

      String value;
      while(it.hasNext()) {
         String name = (String)it.next();
         value = sysProps.getProperty(name);
         if (name.startsWith("weblogic.nodemanager.")) {
            name = name.substring("weblogic.nodemanager.".length());
            if (!props.containsKey(name)) {
               props.setProperty(name, value);
            }
         } else if (name.length() > 0 && !props.containsKey(name)) {
            if (Character.isUpperCase(name.charAt(0))) {
               props.setProperty(name, value);
            } else if (name.startsWith("weblogic.")) {
               String subName = name.substring("weblogic.".length());
               if (subName.length() > 0 && Character.isUpperCase(subName.charAt(0))) {
                  props.setProperty(name, value);
               }
            }
         }
      }

      File nmHome = new File(props.getProperty("NodeManagerHome", System.getProperty("user.dir")));
      value = props.getProperty("PropertiesFile");
      File propsFile;
      if (value != null) {
         propsFile = new File(value);
      } else {
         propsFile = new File(nmHome, "nodemanager.properties");
      }

      NMProperties fileProps = new NMProperties(propsFile);
      fileProps.load();
      fileProps.putAll(props);
      if (this.verbose && fileProps.getProperty("LogToStderr") == null) {
         fileProps.setProperty("LogToStderr", "true");
      }

      return fileProps;
   }

   private void getNMFileLock(Properties props) throws IOException {
      File nmLockFile = new File(this.getNMHome(props), "nodemanager.process.lck");
      FileOutputStream os = new FileOutputStream(nmLockFile);

      try {
         this.nmLock = FileUtils.getFileLock(os.getChannel(), 10000L);
      } catch (IOException var6) {
         IOException throwIOE = new IOException(nmText.msgErrorLockFile(nmLockFile.getPath()));
         throwIOE.initCause(var6);
         throw throwIOE;
      }

      if (this.nmLock == null) {
         throw new IOException(nmText.msgErrorLockFile(nmLockFile.getPath()));
      } else {
         nmLockFile.deleteOnExit();
      }
   }

   private void writePidFile(Properties props) throws IOException {
      FileOutputStream os = null;

      try {
         File pidFile = new File(this.getNMHome(props), "nodemanager.process.id");
         os = new FileOutputStream(pidFile);
         this.writeProcessId(os);
         pidFile.deleteOnExit();
      } finally {
         try {
            if (os != null) {
               os.close();
            }
         } catch (Exception var9) {
         }

      }

   }

   private File getNMHome(Properties props) {
      return new File(props.getProperty("NodeManagerHome", System.getProperty("user.dir")));
   }

   private void writeProcessId(FileOutputStream os) {
      ProcessControl pc = null;
      String dataToWrite = "-1";

      try {
         pc = ProcessControlFactory.getProcessControl();
         if (pc == null) {
            nmLog.log(Level.INFO, nmText.getNativeLibraryNAForPid());
         } else {
            dataToWrite = pc.getProcessId();
         }
      } catch (UnsatisfiedLinkError var6) {
         nmLog.log(Level.WARNING, nmText.getNativeLibraryLoadErrorForPid(), var6);
      }

      try {
         os.write(dataToWrite.getBytes());
         os.flush();
      } catch (IOException var5) {
         nmLog.log(Level.WARNING, nmText.getWritingPidFileError("nodemanager.process.lck"), var5);
      }

   }

   void releaseLock() {
      if (this.nmLock != null) {
         try {
            this.nmLock.release();
         } catch (IOException var2) {
            nmLog.log(Level.WARNING, var2.getMessage());
         }
      }

   }

   void restart(boolean update) {
      this.exit(update ? 86 : 88);
   }

   void exit() {
      this.exit(0);
   }

   private void exit(int exitCode) {
      this.releaseLock();
      System.exit(exitCode);
   }

   public NMServer(Properties props) throws IOException, ConfigException {
      NMProperties nmProps = new NMProperties();
      nmProps.putAll(props);
      this.getNMFileLock(props);
      this.writePidFile(props);
      this.config = new NMServerConfig(nmProps);
      if (this.config.isSecureListener()) {
         this.sslConfig = new SSLConfig(props, new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
      }

   }

   public void start(Channel channel) throws ConfigException, IOException {
      Server server = this.getRuntimeServer();
      server.init(this);
      this.initDomains();
      nmLog.info(nmText.msgSvrImplClass(server.getClass().getName()));
      server.start(this);
   }

   private void initDomains() throws ConfigException, IOException {
      Map map = this.config.getDomainsMap();
      Iterator it = map.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry me = (Map.Entry)it.next();
         String name = (String)me.getKey();
         String[] domLocations = ((String)me.getValue()).split(";");
         String[] var6 = domLocations;
         int var7 = domLocations.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String location = var6[var8];
            if (!location.isEmpty()) {
               DomainDir dir = new DomainDir(location);
               if (dir.isValid()) {
                  String path = dir.getPath();

                  try {
                     path = dir.getCanonicalPath();
                  } catch (IOException var15) {
                     throw new ConfigException(nmText.getConfigError(path) + " " + var15);
                  }

                  DomainManager dm;
                  try {
                     dm = new DomainManager(this, name, path);
                  } catch (IOException var14) {
                     nmLog.log(Level.WARNING, nmText.getConfigError(path), var14);
                     return;
                  }

                  this.domains.put(path, dm);
               }
            }
         }
      }

   }

   public DomainManager getDomainManagerIfPresent(String domain) {
      try {
         return this.getOrCreateDomainManager(domain, (String)null, false);
      } catch (IOException | ConfigException var3) {
         nmLog.log(Level.FINE, "Exception in domain config", var3);
         return null;
      }
   }

   public DomainInfo getDomainInfo(String domainName) {
      DomainManager dm = null;

      try {
         dm = this.getOrCreateDomainManager(domainName, (String)null, false);
         return dm;
      } catch (IOException | ConfigException var4) {
         nmLog.log(Level.FINEST, "Exception in domain config", var4);
         return null;
      }
   }

   public DomainManager getDomainManager(String name, String path) throws ConfigException, IOException {
      return this.getOrCreateDomainManager(name, path, true);
   }

   private DomainManager getOrCreateDomainManager(String name, String path, boolean create) throws ConfigException, IOException {
      Map map = this.config.getDomainsMap();
      String tmpPath = (String)map.get(name);
      if (tmpPath != null) {
         String[] domLocations = tmpPath.split(";");
         if (path == null) {
            String[] var12 = domLocations;
            int var8 = domLocations.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String registeredPath = var12[var9];
               if (!registeredPath.isEmpty()) {
                  path = registeredPath;
                  break;
               }
            }
         } else if (!this.isPathRegistered(path, domLocations)) {
            if (!this.config.isDomainRegistrationEnabled()) {
               throw new ConfigException(nmText.getWrongLocationOfTheDomainTaken(name, tmpPath, path));
            }

            nmLog.finest(nmText.getNewLocationOfTheDomainRegistered(name, tmpPath, path));
         }
      } else {
         Iterator theDoms = this.domains.values().iterator();

         while(true) {
            if (!theDoms.hasNext()) {
               if (!this.config.isDomainRegistrationEnabled()) {
                  throw new ConfigException(nmText.getDynamicDomainRegistrationNotAllowed(name, path));
               }

               if (path == null) {
                  nmLog.warning(nmText.getUnregisteredDomainName(name));
                  path = this.config.getWeblogicHome();
               }
               break;
            }

            DomainManager dm = (DomainManager)theDoms.next();
            if (dm.getDomainName().equals(name) && (path == null || dm.getDomainDir().getCanonicalPath().equals((new DomainDir(path)).getCanonicalPath()))) {
               dm.checkFileStamps();
               return dm;
            }
         }
      }

      return this.findOrCreateDomainManager(name, path, create);
   }

   private boolean isPathRegistered(String p1, String[] paths) throws IOException {
      String[] var3 = paths;
      int var4 = paths.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String p2 = var3[var5];
         if (null == p1 || null == p2) {
            throw new IllegalArgumentException("A path can not be null");
         }

         if ((new DomainDir(p1)).getCanonicalPath().equals((new DomainDir(p2)).getCanonicalPath())) {
            return true;
         }
      }

      return false;
   }

   private DomainManager findOrCreateDomainManager(String name, String path, boolean create) throws ConfigException, IOException {
      path = (new File(path)).getCanonicalPath();
      synchronized(this.domains) {
         DomainManager dm = (DomainManager)this.domains.get(path);
         if (dm == null && create) {
            try {
               dm = new DomainManager(this, name, path);
            } catch (ConfigException var8) {
               nmLog.warning(nmText.getDomainInitError(name, path));
               throw var8;
            } catch (IOException var9) {
               nmLog.warning(nmText.getDomainInitError(name, path));
               throw var9;
            }

            this.domains.put(path, dm);
         } else {
            dm.checkFileStamps();
         }

         return dm;
      }
   }

   public void reportDomainError(String domainName, String domainPath) {
      nmLog.warning(nmText.getDomainInitError(domainName, domainPath));
   }

   public NMServerConfig getConfig() {
      return this.config;
   }

   public SSLConfig getSSLConfig() {
      return this.sslConfig;
   }

   public static void main(String[] args) {
      assert inheritedChannel == null || inheritedChannel instanceof ServerSocketChannel : "Unexpected inherited channel" + inheritedChannel;

      for(int i = 0; i < args.length; ++i) {
         if (args[i].equals("-?") || args[i].equals("-h") || args[i].equals("-help")) {
            printUsage();
            System.exit(0);
         }
      }

      try {
         NMServer nms = getInstance(args);
         nms.logVersion();
         nms.start(inheritedChannel);
      } catch (Throwable var2) {
         nmLog.log(Level.SEVERE, nmText.getFatalError(), var2);
         System.exit(1);
      }

   }

   private void parseArguments(String[] args, NMProperties props) {
      try {
         int i = 0;

         while(i < args.length) {
            String arg = args[i++];
            if (arg.equals("-f")) {
               props.setProperty("PropertiesFile", args[i++]);
            } else if (arg.equals("-n")) {
               props.setProperty("NodeManagerHome", args[i++]);
            } else if (arg.equals("-d")) {
               props.setProperty("LogLevel", "ALL");
               this.debug = true;
            } else if (arg.equals("-v")) {
               this.verbose = true;
            } else {
               if (!arg.startsWith("-%")) {
                  throw new IllegalArgumentException(nmText.getUnrecognizedOption(arg));
               }

               String h = args[i++];
               if (h.contains("Q")) {
                  props.setProperty("QuitEnabled", String.valueOf(true));
               }
            }
         }

      } catch (IndexOutOfBoundsException var6) {
         throw new IllegalArgumentException(nmText.getInvalidArgument());
      }
   }

   private static void printUsage() {
      for(int i = 0; i < usageMsg.length; ++i) {
         System.err.println(usageMsg[i]);
      }

   }

   private ServerManagerI findOrCreateServerManager(String srvrName, String domainName, String domainDir) throws IOException, ConfigException {
      if (srvrName != null && srvrName.length() != 0) {
         if (domainName != null && domainName.length() != 0) {
            DomainManager dm = this.getDomainManager(domainName, domainDir);
            if (dm == null) {
               throw new NMException(nmText.getBadDomain(domainName));
            } else {
               return dm.getServerManager(srvrName, "WebLogic");
            }
         } else {
            throw new NMException(nmText.getDomainNameNull());
         }
      } else {
         throw new NMException(nmText.getServerNameNull());
      }
   }

   private void logVersion() {
      try {
         String version = getWLSVersion();
         if (version != null && !version.trim().isEmpty()) {
            nmLog.log(Level.INFO, version);
            return;
         }
      } catch (Throwable var3) {
         nmLog.log(Level.FINEST, "Unable to get webLogic version due to " + var3);
      }

      try {
         nmLog.log(Level.INFO, "Node manager v14.1.1.0.0 #" + this.loadChangeNo());
      } catch (Throwable var2) {
         nmLog.log(Level.FINEST, "Unable to get WebLogic change No.", var2);
         nmLog.log(Level.INFO, "Node manager v14.1.1.0.0");
      }

   }

   private String loadChangeNo() throws IOException {
      File f = new File(new File(Home.getPath(), "lib"), "change.txt");
      FileReader reader = new FileReader(f);

      String var3;
      try {
         var3 = readOneLine(reader);
      } finally {
         reader.close();
      }

      return var3;
   }

   static String readOneLine(Reader reader) throws IOException {
      BufferedReader br = new BufferedReader(reader);

      try {
         String var3;
         for(String line = br.readLine(); line != null; line = br.readLine()) {
            if (!line.trim().isEmpty()) {
               var3 = line.trim();
               return var3;
            }
         }

         var3 = null;
         return var3;
      } finally {
         br.close();
      }
   }

   static String getWLSVersion() throws Exception {
      Class cls = Class.forName("weblogic.version");
      Method m = cls.getMethod("getReleaseBuildVersion");
      String version = (String)m.invoke(cls);
      return version;
   }

   private Server getRuntimeServer() {
      if (this.config.isRestEnabled()) {
         ServiceLoader loader = ServiceLoader.load(Server.class);
         Iterator var2 = loader.iterator();

         Server server;
         String mode;
         do {
            if (!var2.hasNext()) {
               throw new RuntimeException("No server providers found");
            }

            server = (Server)var2.next();
            mode = "REST";
         } while(!mode.equalsIgnoreCase(server.supportedMode()));

         return server;
      } else {
         return new ClassicServer();
      }
   }

   public void logServerStartMessage() {
      if (this.config != null) {
         String msg = null;
         if (this.config.isSecureListener()) {
            msg = this.config.getListenAddress() != null ? nmText.getSecureSocketListenerHost(Integer.toString(this.config.getListenPort()), this.config.getListenAddress()) : nmText.getSecureSocketListener(Integer.toString(this.config.getListenPort()));
         } else {
            msg = this.config.getListenAddress() != null ? nmText.getPlainListenerStartedHost(Integer.toString(this.config.getListenPort()), this.config.getListenAddress()) : nmText.getPlainListenerStarted(Integer.toString(this.config.getListenPort()));
         }

         nmLog.info(msg);
      } else {
         nmLog.info("NMServerConfig is empty");
      }

   }

   public static String getNMVersion() {
      return "14.1.1.0.0";
   }

   static {
      try {
         inheritedChannel = System.inheritedChannel();
      } catch (Exception var1) {
         inheritedChannel = null;
      }

      nmLog = Logger.getLogger("weblogic.nodemanager");
      nmText = NodeManagerTextTextFormatter.getInstance();
      usageMsg = new String[]{"Usage: java weblogic.nodemanager.server.NMServer [OPTIONS]", "", "Where options include:", "  -n <home>  Specify node manager home directory (default is PWD)", "  -f <file>  Specify node manager properties file", "             (default is NM_HOME/nodemanager.properties)", "  -v         Run in verbose mode", "  -d         Enable debug output to log file", "  -?, -h     Print this usage message"};
   }

   class ClassicServer implements Server {
      Listener listener;

      public void init(NMServer nmServer) throws IOException {
         if (NMServer.this.config.isSecureListener()) {
            this.listener = new SSLListener(nmServer, NMServer.inheritedChannel);
         } else {
            this.listener = new Listener(nmServer, NMServer.inheritedChannel);
         }

      }

      public void start(NMServer nmServer) throws IOException {
         try {
            this.listener.init();
         } catch (BindException var4) {
            IOException ioe = new IOException(NMServer.nmText.getAddressInUse(NMServer.this.getConfig().getListenAddress(), String.valueOf(this.listener.port), var4));
            ioe.initCause(var4);
            throw ioe;
         }

         this.listener.run();
      }

      public String supportedMode() {
         return "CLASSIC";
      }
   }
}
