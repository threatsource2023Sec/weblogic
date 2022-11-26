package weblogic.management.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.management.InvalidAttributeValueException;
import weblogic.Home;
import weblogic.application.utils.EarUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.tools.Inspector;
import weblogic.deploy.api.tools.ModuleInfo;
import weblogic.deploy.common.Debug;
import weblogic.management.DistributedManagementException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.EJBComponentMBean;
import weblogic.management.configuration.JDBCPoolComponentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.http.HttpParsing;

public class ApplicationMigrationProcessor implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean root) throws UpdateException {
      ApplicationMBean[] applications = root.getApplications();

      for(int i = 0; i < applications.length; ++i) {
         ApplicationMBean application = applications[i];
         this.migrateStagingDirectoryIfNeeded(application, root);
         this.migrateAppIfLoadedFromOldAppsDir(application, root);
         this.createAppDeploymentForApplication(root, application);
         root.destroyApplication(application);
      }

      File oldApplicationDir = getOldAppPollerDir(root);
      printFileList(oldApplicationDir);
      String[] listOfRemainingFiles = oldApplicationDir.list();
      if (listOfRemainingFiles != null && listOfRemainingFiles.length > 0) {
         File newAppPollerDir = new File(DomainDir.getAppPollerDir());

         try {
            FileUtils.copyPreservePermissions(oldApplicationDir, newAppPollerDir);
         } catch (IOException var7) {
            throw new UpdateException(var7);
         }
      }

