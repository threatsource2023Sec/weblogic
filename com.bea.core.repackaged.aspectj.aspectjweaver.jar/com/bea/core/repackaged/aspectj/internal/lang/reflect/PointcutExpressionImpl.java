package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.PointcutExpression;

public class PointcutExpressionImpl implements PointcutExpression {
   private String expression;

   public PointcutExpressionImpl(String aPointcutExpression) {
      this.expression = aPointcutExpression;
   }

   public String asString() {
      return this.expression;
   }

   public String toString() {
      return this.asString();
   }
}
