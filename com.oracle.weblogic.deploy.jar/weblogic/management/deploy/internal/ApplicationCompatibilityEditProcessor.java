package weblogic.management.deploy.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.AccessCallback;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.UpdateException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.ArrayUtils;

public class ApplicationCompatibilityEditProcessor extends ApplicationCompatibilityProcessor implements AccessCallback, PropertyChangeListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   DomainMBean root;
   private boolean expectingPeerCreationCallback = false;

   public void shutdown() {
      this.root.removePropertyChangeListener(this);
   }

   public void accessed(DomainMBean root) {
      this.root = root;

      try {
         this.updateConfiguration(root);
      } catch (UpdateException var3) {
         throw new RuntimeException(var3);
      }

      root.addPropertyChangeListener(this);
   }

   ArrayUtils.DiffHandler createAppDeploymentHandler() {
      return new ArrayUtils.DiffHandler() {
         public void addObject(Object added) {
            AppDeploymentMBean mbean = (AppDeploymentMBean)added;

            try {
               ApplicationCompatibilityEditProcessor.this.createApplication(mbean);
               mbean.addPropertyChangeListener(ApplicationCompatibilityEditProcessor.this.createAppDeploymentListener(mbean));
            } catch (DeploymentException var4) {
               DeploymentManagerLogger.logConfigureAppMBeanFailed(mbean.getName());
            }

         }

         public void removeObject(Object removed) {
            AppDeploymentMBean appDeployment = (AppDeploymentMBean)removed;
            ApplicationMBean application = ApplicationCompatibilityEditProcessor.this.root.lookupApplication(appDeployment.getName());
            if (application != null) {
               if (MBeanConverter.isDebugEnabled()) {
                  MBeanConverter.debug("EditProcessor: Destroy " + application.getObjectName() + " from " + ApplicationCompatibilityEditProcessor.this.root.getObjectName());
               }

               ApplicationCompatibilityEditProcessor.this.root.destroyApplication(application);
            }
         }
      };
   }

   private void createApplication(AppDeploymentMBean mbean) throws DeploymentException {
      String path = null;
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      if (ra.isAdminServer()) {
         path = mbean.getAbsoluteSourcePath();
      } else {
         path = AppDeployment.getFile(mbean, ra.getServer()).getAbsolutePath();
      }

      if (MBeanConverter.isDebugEnabled()) {
         MBeanConverter.debug(" AppPath : " + path);
      }

      MBeanConverter.createApplicationForAppDeployment(this.root, mbean, path);
   }

   private PropertyChangeListener createAppDeploymentListener(final AppDeploymentMBean appDeployment) {
      return new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("Targets") && TargetHelper.isTargetedLocaly(appDeployment)) {
               try {
                  ApplicationCompatibilityEditProcessor.this.createApplication(appDeployment);
               } catch (DeploymentException var3) {
                  throw new RuntimeException(var3);
               }
            }

         }
      };
   }

   ArrayUtils.DiffHandler createApplicationHandler() {
      return new ArrayUtils.DiffHandler() {
         public void addObject(Object added) {
            ApplicationMBean mbean = (ApplicationMBean)added;
            if (ApplicationCompatibilityEditProcessor.this.root.lookupAppDeployment(mbean.getName()) == null) {
               ApplicationCompatibilityEditProcessor.this.root.createAppDeployment(mbean.getName(), "bea_wls_nullApp");
               mbean.setDelegationEnabled(true);
            }

         }

         public void removeObject(Object removed) {
            ApplicationMBean app = (ApplicationMBean)removed;
            if (app != null) {
               AppDeploymentMBean mbean = ApplicationCompatibilityEditProcessor.this.root.lookupAppDeployment(app.getName());
               if (mbean != null) {
                  if (MBeanConverter.isDebugEnabled()) {
                     MBeanConverter.debug("EditProcessor: Destroy " + mbean.getObjectName() + " from " + ApplicationCompatibilityEditProcessor.this.root.getObjectName());
                  }

                  ApplicationCompatibilityEditProcessor.this.root.destroyAppDeployment(mbean);
               }

            }
         }
      };
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("AppDeployments")) {
         AppDeploymentMBean[] newDeployments = (AppDeploymentMBean[])((AppDeploymentMBean[])evt.getNewValue());
         if (this.expectingPeerCreationCallback) {
            this.expectingPeerCreationCallback = false;
            return;
         }

         if (MBeanConverter.isDebugEnabled()) {
            MBeanConverter.debug("EditProcessor: AppDeployment change event");
         }

         this.expectingPeerCreationCallback = true;

         try {
            ArrayUtils.computeDiff((Object[])((Object[])evt.getOldValue()), newDeployments, this.createAppDeploymentHandler());
         } finally {
            this.expectingPeerCreationCallback = false;
         }
      }

      if (evt.getPropertyName().equals("Applications")) {
         if (MBeanConverter.isDebugEnabled()) {
            MBeanConverter.debug("EditProcessor: Applications change event");
         }

         ApplicationMBean[] apps = (ApplicationMBean[])((ApplicationMBean[])evt.getNewValue());
         if (this.expectingPeerCreationCallback) {
            this.expectingPeerCreationCallback = false;
            return;
         }

         for(int i = 0; i < apps.length; ++i) {
            this.expectingPeerCreationCallback = true;

            try {
               ArrayUtils.computeDiff((Object[])((Object[])evt.getOldValue()), apps, this.createApplicationHandler());
            } finally {
               this.expectingPeerCreationCallback = false;
            }
         }
      }

   }
}
