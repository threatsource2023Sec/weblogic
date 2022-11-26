package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ReadResult;

public final class RecordReadResult extends ReadResult {
   protected void set(Connection connection, Object message, Object srcAddress, int readSize) {
      super.set(connection, message, srcAddress, readSize);
   }

   public void recycle() {
      this.reset();
   }
}
