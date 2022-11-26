package weblogic.nodemanager.rest.resources;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestCollectionResponseBody;
import weblogic.nodemanager.rest.model.Version;

public class NMVersionsResource extends NMBaseResource {
   private static final String V1 = "12.2.1.3.0";
   private static final String LATEST = "latest";

   @GET
   @Produces({"application/json"})
   public RestCollectionResponseBody getVersions() throws Exception {
      RestCollectionResponseBody rb = this.restCollectionResponseBody(Version.class, "items.version", (URI)null);
      rb.addSelfResourceLinks(this.getSubUri(new String[0]));
      rb.addResourceLink("current", this.getSubUri(new String[]{"12.2.1.3.0"}));
      this.addLatestVersion(rb, "12.2.1.3.0", true, "active");
      return rb;
   }

   @Path("12.2.1.3.0")
   public Object getV1Resource() throws Exception {
      return this.getSubResource(NMVersionResource.class);
   }

   @Path("latest")
   public Object getLatestResource() throws Exception {
      return this.getV1Resource();
   }

   private void addLatestVersion(RestCollectionResponseBody rb, String version, boolean isLatest, String lifecycle) throws Exception {
      JSONObject item = new JSONObject();
      item.put("version", version);
      item.put("isLatest", isLatest);
      item.put("lifecycle", lifecycle);
      Version model = (Version)this.getTypedModel(Version.class, item);
      rb.addItem(model, version, this.getSubUri(new String[]{version}));
   }
}
