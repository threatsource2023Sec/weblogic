package weblogic.jms.module;

import java.security.AccessController;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.ComponentRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSComponentRuntimeMBean;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.common.PartitionNameUtils;

public class JMSComponent extends ComponentRuntimeMBeanImpl implements JMSComponentRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Object registeredLock = new Object();
   private boolean registered;
   private int deploymentState;

   public JMSComponent(String key, String moduleId, ApplicationContextInternal ac) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(key), moduleId, ac.getRuntime(), false);
   }

   public void open() throws ManagementException {
      synchronized(this.registeredLock) {
         if (!this.registered) {
            PrivilegedActionUtilities.register(this, kernelId);
            this.registered = true;
         }
      }
   }

   public void close() throws ManagementException {
      synchronized(this.registeredLock) {
         if (this.registered) {
            PrivilegedActionUtilities.unregister(this, kernelId);
            this.registered = false;
         }
      }
   }
}
