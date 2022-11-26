package org.python.bouncycastle.math.ec;

public class ScaleXPointMap implements ECPointMap {
   protected final ECFieldElement scale;

   public ScaleXPointMap(ECFieldElement var1) {
      this.scale = var1;
   }

   public ECPoint map(ECPoint var1) {
      return var1.scaleX(this.scale);
   }
}
