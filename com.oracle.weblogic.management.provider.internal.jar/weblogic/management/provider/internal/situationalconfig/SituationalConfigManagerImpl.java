package weblogic.management.provider.internal.situationalconfig;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.descriptor.Descriptor;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.RuntimeAccessSettable;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.federatedconfig.FederatedConfigLocator;
import weblogic.management.utils.fileobserver.FileChangeListener;
import weblogic.management.utils.fileobserver.FileChangeObserverDirectory;
import weblogic.management.utils.fileobserver.FileChangeObserverMonitor;
import weblogic.management.utils.situationalconfig.SituationalConfigDirectives;
import weblogic.management.utils.situationalconfig.SituationalConfigFile;
import weblogic.management.utils.situationalconfig.SituationalConfigFileProcessor;
import weblogic.management.utils.situationalconfig.SituationalConfigLoader;
import weblogic.management.utils.situationalconfig.SituationalConfigLocator;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.LocatorUtilities;

@Service
@Named
public class SituationalConfigManagerImpl extends AbstractServerService implements SituationalConfigManager, SituationalConfigFileProcessor, PropertyChangeListener, RuntimeAccessSettable {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final long DEFAULT_POLL_INTERVAL = 5000L;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private static boolean noSitConfig = Boolean.getBoolean("weblogic.SituationalConfigDisabled");
   private static String FAIL_BOOT_ON_ERROR_PROP = "weblogic.SituationalConfig.failBootOnError";
   private static boolean failBootOnError;
   private static boolean inprocess;
   private boolean timerLoadComplete = false;
   private boolean deferringNonDynamicChanges = false;
   private long pollInterval = 5000L;
   private FileChangeObserverMonitor fileChangeMonitor;
   private File[] directoriesToObserve;
   private final Map situationalConfigFiles = Collections.synchronizedMap(new HashMap());
   private AtomicBoolean initialized = new AtomicBoolean(false);
   private TimerManager timerManager;
   private Map unexpiredTimers = Collections.synchronizedMap(new HashMap());
   private ServerRuntimeMBean serverRuntime;
   private SituationalPropertiesProcessor situationalPropManager = (SituationalPropertiesProcessor)LocatorUtilities.getService(SituationalPropertiesProcessor.class);
   private static final String SIT_CONFIG_LOC_PROP = "weblogic.SituationalConfigPath";
   private static final String XML_SUFFIX = ".xml";
   private boolean deferNonDynamicChanges = Boolean.valueOf(System.getProperty("weblogic.SituationalConfigDeferNonDynamicChanges", "true"));
   private final LinkedList registerList = new LinkedList();
   boolean lastPassRun = true;
   private List lastExpiredFiles = new ArrayList();

   public static void setInProcess() {
      inprocess = true;
   }

   public ServerRuntimeMBean getServerRuntime() {
      return this.serverRuntime;
   }

   public void setServerRuntime(ServerRuntimeMBean serverRuntime) {
      if (this.serverRuntime != null) {
         throw new AssertionError("ServerRuntimeMBean may only be set once.");
      } else {
         this.serverRuntime = serverRuntime;
         this.setIsInSitConfig();
      }
   }

   public void findAndLoadSitConfigFiles() throws Exception {
      if (!noSitConfig) {
         ArrayList unexpiredFiles;
         synchronized(this.situationalConfigFiles) {
            this.findInitialConfigFiles();

            try {
               unexpiredFiles = this.findUnexpiredFiles();
            } catch (Throwable var5) {
               if (failBootOnError) {
                  ManagementLogger.logFailServerInvalidSituationalConfig(var5);
                  throw new ServiceFailureException(var5);
               }

               throw var5;
            }

            if (this.isSitConfigRequired() && unexpiredFiles.isEmpty()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitConfig] isSitConfigRequired is true and no Sit Config Files exist. Failing boot.");
               }

               String l = ManagementLogger.logSituationalConfigRequired();
               throw new ServiceFailureException(l);
            }
         }

