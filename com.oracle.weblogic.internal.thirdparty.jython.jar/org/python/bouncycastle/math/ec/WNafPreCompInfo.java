package org.python.bouncycastle.math.ec;

public class WNafPreCompInfo implements PreCompInfo {
   protected ECPoint[] preComp = null;
   protected ECPoint[] preCompNeg = null;
   protected ECPoint twice = null;

   public ECPoint[] getPreComp() {
      return this.preComp;
   }

   public void setPreComp(ECPoint[] var1) {
      this.preComp = var1;
   }

   public ECPoint[] getPreCompNeg() {
      return this.preCompNeg;
   }

   public void setPreCompNeg(ECPoint[] var1) {
      this.preCompNeg = var1;
   }

   public ECPoint getTwice() {
      return this.twice;
   }

   public void setTwice(ECPoint var1) {
      this.twice = var1;
   }
}
