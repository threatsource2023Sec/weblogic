package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import weblogic.nodemanager.rest.utils.NMServerUtils;

public class DomainServerStdOutResource extends NMBaseResource {
   @GET
   @Produces({"text/plain"})
   public Response getServerStdOut(@PathParam("domain-name") String domainName, @PathParam("server-name") String serverName) throws Exception {
      if (!this.isServerConfigured(domainName, serverName)) {
         throw this.badRequest(SERVER_NOT_CONFIGURED);
      } else {
         StreamingOutput stream = NMServerUtils.getServerStdOut(domainName, serverName, "WebLogic");
         return Response.ok(stream, "text/plain").header("Content-Disposition", this.getContentDisposition(serverName + ".out")).build();
      }
   }
}
