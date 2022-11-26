package weblogic.management.configuration;

import java.security.AccessController;
import java.util.Arrays;
import weblogic.management.WebLogicMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class DomainTargetHelper {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static TargetMBean[] getDefaultTargets(TargetInfoMBean bean, Object defTargets) {
      if (bean instanceof DomainTargetedMBean) {
         ServerMBean server = getLocalServer(bean);
         if (server != null) {
            return new TargetMBean[]{server};
         }
      }

      return (TargetMBean[])((TargetMBean[])defTargets);
   }

   private static ServerMBean getLocalServer(TargetInfoMBean bean) {
      DomainMBean domain = getDomain(bean);
      if (domain == null) {
         return null;
      } else {
         String name = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         return domain.lookupServer(name);
      }
   }

   private static DomainMBean getDomain(TargetInfoMBean bean) {
      Object curBean;
      for(curBean = bean; curBean != null && !(curBean instanceof DomainMBean); curBean = ((WebLogicMBean)curBean).getParent()) {
      }

      return (DomainMBean)curBean;
   }

   public static void validateTargets(TargetInfoMBean bean) throws IllegalArgumentException {
      if (bean instanceof DomainTargetedMBean) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotModifyDomainTarget(bean.getName()));
      }
   }

   public static void validateTargets(TargetInfoMBean bean, TargetMBean[] params) throws IllegalArgumentException {
      if (bean instanceof DomainTargetedMBean) {
         TargetMBean[] targets = bean.getTargets();
         int _oldlen = targets == null ? 0 : targets.length;
         int _newlen = params == null ? 0 : params.length;
         if (_oldlen != _newlen || !Arrays.equals(targets, params)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotModifyDomainTarget(bean.getName()));
         }
      }

   }
}
