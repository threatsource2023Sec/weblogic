package org.python.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;

public interface GFElement {
   Object clone();

   boolean equals(Object var1);

   int hashCode();

   boolean isZero();

   boolean isOne();

   GFElement add(GFElement var1) throws RuntimeException;

   void addToThis(GFElement var1) throws RuntimeException;

   GFElement subtract(GFElement var1) throws RuntimeException;

   void subtractFromThis(GFElement var1);

   GFElement multiply(GFElement var1) throws RuntimeException;

   void multiplyThisBy(GFElement var1) throws RuntimeException;

   GFElement invert() throws ArithmeticException;

   BigInteger toFlexiBigInt();

   byte[] toByteArray();

   String toString();

   String toString(int var1);
}
