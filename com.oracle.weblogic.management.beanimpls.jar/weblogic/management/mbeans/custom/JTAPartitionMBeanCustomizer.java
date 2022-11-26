package weblogic.management.mbeans.custom;

import java.security.AccessController;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JTAPartitionMBeanCustomizer extends ConfigurationMBeanCustomizer {
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JTAPartitionMBeanCustomizer(ConfigurationMBeanCustomized base) {
      super(base);
   }
}
