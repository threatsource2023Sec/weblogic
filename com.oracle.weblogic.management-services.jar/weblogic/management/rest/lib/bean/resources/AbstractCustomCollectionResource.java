package weblogic.management.rest.lib.bean.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.rest.lib.bean.utils.PathUtils;
import weblogic.management.rest.lib.bean.utils.QueryUtils;

public abstract class AbstractCustomCollectionResource extends AbstractCustomResource implements GetterResource {
   protected String getIdentityPropertyName() throws Exception {
      return "name";
   }

   protected Response _get() throws Exception {
      return this.ok(this.getRB());
   }

   public RestJsonResponseBody getRB() throws Exception {
      RestJsonResponseBody rb = this.restJsonResponseBody(this.getLinksFilter(this.invocationContext().query()));
      List parentPath = this.getParentPath();
      rb.addParentResourceLink(PathUtils.getTreeRelativeUri(this.invocationContext(), parentPath));
      rb.addSelfResourceLinks(PathUtils.getTreeRelativeUri(this.invocationContext(), this.getSelfPath()));
      if (this.supportsPost()) {
         Iterator var3 = this.getCreateFormNames().iterator();

         while(var3.hasNext()) {
            String createFormName = (String)var3.next();
            rb.addResourceLink("create-form", PathUtils.getTreeRelativeUri(this.invocationContext(), parentPath, createFormName));
         }
      }

      List identities = QueryUtils.getIdentities(this.invocationContext().request(), this.getIdentityPropertyName(), this.invocationContext().query());
      if (identities == null) {
         identities = this.getAllIdentities();
      }

      JSONArray children = new JSONArray();
      rb.setEntities(children);
      JSONObject childrenQuery = QueryUtils.getChildrenQuery(this.invocationContext().query());
      Iterator var6 = identities.iterator();

      while(var6.hasNext()) {
         String identity = (String)var6.next();
         Object childResource = this._getSubResource(identity, childrenQuery);
         if (childResource != null && childResource instanceof GetterResource) {
            RestJsonResponseBody childRb = ((GetterResource)childResource).getRB();
            if (childRb != null) {
               children.put(childRb.toJson());
            }
         }
      }

      return rb;
   }

   protected List getAllIdentities() throws Exception {
      throw new AssertionError("getAllIdentities must be implemented");
   }

   protected boolean supportsPost() throws Exception {
      return this.isEditableTree() && this._supportsCreate();
   }

   protected boolean _supportsCreate() throws Exception {
      return false;
   }

   protected Response create(JSONObject entity) throws Exception {
      this.verifyPost();
      return this._create(entity);
   }

   protected Response _create(JSONObject entity) throws Exception {
      throw new AssertionError("_create must be implemented");
   }

   protected List getCreateFormNames() throws Exception {
      List rtn = new ArrayList();
      rtn.add(StringUtil.getSingular(this.getPath()) + "CreateForm");
      return rtn;
   }

   public Object getSubResource(String subResourceName, JSONObject childrenQuery) throws Exception {
      Object subResource = this._getSubResource(subResourceName, childrenQuery);
      if (subResource == null) {
         throw this.notFound(this.formatter().msgNotFound(subResourceName));
      } else {
         return subResource;
      }
   }

   protected Object _getSubResource(String subResourceName, JSONObject childrenQuery) throws Exception {
      throw new AssertionError("_getSubResource(String subResourceName, JSONObject childrenQuery) must be implemented");
   }
}
