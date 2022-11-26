package weblogic.net.http;

import java.io.IOException;
import java.io.InputStream;

public class SocketClosedNotification extends InputStream {
   private static final IOException IOE = new IOException("SocketMuxer detected socket closure while waiting for a response");

   public int read() throws IOException {
      throw IOE;
   }
}
