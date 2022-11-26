package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Deployment Complete")
@Name("com.oracle.weblogic.deployment.DeploymentCompleteEvent")
@Description("This event covers the completion of a deployment operation")
@Category({"WebLogic Server", "Deployment"})
public class DeploymentCompleteEvent extends DeploymentBaseEvent {
   @Label("Status")
   @Description("The completion status")
   @RelationKey("http://www.oracle.com/wls/Deployment/status")
   protected String status = null;
   @Label("Throwable Message")
   @Description("The Throwable Message if present")
   @RelationKey("http://www.oracle.com/wls/Deployment/throwableMessage")
   protected String throwableMessage = null;

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getThrowableMessage() {
      return this.throwableMessage;
   }

   public void setThrowableMessage(String throwableMessage) {
      this.throwableMessage = throwableMessage;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      super.populateExtensions((Object)null, args, djp, isAfter);
      if (args != null && args.length >= 3) {
         if (args[1] != null) {
            this.status = args[1].toString();
         }

         if (args[2] != null) {
            this.throwableMessage = args[2].toString();
         }

      }
   }
}
