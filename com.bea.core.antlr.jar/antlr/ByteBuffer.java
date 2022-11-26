package antlr;

import java.io.IOException;
import java.io.InputStream;

public class ByteBuffer extends InputBuffer {
   public transient InputStream input;

   public ByteBuffer(InputStream var1) {
      this.input = var1;
   }

   public void fill(int var1) throws CharStreamException {
      try {
         this.syncConsume();

         while(this.queue.nbrEntries < var1 + this.markerOffset) {
            this.queue.append((char)this.input.read());
         }

      } catch (IOException var3) {
         throw new CharStreamIOException(var3);
      }
   }
}
