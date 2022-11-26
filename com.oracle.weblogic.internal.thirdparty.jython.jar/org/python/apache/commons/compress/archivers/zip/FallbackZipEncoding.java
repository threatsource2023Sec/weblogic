package org.python.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;

class FallbackZipEncoding implements ZipEncoding {
   private final String charsetName;

   public FallbackZipEncoding() {
      this.charsetName = null;
   }

   public FallbackZipEncoding(String charsetName) {
      this.charsetName = charsetName;
   }

   public boolean canEncode(String name) {
      return true;
   }

   public ByteBuffer encode(String name) throws IOException {
      return this.charsetName == null ? ByteBuffer.wrap(name.getBytes()) : ByteBuffer.wrap(name.getBytes(this.charsetName));
   }

   public String decode(byte[] data) throws IOException {
      return this.charsetName == null ? new String(data) : new String(data, this.charsetName);
   }
}
