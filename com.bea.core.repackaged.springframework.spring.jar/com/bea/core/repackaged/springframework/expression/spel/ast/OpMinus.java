package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Operation;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class OpMinus extends Operator {
   public OpMinus(int pos, SpelNodeImpl... operands) {
      super("-", pos, operands);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      SpelNodeImpl leftOp = this.getLeftOperand();
      Object operand;
      if (this.children.length < 2) {
         operand = leftOp.getValueInternal(state).getValue();
         if (operand instanceof Number) {
            if (operand instanceof BigDecimal) {
               return new TypedValue(((BigDecimal)operand).negate());
            } else if (operand instanceof Double) {
               this.exitTypeDescriptor = "D";
               return new TypedValue(0.0 - ((Number)operand).doubleValue());
            } else if (operand instanceof Float) {
               this.exitTypeDescriptor = "F";
               return new TypedValue(0.0F - ((Number)operand).floatValue());
            } else if (operand instanceof BigInteger) {
               return new TypedValue(((BigInteger)operand).negate());
            } else if (operand instanceof Long) {
               this.exitTypeDescriptor = "J";
               return new TypedValue(0L - ((Number)operand).longValue());
            } else if (operand instanceof Integer) {
               this.exitTypeDescriptor = "I";
               return new TypedValue(0 - ((Number)operand).intValue());
            } else if (operand instanceof Short) {
               return new TypedValue(0 - ((Number)operand).shortValue());
            } else {
               return operand instanceof Byte ? new TypedValue(0 - ((Number)operand).byteValue()) : new TypedValue(0.0 - ((Number)operand).doubleValue());
            }
         } else {
            return state.operate(Operation.SUBTRACT, operand, (Object)null);
         }
      } else {
         operand = leftOp.getValueInternal(state).getValue();
         Object right = this.getRightOperand().getValueInternal(state).getValue();
         if (operand instanceof Number && right instanceof Number) {
            Number leftNumber = (Number)operand;
            Number rightNumber = (Number)right;
            if (!(leftNumber instanceof BigDecimal) && !(rightNumber instanceof BigDecimal)) {
               if (!(leftNumber instanceof Double) && !(rightNumber instanceof Double)) {
                  if (!(leftNumber instanceof Float) && !(rightNumber instanceof Float)) {
                     if (!(leftNumber instanceof BigInteger) && !(rightNumber instanceof BigInteger)) {
                        if (!(leftNumber instanceof Long) && !(rightNumber instanceof Long)) {
                           if (!CodeFlow.isIntegerForNumericOp(leftNumber) && !CodeFlow.isIntegerForNumericOp(rightNumber)) {
                              return new TypedValue(leftNumber.doubleValue() - rightNumber.doubleValue());
                           } else {
                              this.exitTypeDescriptor = "I";
                              return new TypedValue(leftNumber.intValue() - rightNumber.intValue());
                           }
                        } else {
                           this.exitTypeDescriptor = "J";
                           return new TypedValue(leftNumber.longValue() - rightNumber.longValue());
                        }
                     } else {
                        BigInteger leftBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(leftNumber, BigInteger.class);
                        BigInteger rightBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(rightNumber, BigInteger.class);
                        return new TypedValue(leftBigInteger.subtract(rightBigInteger));
                     }
                  } else {
                     this.exitTypeDescriptor = "F";
                     return new TypedValue(leftNumber.floatValue() - rightNumber.floatValue());
                  }
               } else {
                  this.exitTypeDescriptor = "D";
                  return new TypedValue(leftNumber.doubleValue() - rightNumber.doubleValue());
               }
            } else {
               BigDecimal leftBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(leftNumber, BigDecimal.class);
               BigDecimal rightBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(rightNumber, BigDecimal.class);
               return new TypedValue(leftBigDecimal.subtract(rightBigDecimal));
            }
         } else if (operand instanceof String && right instanceof Integer && ((String)operand).length() == 1) {
            String theString = (String)operand;
            Integer theInteger = (Integer)right;
            return new TypedValue(Character.toString((char)(theString.charAt(0) - theInteger)));
         } else {
            return state.operate(Operation.SUBTRACT, operand, right);
         }
      }
   }

   public String toStringAST() {
      return this.children.length < 2 ? "-" + this.getLeftOperand().toStringAST() : super.toStringAST();
   }

   public SpelNodeImpl getRightOperand() {
      if (this.children.length < 2) {
         throw new IllegalStateException("No right operand");
      } else {
         return this.children[1];
      }
   }

   public boolean isCompilable() {
      if (!this.getLeftOperand().isCompilable()) {
         return false;
      } else if (this.children.length > 1 && !this.getRightOperand().isCompilable()) {
         return false;
      } else {
         return this.exitTypeDescriptor != null;
      }
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      this.getLeftOperand().generateCode(mv, cf);
      String leftDesc = this.getLeftOperand().exitTypeDescriptor;
      String exitDesc = this.exitTypeDescriptor;
      Assert.state(exitDesc != null, "No exit type descriptor");
      char targetDesc = exitDesc.charAt(0);
      CodeFlow.insertNumericUnboxOrPrimitiveTypeCoercion(mv, leftDesc, targetDesc);
      if (this.children.length > 1) {
         cf.enterCompilationScope();
         this.getRightOperand().generateCode(mv, cf);
         String rightDesc = this.getRightOperand().exitTypeDescriptor;
         cf.exitCompilationScope();
         CodeFlow.insertNumericUnboxOrPrimitiveTypeCoercion(mv, rightDesc, targetDesc);
         switch (targetDesc) {
            case 'D':
               mv.visitInsn(103);
               break;
            case 'E':
            case 'G':
            case 'H':
            default:
               throw new IllegalStateException("Unrecognized exit type descriptor: '" + this.exitTypeDescriptor + "'");
            case 'F':
               mv.visitInsn(102);
               break;
            case 'I':
               mv.visitInsn(100);
               break;
            case 'J':
               mv.visitInsn(101);
         }
      } else {
         switch (targetDesc) {
            case 'D':
               mv.visitInsn(119);
               break;
            case 'E':
            case 'G':
            case 'H':
            default:
               throw new IllegalStateException("Unrecognized exit type descriptor: '" + this.exitTypeDescriptor + "'");
            case 'F':
               mv.visitInsn(118);
               break;
            case 'I':
               mv.visitInsn(116);
               break;
            case 'J':
               mv.visitInsn(117);
         }
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
