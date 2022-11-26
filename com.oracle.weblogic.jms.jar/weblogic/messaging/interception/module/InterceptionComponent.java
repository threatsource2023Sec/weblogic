package weblogic.messaging.interception.module;

import java.security.AccessController;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.ComponentRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.runtime.InterceptionComponentRuntimeMBean;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class InterceptionComponent extends ComponentRuntimeMBeanImpl implements InterceptionComponentRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public InterceptionComponent(String key, ApplicationContextInternal ac) throws ManagementException {
      super(key, key, ac.getRuntime(), false);
   }

   public void begin() throws ManagementException {
      PrivilegedActionUtilities.register(this, kernelId);
   }

   public void end() throws ManagementException {
      super.unregister();
   }
}
