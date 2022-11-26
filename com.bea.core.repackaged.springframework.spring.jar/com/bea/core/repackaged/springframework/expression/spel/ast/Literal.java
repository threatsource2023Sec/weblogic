package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.InternalParseException;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.SpelParseException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class Literal extends SpelNodeImpl {
   @Nullable
   private final String originalValue;

   public Literal(@Nullable String originalValue, int pos) {
      super(pos);
      this.originalValue = originalValue;
   }

   @Nullable
   public final String getOriginalValue() {
      return this.originalValue;
   }

   public final TypedValue getValueInternal(ExpressionState state) throws SpelEvaluationException {
      return this.getLiteralValue();
   }

   public String toString() {
      return String.valueOf(this.getLiteralValue().getValue());
   }

   public String toStringAST() {
      return this.toString();
   }

   public abstract TypedValue getLiteralValue();

   public static Literal getIntLiteral(String numberToken, int pos, int radix) {
      try {
         int value = Integer.parseInt(numberToken, radix);
         return new IntLiteral(numberToken, pos, value);
      } catch (NumberFormatException var4) {
         throw new InternalParseException(new SpelParseException(pos >> 16, var4, SpelMessage.NOT_AN_INTEGER, new Object[]{numberToken}));
      }
   }

   public static Literal getLongLiteral(String numberToken, int pos, int radix) {
      try {
         long value = Long.parseLong(numberToken, radix);
         return new LongLiteral(numberToken, pos, value);
      } catch (NumberFormatException var5) {
         throw new InternalParseException(new SpelParseException(pos >> 16, var5, SpelMessage.NOT_A_LONG, new Object[]{numberToken}));
      }
   }

   public static Literal getRealLiteral(String numberToken, int pos, boolean isFloat) {
      try {
         if (isFloat) {
            float value = Float.parseFloat(numberToken);
            return new FloatLiteral(numberToken, pos, value);
         } else {
            double value = Double.parseDouble(numberToken);
            return new RealLiteral(numberToken, pos, value);
         }
      } catch (NumberFormatException var5) {
         throw new InternalParseException(new SpelParseException(pos >> 16, var5, SpelMessage.NOT_A_REAL, new Object[]{numberToken}));
      }
   }
}
