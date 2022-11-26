package org.jboss.weld.module.web.util.el;

import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public abstract class ForwardingExpressionFactory extends ExpressionFactory {
   protected abstract ExpressionFactory delegate();

   public Object coerceToType(Object obj, Class targetType) {
      return this.delegate().coerceToType(obj, targetType);
   }

   public MethodExpression createMethodExpression(ELContext context, String expression, Class expectedReturnType, Class[] expectedParamTypes) {
      return this.delegate().createMethodExpression(context, expression, expectedReturnType, expectedParamTypes);
   }

   public ValueExpression createValueExpression(Object instance, Class expectedType) {
      return this.delegate().createValueExpression(instance, expectedType);
   }

   public ValueExpression createValueExpression(ELContext context, String expression, Class expectedType) {
      return this.delegate().createValueExpression(context, expression, expectedType);
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public String toString() {
      return this.delegate().toString();
   }

   public ELResolver getStreamELResolver() {
      return this.delegate().getStreamELResolver();
   }

   public Map getInitFunctionMap() {
      return this.delegate().getInitFunctionMap();
   }
}
