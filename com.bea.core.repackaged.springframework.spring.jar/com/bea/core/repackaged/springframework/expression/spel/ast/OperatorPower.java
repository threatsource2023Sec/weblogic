package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Operation;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class OperatorPower extends Operator {
   public OperatorPower(int pos, SpelNodeImpl... operands) {
      super("^", pos, operands);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      SpelNodeImpl leftOp = this.getLeftOperand();
      SpelNodeImpl rightOp = this.getRightOperand();
      Object leftOperand = leftOp.getValueInternal(state).getValue();
      Object rightOperand = rightOp.getValueInternal(state).getValue();
      if (leftOperand instanceof Number && rightOperand instanceof Number) {
         Number leftNumber = (Number)leftOperand;
         Number rightNumber = (Number)rightOperand;
         if (leftNumber instanceof BigDecimal) {
            BigDecimal leftBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(leftNumber, BigDecimal.class);
            return new TypedValue(leftBigDecimal.pow(rightNumber.intValue()));
         } else if (leftNumber instanceof BigInteger) {
            BigInteger leftBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(leftNumber, BigInteger.class);
            return new TypedValue(leftBigInteger.pow(rightNumber.intValue()));
         } else if (!(leftNumber instanceof Double) && !(rightNumber instanceof Double)) {
            if (!(leftNumber instanceof Float) && !(rightNumber instanceof Float)) {
               double d = Math.pow(leftNumber.doubleValue(), rightNumber.doubleValue());
               return !(d > 2.147483647E9) && !(leftNumber instanceof Long) && !(rightNumber instanceof Long) ? new TypedValue((int)d) : new TypedValue((long)d);
            } else {
               return new TypedValue(Math.pow((double)leftNumber.floatValue(), (double)rightNumber.floatValue()));
            }
         } else {
            return new TypedValue(Math.pow(leftNumber.doubleValue(), rightNumber.doubleValue()));
         }
      } else {
         return state.operate(Operation.POWER, leftOperand, rightOperand);
      }
   }
}
