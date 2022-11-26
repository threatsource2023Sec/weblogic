package weblogic.nodemanager.grizzly.portunif;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import weblogic.nodemanager.grizzly.GrizzlyInputStream;
import weblogic.nodemanager.grizzly.GrizzlyOutputStream;
import weblogic.nodemanager.grizzly.IdleTimeoutHandler;
import weblogic.nodemanager.server.InternalNMCommandHandler;
import weblogic.nodemanager.server.NMServer;

public class NMProtoReadFilter extends BaseFilter {
   private NMServer nmServer;
   public static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public NMProtoReadFilter(NMServer nmServer) {
      this.nmServer = nmServer;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      int chunkTotalSize = 4098;
      IdleTimeoutHandler.setConnectionIdleTimeout(ctx.getConnection(), -1L);
      ctx.getConnection().setWriteTimeout(0L, TimeUnit.MILLISECONDS);
      ctx.getConnection().setReadTimeout(0L, TimeUnit.MILLISECONDS);
      ctx.getConnection().setReadBufferSize(chunkTotalSize);
      ctx.getConnection().setWriteBufferSize(chunkTotalSize);
      ctx.getTransportContext().configureBlocking(true);
      OutputStream grizzlyOutStream = new GrizzlyOutputStream(ctx);
      InputStream grizzlyInputStream = new GrizzlyInputStream(ctx);
      InternalNMCommandHandler impl = new InternalNMCommandHandler(this.nmServer, grizzlyOutStream, grizzlyInputStream);
      impl.processRequests();
      return ctx.getInvokeAction();
   }
}
