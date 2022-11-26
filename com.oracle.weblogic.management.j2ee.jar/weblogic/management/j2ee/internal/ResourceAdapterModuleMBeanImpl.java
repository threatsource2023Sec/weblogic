package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.management.j2ee.ResourceAdapterModuleMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class ResourceAdapterModuleMBeanImpl extends J2EEModuleMBeanImpl implements ResourceAdapterModuleMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String domain;

   public ResourceAdapterModuleMBeanImpl(String name, String serverName, String jvm, ApplicationInfo info) {
      super(name, serverName, jvm, info);
   }

   public String[] getresourceAdapters() {
      String sObjectName = domain + ":j2eeType=" + "ResourceAdapter" + "," + "J2EEServer" + "=" + JMOTypesHelper.getKeyValue(this.name, "J2EEServer") + "," + "ResourceAdapterModule" + "=" + JMOTypesHelper.getKeyValue(this.name, "name") + ",*";

      try {
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
