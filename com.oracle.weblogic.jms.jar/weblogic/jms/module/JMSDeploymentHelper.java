package weblogic.jms.module;

import java.security.AccessController;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JMSDeploymentHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String getMemberName(String moduleName, DistributedDestinationMemberBean member) {
      String withoutPrefixName = member.getPhysicalDestinationName();
      return "interop-jms".equals(moduleName) ? withoutPrefixName : moduleName + "!" + withoutPrefixName;
   }

   public static String getMigratableTargetName(String backEndName) {
      JMSServerMBean jmsServer = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupJMSServer(backEndName);
      if (jmsServer == null) {
         return null;
      } else {
         TargetMBean[] targets = jmsServer.getTargets();
         if (targets != null && targets.length != 0) {
            return !(targets[0] instanceof MigratableTargetMBean) ? null : targets[0].getName();
         } else {
            return null;
         }
      }
   }

   public static String getDomainName() {
      return ManagementService.getRuntimeAccess(KERNEL_ID) != null ? ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().getName() : null;
   }
}
