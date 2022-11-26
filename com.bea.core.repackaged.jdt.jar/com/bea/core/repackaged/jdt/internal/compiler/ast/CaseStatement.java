package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IntConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.ArrayList;
import java.util.List;

public class CaseStatement extends Statement {
   public Expression constantExpression;
   public BranchLabel targetLabel;
   public Expression[] constantExpressions;
   public BranchLabel[] targetLabels;
   public boolean isExpr = false;

   public CaseStatement(Expression constantExpression, int sourceEnd, int sourceStart) {
      this.constantExpression = constantExpression;
      this.sourceEnd = sourceEnd;
      this.sourceStart = sourceStart;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.constantExpressions != null && this.constantExpressions.length > 1) {
         Expression[] var7;
         int var6 = (var7 = this.constantExpressions).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            Expression e = var7[var5];
            this.analyseConstantExpression(currentScope, flowContext, flowInfo, e);
         }
      } else if (this.constantExpression != null) {
         this.analyseConstantExpression(currentScope, flowContext, flowInfo, this.constantExpression);
      }

      return flowInfo;
   }

   private void analyseConstantExpression(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, Expression e) {
      if (e.constant == Constant.NotAConstant && !e.resolvedType.isEnum()) {
         currentScope.problemReporter().caseExpressionMustBeConstant(e);
      }

      e.analyseCode(currentScope, flowContext, flowInfo);
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      printIndent(tab, output);
      if (this.constantExpression == null) {
         output.append("default ");
         output.append(this.isExpr ? "->" : ":");
      } else {
         output.append("case ");
         if (this.constantExpressions != null && this.constantExpressions.length > 0) {
            int i = 0;

            for(int l = this.constantExpressions.length; i < l; ++i) {
               this.constantExpressions[i].printExpression(0, output);
               if (i < l - 1) {
                  output.append(',');
               }
            }
         } else {
            this.constantExpression.printExpression(0, output);
         }

         output.append(this.isExpr ? " ->" : " :");
      }

      return output;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         int pc = codeStream.position;
         if (this.targetLabels != null) {
            int i = 0;

            for(int l = this.targetLabels.length; i < l; ++i) {
               this.targetLabels[i].place();
            }
         } else {
            this.targetLabel.place();
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void resolve(BlockScope scope) {
   }

   public Constant[] resolveCase(BlockScope scope, TypeBinding switchExpressionType, SwitchStatement switchStatement) {
      scope.enclosingCase = this;
      if (this.constantExpression == null) {
         if (switchStatement.defaultCase != null) {
            scope.problemReporter().duplicateDefaultCase(this);
         }

         switchStatement.defaultCase = this;
         return Constant.NotAConstantList;
      } else {
         switchStatement.cases[switchStatement.caseCount++] = this;
         if (switchExpressionType != null && switchExpressionType.isEnum() && this.constantExpression instanceof SingleNameReference) {
            ((SingleNameReference)this.constantExpression).setActualReceiverType((ReferenceBinding)switchExpressionType);
         }

         TypeBinding caseType = this.constantExpression.resolveType(scope);
         if (caseType != null && switchExpressionType != null) {
            if (this.constantExpressions != null && this.constantExpressions.length > 1) {
               List cases = new ArrayList();
               Expression[] var9;
               int var8 = (var9 = this.constantExpressions).length;

               for(int var7 = 0; var7 < var8; ++var7) {
                  Expression e = var9[var7];
                  if (e != this.constantExpression) {
                     if (switchExpressionType.isEnum() && e instanceof SingleNameReference) {
                        ((SingleNameReference)e).setActualReceiverType((ReferenceBinding)switchExpressionType);
                     }

                     e.resolveType(scope);
                  }

                  Constant con = this.resolveConstantExpression(scope, caseType, switchExpressionType, switchStatement, e);
                  if (con != Constant.NotAConstant) {
                     cases.add(con);
                  }
               }

               if (cases.size() > 0) {
                  return (Constant[])cases.toArray(new Constant[cases.size()]);
               } else {
                  return Constant.NotAConstantList;
               }
            } else {
               return new Constant[]{this.resolveConstantExpression(scope, caseType, switchExpressionType, switchStatement, this.constantExpression)};
            }
         } else {
            return Constant.NotAConstantList;
         }
      }
   }

   public Constant resolveConstantExpression(BlockScope scope, TypeBinding caseType, TypeBinding switchExpressionType, SwitchStatement switchStatement, Expression expression) {
      if (!expression.isConstantValueOfTypeAssignableToType(caseType, switchExpressionType) && !caseType.isCompatibleWith(switchExpressionType)) {
         if (this.isBoxingCompatible(caseType, switchExpressionType, expression, scope)) {
            return expression.constant;
         }
      } else {
         if (!caseType.isEnum()) {
            return expression.constant;
         }

         if ((expression.bits & 534773760) >> 21 != 0) {
            scope.problemReporter().enumConstantsCannotBeSurroundedByParenthesis(expression);
         }

         if (expression instanceof NameReference && (expression.bits & 7) == 1) {
            NameReference reference = (NameReference)expression;
            FieldBinding field = reference.fieldBinding();
            if ((field.modifiers & 16384) == 0) {
               scope.problemReporter().enumSwitchCannotTargetField(reference, field);
            } else if (reference instanceof QualifiedNameReference) {
               scope.problemReporter().cannotUseQualifiedEnumConstantInCaseLabel(reference, field);
            }

            return IntConstant.fromValue(field.original().id + 1);
         }
      }

      scope.problemReporter().typeMismatchError(caseType, (TypeBinding)switchExpressionType, (ASTNode)this.constantExpression, switchStatement.expression);
      return Constant.NotAConstant;
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         if (this.constantExpressions != null && this.constantExpressions.length > 1) {
            Expression[] var6;
            int var5 = (var6 = this.constantExpressions).length;

            for(int var4 = 0; var4 < var5; ++var4) {
               Expression e = var6[var4];
               e.traverse(visitor, blockScope);
            }
         } else if (this.constantExpression != null) {
            this.constantExpression.traverse(visitor, blockScope);
         }
      }

      visitor.endVisit(this, blockScope);
   }
}
