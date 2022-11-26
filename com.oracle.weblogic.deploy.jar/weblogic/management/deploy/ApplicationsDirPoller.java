package weblogic.management.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.application.DeploymentManager;
import weblogic.application.archive.utils.ArchiveUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.deploy.internal.ApplicationPollerLogger;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.FileUtils;

public class ApplicationsDirPoller extends GenericAppPoller {
   private static final boolean debug = false;
   private static final boolean debug2 = false;
   private static final boolean methodTrace = false;
   private static final int MIN_POLLER_INTERVAL = 3000;
   private WebLogicDeploymentManager deployer;
   private boolean firstRun;
   private static final String LAST_RUN_FILE_NAME = ".app_poller_lastrun";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DomainMBean domain;
   private static final String SERVER_CACHE_DIR;
   private Set filesBeingCopied;
   private static final DeploymentManager appDeploymentManager;
   private Map confirmedNames;

   private static String ensureCacheDirInitialized() {
      String serverCacheDir = DomainDir.getCacheDirForServer(ManagementService.getPropertyService(kernelId).getServerName());
      File cacheFile = new File(serverCacheDir);
      if (!cacheFile.exists()) {
         cacheFile.mkdir();
      }

      return serverCacheDir;
   }

   private ApplicationsDirPoller(File startDir, boolean searchRecursively, long sleepInterval, String lastRunFileName) {
      super(startDir, searchRecursively, sleepInterval, lastRunFileName);
      this.confirmedNames = new HashMap();
      this.filesBeingCopied = new HashSet();
      if (!this.startDirFound) {
         boolean created = startDir.mkdir();
         if (!created) {
            ApplicationPollerLogger.logCouldnotCreateAutodeployDir(startDir.toString());
         }
      }

      this.setSleepInterval(sleepInterval);
      this.firstRun = true;

      try {
         this.doit();
      } catch (Throwable var7) {
         ApplicationPollerLogger.logThrowableOnServerStartup(var7);
      }

   }

   public ApplicationsDirPoller(File startDir, boolean searchRecursively, long sleepInterval) {
      this(startDir, searchRecursively, sleepInterval, SERVER_CACHE_DIR + File.separatorChar + ".app_poller_lastrun");
   }

   protected final boolean shouldActivate(File file) {
      if (this.ignoreFile(file)) {
         return false;
      } else if (file.isDirectory()) {
         Long lastCheckPoint = this.getLastCheckPoint(file);
         if (lastCheckPoint == null) {
            this.setCheckPoint(file, (new Date()).getTime());
            return true;
         } else {
            File redeployFile = this.getRedeployFile(file);
            if (redeployFile == null) {
               return false;
            } else {
               this.setCheckPoint(file, redeployFile.lastModified());
               return redeployFile.lastModified() > lastCheckPoint;
            }
         }
      } else {
         return !ArchiveUtils.isValidArchiveName(file.getName()) && !ArchiveUtils.isValidWLSModuleName(file.getName()) ? false : super.shouldActivate(file);
      }
   }

   public final void doActivate() {
      if (this.verbose) {
         Debug.say("doActivate");
      }

      Iterator i = this.getActivateFileList().iterator();

      while(true) {
         DeploymentOptions ddata;
         boolean isPreconfigured;
         File file;
         String appName;
         do {
            if (!i.hasNext()) {
               this.firstRun = false;
               return;
            }

            ddata = this.createDeploymentOptions();
            isPreconfigured = false;
            String fn = (String)i.next();
            file = new File(fn);
            appName = this.getUnusedNameForApp(file);
         } while(appName == null);

         ddata.setName(appName);
         AppDeploymentMBean depmbean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupAppDeployment(appName);
         if (depmbean != null) {
            isPreconfigured = true;
            if (this.firstRun) {
               ApplicationPollerLogger.logRedeployingOnStartup(appName);
            }
         }

         String fileCanPath = file.getAbsolutePath();

         try {
            file = file.getCanonicalFile();
            fileCanPath = file.getCanonicalPath();
         } catch (IOException var14) {
         }

         String stagingMode = null;
         if (!isPreconfigured) {
            if (file.isDirectory()) {
               stagingMode = "nostage";
            } else {
               stagingMode = "stage";
            }

            ddata.setStageMode(stagingMode);
         }

         try {
            if (file.isDirectory() && isPreconfigured || !this.fileIsLocked(file)) {
               ApplicationPollerLogger.logActivate(appName);
               String rootDir = DomainDir.getRootDir();
               debugSay(" +++ rootDir (before canonicalization) : " + rootDir);
               File rootDirFile = new File(rootDir);

               try {
                  rootDir = rootDirFile.getCanonicalPath();
               } catch (IOException var13) {
               }

               debugSay(" +++ rootDir : " + rootDir);
               debugSay(" +++ fileCanPath : " + fileCanPath);
               this.ensureDeploymentMgrInitialized();
               ddata.setDefaultSubmoduleTargets(domain.isAutoDeployForSubmodulesEnabled());
               ProgressObject po;
               if (isPreconfigured) {
                  po = this.deployer.redeploy(new TargetModuleID[0], new File(fileCanPath), (File)null, ddata);
               } else {
                  po = this.deployer.deploy((TargetModuleID[])(new TargetModuleID[0]), new File(fileCanPath), (File)null, ddata);
               }

               if (po.getDeploymentStatus().isFailed()) {
                  throw new ManagementException(po.getDeploymentStatus().getMessage());
               }

               if (file.isDirectory()) {
                  this.warnOnManagedServerTargets(po, appName);
               }

               this.waitForTimeout(po);
            }
         } catch (ManagementException var15) {
            ApplicationPollerLogger.logThrowableOnActivate(appName, var15);
         } catch (TargetException var16) {
            ApplicationPollerLogger.logThrowableOnActivate(appName, var16);
         } catch (DeploymentManagerCreationException var17) {
            ApplicationPollerLogger.logThrowableOnActivate(appName, var17);
         }
      }
   }

