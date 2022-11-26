package weblogic.management.deploy.internal;

import weblogic.management.ManagementException;
import weblogic.management.deploy.DeploymentTaskRuntime;

public interface TaskRuntimeValidator {
   void validate(DeploymentTaskRuntime var1, DeploymentTaskRuntime var2) throws ManagementException;
}
