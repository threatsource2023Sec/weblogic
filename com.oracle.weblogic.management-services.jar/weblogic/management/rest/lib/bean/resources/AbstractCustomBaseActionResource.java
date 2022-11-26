package weblogic.management.rest.lib.bean.resources;

import weblogic.management.rest.lib.bean.utils.TreeUtils;
import weblogic.management.rest.lib.utils.SecurityUtils;

public abstract class AbstractCustomBaseActionResource extends AbstractCustomResource {
   protected void verifyPost() throws Exception {
      super.verifyPost();
      if ("domainConfig".equals(TreeUtils.getBeanTree(this.invocationContext())) && SecurityUtils.isSecurityMBean(this.invocationContext().bean()) && TreeUtils.getServerRuntimeBean(this.invocationContext().request()).isRestartRequired()) {
         throw this.badRequest(this.beanFormatter().msgSecurityOperationAdminServerNeedsReboot());
      }
   }

   protected boolean supportsGet() throws Exception {
      return false;
   }

   protected boolean supportsPost() throws Exception {
      return true;
   }
}
