package org.python.bouncycastle.pqc.math.linearalgebra;

public abstract class GF2nElement implements GFElement {
   protected GF2nField mField;
   protected int mDegree;

   public abstract Object clone();

   abstract void assignZero();

   abstract void assignOne();

   public abstract boolean testRightmostBit();

   abstract boolean testBit(int var1);

   public final GF2nField getField() {
      return this.mField;
   }

   public abstract GF2nElement increase();

   public abstract void increaseThis();

   public final GFElement subtract(GFElement var1) throws RuntimeException {
      return this.add(var1);
   }

   public final void subtractFromThis(GFElement var1) {
      this.addToThis(var1);
   }

   public abstract GF2nElement square();

   public abstract void squareThis();

   public abstract GF2nElement squareRoot();

   public abstract void squareRootThis();

   public final GF2nElement convert(GF2nField var1) throws RuntimeException {
      return this.mField.convert(this, var1);
   }

   public abstract int trace();

   public abstract GF2nElement solveQuadraticEquation() throws RuntimeException;
}
