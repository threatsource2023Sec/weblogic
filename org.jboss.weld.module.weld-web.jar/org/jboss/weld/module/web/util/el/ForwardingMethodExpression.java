package org.jboss.weld.module.web.util.el;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;

public abstract class ForwardingMethodExpression extends MethodExpression {
   private static final long serialVersionUID = -2614033937482335044L;

   protected abstract MethodExpression delegate();

   public MethodInfo getMethodInfo(ELContext context) {
      return this.delegate().getMethodInfo(context);
   }

   public Object invoke(ELContext context, Object[] params) {
      return this.delegate().invoke(context, params);
   }

   public String getExpressionString() {
      return this.delegate().getExpressionString();
   }

   public boolean isLiteralText() {
      return this.delegate().isLiteralText();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else if (obj instanceof ForwardingMethodExpression) {
         MethodExpression delegate = ((ForwardingMethodExpression)obj).delegate();
         return this.delegate().equals(delegate);
      } else {
         return false;
      }
   }

   public boolean isParametersProvided() {
      return this.delegate().isParametersProvided();
   }

   /** @deprecated */
   @Deprecated
   public boolean isParmetersProvided() {
      return this.delegate().isParametersProvided();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public String toString() {
      return this.delegate().toString();
   }
}
