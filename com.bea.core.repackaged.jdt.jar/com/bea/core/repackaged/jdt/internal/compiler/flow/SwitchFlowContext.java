package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;

public class SwitchFlowContext extends FlowContext {
   public BranchLabel breakLabel;
   public UnconditionalFlowInfo initsOnBreak;

   public SwitchFlowContext(FlowContext parent, ASTNode associatedNode, BranchLabel breakLabel, boolean isPreTest, boolean inheritNullFieldChecks) {
      super(parent, associatedNode, inheritNullFieldChecks);
      this.initsOnBreak = FlowInfo.DEAD_END;
      this.breakLabel = breakLabel;
      if (isPreTest && parent.conditionalLevel > -1) {
         ++this.conditionalLevel;
      }

   }

   public BranchLabel breakLabel() {
      return this.breakLabel;
   }

   public String individualToString() {
      StringBuffer buffer = new StringBuffer("Switch flow context");
      buffer.append("[initsOnBreak -").append(this.initsOnBreak.toString()).append(']');
      return buffer.toString();
   }

   public boolean isBreakable() {
      return true;
   }

   public void recordBreakFrom(FlowInfo flowInfo) {
      if ((this.initsOnBreak.tagBits & 1) == 0) {
         this.initsOnBreak = this.initsOnBreak.mergedWith(flowInfo.unconditionalInits());
      } else {
         this.initsOnBreak = flowInfo.unconditionalCopy();
      }

   }
}
