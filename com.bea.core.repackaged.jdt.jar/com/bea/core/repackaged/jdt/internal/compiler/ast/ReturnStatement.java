package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InitializationFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InsideSubRoutineFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class ReturnStatement extends Statement {
   public Expression expression;
   public SubRoutineStatement[] subroutines;
   public LocalVariableBinding saveValueVariable;
   public int initStateIndex;
   private boolean implicitReturn;

   public ReturnStatement(Expression expression, int sourceStart, int sourceEnd) {
      this(expression, sourceStart, sourceEnd, false);
   }

   public ReturnStatement(Expression expression, int sourceStart, int sourceEnd, boolean implicitReturn) {
      this.initStateIndex = -1;
      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
      this.expression = expression;
      this.implicitReturn = implicitReturn;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (!(this.expression instanceof FunctionalExpression) || this.expression.resolvedType != null && this.expression.resolvedType.isValidBinding()) {
         MethodScope methodScope = currentScope.methodScope();
         if (this.expression != null) {
            flowInfo = this.expression.analyseCode(currentScope, flowContext, flowInfo);
            this.expression.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
            if (flowInfo.reachMode() == 0 && currentScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
               this.checkAgainstNullAnnotation(currentScope, flowContext, flowInfo, this.expression);
            }

            if (currentScope.compilerOptions().analyseResourceLeaks) {
               FakedTrackingVariable trackingVariable = FakedTrackingVariable.getCloseTrackingVariable(this.expression, flowInfo, flowContext);
               if (trackingVariable != null) {
                  if (methodScope != trackingVariable.methodScope) {
                     trackingVariable.markClosedInNestedMethod();
                  }

                  flowInfo = FakedTrackingVariable.markPassedToOutside(currentScope, this.expression, flowInfo, flowContext, true);
               }
            }
         }

         this.initStateIndex = methodScope.recordInitializationStates(flowInfo);
         FlowContext traversedContext = flowContext;
         int subCount = 0;
         boolean saveValueNeeded = false;
         boolean hasValueToSave = this.needValueStore();
         boolean noAutoCloseables = true;

         do {
            SubRoutineStatement sub;
            if ((sub = traversedContext.subroutine()) != null) {
               if (this.subroutines == null) {
                  this.subroutines = new SubRoutineStatement[5];
               }

               if (subCount == this.subroutines.length) {
                  System.arraycopy(this.subroutines, 0, this.subroutines = new SubRoutineStatement[subCount * 2], 0, subCount);
               }

               this.subroutines[subCount++] = sub;
               if (sub.isSubRoutineEscaping()) {
                  saveValueNeeded = false;
                  this.bits |= 536870912;
                  break;
               }

               if (sub instanceof TryStatement && ((TryStatement)sub).resources.length > 0) {
                  noAutoCloseables = false;
               }
            }

            traversedContext.recordReturnFrom(flowInfo.unconditionalInits());
            if (traversedContext instanceof InsideSubRoutineFlowContext) {
               ASTNode node = traversedContext.associatedNode;
               if (node instanceof SynchronizedStatement) {
                  this.bits |= 1073741824;
               } else if (node instanceof TryStatement) {
                  TryStatement tryStatement = (TryStatement)node;
                  flowInfo.addInitializationsFrom(tryStatement.subRoutineInits);
                  if (hasValueToSave) {
                     if (this.saveValueVariable == null) {
                        this.prepareSaveValueLocation(tryStatement);
                     }

                     saveValueNeeded = true;
                     this.initStateIndex = methodScope.recordInitializationStates(flowInfo);
                  }
               }
            } else if (traversedContext instanceof InitializationFlowContext) {
               currentScope.problemReporter().cannotReturnInInitializer(this);
               return FlowInfo.DEAD_END;
            }
         } while((traversedContext = traversedContext.getLocalParent()) != null);

         if (this.subroutines != null && subCount != this.subroutines.length) {
            System.arraycopy(this.subroutines, 0, this.subroutines = new SubRoutineStatement[subCount], 0, subCount);
         }

         if (saveValueNeeded) {
            if (this.saveValueVariable != null) {
               this.saveValueVariable.useFlag = 1;
            }
         } else {
            this.saveValueVariable = null;
            if ((this.bits & 1073741824) == 0 && this.expression != null && TypeBinding.equalsEquals(this.expression.resolvedType, TypeBinding.BOOLEAN) && noAutoCloseables) {
               Expression var10000 = this.expression;
               var10000.bits |= 16;
            }
         }

         currentScope.checkUnclosedCloseables(flowInfo, flowContext, this, currentScope);
         flowContext.recordAbruptExit();
         flowContext.expireNullCheckedFieldInfo();
         return FlowInfo.DEAD_END;
      } else {
         flowContext.recordAbruptExit();
         return FlowInfo.DEAD_END;
      }
   }

   public boolean doesNotCompleteNormally() {
      return true;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         boolean alreadyGeneratedExpression = false;
         if (this.needValueStore()) {
            alreadyGeneratedExpression = true;
            this.expression.generateCode(currentScope, codeStream, this.needValue());
            this.generateStoreSaveValueIfNecessary(codeStream);
         }

         if (this.subroutines != null) {
            Object reusableJSRTarget = this.expression == null ? TypeBinding.VOID : this.expression.reusableJSRTarget();
            int i = 0;

            for(int max = this.subroutines.length; i < max; ++i) {
               SubRoutineStatement sub = this.subroutines[i];
               boolean didEscape = sub.generateSubRoutineInvocation(currentScope, codeStream, reusableJSRTarget, this.initStateIndex, this.saveValueVariable);
               if (didEscape) {
                  codeStream.recordPositionsFrom(pc, this.sourceStart);
                  SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, i, codeStream);
                  return;
               }
            }
         }

         if (this.saveValueVariable != null) {
            codeStream.load(this.saveValueVariable);
         }

         if (this.expression != null && !alreadyGeneratedExpression) {
            this.expression.generateCode(currentScope, codeStream, true);
            this.generateStoreSaveValueIfNecessary(codeStream);
         }

         this.generateReturnBytecode(codeStream);
         if (this.saveValueVariable != null) {
            codeStream.removeVariable(this.saveValueVariable);
         }

         if (this.initStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.initStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.initStateIndex);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
         SubRoutineStatement.reenterAllExceptionHandlers(this.subroutines, -1, codeStream);
      }
   }

   public void generateReturnBytecode(CodeStream codeStream) {
      codeStream.generateReturnBytecode(this.expression);
   }

   public void generateStoreSaveValueIfNecessary(CodeStream codeStream) {
      if (this.saveValueVariable != null) {
         codeStream.store(this.saveValueVariable, false);
         codeStream.addVariable(this.saveValueVariable);
      }

   }

   private boolean needValueStore() {
      return this.expression != null && (this.expression.constant == Constant.NotAConstant || (this.expression.implicitConversion & 512) != 0) && !(this.expression instanceof NullLiteral);
   }

   public boolean needValue() {
      return this.saveValueVariable != null || (this.bits & 1073741824) != 0 || (this.bits & 536870912) == 0;
   }

   public void prepareSaveValueLocation(TryStatement targetTryStatement) {
      this.saveValueVariable = targetTryStatement.secretReturnValue;
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      printIndent(tab, output).append("return ");
      if (this.expression != null) {
         this.expression.printExpression(0, output);
      }

      return output.append(';');
   }

   public void resolve(BlockScope scope) {
      MethodScope methodScope = scope.methodScope();
      LambdaExpression lambda = methodScope.referenceContext instanceof LambdaExpression ? (LambdaExpression)methodScope.referenceContext : null;
      MethodBinding methodBinding;
      TypeBinding methodType = lambda != null ? lambda.expectedResultType() : (methodScope.referenceContext instanceof AbstractMethodDeclaration ? ((methodBinding = ((AbstractMethodDeclaration)methodScope.referenceContext).binding) == null ? null : methodBinding.returnType) : TypeBinding.VOID);
      if (this.expression != null) {
         this.expression.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
         this.expression.setExpectedType((TypeBinding)methodType);
         if (lambda != null && lambda.argumentsTypeElided() && this.expression instanceof CastExpression) {
            Expression var10000 = this.expression;
            var10000.bits |= 32;
         }
      }

      TypeBinding expressionType;
      if (methodType != TypeBinding.VOID) {
         if (this.expression == null) {
            if (lambda != null) {
               lambda.returnsExpression((Expression)null, (TypeBinding)methodType);
            }

            if (methodType != null) {
               scope.problemReporter().shouldReturn((TypeBinding)methodType, this);
            }

         } else {
            expressionType = this.expression.resolveType(scope);
            if (lambda != null) {
               lambda.returnsExpression(this.expression, expressionType);
            }

            if (expressionType != null) {
               if (expressionType == TypeBinding.VOID) {
                  scope.problemReporter().attemptToReturnVoidValue(this);
               } else if (methodType != null) {
                  if (((TypeBinding)methodType).isProperType(true) && lambda != null && lambda.updateLocalTypesInMethod(lambda.descriptor)) {
                     methodType = lambda.expectedResultType();
                  }

                  if (TypeBinding.notEquals((TypeBinding)methodType, expressionType)) {
                     scope.compilationUnitScope().recordTypeConversion((TypeBinding)methodType, expressionType);
                  }

                  if (!this.expression.isConstantValueOfTypeAssignableToType(expressionType, (TypeBinding)methodType) && !expressionType.isCompatibleWith((TypeBinding)methodType, scope)) {
                     if (this.isBoxingCompatible(expressionType, (TypeBinding)methodType, this.expression, scope)) {
                        this.expression.computeConversion(scope, (TypeBinding)methodType, expressionType);
                        if (this.expression instanceof CastExpression && (this.expression.bits & 16416) == 0) {
                           CastExpression.checkNeedForAssignedCast(scope, (TypeBinding)methodType, (CastExpression)this.expression);
                        }

                     } else {
                        if ((((TypeBinding)methodType).tagBits & 128L) == 0L) {
                           scope.problemReporter().typeMismatchError(expressionType, (TypeBinding)methodType, (ASTNode)this.expression, this);
                        }

                     }
                  } else {
                     this.expression.computeConversion(scope, (TypeBinding)methodType, expressionType);
                     if (expressionType.needsUncheckedConversion((TypeBinding)methodType)) {
                        scope.problemReporter().unsafeTypeConversion(this.expression, expressionType, (TypeBinding)methodType);
                     }

                     if (this.expression instanceof CastExpression) {
                        if ((this.expression.bits & 16416) == 0) {
                           CastExpression.checkNeedForAssignedCast(scope, (TypeBinding)methodType, (CastExpression)this.expression);
                        } else if (lambda != null && lambda.argumentsTypeElided() && (this.expression.bits & 16384) != 0 && TypeBinding.equalsEquals(((CastExpression)this.expression).expression.resolvedType, (TypeBinding)methodType)) {
                           scope.problemReporter().unnecessaryCast((CastExpression)this.expression);
                        }
                     }

                  }
               }
            }
         }
      } else if (this.expression == null) {
         if (lambda != null) {
            lambda.returnsExpression((Expression)null, TypeBinding.VOID);
         }

      } else {
         expressionType = this.expression.resolveType(scope);
         if (lambda != null) {
            lambda.returnsExpression(this.expression, expressionType);
         }

         if (!this.implicitReturn || expressionType != TypeBinding.VOID && !this.expression.statementExpression()) {
            if (expressionType != null) {
               scope.problemReporter().attemptToReturnNonVoidExpression(this, expressionType);
            }

         }
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope) && this.expression != null) {
         this.expression.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
