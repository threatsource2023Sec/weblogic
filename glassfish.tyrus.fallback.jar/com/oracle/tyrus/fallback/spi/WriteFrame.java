package com.oracle.tyrus.fallback.spi;

public class WriteFrame {
   public final byte[] buf;
   public final int offset;
   public final int length;

   public WriteFrame(byte[] buf, int offset, int length) {
      this.buf = buf;
      this.offset = offset;
      this.length = length;
   }
}
