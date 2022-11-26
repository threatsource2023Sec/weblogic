package weblogic.management.deploy.internal;

import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.work.ExecuteThread;

abstract class AppTransition {
   private String name;
   private boolean startup;
   static final AppTransition PREPARE = new AppTransition("Prepare", true) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         long startTime = System.currentTimeMillis();
         boolean var15 = false;

         try {
            var15 = true;
            adapter.prepare(dep, scopeMBean, resourceGroups);
            var15 = false;
         } finally {
            if (var15) {
               long endTime = System.currentTimeMillis();
               if (Debug.isDeploymentDebugEnabled() && dep instanceof BasicDeployment) {
                  String name = ((BasicDeployment)dep).getDeploymentMBean().getName();
                  Debug.deploymentDebug("Time to PREPARE deployment: " + name + " = " + (endTime - startTime));
               }

            }
         }

         long endTimex = System.currentTimeMillis();
         if (Debug.isDeploymentDebugEnabled() && dep instanceof BasicDeployment) {
            String namex = ((BasicDeployment)dep).getDeploymentMBean().getName();
            Debug.deploymentDebug("Time to PREPARE deployment: " + namex + " = " + (endTimex - startTime));
         }

      }
   };
   static final AppTransition ACTIVATE = new AppTransition("Activate", true) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         long startTime = System.currentTimeMillis();
         boolean var15 = false;

         try {
            var15 = true;
            adapter.activate(dep, scopeMBean, resourceGroups);
            var15 = false;
         } finally {
            if (var15) {
               long endTime = System.currentTimeMillis();
               if (Debug.isDeploymentDebugEnabled() && dep instanceof BasicDeployment) {
                  String name = ((BasicDeployment)dep).getDeploymentMBean().getName();
                  Debug.deploymentDebug("Time to ACTIVATE deployment: " + name + " = " + (endTime - startTime));
               }

            }
         }

         long endTimex = System.currentTimeMillis();
         if (Debug.isDeploymentDebugEnabled() && dep instanceof BasicDeployment) {
            String namex = ((BasicDeployment)dep).getDeploymentMBean().getName();
            Debug.deploymentDebug("Time to ACTIVATE deployment: " + namex + " = " + (endTimex - startTime));
         }

      }
   };
   static final AppTransition ADMIN_TO_PRODUCTION = new AppTransition("Transition from admin to production", true) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         adapter.adminToProduction(dep);
      }
   };
   static final AppTransition GRACEFUL_PRODUCTION_TO_ADMIN = new AppTransition("Graceful transition from production to admin", false) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         adapter.gracefulProductionToAdmin(dep);
      }
   };
   static final AppTransition FORCE_PRODUCTION_TO_ADMIN = new AppTransition("Force transition from production to admin", false) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         adapter.forceProductionToAdmin(dep);
      }
   };
   static final AppTransition DEACTIVATE = new AppTransition("Deactivate", false) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         adapter.deactivate(dep, scopeMBean, resourceGroups);
      }
   };
   static final AppTransition UNPREPARE = new AppTransition("Unprepare", false) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         adapter.unprepare(dep, scopeMBean, resourceGroups);
      }
   };
   static final AppTransition REMOVE = new AppTransition("Remove", false) {
      protected void transitionAppInternal(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
         adapter.remove(dep);
      }
   };

   private AppTransition(String name, boolean startup) {
      this.name = null;
      this.startup = true;
      this.name = name;
      this.startup = startup;
   }

   public String toString() {
      return this.name;
   }

   boolean isStartup() {
      return this.startup;
   }

   void transitionApp(DeploymentAdapter adapter, Object dep, ConfigurationMBean scopeMBean, Set resourceGroups) throws Exception {
      try {
         ExecuteThread.updateWorkDescription(dep.toString());
         this.transitionAppInternal(adapter, dep, scopeMBean, resourceGroups);
      } finally {
         ExecuteThread.updateWorkDescription((String)null);
      }

   }

   protected abstract void transitionAppInternal(DeploymentAdapter var1, Object var2, ConfigurationMBean var3, Set var4) throws Exception;

   // $FF: synthetic method
   AppTransition(String x0, boolean x1, Object x2) {
      this(x0, x1);
   }
}
