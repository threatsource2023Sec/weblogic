package weblogic.diagnostics.instrumentation.gathering;

import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class DeploymentInternalDataRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject instanceof InternalDeploymentData) {
         InternalDeploymentData intData = (InternalDeploymentData)inputObject;
         DeploymentEventInfoImpl info = new DeploymentEventInfoImpl(intData.getDeploymentTaskRuntimeId());
         info.setAppName(intData.getDeploymentName());
         return new DeploymentInternalDataInfoImpl(intData.getDeploymentOperation(), intData.getDeploymentName());
      } else {
         return null;
      }
   }
}
