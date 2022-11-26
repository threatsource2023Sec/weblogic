package weblogic.nodemanager.rest.resources;

import javax.ws.rs.Path;

@Path("{auth-domain-name}/")
public class DomainUserResource extends NMBaseResource {
   @Path("management/nm/")
   public Object getNodemanager() throws Exception {
      return this.getSubResource(NMVersionsResource.class);
   }
}
