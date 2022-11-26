package weblogic.management.rest.lib.bean.resources;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.rest.lib.bean.utils.CustomResourceType;
import weblogic.management.rest.lib.bean.utils.PathUtils;

public abstract class AbstractCustomResource extends BaseBeanResource {
   private Object parentResource;
   private CustomResourceType customResourceType;
   private String path;

   public void init(Object parentResource, Object bean, CustomResourceType customResourceType, JSONObject query) throws Exception {
      this.init(parentResource, bean, customResourceType, (String)null, query);
   }

   public void init(Object parentResource, Object bean, CustomResourceType customResourceType, String path, JSONObject query) throws Exception {
      super.init(bean, query);
      this.customResourceType = customResourceType;
      this.parentResource = parentResource;
      this.path = path;
   }

   protected CustomResourceType getCustomResourceType() {
      return this.customResourceType;
   }

   protected Object getParentResource() {
      return this.parentResource;
   }

   protected String getPath() {
      return this.path != null ? this.path : this.getCustomResourceType().getName();
   }

   public List getSelfPath() throws Exception {
      List rtn = new ArrayList(this.getParentPath());
      rtn.add(this.getPath());
      return rtn;
   }

   protected List getParentPath() throws Exception {
      return this.getParentResource() instanceof AbstractCustomResource ? ((AbstractCustomResource)this.getParentResource()).getSelfPath() : PathUtils.getBeanPath(this.invocationContext());
   }

   protected Object getSubResource(Class clazz, String subResourceName, JSONObject query) throws Exception {
      Object subResource = this.getSubResource(clazz);
      if (subResource instanceof AbstractCustomResource) {
         ((AbstractCustomResource)subResource).init(this, this.invocationContext().bean(), this.getCustomResourceType(), subResourceName, query);
      }

      return subResource;
   }
}
