package org.hibernate.validator.internal.engine.messageinterpolation;

import java.util.Locale;
import javax.el.ExpressionFactory;
import javax.validation.MessageInterpolator;

public class InterpolationTerm {
   private static final String EL_DESIGNATION_CHARACTER = "$";
   private final String expression;
   private final InterpolationTermType type;
   private final TermResolver resolver;

   public InterpolationTerm(String expression, Locale locale, ExpressionFactory expressionFactory) {
      this.expression = expression;
      if (isElExpression(expression)) {
         this.type = InterpolationTermType.EL;
         this.resolver = new ElTermResolver(locale, expressionFactory);
      } else {
         this.type = InterpolationTermType.PARAMETER;
         this.resolver = new ParameterTermResolver();
      }

   }

   public static boolean isElExpression(String expression) {
      return expression.startsWith("$");
   }

   public String interpolate(MessageInterpolator.Context context) {
      return this.resolver.interpolate(context, this.expression);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("InterpolationExpression");
      sb.append("{expression='").append(this.expression).append('\'');
      sb.append(", type=").append(this.type);
      sb.append('}');
      return sb.toString();
   }
}
