package org.cryptacular.generator.sp80038a;

import java.util.concurrent.atomic.AtomicLong;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.ByteUtil;

public class LongCounterNonce implements Nonce {
   private final AtomicLong counter;

   public LongCounterNonce() {
      this(0L);
   }

   public LongCounterNonce(long start) {
      this.counter = new AtomicLong(start);
   }

   public byte[] generate() throws LimitException {
      return ByteUtil.toBytes(this.counter.incrementAndGet());
   }

   public int getLength() {
      return 8;
   }
}
