package weblogic.application.compiler;

import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.utils.compiler.ToolFailureException;

class BaseMerger implements Merger {
   private final FlowDriver driver = new FlowDriver();
   private final CompilerFlow[] flows;

   BaseMerger(CompilerFlow[] flows) {
      this.flows = flows;
   }

   public void merge() throws ToolFailureException {
      this.driver.nextState(this.flows);
   }

   public void cleanup() throws ToolFailureException {
      this.driver.previousState(this.flows);
   }
}
