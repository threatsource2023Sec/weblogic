package weblogic.management.deploy;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;

@Contract
public interface ApplicationUtilsInterface {
   boolean isDeploymentScopedToResourceGroupOrTemplate(DeploymentData var1);

   TargetMBean[] getActualTargets(TargetInfoMBean var1);
}
