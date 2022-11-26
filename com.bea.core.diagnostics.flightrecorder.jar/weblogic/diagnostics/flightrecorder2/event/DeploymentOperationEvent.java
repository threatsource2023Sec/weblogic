package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.DeploymentInternalDataInfo;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Deployment Operation")
@Name("com.oracle.weblogic.deployment.DeploymentOperationEvent")
@Description("This event covers the start of a deployment operation")
@Category({"WebLogic Server", "Deployment"})
public class DeploymentOperationEvent extends DeploymentBaseEvent {
   @Label("Deployment Name")
   @Description("The Deployment Name")
   @RelationKey("http://www.oracle.com/wls/Deployment/deploymentName")
   protected String deploymentName = null;
   @Label("Operation Name")
   @Description("The Operation Name")
   @RelationKey("http://www.oracle.com/wls/Deployment/operationName")
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
