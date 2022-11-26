package weblogic.servlet.internal;

import java.io.IOException;
import weblogic.diagnostics.debug.DebugLogger;

public final class HTTPDebugLogger {
   public static final DebugLogger DEBUG_HTTP = DebugLogger.getDebugLogger("DebugHttp");
   public static final String BROKEN_PIPE = "Broken pipe";

   public static final boolean isEnabled() {
      return DEBUG_HTTP.isDebugEnabled();
   }

   public static final void debug(String msg) {
      DEBUG_HTTP.debug(msg);
   }

   public static final void debug(String msg, Exception e) {
      DEBUG_HTTP.debug(msg, e);
   }

   public static final boolean shouldLogIOException(IOException ioe) {
      return isEnabled() || ioe.getMessage() != null && !ioe.getMessage().contains("Broken pipe");
   }
}
