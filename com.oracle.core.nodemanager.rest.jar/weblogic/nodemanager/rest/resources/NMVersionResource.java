package weblogic.nodemanager.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import weblogic.nodemanager.rest.model.Version;

public class NMVersionResource extends NMBaseResource {
   private static final String V1 = "14.1.1.0.0";
   private static final String NODEMANAGER_URL_SUFFIX = "nodemanager";
   private static final String DOMAINS_URL_SUFFIX = "domains";

   @GET
   @Produces({"application/json"})
   public RestModelResponseBody getVersion() throws Exception {
      JSONObject item = new JSONObject();
      item.put("version", "14.1.1.0.0");
      item.put("isLatest", true);
      item.put("lifecycle", "active");
      RestModelResponseBody rb = this.restModelResponseBody(Version.class, this.getParentUri(), this.getTypedModel(Version.class, item));
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      this.addResourceLink(rb, "nodemanager");
      this.addResourceLink(rb, "domains");
      return rb;
   }

   @Path("nodemanager")
   public NodeManagerResource getNodemanager() throws Exception {
      return (NodeManagerResource)this.getSubResource(NodeManagerResource.class);
   }

   @Path("domains")
   public NMDomainsResource getDomains() throws Exception {
      return (NMDomainsResource)this.getSubResource(NMDomainsResource.class);
   }
}
