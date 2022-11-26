package javax.enterprise.deploy.spi.status;

import java.util.EventObject;
import javax.enterprise.deploy.spi.TargetModuleID;

public class ProgressEvent extends EventObject {
   private transient DeploymentStatus statuscode;
   private transient TargetModuleID targetModuleID;

   public ProgressEvent(Object source, TargetModuleID targetModuleID, DeploymentStatus sCode) {
      super(source);
      this.statuscode = sCode;
      this.targetModuleID = targetModuleID;
   }

   public TargetModuleID getTargetModuleID() {
      return this.targetModuleID;
   }

   public DeploymentStatus getDeploymentStatus() {
      return this.statuscode;
   }
}
