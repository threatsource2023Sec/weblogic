package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.BooleanConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class UnaryExpression extends OperatorExpression {
   public Expression expression;
   public Constant optimizedBooleanConstant;

   public UnaryExpression(Expression expression, int operator) {
      this.expression = expression;
      this.bits |= operator << 6;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if ((this.bits & 4032) >> 6 == 11) {
         flowContext.tagBits ^= 4;
         flowInfo = this.expression.analyseCode(currentScope, flowContext, flowInfo).asNegatedCondition();
         flowContext.tagBits ^= 4;
      } else {
         flowInfo = this.expression.analyseCode(currentScope, flowContext, flowInfo);
      }

      this.expression.checkNPE(currentScope, flowContext, flowInfo);
      return flowInfo;
   }

   public Constant optimizedBooleanConstant() {
      return this.optimizedBooleanConstant == null ? this.constant : this.optimizedBooleanConstant;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (this.constant != Constant.NotAConstant) {
         if (valueRequired) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else {
         label63:
         switch ((this.bits & 4032) >> 6) {
            case 11:
               switch ((this.expression.implicitConversion & 255) >> 4) {
                  case 5:
                     BranchLabel falseLabel;
                     this.expression.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, falseLabel = new BranchLabel(codeStream), valueRequired);
                     if (valueRequired) {
                        codeStream.iconst_0();
                        if (falseLabel.forwardReferenceCount() > 0) {
                           BranchLabel endifLabel;
                           codeStream.goto_(endifLabel = new BranchLabel(codeStream));
                           codeStream.decrStackSize(1);
                           falseLabel.place();
                           codeStream.iconst_1();
                           endifLabel.place();
                        }
                     } else {
                        falseLabel.place();
                     }
                  default:
                     break label63;
               }
            case 12:
               switch ((this.expression.implicitConversion & 255) >> 4) {
                  case 7:
                     this.expression.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.ldc2_w(-1L);
                        codeStream.lxor();
                     }
                  case 8:
                  case 9:
                  default:
                     break label63;
                  case 10:
                     this.expression.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.iconst_m1();
                        codeStream.ixor();
                     }
                     break label63;
               }
            case 13:
               if (this.constant != Constant.NotAConstant) {
                  if (valueRequired) {
                     switch ((this.expression.implicitConversion & 255) >> 4) {
                        case 7:
                           codeStream.generateInlinedValue(this.constant.longValue() * -1L);
                           break label63;
                        case 8:
                           codeStream.generateInlinedValue(this.constant.doubleValue() * -1.0);
                           break label63;
                        case 9:
                           codeStream.generateInlinedValue(this.constant.floatValue() * -1.0F);
                           break label63;
                        case 10:
                           codeStream.generateInlinedValue(this.constant.intValue() * -1);
                     }
                  }
               } else {
                  this.expression.generateCode(currentScope, codeStream, valueRequired);
                  if (valueRequired) {
                     switch ((this.expression.implicitConversion & 255) >> 4) {
                        case 7:
                           codeStream.lneg();
                           break label63;
                        case 8:
                           codeStream.dneg();
                           break label63;
                        case 9:
                           codeStream.fneg();
                           break label63;
                        case 10:
                           codeStream.ineg();
                     }
                  }
               }
               break;
            case 14:
               this.expression.generateCode(currentScope, codeStream, valueRequired);
         }

         if (valueRequired) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void generateOptimizedBoolean(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if (this.constant != Constant.NotAConstant && this.constant.typeID() == 5) {
         super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
      } else {
         if ((this.bits & 4032) >> 6 == 11) {
            this.expression.generateOptimizedBoolean(currentScope, codeStream, falseLabel, trueLabel, valueRequired);
         } else {
            super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
         }

      }
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      output.append(this.operatorToString()).append(' ');
      return this.expression.printExpression(0, output);
   }

   public TypeBinding resolveType(BlockScope scope) {
      boolean expressionIsCast;
      if (expressionIsCast = this.expression instanceof CastExpression) {
         Expression var10000 = this.expression;
         var10000.bits |= 32;
      }

      TypeBinding expressionType = this.expression.resolveType(scope);
      if (expressionType == null) {
         this.constant = Constant.NotAConstant;
         return null;
      } else {
         int expressionTypeID = expressionType.id;
         boolean use15specifics = scope.compilerOptions().sourceLevel >= 3211264L;
         if (use15specifics && !expressionType.isBaseType()) {
            expressionTypeID = scope.environment().computeBoxingType(expressionType).id;
         }

         if (expressionTypeID > 15) {
            this.constant = Constant.NotAConstant;
            scope.problemReporter().invalidOperator(this, expressionType);
            return null;
         } else {
            byte tableId;
            switch ((this.bits & 4032) >> 6) {
               case 11:
                  tableId = 0;
                  break;
               case 12:
                  tableId = 10;
                  break;
               default:
                  tableId = 13;
            }

            int operatorSignature = OperatorSignatures[tableId][(expressionTypeID << 4) + expressionTypeID];
            this.expression.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 16 & 15), expressionType);
            this.bits |= operatorSignature & 15;
            switch (operatorSignature & 15) {
               case 2:
                  this.resolvedType = TypeBinding.CHAR;
                  break;
               case 3:
                  this.resolvedType = TypeBinding.BYTE;
                  break;
               case 4:
               case 6:
               default:
                  this.constant = Constant.NotAConstant;
                  if (expressionTypeID != 0) {
                     scope.problemReporter().invalidOperator(this, expressionType);
                  }

                  return null;
               case 5:
                  this.resolvedType = TypeBinding.BOOLEAN;
                  break;
               case 7:
                  this.resolvedType = TypeBinding.LONG;
                  break;
               case 8:
                  this.resolvedType = TypeBinding.DOUBLE;
                  break;
               case 9:
                  this.resolvedType = TypeBinding.FLOAT;
                  break;
               case 10:
                  this.resolvedType = TypeBinding.INT;
            }

            if (this.expression.constant != Constant.NotAConstant) {
               this.constant = Constant.computeConstantOperation(this.expression.constant, expressionTypeID, (this.bits & 4032) >> 6);
            } else {
               this.constant = Constant.NotAConstant;
               if ((this.bits & 4032) >> 6 == 11) {
                  Constant cst = this.expression.optimizedBooleanConstant();
                  if (cst != Constant.NotAConstant) {
                     this.optimizedBooleanConstant = BooleanConstant.fromValue(!cst.booleanValue());
                  }
               }
            }

            if (expressionIsCast) {
               CastExpression.checkNeedForArgumentCast(scope, tableId, operatorSignature, this.expression, expressionTypeID);
            }

            return this.resolvedType;
         }
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.expression.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }
}
