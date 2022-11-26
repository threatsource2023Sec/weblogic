package weblogic.jms.dotnet.t3.server.spi;

import java.io.IOException;

public interface T3ConnectionGoneEvent {
   IOException getReason();
}
