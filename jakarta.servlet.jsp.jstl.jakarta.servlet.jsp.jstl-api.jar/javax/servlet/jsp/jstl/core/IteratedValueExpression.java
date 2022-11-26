package javax.servlet.jsp.jstl.core;

import javax.el.ELContext;
import javax.el.ValueExpression;

public final class IteratedValueExpression extends ValueExpression {
   private static final long serialVersionUID = 1L;
   protected final int i;
   protected final IteratedExpression iteratedExpression;

   public IteratedValueExpression(IteratedExpression iteratedExpr, int i) {
      this.i = i;
      this.iteratedExpression = iteratedExpr;
   }

   public Object getValue(ELContext context) {
      return this.iteratedExpression.getItem(context, this.i);
   }

   public void setValue(ELContext context, Object value) {
   }

   public boolean isReadOnly(ELContext context) {
      return true;
   }

   public Class getType(ELContext context) {
      return null;
   }

   public Class getExpectedType() {
      return Object.class;
   }

   public String getExpressionString() {
      return this.iteratedExpression.getValueExpression().getExpressionString();
   }

   public boolean equals(Object obj) {
      return this.iteratedExpression.getValueExpression().equals(obj);
   }

   public int hashCode() {
      return this.iteratedExpression.getValueExpression().hashCode();
   }

   public boolean isLiteralText() {
      return false;
   }
}
