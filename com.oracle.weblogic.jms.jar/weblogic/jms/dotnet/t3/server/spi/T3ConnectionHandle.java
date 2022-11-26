package weblogic.jms.dotnet.t3.server.spi;

import weblogic.utils.io.ChunkedDataInputStream;

public interface T3ConnectionHandle extends T3ConnectionGoneListener {
   void onMessage(ChunkedDataInputStream var1);
}
