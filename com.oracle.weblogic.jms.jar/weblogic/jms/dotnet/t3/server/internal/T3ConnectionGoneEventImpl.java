package weblogic.jms.dotnet.t3.server.internal;

import java.io.IOException;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionGoneEvent;
import weblogic.rjvm.PeerGoneEvent;

public final class T3ConnectionGoneEventImpl implements T3ConnectionGoneEvent {
   private IOException reason;

   T3ConnectionGoneEventImpl(PeerGoneEvent event) {
      this.reason = event.getReason();
   }

   public T3ConnectionGoneEventImpl(IOException ioe) {
      this.reason = ioe;
   }

   public IOException getReason() {
      return this.reason;
   }

   public String toString() {
      return this.reason.toString();
   }
}
