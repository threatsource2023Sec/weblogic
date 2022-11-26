package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Deployment Base Instant",
   description = "This defines the values common to all Deployment events",
   path = "wls/Deployment/Deployment_Base_Instant",
   thread = true
)
public class DeploymentBaseInstantEvent extends BaseInstantEvent implements DeploymentEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/Deployment"
   )
   protected String subsystem = "Deployment";
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Task Id",
      description = "The task idenitifier",
      relationKey = "http://www.oracle.com/wls/Deployment/taskId"
   )
   protected String taskId = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Request Id",
      description = "The request identifier",
      relationKey = "http://www.oracle.com/wls/Deployment/requestId"
   )
   protected long requestId;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Application Name",
      description = "The application name",
      relationKey = "http://www.oracle.com/wls/Deployment/appName"
   )
   protected String appName = null;
   private boolean hasRequestId = false;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      DeploymentEventInfoHelper.populateExtensions((Object)null, args, this);
   }

   public String getTaskId() {
      return this.taskId;
   }

   public void setTaskId(String taskId) {
      this.taskId = taskId;
   }

   public long getRequestId() {
      return this.requestId;
   }

   public void setRequestId(long requestId) {
      this.requestId = requestId;
      this.hasRequestId = true;
   }

   public String getAppName() {
      return this.appName;
   }

   public void setAppName(String appName) {
      this.appName = appName;
   }

   public boolean hasRequestId() {
      return this.hasRequestId;
   }

   public boolean isPopulated() {
      return this.appName != null && this.hasRequestId && this.taskId != null;
   }
}
