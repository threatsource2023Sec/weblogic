package weblogic.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public interface FutureServletResponse extends HttpServletResponse {
   void send() throws IOException;
}
