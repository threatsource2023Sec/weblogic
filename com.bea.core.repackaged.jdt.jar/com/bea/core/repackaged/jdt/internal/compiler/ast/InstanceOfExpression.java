package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class InstanceOfExpression extends OperatorExpression {
   public Expression expression;
   public TypeReference type;

   public InstanceOfExpression(Expression expression, TypeReference type) {
      this.expression = expression;
      this.type = type;
      type.bits |= 1073741824;
      this.bits |= 1984;
      this.sourceStart = expression.sourceStart;
      this.sourceEnd = type.sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      LocalVariableBinding local = this.expression.localVariableBinding();
      if (local != null && (local.type.tagBits & 2L) == 0L) {
         FlowInfo flowInfo = this.expression.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
         FlowInfo initsWhenTrue = flowInfo.copy();
         initsWhenTrue.markAsComparedEqualToNonNull(local);
         flowContext.recordUsingNullReference(currentScope, local, this.expression, 1025, flowInfo);
         return FlowInfo.conditional(initsWhenTrue, flowInfo.copy());
      } else {
         if (this.expression instanceof Reference && currentScope.compilerOptions().enableSyntacticNullAnalysisForFields) {
            FieldBinding field = ((Reference)this.expression).lastFieldBinding();
            if (field != null && (field.type.tagBits & 2L) == 0L) {
               flowContext.recordNullCheckedFieldReference((Reference)this.expression, 1);
            }
         }

         return this.expression.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      this.expression.generateCode(currentScope, codeStream, true);
      codeStream.instance_of(this.type, this.type.resolvedType);
      if (valueRequired) {
         codeStream.generateImplicitConversion(this.implicitConversion);
      } else {
         codeStream.pop();
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      this.expression.printExpression(indent, output).append(" instanceof ");
      return this.type.print(0, output);
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      TypeBinding expressionType = this.expression.resolveType(scope);
      TypeBinding checkedType = this.type.resolveType(scope, true);
      if (expressionType != null && checkedType != null && this.type.hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY) && (!expressionType.isCompatibleWith(checkedType) || NullAnnotationMatching.analyse(checkedType, expressionType, -1).isAnyMismatch())) {
         scope.problemReporter().nullAnnotationUnsupportedLocation(this.type);
      }

      if (expressionType != null && checkedType != null) {
         if (!checkedType.isReifiable()) {
            scope.problemReporter().illegalInstanceOfGenericType(checkedType, this);
         } else if (checkedType.isValidBinding() && (expressionType != TypeBinding.NULL && expressionType.isBaseType() || !this.checkCastTypesCompatibility(scope, checkedType, expressionType, (Expression)null))) {
            scope.problemReporter().notCompatibleTypesError(this, expressionType, checkedType);
         }

         return this.resolvedType = TypeBinding.BOOLEAN;
      } else {
         return null;
      }
   }

   public void tagAsUnnecessaryCast(Scope scope, TypeBinding castType) {
      if (this.expression.resolvedType != TypeBinding.NULL) {
         scope.problemReporter().unnecessaryInstanceof(this, castType);
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.expression.traverse(visitor, scope);
         this.type.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
