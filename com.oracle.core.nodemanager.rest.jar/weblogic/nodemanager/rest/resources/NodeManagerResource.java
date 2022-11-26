package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import weblogic.nodemanager.rest.model.NodeManager;
import weblogic.nodemanager.server.NMServer;

public class NodeManagerResource extends NMBaseResource {
   private static final String LOG_URL_SUFFIX = "log";

   @GET
   @Produces({"application/json"})
   public RestModelResponseBody get(@PathParam("auth-domain-name") String domainName) throws Exception {
      NodeManager nm = new NodeManager();
      nm.setVersion(NMServer.getNMVersion());
      nm.allFieldsSet();
      RestModelResponseBody rb = this.restModelResponseBody(NodeManager.class, this.getParentUri(), nm);
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      this.addResourceLink(rb, "log");
      return rb;
   }

   @Path("log")
   public NodeManagerLogResource getLogResource() throws Exception {
      return (NodeManagerLogResource)this.getSubResource(NodeManagerLogResource.class);
   }
}
