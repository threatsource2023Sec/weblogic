package weblogic.nodemanager.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.rest.async.AsyncJob;
import weblogic.nodemanager.rest.model.ServerJob;
import weblogic.nodemanager.rest.utils.NMServerUtils;

public class NMServerJobResource extends NMBaseResource {
   @GET
   @Produces({"application/json"})
   @Consumes({"application/json"})
   public Response getJobDetails(@PathParam("domain-name") String domainName, @PathParam("job") String jobId, @PathParam("server-name") String serverName) throws Exception {
      if (!this.isServerConfigured(domainName, serverName)) {
         throw this.badRequest(SERVER_NOT_CONFIGURED);
      } else {
         AsyncJob asyncJob = NMServerUtils.getServerJob(jobId, domainName, serverName);
         if (asyncJob == null) {
            throw this.notFound(NodeManagerRestTextFormatter.getInstance().msgJobNotFound(jobId));
         } else {
            ServerJob serverJob = this.getJobModel(asyncJob);
            RestModelResponseBody rb = this.restModelResponseBody(ServerJob.class, this.getParentUri(), serverJob);
            rb.addSelfResourceLinks(this.getSubUri(new String[0]));
            return this.ok(rb);
         }
      }
   }
}
