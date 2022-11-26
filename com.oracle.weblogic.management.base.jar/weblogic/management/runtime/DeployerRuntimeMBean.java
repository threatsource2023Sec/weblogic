package weblogic.management.runtime;

import java.util.Map;
import javax.management.InstanceNotFoundException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;

/** @deprecated */
@Deprecated
public interface DeployerRuntimeMBean extends RuntimeMBean {
   String DEPLOYER_NAME = "DeployerRuntime";

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean activate(String var1, String var2, String var3, DeploymentData var4, String var5) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean activate(String var1, String var2, String var3, DeploymentData var4, String var5, boolean var6) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean deactivate(String var1, DeploymentData var2, String var3) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean deactivate(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean remove(String var1, DeploymentData var2, String var3) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean remove(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean unprepare(String var1, DeploymentData var2, String var3) throws ManagementException;

   /** @deprecated */
   @Deprecated
   DeploymentTaskRuntimeMBean unprepare(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean distribute(String var1, String var2, DeploymentData var3, String var4) throws ManagementException;

   DeploymentTaskRuntimeMBean distribute(String var1, String var2, DeploymentData var3, String var4, boolean var5) throws ManagementException;

   DeploymentTaskRuntimeMBean appendToExtensionLoader(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean start(String var1, DeploymentData var2, String var3) throws ManagementException;

   DeploymentTaskRuntimeMBean start(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean stop(String var1, DeploymentData var2, String var3) throws ManagementException;

   DeploymentTaskRuntimeMBean stop(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean deploy(String var1, String var2, String var3, DeploymentData var4, String var5) throws ManagementException;

   DeploymentTaskRuntimeMBean deploy(String var1, String var2, String var3, DeploymentData var4, String var5, boolean var6) throws ManagementException;

   DeploymentTaskRuntimeMBean redeploy(String var1, DeploymentData var2, String var3) throws ManagementException;

   DeploymentTaskRuntimeMBean redeploy(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean redeploy(String var1, String var2, DeploymentData var3, String var4, boolean var5) throws ManagementException;

   DeploymentTaskRuntimeMBean update(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean undeploy(String var1, DeploymentData var2, String var3) throws ManagementException;

   DeploymentTaskRuntimeMBean undeploy(String var1, DeploymentData var2, String var3, boolean var4) throws ManagementException;

   DeploymentTaskRuntimeMBean query(String var1);

   DeploymentTaskRuntimeMBean[] list();

   DeploymentTaskRuntimeMBean[] getDeploymentTaskRuntimes();

   boolean removeTask(String var1);

   DeploymentMBean[] getDeployments(TargetMBean var1);

   String[] purgeRetiredTasks();

   /** @deprecated */
   @Deprecated
   Map getAvailabilityStatusForApplication(String var1, boolean var2) throws InstanceNotFoundException;

   /** @deprecated */
   @Deprecated
   Map getAvailabilityStatusForComponent(ComponentMBean var1, boolean var2) throws InstanceNotFoundException;
}
