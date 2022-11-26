package org.glassfish.tyrus.core;

import java.io.IOException;
import java.io.InputStream;

class BufferedInputStream extends InputStream {
   private final InputStreamBuffer buffer;

   public BufferedInputStream(InputStreamBuffer buffer) {
      this.buffer = buffer;
   }

   public int read() throws IOException {
      return this.buffer.getNextByte();
   }

   public void close() {
      this.buffer.finishReading();
   }
}
