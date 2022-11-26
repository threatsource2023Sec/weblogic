package org.hibernate.validator.internal.engine.messageinterpolation;

import java.util.Arrays;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;

public class ParameterTermResolver implements TermResolver {
   public String interpolate(MessageInterpolator.Context context, String expression) {
      Object variable = this.getVariable(context, this.removeCurlyBraces(expression));
      String resolvedExpression;
      if (variable != null) {
         if (variable.getClass().isArray()) {
            resolvedExpression = Arrays.toString((Object[])((Object[])variable));
         } else {
            resolvedExpression = variable.toString();
         }
      } else {
         resolvedExpression = expression;
      }

      return resolvedExpression;
   }

   private Object getVariable(MessageInterpolator.Context context, String parameter) {
      if (context instanceof HibernateMessageInterpolatorContext) {
         Object variable = ((HibernateMessageInterpolatorContext)context).getMessageParameters().get(parameter);
         if (variable != null) {
            return variable;
         }
      }

      return context.getConstraintDescriptor().getAttributes().get(parameter);
   }

   private String removeCurlyBraces(String parameter) {
      return parameter.substring(1, parameter.length() - 1);
   }
}
