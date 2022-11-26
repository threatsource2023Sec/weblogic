package weblogic.application.compiler;

import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.utils.StateChange;
import weblogic.application.utils.StateChangeException;
import weblogic.application.utils.StateMachineDriver;
import weblogic.utils.compiler.ToolFailureException;

class FlowDriver {
   private static final String DEFAULT_ERROR = "Apps tool failure: Check nested exception for details";
   private final FlowStateChange fsc = new FlowStateChange();
   private final StateMachineDriver driver = new StateMachineDriver();

   public FlowDriver() {
   }

   void run(CompilerFlow[] flow) throws ToolFailureException {
      this.nextState(flow);
      this.previousState(flow);
   }

   void nextState(CompilerFlow[] flow) throws ToolFailureException {
      try {
         this.driver.nextState(this.fsc, flow);
      } catch (StateChangeException var3) {
         handleStateChangeException(var3);
      }

   }

   void previousState(CompilerFlow[] flow) throws ToolFailureException {
      try {
         this.driver.previousState(this.fsc, flow);
      } catch (StateChangeException var3) {
         handleStateChangeException(var3);
      }

   }

   private static void handleStateChangeException(StateChangeException ex) throws ToolFailureException {
      Throwable th = getCause(ex);
      if (th instanceof ToolFailureException) {
         throw (ToolFailureException)th;
      } else {
         throw new ToolFailureException(th.getMessage() == null ? "Apps tool failure: Check nested exception for details" : th.getMessage(), th);
      }
   }

   private static Throwable getCause(Throwable th) {
      return th.getCause() == null ? th : getCause(th.getCause());
   }

   private static final class FlowStateChange implements StateChange {
      private FlowStateChange() {
      }

      public void next(Object obj) throws Exception {
         ((CompilerFlow)obj).compile();
      }

      public void previous(Object obj) throws Exception {
         ((CompilerFlow)obj).cleanup();
      }

      public void logRollbackError(StateChangeException e) {
         System.err.println("Error cleaning up " + e);
         e.printStackTrace();
      }

      // $FF: synthetic method
      FlowStateChange(Object x0) {
         this();
      }
   }
}
