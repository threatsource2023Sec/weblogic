package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;

public abstract class AbstractExpressionPointcut implements ExpressionPointcut, Serializable {
   @Nullable
   private String location;
   @Nullable
   private String expression;

   public void setLocation(@Nullable String location) {
      this.location = location;
   }

   @Nullable
   public String getLocation() {
      return this.location;
   }

   public void setExpression(@Nullable String expression) {
      this.expression = expression;

      try {
         this.onSetExpression(expression);
      } catch (IllegalArgumentException var3) {
         if (this.location != null) {
            throw new IllegalArgumentException("Invalid expression at location [" + this.location + "]: " + var3);
         } else {
            throw var3;
         }
      }
   }

   protected void onSetExpression(@Nullable String expression) throws IllegalArgumentException {
   }

   @Nullable
   public String getExpression() {
      return this.expression;
   }
}
