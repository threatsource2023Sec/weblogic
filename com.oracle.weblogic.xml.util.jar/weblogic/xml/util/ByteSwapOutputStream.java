package weblogic.xml.util;

import java.io.IOException;
import java.io.OutputStream;

class ByteSwapOutputStream extends OutputStream {
   private OutputStream out;
   private int byte1;

   public ByteSwapOutputStream(OutputStream out) {
      this.out = out;
      this.byte1 = -2;
   }

   public void write(int c) throws IOException {
      if (this.byte1 == -2) {
         this.byte1 = c;
      } else {
         this.out.write(c);
         this.out.write(this.byte1);
         this.byte1 = -2;
      }

   }

   public void close() throws IOException {
      if (this.byte1 != -2) {
         this.out.write(0);
         this.out.write(this.byte1);
      }

   }
}
