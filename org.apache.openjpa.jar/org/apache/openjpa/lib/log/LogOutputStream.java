package org.apache.openjpa.lib.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class LogOutputStream extends ByteArrayOutputStream {
   private static final String _sep = J2DoPrivHelper.getLineSeparator();
   private final int _level;
   private final Log _log;

   public LogOutputStream(Log log, int level) {
      this._log = log;
      this._level = level;
   }

   public void flush() throws IOException {
      super.flush();
      byte[] bytes = this.toByteArray();
      if (bytes.length != 0) {
         String msg = new String(bytes);
         if (msg.indexOf(_sep) != -1) {
            StringTokenizer tok = new StringTokenizer(msg, _sep);

            while(tok.hasMoreTokens()) {
               String next = tok.nextToken();
               this.log(next);
            }

            this.reset();
         }

      }
   }

   private void log(String msg) {
      switch (this._level) {
         case 1:
            this._log.trace(msg);
         case 2:
         default:
            break;
         case 3:
            this._log.info(msg);
            break;
         case 4:
            this._log.warn(msg);
            break;
         case 5:
            this._log.error(msg);
            break;
         case 6:
            this._log.fatal(msg);
      }

   }
}
