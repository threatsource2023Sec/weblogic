package org.python.bouncycastle.math.ec;

public class ScaleYPointMap implements ECPointMap {
   protected final ECFieldElement scale;

   public ScaleYPointMap(ECFieldElement var1) {
      this.scale = var1;
   }

   public ECPoint map(ECPoint var1) {
      return var1.scaleY(this.scale);
   }
}
