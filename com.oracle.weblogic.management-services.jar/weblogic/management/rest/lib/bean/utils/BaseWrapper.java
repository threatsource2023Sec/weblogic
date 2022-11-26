package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;

public class BaseWrapper {
   private InvocationContext ic;

   protected InvocationContext invocationContext() {
      return this.ic;
   }

   protected BaseWrapper(InvocationContext ic) throws Exception {
      this.ic = ic;
   }

   protected Object act(String action) throws Exception {
      return this.act(action, new JSONObject());
   }

   protected Object act(String action, JSONObject jsonParams) throws Exception {
      return InvokeUtils.act(this.invocationContext(), BeanType.getBeanType(this.invocationContext().request(), this.invocationContext().bean()).getActionType(action), jsonParams);
   }

   protected Object getProperty(String property) throws Exception {
      return BeanUtils.getBeanProperty(this.invocationContext(), property);
   }
}
