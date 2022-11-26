package weblogic.management.mbeans.custom;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.enterprise.deploy.shared.ModuleType;
import javax.management.InvalidAttributeValueException;
import weblogic.application.ModuleListener;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.shared.WebLogicModuleTypeUtil;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.j2ee.J2EEUtils;
import weblogic.logging.Loggable;
import weblogic.management.DistributedManagementException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.deploy.ApplicationsDirPoller;
import weblogic.management.deploy.DeploymentCompatibilityEvent;
import weblogic.management.deploy.DeploymentCompatibilityEventHandler;
import weblogic.management.deploy.DeploymentCompatibilityEventManager;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class Application extends ConfigurationMBeanCustomizer implements DeploymentCompatibilityEventManager {
   private static final long serialVersionUID = -2233500325718755084L;
   private static final DebugCategory debugging = Debug.getCategory("weblogic.deployment");
   private static final DebugCategory debugnotif = Debug.getCategory("weblogic.deployment.notif");
   private transient String fullPath = null;
   private transient String deploymentType;
   private transient boolean delegationEnabled = false;
   private transient AppDeploymentMBean delegate = null;
   private String stagingMode = null;
   private boolean deployed = false;
   private boolean internal = false;
   private int internalType = 6;
   String path = null;
   String notes = null;
   private final CopyOnWriteArraySet handlers = new CopyOnWriteArraySet();

   public Application(ConfigurationMBeanCustomized base) {
      super(base);
   }

   private String lookupPath() {
      return ((ApplicationMBean)this.getMbean()).getPath();
   }

   public synchronized void unstageTargets(String[] targets) {
      if (debugging.isEnabled()) {
         Debug.say("Unstaging Targets");
      }

      if (targets != null) {
         String[] staged = this.getApplication().getStagedTargets();
         if (staged != null && staged.length != 0) {
            Set stagedSet = new TreeSet();

            int i;
            for(i = 0; i < staged.length; ++i) {
               stagedSet.add(staged[i]);
            }

            if (debugging.isEnabled()) {
               Debug.say("Currently Staged Targets " + stagedSet);
            }

            for(i = 0; i < targets.length; ++i) {
               String targetName = targets[i];
               if (!stagedSet.remove(targetName)) {
                  ClusterMBean cluster = ((DomainMBean)((DomainMBean)this.getMbean().getParent())).lookupCluster(targetName);
                  if (cluster != null) {
                     if (debugging.isEnabled()) {
                        Debug.say("Unstaging Cluster :" + targetName);
                     }

                     ServerMBean[] clusteredServers = cluster.getServers();
                     if (clusteredServers != null) {
                        for(int cs = 0; cs < clusteredServers.length; ++cs) {
                           String clusteredTargetName = clusteredServers[cs].getName();
                           stagedSet.remove(clusteredTargetName);
                           if (debugging.isEnabled()) {
                              Debug.say("Unstaged Clustered :" + clusteredTargetName);
                           }
                        }
                     }
                  } else {
                     VirtualHostMBean virtualHost = ((DomainMBean)((DomainMBean)this.getMbean().getParent())).lookupVirtualHost(targetName);
                     if (virtualHost != null) {
                        if (debugging.isEnabled()) {
                           Debug.say("Unstaging VirtualHost :" + targetName);
                        }

                        TargetMBean[] virtualServers = virtualHost.getTargets();

                        for(int vsCr = 0; vsCr < virtualServers.length; ++vsCr) {
                           String virtualServerName = virtualServers[vsCr].getName();
                           stagedSet.remove(virtualServerName);
                           if (debugging.isEnabled()) {
                              Debug.say("Unstaged Virtual Target : " + virtualServerName);
                           }
                        }
                     }
                  }
               }
            }

            String[] newStaged = new String[stagedSet.size()];
            newStaged = (String[])((String[])stagedSet.toArray(newStaged));
            if (debugging.isEnabled()) {
               Debug.say("Remaining Staged Targets" + stagedSet);
            }

            this.getApplication().setStagedTargets(newStaged);
         }
      }
   }

   public boolean useStagingDirectory(String server) {
      throw new UnsupportedOperationException();
   }

   public boolean stagingEnabled(String server) {
      return "stage".equals(this.getStagingMode(server));
   }

   public boolean staged(String server) {
      boolean ret = true;
      if (this.stagingEnabled(server)) {
         List targs = Arrays.asList((Object[])this.getApplication().getStagedTargets());
         if (targs == null || !targs.contains(server)) {
            ret = false;
         }
      }

      if (debugging.isEnabled()) {
         Debug.say("isStaged  " + server + " = " + ret);
      }

      return ret;
   }

   public void sendAppLevelNotification(String server, String phase, String taskId) {
      DeploymentCompatibilityEvent event = new DeploymentCompatibilityEvent("weblogic.deployment.application", server, this.getName(), phase, (String)null, (String)null, (String)null, (String)null, taskId);
      this.sendNotification(event);
   }

   public void sendModuleNotification(String server, String module, String xition, String currState, String targetState, String taskId, long gentime) {
      currState = this.convertStateNamesTo81(currState);
      targetState = this.convertStateNamesTo81(targetState);
      DeploymentCompatibilityEvent event = new DeploymentCompatibilityEvent("weblogic.deployment.application.module", server, this.getName(), (String)null, xition, module, currState, targetState, taskId);
      this.sendNotification(event);
   }

   private String convertStateNamesTo81(String state) {
      if (state.equals(ModuleListener.STATE_PREPARED.toString())) {
         return "unprepared".toString();
      } else if (state.equals(ModuleListener.STATE_ADMIN.toString())) {
         return "prepared".toString();
      } else if (state.equals(ModuleListener.STATE_ACTIVE.toString())) {
         return "active".toString();
      } else {
         if (debugnotif.isEnabled()) {
            Debug.say("Illegal state " + state);
         }

         return null;
      }
   }

   private String typeAsString(int type) {
      switch (type) {
         case 0:
            return "EAR";
         case 1:
            return "COMPONENT";
         case 2:
            return "EXPLODED EAR";
         case 3:
            return "EXPLODED COMPONENT";
         default:
            return "UNKNOWN";
      }
   }

   private String getTypeFromNew(String newType) {
      if (newType == null) {
         return "UNKNOWN";
      } else if (newType.equals(WebLogicModuleType.UNKNOWN.toString())) {
         return "UNKNOWN";
      } else {
         return newType.equals(ModuleType.EAR.toString()) ? "EAR" : "COMPONENT";
      }
   }

   private int stringAsType(String sType) {
      if (sType.equals("EAR")) {
         return 0;
      } else if (sType.equals("EXPLODED EAR")) {
         return 2;
      } else if (sType.equals("COMPONENT")) {
         return 1;
      } else {
         return sType.equals("EXPLODED COMPONENT") ? 3 : 4;
      }
   }

   public int findExplodedEar() {
      int ret = 0;
      if (this.isAdmin()) {
         File file = new File(this.getFullPath());
         ret = file.isDirectory() ? 1 : 0;
      }

      return ret;
   }

   public boolean isInternalApp() {
      return this.getAppDeployment() == null ? this.internal : this.getAppDeployment().isInternalApp();
   }

   public void setInternalApp(boolean b) {
      if (this.getAppDeployment() == null) {
         this.internal = b;
      } else {
         this.getAppDeployment().setInternalApp(b);
      }

   }

   public void setStagingMode(String mode) {
      this.stagingMode = mode;
   }

   public String getStagingMode() {
      if (!this.isDelegationEnabled()) {
         return this.stagingMode;
      } else {
         return this.getAppDeployment() != null ? this.getAppDeployment().getStagingMode() : null;
      }
   }

   public boolean isDDEditingEnabled() {
      return false;
   }

   public String getDDEditingDisabledReason() {
      return "Not supported anymore.";
   }

   public String getFullPath() {
      if (this.fullPath != null) {
         return this.fullPath;
      } else {
         File appFile;
         try {
            appFile = BootStrap.apply(this.lookupPath());
         } catch (InvalidAttributeValueException var6) {
            throw new AssertionError(var6);
         }

         int type = this.getInternalType();
         if (type == 1 || type == 3 || type == 5) {
            ComponentMBean[] comps = this.getApplication().getComponents();
            if (comps != null && comps.length > 0) {
               String componentURI = comps[0].getURI();
               if (componentURI != null && componentURI.length() > 0) {
                  appFile = new File(appFile, componentURI);
               } else {
                  DeployerRuntimeLogger.logNoURI(this.getMbean().getName(), comps[0].getName());
                  appFile = null;
               }
            } else {
               DeployerRuntimeLogger.logNoModules(this.getMbean().getName());
               appFile = null;
            }
         }

         if (appFile != null) {
            try {
               this.fullPath = appFile.getCanonicalPath();
            } catch (IOException var5) {
               this.fullPath = appFile.getAbsolutePath();
            }
         }

         return this.fullPath;
      }
   }

   public int getInternalType() {
      if (this.internalType == 6) {
         if (this.deploymentType != null && this.deploymentType != "UNKNOWN") {
            this.internalType = this.stringAsType(this.deploymentType);
         } else {
            try {
               this.internalType = J2EEUtils.getDeploymentCategory(this.getApplication());
               this.deploymentType = this.typeAsString(this.internalType);
            } catch (IOException var3) {
               this.internalType = 4;
               Loggable l = DeployerRuntimeLogger.logUnknownAppTypeLoggable(this.getMbean().getName(), this.lookupPath());
               l.log();
            }
         }
      }

      return this.internalType;
   }

   public String getDeploymentType() {
      if (!this.isDelegationEnabled()) {
         if (this.deploymentType == null) {
            this.deploymentType = this.typeAsString(this.getInternalType());
         }

         return this.deploymentType;
      } else {
         AppDeploymentMBean theDelegate = this.getAppDeployment();
         if (theDelegate != null) {
            String newType = theDelegate.getModuleType();
            if (newType != null) {
               return this.getTypeFromNew(newType);
            }
         }

         return "UNKNOWN";
      }
   }

   public void setDeploymentType(String t) {
      this.deploymentType = t;
   }

   public void setDeployed(boolean deployed) {
      this.deployed = deployed;
   }

   public boolean isDeployed() {
      AppDeploymentMBean appDeployment = this.getAppDeployment();
      if (appDeployment == null) {
         return this.deployed;
      } else {
         TargetMBean[] targets = appDeployment.getTargets();
         SubDeploymentMBean[] subDeployments = appDeployment.getSubDeployments();
         return targets != null && targets.length != 0 || subDeployments != null && subDeployments.length != 0;
      }
   }

   private String getStagingMode(String server) {
      String mode = this.getAppDeployment().getStagingMode();
      if (mode != null && mode.length() != 0) {
         if (debugging.isEnabled()) {
            Debug.say(" Value of application's staging mode is " + mode);
         }
      } else {
         mode = DeployHelper.getServerStagingMode(server);
         if (debugging.isEnabled()) {
            Debug.say("Using " + mode + " from serverMBean " + server);
         }
      }

      return mode;
   }

   public boolean isEar() {
      int type = this.getInternalType();
      return type == 0 || type == 2;
   }

   boolean isExplodedEar() {
      return this.getInternalType() == 2;
   }

   public boolean isLoadedFromAppDir() {
      return !this.isAdmin() ? false : ApplicationsDirPoller.isInAppsDir(new File(DomainDir.getAppPollerDir()), this.lookupPath());
   }

   public void refreshDDsIfNeeded(String[] changedFiles, String[] modules) {
      HashSet mset = null;
      if (debugging.isEnabled()) {
         Debug.say("app.refreshDDsIfNeeded " + Arrays.toString(modules));
      }

      if (!this.isConfig()) {
         if (modules != null && modules.length > 0) {
            mset = new HashSet(Arrays.asList((Object[])modules));
         }

         if (debugging.isEnabled()) {
            Debug.say("app.refreshDDsIfNeeded " + mset);
         }

         ComponentMBean[] comps = this.getApplication().getComponents();

         for(int i = 0; i < comps.length; ++i) {
            if (mset == null || mset.contains(comps[i].getName())) {
               comps[i].refreshDDsIfNeeded(changedFiles);
            }
         }

      }
   }

   private boolean containsAppDD(String[] files) {
      int i = 0;

      while(true) {
         if (files != null && i < files.length) {
            if (!files[i].endsWith("META-INF/application.xml") && !files[i].endsWith("META-INF/weblogic-application.xml")) {
               ++i;
               continue;
            }

            return true;
         }

         return false;
      }
   }

   public ComponentMBean[] getComponents() {
      Set componentSet = new HashSet();
      ApplicationMBean appMBean = (ApplicationMBean)this.getMbean();
      this.buildComponentSet(appMBean.getEJBComponents(), componentSet);
      this.buildComponentSet(appMBean.getWebAppComponents(), componentSet);
      this.buildComponentSet(appMBean.getWebServiceComponents(), componentSet);
      this.buildComponentSet(appMBean.getConnectorComponents(), componentSet);
      this.buildComponentSet(appMBean.getJDBCPoolComponents(), componentSet);
      return (ComponentMBean[])((ComponentMBean[])componentSet.toArray(new ComponentMBean[componentSet.size()]));
   }

   private void buildComponentSet(ComponentMBean[] comps, Set componentAll) {
      if (comps != null && comps.length > 0) {
         List compList = Arrays.asList((Object[])comps);
         componentAll.addAll(compList);
      }

   }

   private ApplicationMBean getApplication() {
      return (ApplicationMBean)this.getMbean();
   }

   public AppDeploymentMBean returnDeployableUnit() {
      return this.getAppDeployment();
   }

   public void setDelegationEnabled(boolean state) {
      this.delegationEnabled = state;
   }

   public boolean isDelegationEnabled() {
      return this.delegationEnabled;
   }

   public String getPath() {
      if (this.delegate == null) {
         this.getAppDeployment();
      }

      return this.delegate != null ? this.delegate.getSourcePath() : this.path;
   }

   public void setPath(String p) {
      if (this.delegate == null) {
         this.getAppDeployment();
      }

      if (this.delegate != null) {
         this.delegate.setSourcePath(p);
         this.delegate.setModuleType(WebLogicModuleTypeUtil.getFileModuleTypeAsString(new File(this.delegate.getLocalSourcePath())));
      } else {
         this.path = p;
      }

   }

   public String getNotes() {
      if (this.delegate == null) {
         this.getAppDeployment();
      }

      return this.delegate != null ? this.delegate.getNotes() : this.notes;
   }

   public void setNotes(String notes) throws InvalidAttributeValueException, DistributedManagementException {
      if (this.delegate == null) {
         this.getAppDeployment();
      }

      if (this.delegate != null) {
         this.delegate.setNotes(notes);
      } else {
         this.notes = notes;
      }

   }

   public AppDeploymentMBean getAppDeployment() {
      if (!this.delegationEnabled) {
         return null;
      } else {
         ApplicationMBean appmb = (ApplicationMBean)this.getMbean();
         DomainMBean root = (DomainMBean)appmb.getParent();
         if (root == null) {
            return null;
         } else {
            this.delegate = AppDeploymentHelper.lookupAppOrLib(appmb.getName(), root);
            return this.delegate;
         }
      }
   }

   public void addHandler(Object h) {
      this.handlers.add(h);
   }

   private void sendNotification(DeploymentCompatibilityEvent event) {
      if (debugnotif.isEnabled()) {
         String type = this.getMbean().getObjectName().getType().endsWith("Config") ? "LOCAL" : "REMOTE";
         Debug.say(type + " " + event);
      }

      Iterator it = this.handlers.iterator();

      while(it.hasNext()) {
         try {
            ((DeploymentCompatibilityEventHandler)it.next()).handleEvent(event);
         } catch (Throwable var4) {
            ManagementLogger.logExceptionInCustomizer(var4);
         }
      }

   }

   public boolean isTwoPhase() {
      return true;
   }
}
