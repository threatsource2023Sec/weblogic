package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class IfStatement extends Statement {
   public Expression condition;
   public Statement thenStatement;
   public Statement elseStatement;
   int thenInitStateIndex = -1;
   int elseInitStateIndex = -1;
   int mergedInitStateIndex = -1;

   public IfStatement(Expression condition, Statement thenStatement, int sourceStart, int sourceEnd) {
      this.condition = condition;
      this.thenStatement = thenStatement;
      if (thenStatement instanceof EmptyStatement) {
         thenStatement.bits |= 1;
      }

      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
   }

   public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement, int sourceStart, int sourceEnd) {
      this.condition = condition;
      this.thenStatement = thenStatement;
      if (thenStatement instanceof EmptyStatement) {
         thenStatement.bits |= 1;
      }

      this.elseStatement = elseStatement;
      if (elseStatement instanceof IfStatement) {
         elseStatement.bits |= 536870912;
      }

      if (elseStatement instanceof EmptyStatement) {
         elseStatement.bits |= 1;
      }

      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      FlowInfo conditionFlowInfo = this.condition.analyseCode(currentScope, flowContext, flowInfo);
      int initialComplaintLevel = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
      Constant cst = this.condition.optimizedBooleanConstant();
      this.condition.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
      boolean isConditionOptimizedTrue = cst != Constant.NotAConstant && cst.booleanValue();
      boolean isConditionOptimizedFalse = cst != Constant.NotAConstant && !cst.booleanValue();
      ++flowContext.conditionalLevel;
      FlowInfo thenFlowInfo = conditionFlowInfo.safeInitsWhenTrue();
      if (isConditionOptimizedFalse) {
         thenFlowInfo.setReachMode(1);
      }

      FlowInfo elseFlowInfo = conditionFlowInfo.initsWhenFalse().copy();
      if (isConditionOptimizedTrue) {
         elseFlowInfo.setReachMode(1);
      }

      if ((flowInfo.tagBits & 3) == 0 && (thenFlowInfo.tagBits & 3) != 0) {
         this.bits |= 256;
      } else if ((flowInfo.tagBits & 3) == 0 && (elseFlowInfo.tagBits & 3) != 0) {
         this.bits |= 128;
      }

      boolean reportDeadCodeForKnownPattern = !isKnowDeadCodePattern(this.condition) || currentScope.compilerOptions().reportDeadCodeInTrivialIfStatement;
      if (this.thenStatement != null) {
         this.thenInitStateIndex = currentScope.methodScope().recordInitializationStates(thenFlowInfo);
         if (isConditionOptimizedFalse || (this.bits & 256) != 0) {
            if (reportDeadCodeForKnownPattern) {
               this.thenStatement.complainIfUnreachable(thenFlowInfo, currentScope, initialComplaintLevel, false);
            } else {
               this.bits &= -257;
            }
         }

         thenFlowInfo = this.thenStatement.analyseCode(currentScope, flowContext, thenFlowInfo);
         if (!(this.thenStatement instanceof Block)) {
            flowContext.expireNullCheckedFieldInfo();
         }
      }

      flowContext.expireNullCheckedFieldInfo();
      if ((thenFlowInfo.tagBits & 1) != 0) {
         this.bits |= 1073741824;
      }

      if (this.elseStatement != null) {
         if (thenFlowInfo == FlowInfo.DEAD_END && (this.bits & 536870912) == 0 && !(this.elseStatement instanceof IfStatement)) {
            currentScope.problemReporter().unnecessaryElse(this.elseStatement);
         }

         this.elseInitStateIndex = currentScope.methodScope().recordInitializationStates(elseFlowInfo);
         if (isConditionOptimizedTrue || (this.bits & 128) != 0) {
            if (reportDeadCodeForKnownPattern) {
               this.elseStatement.complainIfUnreachable(elseFlowInfo, currentScope, initialComplaintLevel, false);
            } else {
               this.bits &= -129;
            }
         }

         elseFlowInfo = this.elseStatement.analyseCode(currentScope, flowContext, elseFlowInfo);
         if (!(this.elseStatement instanceof Block)) {
            flowContext.expireNullCheckedFieldInfo();
         }
      }

      currentScope.correlateTrackingVarsIfElse(thenFlowInfo, elseFlowInfo);
      FlowInfo mergedInfo = FlowInfo.mergedOptimizedBranchesIfElse(thenFlowInfo, isConditionOptimizedTrue, elseFlowInfo, isConditionOptimizedFalse, true, flowInfo, this, reportDeadCodeForKnownPattern);
      this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
      --flowContext.conditionalLevel;
      return mergedInfo;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         BranchLabel endifLabel = new BranchLabel(codeStream);
         Constant cst;
         boolean hasThenPart = ((cst = this.condition.optimizedBooleanConstant()) == Constant.NotAConstant || cst.booleanValue()) && this.thenStatement != null && !this.thenStatement.isEmptyBlock();
         boolean hasElsePart = (cst == Constant.NotAConstant || !cst.booleanValue()) && this.elseStatement != null && !this.elseStatement.isEmptyBlock();
         if (hasThenPart) {
            BranchLabel falseLabel = null;
            if (cst != Constant.NotAConstant && cst.booleanValue()) {
               this.condition.generateCode(currentScope, codeStream, false);
            } else {
               this.condition.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, hasElsePart ? (falseLabel = new BranchLabel(codeStream)) : endifLabel, true);
            }

            if (this.thenInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.thenInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.thenInitStateIndex);
            }

            this.thenStatement.generateCode(currentScope, codeStream);
            if (hasElsePart) {
               if ((this.bits & 1073741824) == 0) {
                  this.thenStatement.branchChainTo(endifLabel);
                  int position = codeStream.position;
                  codeStream.goto_(endifLabel);
                  codeStream.recordPositionsFrom(position, this.thenStatement.sourceEnd);
               }

               if (this.elseInitStateIndex != -1) {
                  codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.elseInitStateIndex);
                  codeStream.addDefinitelyAssignedVariables(currentScope, this.elseInitStateIndex);
               }

               if (falseLabel != null) {
                  falseLabel.place();
               }

               this.elseStatement.generateCode(currentScope, codeStream);
            }
         } else if (hasElsePart) {
            if (cst != Constant.NotAConstant && !cst.booleanValue()) {
               this.condition.generateCode(currentScope, codeStream, false);
            } else {
               this.condition.generateOptimizedBoolean(currentScope, codeStream, endifLabel, (BranchLabel)null, true);
            }

            if (this.elseInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.elseInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.elseInitStateIndex);
            }

            this.elseStatement.generateCode(currentScope, codeStream);
         } else {
            this.condition.generateCode(currentScope, codeStream, false);
            codeStream.recordPositionsFrom(pc, this.sourceStart);
         }

         if (this.mergedInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
         }

         endifLabel.place();
         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output).append("if (");
      this.condition.printExpression(0, output).append(")\n");
      this.thenStatement.printStatement(indent + 2, output);
      if (this.elseStatement != null) {
         output.append('\n');
         printIndent(indent, output);
         output.append("else\n");
         this.elseStatement.printStatement(indent + 2, output);
      }

      return output;
   }

   public void resolve(BlockScope scope) {
      TypeBinding type = this.condition.resolveTypeExpecting(scope, TypeBinding.BOOLEAN);
      this.condition.computeConversion(scope, type, type);
      if (this.thenStatement != null) {
         this.thenStatement.resolve(scope);
      }

      if (this.elseStatement != null) {
         this.elseStatement.resolve(scope);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.condition.traverse(visitor, blockScope);
         if (this.thenStatement != null) {
            this.thenStatement.traverse(visitor, blockScope);
         }

         if (this.elseStatement != null) {
            this.elseStatement.traverse(visitor, blockScope);
         }
      }

      visitor.endVisit(this, blockScope);
   }

   public boolean doesNotCompleteNormally() {
      return this.thenStatement != null && this.thenStatement.doesNotCompleteNormally() && this.elseStatement != null && this.elseStatement.doesNotCompleteNormally();
   }

   public boolean completesByContinue() {
      return this.thenStatement != null && this.thenStatement.completesByContinue() || this.elseStatement != null && this.elseStatement.completesByContinue();
   }
}
