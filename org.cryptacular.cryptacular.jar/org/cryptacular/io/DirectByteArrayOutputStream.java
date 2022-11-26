package org.cryptacular.io;

import java.io.ByteArrayOutputStream;

public class DirectByteArrayOutputStream extends ByteArrayOutputStream {
   public DirectByteArrayOutputStream() {
   }

   public DirectByteArrayOutputStream(int capacity) {
      super(capacity);
   }

   public byte[] getBuffer() {
      return this.buf;
   }
}