   public final void doDeactivate() {
      Iterator i = this.getDeactivateFileList().iterator();

      while(i.hasNext()) {
         String fn = (String)i.next();
         File file = new File(fn);
         String appName = (String)this.confirmedNames.get(file);
         if (appName != null) {
            try {
               AppDeploymentMBean depmbean = domain.lookupAppDeployment(appName);
               if (depmbean == null) {
                  appName = this.getUnusedNameForApp(file);
                  if (appName != null) {
                     depmbean = domain.lookupAppDeployment(appName);
                  }
               }

               if (depmbean != null) {
                  ApplicationPollerLogger.logRemove(appName);
                  TargetModuleID[] tmids = new TargetModuleID[0];
                  DeploymentOptions opts = this.createDeploymentOptions();
                  opts.setName(appName);
                  this.ensureDeploymentMgrInitialized();
                  ProgressObject task = null;

                  try {
                     task = this.deployer.undeploy(tmids, opts);
                  } catch (Exception var10) {
                     throw new ManagementException(var10);
                  }

                  if (task.getDeploymentStatus().isFailed()) {
                     throw new ManagementException(task.getDeploymentStatus().getMessage());
                  }

                  this.waitForTimeout(task);
               }
            } catch (ManagementException var11) {
               ApplicationPollerLogger.logThrowableOnDeactivate(appName, var11);
            } catch (DeploymentManagerCreationException var12) {
               ApplicationPollerLogger.logThrowableOnDeactivate(appName, var12);
            }
         }
      }

   }

   private DeploymentOptions createDeploymentOptions() {
      DeploymentOptions opts = new DeploymentOptions();
      opts.setUseNonexclusiveLock(true);
      opts.setOperationInitiatedByAutoDeployPoller(true);
      return opts;
   }

   private void warnOnManagedServerTargets(ProgressObject task, String appName) {
      TargetModuleID[] tmids = task.getResultTargetModuleIDs();
      if (tmids != null) {
         Set tset = new HashSet();

         for(int i = 0; i < tmids.length; ++i) {
            TargetModuleID tmid = tmids[i];
            tset.add(tmid.getTarget());
         }

         if (tset.size() > 1) {
            ApplicationPollerLogger.logWarnOnManagedServerTargets(appName);
         }
      }

   }

   private void waitForTimeout(ProgressObject task) {
      boolean done = false;

      while(!done) {
         DeploymentStatus state = task.getDeploymentStatus();
         if (!state.isRunning()) {
            return;
         }

         try {
            Thread.sleep(2000L);
         } catch (InterruptedException var5) {
         }
      }

   }

   public void setSleepInterval(long si) {
      if (si < 3000L) {
         super.setSleepInterval(3000L);
      } else {
         super.setSleepInterval(si);
      }

   }

   public static void removeStagedFilesForAppsRemovedSinceLastShutdown() {
      File appDir = new File(DomainDir.getAppPollerDir());
      ArrayList appRemoveList = new ArrayList();
      AppDeploymentMBean[] deployables = AppDeploymentHelper.getAppsAndLibs(domain);

      String editDomain;
      for(int i = 0; deployables != null && i < deployables.length; ++i) {
         AppDeploymentMBean dep = deployables[i];
         editDomain = dep.getAbsoluteSourcePath();
         if (editDomain != null && isInAppsDir(appDir, editDomain)) {
            File path = new File(editDomain);
            if (!path.exists()) {
               String stagingLocation = ManagementService.getRuntimeAccess(kernelId).getServer().getStagingDirectoryName() + File.separatorChar + dep.getName();
               FileUtils.remove(new File(stagingLocation));
               AppDeploymentHelper.destroyAppOrLib(dep, domain);
               if (!dep.isInternalApp()) {
                  appRemoveList.add(dep);
               }
            }
         }
      }

      if (appRemoveList.size() != 0) {
         AppDeploymentMBean[] appRemoveArr = (AppDeploymentMBean[])((AppDeploymentMBean[])appRemoveList.toArray(new AppDeploymentMBean[0]));
         EditAccess editAccess = ManagementServiceRestricted.getEditAccess(kernelId);

         try {
            editAccess.startEdit(0, 120000, true);
         } catch (ManagementException var12) {
            return;
         }

         editDomain = null;

         DomainMBean editDomain;
         try {
            editDomain = editAccess.getDomainBean();
         } catch (ManagementException var11) {
            return;
         }

         for(int i = 0; i < appRemoveArr.length; ++i) {
            AppDeploymentMBean runtimeDep = appRemoveArr[i];
            String appName = runtimeDep.getName();
            Object editDep;
            if (runtimeDep instanceof LibraryMBean) {
               editDep = editDomain.lookupLibrary(appName);
            } else {
               editDep = editDomain.lookupAppDeployment(appName);
            }

            if (editDep != null) {
               AppDeploymentHelper.destroyAppOrLib((AppDeploymentMBean)editDep, editDomain);
            }
         }

         try {
            editAccess.saveChanges();
            editAccess.activateChanges(3600000L);
         } catch (ManagementException var10) {
         }

      }
   }

