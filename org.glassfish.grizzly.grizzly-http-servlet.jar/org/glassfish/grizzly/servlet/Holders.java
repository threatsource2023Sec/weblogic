package org.glassfish.grizzly.servlet;

import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class Holders {
   public interface ResponseHolder {
      Response getInternalResponse();
   }

   public interface RequestHolder {
      Request getInternalRequest();
   }
}
