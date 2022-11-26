package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

final class ReadConverter extends InputStreamReader {
   public ReadConverter(IntermediateInputStream in, Charset charset) throws UnsupportedEncodingException {
      super(in, charset);
   }

   public final void close() throws IOException {
   }

   public final int read(char[] cbuf, int off, int len) throws IOException {
      return super.read(cbuf, off, len);
   }

   public final void recycle() {
      while(true) {
         try {
            if (this.ready()) {
               this.read();
               continue;
            }
         } catch (IOException var2) {
         }

         return;
      }
   }
}
