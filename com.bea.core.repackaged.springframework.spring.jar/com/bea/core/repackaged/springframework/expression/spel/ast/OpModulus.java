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

public class OpModulus extends Operator {
   public OpModulus(int pos, SpelNodeImpl... operands) {
      super("%", pos, operands);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      Object leftOperand = this.getLeftOperand().getValueInternal(state).getValue();
      Object rightOperand = this.getRightOperand().getValueInternal(state).getValue();
      if (leftOperand instanceof Number && rightOperand instanceof Number) {
         Number leftNumber = (Number)leftOperand;
         Number rightNumber = (Number)rightOperand;
         if (!(leftNumber instanceof BigDecimal) && !(rightNumber instanceof BigDecimal)) {
            if (!(leftNumber instanceof Double) && !(rightNumber instanceof Double)) {
               if (!(leftNumber instanceof Float) && !(rightNumber instanceof Float)) {
                  if (!(leftNumber instanceof BigInteger) && !(rightNumber instanceof BigInteger)) {
                     if (!(leftNumber instanceof Long) && !(rightNumber instanceof Long)) {
                        if (!CodeFlow.isIntegerForNumericOp(leftNumber) && !CodeFlow.isIntegerForNumericOp(rightNumber)) {
                           return new TypedValue(leftNumber.doubleValue() % rightNumber.doubleValue());
                        } else {
                           this.exitTypeDescriptor = "I";
                           return new TypedValue(leftNumber.intValue() % rightNumber.intValue());
                        }
                     } else {
                        this.exitTypeDescriptor = "J";
                        return new TypedValue(leftNumber.longValue() % rightNumber.longValue());
                     }
                  } else {
                     BigInteger leftBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(leftNumber, BigInteger.class);
                     BigInteger rightBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(rightNumber, BigInteger.class);
                     return new TypedValue(leftBigInteger.remainder(rightBigInteger));
                  }
               } else {
                  this.exitTypeDescriptor = "F";
                  return new TypedValue(leftNumber.floatValue() % rightNumber.floatValue());
               }
            } else {
               this.exitTypeDescriptor = "D";
               return new TypedValue(leftNumber.doubleValue() % rightNumber.doubleValue());
            }
         } else {
            BigDecimal leftBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(leftNumber, BigDecimal.class);
            BigDecimal rightBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(rightNumber, BigDecimal.class);
            return new TypedValue(leftBigDecimal.remainder(rightBigDecimal));
         }
      } else {
         return state.operate(Operation.MODULUS, leftOperand, rightOperand);
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
               mv.visitInsn(115);
               break;
            case 'E':
            case 'G':
            case 'H':
            default:
               throw new IllegalStateException("Unrecognized exit type descriptor: '" + this.exitTypeDescriptor + "'");
            case 'F':
               mv.visitInsn(114);
               break;
            case 'I':
               mv.visitInsn(112);
               break;
            case 'J':
               mv.visitInsn(113);
         }
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
