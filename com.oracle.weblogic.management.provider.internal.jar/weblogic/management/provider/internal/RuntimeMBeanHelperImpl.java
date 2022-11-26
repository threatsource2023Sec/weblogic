package weblogic.management.provider.internal;

import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.provider.RegistrationManager;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanHelper;
import weblogic.management.runtime.ServerRuntimeMBean;

@Service
public class RuntimeMBeanHelperImpl implements RuntimeMBeanHelper {
   @Inject
   private RuntimeAccess runtimeAccess;

   public String getServerName() {
      return this.runtimeAccess.getServerName();
   }

   public RuntimeMBean getDefaultParent() {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext context = manager.getCurrentComponentInvocationContext();
      if (context != null) {
         if (context.isGlobalRuntime()) {
            return this.runtimeAccess.getServerRuntime();
         }

         if (context.getPartitionName() != null) {
            String partitionName = context.getPartitionName();
            RuntimeMBean partitionRuntime = this.runtimeAccess.getServerRuntime().lookupPartitionRuntime(partitionName);
            if (partitionRuntime != null) {
               return partitionRuntime;
            }
         }
      }

      return this.runtimeAccess.getServerRuntime();
   }

   public RegistrationManager getRegistrationManager() {
      return this.runtimeAccess;
   }

   public boolean isParentRequired(RuntimeMBean mbean) {
      return !(mbean instanceof ServerRuntimeMBean) && !(mbean instanceof DomainRuntimeMBean);
   }

   public boolean isParentRequired(String mbeanType) {
      return !mbeanType.equals("ServerRuntime") && !mbeanType.equals("DomainRuntime");
   }
}
