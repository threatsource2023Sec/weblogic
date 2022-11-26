package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.management.j2ee.WebModuleMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class WebModuleMBeanImpl extends J2EEModuleMBeanImpl implements WebModuleMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String domain;

   public WebModuleMBeanImpl(String name, String serverName, String jvm, ApplicationInfo info) {
      super(name, serverName, jvm, info);
   }

   public String[] getservlets() {
      try {
         String sObjectName = domain + ":j2eeType=" + "Servlet" + "," + "J2EEServer" + "=" + JMOTypesHelper.getKeyValue(this.name, "J2EEServer") + "," + "WebModule" + "=" + JMOTypesHelper.getKeyValue(this.name, "name") + ",*";
         ObjectName pattern = new ObjectName(sObjectName);
         return this.queryNames(pattern);
      } catch (MalformedObjectNameException var3) {
         throw new AssertionError(" Malformed ObjectName" + var3);
      }
   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
   }
}
