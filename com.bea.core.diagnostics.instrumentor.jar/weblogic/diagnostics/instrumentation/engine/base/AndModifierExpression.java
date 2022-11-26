package weblogic.diagnostics.instrumentation.engine.base;

public class AndModifierExpression implements ModifierExpression {
   private static final long serialVersionUID = 1L;
   private ModifierExpression expr1;
   private ModifierExpression expr2;

   public AndModifierExpression(ModifierExpression expr1, ModifierExpression expr2) {
      this.expr1 = expr1;
      this.expr2 = expr2;
   }

   public boolean isMatch(int compareFlags) {
      return this.expr1.isMatch(compareFlags) && this.expr2.isMatch(compareFlags);
   }
}
