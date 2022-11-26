package org.hibernate.validator.internal.engine.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.internal.engine.messageinterpolation.el.SimpleELContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;

public class ElTermResolver implements TermResolver {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String VALIDATED_VALUE_NAME = "validatedValue";
   private final Locale locale;
   private final ExpressionFactory expressionFactory;

   public ElTermResolver(Locale locale, ExpressionFactory expressionFactory) {
      this.locale = locale;
      this.expressionFactory = expressionFactory;
   }

   public String interpolate(MessageInterpolator.Context context, String expression) {
      String resolvedExpression = expression;
      SimpleELContext elContext = new SimpleELContext(this.expressionFactory);

      try {
         ValueExpression valueExpression = this.bindContextValues(expression, context, elContext);
         resolvedExpression = (String)valueExpression.getValue(elContext);
      } catch (PropertyNotFoundException var6) {
         LOG.unknownPropertyInExpressionLanguage(expression, var6);
      } catch (ELException var7) {
         LOG.errorInExpressionLanguage(expression, var7);
      } catch (Exception var8) {
         LOG.evaluatingExpressionLanguageExpressionCausedException(expression, var8);
      }

      return resolvedExpression;
   }

   private ValueExpression bindContextValues(String messageTemplate, MessageInterpolator.Context messageInterpolatorContext, SimpleELContext elContext) {
      ValueExpression valueExpression = this.expressionFactory.createValueExpression(messageInterpolatorContext.getValidatedValue(), Object.class);
      elContext.getVariableMapper().setVariable("validatedValue", valueExpression);
      valueExpression = this.expressionFactory.createValueExpression(new FormatterWrapper(this.locale), FormatterWrapper.class);
      elContext.getVariableMapper().setVariable("formatter", valueExpression);
      this.addVariablesToElContext(elContext, messageInterpolatorContext.getConstraintDescriptor().getAttributes());
      if (messageInterpolatorContext instanceof HibernateMessageInterpolatorContext) {
         this.addVariablesToElContext(elContext, ((HibernateMessageInterpolatorContext)messageInterpolatorContext).getExpressionVariables());
      }

      return this.expressionFactory.createValueExpression(elContext, messageTemplate, String.class);
   }

   private void addVariablesToElContext(SimpleELContext elContext, Map variables) {
      Iterator var3 = variables.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         ValueExpression valueExpression = this.expressionFactory.createValueExpression(entry.getValue(), Object.class);
         elContext.getVariableMapper().setVariable((String)entry.getKey(), valueExpression);
      }

   }
}
