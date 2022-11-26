package weblogic.cluster.messaging.internal.server;

import java.io.IOException;
import java.net.Socket;
import weblogic.cluster.messaging.internal.ConnectionImpl;
import weblogic.cluster.messaging.internal.Message;
import weblogic.cluster.messaging.internal.ServerConfigurationInformation;
import weblogic.utils.io.ChunkedDataOutputStream;

public class SSLConnectionImpl extends ConnectionImpl {
   public SSLConnectionImpl(ServerConfigurationInformation info, int timeout) {
      super(info, timeout);
   }

   public SSLConnectionImpl(Socket socket, int timeout) throws IOException {
      super(socket, timeout);
   }

   protected void skipHeader(ChunkedDataOutputStream sos) {
      sos.skip(Message.SSL_HEADER_LENGTH);
   }

   protected void writeHeader(ChunkedDataOutputStream sos) {
      sos.writeBytes("CLUSTER-BROADCAST-SECURE");
   }
}
