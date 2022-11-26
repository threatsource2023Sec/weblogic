package org.python.bouncycastle.crypto.ec;

import org.python.bouncycastle.math.ec.ECPoint;

public class ECPair {
   private final ECPoint x;
   private final ECPoint y;

   public ECPair(ECPoint var1, ECPoint var2) {
      this.x = var1;
      this.y = var2;
   }

   public ECPoint getX() {
      return this.x;
   }

   public ECPoint getY() {
      return this.y;
   }

   public boolean equals(ECPair var1) {
      return var1.getX().equals(this.getX()) && var1.getY().equals(this.getY());
   }

   public boolean equals(Object var1) {
      return var1 instanceof ECPair ? this.equals((ECPair)var1) : false;
   }

   public int hashCode() {
      return this.x.hashCode() + 37 * this.y.hashCode();
   }
}
