package org.python.core.io;

import java.io.IOException;
import java.io.InputStream;

public class TextIOInputStream extends InputStream {
   private TextIOBase textIO;

   public TextIOInputStream(TextIOBase textIO) {
      this.textIO = textIO;
   }

   public int read() throws IOException {
      String result = this.textIO.read(1);
      return result.length() == 0 ? -1 : result.charAt(0);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (len == 0) {
            return 0;
         } else {
            String result = this.textIO.read(len);
            len = result.length();

            for(int i = 0; i < len; ++i) {
               b[off + i] = (byte)result.charAt(i);
            }

            return len == 0 ? -1 : len;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void close() throws IOException {
      this.textIO.close();
   }

   public long skip(long n) throws IOException {
      return this.textIO.seek(n, 1);
   }
}
