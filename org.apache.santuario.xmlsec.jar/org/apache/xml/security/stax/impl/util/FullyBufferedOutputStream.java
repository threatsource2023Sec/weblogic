package org.apache.xml.security.stax.impl.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public class FullyBufferedOutputStream extends FilterOutputStream {
   private UnsyncByteArrayOutputStream buf = new UnsyncByteArrayOutputStream();

   public FullyBufferedOutputStream(OutputStream out) {
      super(out);
   }

   public void write(int b) throws IOException {
      this.buf.write(b);
   }

   public void write(byte[] b) throws IOException {
      this.buf.write(b);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.buf.write(b, off, len);
   }

   public void close() throws IOException {
      this.buf.writeTo(this.out);
      this.out.close();
      this.buf.close();
   }

   public void flush() throws IOException {
   }
}
