package weblogic.cluster.migration.rmiservice;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigratableRemote;
import weblogic.cluster.migration.MigrationManagerService;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.MigratableRMIServiceMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;

@Service
@Named
@RunLevel(10)
public final class MigratableRMIService extends AbstractServerService implements DeploymentHandler {
   private static MigratableRMIService singleton = null;
   private final Map deployments = new HashMap();

   private MigratableRMIService() {
      Debug.assertion(singleton == null, "MigratableRMIService singleton already set");
      singleton = this;
   }

   public void start() throws ServiceFailureException {
      DeploymentHandlerHome.addDeploymentHandler(this);
   }

   public void stop() throws ServiceFailureException {
      this.shutdown();
   }

   public void halt() throws ServiceFailureException {
      this.shutdown();
   }

   public void shutdown() throws ServiceFailureException {
      DeploymentHandlerHome.removeDeploymentHandler(this);
   }

   private void deploy(MigratableRMIServiceMBean mbean) {
      try {
         if (!this.deployments.containsKey(mbean.getName())) {
            Class cl = Class.forName(mbean.getClassname());
            Object o = cl.newInstance();
            MigratableRemote m = (MigratableRemote)o;
            ((Initialization)m).initialize(mbean.getArgument());
            Debug.assertion(mbean.getTargets().length == 1, "[MigratableRMIService] OAM did not localize targets properly!");
            MigratableTargetMBean mt = (MigratableTargetMBean)((MigratableTargetMBean)mbean.getTargets()[0]);
            String name = mbean.getArgument();
            MigrationManagerService.singleton().register(m, name, mt);
            this.deployments.put(mbean.getName(), m);
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   private void undeploy(MigratableRMIServiceMBean mbean) {
      try {
         MigratableRemote m = (MigratableRemote)this.deployments.remove(mbean.getName());
         if (m != null) {
            Debug.assertion(m != null);
            ((Initialization)m).destroy(mbean.getArgument());
            Debug.assertion(mbean.getTargets().length == 1, "[MigratableRMIService] OAM did not localize targets properly!");
            MigratableTargetMBean mt = (MigratableTargetMBean)((MigratableTargetMBean)mbean.getTargets()[0]);
            String name = mbean.getArgument();
            MigrationManagerService.singleton().unregister((Migratable)m, (MigratableTargetMBean)mt);
         } else {
            Debug.say("--> undeploy called but m is null!");
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext ctx) throws DeploymentException {
      if (deployment instanceof MigratableRMIServiceMBean) {
         try {
            this.deploy((MigratableRMIServiceMBean)deployment);
         } catch (Exception var4) {
            throw new DeploymentException("failed to deploy MigratableRMIServiceMBean " + deployment.getName(), var4);
         }
      }

   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      if (deployment instanceof MigratableRMIServiceMBean) {
         try {
            this.undeploy((MigratableRMIServiceMBean)deployment);
         } catch (Exception var4) {
            throw new UndeploymentException("failed to undeploy MigratableRMIServiceMBean " + deployment.getName(), var4);
         }
      }

   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
   }
}
