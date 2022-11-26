package weblogic.coherence.container.server;

import org.jvnet.hk2.annotations.Service;
import weblogic.application.ModuleException;
import weblogic.cacheprovider.coherence.CoherenceClusterContainer;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.coherence.api.internal.CoherenceException;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.EJBComponentRuntimeMBean;

@Service
public class CoherenceServiceImpl implements CoherenceService {
   public void setupCoherenceCaches(ClassLoader loader, ComponentRuntimeMBean compRTMBean, CoherenceClusterRefBean coherenceClusterRefBean) throws DeploymentException {
      CoherenceClusterContainer ccc = new EJBCoherenceClusterContainerImpl(coherenceClusterRefBean, (EJBComponentRuntimeMBean)compRTMBean);

      try {
         CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
         mgr.startUp(loader, ccc);
      } catch (ModuleException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new ModuleException(var7.getMessage(), var7);
      }
   }

   public void releaseCoherenceCaches(ClassLoader loader, ComponentRuntimeMBean compRTMBean, String uri, CoherenceClusterRefBean coherenceClusterRefBean) throws ManagementException, CoherenceException {
      CoherenceClusterContainer ccc = new EJBCoherenceClusterContainerImpl(coherenceClusterRefBean, (EJBComponentRuntimeMBean)compRTMBean);
      CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
      mgr.tearDown(loader, ccc);
   }

   private static class EJBCoherenceClusterContainerImpl implements CoherenceClusterContainer {
      private final CoherenceClusterRefBean coherenceClusterRefBean;
      private final EJBComponentRuntimeMBean runtimeMBean;

      private EJBCoherenceClusterContainerImpl(CoherenceClusterRefBean coherenceClusterRefBean, EJBComponentRuntimeMBean runtimeMBean) {
         this.coherenceClusterRefBean = coherenceClusterRefBean;
         this.runtimeMBean = runtimeMBean;
      }

      public CoherenceClusterRefBean getCoherenceClusterRefBean() {
         return this.coherenceClusterRefBean;
      }

      public void registerRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
         mgr.registerEJBComponentRuntimeMBean(loader, this.runtimeMBean);
      }

      public void unRegisterRuntimeMBeans(CoherenceClusterManager mgr, ClassLoader loader) throws ManagementException {
         mgr.unRegisterEJBComponentRuntimeMBean(loader, this.runtimeMBean);
      }

      // $FF: synthetic method
      EJBCoherenceClusterContainerImpl(CoherenceClusterRefBean x0, EJBComponentRuntimeMBean x1, Object x2) {
         this(x0, x1);
      }
   }
}
