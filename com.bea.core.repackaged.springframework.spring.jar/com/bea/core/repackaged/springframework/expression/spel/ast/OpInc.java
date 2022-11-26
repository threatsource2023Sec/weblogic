package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Operation;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.util.Assert;
import java.math.BigDecimal;
import java.math.BigInteger;

public class OpInc extends Operator {
   private final boolean postfix;

   public OpInc(int pos, boolean postfix, SpelNodeImpl... operands) {
      super("++", pos, operands);
      this.postfix = postfix;
      Assert.notEmpty((Object[])operands, (String)"Operands must not be empty");
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      SpelNodeImpl operand = this.getLeftOperand();
      ValueRef valueRef = operand.getValueRef(state);
      TypedValue typedValue = valueRef.getValue();
      Object value = typedValue.getValue();
      TypedValue returnValue = typedValue;
      TypedValue newValue = null;
      if (value instanceof Number) {
         Number op1 = (Number)value;
         if (op1 instanceof BigDecimal) {
            newValue = new TypedValue(((BigDecimal)op1).add(BigDecimal.ONE), typedValue.getTypeDescriptor());
         } else if (op1 instanceof Double) {
            newValue = new TypedValue(op1.doubleValue() + 1.0, typedValue.getTypeDescriptor());
         } else if (op1 instanceof Float) {
            newValue = new TypedValue(op1.floatValue() + 1.0F, typedValue.getTypeDescriptor());
         } else if (op1 instanceof BigInteger) {
            newValue = new TypedValue(((BigInteger)op1).add(BigInteger.ONE), typedValue.getTypeDescriptor());
         } else if (op1 instanceof Long) {
            newValue = new TypedValue(op1.longValue() + 1L, typedValue.getTypeDescriptor());
         } else if (op1 instanceof Integer) {
            newValue = new TypedValue(op1.intValue() + 1, typedValue.getTypeDescriptor());
         } else if (op1 instanceof Short) {
            newValue = new TypedValue(op1.shortValue() + 1, typedValue.getTypeDescriptor());
         } else if (op1 instanceof Byte) {
            newValue = new TypedValue(op1.byteValue() + 1, typedValue.getTypeDescriptor());
         } else {
            newValue = new TypedValue(op1.doubleValue() + 1.0, typedValue.getTypeDescriptor());
         }
      }

      if (newValue == null) {
         try {
            newValue = state.operate(Operation.ADD, returnValue.getValue(), 1);
         } catch (SpelEvaluationException var10) {
            if (var10.getMessageCode() == SpelMessage.OPERATOR_NOT_SUPPORTED_BETWEEN_TYPES) {
               throw new SpelEvaluationException(operand.getStartPosition(), SpelMessage.OPERAND_NOT_INCREMENTABLE, new Object[]{operand.toStringAST()});
            }

            throw var10;
         }
      }

      try {
         valueRef.setValue(newValue.getValue());
      } catch (SpelEvaluationException var9) {
         if (var9.getMessageCode() == SpelMessage.SETVALUE_NOT_SUPPORTED) {
            throw new SpelEvaluationException(operand.getStartPosition(), SpelMessage.OPERAND_NOT_INCREMENTABLE, new Object[0]);
         }

         throw var9;
      }

      if (!this.postfix) {
         returnValue = newValue;
      }

      return returnValue;
   }

   public String toStringAST() {
      return this.getLeftOperand().toStringAST() + "++";
   }

   public SpelNodeImpl getRightOperand() {
      throw new IllegalStateException("No right operand");
   }
}
