package weblogic.application.internal;

import weblogic.application.ApplicationContext;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.management.DeploymentException;
import weblogic.utils.StringUtils;

public final class DeploymentTimer extends DeploymentStateChecker {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   DeploymentTimer(Deployment delegate) {
      super(delegate);
   }

   private void print(String callback, long t) {
      String s = callback + " on app " + this.getApplicationContext().getApplicationId() + " took " + t + "ms.";
      debugLogger.debug(s);
      J2EELogger.logDebug(s);
   }

   public void prepare(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.prepare(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("PREPARE", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("PREPARE", stop - start);
   }

   public void activate(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.activate(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("ACTIVATE", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("ACTIVATE", stop - start);
   }

   public void deactivate(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.deactivate(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("DEACTIVATE", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("DEACTIVATE", stop - start);
   }

   public void unprepare(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.unprepare(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("UNPREPARE", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("UNPREPARE", stop - start);
   }

   public void remove(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.remove(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("REMOVE", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("REMOVE", stop - start);
   }

   public void prepareUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var12 = false;

      try {
         var12 = true;
         super.prepareUpdate(deploymentContext);
         var12 = false;
      } finally {
         if (var12) {
            long stop = System.currentTimeMillis();
            String[] uris = null;
            if (deploymentContext != null) {
               uris = deploymentContext.getUpdatedResourceURIs();
            }

            this.print("PREPARE_UPDATE uris: " + StringUtils.join(uris, ","), stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      String[] uris = null;
      if (deploymentContext != null) {
         uris = deploymentContext.getUpdatedResourceURIs();
      }

      this.print("PREPARE_UPDATE uris: " + StringUtils.join(uris, ","), stop - start);
   }

   public void activateUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var12 = false;

      try {
         var12 = true;
         super.activateUpdate(deploymentContext);
         var12 = false;
      } finally {
         if (var12) {
            long stop = System.currentTimeMillis();
            String[] uris = null;
            if (deploymentContext != null) {
               uris = deploymentContext.getUpdatedResourceURIs();
            }

            this.print("ACTIVATE_UPDATE uris: " + StringUtils.join(uris, ","), stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      String[] uris = null;
      if (deploymentContext != null) {
         uris = deploymentContext.getUpdatedResourceURIs();
      }

      this.print("ACTIVATE_UPDATE uris: " + StringUtils.join(uris, ","), stop - start);
   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
      long start = System.currentTimeMillis();
      boolean var12 = false;

      try {
         var12 = true;
         super.rollbackUpdate(deploymentContext);
         var12 = false;
      } finally {
         if (var12) {
            long stop = System.currentTimeMillis();
            String[] uris = null;
            if (deploymentContext != null) {
               uris = deploymentContext.getUpdatedResourceURIs();
            }

            this.print("ROLLBACK_UPDATE uris: " + StringUtils.join(uris, ","), stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      String[] uris = null;
      if (deploymentContext != null) {
         uris = deploymentContext.getUpdatedResourceURIs();
      }

      this.print("ROLLBACK_UPDATE uris: " + StringUtils.join(uris, ","), stop - start);
   }

   public void adminToProduction(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.adminToProduction(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("ADMIN_2_PRODUCTION", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("ADMIN_2_PRODUCTION", stop - start);
   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.gracefulProductionToAdmin(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("GRACEFUL_PRODUCTION_2_ADMIN", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("GRACEFUL_PRODUCTION_2_ADMIN", stop - start);
   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var10 = false;

      try {
         var10 = true;
         super.forceProductionToAdmin(deploymentContext);
         var10 = false;
      } finally {
         if (var10) {
            long stop = System.currentTimeMillis();
            this.print("FORCE_PRODUCTION_2_ADMIN", stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      this.print("FORCE_PRODUCTION_2_ADMIN", stop - start);
   }

   public void stop(DeploymentContext deploymentContext) throws DeploymentException {
      long start = System.currentTimeMillis();
      boolean var12 = false;

      try {
         var12 = true;
         super.stop(deploymentContext);
         var12 = false;
      } finally {
         if (var12) {
            long stop = System.currentTimeMillis();
            String[] uris = null;
            if (deploymentContext != null) {
               uris = deploymentContext.getUpdatedResourceURIs();
            }

            this.print("STOP uris: " + StringUtils.join(uris, ","), stop - start);
         }
      }

      long stop = System.currentTimeMillis();
      String[] uris = null;
      if (deploymentContext != null) {
         uris = deploymentContext.getUpdatedResourceURIs();
      }

      this.print("STOP uris: " + StringUtils.join(uris, ","), stop - start);
   }

   public ApplicationContext getApplicationContext() {
      return super.getApplicationContext();
   }
}