         try {
            this.loadUnexpiredFiles(unexpiredFiles, false);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] Loaded: " + this.listFiles());
               debugLogger.debug("[SitConfig] PollInterval: " + this.pollInterval);
            }
         } catch (Throwable var6) {
            ManagementLogger.logErrorLoadingSitConfigFiles(var6.getMessage());
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] Failure loading unexpired files ", var6);
               var6.printStackTrace();
            }

            if (failBootOnError) {
               ManagementLogger.logFailServerInvalidSituationalConfig(var6);
               throw new ServiceFailureException(var6);
            }
         }

      }
   }

   public void setupTimers() throws Exception {
      if (!noSitConfig) {
         if (!this.timerLoadComplete) {
            this.timerLoadComplete = true;

            try {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitConfig] Starting fileChangeMonitor timer ");
               }

               this.setupFileMonitor();
               this.fileChangeMonitor.start();
            } catch (Throwable var5) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitConfig] Failure starting fileChangeMonitor timer ", var5);
                  var5.printStackTrace();
               }
            }

            SituationalConfigFile situationalConfigFile = null;

            try {
               Iterator var2 = this.registerList.iterator();

               while(var2.hasNext()) {
                  SituationalConfigFile oneSituationalConfigFile = (SituationalConfigFile)var2.next();
                  this.createExpirationTimer(oneSituationalConfigFile);
               }
            } catch (Throwable var4) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitConfig] Failure starting expiration timer " + situationalConfigFile, var4);
                  var4.printStackTrace();
               }
            }

         }
      }
   }

   private void registerListener() {
      String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ServerMBean server = domain.lookupServer(serverName);
      if (server != null) {
         server.addPropertyChangeListener(this);
      }

   }

   private void unregisterListener() {
      String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ServerMBean server = domain.lookupServer(serverName);
      if (server != null) {
         server.removePropertyChangeListener(this);
      }

   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("SitConfigPollingInterval")) {
         this.setPollInterval((long)(Integer)evt.getNewValue());
      }

   }

   public synchronized void initialize(long pollInterval) throws Exception {
      if (!this.initialized.get()) {
         this.initialized.set(true);
         this.pollInterval = pollInterval;
         if (inprocess) {
            this.findAndLoadSitConfigFiles();
            this.setupTimers();
         }
      } else {
         this.setPollInterval(pollInterval);
      }

   }

   public void expireSituationalConfig(SituationalConfigFile situationalConfigFile) {
      ArrayList unexpiredFiles = this.findUnexpiredFiles(false);
      SituationalConfigFile foundSitCfgFile = null;
      Iterator var4 = unexpiredFiles.iterator();

      while(var4.hasNext()) {
         SituationalConfigFile oneSitCfgFile = (SituationalConfigFile)var4.next();
         if (oneSitCfgFile.getFile().getAbsolutePath().equals(situationalConfigFile.getFile().getAbsolutePath())) {
            foundSitCfgFile = oneSitCfgFile;
         }
      }

      if (foundSitCfgFile != null) {
         ((XMLSituationalConfigFile)foundSitCfgFile).setExpired(true);
         unexpiredFiles.remove(foundSitCfgFile);
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Expiring: " + situationalConfigFile.getFile().getAbsolutePath());
         }

         this.loadUnexpiredFiles(unexpiredFiles, this.deferNonDynamicChanges);
         ArrayList expiredFiles = new ArrayList();
         expiredFiles.add(situationalConfigFile.getFile());
         this.reloadDeletedOrExpiredFiles(expiredFiles);
      } catch (Exception var9) {
         ManagementLogger.logErrorLoadingSitConfigFiles(var9.getMessage());
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Exception occurred expiring: " + situationalConfigFile.getFile().getAbsolutePath() + " Exception: " + var9);
         }
      } finally {
         this.removeAndCancelUnexpiredTimer(situationalConfigFile.getFile().getAbsolutePath());
      }

   }

   public void start() throws ServiceFailureException {
      try {
         if (!this.isInitialized()) {
            this.initialize(this.pollInterval);
         }

         try {
            this.registerListener();
         } catch (Throwable var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] unable to register listeners: ", var6);
               var6.printStackTrace();
            }
         }

         synchronized(this.situationalConfigFiles) {
            Iterator var2 = this.situationalConfigFiles.values().iterator();

            while(var2.hasNext()) {
               SituationalConfigFile oneSituationalConfigFile = (SituationalConfigFile)var2.next();
               this.registerList.offer(oneSituationalConfigFile);
            }

         }
      } catch (Exception var7) {
         throw new ServiceFailureException(var7.toString(), var7);
      }
   }

   public void processFiles(Collection addedFiles, Collection changedFiles, Collection deletedFiles) throws Exception {
      if (addedFiles.size() == 0 && changedFiles.size() == 0 && deletedFiles.size() == 0) {
         if (!this.lastPassRun) {
            return;
         }

         this.lastPassRun = false;
      } else {
         this.lastPassRun = true;
      }

      ArrayList unexpiredFiles = new ArrayList();

      try {
         synchronized(this.situationalConfigFiles) {
            this.processAddedOrModifiedFiles(addedFiles);
            this.processAddedOrModifiedFiles(changedFiles);
            this.processDeletedFiles(deletedFiles);
            Iterator var6 = this.situationalConfigFiles.values().iterator();

            while(var6.hasNext()) {
               SituationalConfigFile oneSituationalConfigFile = (SituationalConfigFile)var6.next();
               if (!this.expired(oneSituationalConfigFile)) {
                  unexpiredFiles.add(oneSituationalConfigFile);
                  ((XMLSituationalConfigFile)oneSituationalConfigFile).setExpired(false);
                  this.createExpirationTimer(oneSituationalConfigFile);
               } else {
                  ((XMLSituationalConfigFile)oneSituationalConfigFile).setExpired(true);
               }
            }
         }
      } finally {
         this.loadUnexpiredFiles(unexpiredFiles, this.deferNonDynamicChanges);
         this.loadSystemResources(addedFiles);
         this.loadSystemResources(changedFiles);
         this.loadSystemResources(deletedFiles);
         this.reloadDeletedOrExpiredFiles(deletedFiles);
      }

   }

   public void setPollInterval(long pollInterval) {
      this.pollInterval = pollInterval;

      try {
         FileChangeObserverMonitor fileChangeObserverMonitor = this.getFileChangeMonitor();
         if (fileChangeObserverMonitor != null) {
            fileChangeObserverMonitor.setPollInterval(pollInterval);
         }
      } catch (Exception var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Exception occurred setting the poll interval: ", var4);
         }
      }

   }

   private void setIsInSitConfig() {
      this.setIsInSitConfig((List)null);
   }

   private void setIsInSitConfig(List files) {
      if (this.serverRuntime != null) {
         this.serverRuntime.setInSitConfigState(((SituationalPropertiesProcessorImpl)this.situationalPropManager).isBaseConfigOverridden() || this.isBaseConfigOverridden(files));
      }
   }

   private boolean isBaseConfigOverridden(List unexpiredFiles) {
      if (unexpiredFiles == null) {
         unexpiredFiles = this.findUnexpiredFiles(false);
      }

      if (unexpiredFiles == null) {
         return false;
      } else {
         return ((List)unexpiredFiles).size() > 0;
      }
   }

   public void reload() throws IOException {
      if (!noSitConfig) {
         try {
            ArrayList unexpiredFiles = this.findUnexpiredFiles(false);
            if (unexpiredFiles != null) {
               this.load(unexpiredFiles, this.deferNonDynamicChanges, (Descriptor)null, (HashMap)null);
            }
         } catch (Exception var3) {
            ManagementLogger.logErrorLoadingSitConfigFiles(var3.getMessage());
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] Exception occurred creating Federated Stream: ", var3);
            }

            if (var3 instanceof IOException) {
               throw (IOException)var3;
            } else {
               IOException e = new IOException(var3.toString());
               e.initCause(var3);
               throw e;
            }
         }
      }
   }

   TimerManager getTimerManager() {
      return this.timerManager;
   }

   Map getUnexpiredTimers() {
      return this.unexpiredTimers;
   }

   boolean expired(SituationalConfigFile situationalConfigFile) {
      boolean expired = false;

      long expir;
      try {
         SituationalConfigDirectives situationalConfigDirectives = situationalConfigFile.getSituationalConfigDirectives();
         expir = situationalConfigDirectives == null ? -1L : situationalConfigDirectives.getExpiration();
      } catch (Exception var7) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Exception for " + situationalConfigFile + "  occurred determining if the config has expired: ", var7);
         }

         String l = ManagementLogger.logInvalidSituationalConfigException(situationalConfigFile.toString(), var7);
         throw new IllegalStateException(l, var7);
      }

      if (expir == -1L) {
         expired = false;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] " + situationalConfigFile.toString() + " expired=" + expired + ", time=" + expir);
         }
      } else if (System.currentTimeMillis() >= expir) {
         expired = true;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[SitConfig] " + situationalConfigFile.toString() + " expired=" + expired + ", time=" + expir);
      }

      return expired;
   }

   public boolean isInitialized() {
      return this.initialized.get();
   }

   public long getPollInterval() {
      return this.pollInterval;
   }

   public boolean hasActiveConfiguration() {
      return this.findUnexpiredFiles().size() > 0;
   }

   public boolean isDeferringNonDynamicChanges() {
      return this.deferringNonDynamicChanges;
   }

   SituationalConfigLoader createSituationalConfigLoader(SituationalConfigLocator sitCfgLocator) {
      return this.createSituationalConfigLoader(sitCfgLocator, false);
   }

   SituationalConfigLoader createSituationalConfigLoader(SituationalConfigLocator sitCfgLocator, boolean forceReload) {
      boolean isInSitConfigState = this.serverRuntime != null ? this.serverRuntime.isInSitConfigState() : false;
      return new XMLFileLoader(isInSitConfigState, forceReload, new FederatedConfigLocator[]{sitCfgLocator});
   }

   SituationalConfigLocator createSituationalConfigLocator() {
      return new XMLSituationalConfigLocator();
   }

   File[] getDirectoriesToObserve() {
      if (this.directoriesToObserve == null) {
         String path = System.getProperty("weblogic.SituationalConfigPath", "$optconfig");
         path = path.replaceAll("\\$optconfig", DomainDir.getOptConfigDir());
         path = path.replaceAll("\\$domainroot", DomainDir.getRootDir());
         String serverName = null;
         if (!inprocess) {
            serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
         } else {
            serverName = "bogus";
         }

         String[] paths = path.split("[:]");
         this.directoriesToObserve = new File[paths.length * 5];
         int i = 0;
         String[] var5 = paths;
         int var6 = paths.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String pathval = var5[var7];
            this.directoriesToObserve[i++] = new File(pathval);
            this.directoriesToObserve[i++] = new File(pathval + File.separator + serverName);
            this.directoriesToObserve[i++] = new File(pathval + File.separator + "jms");
            this.directoriesToObserve[i++] = new File(pathval + File.separator + "jdbc");
            this.directoriesToObserve[i++] = new File(pathval + File.separator + "diagnostics");
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Watching " + Arrays.toString(this.directoriesToObserve));
         }
      }

      return this.directoriesToObserve;
   }

   FileChangeObserverMonitor getFileChangeMonitor() {
      return this.fileChangeMonitor;
   }

   int getNumFilesBeingProcessed() {
      return this.situationalConfigFiles.size();
   }

   boolean isFileBeingProcessed(File file) {
      return this.situationalConfigFiles.get(file.getAbsolutePath()) != null;
   }

   private Timer createExpirationTimer(SituationalConfigFile situationalConfigFile) throws Exception {
      SituationalConfigExpirationListener listener = new SituationalConfigExpirationListener(this, situationalConfigFile);
      TimerManager timerManager = this.getTimerManager();
      if (timerManager == null) {
         timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
         this.timerManager = timerManager;
      }

      if (situationalConfigFile != null && situationalConfigFile.getSituationalConfigDirectives() != null) {
         long time = situationalConfigFile.getSituationalConfigDirectives().getExpiration();
         if (time == -1L) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] " + situationalConfigFile.toString() + " will not expire.");
            }

            return null;
         } else {
            Date d = new Date(time);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] " + situationalConfigFile.toString() + " creating Expiration at " + d);
            }

            String path = situationalConfigFile.getFile().getAbsolutePath();
            Timer t = (Timer)this.unexpiredTimers.get(path);
            if (t != null) {
               if (time == t.getTimeout()) {
                  return t;
               }

               this.removeAndCancelUnexpiredTimer(path);
            }

            Timer timer = timerManager.schedule(listener, d);
            ((XMLSituationalConfigFile)situationalConfigFile).setExpired(false);
            this.unexpiredTimers.put(situationalConfigFile.getFile().getAbsolutePath(), timer);
            return timer;
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] situationalConfigFile or config directives is null.");
         }

         return null;
      }
   }

   private void findInitialConfigFiles() {
      File[] var1 = this.getDirectoriesToObserve();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         File dir = var1[var3];
         File[] files = dir.listFiles(new SituationalConfigFileFilter());
         if (files != null) {
            File[] var6 = files;
            int var7 = files.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               File oneFile = var6[var8];
               this.createSitCfgFile(oneFile);
            }
         }
      }

   }

   private void setupFileMonitor() {
      this.fileChangeMonitor = (FileChangeObserverMonitor)LocatorUtilities.getService(FileChangeObserverMonitor.class);
      this.setPollInterval(this.pollInterval);
      File[] var1 = this.getDirectoriesToObserve();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         File dir = var1[var3];
         DirectoryListener fileListener = new DirectoryListener(this);
         FileChangeObserverDirectory observer = new FileChangeObserverDirectory(dir, new SituationalConfigFileFilter(), new FileChangeListener[]{fileListener});
         this.fileChangeMonitor.addFileChangeObserver(observer);
      }

   }

   private void removeExistingSitCfgFile(File file) {
      String path = file.getAbsolutePath();
      this.situationalConfigFiles.remove(path);
      this.removeAndCancelUnexpiredTimer(path);
   }

   private void createSitCfgFile(File file) {
      SituationalConfigFile sitCfgFile = new XMLSituationalConfigFile(file);
      this.situationalConfigFiles.put(file.getAbsolutePath(), sitCfgFile);
   }

   private synchronized void processAddedOrModifiedFiles(Collection files) {
      Iterator var2 = files.iterator();

      while(var2.hasNext()) {
         File file = (File)var2.next();
         this.removeExistingSitCfgFile(file);
         this.createSitCfgFile(file);
      }

   }

   private synchronized void processDeletedFiles(Collection files) {
      Iterator var2 = files.iterator();

      while(var2.hasNext()) {
         File file = (File)var2.next();
         this.removeExistingSitCfgFile(file);
      }

   }

   private ArrayList findUnexpiredFiles() {
      return this.findUnexpiredFiles(true);
   }

   private ArrayList findUnexpiredFiles(boolean throwEx) {
      ArrayList unexpiredFiles = new ArrayList();
      synchronized(this.situationalConfigFiles) {
         Iterator var4 = this.situationalConfigFiles.values().iterator();

         while(var4.hasNext()) {
            SituationalConfigFile oneSituationalConfigFile = (SituationalConfigFile)var4.next();

            try {
               if (!this.expired(oneSituationalConfigFile)) {
                  unexpiredFiles.add(oneSituationalConfigFile);
               }
            } catch (RuntimeException var8) {
               var8.printStackTrace();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitConfig] Error Loading ", var8);
               }

               if (throwEx) {
                  throw var8;
               }
            }
         }

         return unexpiredFiles;
      }
   }

   private static boolean compare(List c1, List c2) {
      return c1.size() != c2.size() ? false : c1.containsAll(c2);
   }

   private void load(List unexpiredFiles, boolean deferOnNonDynamicChanges, Descriptor configDescriptor, HashMap systemResourceDescriptors) {
      try {
         List configFiles = new ArrayList();
         HashMap jmsFiles = new HashMap();
         HashMap jdbcFiles = new HashMap();
         HashMap diagnosticFiles = new HashMap();
         this.mapUnexpiredFilesToComponents(unexpiredFiles, configFiles, jmsFiles, jdbcFiles, diagnosticFiles);
         SituationalConfigLoader situationalConfigLoader = null;
         Iterator var10;
         if (configFiles.size() > 0) {
            situationalConfigLoader = this.createSituationalConfigLoader((List)configFiles);
            if (situationalConfigLoader == null) {
               return;
            }

            var10 = configFiles.iterator();

            while(var10.hasNext()) {
               SituationalConfigFile scf = (SituationalConfigFile)var10.next();
               if (configDescriptor == null) {
                  ManagementLogger.logLoadingSitConfigFile(scf.getFile().getAbsolutePath());
               }
            }

            this.deferringNonDynamicChanges = situationalConfigLoader.loadConfig(deferOnNonDynamicChanges, configDescriptor);
         }

         var10 = jmsFiles.keySet().iterator();

         Iterator var12;
         SituationalConfigFile scf;
         String diagnosticBaseFileName;
         boolean retDeferringNonDynamicChanges;
         while(var10.hasNext()) {
            diagnosticBaseFileName = (String)var10.next();
            var12 = ((List)jmsFiles.get(diagnosticBaseFileName)).iterator();

            while(var12.hasNext()) {
               scf = (SituationalConfigFile)var12.next();
               ManagementLogger.logLoadingSitConfigFile(scf.getFile().getAbsolutePath());
            }

            situationalConfigLoader = this.createSituationalConfigLoader((List)jmsFiles.get(diagnosticBaseFileName));
            retDeferringNonDynamicChanges = situationalConfigLoader.loadJMS(diagnosticBaseFileName, deferOnNonDynamicChanges, systemResourceDescriptors);
            if (retDeferringNonDynamicChanges) {
               this.deferringNonDynamicChanges = true;
            }
         }

         var10 = jdbcFiles.keySet().iterator();

         while(var10.hasNext()) {
            diagnosticBaseFileName = (String)var10.next();
            var12 = ((List)jdbcFiles.get(diagnosticBaseFileName)).iterator();

            while(var12.hasNext()) {
               scf = (SituationalConfigFile)var12.next();
               ManagementLogger.logLoadingSitConfigFile(scf.getFile().getAbsolutePath());
            }

            situationalConfigLoader = this.createSituationalConfigLoader((List)jdbcFiles.get(diagnosticBaseFileName));
            retDeferringNonDynamicChanges = situationalConfigLoader.loadJDBC(diagnosticBaseFileName, deferOnNonDynamicChanges, systemResourceDescriptors);
            if (retDeferringNonDynamicChanges) {
               this.deferringNonDynamicChanges = true;
            }
         }

         var10 = diagnosticFiles.keySet().iterator();

         while(var10.hasNext()) {
            diagnosticBaseFileName = (String)var10.next();
            var12 = ((List)diagnosticFiles.get(diagnosticBaseFileName)).iterator();

            while(var12.hasNext()) {
               scf = (SituationalConfigFile)var12.next();
               ManagementLogger.logLoadingSitConfigFile(scf.getFile().getAbsolutePath());
            }

            situationalConfigLoader = this.createSituationalConfigLoader((List)diagnosticFiles.get(diagnosticBaseFileName));
            retDeferringNonDynamicChanges = situationalConfigLoader.loadDiagnostics(diagnosticBaseFileName, deferOnNonDynamicChanges, systemResourceDescriptors);
            if (retDeferringNonDynamicChanges) {
               this.deferringNonDynamicChanges = true;
            }
         }

         this.setIsInSitConfig(unexpiredFiles);
      } catch (Exception var14) {
         throw new RuntimeException(var14);
      }
   }

   public String listFiles() {
      Map config = new HashMap();
      synchronized(this.situationalConfigFiles) {
         Iterator var3 = this.situationalConfigFiles.values().iterator();

         while(var3.hasNext()) {
            SituationalConfigFile situationalConfigFile = (SituationalConfigFile)var3.next();

            try {
               SituationalConfigDirectives situationalConfigDirectives = situationalConfigFile.getSituationalConfigDirectives();
               long expir = situationalConfigDirectives.getExpiration();
               config.put(situationalConfigFile.toString(), String.valueOf(expir));
            } catch (Exception var9) {
               config.put(situationalConfigFile.toString(), var9.toString());
            }
         }

         return "ConfigFiles: " + config;
      }
   }

   public InputStream getSituationalConfigInputStream(String sysResourceFileName) {
      if (!noSitConfig && this.situationalConfigFiles.size() != 0 && this.isBaseConfigOverridden((List)null)) {
         try {
            List sitConfigFiles = this.getSituationalConfigFilesForSystemResource(this.findUnexpiredFiles(), sysResourceFileName);
            if (sitConfigFiles != null && !sitConfigFiles.isEmpty()) {
               SituationalConfigLoader situationalConfigLoader = this.createSituationalConfigLoader(sitConfigFiles);
               InputStream baseSysResourceStream = new FileInputStream(sysResourceFileName);
               InputStream is = situationalConfigLoader.createFederatedStream(baseSysResourceStream);
               return is;
            } else {
               return null;
            }
         } catch (Exception var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error getting input stream for sit config files for: " + sysResourceFileName, var6);
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public void applyChangesToTemporaryDescriptors(Descriptor configDescriptor, HashMap systemResourceDescriptors) throws IOException {
      if (!noSitConfig && (configDescriptor != null || systemResourceDescriptors != null)) {
         try {
            ArrayList unexpiredFiles = this.findUnexpiredFiles(false);
            if (unexpiredFiles != null) {
               this.load(unexpiredFiles, false, configDescriptor, systemResourceDescriptors);
            }
         } catch (Exception var5) {
            ManagementLogger.logErrorLoadingSitConfigFiles(var5.getMessage());
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] Exception occurred creating Federated Stream: ", var5);
            }

            if (var5 instanceof IOException) {
               throw (IOException)var5;
            } else {
               IOException e = new IOException(var5.toString());
               e.initCause(var5);
               throw e;
            }
         }
      }
   }

   protected boolean isSitConfigRequired() {
      if (failBootOnError) {
         return true;
      } else {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         return server == null ? false : server.isSitConfigRequired();
      }
   }

   private SituationalConfigLoader createSituationalConfigLoader(List unexpiredFiles) throws Exception {
      SituationalConfigLocator sitCfgLocator = this.createSituationalConfigLocator();
      if (sitCfgLocator == null) {
         sitCfgLocator = new XMLSituationalConfigLocator();
      }

      ((SituationalConfigLocator)sitCfgLocator).setFiles(unexpiredFiles);
      return this.createSituationalConfigLoader((SituationalConfigLocator)sitCfgLocator);
   }

   private void loadUnexpiredFiles(List unexpiredFiles, boolean deferOnNonDynamicChanges) throws Exception {
      boolean var15 = false;

      try {
         var15 = true;
         synchronized(this.lastExpiredFiles) {
            if (debugLogger.isDebugEnabled()) {
               Iterator var4 = this.lastExpiredFiles.iterator();

               label114:
               while(true) {
                  SituationalConfigFile s;
                  if (!var4.hasNext()) {
                     var4 = unexpiredFiles.iterator();

                     while(true) {
                        if (!var4.hasNext()) {
                           break label114;
                        }

                        s = (SituationalConfigFile)var4.next();
                        if (!this.lastExpiredFiles.contains(s)) {
                           debugLogger.debug("[SitConfig] " + s.toString() + " Added File");
                        }
                     }
                  }

                  s = (SituationalConfigFile)var4.next();
                  if (!unexpiredFiles.contains(s)) {
                     debugLogger.debug("[SitConfig] " + s.toString() + " Removed File");
                  }
               }
            }
         }

         this.load(unexpiredFiles, deferOnNonDynamicChanges, (Descriptor)null, (HashMap)null);
         var15 = false;
      } finally {
         if (var15) {
            synchronized(this.lastExpiredFiles) {
               this.lastExpiredFiles.clear();
               this.lastExpiredFiles.addAll(unexpiredFiles);
            }
         }
      }

      synchronized(this.lastExpiredFiles) {
         this.lastExpiredFiles.clear();
         this.lastExpiredFiles.addAll(unexpiredFiles);
      }
   }

   private void removeAndCancelUnexpiredTimer(String filePath) {
      Timer timer = (Timer)this.unexpiredTimers.remove(filePath);
      if (timer != null) {
         timer.cancel();
      }

   }

   private void mapUnexpiredFilesToComponents(List unexpiredFiles, List configFiles, HashMap jmsFiles, HashMap jdbcFiles, HashMap diagnosticFiles) {
      Iterator var6 = unexpiredFiles.iterator();

      while(true) {
         while(var6.hasNext()) {
            SituationalConfigFile scf = (SituationalConfigFile)var6.next();
            File f = scf.getFile();
            if (f == null) {
               configFiles.add(scf);
            } else {
               SystemResourceType rt = this.getSystemResourceType(f);
               String matchingFile = rt != SituationalConfigManagerImpl.SystemResourceType.CONFIG && rt != SituationalConfigManagerImpl.SystemResourceType.UNKNOWN ? this.matchingBaseSystemResourceFile(f, rt) : null;
               if (matchingFile != null) {
                  switch (rt) {
                     case JMS:
                        List jmsEntry = (List)jmsFiles.get(matchingFile);
                        if (jmsEntry == null) {
                           jmsEntry = new ArrayList();
                        }

                        ((List)jmsEntry).add(scf);
                        jmsFiles.put(matchingFile, jmsEntry);
                        break;
                     case JDBC:
                        List jdbcEntry = (List)jdbcFiles.get(matchingFile);
                        if (jdbcEntry == null) {
                           jdbcEntry = new ArrayList();
                        }

                        ((List)jdbcEntry).add(scf);
                        jdbcFiles.put(matchingFile, jdbcEntry);
                        break;
                     case DIAGNOSTICS:
                        List diagEntry = (List)diagnosticFiles.get(matchingFile);
                        if (diagEntry == null) {
                           diagEntry = new ArrayList();
                        }

                        ((List)diagEntry).add(scf);
                        diagnosticFiles.put(matchingFile, diagEntry);
                        break;
                     case CONFIG:
                     default:
                        configFiles.add(scf);
                  }
               } else {
                  configFiles.add(scf);
               }
            }
         }

         return;
      }
   }

   private String matchingBaseSystemResourceFile(File sitConfigFile, SystemResourceType rt) {
      int endIndex = sitConfigFile.getName().lastIndexOf("situational-config.xml");
      if (endIndex == -1) {
         return null;
      } else {
         String fileBase = sitConfigFile.getName().substring(0, endIndex - 1) + ".xml";
         String path = null;
         switch (rt) {
            case JMS:
               path = DomainDir.getJMSDir();
               break;
            case JDBC:
               path = DomainDir.getJDBCDir();
               break;
            case DIAGNOSTICS:
               path = DomainDir.getDiagnosticsDir();
               break;
            case CONFIG:
            default:
               return null;
         }

         File f = new File(path, fileBase);
         if (f.exists() && f.isFile()) {
            try {
               return f.getCanonicalPath();
            } catch (IOException var8) {
               return f.getAbsolutePath();
            }
         } else {
            return null;
         }
      }
   }

   private synchronized void loadSystemResources(Collection files) {
      Iterator var2 = files.iterator();

      while(true) {
         String baseFileName;
         do {
            File file;
            SystemResourceType rt;
            do {
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  file = (File)var2.next();
                  rt = this.getSystemResourceType(file);
               } while(rt == SituationalConfigManagerImpl.SystemResourceType.CONFIG);
            } while(rt == SituationalConfigManagerImpl.SystemResourceType.UNKNOWN);

            baseFileName = this.matchingBaseSystemResourceFile(file, rt);
         } while(baseFileName == null);

         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         SystemResourceMBean[] var7 = runtimeAccess.getDomain().getSystemResources();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            SystemResourceMBean sysRes = var7[var9];

            try {
               File baseFile = new File(sysRes.getSourcePath());
               if (!baseFile.getCanonicalPath().equals(baseFileName)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Skipping system resource base file " + baseFile.getAbsolutePath() + " since it does not match " + baseFileName);
                  }
               } else {
                  ApplicationAccess appAccess = ApplicationAccess.getApplicationAccess();
                  ApplicationContextInternal ctx = appAccess.getApplicationContext(sysRes.getName());
                  if (ctx != null) {
                     ctx.reloadConfiguration();
                  }
               }
            } catch (Exception var14) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Skip loading sit config for " + sysRes, var14);
               }
            }
         }
      }
   }

   private synchronized void reloadDeletedOrExpiredFiles(Collection files) {
      Iterator var2 = files.iterator();

      while(true) {
         SystemResourceType rt;
         String baseFileName;
         label92:
         do {
            while(var2.hasNext()) {
               File file = (File)var2.next();
               rt = this.getSystemResourceType(file);
               if (rt != SituationalConfigManagerImpl.SystemResourceType.CONFIG && rt != SituationalConfigManagerImpl.SystemResourceType.UNKNOWN) {
                  baseFileName = this.matchingBaseSystemResourceFile(file, rt);
                  continue label92;
               }

               if (rt == SituationalConfigManagerImpl.SystemResourceType.CONFIG) {
                  ArrayList sitFiles = this.findUnexpiredFiles();
                  Iterator var6 = sitFiles.iterator();

                  while(var6.hasNext()) {
                     SituationalConfigFile scf = (SituationalConfigFile)var6.next();
                     SystemResourceType resourceType = this.getSystemResourceType(scf.getFile());
                     if (resourceType == SituationalConfigManagerImpl.SystemResourceType.CONFIG) {
                        return;
                     }
                  }

                  try {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Reloading config.xml because deleted file: " + file);
                     }

                     SituationalConfigLocator sitCfgLocator = this.createSituationalConfigLocator();
                     SituationalConfigLoader situationalConfigLoader = this.createSituationalConfigLoader(sitCfgLocator, true);
                     boolean retDeferringNonDynamicChanges = false;
                     this.deferringNonDynamicChanges = situationalConfigLoader.loadConfig(this.deferNonDynamicChanges);
                  } catch (Exception var17) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Skip reloading deleted config.xml sit config: " + file, var17);
                     }
                  }
               }
            }

            return;
         } while(baseFileName == null);

         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         SystemResourceMBean[] var22 = runtimeAccess.getDomain().getSystemResources();
         int var24 = var22.length;

         for(int var9 = 0; var9 < var24; ++var9) {
            SystemResourceMBean sysRes = var22[var9];

            try {
               File baseFile = new File(sysRes.getSourcePath());
               if (baseFile.getCanonicalPath().equals(baseFileName)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Reloading system resource for " + sysRes);
                  }

                  List sitConfigFiles = this.getSituationalConfigFilesForSystemResource(this.findUnexpiredFiles(), sysRes.getSourcePath());
                  if (sitConfigFiles != null && !sitConfigFiles.isEmpty()) {
                     return;
                  }

                  SituationalConfigLocator sitCfgLocator = this.createSituationalConfigLocator();
                  SituationalConfigLoader situationalConfigLoader = this.createSituationalConfigLoader(sitCfgLocator, true);
                  boolean retDeferringNonDynamicChanges = false;
                  if (rt == SituationalConfigManagerImpl.SystemResourceType.JMS) {
                     retDeferringNonDynamicChanges = situationalConfigLoader.loadJMS(baseFileName, this.deferNonDynamicChanges, (HashMap)null);
                  } else if (rt == SituationalConfigManagerImpl.SystemResourceType.DIAGNOSTICS) {
                     retDeferringNonDynamicChanges = situationalConfigLoader.loadDiagnostics(baseFileName, this.deferNonDynamicChanges, (HashMap)null);
                  } else if (rt == SituationalConfigManagerImpl.SystemResourceType.JDBC) {
                     retDeferringNonDynamicChanges = situationalConfigLoader.loadJDBC(baseFileName, this.deferNonDynamicChanges, (HashMap)null);
                  }

                  if (retDeferringNonDynamicChanges) {
                     this.deferringNonDynamicChanges = true;
                  }
               }
            } catch (Exception var16) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Skip reloading deleted sit config for " + sysRes, var16);
               }
            }
         }
      }
   }

   private SystemResourceType getSystemResourceType(File f) {
      File parent = f.getParentFile();
      SystemResourceType rt = SituationalConfigManagerImpl.SystemResourceType.UNKNOWN;
      if (!f.getName().endsWith(".xml")) {
         return rt;
      } else {
         if (parent.getName().equals("jms")) {
            rt = SituationalConfigManagerImpl.SystemResourceType.JMS;
         } else if (parent.getName().equals("jdbc")) {
            rt = SituationalConfigManagerImpl.SystemResourceType.JDBC;
         } else if (parent.getName().equals("diagnostics")) {
            rt = SituationalConfigManagerImpl.SystemResourceType.DIAGNOSTICS;
         } else {
            rt = SituationalConfigManagerImpl.SystemResourceType.CONFIG;
         }

         return rt;
      }
   }

   private synchronized List getSituationalConfigFilesForSystemResource(List files, String sysResourceFileName) {
      List retFiles = new ArrayList();
      Iterator var4 = files.iterator();

      while(var4.hasNext()) {
         SituationalConfigFile file = (SituationalConfigFile)var4.next();

         try {
            File baseFile = new File(sysResourceFileName);
            File f = file.getFile();
            if (f != null) {
               SystemResourceType rt = this.getSystemResourceType(f);
               if (rt != SituationalConfigManagerImpl.SystemResourceType.CONFIG && rt != SituationalConfigManagerImpl.SystemResourceType.UNKNOWN) {
                  String matchingBaseFileName = this.matchingBaseSystemResourceFile(f, rt);
                  if (matchingBaseFileName != null) {
                     if (!baseFile.getCanonicalPath().equals(matchingBaseFileName)) {
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("Skipping system resource base file " + baseFile.getAbsolutePath() + " since it does not match " + matchingBaseFileName);
                        }
                     } else {
                        retFiles.add(file);
                     }
                  }
               }
            }
         } catch (Exception var10) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Skip matching for file: " + file, var10);
            }
         }
      }

      return retFiles;
   }

   static {
      failBootOnError = Boolean.getBoolean(FAIL_BOOT_ON_ERROR_PROP);
      inprocess = false;
   }

   static enum SystemResourceType {
      JMS,
      JDBC,
      DIAGNOSTICS,
      CONFIG,
      UNKNOWN;
   }
}
