package org.cryptacular.generator.sp80038d;

import java.util.concurrent.atomic.AtomicLong;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.ByteUtil;

public class CounterNonce implements Nonce {
   public static final int DEFAULT_LENGTH = 12;
   public static final long MAX_INVOCATIONS = 4294967295L;
   private final byte[] fixed;
   private final AtomicLong count;

   public CounterNonce(String fixed, long invocations) {
      this(ByteUtil.toBytes(fixed), invocations);
   }

   public CounterNonce(int fixed, long invocations) {
      this(ByteUtil.toBytes(fixed), invocations);
   }

   public CounterNonce(long fixed, long invocations) {
      this(ByteUtil.toBytes(fixed), invocations);
   }

   public CounterNonce(byte[] fixed, long invocations) {
      if (fixed != null && fixed.length != 0) {
         this.count = new AtomicLong(invocations);
         this.fixed = fixed;
      } else {
         throw new IllegalArgumentException("Fixed part cannot be null or empty.");
      }
   }

   public byte[] generate() throws LimitException {
      byte[] value = new byte[this.getLength()];
      System.arraycopy(this.fixed, 0, value, 0, this.fixed.length);
      long next = this.count.incrementAndGet();
      if (value.length != 12 && next > 4294967295L) {
         throw new LimitException("Exceeded 2^32 invocations.");
      } else {
         ByteUtil.toBytes(next, value, this.fixed.length);
         return value;
      }
   }

   public int getLength() {
      return this.fixed.length + 8;
   }

   public long getInvocations() {
      return this.count.get();
   }
}
