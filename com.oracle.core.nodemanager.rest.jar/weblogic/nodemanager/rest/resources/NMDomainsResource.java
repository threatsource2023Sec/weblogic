package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.glassfish.admin.rest.model.RestCollectionResponseBody;
import weblogic.nodemanager.rest.model.Domain;

public class NMDomainsResource extends NMBaseResource {
   @GET
   @Produces({"application/json"})
   public RestCollectionResponseBody get(@PathParam("auth-domain-name") String domainName) throws Exception {
      RestCollectionResponseBody rb = this.restCollectionResponseBody(Domain.class, "items.name", this.getParentUri());
      Domain nmd = new Domain();
      nmd.setName(domainName);
      nmd.allFieldsSet();
      rb.addItem(nmd, nmd.getName());
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      return rb;
   }

   @Path("{domain-name}")
   public NMDomainResource getChildResource() throws Exception {
      return (NMDomainResource)this.getSubResource(NMDomainResource.class);
   }
}
