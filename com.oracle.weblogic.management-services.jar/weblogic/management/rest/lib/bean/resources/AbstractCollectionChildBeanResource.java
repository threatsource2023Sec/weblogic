package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.rest.lib.bean.utils.CollectionChildBeanDeleter;
import weblogic.management.rest.lib.bean.utils.ContainedBeansType;
import weblogic.management.rest.lib.bean.utils.InvocationContext;
import weblogic.management.rest.lib.bean.utils.MethodType;
import weblogic.management.rest.lib.bean.utils.PartitionUtils;

public abstract class AbstractCollectionChildBeanResource extends AbstractBeanResource {
   private ContainedBeansType propertyType;

   public void init(Object parentBean, Object childBean, ContainedBeansType propertyType, JSONObject childQuery) throws Exception {
      super.init(childBean, childQuery);
      this.propertyType = propertyType;
   }

   protected Response _delete() throws Exception {
      return this._delete(new CollectionChildBeanDeleter(this.invocationContext(), this.getPropertyType()));
   }

   protected boolean supportsDelete() throws Exception {
      return this.isEditableTree() && this.hasDestroyer();
   }

   private boolean hasDestroyer() throws Exception {
      MethodType mt = this.getPropertyType().getDestroyer(this.invocationContext().request());
      return mt != null && PartitionUtils.isVisible((InvocationContext)this.invocationContext(), mt);
   }

   protected ContainedBeansType getPropertyType() {
      return this.propertyType;
   }
}
