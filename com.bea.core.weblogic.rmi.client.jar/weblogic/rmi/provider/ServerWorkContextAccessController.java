package weblogic.rmi.provider;

import java.security.AccessController;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.WorkContextResource;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.utils.StringUtils;
import weblogic.workarea.WorkContextMap;
import weblogic.workarea.spi.WorkContextAccessController;

public class ServerWorkContextAccessController extends WorkContextAccessController {
   private static final String[] ACTION_MAP = new String[]{"create", "read", "modify", "delete", "callback"};
   private static final String CONTEXT_DELIMITER = ".";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());

   public static void initialize() {
      new ServerWorkContextAccessController();
   }

   protected ServerWorkContextAccessController() {
   }

   protected boolean checkAccess(String key, int type) {
      if (type >= ACTION_MAP.length) {
         throw new IllegalArgumentException("Invalid action: " + type);
      } else if (!KernelStatus.isServer()) {
         return true;
      } else {
         AbstractSubject authUser = SubjectManager.getSubjectManager().getCurrentSubject(kernelId);
         if (authUser == kernelId) {
            return true;
         } else {
            String[] args = StringUtils.splitCompletely(key, ".");
            WorkContextResource wcr = new WorkContextResource(args, ACTION_MAP[type]);
            AuthorizationManager authorizationManager = (AuthorizationManager)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.AUTHORIZE);
            return authorizationManager.isAccessAllowed((AuthenticatedSubject)authUser, wcr, new ResourceIDDContextWrapper());
         }
      }
   }

   protected WorkContextMap getPriviledgedWrapper(WorkContextMap map) {
      return new PriviledgedWorkContextMap(map);
   }
}
