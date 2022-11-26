package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.rest.lib.utils.ConfigurationTransaction;

public abstract class AbstractBeanEditor implements ConfigurationTransaction.TransactionContents {
   private RefreshableInvocationContext ic;

   public AbstractBeanEditor(InvocationContext ic) throws Exception {
      this.ic = new RefreshableInvocationContext(ic);
   }

   public boolean doWork(HttpServletRequest request, JSONObject values) throws Exception {
      this.ic.refresh();
      return this._doWork(request, values);
   }

   protected abstract boolean _doWork(HttpServletRequest var1, JSONObject var2) throws Exception;

   protected InvocationContext invocationContext() {
      return this.ic.invocationContext();
   }
}
