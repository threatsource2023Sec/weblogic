package weblogic.management.rest.lib.bean.resources;

import org.codehaus.jettison.json.JSONObject;
import weblogic.management.rest.lib.bean.utils.InvocationContext;
import weblogic.management.rest.lib.bean.utils.TreeUtils;

public abstract class BaseBeanResource extends BaseResource {
   private InvocationContext ic;

   protected void init(Object bean, JSONObject query) throws Exception {
      this.ic = new InvocationContext(this.getUriInfo(), this.getRequest(), bean, query);
   }

   protected Object getBean() {
      return this.invocationContext().bean();
   }

   protected InvocationContext invocationContext() {
      return this.ic;
   }

   protected boolean supportsPost() throws Exception {
      return this.isEditableTree();
   }

   protected String getBeanTree() throws Exception {
      return TreeUtils.getBeanTree(this.invocationContext());
   }

   protected boolean isEditTree() throws Exception {
      return TreeUtils.isEditTree(this.invocationContext());
   }

   protected boolean isEditableTree() throws Exception {
      return TreeUtils.isEditableTree(this.invocationContext());
   }
}
