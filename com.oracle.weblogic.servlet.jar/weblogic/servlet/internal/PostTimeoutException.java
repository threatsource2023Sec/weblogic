package weblogic.servlet.internal;

import java.io.IOException;

public final class PostTimeoutException extends IOException {
   private static final long serialVersionUID = -1922335678836094250L;

   public PostTimeoutException() {
   }

   public PostTimeoutException(String msg) {
      super(msg);
   }
}
