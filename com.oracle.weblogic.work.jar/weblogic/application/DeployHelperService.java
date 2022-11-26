package weblogic.application;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;

@Contract
public interface DeployHelperService {
   DeploymentContext createDeploymentContext(BasicDeploymentMBean var1) throws DeploymentException;
}
