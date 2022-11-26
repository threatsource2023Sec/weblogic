package weblogic.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

public class SocketTimeoutNotification extends InputStream {
   private static final SocketTimeoutException STOE = new SocketTimeoutException();

   public int read() throws IOException {
      throw STOE;
   }
}
