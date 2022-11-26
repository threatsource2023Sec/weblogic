package weblogic.persistence;

import java.io.IOException;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.AppDeploymentExtensionFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.application.utils.PersistenceUtils;
import weblogic.management.DeploymentException;

public class PersistenceDeployment {
   static class PersistenceDeploymentExtension extends BaseAppDeploymentExtension {
      private static final String EAR_PERSISTENCE_UNIT_REGISTRY_CLASS_NAME = EarPersistenceUnitRegistry.class.getName();

      public void init(ApplicationContextInternal appCtx) throws DeploymentException {
         if (appCtx.isEar()) {
            try {
               PersistenceUtils.addRootPersistenceJars(appCtx.getAppClassLoader(), appCtx.getApplicationId(), appCtx.getApplicationDD());
               PersistenceUnitRegistry reg = new EarPersistenceUnitRegistry(appCtx.getAppClassLoader(), appCtx);
               appCtx.putUserObject(EAR_PERSISTENCE_UNIT_REGISTRY_CLASS_NAME, reg);
            } catch (IOException | EnvironmentException var3) {
               throw new DeploymentException(var3);
            }
         }
      }

      public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
         if (appCtx.isEar()) {
            try {
               EarPersistenceUnitRegistry pureg = (EarPersistenceUnitRegistry)appCtx.getUserObject(EAR_PERSISTENCE_UNIT_REGISTRY_CLASS_NAME);
               if (pureg != null) {
                  pureg.initialize();
               }

            } catch (EnvironmentException var3) {
               throw new DeploymentException(var3);
            }
         }
      }

      public void activate(ApplicationContextInternal appCtx) throws DeploymentException {
         if (appCtx.isEar()) {
            AbstractPersistenceUnitRegistry registry = (AbstractPersistenceUnitRegistry)appCtx.getUserObject(EAR_PERSISTENCE_UNIT_REGISTRY_CLASS_NAME);
            if (registry != null) {
               ApplicationRuntimeMBeanImpl appRTMBean = appCtx.getRuntime();
               if (appRTMBean != null) {
                  try {
                     registry.setParentRuntimeMBean(appRTMBean, appRTMBean);
                  } catch (EnvironmentException var5) {
                     throw new DeploymentException(var5);
                  }
               }
            }

         }
      }

      public void unprepare(ApplicationContextInternal appCtx) throws DeploymentException {
         if (appCtx.isEar()) {
            PersistenceUnitRegistry pureg = (PersistenceUnitRegistry)appCtx.getUserObject(EAR_PERSISTENCE_UNIT_REGISTRY_CLASS_NAME);
            if (pureg != null) {
               pureg.close();
               pureg = null;
            }

         }
      }
   }

   public static class PersistenceDeploymentExtensionFactory implements AppDeploymentExtensionFactory {
      public AppDeploymentExtension createPostProcessorExtension(ApplicationContextInternal appCtx) {
         return null;
      }

      public AppDeploymentExtension createPreProcessorExtension(ApplicationContextInternal appCtx) {
         return new PersistenceDeploymentExtension();
      }
   }
}
