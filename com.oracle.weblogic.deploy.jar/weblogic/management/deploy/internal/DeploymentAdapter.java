package weblogic.management.deploy.internal;

import java.util.Set;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.SystemResourceDeployment;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.CustomResourceMBean;
import weblogic.management.deploy.classdeployment.ClassDeploymentManager;
import weblogic.management.internal.DeploymentHandlerHome;

abstract class DeploymentAdapter {
   private static final boolean deploySystemResourceOnePhase = true;
   private static final boolean deployDeploymentHandlerOnePhase = true;
   static final DeploymentAdapter BASIC_DEP_ADAPTER = new DeploymentAdapter() {
      public String getName(Object obj) {
         BasicDeployment dep = (BasicDeployment)obj;
         return ApplicationVersionUtils.getDisplayName(dep.getDeploymentMBean());
      }

      protected void doPrepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         ((BasicDeployment)obj).prepare();
         if (DeploymentAdapter.isDeployedInPhaseOne(obj)) {
            ((BasicDeployment)obj).activateFromServerLifecycle();
         }

      }

      protected void doActivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         if (!DeploymentAdapter.isDeployedInPhaseOne(obj)) {
            ((BasicDeployment)obj).activateFromServerLifecycle();
         }
      }

      public void adminToProduction(Object obj) throws Exception {
         ((BasicDeployment)obj).adminToProductionFromServerLifecycle();
      }

      public void gracefulProductionToAdmin(Object obj) throws Exception {
         ((BasicDeployment)obj).productionToAdminFromServerLifecycle(true);
      }

      public void forceProductionToAdmin(Object obj) throws Exception {
         ((BasicDeployment)obj).productionToAdminFromServerLifecycle(false);
      }

      protected void doDeactivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         if (!DeploymentAdapter.isDeployedInPhaseOne(obj)) {
            ((BasicDeployment)obj).deactivateFromServerLifecycle();
         }
      }

      protected void doUnprepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         if (DeploymentAdapter.isDeployedInPhaseOne(obj)) {
            ((BasicDeployment)obj).deactivateFromServerLifecycle();
         }

         ((BasicDeployment)obj).unprepareFromServerLifecycle();
      }

      public void remove(Object obj) throws Exception {
         ((BasicDeployment)obj).remove();
      }

      public void remove(Object obj, boolean removeStagedFiles) throws Exception {
         ((BasicDeployment)obj).remove(removeStagedFiles);
      }
   };
   static final DeploymentAdapter DEPLOYMENT_HANDLERS_ADAPTER = new DeploymentAdapter() {
      Set deployments;

      protected void doPrepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         this.deployments = DeploymentHandlerHome.getInstance().prepareInitialDeployments(scopeMBean, resourceGroups);
         DeploymentHandlerHome.getInstance().activateInitialDeployments(this.deployments);
      }

      protected void doActivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      }

      protected void doDeactivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      }

      protected void doUnprepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         this.deployments = DeploymentHandlerHome.getInstance().deactivateCurrentDeployments(scopeMBean, resourceGroups);
         DeploymentHandlerHome.getInstance().unprepareCurrentDeployments(this.deployments);
      }
   };
   static final DeploymentAdapter RESOURCE_DEPENDENT_DEPLOYMENT_HANDLERS_ADAPTER = new DeploymentAdapter() {
      Set deployments;

      protected void doPrepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         this.deployments = DeploymentHandlerHome.getInstance().prepareResourceDependentInitialDeployments(scopeMBean, resourceGroups);
         DeploymentHandlerHome.getInstance().activateResourceDependentInitialDeployments(this.deployments);
      }

      protected void doActivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      }

      protected void doDeactivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      }

      protected void doUnprepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         this.deployments = DeploymentHandlerHome.getInstance().deactivateResourceDependentCurrentDeployments(scopeMBean, resourceGroups);
         DeploymentHandlerHome.getInstance().unprepareResourceDependentCurrentDeployments(this.deployments);
      }
   };
   static final DeploymentAdapter STARTUP_CLASSES_ADAPTER = new DeploymentAdapter() {
      void activate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         ClassDeploymentManager.getInstance().runStartupsBeforeAppActivation(scopeMBean);
      }
   };

   private DeploymentAdapter() {
   }

   private static boolean isDeployedInPhaseOne(Object obj) {
      return obj instanceof SystemResourceDeployment && !(((BasicDeployment)obj).getDeploymentMBean() instanceof CustomResourceMBean);
   }

   String getName(Object dep) {
      return dep.toString();
   }

   void prepare(Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      try {
         this.preInvoke();
         this.doPrepare(dep, scopeMBean, resourceGroups);
      } finally {
         this.postInvoke();
      }

   }

   void activate(Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      try {
         this.preInvoke();
         this.doActivate(dep, scopeMBean, resourceGroups);
      } finally {
         this.postInvoke();
      }

   }

   void adminToProduction(Object dep) throws Exception {
   }

   void gracefulProductionToAdmin(Object dep) throws Exception {
   }

   void forceProductionToAdmin(Object dep) throws Exception {
   }

   void deactivate(Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      try {
         this.preInvoke();
         this.doDeactivate(dep, scopeMBean, resourceGroups);
      } finally {
         this.postInvoke();
      }

   }

   void unprepare(Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      try {
         this.preInvoke();
         this.doUnprepare(dep, scopeMBean, resourceGroups);
      } finally {
         this.postInvoke();
      }

   }

   void remove(Object dep) throws Exception {
   }

   void remove(Object dep, boolean removeStagedFiles) throws Exception {
   }

   protected void preInvoke() {
      ApplicationVersionUtils.setCurrentAdminMode(true);
   }

   protected void postInvoke() {
      ApplicationVersionUtils.unsetCurrentAdminMode();
   }

   protected void doPrepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
   }

   protected void doActivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
   }

   protected void doDeactivate(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
   }

   protected void doUnprepare(Object obj, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
   }

   // $FF: synthetic method
   DeploymentAdapter(Object x0) {
      this();
   }
}
