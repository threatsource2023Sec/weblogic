package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;

public class Identifier extends SpelNodeImpl {
   private final TypedValue id;

   public Identifier(String payload, int pos) {
      super(pos);
      this.id = new TypedValue(payload);
   }

   public String toStringAST() {
      return String.valueOf(this.id.getValue());
   }

   public TypedValue getValueInternal(ExpressionState state) {
      return this.id;
   }
}
