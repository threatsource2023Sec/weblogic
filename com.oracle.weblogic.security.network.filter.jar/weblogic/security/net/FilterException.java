package weblogic.security.net;

import java.io.IOException;

public final class FilterException extends IOException {
   private static final long serialVersionUID = -2581814989275884152L;

   public FilterException() {
   }

   public FilterException(String msg) {
      super(msg);
   }
}
