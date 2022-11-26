package antlr;

import java.io.IOException;

public class CharStreamIOException extends CharStreamException {
   public IOException io;

   public CharStreamIOException(IOException var1) {
      super(var1.getMessage());
      this.io = var1;
   }
}
