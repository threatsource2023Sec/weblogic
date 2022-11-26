package org.python.netty.util.internal;

import java.util.concurrent.atomic.LongAdder;

final class LongAdderCounter extends LongAdder implements LongCounter {
   public long value() {
      return this.longValue();
   }
}
