package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;

public class Block extends Statement {
   public Statement[] statements;
   public int explicitDeclarations;
   public BlockScope scope;

   public Block(int explicitDeclarations) {
      this.explicitDeclarations = explicitDeclarations;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.statements == null) {
         return flowInfo;
      } else {
         int complaintLevel = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
         boolean enableSyntacticNullAnalysisForFields = currentScope.compilerOptions().enableSyntacticNullAnalysisForFields;
         int i = 0;

         int numLocals;
         for(numLocals = this.statements.length; i < numLocals; ++i) {
            Statement stat = this.statements[i];
            if ((complaintLevel = stat.complainIfUnreachable(flowInfo, this.scope, complaintLevel, true)) < 2) {
               flowInfo = stat.analyseCode(this.scope, flowContext, flowInfo);
            }

            flowContext.mergeFinallyNullInfo(flowInfo);
            if (enableSyntacticNullAnalysisForFields) {
               flowContext.expireNullCheckedFieldInfo();
            }
         }

         if (this.scope != currentScope) {
            this.scope.checkUnclosedCloseables(flowInfo, flowContext, (ASTNode)null, (BlockScope)null);
         }

         if (this.explicitDeclarations > 0) {
            LocalVariableBinding[] locals = this.scope.locals;
            if (locals != null) {
               numLocals = this.scope.localIndex;

               for(int i = 0; i < numLocals; ++i) {
                  flowInfo.resetAssignmentInfo(locals[i]);
               }
            }
         }

         return flowInfo;
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         if (this.statements != null) {
            int i = 0;

            for(int max = this.statements.length; i < max; ++i) {
               this.statements[i].generateCode(this.scope, codeStream);
            }
         }

         if (this.scope != currentScope) {
            codeStream.exitUserScope(this.scope);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public boolean isEmptyBlock() {
      return this.statements == null;
   }

   public StringBuffer printBody(int indent, StringBuffer output) {
      if (this.statements == null) {
         return output;
      } else {
         for(int i = 0; i < this.statements.length; ++i) {
            this.statements[i].printStatement(indent + 1, output);
            output.append('\n');
         }

         return output;
      }
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output);
      output.append("{\n");
      this.printBody(indent, output);
      return printIndent(indent, output).append('}');
   }

   public void resolve(BlockScope upperScope) {
      if ((this.bits & 8) != 0) {
         upperScope.problemReporter().undocumentedEmptyBlock(this.sourceStart, this.sourceEnd);
      }

      if (this.statements != null) {
         this.scope = this.explicitDeclarations == 0 ? upperScope : new BlockScope(upperScope, this.explicitDeclarations);
         int i = 0;

         for(int length = this.statements.length; i < length; ++i) {
            this.statements[i].resolve(this.scope);
         }
      }

   }

   public void resolveUsing(BlockScope givenScope) {
      if ((this.bits & 8) != 0) {
         givenScope.problemReporter().undocumentedEmptyBlock(this.sourceStart, this.sourceEnd);
      }

      this.scope = givenScope;
      if (this.statements != null) {
         int i = 0;

         for(int length = this.statements.length; i < length; ++i) {
            this.statements[i].resolve(this.scope);
         }
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope) && this.statements != null) {
         int i = 0;

         for(int length = this.statements.length; i < length; ++i) {
            this.statements[i].traverse(visitor, this.scope);
         }
      }

      visitor.endVisit(this, blockScope);
   }

   public void branchChainTo(BranchLabel label) {
      if (this.statements != null) {
         this.statements[this.statements.length - 1].branchChainTo(label);
      }

   }

   public boolean doesNotCompleteNormally() {
      int length = this.statements == null ? 0 : this.statements.length;
      return length > 0 && this.statements[length - 1].doesNotCompleteNormally();
   }

   public boolean completesByContinue() {
      int length = this.statements == null ? 0 : this.statements.length;
      return length > 0 && this.statements[length - 1].completesByContinue();
   }
}
