package weblogic.messaging.saf;

public final class OperationState {
   private static final String RUNNING_STATE = "RUNNING";
   private static final String STOPPED_STATE = "STOPPED";
   private static final String COMPLETED_STATE = "COMPLETED";
   public static final OperationState RUNNING = new OperationState("RUNNING");
   public static final OperationState STOPPED = new OperationState("STOPPED");
   public static final OperationState COMPLETED = new OperationState("COMPLETED");
   private final String name;

   private OperationState(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}
