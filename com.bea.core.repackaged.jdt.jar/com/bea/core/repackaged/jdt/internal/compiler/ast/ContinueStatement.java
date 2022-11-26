package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;

public class ContinueStatement extends BranchStatement {
   public ContinueStatement(char[] label, int sourceStart, int sourceEnd) {
      super(label, sourceStart, sourceEnd);
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      FlowContext targetContext = this.label == null ? flowContext.getTargetContextForDefaultContinue() : flowContext.getTargetContextForContinueLabel(this.label);
      if (targetContext == null) {
         if (this.label == null) {
            currentScope.problemReporter().invalidContinue(this);
         } else {
            currentScope.problemReporter().undefinedLabel(this);
         }

         return flowInfo;
      } else {
         targetContext.recordAbruptExit();
         targetContext.expireNullCheckedFieldInfo();
         if (targetContext == FlowContext.NotContinuableContext) {
            currentScope.problemReporter().invalidContinue(this);
            return flowInfo;
         } else {
            this.initStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
            this.targetLabel = targetContext.continueLabel();
            FlowContext traversedContext = flowContext;
            int subCount = 0;
            this.subroutines = new SubRoutineStatement[5];

            do {
               SubRoutineStatement sub;
               if ((sub = traversedContext.subroutine()) != null) {
                  if (subCount == this.subroutines.length) {
                     System.arraycopy(this.subroutines, 0, this.subroutines = new SubRoutineStatement[subCount * 2], 0, subCount);
                  }

                  this.subroutines[subCount++] = sub;
                  if (sub.isSubRoutineEscaping()) {
                     break;
                  }
               }

               traversedContext.recordReturnFrom(flowInfo.unconditionalInits());
               if (traversedContext instanceof InsideSubRoutineFlowContext) {
                  ASTNode node = traversedContext.associatedNode;
                  if (node instanceof TryStatement) {
                     TryStatement tryStatement = (TryStatement)node;
                     flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
                  }
               } else if (traversedContext == targetContext) {
                  targetContext.recordContinueFrom(flowContext, flowInfo);
                  break;
               }
            } while((traversedContext = traversedContext.getLocalParent()) != null);

            if (subCount != this.subroutines.length) {
               System.arraycopy(this.subroutines, 0, this.subroutines = new SubRoutineStatement[subCount], 0, subCount);
            }

            return FlowInfo.DEAD_END;
         }
      }
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      printIndent(tab, output).append("continue ");
      if (this.label != null) {
         output.append(this.label);
      }

      return output.append(';');
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      visitor.visit(this, blockScope);
      visitor.endVisit(this, blockScope);
   }

   public boolean doesNotCompleteNormally() {
      return true;
   }

   public boolean completesByContinue() {
      return true;
   }
}
