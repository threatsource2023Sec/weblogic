package weblogic.nodemanager.grizzly;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import weblogic.nodemanager.NodeManagerRestTextFormatter;

public class GrizzlyInputStream extends InputStream {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   private final FilterChainContext ctx;
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public GrizzlyInputStream(FilterChainContext ctx) {
      this.ctx = ctx;
   }

   public int read() throws IOException {
      throw new UnsupportedOperationException();
   }

   public int read(byte[] b, int off, int len) throws IOException {
      Buffer input = null;

      try {
         input = (Buffer)this.ctx.getMessage();
         if (input == null || input.remaining() < 1) {
            try {
               if (input != null) {
                  input.tryDispose();
                  input.release();
               }

               nmLog.finest("Waiting for commands from client");
               ReadResult rs = this.ctx.read();
               input = (Buffer)rs.getMessage();
               this.ctx.setMessage(input);
               rs.recycle();
            } catch (Exception var7) {
               if (nmLog.isLoggable(Level.FINE)) {
                  nmLog.fine("Read Timeout or connection might have been closed by Client:" + var7.getMessage());
               }

               return -1;
            }
         }

         int remaining = input.remaining();
         int copyByteLen = remaining > len ? len : remaining;
         input.get(b, off, copyByteLen);
         return copyByteLen;
      } catch (Exception var8) {
         nmLog.log(Level.WARNING, nmRestText.msgGrizzlyReadError(), var8);
         if (input != null) {
            input.tryDispose();
         }

         return -1;
      }
   }
}
