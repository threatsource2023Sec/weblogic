package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.raw.Nat128;
import org.python.bouncycastle.util.Arrays;

public class SecT113FieldElement extends ECFieldElement {
   protected long[] x;

   public SecT113FieldElement(BigInteger var1) {
      if (var1 != null && var1.signum() >= 0 && var1.bitLength() <= 113) {
         this.x = SecT113Field.fromBigInteger(var1);
      } else {
         throw new IllegalArgumentException("x value invalid for SecT113FieldElement");
      }
   }

   public SecT113FieldElement() {
      this.x = Nat128.create64();
   }

   protected SecT113FieldElement(long[] var1) {
      this.x = var1;
   }

   public boolean isOne() {
      return Nat128.isOne64(this.x);
   }

   public boolean isZero() {
      return Nat128.isZero64(this.x);
   }

   public boolean testBitZero() {
      return (this.x[0] & 1L) != 0L;
   }

   public BigInteger toBigInteger() {
      return Nat128.toBigInteger64(this.x);
   }

   public String getFieldName() {
      return "SecT113Field";
   }

   public int getFieldSize() {
      return 113;
   }

   public ECFieldElement add(ECFieldElement var1) {
      long[] var2 = Nat128.create64();
      SecT113Field.add(this.x, ((SecT113FieldElement)var1).x, var2);
      return new SecT113FieldElement(var2);
   }

   public ECFieldElement addOne() {
      long[] var1 = Nat128.create64();
      SecT113Field.addOne(this.x, var1);
      return new SecT113FieldElement(var1);
   }

   public ECFieldElement subtract(ECFieldElement var1) {
      return this.add(var1);
   }

   public ECFieldElement multiply(ECFieldElement var1) {
      long[] var2 = Nat128.create64();
      SecT113Field.multiply(this.x, ((SecT113FieldElement)var1).x, var2);
      return new SecT113FieldElement(var2);
   }

   public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      return this.multiplyPlusProduct(var1, var2, var3);
   }

   public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      long[] var4 = this.x;
      long[] var5 = ((SecT113FieldElement)var1).x;
      long[] var6 = ((SecT113FieldElement)var2).x;
      long[] var7 = ((SecT113FieldElement)var3).x;
      long[] var8 = Nat128.createExt64();
      SecT113Field.multiplyAddToExt(var4, var5, var8);
      SecT113Field.multiplyAddToExt(var6, var7, var8);
      long[] var9 = Nat128.create64();
      SecT113Field.reduce(var8, var9);
      return new SecT113FieldElement(var9);
   }

   public ECFieldElement divide(ECFieldElement var1) {
      return this.multiply(var1.invert());
   }

   public ECFieldElement negate() {
      return this;
   }

   public ECFieldElement square() {
      long[] var1 = Nat128.create64();
      SecT113Field.square(this.x, var1);
      return new SecT113FieldElement(var1);
   }

   public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
      return this.squarePlusProduct(var1, var2);
   }

   public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
      long[] var3 = this.x;
      long[] var4 = ((SecT113FieldElement)var1).x;
      long[] var5 = ((SecT113FieldElement)var2).x;
      long[] var6 = Nat128.createExt64();
      SecT113Field.squareAddToExt(var3, var6);
      SecT113Field.multiplyAddToExt(var4, var5, var6);
      long[] var7 = Nat128.create64();
      SecT113Field.reduce(var6, var7);
      return new SecT113FieldElement(var7);
   }

   public ECFieldElement squarePow(int var1) {
      if (var1 < 1) {
         return this;
      } else {
         long[] var2 = Nat128.create64();
         SecT113Field.squareN(this.x, var1, var2);
         return new SecT113FieldElement(var2);
      }
   }

   public ECFieldElement invert() {
      long[] var1 = Nat128.create64();
      SecT113Field.invert(this.x, var1);
      return new SecT113FieldElement(var1);
   }

   public ECFieldElement sqrt() {
      long[] var1 = Nat128.create64();
      SecT113Field.sqrt(this.x, var1);
      return new SecT113FieldElement(var1);
   }

   public int getRepresentation() {
      return 2;
   }

   public int getM() {
      return 113;
   }

   public int getK1() {
      return 9;
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
      } else if (!(var1 instanceof SecT113FieldElement)) {
         return false;
      } else {
         SecT113FieldElement var2 = (SecT113FieldElement)var1;
         return Nat128.eq64(this.x, var2.x);
      }
   }

   public int hashCode() {
      return 113009 ^ Arrays.hashCode((long[])this.x, 0, 2);
   }
}
