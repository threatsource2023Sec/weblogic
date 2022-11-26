package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class BreakStatement extends BranchStatement {
   public Expression expression;
   public SwitchExpression switchExpression;
   public boolean isImplicit;

   public BreakStatement(char[] label, int sourceStart, int e) {
      super(label, sourceStart, e);
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      FlowContext targetContext = this.label == null ? flowContext.getTargetContextForDefaultBreak() : flowContext.getTargetContextForBreakLabel(this.label);
      if (targetContext == null) {
         if (this.label == null) {
            currentScope.problemReporter().invalidBreak(this);
         } else if (this.switchExpression == null) {
            currentScope.problemReporter().undefinedLabel(this);
         }

         return flowInfo;
      } else {
         if ((this.isImplicit || this.switchExpression != null) && this.expression != null) {
            flowInfo = this.expression.analyseCode(currentScope, flowContext, flowInfo);
            this.expression.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
            if (flowInfo.reachMode() == 0 && currentScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
               this.checkAgainstNullAnnotation(currentScope, flowContext, flowInfo, this.expression);
            }
         }

         targetContext.recordAbruptExit();
         targetContext.expireNullCheckedFieldInfo();
         this.initStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
         this.targetLabel = targetContext.breakLabel();
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
            traversedContext.recordBreakTo(targetContext);
            if (traversedContext instanceof InsideSubRoutineFlowContext) {
               ASTNode node = traversedContext.associatedNode;
               if (node instanceof TryStatement) {
                  TryStatement tryStatement = (TryStatement)node;
                  flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
               }
            } else if (traversedContext == targetContext) {
               targetContext.recordBreakFrom(flowInfo);
               break;
            }
         } while((traversedContext = traversedContext.getLocalParent()) != null);

         if (subCount != this.subroutines.length) {
            System.arraycopy(this.subroutines, 0, this.subroutines = new SubRoutineStatement[subCount], 0, subCount);
         }

         return FlowInfo.DEAD_END;
      }
   }

   protected void generateExpressionResultCode(BlockScope currentScope, CodeStream codeStream) {
      if (this.label == null && this.expression != null) {
         this.expression.generateCode(currentScope, codeStream, this.switchExpression != null);
      }

   }

   protected void adjustStackSize(BlockScope currentScope, CodeStream codeStream) {
      if (this.label == null && this.expression != null && this.switchExpression != null) {
         TypeBinding postConversionType = this.expression.postConversionType(currentScope);
         switch (postConversionType.id) {
            case 6:
               break;
            case 7:
            case 8:
               codeStream.decrStackSize(2);
               break;
            default:
               codeStream.decrStackSize(1);
         }
      }

   }

   public void resolve(BlockScope scope) {
      super.resolve(scope);
      if (this.expression != null && (this.switchExpression != null || this.isImplicit)) {
         if (this.switchExpression == null && this.isImplicit && !this.expression.statementExpression() && scope.compilerOptions().enablePreviewFeatures) {
            scope.problemReporter().invalidExpressionAsStatement(this.expression);
            return;
         }

         this.expression.resolveType(scope);
      } else if (this.expression == null && this.switchExpression != null) {
         scope.problemReporter().switchExpressionBreakMissingValue(this);
      }

   }

   public TypeBinding resolveExpressionType(BlockScope scope) {
      return this.expression != null ? this.expression.resolveType(scope) : null;
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      if (!this.isImplicit) {
         printIndent(tab, output).append("break");
      }

      if (this.label != null) {
         output.append(' ').append(this.label);
      }

      if (this.expression != null) {
         output.append(' ');
         this.expression.printExpression(tab, output);
      }

      return output.append(';');
   }

   public void traverse(ASTVisitor visitor, BlockScope blockscope) {
      if (visitor.visit(this, blockscope) && this.expression != null) {
         this.expression.traverse(visitor, blockscope);
      }

      visitor.endVisit(this, blockscope);
   }

   public boolean doesNotCompleteNormally() {
      return true;
   }
}
