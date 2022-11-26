package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class ChunkedWriter extends FilterWriter {
   private static final int DEFAULT_CHUNK_SIZE = 4096;
   private final int chunkSize;

   public ChunkedWriter(Writer writer, int chunkSize) {
      super(writer);
      if (chunkSize <= 0) {
         throw new IllegalArgumentException();
      } else {
         this.chunkSize = chunkSize;
      }
   }

   public ChunkedWriter(Writer writer) {
      this(writer, 4096);
   }

   public void write(char[] data, int srcOffset, int length) throws IOException {
      int bytes = length;

      int chunk;
      for(int dstOffset = srcOffset; bytes > 0; dstOffset += chunk) {
         chunk = Math.min(bytes, this.chunkSize);
         this.out.write(data, dstOffset, chunk);
         bytes -= chunk;
      }

   }
}
