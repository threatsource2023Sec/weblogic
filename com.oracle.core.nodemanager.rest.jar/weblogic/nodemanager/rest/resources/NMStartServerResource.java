package weblogic.nodemanager.rest.resources;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import weblogic.nodemanager.rest.utils.NMServerUtils;

public class NMStartServerResource extends NMBaseResource {
   @POST
   @Produces({"application/json"})
   public Response startServer(@PathParam("domain-name") String domainName, @PathParam("server-name") String serverName, @HeaderParam("Prefer") String prefer) throws Exception {
      if (!this.isServerConfigured(domainName, serverName)) {
         throw this.badRequest(SERVER_NOT_CONFIGURED);
      } else {
         return this.getJobResponse(NMServerUtils.startServer(domainName, serverName, this.findMaxWaitTime(prefer)));
      }
   }
}
