package weblogic.management.rest.lib.bean.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.AttributeType;
import weblogic.management.rest.lib.bean.utils.BeanConfigurationTransaction;
import weblogic.management.rest.lib.bean.utils.BeanCreator;
import weblogic.management.rest.lib.bean.utils.BeanUtils;
import weblogic.management.rest.lib.bean.utils.CollectionChildBeanCreator;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.InvocationContext;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;
import weblogic.management.rest.lib.bean.utils.PathUtils;
import weblogic.management.rest.lib.bean.utils.QueryUtils;
import weblogic.management.rest.lib.bean.utils.ResourceDef;
import weblogic.management.rest.lib.utils.ConfigurationTransaction;

public abstract class AbstractBeanCollectionResource extends BaseBeanResource implements GetterResource {
   private ContainedBeansType propertyType;

   public void init(Object bean, ContainedBeansType propertyType, JSONObject query) throws Exception {
      super.init(bean, query);
      this.propertyType = propertyType;
   }

   protected ContainedBeansType getPropertyType() {
      return this.propertyType;
   }

   protected Response _get() throws Exception {
      return this.ok(this.getRB());
   }

   public RestJsonResponseBody getRB() throws Exception {
      RestJsonResponseBody rb = this.restJsonResponseBody(this.getLinksFilter(this.invocationContext().query()));
      List path = PathUtils.getBeanPath(this.invocationContext());
      rb.addParentResourceLink(PathUtils.getTreeRelativeUri(this.invocationContext(), path));
      rb.addSelfResourceLinks(PathUtils.getTreeRelativeUri(this.invocationContext(), path, this.getPropertyType().getName()));
      if (this.isEditableTree() && this.hasCreator()) {
         rb.addResourceLink("create-form", PathUtils.getTreeRelativeUri(this.invocationContext(), path, this.getPropertyType().getCreateFormResourceName()));
      }

      List identities = QueryUtils.getIdentities(this.invocationContext().request(), this.getPropertyType().getType(this.invocationContext().request()).getIdentityPropertyType().getName(), this.invocationContext().query());
      Object childBean;
      if (identities == null) {
         identities = new ArrayList();
         Object[] childBeans = (Object[])((Object[])BeanUtils.getBeanProperty(this.invocationContext(), (AttributeType)this.getPropertyType()));
         if (childBeans != null) {
            Object[] var5 = childBeans;
            int var6 = childBeans.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               childBean = var5[var7];
               ((List)identities).add((String)BeanUtils.getIdentity(this.invocationContext().clone(childBean)));
            }
         }
      }

      JSONArray children = new JSONArray();
      rb.setEntities(children);
      JSONObject childrenQuery = QueryUtils.getChildrenQuery(this.invocationContext().query());
      Iterator var12 = ((List)identities).iterator();

      while(var12.hasNext()) {
         String identity = (String)var12.next();
         childBean = this._getSubResource(identity, childrenQuery);
         if (childBean != null && childBean instanceof GetterResource) {
            RestJsonResponseBody childRb = ((GetterResource)childBean).getRB();
            if (childRb != null) {
               children.put(childRb.toJson());
            }
         }
      }

      return rb;
   }

   public Response create(JSONObject entity) throws Exception {
      this.verifyPost();
      return this._create(entity);
   }

   protected Response _create(JSONObject entity) throws Exception {
      return this._create(entity, new CollectionChildBeanCreator(this.invocationContext(), this.getPropertyType()));
   }

   protected Response _create(JSONObject entity, BeanCreator contents) throws Exception {
      String name = (String)BeanUtils.getIdentity(this.invocationContext().request(), entity, this.getPropertyType().getType(this.invocationContext().request()));
      ConfigurationTransaction.Result result = BeanConfigurationTransaction.doTransaction(this.invocationContext(), entity, contents, this.isEditTree(), this.invocationContext().saveChanges());
      if (result.succeeded()) {
         if (name == null) {
            name = (String)BeanUtils.getIdentity(this.invocationContext().clone(contents.getNewBean()));
         }

         return this.created(this.responseBody(), this.getSubUri(new String[]{name}));
      } else if (result.getException() == null) {
         if (name != null) {
            throw this.badRequest(this.formatter().msgAlreadyExists(name));
         } else {
            throw this.badRequest(this.formatter().msgAlreadyExistsAnonymous());
         }
      } else {
         return this.badRequest(result.report(this.responseBody()));
      }
   }

   protected boolean supportsPost() throws Exception {
      return this.isEditableTree() && this.hasCreator();
   }

   private boolean hasCreator() throws Exception {
      MethodType mt = this.getPropertyType().getCreator(this.invocationContext().request());
      return mt != null && PartitionUtils.isVisible((InvocationContext)this.invocationContext(), mt);
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
      Class clazz = ResourceDef.getResourceClass(this.getPropertyType().getChildResourceDef(this.getBeanTree()));
      if (clazz == null) {
         return null;
      } else {
         Object subResourceBean = PathUtils.findChild(this.invocationContext(), subResourceName, this.getPropertyType());
         if (subResourceBean == null) {
            return null;
         } else if (!PartitionUtils.isVisible(this.invocationContext().clone(subResourceBean))) {
            return null;
         } else {
            Object subResource = this.getSubResource(clazz);
            if (subResource instanceof AbstractCollectionChildBeanResource) {
               ((AbstractCollectionChildBeanResource)subResource).init(this.invocationContext().bean(), subResourceBean, this.getPropertyType(), childrenQuery);
            }

            return subResource;
         }
      }
   }
}
