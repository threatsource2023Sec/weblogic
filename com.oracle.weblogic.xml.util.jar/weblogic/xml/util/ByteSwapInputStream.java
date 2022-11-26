package weblogic.xml.util;

import java.io.IOException;
import java.io.InputStream;

class ByteSwapInputStream extends InputStream {
   private InputStream in;
   private int byte1;

   public ByteSwapInputStream(InputStream in) {
      this.in = in;
      this.byte1 = -2;
   }

   public int read() throws IOException {
      if (this.byte1 == -2) {
         this.byte1 = this.in.read();
         return this.byte1 == -1 ? -1 : this.in.read();
      } else {
         int temp = this.byte1;
         this.byte1 = -2;
         return temp;
      }
   }
}
