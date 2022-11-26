package weblogic.cacheprovider.coherence.application;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.cacheprovider.CacheProviderLogger;
import weblogic.cacheprovider.coherence.CoherenceClusterContainer;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;

public class CoherenceAppDeploymentExtension extends BaseAppDeploymentExtension {
   public void prepare(final ApplicationContextInternal appCtx) throws DeploymentException {
      if (appCtx.isEar()) {
         try {
            ClassLoader loader = appCtx.getAppClassLoader();
            final CoherenceClusterRefBean clusterRef = this.getCoherenceClusterRefBean(appCtx);
            CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
            mgr.startUp(loader, new CoherenceClusterContainer() {
               public void unRegisterRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
               }

               public void registerRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
                  mgr.registerApplicationRuntimeMBean(loader, appCtx.getRuntime());
               }

               public CoherenceClusterRefBean getCoherenceClusterRefBean() {
                  return clusterRef;
               }
            });
         } catch (DeploymentException var5) {
            throw var5;
         } catch (Exception var6) {
            throw new DeploymentException(var6.getMessage(), var6);
         }
      }
   }

   public void unprepare(final ApplicationContextInternal appCtx) throws DeploymentException {
      if (appCtx.isEar()) {
         try {
            ClassLoader loader = appCtx.getAppClassLoader();
            final CoherenceClusterRefBean clusterRef = this.getCoherenceClusterRefBean(appCtx);
            CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
            mgr.tearDown(loader, new CoherenceClusterContainer() {
               public void unRegisterRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
                  mgr.unRegisterApplicationRuntimeMBean(loader, appCtx.getRuntime());
               }

               public void registerRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
               }

               public CoherenceClusterRefBean getCoherenceClusterRefBean() {
                  return clusterRef;
               }
            });
         } catch (Exception var5) {
            CacheProviderLogger.logFailedToUnprepare(appCtx.getApplicationId(), var5);
         }

      }
   }

   private CoherenceClusterRefBean getCoherenceClusterRefBean(ApplicationContextInternal appCtx) {
      CoherenceClusterRefBean clusterRef = null;
      WeblogicApplicationBean wlAppBean = appCtx.getWLApplicationDD();
      if (wlAppBean != null) {
         clusterRef = wlAppBean.getCoherenceClusterRef();
      }

      return clusterRef;
   }
}
