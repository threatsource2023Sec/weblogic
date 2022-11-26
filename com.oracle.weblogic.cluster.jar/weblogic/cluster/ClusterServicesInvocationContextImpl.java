package weblogic.cluster;

import java.security.AccessController;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
public class ClusterServicesInvocationContextImpl implements ClusterServicesInvocationContext {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ManagedInvocationContext setInvocationContext(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
         String currentPartitionName = manager.getCurrentComponentInvocationContext().getPartitionName();
         if (currentPartitionName.equalsIgnoreCase(partitionName)) {
            return null;
         } else {
            ComponentInvocationContext cic = manager.createComponentInvocationContext(partitionName);
            return this.setInvocationContext(cic);
         }
      }
   }

   public ManagedInvocationContext setInvocationContext(ComponentInvocationContext cic) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      return manager.setCurrentComponentInvocationContext(cic);
   }

   public ManagedInvocationContext setInvocationContext(String appId, String moduleName) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      ComponentInvocationContext cic = manager.createComponentInvocationContext(appId, moduleName, (String)null);
      return manager.setCurrentComponentInvocationContext(cic);
   }
}
