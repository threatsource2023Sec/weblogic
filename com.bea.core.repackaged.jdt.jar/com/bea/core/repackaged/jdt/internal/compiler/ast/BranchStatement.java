package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;

public abstract class BranchStatement extends Statement {
   public char[] label;
   public BranchLabel targetLabel;
   public SubRoutineStatement[] subroutines;
   public int initStateIndex = -1;

   public BranchStatement(char[] label, int sourceStart, int sourceEnd) {
      this.label = label;
      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
   }

   protected void generateExpressionResultCode(BlockScope currentScope, CodeStream codeStream) {
   }

   protected void adjustStackSize(BlockScope currentScope, CodeStream codeStream) {
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         this.generateExpressionResultCode(currentScope, codeStream);
         int pc = codeStream.position;
         if (this.subroutines != null) {
            int i = 0;

            for(int max = this.subroutines.length; i < max; ++i) {
               SubRoutineStatement sub = this.subroutines[i];
               boolean didEscape = sub.generateSubRoutineInvocation(currentScope, codeStream, this.targetLabel, this.initStateIndex, (LocalVariableBinding)null);
               if (didEscape) {
                  codeStream.recordPositionsFrom(pc, this.sourceStart);
                  SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, i, codeStream);
                  if (this.initStateIndex != -1) {
                     codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.initStateIndex);
                     codeStream.addDefinitelyAssignedVariables(currentScope, this.initStateIndex);
                  }

                  return;
               }
            }
         }

         codeStream.goto_(this.targetLabel);
         this.adjustStackSize(currentScope, codeStream);
         codeStream.recordPositionsFrom(pc, this.sourceStart);
         SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, -1, codeStream);
         if (this.initStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.initStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.initStateIndex);
         }

      }
   }

   public void resolve(BlockScope scope) {
   }
}
