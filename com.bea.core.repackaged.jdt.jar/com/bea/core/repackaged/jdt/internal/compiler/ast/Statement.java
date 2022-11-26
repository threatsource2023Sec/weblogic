package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Substitution;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public abstract class Statement extends ASTNode {
   public static final int NOT_COMPLAINED = 0;
   public static final int COMPLAINED_FAKE_REACHABLE = 1;
   public static final int COMPLAINED_UNREACHABLE = 2;

   protected static boolean isKnowDeadCodePattern(Expression expression) {
      if (expression instanceof UnaryExpression) {
         expression = ((UnaryExpression)expression).expression;
      }

      return expression instanceof Reference;
   }

   public abstract FlowInfo analyseCode(BlockScope var1, FlowContext var2, FlowInfo var3);

   public boolean doesNotCompleteNormally() {
      return false;
   }

   public boolean completesByContinue() {
      return false;
   }

   protected void analyseArguments(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, MethodBinding methodBinding, Expression[] arguments) {
      if (arguments != null) {
         CompilerOptions compilerOptions = currentScope.compilerOptions();
         if (compilerOptions.sourceLevel >= 3342336L && methodBinding.isPolymorphic()) {
            return;
         }

         boolean considerTypeAnnotations = currentScope.environment().usesNullTypeAnnotations();
         boolean hasJDK15NullAnnotations = methodBinding.parameterNonNullness != null;
         int numParamsToCheck = methodBinding.parameters.length;
         int varArgPos = -1;
         TypeBinding varArgsType = null;
         boolean passThrough = false;
         TypeBinding expectedType;
         if ((considerTypeAnnotations || hasJDK15NullAnnotations) && methodBinding.isVarargs()) {
            varArgPos = numParamsToCheck - 1;
            varArgsType = methodBinding.parameters[varArgPos];
            if (numParamsToCheck == arguments.length) {
               expectedType = arguments[varArgPos].resolvedType;
               if (expectedType == TypeBinding.NULL || varArgsType.dimensions() == expectedType.dimensions() && expectedType.isCompatibleWith(varArgsType)) {
                  passThrough = true;
               }
            }

            if (!passThrough) {
               --numParamsToCheck;
            }
         }

         TypeBinding expectedType;
         int i;
         if (considerTypeAnnotations) {
            for(i = 0; i < numParamsToCheck; ++i) {
               expectedType = methodBinding.parameters[i];
               Boolean specialCaseNonNullness = hasJDK15NullAnnotations ? methodBinding.parameterNonNullness[i] : null;
               this.analyseOneArgument18(currentScope, flowContext, flowInfo, expectedType, arguments[i], specialCaseNonNullness, methodBinding.original().parameters[i]);
            }

            if (!passThrough && varArgsType instanceof ArrayBinding) {
               expectedType = ((ArrayBinding)varArgsType).elementsType();
               Boolean specialCaseNonNullness = hasJDK15NullAnnotations ? methodBinding.parameterNonNullness[varArgPos] : null;

               for(int i = numParamsToCheck; i < arguments.length; ++i) {
                  this.analyseOneArgument18(currentScope, flowContext, flowInfo, expectedType, arguments[i], specialCaseNonNullness, methodBinding.original().parameters[varArgPos]);
               }
            }
         } else if (hasJDK15NullAnnotations) {
            for(i = 0; i < numParamsToCheck; ++i) {
               if (methodBinding.parameterNonNullness[i] == Boolean.TRUE) {
                  expectedType = methodBinding.parameters[i];
                  Expression argument = arguments[i];
                  int nullStatus = argument.nullStatus(flowInfo, flowContext);
                  if (nullStatus != 4) {
                     flowContext.recordNullityMismatch(currentScope, argument, argument.resolvedType, expectedType, flowInfo, nullStatus, (NullAnnotationMatching)null);
                  }
               }
            }
         }
      }

   }

   void analyseOneArgument18(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, TypeBinding expectedType, Expression argument, Boolean expectedNonNullness, TypeBinding originalExpected) {
      if (argument instanceof ConditionalExpression && argument.isPolyExpression()) {
         ConditionalExpression ce = (ConditionalExpression)argument;
         ce.internalAnalyseOneArgument18(currentScope, flowContext, expectedType, ce.valueIfTrue, flowInfo, ce.ifTrueNullStatus, expectedNonNullness, originalExpected);
         ce.internalAnalyseOneArgument18(currentScope, flowContext, expectedType, ce.valueIfFalse, flowInfo, ce.ifFalseNullStatus, expectedNonNullness, originalExpected);
      } else if (argument instanceof SwitchExpression && argument.isPolyExpression()) {
         SwitchExpression se = (SwitchExpression)argument;

         for(int i = 0; i < se.resultExpressions.size(); ++i) {
            se.internalAnalyseOneArgument18(currentScope, flowContext, expectedType, (Expression)se.resultExpressions.get(i), flowInfo, (Integer)se.resultExpressionNullStatus.get(i), expectedNonNullness, originalExpected);
         }

      } else {
         int nullStatus = argument.nullStatus(flowInfo, flowContext);
         this.internalAnalyseOneArgument18(currentScope, flowContext, expectedType, argument, flowInfo, nullStatus, expectedNonNullness, originalExpected);
      }
   }

   void internalAnalyseOneArgument18(BlockScope currentScope, FlowContext flowContext, TypeBinding expectedType, Expression argument, FlowInfo flowInfo, int nullStatus, Boolean expectedNonNullness, TypeBinding originalExpected) {
      int statusFromAnnotatedNull = expectedNonNullness == Boolean.TRUE ? nullStatus : 0;
      NullAnnotationMatching annotationStatus = NullAnnotationMatching.analyse(expectedType, argument.resolvedType, nullStatus);
      if (!annotationStatus.isAnyMismatch() && statusFromAnnotatedNull != 0) {
         expectedType = originalExpected;
      }

      if (statusFromAnnotatedNull == 2) {
         currentScope.problemReporter().nullityMismatchingTypeAnnotation(argument, argument.resolvedType, expectedType, annotationStatus);
      } else if (annotationStatus.isAnyMismatch() || (statusFromAnnotatedNull & 16) != 0) {
         if (!expectedType.hasNullTypeAnnotations() && expectedNonNullness == Boolean.TRUE) {
            LookupEnvironment env = currentScope.environment();
            expectedType = env.createAnnotatedType(expectedType, new AnnotationBinding[]{env.getNonNullAnnotation()});
         }

         flowContext.recordNullityMismatch(currentScope, argument, argument.resolvedType, expectedType, flowInfo, nullStatus, annotationStatus);
      }

   }

   void checkAgainstNullAnnotation(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, Expression expr) {
      int nullStatus = expr.nullStatus(flowInfo, flowContext);
      MethodBinding methodBinding = null;
      boolean useTypeAnnotations = scope.environment().usesNullTypeAnnotations();

      long tagBits;
      try {
         methodBinding = scope.methodScope().referenceMethodBinding();
         tagBits = useTypeAnnotations ? methodBinding.returnType.tagBits : methodBinding.tagBits;
      } catch (NullPointerException var10) {
         return;
      }

      if (useTypeAnnotations) {
         this.checkAgainstNullTypeAnnotation(scope, methodBinding.returnType, expr, flowContext, flowInfo);
      } else if (nullStatus != 4 && (tagBits & 72057594037927936L) != 0L) {
         flowContext.recordNullityMismatch(scope, expr, expr.resolvedType, methodBinding.returnType, flowInfo, nullStatus, (NullAnnotationMatching)null);
      }

   }

   protected void checkAgainstNullTypeAnnotation(BlockScope scope, TypeBinding requiredType, Expression expression, FlowContext flowContext, FlowInfo flowInfo) {
      if (expression instanceof ConditionalExpression && expression.isPolyExpression()) {
         ConditionalExpression ce = (ConditionalExpression)expression;
         this.internalCheckAgainstNullTypeAnnotation(scope, requiredType, ce.valueIfTrue, ce.ifTrueNullStatus, flowContext, flowInfo);
         this.internalCheckAgainstNullTypeAnnotation(scope, requiredType, ce.valueIfFalse, ce.ifFalseNullStatus, flowContext, flowInfo);
      } else if (expression instanceof SwitchExpression && expression.isPolyExpression()) {
         SwitchExpression se = (SwitchExpression)expression;

         for(int i = 0; i < se.resultExpressions.size(); ++i) {
            this.internalCheckAgainstNullTypeAnnotation(scope, requiredType, (Expression)se.resultExpressions.get(i), (Integer)se.resultExpressionNullStatus.get(i), flowContext, flowInfo);
         }

      } else {
         int nullStatus = expression.nullStatus(flowInfo, flowContext);
         this.internalCheckAgainstNullTypeAnnotation(scope, requiredType, expression, nullStatus, flowContext, flowInfo);
      }
   }

   private void internalCheckAgainstNullTypeAnnotation(BlockScope scope, TypeBinding requiredType, Expression expression, int nullStatus, FlowContext flowContext, FlowInfo flowInfo) {
      NullAnnotationMatching annotationStatus = NullAnnotationMatching.analyse(requiredType, expression.resolvedType, (TypeBinding)null, (Substitution)null, nullStatus, expression, NullAnnotationMatching.CheckMode.COMPATIBLE);
      if (annotationStatus.isDefiniteMismatch()) {
         scope.problemReporter().nullityMismatchingTypeAnnotation(expression, expression.resolvedType, requiredType, annotationStatus);
      } else {
         if (annotationStatus.wantToReport()) {
            annotationStatus.report(scope);
         }

         if (annotationStatus.isUnchecked()) {
            flowContext.recordNullityMismatch(scope, expression, expression.resolvedType, requiredType, flowInfo, nullStatus, annotationStatus);
         }
      }

   }

   public void branchChainTo(BranchLabel label) {
   }

   public boolean breaksOut(final char[] label) {
      return (new ASTVisitor() {
         boolean breaksOut;

         public boolean visit(TypeDeclaration type, BlockScope skope) {
            return label != null;
         }

         public boolean visit(TypeDeclaration type, ClassScope skope) {
            return label != null;
         }

         public boolean visit(LambdaExpression lambda, BlockScope skope) {
            return label != null;
         }

         public boolean visit(WhileStatement whileStatement, BlockScope skope) {
            return label != null;
         }

         public boolean visit(DoStatement doStatement, BlockScope skope) {
            return label != null;
         }

         public boolean visit(ForeachStatement foreachStatement, BlockScope skope) {
            return label != null;
         }

         public boolean visit(ForStatement forStatement, BlockScope skope) {
            return label != null;
         }

         public boolean visit(SwitchStatement switchStatement, BlockScope skope) {
            return label != null;
         }

         public boolean visit(BreakStatement breakStatement, BlockScope skope) {
            if (label == null || CharOperation.equals(label, breakStatement.label)) {
               this.breaksOut = true;
            }

            return false;
         }

         public boolean breaksOut() {
            Statement.this.traverse(this, (BlockScope)null);
            return this.breaksOut;
         }
      }).breaksOut();
   }

   public boolean continuesAtOuterLabel() {
      return (new ASTVisitor() {
         boolean continuesToLabel;

         public boolean visit(ContinueStatement continueStatement, BlockScope skope) {
            if (continueStatement.label != null) {
               this.continuesToLabel = true;
            }

            return false;
         }

         public boolean continuesAtOuterLabel() {
            Statement.this.traverse(this, (BlockScope)null);
            return this.continuesToLabel;
         }
      }).continuesAtOuterLabel();
   }

   public int complainIfUnreachable(FlowInfo flowInfo, BlockScope scope, int previousComplaintLevel, boolean endOfBlock) {
      if ((flowInfo.reachMode() & 3) != 0) {
         if ((flowInfo.reachMode() & 1) != 0) {
            this.bits &= Integer.MAX_VALUE;
         }

         if (flowInfo == FlowInfo.DEAD_END) {
            if (previousComplaintLevel < 2) {
               scope.problemReporter().unreachableCode(this);
               if (endOfBlock) {
                  scope.checkUnclosedCloseables(flowInfo, (FlowContext)null, (ASTNode)null, (BlockScope)null);
               }
            }

            return 2;
         } else {
            if (previousComplaintLevel < 1) {
               scope.problemReporter().fakeReachable(this);
               if (endOfBlock) {
                  scope.checkUnclosedCloseables(flowInfo, (FlowContext)null, (ASTNode)null, (BlockScope)null);
               }
            }

            return 1;
         }
      } else {
         return previousComplaintLevel;
      }
   }

   public void generateArguments(MethodBinding binding, Expression[] arguments, BlockScope currentScope, CodeStream codeStream) {
      int paramLength;
      if (binding.isVarargs()) {
         TypeBinding[] params = binding.parameters;
         paramLength = params.length;
         int varArgIndex = paramLength - 1;

         for(int i = 0; i < varArgIndex; ++i) {
            arguments[i].generateCode(currentScope, codeStream, true);
         }

         ArrayBinding varArgsType = (ArrayBinding)params[varArgIndex];
         ArrayBinding codeGenVarArgsType = (ArrayBinding)binding.parameters[varArgIndex].erasure();
         int elementsTypeID = varArgsType.elementsType().id;
         int argLength = arguments == null ? 0 : arguments.length;
         if (argLength > paramLength) {
            codeStream.generateInlinedValue(argLength - varArgIndex);
            codeStream.newArray(codeGenVarArgsType);

            for(int i = varArgIndex; i < argLength; ++i) {
               codeStream.dup();
               codeStream.generateInlinedValue(i - varArgIndex);
               arguments[i].generateCode(currentScope, codeStream, true);
               codeStream.arrayAtPut(elementsTypeID, false);
            }
         } else if (argLength == paramLength) {
            TypeBinding lastType = arguments[varArgIndex].resolvedType;
            if (lastType != TypeBinding.NULL && (varArgsType.dimensions() != lastType.dimensions() || !lastType.isCompatibleWith(codeGenVarArgsType))) {
               codeStream.generateInlinedValue((int)1);
               codeStream.newArray(codeGenVarArgsType);
               codeStream.dup();
               codeStream.generateInlinedValue((int)0);
               arguments[varArgIndex].generateCode(currentScope, codeStream, true);
               codeStream.arrayAtPut(elementsTypeID, false);
            } else {
               arguments[varArgIndex].generateCode(currentScope, codeStream, true);
            }
         } else {
            codeStream.generateInlinedValue((int)0);
            codeStream.newArray(codeGenVarArgsType);
         }
      } else if (arguments != null) {
         int i = 0;

         for(paramLength = arguments.length; i < paramLength; ++i) {
            arguments[i].generateCode(currentScope, codeStream, true);
         }
      }

   }

   public abstract void generateCode(BlockScope var1, CodeStream var2);

   public boolean isBoxingCompatible(TypeBinding expressionType, TypeBinding targetType, Expression expression, Scope scope) {
      if (scope.isBoxingCompatibleWith(expressionType, targetType)) {
         return true;
      } else {
         return expressionType.isBaseType() && !targetType.isBaseType() && !targetType.isTypeVariable() && scope.compilerOptions().sourceLevel >= 3211264L && (targetType.id == 26 || targetType.id == 27 || targetType.id == 28) && expression.isConstantValueOfTypeAssignableToType(expressionType, scope.environment().computeBoxingType(targetType));
      }
   }

   public boolean isEmptyBlock() {
      return false;
   }

   public boolean isValidJavaStatement() {
      return true;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      return this.printStatement(indent, output);
   }

   public abstract StringBuffer printStatement(int var1, StringBuffer var2);

   public abstract void resolve(BlockScope var1);

   public Constant[] resolveCase(BlockScope scope, TypeBinding testType, SwitchStatement switchStatement) {
      this.resolve(scope);
      return new Constant[]{Constant.NotAConstant};
   }

   public TypeBinding resolveExpressionType(BlockScope scope) {
      return null;
   }

   public TypeBinding invocationTargetType() {
      return null;
   }

   public TypeBinding expectedType() {
      return this.invocationTargetType();
   }

   public ExpressionContext getExpressionContext() {
      return ExpressionContext.VANILLA_CONTEXT;
   }

   protected MethodBinding findConstructorBinding(BlockScope scope, Invocation site, ReferenceBinding receiverType, TypeBinding[] argumentTypes) {
      MethodBinding ctorBinding = scope.getConstructor(receiverType, argumentTypes, site);
      return resolvePolyExpressionArguments(site, ctorBinding, argumentTypes, scope);
   }
}
