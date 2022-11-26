package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;

public class Assign extends SpelNodeImpl {
   public Assign(int pos, SpelNodeImpl... operands) {
      super(pos, operands);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      TypedValue newValue = this.children[1].getValueInternal(state);
      this.getChild(0).setValue(state, newValue.getValue());
      return newValue;
   }

   public String toStringAST() {
      return this.getChild(0).toStringAST() + "=" + this.getChild(1).toStringAST();
   }
}
