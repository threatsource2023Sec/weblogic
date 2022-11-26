package weblogic.entitlement.rules;

import java.security.AccessController;
import javax.security.auth.Subject;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.spi.Resource;

public class InSecureMode extends BasePredicate {
   private static final String VERSION = "1.0";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public InSecureMode() {
      super("InSecureModeName", "InSecureModeDescription");
   }

   public boolean evaluate(Subject subject, Resource resource, ContextHandler context) {
      return !ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getSecureMode().isSecureModeEnabled();
   }

   public String getVersion() {
      return "1.0";
   }
}
