package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.InputStream;

final class IntermediateInputStream extends InputStream {
   ByteChunk bc = null;
   boolean initialized = false;

   public IntermediateInputStream() {
   }

   public final void close() throws IOException {
      throw new IOException("close() called - shouldn't happen ");
   }

   public final int read(byte[] cbuf, int off, int len) throws IOException {
      return !this.initialized ? -1 : this.bc.substract(cbuf, off, len);
   }

   public final int read() throws IOException {
      return !this.initialized ? -1 : this.bc.substract();
   }

   public int available() throws IOException {
      return !this.initialized ? 0 : this.bc.getLength();
   }

   void setByteChunk(ByteChunk mb) {
      this.initialized = mb != null;
      this.bc = mb;
   }
}