      FileUtils.remove(oldApplicationDir);
      this.migrateDefaultWebApps(root);
   }

   private void migrateStagingDirectoryIfNeeded(ApplicationMBean application, DomainMBean domain) throws UpdateException {
      if (isDebugEnabled()) {
         debugSay(" +++ Migrating Staging directory ...");
      }

      if (application.getStagingMode() == "external_stage") {
         List targets = this.getRealTargets(application, domain);
         if (isDebugEnabled()) {
            debugSay(" +++ Collected Targets : " + targets);
         }

         Iterator iter = targets.iterator();

         while(iter.hasNext()) {
            TargetMBean theTarget = (TargetMBean)iter.next();
            this.migrateStagingDirectoryIfNeededForTarget(application, theTarget);
         }

         if (isDebugEnabled()) {
            debugSay(" +++ Migration of Staging directory completed");
         }

      }
   }

   private void migrateStagingDirectoryIfNeededForTarget(ApplicationMBean application, TargetMBean target) throws UpdateException {
      if (target instanceof ServerMBean) {
         ServerMBean server = (ServerMBean)target;
         String server81StyleDirName = server.get81StyleDefaultStagingDirName();
         if (server81StyleDirName == null) {
            return;
         }

         String oldDefaultStagingDirName = server81StyleDirName + File.separatorChar + application.getName();
         if (isDebugEnabled()) {
            debugSay(" +++ oldDefaultStagingDirName : " + oldDefaultStagingDirName);
         }

         File oldDefaultStagingDir = new File(oldDefaultStagingDirName);
         if (!oldDefaultStagingDir.exists()) {
            return;
         }

         String serverDefaultStagingDir = server.getDefaultStagingDirName();
         if (serverDefaultStagingDir == null) {
            return;
         }

         String newDefaultStagingDir = serverDefaultStagingDir + File.separatorChar + application.getName();
         if (isDebugEnabled()) {
            debugSay(" +++ newDefaultStagingDir : " + newDefaultStagingDir);
         }

         String serverStagingDir = server.getStagingDirectoryName();
         if (serverStagingDir == null) {
            return;
         }

         String appStagingDir = serverStagingDir + File.separatorChar + application.getName();
         if (isDebugEnabled()) {
            debugSay(" +++ appStagingDir : " + appStagingDir);
         }

         if (!newDefaultStagingDir.equals(appStagingDir)) {
            return;
         }

         try {
            FileUtils.copyPreserveTimestampsPreservePermissions(oldDefaultStagingDir, new File(newDefaultStagingDir));
            if (isDebugEnabled()) {
               debugSay(" +++ copied oldDefaultStagingDir(" + oldDefaultStagingDir + ") to newDefaultStagingDir(" + newDefaultStagingDir + ")");
            }

            FileUtils.remove(oldDefaultStagingDir);
            if (isDebugEnabled()) {
               debugSay(" +++ deleted oldDefaultStagingDir(" + oldDefaultStagingDir + ")");
            }
         } catch (IOException var12) {
            throw new UpdateException(var12);
         }
      }

   }

   private List getRealTargets(ApplicationMBean app, DomainMBean root) {
      List results = new ArrayList();
      ComponentMBean[] comps = app.getComponents();
      if (comps != null) {
         for(int i = 0; i < comps.length; ++i) {
            List compTargets = this.getRealTargets(comps[i], root);
            if (((List)results).isEmpty()) {
               results = compTargets;
            } else {
               for(Iterator iter = compTargets.iterator(); iter.hasNext(); results = this.addTargetToListIfNeeded((TargetMBean)iter.next(), (List)results)) {
               }
            }
         }
      }

      return (List)results;
   }

   private List getRealTargets(ComponentMBean comp, DomainMBean root) {
      List results = new ArrayList();
      int i;
      if (comp instanceof WebAppComponentMBean) {
         TargetMBean[] vhosts = ((WebAppComponentMBean)comp).getVirtualHosts();
         if (vhosts != null) {
            for(i = 0; i < vhosts.length; ++i) {
               Set serverNames = vhosts[i].getServerNames();

               ServerMBean phyTarget;
               for(Iterator iter = serverNames.iterator(); iter.hasNext(); results = this.addTargetToListIfNeeded(phyTarget, (List)results)) {
                  phyTarget = root.lookupServer((String)iter.next());
               }
            }
         }
      }

      TargetMBean[] compTargets = comp.getTargets();
      if (compTargets != null) {
         if (((List)results).isEmpty()) {
            List compTargetsList = Arrays.asList(compTargets);
            ((List)results).addAll(compTargetsList);
            if (isDebugEnabled()) {
               debugSay(" +++ Added targets : " + compTargetsList);
            }
         } else {
            for(i = 0; i < compTargets.length; ++i) {
               results = this.addTargetToListIfNeeded(compTargets[i], (List)results);
            }
         }
      }

      return (List)results;
   }

   private List addTargetToListIfNeeded(TargetMBean target, List results) {
      if (target != null) {
         if (target instanceof ClusterMBean) {
            ClusterMBean theCluster = (ClusterMBean)target;
            ServerMBean[] allServers = theCluster.getServers();

            for(int i = 0; i < allServers.length; ++i) {
               results = this.addTargetToListIfNeeded(allServers[i], results);
            }

            return results;
         }

         if (!results.contains(target)) {
            results.add(target);
            if (isDebugEnabled()) {
               debugSay(" +++ Added target : " + target.getName());
            }
         }
      }

      return results;
   }

   private void migrateDefaultWebApps(DomainMBean root) {
      ServerMBean[] servers = root.getServers();
      if (servers != null) {
         for(int i = 0; i < servers.length; ++i) {
            this.migrateDefaultWebApps(servers[i].getWebServer());
         }
      }

      VirtualHostMBean[] vhosts = root.getVirtualHosts();
      if (vhosts != null) {
         for(int i = 0; i < vhosts.length; ++i) {
            this.migrateDefaultWebApps((WebServerMBean)vhosts[i]);
         }
      }

   }

   private void migrateDefaultWebApps(WebServerMBean webServer) {
      if (!webServer.isSet("DefaultWebAppContextRoot") && webServer.isSet("DefaultWebApp") && webServer.getDefaultWebApp() != null) {
         String contextRoot = this.findContextRoot(webServer.getDefaultWebApp());
         if (contextRoot != null) {
            webServer.setDefaultWebAppContextRoot(contextRoot);
         }

      }
   }

   private String findContextRoot(WebAppComponentMBean mbean) {
      return HttpParsing.ensureStartingSlash(mbean.getName());
   }

   private AppDeploymentMBean createAppDeploymentForApplication(DomainMBean root, ApplicationMBean app) throws UpdateException {
      if (isDebugEnabled()) {
         debugSay("creating AppDeploymentMBean for " + app);
      }

      boolean ignoreStrickCheck = false;
      int type = app.getInternalType();
      if (type == 4) {
         if ((new File(app.getPath())).exists()) {
            DeployerRuntimeLogger.logUnknownAppType(app.getName(), app.getPath());
            return null;
         }

         if (!app.getPath().endsWith(".ear") && app.getComponents().length <= 1) {
            type = 1;
         } else {
            type = 0;
         }

         ignoreStrickCheck = true;
      }

      AppDeploymentMBean appdeployment = root.lookupAppDeployment(app.getName());
      if (appdeployment != null) {
         String msg = DeployerRuntimeLogger.appAlreadyExists(app.getName());
         throw new UpdateException(msg);
      } else {
         appdeployment = this.createAppDeploymentMBean(type, app, root);
         app.setDelegationEnabled(false);

         try {
            this.setAppDeploymentTargets(app, appdeployment, ignoreStrickCheck);
            appdeployment.setDeploymentOrder(app.getLoadOrder());
            if (app.getStagingMode() != null) {
               appdeployment.setStagingMode(app.getStagingMode());
            }

            appdeployment.setInternalApp(false);
            appdeployment.setSecurityDDModel("Advanced");
         } catch (Throwable var7) {
            DeployerRuntimeLogger.logApplicationUpgradeProblem(app.getName(), var7);
            root.destroyAppDeployment(appdeployment);
         }

         app.setDelegationEnabled(true);
         return appdeployment;
      }
   }

   private AppDeploymentMBean createAppDeploymentMBean(int type, ApplicationMBean app, DomainMBean root) {
      String moduleType = null;
      String sourcePath;
      if (type != 2 && type != 0) {
         ComponentMBean[] comps = app.getComponents();
         if (comps != null && comps.length == 1) {
            String uri = app.getComponents()[0].getURI();
            if (!uri.equals("jms-xa-adp.rar") && !uri.equals("jms-local-adp.rar") && !uri.equals("jms-notran-adp.rar") && !uri.equals("jms-notran-adp51.rar")) {
               sourcePath = app.getPath() + File.separatorChar + app.getComponents()[0].getURI();
            } else {
               Home.getHome();
               String newBeaHome = Home.getPath();
               sourcePath = newBeaHome + File.separatorChar + "lib" + File.separatorChar + app.getComponents()[0].getURI();
            }

            moduleType = this.getTypeFromComp(app.getComponents()[0]);
         } else {
            DeployerRuntimeLogger.getOldActivateLoggable(app.getName()).log();
            sourcePath = app.getPath();
         }
      } else {
         sourcePath = app.getPath();
         moduleType = ModuleType.EAR.toString();
      }

      AppDeploymentMBean appdeployment = root.createAppDeployment(app.getName(), sourcePath);
      appdeployment.setModuleType(moduleType);
      return appdeployment;
   }

   private void setAppDeploymentTargets(ApplicationMBean oldmb, AppDeploymentMBean newmb, boolean ignoreStrickCheck) throws DistributedManagementException, InvalidAttributeValueException, UpdateException {
      ComponentMBean[] comps = oldmb.getComponents();
      if (comps != null) {
         if (comps.length == 1) {
            newmb.setTargets(comps[0].getTargets());
            String compName = comps[0].getName();
            if (!compName.equals(newmb.getName())) {
               newmb.setCompatibilityName(compName);
               newmb.setModuleType(this.getTypeFromComp(comps[0]));
            }
         } else {
            this.setAppDeploymentTargetsForEAR(oldmb, newmb, ignoreStrickCheck);
         }

      }
   }

   private void setAppDeploymentTargetsForEAR(ApplicationMBean appmbean, AppDeploymentMBean newmb, boolean ignoreStrickCheck) throws InvalidAttributeValueException, DistributedManagementException, UpdateException {
      Map targetListCache = new HashMap();
      Inspector inspector = null;
      ModuleInfo[] modules = null;
      if (!ignoreStrickCheck) {
         try {
            inspector = new Inspector(new File(appmbean.getPath()));
            modules = inspector.getModuleInfo().getSubModules();
         } catch (Throwable var14) {
            if (inspector != null) {
               inspector.close();
            }

            throw new UpdateException(var14.getMessage());
         }
      }

      boolean hasAllModules = ignoreStrickCheck || this.hasAllModules(appmbean, modules);
      ComponentMBean[] comps = appmbean.getComponents();

      for(int i = 0; comps != null && i < comps.length; ++i) {
         TargetMBean[] targets = this.getTargetsAndVirtualHosts(comps[i]);

         for(int j = 0; j < targets.length; ++j) {
            String sdName = this.getNameForSubDeployment(comps[i], modules, ignoreStrickCheck);
            SubDeploymentMBean mt = newmb.lookupSubDeployment(sdName);
            if (mt == null) {
               mt = newmb.createSubDeployment(sdName);
            }

            mt.setCompatibilityName(comps[i].getName());
            mt.setModuleType(this.getTypeFromComp(comps[i]));
            if (hasAllModules && this.isGlobalTarget(comps, targets[j], targetListCache)) {
               this.addTarget(newmb, targets[j]);
            } else {
               this.addTarget(mt, targets[j]);
            }
         }
      }

      if (inspector != null) {
         inspector.close();
      }

   }

   private void addTarget(TargetInfoMBean bean, TargetMBean newtarget) throws DistributedManagementException, InvalidAttributeValueException {
      if (bean.getTargets() != null && bean.getTargets().length != 0) {
         TargetMBean[] targets = bean.getTargets();

         for(int i = 0; targets != null && i < targets.length; ++i) {
            if (targets[i].getName().equals(newtarget.getName())) {
               return;
            }
         }

         bean.addTarget(newtarget);
      } else {
         bean.addTarget(newtarget);
      }

   }

   private boolean hasAllModules(ApplicationMBean appmbean, ModuleInfo[] modules) {
      return modules.length == appmbean.getComponents().length;
   }

   private String getNameForSubDeployment(ComponentMBean comp, ModuleInfo[] modInfos, boolean ignoreStrickCheck) {
      if (comp instanceof WebAppComponentMBean) {
         String ctx = this.getContextRoot(comp, modInfos, ignoreStrickCheck);
         if (ctx != null && !"".equals(ctx) && !"/".equals(ctx)) {
            return ctx;
         }
      }

      return comp.getURI() == null ? comp.getName() : comp.getURI();
   }

   private String getContextRoot(ComponentMBean comp, ModuleInfo[] modules, boolean ignoreStrickCheck) {
      if (ignoreStrickCheck) {
         return null;
      } else {
         String result = null;
         if (modules.length == 0) {
            throw new AssertionError("EAR has no modules.");
         } else {
            for(int j = 0; modules != null && j < modules.length; ++j) {
               if (modules[j].getName().equals(comp.getURI())) {
                  String[] ctxts = modules[j].getContextRoots();
                  if (ctxts != null && ctxts.length > 0) {
                     if (ctxts.length == 1) {
                        result = EarUtils.fixAppContextRoot(ctxts[0]);
                     } else {
                        result = comp.getName();
                     }
                  } else {
                     result = null;
                  }
               }
            }

            return result;
         }
      }
   }

   private String getTypeFromComp(ComponentMBean comp) {
      if (comp instanceof WebAppComponentMBean) {
         return ModuleType.WAR.toString();
      } else if (comp instanceof EJBComponentMBean) {
         return ModuleType.EJB.toString();
      } else if (comp instanceof ConnectorComponentMBean) {
         return ModuleType.RAR.toString();
      } else {
         return comp instanceof JDBCPoolComponentMBean ? WebLogicModuleType.JDBC.toString() : "unknown";
      }
   }

   private TargetMBean[] getTargetsAndVirtualHosts(ComponentMBean comp) {
      ArrayList alltargets = new ArrayList();
      TargetMBean[] targets = comp.getTargets();
      if (targets != null) {
         alltargets.addAll(Arrays.asList(targets));
      }

      if (comp instanceof WebAppComponentMBean) {
         TargetMBean[] vhosts = ((WebAppComponentMBean)comp).getVirtualHosts();
         if (vhosts != null) {
            alltargets.addAll(Arrays.asList(vhosts));
         }
      }

      return (TargetMBean[])((TargetMBean[])alltargets.toArray(new TargetMBean[alltargets.size()]));
   }

   private boolean isGlobalTarget(ComponentMBean[] allcomps, TargetMBean t, Map targetListCache) {
      for(int i = 0; i < allcomps.length; ++i) {
         Set targetList = this.getOrCreateTargetList(allcomps[i], targetListCache);
         if (!targetList.contains(t.getName())) {
            return false;
         }
      }

      return true;
   }

   private Set getOrCreateTargetList(ComponentMBean c, Map targetListCache) {
      Set result = (Set)targetListCache.get(c.getName() + c.getType());
      if (result == null) {
         result = new HashSet();
         TargetMBean[] targets = this.getTargetsAndVirtualHosts(c);

         for(int i = 0; i < targets.length; ++i) {
            ((Set)result).add(targets[i].getName());
         }

         targetListCache.put(c.getName() + c.getType(), result);
      }

      return (Set)result;
   }

   private boolean ensureNewAppPollerDirExists(File newAppPollerDir) throws UpdateException {
      boolean newAppPollerDirCreated = true;
      if (!newAppPollerDir.exists()) {
         newAppPollerDirCreated = newAppPollerDir.mkdir();
      }

      if (!newAppPollerDirCreated) {
         String errMsg = ApplicationPollerLogger.logCouldnotCreateAutodeployDirLoggable(newAppPollerDir.toString()).getMessage();
         throw new UpdateException(errMsg);
      } else {
         return newAppPollerDirCreated;
      }
   }

   private void migrateAppIfLoadedFromOldAppsDir(ApplicationMBean appMBean, DomainMBean root) throws UpdateException {
      String appPath = appMBean.getPath();
      File appFile = new File(appPath);
      File newAppPollerDir = new File(DomainDir.getAppPollerDir());
      String newAppPollerDirName = newAppPollerDir.getAbsolutePath();
      boolean pollerDirExists = this.ensureNewAppPollerDirExists(newAppPollerDir);
      if (pollerDirExists) {
         File oldAppPollerDir = getOldAppPollerDir(root);
         String oldAppPollerDirName = oldAppPollerDir.getAbsolutePath();
         if (oldAppPollerDir.exists()) {
            try {
               if (!appFile.isAbsolute()) {
                  appFile = new File(DomainDir.getRootDir(), appPath);
               }

               appFile = appFile.getCanonicalFile();
               appPath = appFile.getCanonicalPath();
               oldAppPollerDir = oldAppPollerDir.getCanonicalFile();
               oldAppPollerDirName = oldAppPollerDir.getCanonicalPath();
            } catch (IOException var17) {
            }

            String errMsg;
            try {
               newAppPollerDir = newAppPollerDir.getCanonicalFile();
               newAppPollerDirName = newAppPollerDir.getCanonicalPath();
               if (isLoadedFromOldAppsDir(appFile, root)) {
                  String newPath = newAppPollerDirName;
                  if (!appPath.equals(oldAppPollerDirName)) {
                     newPath = StringUtils.replaceGlobal(appPath, oldAppPollerDirName, newAppPollerDirName);
                  }

                  if (!appPath.equals(oldAppPollerDirName)) {
                     File newAppLocation = new File(newPath);
                     FileUtils.copyPreservePermissions(appFile, newAppLocation);
                     if (isDebugEnabled()) {
                        debugSay(" +++ copied " + appFile + " to " + newAppLocation);
                     }

                     boolean removedFile = FileUtils.remove(appFile);
                     if (isDebugEnabled()) {
                        debugSay(" +++ Removed " + appFile + " :: " + removedFile);
                     }
                  } else {
                     ComponentMBean[] comps = appMBean.getComponents();

                     for(int i = 0; i < comps.length; ++i) {
                        String compPath = appPath + File.separatorChar + comps[i].getURI();
                        File compPathFile = new File(compPath);
                        File newCompPathFile = new File(newAppPollerDir, comps[i].getURI());
                        FileUtils.copyPreservePermissions(compPathFile, newCompPathFile);
                        if (isDebugEnabled()) {
                           debugSay(" +++ copied component " + compPathFile + " to " + newCompPathFile);
                        }

                        boolean removedFile = FileUtils.remove(compPathFile);
                        if (isDebugEnabled()) {
                           debugSay(" +++ Removed " + compPathFile + " :: " + removedFile);
                        }
                     }
                  }

                  if (isDebugEnabled()) {
                     debugSay(" +++ Application migrated to new autodeploy directory: " + newPath);
                  }

                  appMBean.setPath(newPath);
                  ApplicationPollerLogger.logApplicationMigrated(appMBean.getName(), newPath);
               }

            } catch (IOException var18) {
               errMsg = ApplicationPollerLogger.logIOExceptionLoggable(var18).getMessage();
               throw new UpdateException(errMsg);
            } catch (ManagementException var19) {
               errMsg = ApplicationPollerLogger.logExceptionWhileMigratingLoggable(var19).getMessage();
               throw new UpdateException(errMsg);
            } catch (InvalidAttributeValueException var20) {
               errMsg = ApplicationPollerLogger.logExceptionWhileMigratingLoggable(var20).getMessage();
               throw new UpdateException(errMsg);
            }
         }
      }
   }

   public static boolean isLoadedFromOldAppsDir(File pathDir, DomainMBean root) {
      File appPollerDir = getOldAppPollerDir(root);
      String appPollerDirName = appPollerDir.getName();

      try {
         appPollerDir = appPollerDir.getCanonicalFile();
         appPollerDirName = appPollerDir.getCanonicalPath();
      } catch (IOException var7) {
         ApplicationPollerLogger.logIOException(var7);
      }

      String pathDirName = pathDir.getAbsolutePath();

      try {
         pathDir = pathDir.getCanonicalFile();
         pathDirName = pathDir.getCanonicalPath();
      } catch (IOException var6) {
         ApplicationPollerLogger.logIOException(var6);
      }

      return pathDirName.indexOf(appPollerDirName) > -1;
   }

   private static void debugSay(String msg) {
      Debug.deploymentDebug(msg);
   }

   private static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   private static void printFileList(File theFile) {
      if (isDebugEnabled()) {
         String[] list = theFile.list();
         StringBuffer sb = new StringBuffer();
         sb.append("ls ").append(theFile.toString()).append(" - ");
         if (list == null) {
            sb.append(" nothing to list since file doesn't exist");
         } else {
            sb.append("" + list.length).append(" : ");

            for(int i = 0; i < list.length; ++i) {
               if (i != 0) {
                  sb.append(", ");
               }

               sb.append(list[i]);
            }
         }

         Debug.deploymentDebug(sb.toString());
      }

   }

   private static File getOldAppPollerDir(DomainMBean root) {
      File oldApplicationDir = null;
      if (root.getConfigurationVersion().startsWith("6")) {
         String oldPath = DomainDir.getRootDir() + File.separator + "config" + File.separator + root.getName() + File.separator + "applications";
         oldApplicationDir = new File(oldPath);
      } else {
         oldApplicationDir = new File(DomainDir.getOldAppPollerDir());
      }

      return oldApplicationDir;
   }
}
