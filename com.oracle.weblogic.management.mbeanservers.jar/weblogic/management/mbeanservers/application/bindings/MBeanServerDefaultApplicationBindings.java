package weblogic.management.mbeanservers.application.bindings;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServer;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.env.bindings.DefaultBindings;
import weblogic.jndi.internal.NonListableRef;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
public class MBeanServerDefaultApplicationBindings implements DefaultBindings {
   private String DOMAIN_RUNTIME_MBEAN_BINDING = "java:comp/env/jmx/domainRuntime";
   private String RUNTIME_MBEAN_BINDING = "java:comp/env/jmx/runtime";
   private String EDIT_MBEAN_BINDING = "java:comp/env/jmx/edit";
   private Map defaultBindings = new HashMap();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MBeanServerDefaultApplicationBindings() {
      MBeanServer mbeanServer = ManagementService.getDomainRuntimeMBeanServer(kernelId);
      if (mbeanServer != null) {
         this.defaultBindings.put(this.DOMAIN_RUNTIME_MBEAN_BINDING, new NonListableRef(mbeanServer));
      }

      mbeanServer = ManagementService.getRuntimeMBeanServer(kernelId);
      if (mbeanServer != null) {
         this.defaultBindings.put(this.RUNTIME_MBEAN_BINDING, new NonListableRef(mbeanServer));
      }

      mbeanServer = ManagementService.getEditMBeanServer(kernelId);
      if (mbeanServer != null) {
         this.defaultBindings.put(this.EDIT_MBEAN_BINDING, new NonListableRef(mbeanServer));
      }

   }

   public Map getDefaultBindings() {
      return this.defaultBindings;
   }
}
