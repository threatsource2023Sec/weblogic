package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.PathUtils;

public abstract class AbstractCustomCreateFormResource extends AbstractCustomResource {
   protected String getPropertyName() throws Exception {
      throw new AssertionError("getPropertyName must be implemented");
   }

   protected JSONObject getModel() throws Exception {
      throw new AssertionError("getModel must be implemented");
   }

   protected String getSingularPropertyName() throws Exception {
      String p = this.getPath();
      if (!p.endsWith("CreateForm")) {
         throw new AssertionError("path must end with CreateForm");
      } else {
         return p.substring(0, p.length() - "CreateForm".length());
      }
   }

   protected Response _get() throws Exception {
      RestJsonResponseBody rb = this.restJsonResponseBody(this.getLinksFilter(this.invocationContext().query()));
      rb.addParentResourceLink(PathUtils.getTreeRelativeUri(this.invocationContext(), this.getParentPath()));
      rb.addSelfResourceLinks(PathUtils.getTreeRelativeUri(this.invocationContext(), this.getSelfPath()));
      rb.addResourceLink("create", PathUtils.getTreeRelativeUri(this.invocationContext(), this.getParentPath(), this.getPropertyName()));
      rb.setEntity(this.getModel());
      return this.ok(rb);
   }

   protected boolean supportsPost() throws Exception {
      return false;
   }
}
