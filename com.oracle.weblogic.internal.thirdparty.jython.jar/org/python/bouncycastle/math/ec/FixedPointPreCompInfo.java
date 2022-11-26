package org.python.bouncycastle.math.ec;

public class FixedPointPreCompInfo implements PreCompInfo {
   protected ECPoint offset = null;
   protected ECPoint[] preComp = null;
   protected int width = -1;

   public ECPoint getOffset() {
      return this.offset;
   }

   public void setOffset(ECPoint var1) {
      this.offset = var1;
   }

   public ECPoint[] getPreComp() {
      return this.preComp;
   }

   public void setPreComp(ECPoint[] var1) {
      this.preComp = var1;
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }
}
