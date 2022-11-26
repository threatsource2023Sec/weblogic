package org.cryptacular.generator.sp80038a;

import java.math.BigInteger;
import java.util.Arrays;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;

public class BigIntegerCounterNonce implements Nonce {
   private BigInteger counter;
   private final int length;

   public BigIntegerCounterNonce(BigInteger counter, int length) {
      if (length < 1) {
         throw new IllegalArgumentException("Length must be positive");
      } else {
         this.length = length;
         this.counter = counter;
      }
   }

   public byte[] generate() throws LimitException {
      byte[] value;
      synchronized(this) {
         this.counter = this.counter.add(BigInteger.ONE);
         value = this.counter.toByteArray();
      }

      if (value.length > this.length) {
         throw new LimitException("Counter value exceeded max byte length " + this.length);
      } else if (value.length < this.length) {
         byte[] temp = new byte[this.length];
         Arrays.fill(temp, (byte)0);
         System.arraycopy(value, 0, temp, temp.length - value.length, value.length);
         return temp;
      } else {
         return value;
      }
   }

   public int getLength() {
      return this.length;
   }
}
