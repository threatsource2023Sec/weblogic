package com.oracle.injection.integration;

import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.management.configuration.AppDeploymentMBean;

class CDIAppDeploymentExtensionFactory implements AppDeploymentExtensionFactory {
   public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
      if (appCtx != null) {
         AppDeploymentMBean appDeploymentMBean = appCtx.getAppDeploymentMBean();
         if (appDeploymentMBean != null) {
            if (!appDeploymentMBean.isInternalApp()) {
               return new CDIAppDeploymentExtension();
            }

            return null;
         }
      }

      return new CDIAppDeploymentExtension();
   }

   public CDIAppValidationExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
      if (appCtx != null) {
         AppDeploymentMBean appDeploymentMBean = appCtx.getAppDeploymentMBean();
         if (appDeploymentMBean != null) {
            if (!appDeploymentMBean.isInternalApp()) {
               return new CDIAppValidationExtension();
            }

            return null;
         }
      }

      return new CDIAppValidationExtension();
   }
}
