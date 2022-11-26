package antlr;

import java.io.IOException;

public class TokenStreamIOException extends TokenStreamException {
   public IOException io;

   public TokenStreamIOException(IOException var1) {
      super(var1.getMessage());
      this.io = var1;
   }
}
