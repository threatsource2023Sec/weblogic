package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONArray;

public class RefreshableInvocationContext {
   private InvocationContext ic;
   private String beanTree;
   private JSONArray path;

   public RefreshableInvocationContext(InvocationContext ic) throws Exception {
      this.ic = ic;
      Object p = PathUtils.getJsonBeanPath(this.invocationContext());
      if (p != null) {
         this.path = (JSONArray)p;
         this.beanTree = TreeUtils.getBeanTree(this.invocationContext());
      }

   }

   public InvocationContext refresh() throws Exception {
      if (this.path != null) {
         this.ic = this.invocationContext().clone(PathUtils.findBean(this.invocationContext(), this.beanTree, this.path));
      }

      return this.invocationContext();
   }

   public InvocationContext invocationContext() {
      return this.ic;
   }
}
