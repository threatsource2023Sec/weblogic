package weblogic.nodemanager.grizzly.portunif;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.memory.MemoryManager;

public class NMProtoWriteFilter extends BaseFilter {
   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      byte[] out = (byte[])ctx.getMessage();
      MemoryManager mm = ctx.getConnection().getTransport().getMemoryManager();
      Buffer output = mm.allocate(out.length);
      output.put(out);
      output.allowBufferDispose();
      ctx.setMessage(output.flip());
      return ctx.getInvokeAction();
   }
}
