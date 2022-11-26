package org.python.bouncycastle.crypto.params;

public class CramerShoupKeyParameters extends AsymmetricKeyParameter {
   private CramerShoupParameters params;

   protected CramerShoupKeyParameters(boolean var1, CramerShoupParameters var2) {
      super(var1);
      this.params = var2;
   }

   public CramerShoupParameters getParameters() {
      return this.params;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof CramerShoupKeyParameters)) {
         return false;
      } else {
         CramerShoupKeyParameters var2 = (CramerShoupKeyParameters)var1;
         if (this.params == null) {
            return var2.getParameters() == null;
         } else {
            return this.params.equals(var2.getParameters());
         }
      }
   }

   public int hashCode() {
      int var1 = this.isPrivate() ? 0 : 1;
      if (this.params != null) {
         var1 ^= this.params.hashCode();
      }

      return var1;
   }
}
