package weblogic.management.rest.lib.bean.resources;

import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import weblogic.management.rest.lib.bean.utils.ActionType;
import weblogic.management.rest.lib.bean.utils.InvokeUtils;
import weblogic.management.rest.lib.bean.utils.TreeUtils;
import weblogic.management.rest.lib.utils.SecurityUtils;

public abstract class AbstractBeanActionResource extends BaseBeanResource {
   private ActionType actionType;

   public void init(Object bean, ActionType actionType, JSONObject query) throws Exception {
      super.init(bean, query);
      this.actionType = actionType;
   }

   protected ActionType getActionType() {
      return this.actionType;
   }

   protected Response act(JSONObject params) throws Exception {
      RestJsonResponseBody rb = this._act(params);
      if (rb == null) {
         throw this.notFound();
      } else {
         return this.acted(rb);
      }
   }

   protected RestJsonResponseBody _act(JSONObject params) throws Exception {
      this.verifyPost();
      if (this.getBean() == null) {
         return null;
      } else if ("domainConfig".equals(TreeUtils.getBeanTree(this.invocationContext())) && SecurityUtils.isSecurityMBean(this.invocationContext().bean()) && TreeUtils.getServerRuntimeBean(this.invocationContext().request()).isRestartRequired()) {
         throw this.badRequest(this.beanFormatter().msgSecurityOperationAdminServerNeedsReboot());
      } else {
         RestJsonResponseBody rb = this.restJsonResponseBody();
         InvokeUtils.act(this.invocationContext(), rb, this.actionType, params);
         return rb;
      }
   }

   protected boolean supportsGet() throws Exception {
      return false;
   }

   protected boolean supportsPost() throws Exception {
      return true;
   }
}
