package weblogic.diagnostics.instrumentation.engine.base;

public class ModifierValue implements ModifierExpression {
   private static final long serialVersionUID = 1L;
   private int modFlags;

   public ModifierValue(int modFlags) {
      this.modFlags = modFlags;
   }

   public boolean isMatch(int compareFlags) {
      if (this.modFlags == 0) {
         return true;
      } else {
         return (this.modFlags & compareFlags) != 0;
      }
   }
}
