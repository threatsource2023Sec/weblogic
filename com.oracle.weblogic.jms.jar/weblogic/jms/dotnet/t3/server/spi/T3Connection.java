package weblogic.jms.dotnet.t3.server.spi;

import java.io.IOException;
import weblogic.common.internal.PeerInfo;
import weblogic.rjvm.JVMID;
import weblogic.utils.io.ChunkedDataOutputStream;

public interface T3Connection {
   int T3CONNECTION_INVOKE_ID = 100;

   ChunkedDataOutputStream getRequestStream() throws IOException;

   void send(ChunkedDataOutputStream var1) throws IOException;

   PeerInfo getPeerInfo();

   void shutdown();

   JVMID getRJVMId();
}
