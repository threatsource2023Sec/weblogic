package org.python.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.python.apache.commons.compress.utils.BitInputStream;

class BitStream extends BitInputStream {
   BitStream(InputStream in) {
      super(in, ByteOrder.LITTLE_ENDIAN);
   }

   int nextBit() throws IOException {
      return (int)this.readBits(1);
   }

   long nextBits(int n) throws IOException {
      return this.readBits(n);
   }

   int nextByte() throws IOException {
      return (int)this.readBits(8);
   }
}
