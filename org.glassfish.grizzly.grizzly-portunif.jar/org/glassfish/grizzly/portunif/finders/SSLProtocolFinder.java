package org.glassfish.grizzly.portunif.finders;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.portunif.PUContext;
import org.glassfish.grizzly.portunif.ProtocolFinder;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.ssl.SSLUtils;

public class SSLProtocolFinder implements ProtocolFinder {
   private static final Logger LOGGER = Grizzly.logger(SSLProtocolFinder.class);
   private final SSLEngineConfigurator sslEngineConfigurator;

   public SSLProtocolFinder(SSLEngineConfigurator sslEngineConfigurator) {
      this.sslEngineConfigurator = sslEngineConfigurator;
   }

   public ProtocolFinder.Result find(PUContext puContext, FilterChainContext ctx) {
      Buffer buffer = (Buffer)ctx.getMessage();

      try {
         int expectedLength = SSLUtils.getSSLPacketSize(buffer);
         if (expectedLength == -1 || buffer.remaining() < expectedLength) {
            return ProtocolFinder.Result.NEED_MORE_DATA;
         }
      } catch (SSLException var5) {
         LOGGER.log(Level.FINE, "Packet header is not SSL", var5);
         return ProtocolFinder.Result.NOT_FOUND;
      }

      return ProtocolFinder.Result.FOUND;
   }
}
