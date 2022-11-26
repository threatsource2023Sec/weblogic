package weblogic.net.http;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/** @deprecated */
@Deprecated
public final class HttpOutputStream extends FilterOutputStream {
   public HttpOutputStream(OutputStream o) {
      super(o);
   }

   public void write(int i) throws IOException {
      this.out.write(i);
   }

   public void write(byte[] buf, int off, int len) throws IOException {
      this.out.write(buf, off, len);
   }

   public void write(byte[] buf) throws IOException {
      this.out.write(buf, 0, buf.length);
   }

   public void print(String s) throws IOException {
      int l = s.length();

      for(int i = 0; i < l; ++i) {
         this.write(s.charAt(i));
      }

   }
}
