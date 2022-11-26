package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.raw.Nat256;
import org.python.bouncycastle.util.Arrays;

public class SecT233FieldElement extends ECFieldElement {
   protected long[] x;

   public SecT233FieldElement(BigInteger var1) {
      if (var1 != null && var1.signum() >= 0 && var1.bitLength() <= 233) {
         this.x = SecT233Field.fromBigInteger(var1);
      } else {
         throw new IllegalArgumentException("x value invalid for SecT233FieldElement");
      }
   }

   public SecT233FieldElement() {
      this.x = Nat256.create64();
   }

   protected SecT233FieldElement(long[] var1) {
      this.x = var1;
   }

   public boolean isOne() {
      return Nat256.isOne64(this.x);
   }

   public boolean isZero() {
      return Nat256.isZero64(this.x);
   }

   public boolean testBitZero() {
      return (this.x[0] & 1L) != 0L;
   }

   public BigInteger toBigInteger() {
      return Nat256.toBigInteger64(this.x);
   }

   public String getFieldName() {
      return "SecT233Field";
   }

   public int getFieldSize() {
      return 233;
   }

   public ECFieldElement add(ECFieldElement var1) {
      long[] var2 = Nat256.create64();
      SecT233Field.add(this.x, ((SecT233FieldElement)var1).x, var2);
      return new SecT233FieldElement(var2);
   }

   public ECFieldElement addOne() {
      long[] var1 = Nat256.create64();
      SecT233Field.addOne(this.x, var1);
      return new SecT233FieldElement(var1);
   }

   public ECFieldElement subtract(ECFieldElement var1) {
      return this.add(var1);
   }

   public ECFieldElement multiply(ECFieldElement var1) {
      long[] var2 = Nat256.create64();
      SecT233Field.multiply(this.x, ((SecT233FieldElement)var1).x, var2);
      return new SecT233FieldElement(var2);
   }

   public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      return this.multiplyPlusProduct(var1, var2, var3);
   }

   public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      long[] var4 = this.x;
      long[] var5 = ((SecT233FieldElement)var1).x;
      long[] var6 = ((SecT233FieldElement)var2).x;
      long[] var7 = ((SecT233FieldElement)var3).x;
      long[] var8 = Nat256.createExt64();
      SecT233Field.multiplyAddToExt(var4, var5, var8);
      SecT233Field.multiplyAddToExt(var6, var7, var8);
      long[] var9 = Nat256.create64();
      SecT233Field.reduce(var8, var9);
      return new SecT233FieldElement(var9);
   }

   public ECFieldElement divide(ECFieldElement var1) {
      return this.multiply(var1.invert());
   }

   public ECFieldElement negate() {
      return this;
   }

   public ECFieldElement square() {
      long[] var1 = Nat256.create64();
      SecT233Field.square(this.x, var1);
      return new SecT233FieldElement(var1);
   }

   public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
      return this.squarePlusProduct(var1, var2);
   }

   public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
      long[] var3 = this.x;
      long[] var4 = ((SecT233FieldElement)var1).x;
      long[] var5 = ((SecT233FieldElement)var2).x;
      long[] var6 = Nat256.createExt64();
      SecT233Field.squareAddToExt(var3, var6);
      SecT233Field.multiplyAddToExt(var4, var5, var6);
      long[] var7 = Nat256.create64();
      SecT233Field.reduce(var6, var7);
      return new SecT233FieldElement(var7);
   }

   public ECFieldElement squarePow(int var1) {
      if (var1 < 1) {
         return this;
      } else {
         long[] var2 = Nat256.create64();
         SecT233Field.squareN(this.x, var1, var2);
         return new SecT233FieldElement(var2);
      }
   }

   public ECFieldElement invert() {
      long[] var1 = Nat256.create64();
      SecT233Field.invert(this.x, var1);
      return new SecT233FieldElement(var1);
   }

   public ECFieldElement sqrt() {
      long[] var1 = Nat256.create64();
      SecT233Field.sqrt(this.x, var1);
      return new SecT233FieldElement(var1);
   }

   public int getRepresentation() {
      return 2;
   }

   public int getM() {
      return 233;
   }

   public int getK1() {
      return 74;
   }

   public int getK2() {
      return 0;
   }

   public int getK3() {
      return 0;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SecT233FieldElement)) {
         return false;
      } else {
         SecT233FieldElement var2 = (SecT233FieldElement)var1;
         return Nat256.eq64(this.x, var2.x);
      }
   }

   public int hashCode() {
      return 2330074 ^ Arrays.hashCode((long[])this.x, 0, 4);
   }
}
