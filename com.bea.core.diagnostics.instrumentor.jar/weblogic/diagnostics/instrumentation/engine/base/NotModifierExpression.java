package weblogic.diagnostics.instrumentation.engine.base;

public class NotModifierExpression implements ModifierExpression {
   private static final long serialVersionUID = 1L;
   private ModifierExpression expr1;

   public NotModifierExpression(ModifierExpression expr1) {
      this.expr1 = expr1;
   }

   public boolean isMatch(int compareFlags) {
      return !this.expr1.isMatch(compareFlags);
   }
}
