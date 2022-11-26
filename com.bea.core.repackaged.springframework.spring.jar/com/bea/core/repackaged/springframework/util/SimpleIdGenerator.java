package com.bea.core.repackaged.springframework.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleIdGenerator implements IdGenerator {
   private final AtomicLong mostSigBits = new AtomicLong(0L);
   private final AtomicLong leastSigBits = new AtomicLong(0L);

   public UUID generateId() {
      long leastSigBits = this.leastSigBits.incrementAndGet();
      if (leastSigBits == 0L) {
         this.mostSigBits.incrementAndGet();
      }

      return new UUID(this.mostSigBits.get(), leastSigBits);
   }
}
