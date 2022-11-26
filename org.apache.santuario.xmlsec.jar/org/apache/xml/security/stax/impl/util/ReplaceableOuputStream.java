package org.apache.xml.security.stax.impl.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ReplaceableOuputStream extends FilterOutputStream {
   public ReplaceableOuputStream(OutputStream out) {
      super(out);
   }

   public void setNewOutputStream(OutputStream outputStream) {
      this.out = outputStream;
   }

   public void write(int b) throws IOException {
      this.out.write(b);
   }

   public void write(byte[] b) throws IOException {
      this.out.write(b);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.out.write(b, off, len);
   }
}
