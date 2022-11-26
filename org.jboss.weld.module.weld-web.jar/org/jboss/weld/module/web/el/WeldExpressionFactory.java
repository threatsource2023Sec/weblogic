package org.jboss.weld.module.web.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import org.jboss.weld.logging.ElLogger;
import org.jboss.weld.module.web.util.el.ForwardingExpressionFactory;

public class WeldExpressionFactory extends ForwardingExpressionFactory {
   private final ExpressionFactory delegate;

   public WeldExpressionFactory(ExpressionFactory expressionFactory) {
      if (expressionFactory == null) {
         throw ElLogger.LOG.nullExpressionFactory();
      } else {
         this.delegate = expressionFactory;
      }
   }

   protected ExpressionFactory delegate() {
      return this.delegate;
   }

   public ValueExpression createValueExpression(ELContext context, String expression, Class expectedType) {
      return new WeldValueExpression(super.createValueExpression(context, expression, expectedType));
   }

   public MethodExpression createMethodExpression(ELContext context, String expression, Class expectedReturnType, Class[] expectedParamTypes) {
      return new WeldMethodExpression(super.createMethodExpression(context, expression, expectedReturnType, expectedParamTypes));
   }
}
