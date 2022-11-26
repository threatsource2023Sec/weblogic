package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Deployment Complete",
   description = "This event covers the completion of a deployment operation",
   path = "wls/Deployment/Deployment_Complete",
   thread = true
)
public class DeploymentCompleteEvent extends DeploymentBaseInstantEvent {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Status",
      description = "The completion status",
      relationKey = "http://www.oracle.com/wls/Deployment/status"
   )
   protected String status = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Throwable Message",
      description = "The Throwable Message if present",
      relationKey = "http://www.oracle.com/wls/Deployment/throwableMessage"
   )
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
