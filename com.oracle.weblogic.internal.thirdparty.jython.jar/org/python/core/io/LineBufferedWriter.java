package org.python.core.io;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class LineBufferedWriter extends BufferedWriter {
   public LineBufferedWriter(RawIOBase rawIO) {
      super(rawIO, 0);
      this.buffer = ByteBuffer.allocate(8192);
   }

   public int write(ByteBuffer bytes) {
      int size = bytes.remaining();

      while(bytes.hasRemaining()) {
         byte next = bytes.get();

         try {
            this.buffer.put(next);
         } catch (BufferOverflowException var5) {
            this.flush();
            this.buffer.put(next);
         }

         if (next == 10) {
            this.flush();
         }
      }

      return size;
   }
}
