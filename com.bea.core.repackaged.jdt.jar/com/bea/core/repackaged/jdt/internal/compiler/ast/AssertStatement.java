package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class AssertStatement extends Statement {
   public Expression assertExpression;
   public Expression exceptionArgument;
   int preAssertInitStateIndex = -1;
   private FieldBinding assertionSyntheticFieldBinding;

   public AssertStatement(Expression exceptionArgument, Expression assertExpression, int startPosition) {
      this.assertExpression = assertExpression;
      this.exceptionArgument = exceptionArgument;
      this.sourceStart = startPosition;
      this.sourceEnd = exceptionArgument.sourceEnd;
   }

   public AssertStatement(Expression assertExpression, int startPosition) {
      this.assertExpression = assertExpression;
      this.sourceStart = startPosition;
      this.sourceEnd = assertExpression.sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      this.preAssertInitStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
      Constant cst = this.assertExpression.optimizedBooleanConstant();
      this.assertExpression.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
      boolean isOptimizedTrueAssertion = cst != Constant.NotAConstant && cst.booleanValue();
      boolean isOptimizedFalseAssertion = cst != Constant.NotAConstant && !cst.booleanValue();
      flowContext.tagBits |= 4096;
      FlowInfo conditionFlowInfo = this.assertExpression.analyseCode(currentScope, flowContext, flowInfo.copy());
      flowContext.extendTimeToLiveForNullCheckedField(1);
      flowContext.tagBits &= -4097;
      UnconditionalFlowInfo assertWhenTrueInfo = conditionFlowInfo.initsWhenTrue().unconditionalInits();
      FlowInfo assertInfo = conditionFlowInfo.initsWhenFalse();
      if (isOptimizedTrueAssertion) {
         assertInfo.setReachMode(1);
      }

      if (this.exceptionArgument != null) {
         FlowInfo exceptionInfo = this.exceptionArgument.analyseCode(currentScope, flowContext, assertInfo.copy());
         if (isOptimizedTrueAssertion) {
            currentScope.problemReporter().fakeReachable(this.exceptionArgument);
         } else {
            flowContext.checkExceptionHandlers((TypeBinding)currentScope.getJavaLangAssertionError(), this, exceptionInfo, currentScope);
         }
      }

      if (!isOptimizedTrueAssertion) {
         this.manageSyntheticAccessIfNecessary(currentScope, flowInfo);
      }

      flowContext.recordAbruptExit();
      if (isOptimizedFalseAssertion) {
         return flowInfo;
      } else {
         CompilerOptions compilerOptions = currentScope.compilerOptions();
         return !compilerOptions.includeNullInfoFromAsserts ? flowInfo.nullInfoLessUnconditionalCopy().mergedWith(assertInfo.nullInfoLessUnconditionalCopy()).addNullInfoFrom(flowInfo) : flowInfo.mergedWith(assertInfo.nullInfoLessUnconditionalCopy()).addInitializationsFrom(assertWhenTrueInfo.discardInitializationInfo());
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         if (this.assertionSyntheticFieldBinding != null) {
            BranchLabel assertionActivationLabel = new BranchLabel(codeStream);
            codeStream.fieldAccess((byte)-78, this.assertionSyntheticFieldBinding, (TypeBinding)null);
            codeStream.ifne(assertionActivationLabel);
            BranchLabel falseLabel;
            this.assertExpression.generateOptimizedBoolean(currentScope, codeStream, falseLabel = new BranchLabel(codeStream), (BranchLabel)null, true);
            codeStream.newJavaLangAssertionError();
            codeStream.dup();
            if (this.exceptionArgument != null) {
               this.exceptionArgument.generateCode(currentScope, codeStream, true);
               codeStream.invokeJavaLangAssertionErrorConstructor(this.exceptionArgument.implicitConversion & 15);
            } else {
               codeStream.invokeJavaLangAssertionErrorDefaultConstructor();
            }

            codeStream.athrow();
            if (this.preAssertInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preAssertInitStateIndex);
            }

            falseLabel.place();
            assertionActivationLabel.place();
         } else if (this.preAssertInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preAssertInitStateIndex);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void resolve(BlockScope scope) {
      this.assertExpression.resolveTypeExpecting(scope, TypeBinding.BOOLEAN);
      if (this.exceptionArgument != null) {
         TypeBinding exceptionArgumentType = this.exceptionArgument.resolveType(scope);
         if (exceptionArgumentType != null) {
            int id = exceptionArgumentType.id;
            switch (id) {
               case 6:
                  scope.problemReporter().illegalVoidExpression(this.exceptionArgument);
               default:
                  id = 1;
               case 2:
               case 3:
               case 4:
               case 5:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
                  this.exceptionArgument.implicitConversion = (id << 4) + id;
            }
         }
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.assertExpression.traverse(visitor, scope);
         if (this.exceptionArgument != null) {
            this.exceptionArgument.traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   public void manageSyntheticAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         SourceTypeBinding outerMostClass;
         ReferenceBinding enclosing;
         for(outerMostClass = currentScope.enclosingSourceType(); outerMostClass.isLocalType(); outerMostClass = (SourceTypeBinding)enclosing) {
            enclosing = outerMostClass.enclosingType();
            if (enclosing == null || enclosing.isInterface()) {
               break;
            }
         }

         this.assertionSyntheticFieldBinding = outerMostClass.addSyntheticFieldForAssert(currentScope);
         TypeDeclaration typeDeclaration = outerMostClass.scope.referenceType();
         AbstractMethodDeclaration[] methods = typeDeclaration.methods;
         int i = 0;

         for(int max = methods.length; i < max; ++i) {
            AbstractMethodDeclaration method = methods[i];
            if (method.isClinit()) {
               ((Clinit)method).setAssertionSupport(this.assertionSyntheticFieldBinding, currentScope.compilerOptions().sourceLevel < 3211264L);
               break;
            }
         }
      }

   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      printIndent(tab, output);
      output.append("assert ");
      this.assertExpression.printExpression(0, output);
      if (this.exceptionArgument != null) {
         output.append(": ");
         this.exceptionArgument.printExpression(0, output);
      }

      return output.append(';');
   }
}
