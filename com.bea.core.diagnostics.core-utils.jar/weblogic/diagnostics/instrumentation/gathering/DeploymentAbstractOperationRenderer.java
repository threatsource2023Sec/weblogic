package weblogic.diagnostics.instrumentation.gathering;

import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.operations.AbstractOperation;
import weblogic.diagnostics.instrumentation.ValueRenderer;

public class DeploymentAbstractOperationRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject instanceof AbstractOperation) {
         AbstractOperation operation = (AbstractOperation)inputObject;
         BasicDeployment dep = operation.getApplication();
         return new DeploymentEventInfoImpl(operation.getTaskId(), operation.getRequestId(), dep == null ? null : dep.getName());
      } else {
         return null;
      }
   }
}
