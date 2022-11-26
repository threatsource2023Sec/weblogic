package org.glassfish.grizzly.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExpectationHandler {
   void onExpectAcknowledgement(HttpServletRequest var1, HttpServletResponse var2, AckAction var3) throws Exception;

   public interface AckAction {
      void acknowledge() throws IOException;

      void fail() throws IOException;
   }
}
