package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import weblogic.nodemanager.rest.model.Domain;

public class NMDomainResource extends NMBaseResource {
   private static final String SERVERS = "servers";

   @GET
   @Produces({"application/json"})
   public RestModelResponseBody get(@PathParam("domain-name") String domainName) throws Exception {
      Domain nmd = new Domain();
      nmd.setName(domainName);
      nmd.allFieldsSet();
      RestModelResponseBody rb = this.restModelResponseBody(Domain.class, this.getParentUri(), nmd);
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      this.addResourceLink(rb, "servers");
      return rb;
   }

   @Path("servers")
   public DomainServersResource getDomainServersResource() throws Exception {
      return (DomainServersResource)this.getSubResource(DomainServersResource.class);
   }
}
