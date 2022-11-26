package weblogic.nodemanager.grizzly.portunif;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.portunif.PUContext;
import org.glassfish.grizzly.portunif.ProtocolFinder;
import org.glassfish.grizzly.portunif.ProtocolFinder.Result;
import org.glassfish.grizzly.portunif.finders.HttpProtocolFinder;

public class NMProtoFinder implements ProtocolFinder {
   public static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public ProtocolFinder.Result find(PUContext puContext, FilterChainContext ctx) {
      HttpProtocolFinder httpFin = new HttpProtocolFinder();
      ProtocolFinder.Result httpResult = httpFin.find(puContext, ctx);
      if (httpResult == Result.FOUND) {
         nmLog.fine("Got HTTP Request");
         if (nmLog.isLoggable(Level.FINEST) && ctx.getConnection() != null) {
            nmLog.finest("Accepted connection from " + ctx.getConnection().getPeerAddress());
         }

         return Result.NOT_FOUND;
      } else if (httpResult == Result.NOT_FOUND) {
         nmLog.fine("Got NM custom protocol request");
         if (nmLog.isLoggable(Level.FINEST)) {
            nmLog.finest("Accepted connection from " + ctx.getAddress());
         }

         return Result.FOUND;
      } else {
         nmLog.finest("Need more data to identify protcol");
         return httpResult;
      }
   }
}
