package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import weblogic.nodemanager.rest.utils.NMRestUtils;

public class NodeManagerLogResource extends NMBaseResource {
   @GET
   @Produces({"text/plain"})
   public Response get(@PathParam("auth-domain-name") String domainName) throws Exception {
      StreamingOutput stream = NMRestUtils.getNodeManagerLog(domainName);
      return Response.ok(stream, "text/plain").header("Content-Disposition", this.getContentDisposition("nodemanager.log")).build();
   }
}
