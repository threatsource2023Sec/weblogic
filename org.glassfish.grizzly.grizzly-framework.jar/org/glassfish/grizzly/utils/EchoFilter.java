package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class EchoFilter extends BaseFilter {
   private static final Logger logger = Grizzly.logger(EchoFilter.class);

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Object message = ctx.getMessage();
      Connection connection = ctx.getConnection();
      Object address = ctx.getAddress();
      if (logger.isLoggable(Level.FINEST)) {
         logger.log(Level.FINEST, "EchoFilter. connection={0} dstAddress={1} message={2}", new Object[]{connection, address, message});
      }

      if (message instanceof Buffer) {
         ((Buffer)message).allowBufferDispose(true);
      }

      ctx.write(address, message, (CompletionHandler)null);
      return ctx.getStopAction();
   }
}
