package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;

class RootInputStream extends InputStream {
   private InputStream is = null;
   private int lineNumber = 1;
   private int prev = -1;
   private boolean truncated = false;

   public RootInputStream(InputStream is) {
      this.is = is;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public void truncate() {
      this.truncated = true;
   }

   public int read() throws IOException {
      if (this.truncated) {
         return -1;
      } else {
         int b = this.is.read();
         if (this.prev == 13 && b == 10) {
            ++this.lineNumber;
         }

         this.prev = b;
         return b;
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (this.truncated) {
         return -1;
      } else {
         int n = this.is.read(b, off, len);

         for(int i = off; i < off + n; ++i) {
            if (this.prev == 13 && b[i] == 10) {
               ++this.lineNumber;
            }

            this.prev = b[i];
         }

         return n;
      }
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }
}
