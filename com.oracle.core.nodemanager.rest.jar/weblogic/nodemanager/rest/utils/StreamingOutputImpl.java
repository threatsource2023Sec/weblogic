package weblogic.nodemanager.rest.utils;

import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;
import weblogic.nodemanager.rest.RestInputOutput;
import weblogic.nodemanager.server.InternalNMCommandHandler;

public class StreamingOutputImpl implements StreamingOutput {
   private final InternalNMCommandHandler handler;

   public StreamingOutputImpl(InternalNMCommandHandler impl) {
      this.handler = impl;
   }

   public void write(OutputStream output) throws IOException, WebApplicationException {
      RestInputOutput rio = (RestInputOutput)this.handler.getIoHandler();
      rio.resetOutput(output);
      this.handler.processRequests();
      String line = rio.getLatestResponse();
      if (line.contains("-ERR ")) {
         throw new WebApplicationException(new Throwable(), Response.status(Status.BAD_REQUEST).entity(line.substring(CommonUtils.ERROR_MSG_SUBSTRING_INDEX)).build());
      }
   }
}
