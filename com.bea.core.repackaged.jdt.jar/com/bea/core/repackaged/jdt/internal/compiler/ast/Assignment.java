package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.WildcardBinding;

public class Assignment extends Expression {
   public Expression lhs;
   public Expression expression;

   public Assignment(Expression lhs, Expression expression, int sourceEnd) {
      this.lhs = lhs;
      lhs.bits |= 8192;
      this.expression = expression;
      this.sourceStart = lhs.sourceStart;
      this.sourceEnd = sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      LocalVariableBinding local = this.lhs.localVariableBinding();
      this.expression.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
      FlowInfo preInitInfo = null;
      CompilerOptions compilerOptions = currentScope.compilerOptions();
      boolean shouldAnalyseResource = local != null && flowInfo.reachMode() == 0 && compilerOptions.analyseResourceLeaks && (FakedTrackingVariable.isAnyCloseable(this.expression.resolvedType) || this.expression.resolvedType == TypeBinding.NULL);
      if (shouldAnalyseResource) {
         preInitInfo = flowInfo.unconditionalCopy();
         FakedTrackingVariable.preConnectTrackerAcrossAssignment(this, local, this.expression, flowInfo);
      }

      FlowInfo flowInfo = ((Reference)this.lhs).analyseAssignment(currentScope, flowContext, flowInfo, this, false).unconditionalInits();
      if (shouldAnalyseResource) {
         FakedTrackingVariable.handleResourceAssignment(currentScope, preInitInfo, flowInfo, flowContext, this, this.expression, local);
      } else {
         FakedTrackingVariable.cleanUpAfterAssignment(currentScope, this.lhs.bits, this.expression);
      }

      int nullStatus = this.expression.nullStatus(flowInfo, flowContext);
      if (local != null && (local.type.tagBits & 2L) == 0L && nullStatus == 2) {
         flowContext.recordUsingNullReference(currentScope, local, this.lhs, 769, flowInfo);
      }

      if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
         VariableBinding var = this.lhs.nullAnnotatedVariableBinding(compilerOptions.sourceLevel >= 3407872L);
         if (var != null) {
            nullStatus = NullAnnotationMatching.checkAssignment(currentScope, flowContext, var, flowInfo, nullStatus, this.expression, this.expression.resolvedType);
            if (nullStatus == 4 && var instanceof FieldBinding && this.lhs instanceof Reference && compilerOptions.enableSyntacticNullAnalysisForFields) {
               int timeToLive = (this.bits & 1048576) != 0 ? 2 : 1;
               flowContext.recordNullCheckedFieldReference((Reference)this.lhs, timeToLive);
            }
         }
      }

      if (local != null && (local.type.tagBits & 2L) == 0L) {
         flowInfo.markNullStatus(local, nullStatus);
         flowContext.markFinallyNullStatus(local, nullStatus);
      }

