package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.LoopingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class DoStatement extends Statement {
   public Expression condition;
   public Statement action;
   private BranchLabel breakLabel;
   private BranchLabel continueLabel;
   int mergedInitStateIndex = -1;
   int preConditionInitStateIndex = -1;

   public DoStatement(Expression condition, Statement action, int sourceStart, int sourceEnd) {
      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
      this.condition = condition;
      this.action = action;
      if (action instanceof EmptyStatement) {
         action.bits |= 1;
      }

   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      this.breakLabel = new BranchLabel();
      this.continueLabel = new BranchLabel();
      LoopingFlowContext loopingContext = new LoopingFlowContext(flowContext, flowInfo, this, this.breakLabel, this.continueLabel, currentScope, false);
      Constant cst = this.condition.constant;
      boolean isConditionTrue = cst != Constant.NotAConstant && cst.booleanValue();
      cst = this.condition.optimizedBooleanConstant();
      boolean isConditionOptimizedTrue = cst != Constant.NotAConstant && cst.booleanValue();
      boolean isConditionOptimizedFalse = cst != Constant.NotAConstant && !cst.booleanValue();
      int previousMode = flowInfo.reachMode();
      FlowInfo initsOnCondition = flowInfo;
      UnconditionalFlowInfo actionInfo = flowInfo.nullInfoLessUnconditionalCopy();
      if (this.action != null && !this.action.isEmptyBlock()) {
         actionInfo = this.action.analyseCode(currentScope, loopingContext, actionInfo).unconditionalInits();
         if ((actionInfo.tagBits & loopingContext.initsOnContinue.tagBits & 1) != 0) {
            this.continueLabel = null;
         }

         if ((this.condition.implicitConversion & 1024) != 0) {
            initsOnCondition = flowInfo.unconditionalInits().addInitializationsFrom(actionInfo.mergedWith(loopingContext.initsOnContinue));
         }
      }

      this.condition.checkNPEbyUnboxing(currentScope, flowContext, initsOnCondition);
      actionInfo.setReachMode(previousMode);
      LoopingFlowContext condLoopContext;
      FlowInfo condInfo = this.condition.analyseCode(currentScope, condLoopContext = new LoopingFlowContext(flowContext, flowInfo, this, (BranchLabel)null, (BranchLabel)null, currentScope, true), (this.action == null ? actionInfo : actionInfo.mergedWith(loopingContext.initsOnContinue)).copy());
      this.preConditionInitStateIndex = currentScope.methodScope().recordInitializationStates(actionInfo.mergedWith(loopingContext.initsOnContinue));
      if (!isConditionOptimizedFalse && this.continueLabel != null) {
         loopingContext.complainOnDeferredFinalChecks(currentScope, condInfo);
         condLoopContext.complainOnDeferredFinalChecks(currentScope, condInfo);
         loopingContext.complainOnDeferredNullChecks(currentScope, flowInfo.unconditionalCopy().addPotentialNullInfoFrom(condInfo.initsWhenTrue().unconditionalInits()));
         condLoopContext.complainOnDeferredNullChecks(currentScope, actionInfo.addPotentialNullInfoFrom(condInfo.initsWhenTrue().unconditionalInits()));
      } else {
         loopingContext.complainOnDeferredNullChecks(currentScope, flowInfo.unconditionalCopy().addPotentialNullInfoFrom(condInfo.initsWhenTrue().unconditionalInits()), false);
         condLoopContext.complainOnDeferredNullChecks(currentScope, actionInfo.addPotentialNullInfoFrom(condInfo.initsWhenTrue().unconditionalInits()), false);
      }

      UnconditionalFlowInfo mergedInfo;
      if (loopingContext.hasEscapingExceptions()) {
         FlowInfo loopbackFlowInfo = flowInfo.copy();
         mergedInfo = loopbackFlowInfo.mergedWith(loopbackFlowInfo.unconditionalCopy().addNullInfoFrom(condInfo.initsWhenTrue()).unconditionalInits());
         loopingContext.simulateThrowAfterLoopBack(mergedInfo);
      }

      mergedInfo = FlowInfo.mergedOptimizedBranches((FlowInfo)((loopingContext.initsOnBreak.tagBits & 3) != 0 ? loopingContext.initsOnBreak : flowInfo.unconditionalCopy().addInitializationsFrom(loopingContext.initsOnBreak)), isConditionOptimizedTrue, (condInfo.tagBits & 3) == 0 ? flowInfo.copy().addInitializationsFrom(condInfo.initsWhenFalse()) : condInfo, false, !isConditionTrue);
      this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
      return mergedInfo;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         BranchLabel actionLabel = new BranchLabel(codeStream);
         if (this.action != null) {
            actionLabel.tagBits |= 2;
         }

         actionLabel.place();
         this.breakLabel.initialize(codeStream);
         boolean hasContinueLabel = this.continueLabel != null;
         if (hasContinueLabel) {
            this.continueLabel.initialize(codeStream);
         }

         if (this.action != null) {
            this.action.generateCode(currentScope, codeStream);
         }

         if (hasContinueLabel) {
            this.continueLabel.place();
            if (this.preConditionInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preConditionInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.preConditionInitStateIndex);
            }

            Constant cst = this.condition.optimizedBooleanConstant();
            boolean isConditionOptimizedFalse = cst != Constant.NotAConstant && !cst.booleanValue();
            if (isConditionOptimizedFalse) {
               this.condition.generateCode(currentScope, codeStream, false);
            } else {
               this.condition.generateOptimizedBoolean(currentScope, codeStream, actionLabel, (BranchLabel)null, true);
            }
         }

         if (this.mergedInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
         }

         if (this.breakLabel.forwardReferenceCount() > 0) {
            this.breakLabel.place();
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output).append("do");
      if (this.action == null) {
         output.append(" ;\n");
      } else {
         output.append('\n');
         this.action.printStatement(indent + 1, output).append('\n');
      }

      output.append("while (");
      return this.condition.printExpression(0, output).append(");");
   }

   public void resolve(BlockScope scope) {
      TypeBinding type = this.condition.resolveTypeExpecting(scope, TypeBinding.BOOLEAN);
      this.condition.computeConversion(scope, type, type);
      if (this.action != null) {
         this.action.resolve(scope);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.action != null) {
            this.action.traverse(visitor, scope);
         }

         this.condition.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }

   public boolean doesNotCompleteNormally() {
      Constant cst = this.condition.constant;
      boolean isConditionTrue = cst == null || cst != Constant.NotAConstant && cst.booleanValue();
      cst = this.condition.optimizedBooleanConstant();
      boolean isConditionOptimizedTrue = cst == null ? true : cst != Constant.NotAConstant && cst.booleanValue();
      if (!isConditionTrue && !isConditionOptimizedTrue) {
         if (this.action != null && !this.action.breaksOut((char[])null)) {
            return this.action.doesNotCompleteNormally() && !this.action.completesByContinue();
         } else {
            return false;
         }
      } else {
         return this.action == null || !this.action.breaksOut((char[])null);
      }
   }

   public boolean completesByContinue() {
      return this.action.continuesAtOuterLabel();
   }
}
