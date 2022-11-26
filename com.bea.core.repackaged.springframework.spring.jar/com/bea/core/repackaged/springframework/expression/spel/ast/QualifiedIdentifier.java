package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class QualifiedIdentifier extends SpelNodeImpl {
   @Nullable
   private TypedValue value;

   public QualifiedIdentifier(int pos, SpelNodeImpl... operands) {
      super(pos, operands);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      if (this.value == null) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < this.getChildCount(); ++i) {
            Object value = this.children[i].getValueInternal(state).getValue();
            if (i > 0 && (value == null || !value.toString().startsWith("$"))) {
               sb.append(".");
            }

            sb.append(value);
         }

         this.value = new TypedValue(sb.toString());
      }

      return this.value;
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder();
      if (this.value != null) {
         sb.append(this.value.getValue());
      } else {
         for(int i = 0; i < this.getChildCount(); ++i) {
            if (i > 0) {
               sb.append(".");
            }

            sb.append(this.getChild(i).toStringAST());
         }
      }

      return sb.toString();
   }
}
