package weblogic.servlet.internal;

import java.io.IOException;

public class WriteClientIOException extends IOException {
   public WriteClientIOException() {
   }

   public WriteClientIOException(String msg) {
      super(msg);
   }
}