   private boolean fileIsLocked(File f) {
      FileInputStream fis = null;

      boolean var3;
      try {
         if (!f.isDirectory()) {
            fis = new FileInputStream(f);
            this.fileFree(f);
            var3 = false;
            return var3;
         }

         if (f.renameTo(f)) {
            this.fileFree(f);
            var3 = false;
            return var3;
         }

         this.fileHeld(f);
         this.removeFileFromMap(f);
         var3 = true;
      } catch (IOException var16) {
         this.fileHeld(f);
         this.removeFileFromMap(f);
         boolean var4 = true;
         return var4;
      } finally {
         if (fis != null) {
            try {
               fis.close();
               fis = null;
            } catch (IOException var15) {
               ApplicationPollerLogger.logIOException(var15);
               fis = null;
            }
         }

      }

      return var3;
   }

   private void fileHeld(File f) {
      if (!this.filesBeingCopied.contains(f)) {
         ApplicationPollerLogger.logFileHeld(f);
         this.filesBeingCopied.add(f);
      }
   }

   private void fileFree(File f) {
      if (this.filesBeingCopied.contains(f)) {
         this.filesBeingCopied.remove(f);
      }

   }

   private String getUnusedNameForApp(File file) {
      String confirmedName = null;
      String fname = file.getName();

      try {
         confirmedName = appDeploymentManager.confirmApplicationName(false, file, (File)null, (String)null, "", domain);
         this.confirmedNames.put(file, confirmedName);
      } catch (Exception var13) {
         ApplicationPollerLogger.logThrowableOnActivate(fname, var13);
      }

      AppDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibs(domain);

      for(int i = 0; apps != null && i < apps.length; ++i) {
         AppDeploymentMBean depmbean = apps[i];
         if (!depmbean.isInternalApp()) {
            String appMBeanFileName = depmbean.getAbsoluteSourcePath();
            File appMBeanPath = new File(appMBeanFileName);

            try {
               File f1 = appMBeanPath.getCanonicalFile();
               File f2 = file.getCanonicalFile();
               if (f1.equals(f2)) {
                  return depmbean.getName();
               }
            } catch (IOException var12) {
               ApplicationPollerLogger.logIOException(var12);
            }
         }
      }

      AppDeploymentMBean appWithSameName = domain.lookupAppDeployment(confirmedName);
      if (appWithSameName == null) {
         return confirmedName;
      } else {
         try {
            String retName = appDeploymentManager.confirmApplicationName(false, file, (File)null, confirmedName, "", domain);
            return retName;
         } catch (Exception var11) {
            ApplicationPollerLogger.logThrowableOnActivate(fname, var11);
            return null;
         }
      }
   }

   private File getRedeployFile(File file) {
      File redeployFile = new File(file.getAbsolutePath() + File.separatorChar + "META-INF" + File.separatorChar + "REDEPLOY");
      if (!redeployFile.exists()) {
         redeployFile = new File(file.getAbsolutePath() + File.separatorChar + "WEB-INF" + File.separatorChar + "REDEPLOY");
      }

      return redeployFile.exists() ? redeployFile : null;
   }

   private boolean ignoreFile(File f) {
      return f.getName().startsWith(".wlnot");
   }

   private void ensureDeploymentMgrInitialized() throws DeploymentManagerCreationException {
      if (this.deployer == null) {
         this.deployer = SessionHelper.getDeploymentManager((String)null, (String)null);
      }

   }

   public static boolean isInAppsDir(File appsDir, String pathDirName) {
      String appsDirName = appsDir.getName();

      try {
         appsDir = appsDir.getCanonicalFile();
         appsDirName = appsDir.getCanonicalPath();
      } catch (IOException var6) {
         ApplicationPollerLogger.logIOException(var6);
      }

      File pathDir = new File(pathDirName);

      try {
         pathDir = pathDir.getCanonicalFile();
         pathDirName = pathDir.getCanonicalPath();
      } catch (IOException var5) {
         ApplicationPollerLogger.logIOException(var5);
      }

      return pathDirName.indexOf(appsDirName) > -1;
   }

   private static void debugSay(String msg) {
   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      SERVER_CACHE_DIR = ensureCacheDirInitialized();
      appDeploymentManager = DeploymentManager.getDeploymentManager();
   }
}
