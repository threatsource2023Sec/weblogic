package weblogic.nodemanager.rest.resources;

import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.glassfish.admin.rest.model.RestCollectionResponseBody;
import weblogic.nodemanager.rest.async.AsyncJob;
import weblogic.nodemanager.rest.model.ServerJob;
import weblogic.nodemanager.rest.utils.NMServerUtils;

public class NMServerJobsResource extends NMBaseResource {
   @GET
   @Produces({"application/json"})
   public RestCollectionResponseBody get(@PathParam("domain-name") String domainName, @PathParam("server-name") String serverName) throws Exception {
      if (!this.isServerConfigured(domainName, serverName)) {
         throw this.badRequest(SERVER_NOT_CONFIGURED);
      } else {
         RestCollectionResponseBody rb = this.restCollectionResponseBody(ServerJob.class, "items.name", this.getParentUri());
         List jobs = NMServerUtils.getServerJobs(domainName, serverName);
         Iterator var5 = jobs.iterator();

         while(var5.hasNext()) {
            AsyncJob job = (AsyncJob)var5.next();
            ServerJob serverJob = this.getJobModel(job);
            rb.addItem(serverJob, serverJob.getName());
         }

         rb.addSelfResourceLinks(this.getSubUri(new String[0]));
         return rb;
      }
   }

   @Path("{job}")
   public NMServerJobResource getJob() throws Exception {
      return (NMServerJobResource)this.getSubResource(NMServerJobResource.class);
   }
}
