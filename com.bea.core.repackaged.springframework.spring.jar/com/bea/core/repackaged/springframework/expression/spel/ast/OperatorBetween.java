package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeComparator;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.BooleanTypedValue;
import java.util.List;

public class OperatorBetween extends Operator {
   public OperatorBetween(int pos, SpelNodeImpl... operands) {
      super("between", pos, operands);
   }

   public BooleanTypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      Object left = this.getLeftOperand().getValueInternal(state).getValue();
      Object right = this.getRightOperand().getValueInternal(state).getValue();
      if (right instanceof List && ((List)right).size() == 2) {
         List list = (List)right;
         Object low = list.get(0);
         Object high = list.get(1);
         TypeComparator comp = state.getTypeComparator();

         try {
            return BooleanTypedValue.forValue(comp.compare(left, low) >= 0 && comp.compare(left, high) <= 0);
         } catch (SpelEvaluationException var9) {
            var9.setPosition(this.getStartPosition());
            throw var9;
         }
      } else {
         throw new SpelEvaluationException(this.getRightOperand().getStartPosition(), SpelMessage.BETWEEN_RIGHT_OPERAND_MUST_BE_TWO_ELEMENT_LIST, new Object[0]);
      }
   }
}
