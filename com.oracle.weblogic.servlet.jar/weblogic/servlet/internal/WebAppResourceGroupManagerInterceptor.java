package weblogic.servlet.internal;

import java.util.HashMap;
import java.util.Map;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationContextInternal;
import weblogic.management.configuration.util.ResourceGroupManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.servlet.internal.session.GracefulShutdownHelper;

@Service
@Interceptor
@ContractsProvided({WebAppResourceGroupManagerInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(WebAppShutdownService.class)
public class WebAppResourceGroupManagerInterceptor extends ResourceGroupManagerInterceptorAdapter {
   private static Map resourceGroupStates = new HashMap();

   public void startResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      setResourceGroupState(ResourceGroup, RGState.RUNNING);
      methodInvocation.proceed();
   }

   public void startResourceGroupInAdmin(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      setResourceGroupState(ResourceGroup, RGState.RUNNING);
      methodInvocation.proceed();
   }

   public void shutdownResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      setResourceGroupState(ResourceGroup, RGState.SUSPENDING);
      if (!ignoreSessions) {
         GracefulShutdownHelper.waitForPendingSessions(waitForAllSessions, partitionName, ResourceGroup);
      }

      setResourceGroupState(ResourceGroup, RGState.SHUTDOWN);
      methodInvocation.proceed();
   }

   public void forceShutdownResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      setResourceGroupState(ResourceGroup, RGState.SHUTDOWN);
      methodInvocation.proceed();
   }

   protected static void setResourceGroupState(String ResourceGroup, ResourceGroupLifecycleOperations.RGState state) {
      resourceGroupStates.put(ResourceGroup, state);
   }

   protected static ResourceGroupLifecycleOperations.RGState getResourceGroupState(String ResourceGroup) {
      return (ResourceGroupLifecycleOperations.RGState)resourceGroupStates.get(ResourceGroup);
   }

   protected static boolean isResourceGroupSuspending(ApplicationContextInternal appCtx) {
      return appCtx != null && appCtx.getResourceGroupMBean() != null && appCtx.getResourceGroupMBean().getName() != null ? RGState.SUSPENDING.equals(getResourceGroupState(appCtx.getResourceGroupMBean().getName())) : false;
   }

   protected static boolean isResourceGroupShutdown(ApplicationContextInternal appCtx) {
      return appCtx != null && appCtx.getResourceGroupMBean() != null && appCtx.getResourceGroupMBean().getName() != null ? RGState.SHUTDOWN.equals(getResourceGroupState(appCtx.getResourceGroupMBean().getName())) : false;
   }
}
