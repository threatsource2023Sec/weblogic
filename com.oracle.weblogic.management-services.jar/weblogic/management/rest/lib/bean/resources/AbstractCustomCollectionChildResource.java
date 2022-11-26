package weblogic.management.rest.lib.bean.resources;

import java.util.Iterator;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.utils.JsonFilter;
import weblogic.management.rest.lib.bean.utils.PathUtils;
import weblogic.management.rest.lib.bean.utils.QueryUtils;

public abstract class AbstractCustomCollectionChildResource extends AbstractCustomResource implements GetterResource {
   protected Response _get() throws Exception {
      return this.ok(this.getRB());
   }

   public RestJsonResponseBody getRB() throws Exception {
      RestJsonResponseBody rb = this.restJsonResponseBody(this.getLinksFilter(this.invocationContext().query()));
      rb.addParentResourceLink(PathUtils.getTreeRelativeUri(this.invocationContext(), this.getParentPath()));
      rb.addSelfResourceLinks(PathUtils.getTreeRelativeUri(this.invocationContext(), this.getSelfPath()));
      this.addLinks(rb);
      JsonFilter.Scope filter = QueryUtils.getPropertiesFilter(this.invocationContext().request(), this.invocationContext().query()).newScope();
      rb.setEntity(this.getModel(filter));
      Map children = QueryUtils.getChildren(this.invocationContext().request(), this.invocationContext().query());
      if (children != null) {
         JSONObject model = rb.getEntity();
         Iterator var5 = children.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry e = (Map.Entry)var5.next();
            String childPropertyName = (String)e.getKey();
            JSONObject childQuery = (JSONObject)e.getValue();
            Object childResource = this._getSubResource(childPropertyName, childQuery);
            if (childResource != null && childResource instanceof GetterResource) {
               RestJsonResponseBody childRb = ((GetterResource)childResource).getRB();
               Object child = childRb != null ? childRb.toJson() : JSONObject.NULL;
               model.put(childPropertyName, child);
            }
         }
      }

      return rb;
   }

   protected JSONObject getModel(JsonFilter.Scope filter) throws Exception {
      throw new AssertionError("getModel must be implemented");
   }

   protected void addLinks(RestJsonResponseBody rb) throws Exception {
   }

   protected boolean supportsPost() throws Exception {
      return this.isEditableTree() && this._supportsUpdate();
   }

   protected boolean _supportsUpdate() throws Exception {
      return false;
   }

   protected Response update(JSONObject entity) throws Exception {
      this.verifyPost();
      return this._update(entity);
   }

   protected Response _update(JSONObject entity) throws Exception {
      throw new AssertionError("_update must be implemented");
   }

   protected boolean supportsDelete() throws Exception {
      return this.isEditableTree() && this._supportsDelete();
   }

   protected boolean _supportsDelete() throws Exception {
      return false;
   }

   protected Response _delete() throws Exception {
      throw new AssertionError("_delete must be implemented");
   }

   protected Object getSubResource(String subResourceName, JSONObject childrenQuery) throws Exception {
      Object subResource = this._getSubResource(subResourceName, childrenQuery);
      if (subResource == null) {
         throw this.notFound(this.formatter().msgNotFound(subResourceName));
      } else {
         return subResource;
      }
   }

   protected Object _getSubResource(String subResourceName, JSONObject childrenQuery) throws Exception {
      return null;
   }
}
