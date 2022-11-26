package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.management.j2ee.J2EEDomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class J2EEDomainMBeanImpl extends J2EEManagedObjectMBeanImpl implements J2EEDomainMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String domain;

   public J2EEDomainMBeanImpl(String name) {
      super(name);
   }

   public String[] getservers() {
      try {
         ObjectName objectPattern = new ObjectName(domain + ":j2eeType=" + "J2EEServer" + ",*");
         return this.queryNames(objectPattern);
      } catch (MalformedObjectNameException var2) {
         throw new AssertionError(" Malformed ObjectName" + var2);
      }
   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
   }
}
