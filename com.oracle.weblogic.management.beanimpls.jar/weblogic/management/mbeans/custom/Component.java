package weblogic.management.mbeans.custom;

import java.io.File;
import java.security.AccessController;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.application.utils.TargetUtils;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class Component extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = -2559563505270982380L;
   private static final DebugCategory DEBUGGING = Debug.getCategory("weblogic.deployment");
   protected static final boolean DEBUG;
   private transient File compFile = null;
   private transient File tmpdir = null;
   private transient File altddFile = null;
   private boolean isArchivedEar;
   private String altDDURI = null;
   private transient ApplicationMBean applicationMBean;
   private transient AppDeploymentMBean appDeployment = null;
   private static final AuthenticatedSubject kernelId;
   TargetMBean[] targets = new TargetMBean[0];

   public Component(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public boolean activated(TargetMBean target) {
      return Arrays.asList((Object[])this.getComponentMBean().getActivatedTargets()).contains(target);
   }

   protected boolean containsDD(String[] files, String subject) {
      for(int i = 0; files != null && i < files.length; ++i) {
         if (files[i].endsWith(subject)) {
            return true;
         }
      }

      return false;
   }

   protected ComponentMBean getComponentMBean() {
      return (ComponentMBean)this.getMbean();
   }

   public ApplicationMBean getApplication() {
      if (this.applicationMBean != null) {
         return this.applicationMBean;
      } else {
         this.setApplication((ApplicationMBean)this.getMbean().getParent());
         return this.applicationMBean;
      }
   }

   public void setApplication(ApplicationMBean applicationMBean) {
      this.applicationMBean = applicationMBean;
      if (applicationMBean == null) {
         this.appDeployment = null;
      } else {
         this.appDeployment = applicationMBean.getAppDeployment();
      }
   }

   protected AppDeploymentMBean getAppDeployment() {
      if (this.appDeployment == null) {
         this.initFromParent();
      }

      return this.appDeployment;
   }

   private void initFromParent() {
      this.applicationMBean = (ApplicationMBean)this.getMbean().getParent();
      this.setApplication(this.applicationMBean);
   }

   public void setTargets(TargetMBean[] targets) {
      if (this.getAppDeployment() != null) {
         MBeanConverter.setTargetsForComponent(this.getAppDeployment(), this.getComponentMBean(), targets);
      }

      this.putValueNotify("Targets", targets);
      this.targets = targets;
   }

   public TargetMBean[] getTargets() {
      return this.getAppDeployment() != null ? MBeanConverter.getTargetsForComponent(this.getAppDeployment(), this.getComponentMBean()) : this.targets;
   }

   public TargetMBean[] getActivatedTargets() {
      DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
      AppRuntimeStateManager appRT = AppRuntimeStateManager.getManager();
      TargetMBean[] targs = this.getTargets();
      Set targets = new HashSet();

      for(int i = 0; i < targs.length; ++i) {
         String tname = targs[i].getName();
         if (this.targetAlive(targs[i], domainAccess)) {
            String s = appRT.getCurrentState(this.getApplication().getName(), this.getName(), tname);
            if (this.compActive(s)) {
               targets.add(targs[i]);
            }
         }
      }

      return (TargetMBean[])((TargetMBean[])targets.toArray(new TargetMBean[0]));
   }

   private boolean targetAlive(TargetMBean target, DomainAccess domainAccess) {
      if (domainAccess != null) {
         Set servers = target.getServerNames();
         Iterator iterator = servers.iterator();

         ServerLifeCycleRuntimeMBean serverState;
         do {
            if (!iterator.hasNext()) {
               return false;
            }

            String tname = (String)iterator.next();
            serverState = domainAccess.lookupServerLifecycleRuntime(tname);
         } while(!this.serverAlive(serverState));

         return true;
      } else {
         return TargetUtils.isDeployedLocally(new TargetMBean[]{target});
      }
   }

   private boolean serverAlive(ServerLifeCycleRuntimeMBean serverState) {
      return serverState != null && ("ADMIN".equals(serverState.getState()) || "RUNNING".equals(serverState.getState()));
   }

   private boolean compActive(String s) {
      return "STATE_ADMIN".equals(s) || "STATE_ACTIVE".equals(s) || "STATE_UPDATE_PENDING".equals(s);
   }

   static {
      DEBUG = DEBUGGING.isEnabled();
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