      return flowInfo;
   }

   void checkAssignment(BlockScope scope, TypeBinding lhsType, TypeBinding rhsType) {
      FieldBinding leftField = this.getLastField(this.lhs);
      if (leftField != null && rhsType != TypeBinding.NULL && lhsType.kind() == 516 && ((WildcardBinding)lhsType).boundKind != 2) {
         scope.problemReporter().wildcardAssignment(lhsType, rhsType, this.expression);
      } else if (leftField != null && !leftField.isStatic() && leftField.declaringClass != null && leftField.declaringClass.isRawType()) {
         scope.problemReporter().unsafeRawFieldAssignment(leftField, rhsType, this.lhs);
      } else if (rhsType.needsUncheckedConversion(lhsType)) {
         scope.problemReporter().unsafeTypeConversion(this.expression, rhsType, lhsType);
      }

   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      ((Reference)this.lhs).generateAssignment(currentScope, codeStream, this, valueRequired);
      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   FieldBinding getLastField(Expression someExpression) {
      if (someExpression instanceof SingleNameReference) {
         if ((someExpression.bits & 7) == 1) {
            return (FieldBinding)((SingleNameReference)someExpression).binding;
         }
      } else {
         if (someExpression instanceof FieldReference) {
            return ((FieldReference)someExpression).binding;
         }

         if (someExpression instanceof QualifiedNameReference) {
            QualifiedNameReference qName = (QualifiedNameReference)someExpression;
            if (qName.otherBindings != null) {
               return qName.otherBindings[qName.otherBindings.length - 1];
            }

            if ((someExpression.bits & 7) == 1) {
               return (FieldBinding)qName.binding;
            }
         }
      }

      return null;
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return (this.implicitConversion & 512) != 0 ? 4 : this.expression.nullStatus(flowInfo, flowContext);
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      return this.printExpressionNoParenthesis(indent, output);
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      output.append('(');
      return this.printExpressionNoParenthesis(0, output).append(')');
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      this.lhs.printExpression(indent, output).append(" = ");
      return this.expression.printExpression(0, output);
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      return this.print(indent, output).append(';');
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      if (this.lhs instanceof Reference && !this.lhs.isThis()) {
         TypeBinding lhsType = this.lhs.resolveType(scope);
         this.expression.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
         this.expression.setExpectedType(lhsType);
         if (lhsType != null) {
            this.resolvedType = lhsType.capture(scope, this.lhs.sourceStart, this.lhs.sourceEnd);
         }

         LocalVariableBinding localVariableBinding = this.lhs.localVariableBinding();
         if (localVariableBinding != null && (localVariableBinding.isCatchParameter() || localVariableBinding.isParameter())) {
            localVariableBinding.tagBits &= -2049L;
         }

         TypeBinding rhsType = this.expression.resolveType(scope);
         if (lhsType != null && rhsType != null) {
            Binding left = getDirectBinding(this.lhs);
            if (left != null && !left.isVolatile() && left == getDirectBinding(this.expression)) {
               scope.problemReporter().assignmentHasNoEffect(this, left.shortReadableName());
            }

            if (TypeBinding.notEquals(lhsType, rhsType)) {
               scope.compilationUnitScope().recordTypeConversion(lhsType, rhsType);
            }

            if (!this.expression.isConstantValueOfTypeAssignableToType(rhsType, lhsType) && !rhsType.isCompatibleWith(lhsType, scope)) {
               if (this.isBoxingCompatible(rhsType, lhsType, this.expression, scope)) {
                  this.expression.computeConversion(scope, lhsType, rhsType);
                  if (this.expression instanceof CastExpression && (this.expression.bits & 16384) == 0) {
                     CastExpression.checkNeedForAssignedCast(scope, lhsType, (CastExpression)this.expression);
                  }

                  return this.resolvedType;
               } else {
                  scope.problemReporter().typeMismatchError(rhsType, (TypeBinding)lhsType, (ASTNode)this.expression, this.lhs);
                  return lhsType;
               }
            } else {
               this.expression.computeConversion(scope, lhsType, rhsType);
               this.checkAssignment(scope, lhsType, rhsType);
               if (this.expression instanceof CastExpression && (this.expression.bits & 16384) == 0) {
                  CastExpression.checkNeedForAssignedCast(scope, lhsType, (CastExpression)this.expression);
               }

               return this.resolvedType;
            }
         } else {
            return null;
         }
      } else {
         scope.problemReporter().expressionShouldBeAVariable(this.lhs);
         return null;
      }
   }

   public TypeBinding resolveTypeExpecting(BlockScope scope, TypeBinding expectedType) {
      TypeBinding type = super.resolveTypeExpecting(scope, expectedType);
      if (type == null) {
         return null;
      } else {
         TypeBinding lhsType = this.resolvedType;
         TypeBinding rhsType = this.expression.resolvedType;
         if (TypeBinding.equalsEquals(expectedType, TypeBinding.BOOLEAN) && TypeBinding.equalsEquals(lhsType, TypeBinding.BOOLEAN) && (this.lhs.bits & 8192) != 0) {
            scope.problemReporter().possibleAccidentalBooleanAssignment(this);
         }

         this.checkAssignment(scope, lhsType, rhsType);
         return type;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.lhs.traverse(visitor, scope);
         this.expression.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }

   public LocalVariableBinding localVariableBinding() {
      return this.lhs.localVariableBinding();
   }

   public boolean statementExpression() {
      return (this.bits & 534773760) == 0;
   }
}
