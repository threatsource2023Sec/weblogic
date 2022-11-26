package weblogic.management.deploy;

public final class DeploymentCompatibilityEvent {
   private String eventType = null;
   private String serverName = null;
   private String applicationName = null;
   private String applicationPhase = null;
   private String transition = null;
   private String moduleName = null;
   private String currentState = null;
   private String targetState = null;
   private String taskID = null;

   public DeploymentCompatibilityEvent(String eventType, String serverName, String applicationName, String applicationPhase, String transition, String moduleName, String currentState, String targetState, String taskID) {
      this.eventType = eventType;
      this.serverName = serverName;
      this.applicationName = applicationName;
      this.applicationPhase = applicationPhase;
      this.transition = transition;
      this.moduleName = moduleName;
      this.currentState = currentState;
      this.targetState = targetState;
      this.taskID = taskID;
   }

   public String getEventType() {
      return this.eventType;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getApplicationPhase() {
      return this.applicationPhase;
   }

   public String getTransition() {
      return this.transition;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getCurrentState() {
      return this.currentState;
   }

   public String getTargetState() {
      return this.targetState;
   }

   public String getTaskID() {
      return this.taskID;
   }

   public String toString() {
      StringBuffer s = new StringBuffer("DeployEvent[");
      if (this.eventType.equals("weblogic.deployment.application.module")) {
         s.append("MODULE.").append(this.applicationName).append(".").append(this.moduleName).append(".").append(this.currentState).append(">").append(this.targetState).append(".").append(this.transition).append("@").append(this.serverName);
      } else {
         s.append("APP.").append(this.applicationName).append(".").append(this.applicationPhase).append("@").append(this.serverName);
      }

      s.append("]").toString();
      return s.toString();
   }
}
