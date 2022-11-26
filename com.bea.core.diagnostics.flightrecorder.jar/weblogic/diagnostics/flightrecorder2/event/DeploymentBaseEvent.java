package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.DeploymentEventInfo;
import weblogic.diagnostics.flightrecorder.event.DeploymentEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Deployment Base Instant")
@Description("This defines the values common to all Deployment events")
@Category({"WebLogic Server", "Deployment"})
@Name("com.oracle.weblogic.deployment.DeploymentBaseEvent")
public abstract class DeploymentBaseEvent extends BaseEvent implements DeploymentEventInfo {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Deployment")
   protected String subsystem = "Deployment";
   @Label("Task Id")
   @Description("The task idenitifier")
   @RelationKey("http://www.oracle.com/wls/Deployment/taskId")
   protected String taskId = null;
   @Label("Request Id")
   @Description("The request identifier")
   @RelationKey("http://www.oracle.com/wls/Deployment/requestId")
   protected long requestId;
   @Label("Application Name")
   @Description("The application name")
   @RelationKey("http://www.oracle.com/wls/Deployment/appName")
   protected String appName = null;
   private boolean hasRequestId = false;

   public DeploymentBaseEvent() {
   }

   public DeploymentBaseEvent(BaseEvent e) {
      super(e);
   }

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
