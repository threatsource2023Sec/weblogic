package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class SynchronizedStatement extends SubRoutineStatement {
   public Expression expression;
   public Block block;
   public BlockScope scope;
   public LocalVariableBinding synchroVariable;
   static final char[] SecretLocalDeclarationName = " syncValue".toCharArray();
   int preSynchronizedInitStateIndex = -1;
   int mergedSynchronizedInitStateIndex = -1;

   public SynchronizedStatement(Expression expression, Block statement, int s, int e) {
      this.expression = expression;
      this.block = statement;
      this.sourceEnd = e;
      this.sourceStart = s;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      this.preSynchronizedInitStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
      this.synchroVariable.useFlag = 1;
      flowInfo = this.block.analyseCode(this.scope, new InsideSubRoutineFlowContext(flowContext, this), this.expression.analyseCode(this.scope, flowContext, flowInfo));
      this.mergedSynchronizedInitStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
      if ((flowInfo.tagBits & 1) != 0) {
         this.bits |= 536870912;
      }

      return flowInfo;
   }

   public boolean isSubRoutineEscaping() {
      return false;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         this.anyExceptionLabel = null;
         int pc = codeStream.position;
         this.expression.generateCode(this.scope, codeStream, true);
         if (this.block.isEmptyBlock()) {
            switch (this.synchroVariable.type.id) {
               case 7:
               case 8:
                  codeStream.dup2();
                  break;
               default:
                  codeStream.dup();
            }

            codeStream.monitorenter();
            codeStream.monitorexit();
            if (this.scope != currentScope) {
               codeStream.exitUserScope(this.scope);
            }
         } else {
            codeStream.store(this.synchroVariable, true);
            codeStream.addVariable(this.synchroVariable);
            codeStream.monitorenter();
            this.enterAnyExceptionHandler(codeStream);
            this.block.generateCode(this.scope, codeStream);
            if (this.scope != currentScope) {
               codeStream.exitUserScope(this.scope, this.synchroVariable);
            }

            BranchLabel endLabel = new BranchLabel(codeStream);
            if ((this.bits & 536870912) == 0) {
               codeStream.load(this.synchroVariable);
               codeStream.monitorexit();
               this.exitAnyExceptionHandler();
               codeStream.goto_(endLabel);
               this.enterAnyExceptionHandler(codeStream);
            }

            codeStream.pushExceptionOnStack(this.scope.getJavaLangThrowable());
            if (this.preSynchronizedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preSynchronizedInitStateIndex);
            }

            this.placeAllAnyExceptionHandler();
            codeStream.load(this.synchroVariable);
            codeStream.monitorexit();
            this.exitAnyExceptionHandler();
            codeStream.athrow();
            if (this.mergedSynchronizedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedSynchronizedInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedSynchronizedInitStateIndex);
            }

            if (this.scope != currentScope) {
               codeStream.removeVariable(this.synchroVariable);
            }

            if ((this.bits & 536870912) == 0) {
               endLabel.place();
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public boolean generateSubRoutineInvocation(BlockScope currentScope, CodeStream codeStream, Object targetLocation, int stateIndex, LocalVariableBinding secretLocal) {
      codeStream.load(this.synchroVariable);
      codeStream.monitorexit();
      this.exitAnyExceptionHandler();
      return false;
   }

   public void resolve(BlockScope upperScope) {
      this.scope = new BlockScope(upperScope);
      TypeBinding type = this.expression.resolveType(this.scope);
      if (type != null) {
         switch (type.id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
               this.scope.problemReporter().invalidTypeToSynchronize(this.expression, type);
               break;
            case 6:
               this.scope.problemReporter().illegalVoidExpression(this.expression);
            case 11:
            default:
               break;
            case 12:
               this.scope.problemReporter().invalidNullToSynchronize(this.expression);
         }

         this.synchroVariable = new LocalVariableBinding(SecretLocalDeclarationName, type, 0, false);
         this.scope.addLocalVariable(this.synchroVariable);
         this.synchroVariable.setConstant(Constant.NotAConstant);
         this.expression.computeConversion(this.scope, type, type);
      }

      this.block.resolveUsing(this.scope);
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output);
      output.append("synchronized (");
      this.expression.printExpression(0, output).append(')');
      output.append('\n');
      return this.block.printStatement(indent + 1, output);
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.expression.traverse(visitor, this.scope);
         this.block.traverse(visitor, this.scope);
      }

      visitor.endVisit(this, blockScope);
   }

   public boolean doesNotCompleteNormally() {
      return this.block.doesNotCompleteNormally();
   }

   public boolean completesByContinue() {
      return this.block.completesByContinue();
   }
}
