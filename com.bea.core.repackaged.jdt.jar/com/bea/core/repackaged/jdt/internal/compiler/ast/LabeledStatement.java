package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.LabelFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;

public class LabeledStatement extends Statement {
   public Statement statement;
   public char[] label;
   public BranchLabel targetLabel;
   public int labelEnd;
   int mergedInitStateIndex = -1;

   public LabeledStatement(char[] label, Statement statement, long labelPosition, int sourceEnd) {
      this.statement = statement;
      if (statement instanceof EmptyStatement) {
         statement.bits |= 1;
      }

      this.label = label;
      this.sourceStart = (int)(labelPosition >>> 32);
      this.labelEnd = (int)labelPosition;
      this.sourceEnd = sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.statement == null) {
         return flowInfo;
      } else {
         LabelFlowContext labelContext;
         FlowInfo statementInfo = this.statement.analyseCode(currentScope, labelContext = new LabelFlowContext(flowContext, this, this.label, this.targetLabel = new BranchLabel(), currentScope), flowInfo);
         boolean reinjectNullInfo = (statementInfo.tagBits & 3) != 0 && (labelContext.initsOnBreak.tagBits & 3) == 0;
         FlowInfo mergedInfo = statementInfo.mergedWith(labelContext.initsOnBreak);
         if (reinjectNullInfo) {
            ((UnconditionalFlowInfo)mergedInfo).addNullInfoFrom(flowInfo.unconditionalFieldLessCopy()).addNullInfoFrom(labelContext.initsOnBreak.unconditionalFieldLessCopy());
         }

         this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
         if ((this.bits & 64) == 0) {
            currentScope.problemReporter().unusedLabel(this);
         }

         return mergedInfo;
      }
   }

   public ASTNode concreteStatement() {
      return this.statement;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         if (this.targetLabel != null) {
            this.targetLabel.initialize(codeStream);
            if (this.statement != null) {
               this.statement.generateCode(currentScope, codeStream);
            }

            this.targetLabel.place();
         }

         if (this.mergedInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      printIndent(tab, output).append(this.label).append(": ");
      if (this.statement == null) {
         output.append(';');
      } else {
         this.statement.printStatement(0, output);
      }

      return output;
   }

   public void resolve(BlockScope scope) {
      if (this.statement != null) {
         this.statement.resolve(scope);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope) && this.statement != null) {
         this.statement.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }

   public boolean doesNotCompleteNormally() {
      return this.statement.breaksOut(this.label) ? false : this.statement.doesNotCompleteNormally();
   }

   public boolean completesByContinue() {
      return this.statement instanceof ContinueStatement;
   }
}
