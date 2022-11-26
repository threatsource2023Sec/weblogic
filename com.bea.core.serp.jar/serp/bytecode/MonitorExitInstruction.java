package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class MonitorExitInstruction extends MonitorInstruction {
   MonitorExitInstruction(Code owner) {
      super(owner, 195);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterMonitorExitInstruction(this);
      visit.exitMonitorExitInstruction(this);
   }
}
