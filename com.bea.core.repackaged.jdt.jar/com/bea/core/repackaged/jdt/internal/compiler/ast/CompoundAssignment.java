package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class CompoundAssignment extends Assignment implements OperatorIds {
   public int operator;
   public int preAssignImplicitConversion;

   public CompoundAssignment(Expression lhs, Expression expression, int operator, int sourceEnd) {
      super(lhs, expression, sourceEnd);
      lhs.bits &= -8193;
      lhs.bits |= 65536;
      this.operator = operator;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.resolvedType.id != 11) {
         this.lhs.checkNPE(currentScope, flowContext, flowInfo);
         flowContext.recordAbruptExit();
      }

      this.expression.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
      FlowInfo flowInfo = ((Reference)this.lhs).analyseAssignment(currentScope, flowContext, flowInfo, this, true).unconditionalInits();
      if (this.resolvedType.id == 11) {
         LocalVariableBinding local = this.lhs.localVariableBinding();
         if (local != null) {
            flowInfo.markAsDefinitelyNonNull(local);
            flowContext.markFinallyNullStatus(local, 4);
         }
      }

      return flowInfo;
   }

   public boolean checkCastCompatibility() {
      return true;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      ((Reference)this.lhs).generateCompoundAssignment(currentScope, codeStream, this.expression, this.operator, this.preAssignImplicitConversion, valueRequired);
      if (valueRequired) {
         codeStream.generateImplicitConversion(this.implicitConversion);
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return 4;
   }

   public String operatorToString() {
      switch (this.operator) {
         case 2:
            return "&=";
         case 3:
            return "|=";
         case 4:
         case 5:
         case 6:
         case 7:
         case 11:
         case 12:
         case 18:
         default:
            return "unknown operator";
         case 8:
            return "^=";
         case 9:
            return "/=";
         case 10:
            return "<<=";
         case 13:
            return "-=";
         case 14:
            return "+=";
         case 15:
            return "*=";
         case 16:
            return "%=";
         case 17:
            return ">>=";
         case 19:
            return ">>>=";
      }
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      this.lhs.printExpression(indent, output).append(' ').append(this.operatorToString()).append(' ');
      return this.expression.printExpression(0, output);
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      if (this.lhs instanceof Reference && !this.lhs.isThis()) {
         boolean expressionIsCast = this.expression instanceof CastExpression;
         if (expressionIsCast) {
            Expression var10000 = this.expression;
            var10000.bits |= 32;
         }

         TypeBinding originalLhsType = this.lhs.resolveType(scope);
         TypeBinding originalExpressionType = this.expression.resolveType(scope);
         if (originalLhsType != null && originalExpressionType != null) {
            LookupEnvironment env = scope.environment();
            TypeBinding lhsType = originalLhsType;
            TypeBinding expressionType = originalExpressionType;
            boolean use15specifics = scope.compilerOptions().sourceLevel >= 3211264L;
            boolean unboxedLhs = false;
            if (use15specifics) {
               if (!originalLhsType.isBaseType() && originalExpressionType.id != 11 && originalExpressionType.id != 12) {
                  TypeBinding unboxedType = env.computeBoxingType(originalLhsType);
                  if (TypeBinding.notEquals(unboxedType, originalLhsType)) {
                     lhsType = unboxedType;
                     unboxedLhs = true;
                  }
               }

               if (!originalExpressionType.isBaseType() && lhsType.id != 11 && lhsType.id != 12) {
                  expressionType = env.computeBoxingType(originalExpressionType);
               }
            }

            if (this.restrainUsageToNumericTypes() && !lhsType.isNumericType()) {
               scope.problemReporter().operatorOnlyValidOnNumericType(this, lhsType, expressionType);
               return null;
            } else {
               int lhsID = lhsType.id;
               int expressionID = expressionType.id;
               if (lhsID > 15 || expressionID > 15) {
                  if (lhsID != 11) {
                     scope.problemReporter().invalidOperator(this, lhsType, expressionType);
                     return null;
                  }

                  expressionID = 1;
               }

               int result = OperatorExpression.OperatorSignatures[this.operator][(lhsID << 4) + expressionID];
               if (result == 0) {
                  scope.problemReporter().invalidOperator(this, lhsType, expressionType);
                  return null;
               } else {
                  if (this.operator == 14) {
                     if (lhsID == 1 && scope.compilerOptions().complianceLevel < 3342336L) {
                        scope.problemReporter().invalidOperator(this, lhsType, expressionType);
                        return null;
                     }

                     if ((lhsType.isNumericType() || lhsID == 5) && !expressionType.isNumericType()) {
                        scope.problemReporter().invalidOperator(this, lhsType, expressionType);
                        return null;
                     }
                  }

                  TypeBinding resultType = TypeBinding.wellKnownType(scope, result & 15);
                  if (this.checkCastCompatibility() && originalLhsType.id != 11 && resultType.id != 11 && !this.checkCastTypesCompatibility(scope, originalLhsType, resultType, (Expression)null)) {
                     scope.problemReporter().invalidOperator(this, originalLhsType, expressionType);
                     return null;
                  } else {
                     this.lhs.computeConversion(scope, TypeBinding.wellKnownType(scope, result >>> 16 & 15), originalLhsType);
                     this.expression.computeConversion(scope, TypeBinding.wellKnownType(scope, result >>> 8 & 15), originalExpressionType);
                     this.preAssignImplicitConversion = (unboxedLhs ? 512 : 0) | lhsID << 4 | result & 15;
                     if (unboxedLhs) {
                        scope.problemReporter().autoboxing(this, lhsType, originalLhsType);
                     }

                     if (expressionIsCast) {
                        CastExpression.checkNeedForArgumentCasts(scope, this.operator, result, this.lhs, originalLhsType.id, false, this.expression, originalExpressionType.id, true);
                     }

                     return this.resolvedType = originalLhsType;
                  }
               }
            }
         } else {
            return null;
         }
      } else {
         scope.problemReporter().expressionShouldBeAVariable(this.lhs);
         return null;
      }
   }

   public boolean restrainUsageToNumericTypes() {
      return false;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.lhs.traverse(visitor, scope);
         this.expression.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
