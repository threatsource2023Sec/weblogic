package weblogic.nodemanager.mbean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.logging.LogFileConfigUtil;
import weblogic.logging.Loggable;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceServerMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ManagedExternalServerMBean;
import weblogic.management.configuration.ManagedExternalServerStartMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerStartMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.configuration.UnixMachineMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.PropertyService;
import weblogic.nodemanager.NodeManagerLogger;
import weblogic.nodemanager.ScriptExecutionFailureException;
import weblogic.nodemanager.adminserver.NodeManagerMonitorImpl;
import weblogic.nodemanager.client.NMClient;
import weblogic.nodemanager.client.ShellClient;
import weblogic.nodemanager.common.CoherenceStartupConfig;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.SystemComponentStartupConfig;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.ServerResource;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.security.utils.SSLSetup;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class NodeManagerRuntime implements NodeManagerLifecycleService {
   private String type = "ssl";
   private String host = "localhost";
   private int port = 5556;
   private String cmd = "VERSION";
   private String nmHome = ".";
   private static final String TEMP_FILE_NAME = "nodemanager";
   private static final String TEMP_FILE_EXT = ".tmp";
   private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
   private static final int DEFAULT_CONNECTION_CREATION_TIMEOUT = 180000;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private MachineMBean myMachine;
   private int connectionCreationTimeout = getGlobalConnectionCreationTimeout();
   private static volatile NodeManagerMonitorImpl monitor;
   private final DebugLogger debugLogger;
   private static final Map unregisteredLoggers = new HashMap();
   private static final DebugLogger registeredLogger = DebugLogger.getDebugLogger("DebugNodeManagerRuntime");

   private NodeManagerRuntime() {
      this.debugLogger = registeredLogger;
   }

   private NodeManagerRuntime(String host) {
      this.host = host;
      this.debugLogger = registeredLogger;
   }

   private static DebugLogger getDebugLogger(NodeManagerMBean nmmb) {
      if (!nmmb.isDebugEnabled()) {
         return registeredLogger;
      } else {
         synchronized(unregisteredLoggers) {
            String key = getDebugLoggerKey(nmmb);
            DebugLogger logger = (DebugLogger)unregisteredLoggers.get(key);
            if (logger == null) {
               logger = DebugLogger.createUnregisteredDebugLogger(key, true);
               unregisteredLoggers.put(key, logger);
            }

            return logger;
         }
      }
   }

   private static int getGlobalConnectionCreationTimeout() {
      int connectionCreationTimeout = -1;
      String connectionCreationTimeoutString = null;

      try {
         connectionCreationTimeoutString = System.getProperty("weblogic.nodemanager.socketcreatetimeout");
         if (connectionCreationTimeoutString != null) {
            connectionCreationTimeout = Integer.parseInt(connectionCreationTimeoutString);
         }
      } catch (NumberFormatException var3) {
         if (registeredLogger.isDebugEnabled()) {
            registeredLogger.debug("connectionCreationTimeout is not Integer number:" + connectionCreationTimeoutString);
         }
      } catch (SecurityException var4) {
         if (registeredLogger.isDebugEnabled()) {
            registeredLogger.debug("SecurityExcpetion is raised on connectionCreationTimeout processing:" + var4);
         }
      }

      return connectionCreationTimeout >= 0 ? connectionCreationTimeout : -1;
   }

   private int calculateTimeout(MachineMBean myMachine, ManagedExternalServerMBean mesb, ServerTemplateMBean stb) {
      int nodeManagerMBeanTimeout = -1;
      int managedExternalServerMBeanTimeout = -1;
      int serverTemplateMBeanTimeout = -1;
      if (myMachine != null) {
         NodeManagerMBean nmmb = myMachine.getNodeManager();
         if (nmmb != null) {
            int timeout = nmmb.getNMSocketCreateTimeoutInMillis();
            if (timeout != 180000) {
               nodeManagerMBeanTimeout = timeout;
            }
         }
      }

      int timeout;
      if (mesb != null) {
         timeout = mesb.getNMSocketCreateTimeoutInMillis();
         if (timeout != 180000) {
            managedExternalServerMBeanTimeout = timeout;
         }
      }

      if (stb != null) {
         timeout = stb.getNMSocketCreateTimeoutInMillis();
         if (timeout != 180000) {
            serverTemplateMBeanTimeout = timeout;
         }
      }

      if (this.connectionCreationTimeout >= 0) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("nodemanager connection creation timeout is configured by system property : " + this.connectionCreationTimeout + "." + (nodeManagerMBeanTimeout >= 0 ? " timeout config in NodeManagerMBean configuration exists but is ignored. " : "") + (managedExternalServerMBeanTimeout >= 0 ? " timeout config in ManagedExternalServerMBean configuration exists but is ignored. " : "") + (serverTemplateMBeanTimeout >= 0 ? " timeout config in ServerTemplateMBean configuration exists but is ignored. " : ""));
         }

         return this.connectionCreationTimeout;
      } else if (nodeManagerMBeanTimeout >= 0) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("nodemanager connection creation timeout is configured by NodeManagerMBean : " + nodeManagerMBeanTimeout + "." + (managedExternalServerMBeanTimeout >= 0 ? " timeout config in ManagedExternalServerMBean configuration exists but is ignored. " : "") + (serverTemplateMBeanTimeout >= 0 ? " timeout config in ServerTemplateMBean configuration exists but is ignored. " : ""));
         }

         return nodeManagerMBeanTimeout;
      } else if (managedExternalServerMBeanTimeout >= 0) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("nodemanager connection creation timeout is configured by ManagedExternalServerMBean : " + managedExternalServerMBeanTimeout + ".");
         }

         return managedExternalServerMBeanTimeout;
      } else if (serverTemplateMBeanTimeout >= 0) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("nodemanager connection creation timeout is configured by ServerTemplateMBean : " + serverTemplateMBeanTimeout + ".");
         }

         return serverTemplateMBeanTimeout;
      } else {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("default nodemanager connection creation timeout is used : 180000.");
         }

         return 180000;
      }
   }

   private NodeManagerRuntime(MachineMBean mmb) {
      this.myMachine = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupMachine(mmb.getName());
      DebugLogger logger = registeredLogger;
      if (this.myMachine != null) {
         NodeManagerMBean nmmb = this.myMachine.getNodeManager();
         if (nmmb != null) {
            this.type = nmmb.getNMType();

            assert this.type != null;

            String h = nmmb.getListenAddress();
            if (h != null && h.trim().length() > 0) {
               this.host = h;
            }

            int p = nmmb.getListenPort();
            if (p > 0) {
               this.port = p;
            }

            this.cmd = nmmb.getShellCommand();
            this.nmHome = nmmb.getNodeManagerHome();
            logger = getDebugLogger(nmmb);
            if (logger.isDebugEnabled()) {
               logger.debug("NodeManagerRuntime created");
            }
         }
      }

      this.debugLogger = logger;
   }

   private NodeManagerRuntime(String host, int port, String type) {
      this.host = host;
      this.port = port;
      this.type = type;
      this.debugLogger = registeredLogger;
   }

   private static String getDebugLoggerKey(NodeManagerMBean nmmb) {
      return String.format("NodeManager-%s", nmmb.getName());
   }

   public static void removeDebugLogger(NodeManagerMBean nmmb) {
      String key = getDebugLoggerKey(nmmb);
      synchronized(unregisteredLoggers) {
         unregisteredLoggers.remove(key);
      }
   }

   public static NodeManagerRuntime getInstance(String host, int port, String type) {
      return new NodeManagerRuntime(host, port, type);
   }

   public static NodeManagerRuntime getInstance(MachineMBean mmb) {
      assert Kernel.isServer();

      return mmb != null ? new NodeManagerRuntime(mmb) : new NodeManagerRuntime();
   }

   public static NodeManagerRuntime getInstance(ServerTemplateMBean smb) {
      assert Kernel.isServer();

      MachineMBean mmb = smb.getMachine();
      if (mmb != null) {
         return new NodeManagerRuntime(mmb);
      } else {
         String host = smb.getListenAddress();
         return host != null && host.trim().length() > 0 ? new NodeManagerRuntime(host.trim()) : new NodeManagerRuntime();
      }
   }

   public static NodeManagerRuntime getInstance(ManagedExternalServerMBean smb) {
      assert Kernel.isServer();

      MachineMBean mmb = smb.getMachine();
      return mmb != null ? new NodeManagerRuntime(mmb) : new NodeManagerRuntime();
   }

   public static void checkStartPrivileges(String serverName, AuthenticatedSubject subject) throws SecurityException {
      AuthorizationManager am = SecurityServiceManager.getAuthorizationManager(kernelId, SecurityServiceManager.getAdministrativeRealmName());
      ServerResource resource = new ServerResource((String)null, serverName, "boot");
      if (!am.isAccessAllowed(subject, resource, new ResourceIDDContextWrapper(true))) {
         Loggable loggable = SecurityLogger.logUserNotPermittedToBootLoggable(SubjectUtils.getUsername(subject));
         throw new SecurityException(loggable.getMessageText());
      }
   }

   public NodeManagerTask start(ServerTemplateMBean smb) throws IOException {
      return this.start((ServerTemplateMBean)smb, (String)null);
   }

   public NodeManagerTask start(ServerTemplateMBean smb, String transientServerArgs) throws IOException {
      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Preparing for server startup");
      }

      NMClient nmc = this.getNMClient(smb);
      StartupProperties sp = NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, smb);
      Properties props = sp.getStartupProperties();
      props.putAll(sp.getBootProperties());
      if (transientServerArgs != null) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Applying transientServerArgs: " + transientServerArgs);
         }

         props.setProperty("TransientServerArgs", transientServerArgs);
      }

      String serverName = smb.getName();
      StartRequest sr = new StartRequest(serverName, nmc, props, (Properties)null);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(sr));
      if (this.myMachine != null) {
         NodeManagerLogger.logServerStartOnMachine(smb.getName(), nmc.getHostAndPort(), this.myMachine.getName());
      } else {
         NodeManagerLogger.logServerStartOnMachine1(smb.getName(), nmc.getHostAndPort());
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(serverName, "Server start request task created");
      }

      return sr;
   }

   public NodeManagerTask start(ManagedExternalServerMBean smb) throws IOException {
      return this.startWithProperties(smb, (Properties)null);
   }

   public NodeManagerTask start(SystemComponentMBean smb, Properties runtimeProperties) throws IOException {
      return this.startWithProperties(smb, runtimeProperties);
   }

   private NodeManagerTask startWithProperties(ManagedExternalServerMBean smb, Properties runtimeProperties) throws IOException {
      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Preparing for server startup");
      }

      NMClient nmc = this.getNMClient(smb);
      StartupProperties sp = NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, smb);
      Properties props = sp.getStartupProperties();
      props.putAll(sp.getBootProperties());
      StartRequest sr = new StartRequest(smb.getName(), nmc, props, runtimeProperties);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(sr));
      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Server start request task created");
      }

      return sr;
   }

   public void kill(ServerTemplateMBean smb) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            nmc.kill();
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error executing kill on NodeManager: " + var7, var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'kill' succeeded");
      }

   }

   public void kill(ManagedExternalServerMBean smb) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            nmc.kill();
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error executing kill on NodeManager: " + var7, var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'kill' succeeded");
      }

   }

   public void kill(SystemComponentMBean smb, Properties runtimeProperties) throws IOException {
      try {
         NMClient nmc = this.getNMClient((ManagedExternalServerMBean)smb);

         try {
            if (runtimeProperties != null && !runtimeProperties.isEmpty()) {
               nmc.setRuntimeProperties(runtimeProperties);
            }

            nmc.kill();
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error executing kill on NodeManager: " + var8, var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'kill' succeeded");
      }

   }

   public void softRestart(SystemComponentMBean smb, Properties runtimeProperties) throws IOException {
      try {
         NMClient nmc = this.getNMClient((ManagedExternalServerMBean)smb);

         try {
            if (runtimeProperties != null && !runtimeProperties.isEmpty()) {
               nmc.setRuntimeProperties(runtimeProperties);
            }

            nmc.softRestart();
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error executing softRestart NodeManager: " + var8, var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'softRestart' succeeded");
      }

   }

   public void initState(ServerTemplateMBean smb) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            nmc.initState();
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error initState on NodeManager: " + var7, var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'initState' succeeded");
      }

   }

   public void initState(ManagedExternalServerMBean smb) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            nmc.initState();
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error initState on NodeManager: " + var7, var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'initState' succeeded");
      }

   }

   public void remove(ManagedExternalServerMBean smb) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);
         this.doRemove(nmc);
      } catch (IOException var3) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error removing server on NodeManager: " + var3, var3);
         }

         throw var3;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'remove' succeeded");
      }

   }

   private void doRemove(NMClient nmc) throws IOException {
      try {
         nmc.remove();
      } finally {
         nmc.done();
      }

   }

   public void remove(ServerTemplateMBean smb) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);
         this.doRemove(nmc);
      } catch (IOException var3) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error removing server on NodeManager: " + var3, var3);
         }

         throw var3;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'remove' succeeded");
      }

   }

   public String getStates(boolean allServers, int timeout) throws IOException {
      return this.getStates(allServers, timeout, ' ');
   }

   public String getStates(boolean allServers, int timeout, char separator) throws IOException {
      String states;
      try {
         NMClient nmc = this.getNMClient();

         try {
            states = nmc.getStates(timeout, separator);
         } finally {
            nmc.done();
         }

         ArrayList serverStates = new ArrayList();
         if (allServers) {
            ServerMBean[] servers = ManagementService.getRuntimeAccess(kernelId).getDomain().getServers();
            String normalizedStates = separator + states;

            String state;
            for(int i = 0; i < servers.length; ++i) {
               ServerMBean smb = servers[i];
               ServerStartMBean ssmb = smb.getServerStart();
               state = separator + smb.getName() + "=";
               if (normalizedStates.indexOf(state) < 0 && ssmb != null && smb.getMachine() != null && this.myMachine != null && ssmb.getRootDirectory() != null && smb.getMachine().getName().equals(this.myMachine.getName())) {
                  String svrState = this.getState((ServerTemplateMBean)smb, timeout);
                  serverStates.add(smb.getName() + '=' + svrState);
               }
            }

            if (serverStates.size() > 0) {
               StringBuffer sb = new StringBuffer(states);
               if (sb.length() > 0 && sb.charAt(sb.length() - 1) != separator) {
                  sb.append(separator);
               }

               int loc = 0;
               Iterator var20 = serverStates.iterator();

               while(var20.hasNext()) {
                  state = (String)var20.next();
                  ++loc;
                  sb.append(state);
                  if (loc < serverStates.size()) {
                     sb.append(separator);
                  }
               }

               states = sb.toString();
            }
         }
      } catch (IOException var17) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("", "Error getStates on NodeManager: " + var17, var17);
         }

         throw var17;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("", "Command 'getStates' returned '" + states + "'");
      }

      return states;
   }

   public String getState(ServerTemplateMBean smb, int timeout) throws IOException {
      String state;
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            state = nmc.getState(timeout);
         } finally {
            nmc.done();
         }
      } catch (IOException var9) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error getState on NodeManager: " + var9, var9);
         }

         throw var9;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'getState' returned '" + state + "'");
      }

      return state;
   }

   public String getState(ServerTemplateMBean smb) throws IOException {
      return this.getState(smb, this.calculateTimeout((MachineMBean)null, (ManagedExternalServerMBean)null, smb));
   }

   public String getState(ManagedExternalServerMBean smb, int timeout) throws IOException {
      String state;
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            state = nmc.getState(timeout);
         } finally {
            nmc.done();
         }
      } catch (IOException var9) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error getState on NodeManager: " + var9, var9);
         }

         throw var9;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'getState' returned '" + state + "'");
      }

      return state;
   }

   public String getState(ManagedExternalServerMBean smb) throws IOException {
      return this.getState(smb, this.calculateTimeout((MachineMBean)null, smb, (ServerTemplateMBean)null));
   }

   public void getLog(ServerTemplateMBean smb, Writer out) throws IOException {
      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            nmc.getLog(out);
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error getLog on NodeManager: " + var8, var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'getLog' succeeded");
      }

   }

   public Reader getLog(ServerTemplateMBean smb) throws IOException {
      File tmpFile = null;

      try {
         tmpFile = this.createTempFile();
      } catch (IOException var11) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Could not execute command getLog for server " + smb.getName() + " using the NodeManager. Failed to create output file " + "nodemanager" + ".tmp" + " in the directory " + TEMP_DIR);
         }

         throw var11;
      }

      TempFileReader tmpReader;
      try {
         FileWriter out = new FileWriter(tmpFile);

         try {
            this.getLog(smb, out);
         } finally {
            out.close();
         }

         tmpReader = new TempFileReader(tmpFile);
      } catch (IOException var10) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error connecting to NodeManager: " + var10, var10);
         }

         throw var10;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'getLog' succeeded (tmp file is '" + tmpFile + "'");
      }

      return tmpReader;
   }

   public void getNMLog(Writer out) throws IOException {
      NMClient nmc = this.getNMClient();

      try {
         try {
            nmc.getNMLog(out);
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error getNMLog on NodeManager: " + var7, (Throwable)var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'getNMLog' succeeded");
      }

   }

   public Reader getNMLog() throws IOException {
      File tmpFile = null;

      try {
         tmpFile = this.createTempFile();
      } catch (IOException var10) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Could not execute command getNMLog on the NodeManager. Unable to create file nodemanager.tmp under directory " + TEMP_DIR);
         }

         throw var10;
      }

      TempFileReader tmpReader;
      try {
         FileWriter out = new FileWriter(tmpFile);

         try {
            this.getNMLog(out);
         } finally {
            out.close();
         }

         tmpReader = new TempFileReader(tmpFile);
      } catch (IOException var9) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error getNMLog on NodeManager: " + var9, (Throwable)var9);
         }

         throw var9;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'getNMLog' succeeded (tmp file is '" + tmpFile + "'");
      }

      return tmpReader;
   }

   private File createTempFile() throws IOException {
      File dir = TEMP_DIR != null ? new File(TEMP_DIR) : null;
      return File.createTempFile("nodemanager", ".tmp", dir);
   }

   public void runScript(File script, long timeout) throws IOException, ScriptExecutionFailureException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.execScript(script.getPath(), timeout);
         } finally {
            nmc.done();
         }
      } catch (IOException var9) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error runScript on NodeManager: " + var9, (Throwable)var9);
         }

         throw var9;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'runScript' succeeded");
      }

   }

   public void updateServerProps(ServerMBean smb) throws IOException {
      StartupProperties sp = NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, (ServerTemplateMBean)smb);
      Properties props = sp.getStartupProperties();
      props.putAll(sp.getBootProperties());

      try {
         NMClient nmc = this.getNMClient((ServerTemplateMBean)smb);

         try {
            nmc.updateServerProps(props);
         } finally {
            nmc.done();
         }
      } catch (IOException var9) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Error updateServerProps on NodeManager: " + var9, var9);
         }

         throw var9;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug(smb.getName(), "Command 'updateServerProps' succeeded");
      }

   }

   public void updateDomainCredentials(String oldUser, String oldPwd) throws IOException {
      try {
         NMClient nmc = this.getNMClient();
         nmc.setNMUser(oldUser);
         nmc.setNMPass(oldPwd);
         DomainMBean dmb = ManagementService.getRuntimeAccess(kernelId).getDomain();
         SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();

         try {
            nmc.chgCred(scmb.getNodeManagerUsername(), scmb.getNodeManagerPassword());
         } finally {
            nmc.done();
         }
      } catch (IOException var10) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error updateDomainCredentials on NodeManager: " + var10, (Throwable)var10);
         }

         throw var10;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'chgCred' succeeded");
      }

   }

   public String getVersion() throws IOException {
      String version;
      try {
         NMClient nmc = this.getNMClient();

         try {
            version = nmc.getVersion();
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error getVersion on NodeManager: " + var7, (Throwable)var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'getVersion' returned '" + version + "'");
      }

      return version;
   }

   public void changeList(NMMachineChangeList changes) throws IOException {
      NMClient nmc = this.getNMClient();

      try {
         try {
            nmc.changeList(changes);
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error changeList on NodeManager: " + var7, (Throwable)var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'changeList' succeeded");
      }

   }

   public void syncChangeList(NMMachineChangeList changes) throws IOException {
      NMClient nmc = this.getNMClient();

      try {
         try {
            nmc.syncChangeList(changes);
         } finally {
            nmc.done();
         }
      } catch (IOException var7) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error syncChangeList on NodeManager: " + var7, (Throwable)var7);
         }

         throw var7;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'syncChangeList' succeeded");
      }

   }

   public void invocationRequest(String pluginType, String serverType, String[] command, Writer out) throws IOException {
      NMClient nmc = this.getNMClient();

      try {
         try {
            nmc.setServerType(serverType);
            nmc.invocationRequest(pluginType, command, out);
         } finally {
            nmc.done();
         }
      } catch (IOException var10) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error invocationRequest on NodeManager: " + var10, (Throwable)var10);
         }

         throw var10;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'invocationRequest' succeeded");
      }

   }

   public void invocationRequest(String pluginType, String serverType, String[] command, OutputStream out) throws IOException {
      NMClient nmc = this.getNMClient();

      try {
         try {
            nmc.setServerType(serverType);
            nmc.invocationRequest(pluginType, command, out);
         } finally {
            nmc.done();
         }
      } catch (IOException var10) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error invocationRequest on NodeManager: " + var10, (Throwable)var10);
         }

         throw var10;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'invocationRequest' succeeded");
      }

   }

   /** @deprecated */
   @Deprecated
   public void diagnosticRequest(String serverType, String type, String[] command, Writer out) throws IOException {
      NMClient nmc = this.getNMClient();

      try {
         try {
            nmc.setServerType(serverType);
            nmc.diagnosticRequest(type, command, out);
         } finally {
            nmc.done();
         }
      } catch (IOException var10) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error diagnosticRequest on NodeManager: " + var10, (Throwable)var10);
         }

         throw var10;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'diagnosticRequest' succeeded");
      }

   }

   private NMClient getNMClient() throws IOException {
      if (this.debugLogger.isDebugEnabled()) {
         this.debug("NM type is " + this.type);
      }

      NMClient nmc = NMClient.getInstance(this.type);
      nmc.setVerbose(this.debugLogger.isDebugEnabled());
      nmc.setHost(this.host);
      nmc.setPort(this.port);
      nmc.setConnectionCreationTimeout(this.calculateTimeout(this.myMachine, (ManagedExternalServerMBean)null, (ServerTemplateMBean)null));
      if (this.type.equalsIgnoreCase("ssh") || this.type.equalsIgnoreCase("rsh")) {
         if (this.nmHome != null) {
            nmc.setNMDir(this.nmHome);
         }

         if (this.cmd != null) {
            ((ShellClient)nmc).setShellCommand(this.cmd);
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("Client shell command is '" + this.cmd + '"');
            }
         }
      }

      nmc.setDomainName(ManagementService.getRuntimeAccess(kernelId).getDomainName());

      try {
         DomainMBean dmb = ManagementService.getRuntimeAccess(kernelId).getDomain();
         SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();

         assert scmb != null;

         String user = scmb.getNodeManagerUsername();
         String pass = scmb.getNodeManagerPassword();
         if (user != null && user.length() > 0 && pass != null && pass.length() > 0) {
            nmc.setNMUser(user);
            nmc.setNMPass(pass);
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("Node manager username and password specified");
            }
         }

         return nmc;
      } catch (Throwable var6) {
         throw new IOException(var6.getLocalizedMessage());
      }
   }

   private static NMClient getNMClient(DebugLogger debugLogger, String type, String host, int port, String nmHome, String cmd) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(host + ":" + port + "> <NM type is " + type);
      }

      NMClient nmc = NMClient.getInstance(type);
      nmc.setVerbose(debugLogger.isDebugEnabled());
      nmc.setHost(host);
      nmc.setPort(port);
      if (type.equalsIgnoreCase("ssh") || type.equalsIgnoreCase("rsh")) {
         if (nmHome != null) {
            nmc.setNMDir(nmHome);
         }

         if (cmd != null) {
            ((ShellClient)nmc).setShellCommand(cmd);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(host + ":" + port + "> <Client shell command is '" + cmd + '"');
            }
         }
      }

      nmc.setDomainName(ManagementService.getRuntimeAccess(kernelId).getDomainName());
      DomainMBean dmb = ManagementService.getRuntimeAccess(kernelId).getDomain();
      SecurityConfigurationMBean scmb = dmb.getSecurityConfiguration();

      assert scmb != null;

      String user = scmb.getNodeManagerUsername();
      String pass = scmb.getNodeManagerPassword();
      if (user != null && user.length() > 0 && pass != null && pass.length() > 0) {
         nmc.setNMUser(user);
         nmc.setNMPass(pass);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(host + ":" + port + "> <Node manager username and password specified");
         }
      }

      return nmc;
   }

   private NMClient getNMClient(ServerTemplateMBean smb) throws IOException {
      NMClient nmc = this.getNMClient();
      nmc.setConnectionCreationTimeout(this.calculateTimeout((MachineMBean)null, (ManagedExternalServerMBean)null, smb));
      ServerStartMBean ssmb = smb.getServerStart();
      String dir = ssmb.getRootDirectory();
      if (dir != null) {
         nmc.setDomainDir(dir);
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Root directory for server is '" + dir + "'");
         }
      }

      String serverName = smb.getName();
      nmc.setServerName(serverName);
      nmc.setServerType("WebLogic");
      return nmc;
   }

   private NMClient getNMClient(ManagedExternalServerMBean smb) throws IOException {
      NMClient nmc = this.getNMClient();
      nmc.setConnectionCreationTimeout(this.calculateTimeout((MachineMBean)null, smb, (ServerTemplateMBean)null));
      ManagedExternalServerStartMBean ssmb = smb.getManagedExternalServerStart();
      String dir = ssmb.getRootDirectory();
      if (dir != null) {
         nmc.setDomainDir(dir);
         if (this.debugLogger.isDebugEnabled()) {
            this.debug(smb.getName(), "Root directory for server is '" + dir + "'");
         }
      }

      nmc.setServerName(smb.getName());
      nmc.setServerType(smb.getManagedExternalType());
      return nmc;
   }

   public Properties getBootProperties(ServerTemplateMBean smb) {
      return NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, smb).getBootProperties();
   }

   public Properties getBootProperties(ManagedExternalServerMBean smb) {
      return NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, smb).getBootProperties();
   }

   public Properties getStartupProperties(ServerTemplateMBean smb) {
      return NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, smb).getStartupProperties();
   }

   public Properties getStartupProperties(ManagedExternalServerMBean smb) {
      return NodeManagerRuntime.StartupPropertiesFactory.getStartupProperties(this, smb).getStartupProperties();
   }

   private void debug(String msg) {
      this.debugLogger.debug(this.host + ":" + this.port + "> <" + msg);
   }

   private void debug(String msg, Throwable t) {
      this.debugLogger.debug(this.host + ":" + this.port + "> <" + msg, t);
   }

   private void debug(String serverName, String msg) {
      this.debugLogger.debug(this.host + ":" + this.port + "> <" + serverName + "> <" + msg);
   }

   private void debug(String serverName, String msg, Throwable t) {
      this.debugLogger.debug(this.host + ":" + this.port + "> <" + serverName + "> <" + msg, t);
   }

   public static void setNodeManagerMonitor(NodeManagerMonitorImpl m) {
      monitor = m;
   }

   public void syncMachineIfNecessary(MachineMBean machineMBean) throws IOException {
      if (monitor != null && machineMBean != null) {
         monitor.syncMachineIfNecessary(machineMBean);
      }

   }

   public int execScript(String scriptName, String scriptDir, Map scriptEnv, Writer writer, long timeoutMillis) throws IOException, ScriptExecutionFailureException {
      int rc = true;

      int rc;
      try {
         NMClient nmc = this.getNMClient();

         try {
            rc = nmc.executeScript(scriptName, scriptDir, scriptEnv, writer, timeoutMillis);
         } finally {
            nmc.done();
         }
      } catch (ScriptExecutionFailureException var14) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Script failed during execution: " + var14, (Throwable)var14);
         }

         throw var14;
      } catch (IOException var15) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error executeScript on NodeManager: " + var15, (Throwable)var15);
         }

         throw var15;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'executeScript' succeeded");
      }

      return rc;
   }

   public void restartNM(long timeoutMillis) throws IOException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.restart(timeoutMillis, false);
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error restart on NodeManager: " + var8, (Throwable)var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'restart' succeeded");
      }

   }

   public void restartAll(long timeoutMillis) throws IOException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.restartAll(timeoutMillis, false);
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error restart on NodeManager: " + var8, (Throwable)var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'restart' succeeded");
      }

   }

   public void restartAllAndUpdate(long timeoutMillis) throws IOException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.restartAll(timeoutMillis, true);
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error restart on NodeManager: " + var8, (Throwable)var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'restart' succeeded");
      }

   }

   public void restartNMAndUpdate(long timeoutMillis) throws IOException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.restart(timeoutMillis, true);
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error restart on NodeManager: " + var8, (Throwable)var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'restart' succeeded");
      }

   }

   public void restartAllAsyncAndUpdate() throws IOException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.restartAllAsync(true);
         } finally {
            nmc.done();
         }
      } catch (IOException var6) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error restart async on NodeManager: " + var6, (Throwable)var6);
         }

         throw var6;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'restart async' succeeded");
      }

   }

   public void restartNMAsyncAndUpdate() throws IOException {
      try {
         NMClient nmc = this.getNMClient();

         try {
            nmc.restartAsync(true);
         } finally {
            nmc.done();
         }
      } catch (IOException var6) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error restart async on NodeManager: " + var6, (Throwable)var6);
         }

         throw var6;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'restart async' succeeded");
      }

   }

   public static boolean isReachable(NodeManagerMBean nmmb) {
      String type = "ssl";
      String host = "localhost";
      int port = 5556;
      String cmd = "VERSION";
      String nmHome = ".";
      type = nmmb.getNMType();

      assert type != null;

      String h = nmmb.getListenAddress();
      if (h != null && h.trim().length() > 0) {
         host = h;
      }

      int p = nmmb.getListenPort();
      if (p > 0) {
         port = p;
      }

      cmd = nmmb.getShellCommand();
      nmHome = nmmb.getNodeManagerHome();
      DebugLogger debugLogger = getDebugLogger(nmmb);
      NMClient nmc = getNMClient(debugLogger, type, host, port, nmHome, cmd);

      try {
         nmc.getVersion();
         nmc.done();
         return true;
      } catch (IOException var11) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(host + ":" + port + "> < problem connecting to NM " + nmmb.getName(), var11);
         }

         return false;
      }
   }

   public String getProgress(ServerTemplateMBean smb) throws IOException {
      String retVal = null;

      try {
         NMClient nmc = this.getNMClient(smb);

         try {
            retVal = nmc.getProgress();
         } finally {
            nmc.done();
         }
      } catch (IOException var8) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Error getProgress on NodeManager: " + var8, (Throwable)var8);
         }

         throw var8;
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Command 'getProgress' succeeded");
      }

      return retVal;
   }

   public String toString() {
      return "NodeManagerRuntime(" + (this.myMachine == null ? "null" : this.myMachine.getName()) + "," + System.identityHashCode(this) + ")";
   }

   @Service
   private static class NodeManagerLifecycleServiceGeneratorImpl implements NodeManagerLifecycleServiceGenerator {
      public NodeManagerLifecycleService getInstance(String host, int port, String type) {
         return NodeManagerRuntime.getInstance(host, port, type);
      }

      public NodeManagerLifecycleService getInstance(MachineMBean mmb) {
         return NodeManagerRuntime.getInstance(mmb);
      }

      public NodeManagerLifecycleService getInstance(ServerTemplateMBean smb) {
         return NodeManagerRuntime.getInstance(smb);
      }

      public NodeManagerLifecycleService getInstance(ManagedExternalServerMBean smb) {
         return NodeManagerRuntime.getInstance(smb);
      }

      public void checkStartPrivileges(String serverName, AuthenticatedSubject subject) throws SecurityException {
         NodeManagerRuntime.checkStartPrivileges(serverName, subject);
      }
   }

   private abstract static class ManagedExternalServerStartupProperties extends AbstractStartupProperties {
      private final ManagedExternalServerMBean smb;

      public ManagedExternalServerStartupProperties(NodeManagerRuntime nmr, ManagedExternalServerMBean smb) {
         super(nmr);
         this.smb = smb;
      }

      public Properties getStartupProperties() {
         ManagedExternalServerStartMBean ssmb = this.smb.getManagedExternalServerStart();

         assert ssmb != null;

         StartupConfig.ValuesHolder conf = this.createStartupConfigValuesHolder();
         String s;
         if ((s = this.trim(ssmb.getMWHome())) != null || (s = this.trim(ssmb.getBeaHome())) != null) {
            conf.setMwHome(s);
         }

         if ((s = this.trim(ssmb.getJavaVendor())) != null) {
            conf.setJavaVendor(s);
         }

         if ((s = this.trim(ssmb.getJavaHome())) != null) {
            conf.setJavaHome(s);
         }

         if ((s = this.trim(ssmb.getClassPath())) != null) {
            conf.setClassPath(s);
         }

         if ((s = this.trim(ssmb.getArguments())) != null) {
            conf.setArguments(s);
         }

         MachineMBean mmb = this.smb.getMachine();
         if (mmb != null && mmb instanceof UnixMachineMBean) {
            UnixMachineMBean umb = (UnixMachineMBean)mmb;
            if (umb.isPostBindUIDEnabled() && (s = this.trim(umb.getPostBindUID())) != null) {
               conf.setUid(s);
            }

            if (umb.isPostBindGIDEnabled() && (s = this.trim(umb.getPostBindGID())) != null) {
               conf.setGid(s);
            }
         }

         DomainMBean dmb = ManagementService.getRuntimeAccess(NodeManagerRuntime.kernelId).getDomain();
         if (!this.smb.getName().equals(dmb.getAdminServerName())) {
            conf.setAdminURL(((PropertyService)LocatorUtilities.getService(PropertyService.class)).getAdminHttpUrl());
            if (this.isDebug()) {
               this.debug("StartupProperties: AdminURL = " + conf.getAdminURL());
            }
         } else if (this.isDebug()) {
            this.debug("StartupProperties: AdminURL is not set for Admin server");
         }

         conf.setAutoRestart(this.smb.getAutoRestart());
         conf.setRestartMax(this.smb.getRestartMax());
         conf.setRestartInterval(this.smb.getRestartIntervalSeconds());
         int restartDelay = this.smb.getRestartDelaySeconds();
         if (this.isDebug()) {
            this.debug("StartupProperties: Configured restart delay = " + restartDelay);
         }

         conf.setRestartDelaySeconds(restartDelay);
         return this.getStartupProperties(conf, this.smb.getName());
      }

      public Properties getBootProperties() {
         StartupConfig.ValuesHolder conf = this.createStartupConfigValuesHolder();
         ManagedExternalServerStartMBean ssmb = this.smb.getManagedExternalServerStart();

         assert ssmb != null;

         StartupConfig sConf = conf.toStartupConfig();
         Properties props = sConf.getBootProperties();
         if (this.isDebug()) {
            Iterator it = props.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry e = (Map.Entry)it.next();
               this.debug(this.smb.getName(), "Server boot property '" + e.getKey() + "' is '" + e.getValue() + "'");
            }
         }

         return props;
      }
   }

   @Contract
   public interface CoherenceProperties {
      Properties getStartupProperties(AbstractStartupProperties var1, CoherenceServerMBean var2, StartupConfig.ValuesHolder var3, String var4);
   }

   public static class CoherenceServerStartupProperties extends ManagedExternalServerStartupProperties {
      private final CoherenceServerMBean smb;

      public CoherenceServerStartupProperties(NodeManagerRuntime nmr, CoherenceServerMBean smb) {
         super(nmr, smb);
         this.smb = smb;
      }

      protected StartupConfig createStartupConfig(Properties props) throws ConfigException {
         return new CoherenceStartupConfig(props);
      }

      protected StartupConfig.ValuesHolder createStartupConfigValuesHolder() {
         return new CoherenceStartupConfig.ValuesHolder();
      }

      public Properties getStartupProperties(StartupConfig.ValuesHolder conf, String serverName) {
         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         CoherenceProperties cp = (CoherenceProperties)serviceLocator.getService(CoherenceProperties.class, new Annotation[0]);
         return cp != null ? cp.getStartupProperties(this, this.smb, conf, serverName) : super.getAndLogStartupProperties(conf, serverName);
      }
   }

   private static class SystemComponentStartupProperties extends ManagedExternalServerStartupProperties {
      private SystemComponentStartupProperties(NodeManagerRuntime nmr, ManagedExternalServerMBean smb) {
         super(nmr, smb);
      }

      protected StartupConfig createStartupConfig(Properties props) throws ConfigException {
         return new SystemComponentStartupConfig(props);
      }

      protected StartupConfig.ValuesHolder createStartupConfigValuesHolder() {
         return new SystemComponentStartupConfig.ValuesHolder();
      }

      // $FF: synthetic method
      SystemComponentStartupProperties(NodeManagerRuntime x0, ManagedExternalServerMBean x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class ServerStartupProperties extends AbstractStartupProperties {
      private final ServerTemplateMBean smb;

      public ServerStartupProperties(NodeManagerRuntime nmr, ServerTemplateMBean smb) {
         super(nmr);
         this.smb = smb;
      }

      public Properties getStartupProperties() {
         ServerStartMBean ssmb = this.smb.getServerStart();

         assert ssmb != null;

         StartupConfig.ValuesHolder conf = this.createStartupConfigValuesHolder();
         String s;
         if ((s = this.trim(ssmb.getMWHome())) != null || (s = this.trim(ssmb.getBeaHome())) != null) {
            conf.setMwHome(s);
         }

         if ((s = this.trim(ssmb.getJavaVendor())) != null) {
            conf.setJavaVendor(s);
         }

         if ((s = this.trim(ssmb.getJavaHome())) != null) {
            conf.setJavaHome(s);
         }

         if ((s = this.trim(ssmb.getClassPath())) != null) {
            conf.setClassPath(s);
         }

         if ((s = this.trim(ssmb.getSecurityPolicyFile())) != null) {
            conf.setSecurityPolicyFile(s);
         }

         if ((s = this.trim(ssmb.getArguments())) != null) {
            conf.setArguments(s);
         }

         if ((s = this.trim(this.getSSLArguments(this.smb))) != null) {
            conf.setSSLArguments(s);
         }

         MachineMBean mmb = this.smb.getMachine();
         if (mmb != null && mmb instanceof UnixMachineMBean) {
            UnixMachineMBean umb = (UnixMachineMBean)mmb;
            if (umb.isPostBindUIDEnabled() && (s = this.trim(umb.getPostBindUID())) != null) {
               conf.setUid(s);
            }

            if (umb.isPostBindGIDEnabled() && (s = this.trim(umb.getPostBindGID())) != null) {
               conf.setGid(s);
            }
         }

         String adminName = ManagementService.getRuntimeAccess(NodeManagerRuntime.kernelId).getDomain().getAdminServerName();
         if (!this.smb.getName().equals(adminName)) {
            conf.setAdminURL(((PropertyService)LocatorUtilities.getService(PropertyService.class)).getAdminHttpUrl());
            if (this.isDebug()) {
               this.debug("StartupProperties: AdminURL = " + conf.getAdminURL());
            }
         } else if (this.isDebug()) {
            this.debug("StartupProperties: AdminURL is not set for Admin server");
         }

         conf.setAutoRestart(this.smb.getAutoRestart());
         conf.setRestartMax(this.smb.getRestartMax());
         conf.setRestartInterval(this.smb.getRestartIntervalSeconds());
         int restartDelay = this.smb.getRestartDelaySeconds();
         if (this.isDebug()) {
            this.debug("StartupProperties: Configured restart delay = " + restartDelay);
         }

         ClusterMBean cmb;
         int hcheckDelay;
         if (this.smb.isAutoMigrationEnabled() && (cmb = this.smb.getCluster()) != null) {
            int hcheckInterval = cmb.getHealthCheckIntervalMillis() / 1000;
            int hcheckPeriods = cmb.getHealthCheckPeriodsUntilFencing();
            hcheckDelay = hcheckInterval * hcheckPeriods;
            if (this.isDebug()) {
               this.debug("StartupProperties: Health Check Interval seconds = " + hcheckInterval);
               this.debug("StartupProperties: Health Check Period Before Fencing = " + hcheckPeriods);
            }

            if (hcheckDelay > 0 && restartDelay == 0) {
               if (this.isDebug()) {
                  this.debug("StartupProperties:  Resetting restart delay to " + hcheckDelay);
               }

               restartDelay = hcheckDelay;
            }
         }

         if (this.isDebug()) {
            this.debug("StartupProperties:  Restart delay seconds " + restartDelay);
         }

         conf.setRestartDelaySeconds(restartDelay);
         if (this.smb.isAutoMigrationEnabled()) {
            List ipList = new ArrayList();
            if (!ipList.contains(this.smb.getListenAddress())) {
               ipList.add(this.smb.getListenAddress());
            }

            NetworkAccessPointMBean[] var15 = this.smb.getNetworkAccessPoints();
            hcheckDelay = var15.length;

            for(int var11 = 0; var11 < hcheckDelay; ++var11) {
               NetworkAccessPointMBean nap = var15[var11];
               if (!ipList.contains(nap.getListenAddress())) {
                  ipList.add(nap.getListenAddress());
               }
            }

            if (ipList.isEmpty()) {
               NodeManagerLogger.logNoIPFoundForMigratableServer();
            }

            conf.setServerIPs(StartupConfig.getServerIPsFromList(ipList));
         }

         conf.setLogFileConfig(LogFileConfigUtil.getLogFileConfig(this.smb.getLog()));
         return this.getStartupProperties(conf, this.smb.getName());
      }

      public Properties getBootProperties() {
         StartupConfig.ValuesHolder conf = this.createStartupConfigValuesHolder();
         ServerStartMBean ssmb = this.smb.getServerStart();

         assert ssmb != null;

         String username = ssmb.getUsername();
         String password = ssmb.getPassword();
         if (username != null && username.length() != 0 && password != null && password.length() != 0) {
            ClearOrEncryptedService ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
            String s;
            if ((s = this.trim(ssmb.getUsername())) != null) {
               conf.setUsername(ces.encrypt(s));
            }

            if ((s = ssmb.getPassword()) != null) {
               conf.setPassword(ces.encrypt(s));
            }
         } else {
            ManagementService.getPropertyService(NodeManagerRuntime.kernelId).establishServerBootIdentity(conf);
         }

         SSLMBean sslmb = this.smb.getSSL();
         if ("KeyStores".equals(sslmb.getIdentityAndTrustLocations())) {
            conf.setKeyStoreProperties(SSLSetup.getSSLTrustProperties(this.smb), new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService()));
         }

         StartupConfig sConf = conf.toStartupConfig();
         Properties props = sConf.getBootProperties();
         if (this.isDebug()) {
            Iterator it = props.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry e = (Map.Entry)it.next();
               this.debug(this.smb.getName(), "Server boot property '" + e.getKey() + "' is '" + e.getValue() + "'");
            }
         }

         return props;
      }

      private String getSSLArguments(ServerTemplateMBean smb) {
         SSLMBean sslmb = smb.getSSL();
         if (sslmb == null) {
            return null;
         } else {
            StringBuffer sb = new StringBuffer();
            if (sslmb.isHostnameVerificationIgnored()) {
               sb.append("-Dweblogic.security.SSL.ignoreHostnameVerification=");
               sb.append(sslmb.isHostnameVerificationIgnored());
            }

            String s;
            if ((s = sslmb.getHostnameVerifier()) != null) {
               sb.append(" -Dweblogic.security.SSL.hostnameVerifier=");
               sb.append(s);
            }

            sb.append(" -Dweblogic.ReverseDNSAllowed=");
            sb.append(smb.isReverseDNSAllowed());
            return sb.toString();
         }
      }

      protected StartupConfig createStartupConfig(Properties props) throws ConfigException {
         return new StartupConfig(props);
      }

      protected StartupConfig.ValuesHolder createStartupConfigValuesHolder() {
         return new StartupConfig.ValuesHolder();
      }
   }

   public abstract static class AbstractStartupProperties implements StartupProperties {
      protected final NodeManagerRuntime nmr;

      AbstractStartupProperties(NodeManagerRuntime nmr) {
         this.nmr = nmr;
      }

      protected abstract StartupConfig createStartupConfig(Properties var1) throws ConfigException;

      protected abstract StartupConfig.ValuesHolder createStartupConfigValuesHolder();

      protected Properties getStartupProperties(StartupConfig.ValuesHolder holder, String serverName) {
         return this.getAndLogStartupProperties(holder, serverName);
      }

      protected Properties getAndLogStartupProperties(StartupConfig.ValuesHolder holder, String serverName) {
         StartupConfig sConf = holder.toStartupConfig();
         Properties props = sConf.getStartupProperties();
         if (this.isDebug()) {
            Iterator var5 = props.entrySet().iterator();

            while(var5.hasNext()) {
               Object o = var5.next();
               Map.Entry e = (Map.Entry)o;
               if (this.isDebug()) {
                  this.debug(serverName, "Server start property '" + e.getKey() + "' is '" + e.getValue() + "'");
               }
            }
         }

         return props;
      }

      protected void debug(String msg) {
         this.nmr.debug(msg);
      }

      protected void debug(String serverName, String msg) {
         this.nmr.debug(serverName, msg);
      }

      protected String trim(String s) {
         if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
               return s;
            }
         }

         return null;
      }

      protected boolean isDebug() {
         return this.nmr.debugLogger.isDebugEnabled();
      }
   }

   private static class StartupPropertiesFactory {
      static StartupProperties getStartupProperties(NodeManagerRuntime nmr, ServerTemplateMBean smb) {
         return new ServerStartupProperties(nmr, smb);
      }

      static StartupProperties getStartupProperties(NodeManagerRuntime nmr, ManagedExternalServerMBean smb) {
         return (StartupProperties)(smb instanceof CoherenceServerMBean ? new CoherenceServerStartupProperties(nmr, (CoherenceServerMBean)smb) : new SystemComponentStartupProperties(nmr, smb));
      }
   }

   interface StartupProperties {
      Properties getStartupProperties();

      Properties getBootProperties();
   }
}
