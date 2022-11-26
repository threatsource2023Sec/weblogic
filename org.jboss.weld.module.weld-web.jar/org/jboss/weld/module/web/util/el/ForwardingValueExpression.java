package org.jboss.weld.module.web.util.el;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.el.ValueReference;

public abstract class ForwardingValueExpression extends ValueExpression {
   private static final long serialVersionUID = -2318681808639242038L;

   protected abstract ValueExpression delegate();

   public Class getExpectedType() {
      return this.delegate().getExpectedType();
   }

   public Class getType(ELContext context) {
      return this.delegate().getType(context);
   }

   public Object getValue(ELContext context) {
      return this.delegate().getValue(context);
   }

   public boolean isReadOnly(ELContext context) {
      return this.delegate().isReadOnly(context);
   }

   public void setValue(ELContext context, Object value) {
      this.delegate().setValue(context, value);
   }

   public String getExpressionString() {
      return this.delegate().getExpressionString();
   }

   public boolean isLiteralText() {
      return this.delegate().isLiteralText();
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

   public ValueReference getValueReference(ELContext context) {
      return this.delegate().getValueReference(context);
   }
}
