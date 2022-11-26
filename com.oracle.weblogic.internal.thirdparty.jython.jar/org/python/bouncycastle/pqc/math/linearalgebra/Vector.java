package org.python.bouncycastle.pqc.math.linearalgebra;

public abstract class Vector {
   protected int length;

   public final int getLength() {
      return this.length;
   }

   public abstract byte[] getEncoded();

   public abstract boolean isZero();

   public abstract Vector add(Vector var1);

   public abstract Vector multiply(Permutation var1);

   public abstract boolean equals(Object var1);

   public abstract int hashCode();

   public abstract String toString();
}
