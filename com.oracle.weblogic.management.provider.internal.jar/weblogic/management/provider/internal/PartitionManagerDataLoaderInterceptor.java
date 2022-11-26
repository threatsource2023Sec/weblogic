package weblogic.management.provider.internal;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.HK2Invocation;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorDataLoader;
import weblogic.management.configuration.util.PartitionManagerPartitionAPI;
import weblogic.management.configuration.util.PartitionManagerResourceGroupAPI;
import weblogic.management.configuration.util.Setup;
import weblogic.management.configuration.util.Teardown;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Interceptor
@Setup
@Teardown
@Rank(Integer.MAX_VALUE)
@ContractsProvided({PartitionManagerDataLoaderInterceptor.class, MethodInterceptor.class, PartitionManagerInterceptorDataLoader.class})
public class PartitionManagerDataLoaderInterceptor implements MethodInterceptor, PartitionManagerInterceptorDataLoader {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   private PartitionServerServiceInterceptorArranger arranger;

   public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
      if (!(methodInvocation instanceof HK2Invocation)) {
         throw new IllegalArgumentException("Expected HK2MethodInvocation, found " + methodInvocation.getClass().getName());
      } else {
         this.announceAnyErrors();
         CallContext callContext = new CallContext(methodInvocation);
         this.loadUserData(methodInvocation, callContext);
         ComponentInvocationContextManager cicMgr = ComponentInvocationContextManager.getInstance(kernelId);
         ComponentInvocationContext cic = cicMgr.createComponentInvocationContext(callContext.partitionName);
         return ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public Object call() throws Exception {
               try {
                  return methodInvocation.proceed();
               } catch (Throwable var2) {
                  if (var2 instanceof Exception) {
                     throw (Exception)var2;
                  } else {
                     throw new RuntimeException(var2);
                  }
               }
            }
         });
      }
   }

   public void loadUserData(MethodInvocation methodInvocation) throws Exception {
      this.loadUserData(methodInvocation, new CallContext(methodInvocation));
   }

   public void loadUserData(MethodInvocation methodInvocation, CallContext callContext) throws Exception {
      HK2Invocation hk2Invocation = (HK2Invocation)methodInvocation;
      if (!this.hasDataBeenSet(hk2Invocation)) {
         Method m = methodInvocation.getMethod();
         Object[] args = methodInvocation.getArguments();
         this.dumpCall(methodInvocation, hk2Invocation, m, args, callContext);
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         this.setUserDataFromServer(hk2Invocation, ManagementService.getRuntimeAccess(kernelId).getServerRuntime());
         this.setUserDataFromEnv(hk2Invocation);
         if (callContext.partitionName != null) {
            PartitionMBean partition = domain.lookupPartition(callContext.partitionName);
            if (partition == null) {
               throw new IllegalArgumentException("Partition " + callContext.partitionName + " not found");
            }

            if (callContext.resourceGroupName != null) {
               ResourceGroupMBean resourceGroup = partition.lookupResourceGroup(callContext.resourceGroupName);
               if (resourceGroup == null) {
                  throw new IllegalArgumentException("Partition " + callContext.partitionName + " does not contain requested resource group " + callContext.resourceGroupName);
               }

               this.setUserDataFromResourceGroup(hk2Invocation, resourceGroup);
            } else {
               this.setUserDataFromPartition(hk2Invocation, partition);
            }
         } else {
            ResourceGroupMBean domainLevelResourceGroup = domain.lookupResourceGroup(callContext.resourceGroupName);
            if (domainLevelResourceGroup == null) {
               throw new IllegalArgumentException("Domain-level resource group " + callContext.resourceGroupName + " not found");
            }

            this.setUserDataFromResourceGroup(hk2Invocation, domainLevelResourceGroup);
         }

         this.markDataAsSet(hk2Invocation);
      }
   }

   private boolean hasDataBeenSet(HK2Invocation hk2Invocation) {
      Object obj = hk2Invocation.getUserData("__dataHasBeenSet");
      return obj != null && obj instanceof Boolean ? (Boolean)obj : false;
   }

   private void markDataAsSet(HK2Invocation hk2Invocation) {
      hk2Invocation.setUserData("__dataHasBeenSet", Boolean.TRUE);
   }

   private void setUserDataFromServer(HK2Invocation hk2Invocation, ServerRuntimeMBean serverRuntime) {
      hk2Invocation.setUserData("__isAdminServer", serverRuntime.isAdminServer());
      hk2Invocation.setUserData("__serverName", serverRuntime.getName());
   }

   private void setUserDataFromEnv(HK2Invocation hk2Invocation) {
      if (PartitionLifecycleDebugger.isDebugEnabled()) {
         hk2Invocation.setUserData("__debugLog", PartitionLifecycleDebugger.getInstance());
      }

   }

   private void setUserDataFromResourceGroup(HK2Invocation hk2Invocation, ResourceGroupMBean rg) {
      hk2Invocation.setUserData("__resourceGroupMBean", rg);
      hk2Invocation.setUserData("__basicDeployments", Arrays.copyOf(rg.getBasicDeployments(), rg.getBasicDeployments().length));
      hk2Invocation.setUserData("__deployments", Arrays.copyOf(rg.getDeployments(), rg.getDeployments().length));
      if (rg.getParent() instanceof PartitionMBean) {
         this.setUserDataDescribingPartition(hk2Invocation, (PartitionMBean)rg.getParent());
      }

   }

   private void setUserDataFromPartition(HK2Invocation hk2Invocation, PartitionMBean partition) {
      this.setUserDataDescribingPartition(hk2Invocation, partition);
      List basicDeployments = new ArrayList();
      List deployments = new ArrayList();
      hk2Invocation.setUserData("__basicDeployments", basicDeployments.toArray(new BasicDeploymentMBean[basicDeployments.size()]));
      hk2Invocation.setUserData("__deployments", deployments.toArray(new DeploymentMBean[deployments.size()]));
   }

   private void setUserDataDescribingPartition(HK2Invocation hk2Invocation, PartitionMBean partition) {
      hk2Invocation.setUserData("__partitionMBean", partition);
      PartitionRuntimeMBean partitionRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partition.getName());
      if (partitionRuntime != null) {
         hk2Invocation.setUserData("__partitionRuntimeMBean", partitionRuntime);
      }

      hk2Invocation.setUserData("__partitionRuntimeAtLeastBooted", this.isPartitionAtLeastBooted(partitionRuntime));
   }

   private boolean isPartitionAtLeastBooted(PartitionRuntimeMBean pRT) {
      if (pRT != null && pRT.getPrevInternalState() != State.UNKNOWN) {
         return pRT.getPrevInternalState() != State.SHUTDOWN || pRT.getPrevInternalSubState() != State.HALTED;
      } else {
         return false;
      }
   }

   private void dumpCall(MethodInvocation mi, HK2Invocation hk2Inv, Method m, Object[] args, CallContext callContext) {
      if (PartitionLifecycleDebugger.isDebugEnabled()) {
         PartitionLifecycleDebugger.debug("PartitionManagerService." + m.getName() + " invoked with arguments" + System.lineSeparator());

         for(int i = 0; i < m.getParameterTypes().length; ++i) {
            Class type = m.getParameterTypes()[i];
            PartitionLifecycleDebugger.debug("  " + type.getName() + " = " + (args[i] != null ? args[i].toString() : "no args to display"));
         }

      }
   }

   private void addAll(List list, ConfigurationMBean[] beans) {
      ConfigurationMBean[] var3 = beans;
      int var4 = beans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ConfigurationMBean bean = var3[var5];
         list.add(bean);
      }

   }

   private Object getArg(String argName, Object[] args, int index, Class tClass) {
      if (args.length <= index) {
         throw new IllegalArgumentException("Expected " + argName + " of type " + tClass.getName() + " at argument index " + index + " but only " + args.length + " arguments were present");
      } else if (args[index] == null) {
         return null;
      } else if (!tClass.isAssignableFrom(args[index].getClass())) {
         throw new IllegalArgumentException("Expected " + argName + " of type " + tClass.getName() + " but actual argument was of type " + args[index].getClass().getName());
      } else {
         return args[index];
      }
   }

   private void announceAnyErrors() {
      if (!this.arranger.getErrors().getErrors().isEmpty()) {
         throw this.arranger.getErrors();
      }
   }

   private class CallContext {
      private String partitionName;
      private String resourceGroupName;

      private CallContext(MethodInvocation mi) {
         this.partitionName = null;
         this.resourceGroupName = null;
         Method m = mi.getMethod();
         boolean hasPartitionAnno = m.getAnnotation(PartitionManagerPartitionAPI.class) != null;
         boolean hasResourceGroupAnno = m.getAnnotation(PartitionManagerResourceGroupAPI.class) != null;
         Object[] args = mi.getArguments();
         this.partitionName = (String)PartitionManagerDataLoaderInterceptor.this.getArg("partitionName", args, 0, String.class);
         this.resourceGroupName = null;
         if (hasResourceGroupAnno) {
            this.resourceGroupName = (String)PartitionManagerDataLoaderInterceptor.this.getArg("resourceGroupName", args, 1, String.class);
         }

         if (hasPartitionAnno && this.partitionName == null) {
            throw new IllegalArgumentException("Required partition name is null");
         } else if (hasResourceGroupAnno && this.resourceGroupName == null) {
            throw new IllegalArgumentException("Required resource group name is null");
         }
      }

      // $FF: synthetic method
      CallContext(MethodInvocation x1, Object x2) {
         this(x1);
      }
   }
}
