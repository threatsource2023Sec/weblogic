package weblogic.deploy.service.internal.statemachines.targetserver;

public class AwaitingCancel extends TargetServerState {
   public TargetServerState receivedCancel() {
      this.fireStateTransitionEvent(this, "receivedCancel", this.getId());
      this.cancelIfNecessary();
      return this.getCurrentState();
   }

   public final String toString() {
      return "AwaitingCancel";
   }
}
