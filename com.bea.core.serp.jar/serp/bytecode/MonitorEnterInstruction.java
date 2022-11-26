package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class MonitorEnterInstruction extends MonitorInstruction {
   MonitorEnterInstruction(Code owner) {
      super(owner, 194);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterMonitorEnterInstruction(this);
      visit.exitMonitorEnterInstruction(this);
   }
}
