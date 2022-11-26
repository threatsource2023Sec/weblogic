package org.glassfish.tyrus.core;

import java.io.IOException;
import java.io.Reader;

class BufferedStringReader extends Reader {
   private final ReaderBuffer readerBuffer;

   public BufferedStringReader(ReaderBuffer readerBuffer) {
      this.readerBuffer = readerBuffer;
   }

   public int read(char[] destination, int offsetToStart, int numberOfChars) throws IOException {
      char[] got = this.readerBuffer.getNextChars(numberOfChars);
      if (got != null) {
         System.arraycopy(got, 0, destination, offsetToStart, got.length);
         return got.length;
      } else {
         return -1;
      }
   }

   public void close() {
      this.readerBuffer.finishReading();
   }
}
