package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Deployment Operation",
   description = "This event covers the start of a deployment operation",
   path = "wls/Deployment/Deployment_Operation",
   thread = true
)
public class DeploymentOperationEvent extends DeploymentBaseInstantEvent {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Deployment Name",
      description = "The Deployment Name",
      relationKey = "http://www.oracle.com/wls/Deployment/deploymentName"
   )
   protected String deploymentName = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Operation Name",
      description = "The Operation Name",
      relationKey = "http://www.oracle.com/wls/Deployment/operationName"
   )
   protected String operationName = null;

   public String getDeploymentName() {
      return this.deploymentName;
   }

   public void setDeploymentName(String deploymentName) {
      this.deploymentName = deploymentName;
   }

   public String getOperationName() {
      return this.operationName;
   }

   public void setOperationName(String operationName) {
      this.operationName = operationName;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      if (args != null && args.length > 3) {
         if (args[1] != null && args[1] instanceof Long) {
            this.setRequestId((Long)args[1]);
         }

         if (args[2] != null) {
            this.setTaskId(args[2].toString());
         }

         if (args[3] != null && args[3] instanceof DeploymentInternalDataInfo) {
            DeploymentInternalDataInfo info = (DeploymentInternalDataInfo)args[3];
            this.deploymentName = info.getDeploymentName();
            this.operationName = info.getOperationName();
         }

      }
   }
}
