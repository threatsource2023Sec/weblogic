package org.apache.commons.io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ChunkedOutputStream extends FilterOutputStream {
   private static final int DEFAULT_CHUNK_SIZE = 4096;
   private final int chunkSize;

   public ChunkedOutputStream(OutputStream stream, int chunkSize) {
      super(stream);
      if (chunkSize <= 0) {
         throw new IllegalArgumentException();
      } else {
         this.chunkSize = chunkSize;
      }
   }

   public ChunkedOutputStream(OutputStream stream) {
      this(stream, 4096);
   }

   public void write(byte[] data, int srcOffset, int length) throws IOException {
      int bytes = length;

      int chunk;
      for(int dstOffset = srcOffset; bytes > 0; dstOffset += chunk) {
         chunk = Math.min(bytes, this.chunkSize);
         this.out.write(data, dstOffset, chunk);
         bytes -= chunk;
      }

   }
}
