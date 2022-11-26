package weblogic.nodemanager.grizzly;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.logging.Logger;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public class GrizzlyOutputStream extends OutputStream {
   private final FilterChainContext ctx;
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public GrizzlyOutputStream(FilterChainContext ctx) {
      this.ctx = ctx;
   }

   public void write(int b) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void write(byte[] b, int off, int len) throws IOException {
      byte[] outArr = new byte[len];
      outArr = Arrays.copyOfRange(b, off, len);
      this.ctx.write(outArr);
   }
}
