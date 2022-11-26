package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat448;
import org.python.bouncycastle.util.Arrays;

public class SecT409FieldElement extends ECFieldElement {
   protected long[] x;

   public SecT409FieldElement(BigInteger var1) {
      if (var1 != null && var1.signum() >= 0 && var1.bitLength() <= 409) {
         this.x = SecT409Field.fromBigInteger(var1);
      } else {
         throw new IllegalArgumentException("x value invalid for SecT409FieldElement");
      }
   }

   public SecT409FieldElement() {
      this.x = Nat448.create64();
   }

   protected SecT409FieldElement(long[] var1) {
      this.x = var1;
   }

   public boolean isOne() {
      return Nat448.isOne64(this.x);
   }

   public boolean isZero() {
      return Nat448.isZero64(this.x);
   }

   public boolean testBitZero() {
      return (this.x[0] & 1L) != 0L;
   }

   public BigInteger toBigInteger() {
      return Nat448.toBigInteger64(this.x);
   }

   public String getFieldName() {
      return "SecT409Field";
   }

   public int getFieldSize() {
      return 409;
   }

   public ECFieldElement add(ECFieldElement var1) {
      long[] var2 = Nat448.create64();
      SecT409Field.add(this.x, ((SecT409FieldElement)var1).x, var2);
      return new SecT409FieldElement(var2);
   }

   public ECFieldElement addOne() {
      long[] var1 = Nat448.create64();
      SecT409Field.addOne(this.x, var1);
      return new SecT409FieldElement(var1);
   }

   public ECFieldElement subtract(ECFieldElement var1) {
      return this.add(var1);
   }

   public ECFieldElement multiply(ECFieldElement var1) {
      long[] var2 = Nat448.create64();
      SecT409Field.multiply(this.x, ((SecT409FieldElement)var1).x, var2);
      return new SecT409FieldElement(var2);
   }

   public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      return this.multiplyPlusProduct(var1, var2, var3);
   }

   public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      long[] var4 = this.x;
      long[] var5 = ((SecT409FieldElement)var1).x;
      long[] var6 = ((SecT409FieldElement)var2).x;
      long[] var7 = ((SecT409FieldElement)var3).x;
      long[] var8 = Nat.create64(13);
      SecT409Field.multiplyAddToExt(var4, var5, var8);
      SecT409Field.multiplyAddToExt(var6, var7, var8);
      long[] var9 = Nat448.create64();
      SecT409Field.reduce(var8, var9);
      return new SecT409FieldElement(var9);
   }

   public ECFieldElement divide(ECFieldElement var1) {
      return this.multiply(var1.invert());
   }

   public ECFieldElement negate() {
      return this;
   }

   public ECFieldElement square() {
      long[] var1 = Nat448.create64();
      SecT409Field.square(this.x, var1);
      return new SecT409FieldElement(var1);
   }

   public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
      return this.squarePlusProduct(var1, var2);
   }

   public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
      long[] var3 = this.x;
      long[] var4 = ((SecT409FieldElement)var1).x;
      long[] var5 = ((SecT409FieldElement)var2).x;
      long[] var6 = Nat.create64(13);
      SecT409Field.squareAddToExt(var3, var6);
      SecT409Field.multiplyAddToExt(var4, var5, var6);
      long[] var7 = Nat448.create64();
      SecT409Field.reduce(var6, var7);
      return new SecT409FieldElement(var7);
   }

   public ECFieldElement squarePow(int var1) {
      if (var1 < 1) {
         return this;
      } else {
         long[] var2 = Nat448.create64();
         SecT409Field.squareN(this.x, var1, var2);
         return new SecT409FieldElement(var2);
      }
   }

   public ECFieldElement invert() {
      long[] var1 = Nat448.create64();
      SecT409Field.invert(this.x, var1);
      return new SecT409FieldElement(var1);
   }

   public ECFieldElement sqrt() {
      long[] var1 = Nat448.create64();
      SecT409Field.sqrt(this.x, var1);
      return new SecT409FieldElement(var1);
   }

   public int getRepresentation() {
      return 2;
   }

   public int getM() {
      return 409;
   }

   public int getK1() {
      return 87;
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
      } else if (!(var1 instanceof SecT409FieldElement)) {
         return false;
      } else {
         SecT409FieldElement var2 = (SecT409FieldElement)var1;
         return Nat448.eq64(this.x, var2.x);
      }
   }

   public int hashCode() {
      return 4090087 ^ Arrays.hashCode((long[])this.x, 0, 7);
   }
}
