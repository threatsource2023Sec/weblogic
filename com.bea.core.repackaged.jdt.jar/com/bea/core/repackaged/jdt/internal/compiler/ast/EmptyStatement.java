package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;

public class EmptyStatement extends Statement {
   public EmptyStatement(int startPosition, int endPosition) {
      this.sourceStart = startPosition;
      this.sourceEnd = endPosition;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      return flowInfo;
   }

   public int complainIfUnreachable(FlowInfo flowInfo, BlockScope scope, int complaintLevel, boolean endOfBlock) {
      return scope.compilerOptions().complianceLevel < 3145728L ? complaintLevel : super.complainIfUnreachable(flowInfo, scope, complaintLevel, endOfBlock);
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      return printIndent(tab, output).append(';');
   }

   public void resolve(BlockScope scope) {
      if ((this.bits & 1) == 0) {
         scope.problemReporter().superfluousSemicolon(this.sourceStart, this.sourceEnd);
      } else {
         scope.problemReporter().emptyControlFlowStatement(this.sourceStart, this.sourceEnd);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}
