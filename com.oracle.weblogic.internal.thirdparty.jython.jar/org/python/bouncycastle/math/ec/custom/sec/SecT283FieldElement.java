package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.raw.Nat;
import org.python.bouncycastle.math.raw.Nat320;
import org.python.bouncycastle.util.Arrays;

public class SecT283FieldElement extends ECFieldElement {
   protected long[] x;

   public SecT283FieldElement(BigInteger var1) {
      if (var1 != null && var1.signum() >= 0 && var1.bitLength() <= 283) {
         this.x = SecT283Field.fromBigInteger(var1);
      } else {
         throw new IllegalArgumentException("x value invalid for SecT283FieldElement");
      }
   }

   public SecT283FieldElement() {
      this.x = Nat320.create64();
   }

   protected SecT283FieldElement(long[] var1) {
      this.x = var1;
   }

   public boolean isOne() {
      return Nat320.isOne64(this.x);
   }

   public boolean isZero() {
      return Nat320.isZero64(this.x);
   }

   public boolean testBitZero() {
      return (this.x[0] & 1L) != 0L;
   }

   public BigInteger toBigInteger() {
      return Nat320.toBigInteger64(this.x);
   }

   public String getFieldName() {
      return "SecT283Field";
   }

   public int getFieldSize() {
      return 283;
   }

   public ECFieldElement add(ECFieldElement var1) {
      long[] var2 = Nat320.create64();
      SecT283Field.add(this.x, ((SecT283FieldElement)var1).x, var2);
      return new SecT283FieldElement(var2);
   }

   public ECFieldElement addOne() {
      long[] var1 = Nat320.create64();
      SecT283Field.addOne(this.x, var1);
      return new SecT283FieldElement(var1);
   }

   public ECFieldElement subtract(ECFieldElement var1) {
      return this.add(var1);
   }

   public ECFieldElement multiply(ECFieldElement var1) {
      long[] var2 = Nat320.create64();
      SecT283Field.multiply(this.x, ((SecT283FieldElement)var1).x, var2);
      return new SecT283FieldElement(var2);
   }

   public ECFieldElement multiplyMinusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      return this.multiplyPlusProduct(var1, var2, var3);
   }

   public ECFieldElement multiplyPlusProduct(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3) {
      long[] var4 = this.x;
      long[] var5 = ((SecT283FieldElement)var1).x;
      long[] var6 = ((SecT283FieldElement)var2).x;
      long[] var7 = ((SecT283FieldElement)var3).x;
      long[] var8 = Nat.create64(9);
      SecT283Field.multiplyAddToExt(var4, var5, var8);
      SecT283Field.multiplyAddToExt(var6, var7, var8);
      long[] var9 = Nat320.create64();
      SecT283Field.reduce(var8, var9);
      return new SecT283FieldElement(var9);
   }

   public ECFieldElement divide(ECFieldElement var1) {
      return this.multiply(var1.invert());
   }

   public ECFieldElement negate() {
      return this;
   }

   public ECFieldElement square() {
      long[] var1 = Nat320.create64();
      SecT283Field.square(this.x, var1);
      return new SecT283FieldElement(var1);
   }

   public ECFieldElement squareMinusProduct(ECFieldElement var1, ECFieldElement var2) {
      return this.squarePlusProduct(var1, var2);
   }

   public ECFieldElement squarePlusProduct(ECFieldElement var1, ECFieldElement var2) {
      long[] var3 = this.x;
      long[] var4 = ((SecT283FieldElement)var1).x;
      long[] var5 = ((SecT283FieldElement)var2).x;
      long[] var6 = Nat.create64(9);
      SecT283Field.squareAddToExt(var3, var6);
      SecT283Field.multiplyAddToExt(var4, var5, var6);
      long[] var7 = Nat320.create64();
      SecT283Field.reduce(var6, var7);
      return new SecT283FieldElement(var7);
   }

   public ECFieldElement squarePow(int var1) {
      if (var1 < 1) {
         return this;
      } else {
         long[] var2 = Nat320.create64();
         SecT283Field.squareN(this.x, var1, var2);
         return new SecT283FieldElement(var2);
      }
   }

   public ECFieldElement invert() {
      long[] var1 = Nat320.create64();
      SecT283Field.invert(this.x, var1);
      return new SecT283FieldElement(var1);
   }

   public ECFieldElement sqrt() {
      long[] var1 = Nat320.create64();
      SecT283Field.sqrt(this.x, var1);
      return new SecT283FieldElement(var1);
   }

   public int getRepresentation() {
      return 3;
   }

   public int getM() {
      return 283;
   }

   public int getK1() {
      return 5;
   }

   public int getK2() {
      return 7;
   }

   public int getK3() {
      return 12;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SecT283FieldElement)) {
         return false;
      } else {
         SecT283FieldElement var2 = (SecT283FieldElement)var1;
         return Nat320.eq64(this.x, var2.x);
      }
   }

   public int hashCode() {
      return 2831275 ^ Arrays.hashCode((long[])this.x, 0, 5);
   }
}
