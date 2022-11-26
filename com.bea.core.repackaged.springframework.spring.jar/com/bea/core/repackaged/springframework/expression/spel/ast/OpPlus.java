package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Operation;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class OpPlus extends Operator {
   public OpPlus(int pos, SpelNodeImpl... operands) {
      super("+", pos, operands);
      Assert.notEmpty((Object[])operands, (String)"Operands must not be empty");
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      SpelNodeImpl leftOp = this.getLeftOperand();
      if (this.children.length < 2) {
         Object operandOne = leftOp.getValueInternal(state).getValue();
         if (operandOne instanceof Number) {
            if (operandOne instanceof Double) {
               this.exitTypeDescriptor = "D";
            } else if (operandOne instanceof Float) {
               this.exitTypeDescriptor = "F";
            } else if (operandOne instanceof Long) {
               this.exitTypeDescriptor = "J";
            } else if (operandOne instanceof Integer) {
               this.exitTypeDescriptor = "I";
            }

            return new TypedValue(operandOne);
         } else {
            return state.operate(Operation.ADD, operandOne, (Object)null);
         }
      } else {
         TypedValue operandOneValue = leftOp.getValueInternal(state);
         Object leftOperand = operandOneValue.getValue();
         TypedValue operandTwoValue = this.getRightOperand().getValueInternal(state);
         Object rightOperand = operandTwoValue.getValue();
         if (leftOperand instanceof Number && rightOperand instanceof Number) {
            Number leftNumber = (Number)leftOperand;
            Number rightNumber = (Number)rightOperand;
            if (!(leftNumber instanceof BigDecimal) && !(rightNumber instanceof BigDecimal)) {
               if (!(leftNumber instanceof Double) && !(rightNumber instanceof Double)) {
                  if (!(leftNumber instanceof Float) && !(rightNumber instanceof Float)) {
                     if (!(leftNumber instanceof BigInteger) && !(rightNumber instanceof BigInteger)) {
                        if (!(leftNumber instanceof Long) && !(rightNumber instanceof Long)) {
                           if (!CodeFlow.isIntegerForNumericOp(leftNumber) && !CodeFlow.isIntegerForNumericOp(rightNumber)) {
                              return new TypedValue(leftNumber.doubleValue() + rightNumber.doubleValue());
                           } else {
                              this.exitTypeDescriptor = "I";
                              return new TypedValue(leftNumber.intValue() + rightNumber.intValue());
                           }
                        } else {
                           this.exitTypeDescriptor = "J";
                           return new TypedValue(leftNumber.longValue() + rightNumber.longValue());
                        }
                     } else {
                        BigInteger leftBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(leftNumber, BigInteger.class);
                        BigInteger rightBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(rightNumber, BigInteger.class);
                        return new TypedValue(leftBigInteger.add(rightBigInteger));
                     }
                  } else {
                     this.exitTypeDescriptor = "F";
                     return new TypedValue(leftNumber.floatValue() + rightNumber.floatValue());
                  }
               } else {
                  this.exitTypeDescriptor = "D";
                  return new TypedValue(leftNumber.doubleValue() + rightNumber.doubleValue());
               }
            } else {
               BigDecimal leftBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(leftNumber, BigDecimal.class);
               BigDecimal rightBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(rightNumber, BigDecimal.class);
               return new TypedValue(leftBigDecimal.add(rightBigDecimal));
            }
         } else if (leftOperand instanceof String && rightOperand instanceof String) {
            this.exitTypeDescriptor = "Ljava/lang/String";
            return new TypedValue((String)leftOperand + rightOperand);
         } else if (leftOperand instanceof String) {
            return new TypedValue(leftOperand + (rightOperand == null ? "null" : convertTypedValueToString(operandTwoValue, state)));
         } else {
            return rightOperand instanceof String ? new TypedValue((leftOperand == null ? "null" : convertTypedValueToString(operandOneValue, state)) + rightOperand) : state.operate(Operation.ADD, leftOperand, rightOperand);
         }
      }
   }

   public String toStringAST() {
      return this.children.length < 2 ? "+" + this.getLeftOperand().toStringAST() : super.toStringAST();
   }

   public SpelNodeImpl getRightOperand() {
      if (this.children.length < 2) {
         throw new IllegalStateException("No right operand");
      } else {
         return this.children[1];
      }
   }

   private static String convertTypedValueToString(TypedValue value, ExpressionState state) {
      TypeConverter typeConverter = state.getEvaluationContext().getTypeConverter();
      TypeDescriptor typeDescriptor = TypeDescriptor.valueOf(String.class);
      return typeConverter.canConvert(value.getTypeDescriptor(), typeDescriptor) ? String.valueOf(typeConverter.convertValue(value.getValue(), value.getTypeDescriptor(), typeDescriptor)) : String.valueOf(value.getValue());
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

   private void walk(MethodVisitor mv, CodeFlow cf, @Nullable SpelNodeImpl operand) {
      if (operand instanceof OpPlus) {
         OpPlus plus = (OpPlus)operand;
         this.walk(mv, cf, plus.getLeftOperand());
         this.walk(mv, cf, plus.getRightOperand());
      } else if (operand != null) {
         cf.enterCompilationScope();
         operand.generateCode(mv, cf);
         if (!"Ljava/lang/String".equals(cf.lastDescriptor())) {
            mv.visitTypeInsn(192, "java/lang/String");
         }

         cf.exitCompilationScope();
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
      }

   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      if ("Ljava/lang/String".equals(this.exitTypeDescriptor)) {
         mv.visitTypeInsn(187, "java/lang/StringBuilder");
         mv.visitInsn(89);
         mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "()V", false);
         this.walk(mv, cf, this.getLeftOperand());
         this.walk(mv, cf, this.getRightOperand());
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
      } else {
         this.children[0].generateCode(mv, cf);
         String leftDesc = this.children[0].exitTypeDescriptor;
         String exitDesc = this.exitTypeDescriptor;
         Assert.state(exitDesc != null, "No exit type descriptor");
         char targetDesc = exitDesc.charAt(0);
         CodeFlow.insertNumericUnboxOrPrimitiveTypeCoercion(mv, leftDesc, targetDesc);
         if (this.children.length > 1) {
            cf.enterCompilationScope();
            this.children[1].generateCode(mv, cf);
            String rightDesc = this.children[1].exitTypeDescriptor;
            cf.exitCompilationScope();
            CodeFlow.insertNumericUnboxOrPrimitiveTypeCoercion(mv, rightDesc, targetDesc);
            switch (targetDesc) {
               case 'D':
                  mv.visitInsn(99);
                  break;
               case 'E':
               case 'G':
               case 'H':
               default:
                  throw new IllegalStateException("Unrecognized exit type descriptor: '" + this.exitTypeDescriptor + "'");
               case 'F':
                  mv.visitInsn(98);
                  break;
               case 'I':
                  mv.visitInsn(96);
                  break;
               case 'J':
                  mv.visitInsn(97);
            }
         }
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
