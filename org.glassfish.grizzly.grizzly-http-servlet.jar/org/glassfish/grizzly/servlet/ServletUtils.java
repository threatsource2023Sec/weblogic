package org.glassfish.grizzly.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class ServletUtils {
   public static Request getInternalRequest(HttpServletRequest servletRequest) {
      if (servletRequest instanceof Holders.RequestHolder) {
         return ((Holders.RequestHolder)servletRequest).getInternalRequest();
      } else {
         throw new IllegalArgumentException("Passed HttpServletRequest is not based on Grizzly");
      }
   }

   public static Response getInternalResponse(HttpServletResponse servletResponse) {
      if (servletResponse instanceof Holders.ResponseHolder) {
         return ((Holders.ResponseHolder)servletResponse).getInternalResponse();
      } else {
         throw new IllegalArgumentException("Passed HttpServletResponse is not based on Grizzly");
      }
   }
}
