package weblogic.management.deploy.internal;

import java.io.Serializable;
import weblogic.deploy.common.Debug;
import weblogic.utils.PlatformConstants;

public class AppTargetState implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String EOL;
   private String appName;
   private String target;
   private String state;
   private int stagingState;

   public AppTargetState() {
      this.stagingState = -1;
   }

   public AppTargetState(String theAppName) {
      this();
      this.setAppName(theAppName);
   }

   public AppTargetState(String theAppName, String theTarget) {
      this(theAppName);
      this.setTarget(theTarget);
   }

   public void setAppName(String appName) {
      this.appName = appName;
   }

   public void setTarget(String theTarget) {
      this.target = theTarget;
   }

   public String getState() {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug(" +++ Returning app's state : " + this.state);
      }

      return this.state;
   }

   public void setState(String state) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug(" +++ Setting app's state : " + state);
      } else if (Debug.isDeploymentDebugConciseEnabled()) {
         Debug.deploymentDebugConcise("Setting state for: " + this + ", to: " + state);
      }

      this.state = state;
   }

   public final int getStagingState() {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug(" +++ Getting app's stage state : " + this.stagingState);
      }

      return this.stagingState;
   }

   public final void setStagingState(int stagingState) {
      if (stagingState >= 0 && stagingState <= 1) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug(" +++ Setting app's stage state : " + stagingState);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Setting stagingState for: " + this + ", to: " + stagingState);
         }

         this.stagingState = stagingState;
      } else {
         throw new AssertionError("Given stagingState '" + stagingState + "' is invalid. It should be in the range'" + 0 + "' or '" + 1 + "'");
      }
   }

   public void pretty(StringBuffer sb) {
      sb.append("Application Name: " + this.appName + EOL);
      sb.append("Target: " + this.target + EOL);
      sb.append("State: " + this.state + EOL);
      sb.append("Staging state: " + this.stagingState + EOL);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (!Debug.isDeploymentDebugConciseEnabled()) {
         sb.append(super.toString());
      }

      sb.append("(");
      sb.append("appName=").append(this.appName).append(", ");
      sb.append("target=").append(this.target).append(", ");
      sb.append("state=").append(this.state).append(", ");
      sb.append("stagingState=").append(this.stagingState).append(")");
      return sb.toString();
   }

   static {
      EOL = PlatformConstants.EOL;
   }
}
