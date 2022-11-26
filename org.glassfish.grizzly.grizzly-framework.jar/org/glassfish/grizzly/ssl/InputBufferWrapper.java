package org.glassfish.grizzly.ssl;

import org.glassfish.grizzly.memory.ByteBufferWrapper;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;

final class InputBufferWrapper extends ByteBufferWrapper {
   private DirectByteBufferRecord record;

   public InputBufferWrapper() {
   }

   public InputBufferWrapper prepare(int size) {
      DirectByteBufferRecord recordLocal = DirectByteBufferRecord.get();
      this.record = recordLocal;
      this.visible = recordLocal.allocate(size);
      return this;
   }

   public void dispose() {
      this.record.release();
      this.record = null;
      super.dispose();
   }
}
