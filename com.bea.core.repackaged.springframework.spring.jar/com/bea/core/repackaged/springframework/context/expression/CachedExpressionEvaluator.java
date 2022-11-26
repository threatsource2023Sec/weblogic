package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.core.DefaultParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.expression.Expression;
import com.bea.core.repackaged.springframework.expression.spel.standard.SpelExpressionParser;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.Map;

public abstract class CachedExpressionEvaluator {
   private final SpelExpressionParser parser;
   private final ParameterNameDiscoverer parameterNameDiscoverer;

   protected CachedExpressionEvaluator(SpelExpressionParser parser) {
      this.parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
      Assert.notNull(parser, (String)"SpelExpressionParser must not be null");
      this.parser = parser;
   }

   protected CachedExpressionEvaluator() {
      this(new SpelExpressionParser());
   }

   protected SpelExpressionParser getParser() {
      return this.parser;
   }

   protected ParameterNameDiscoverer getParameterNameDiscoverer() {
      return this.parameterNameDiscoverer;
   }

   protected Expression getExpression(Map cache, AnnotatedElementKey elementKey, String expression) {
      ExpressionKey expressionKey = this.createKey(elementKey, expression);
      Expression expr = (Expression)cache.get(expressionKey);
      if (expr == null) {
         expr = this.getParser().parseExpression(expression);
         cache.put(expressionKey, expr);
      }

      return expr;
   }

   private ExpressionKey createKey(AnnotatedElementKey elementKey, String expression) {
      return new ExpressionKey(elementKey, expression);
   }

   protected static class ExpressionKey implements Comparable {
      private final AnnotatedElementKey element;
      private final String expression;

      protected ExpressionKey(AnnotatedElementKey element, String expression) {
         Assert.notNull(element, (String)"AnnotatedElementKey must not be null");
         Assert.notNull(expression, (String)"Expression must not be null");
         this.element = element;
         this.expression = expression;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ExpressionKey)) {
            return false;
         } else {
            ExpressionKey otherKey = (ExpressionKey)other;
            return this.element.equals(otherKey.element) && ObjectUtils.nullSafeEquals(this.expression, otherKey.expression);
         }
      }

      public int hashCode() {
         return this.element.hashCode() * 29 + this.expression.hashCode();
      }

      public String toString() {
         return this.element + " with expression \"" + this.expression + "\"";
      }

      public int compareTo(ExpressionKey other) {
         int result = this.element.toString().compareTo(other.element.toString());
         if (result == 0) {
            result = this.expression.compareTo(other.expression);
         }

         return result;
      }
   }
}
