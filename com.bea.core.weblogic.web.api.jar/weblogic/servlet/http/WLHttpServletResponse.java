package weblogic.servlet.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.WritableByteChannel;
import javax.servlet.http.HttpServletResponse;

public interface WLHttpServletResponse extends HttpServletResponse {
   Socket getSocket() throws IOException;

   WritableByteChannel getWritableByteChannel() throws IOException;
}
