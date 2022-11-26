package weblogic.servlet.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.PartitionUtils;
import weblogic.servlet.internal.session.GracefulShutdownHelper;

@Service
@Interceptor
@ContractsProvided({WebAppPartitionManagerInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(WebAppShutdownService.class)
public class WebAppPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
   private static Map partitionStates = new HashMap();

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      Set rgs = this.getAffectedRGs(methodInvocation);
      setPartitionState(partitionName, State.SUSPENDING);
      this.setResourceGroupStates(rgs, RGState.SUSPENDING);
      if (!ignoreSessions) {
         GracefulShutdownHelper.waitForPendingSessions(waitForAllSessions, partitionName, rgs);
      }

      setPartitionState(partitionName, State.SHUTDOWN);
      this.setResourceGroupStates(rgs, RGState.SHUTDOWN);
      methodInvocation.proceed();
   }

   protected boolean shouldDoShutdown(MethodInvocation methodInvocation) {
      return true;
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      setPartitionState(partitionName, State.SHUTDOWN);
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      setPartitionState(partitionName, State.RUNNING);
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      setPartitionState(partitionName, State.RUNNING);
      methodInvocation.proceed();
   }

   protected static void setPartitionState(String partitionName, PartitionRuntimeMBean.State state) {
      partitionStates.put(partitionName, state);
   }

   protected static PartitionRuntimeMBean.State getPartitionState(String partitionName) {
      return (PartitionRuntimeMBean.State)partitionStates.get(partitionName);
   }

   public static boolean isPartitionSuspending() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = cic.getPartitionName();
      return partitionName == null ? false : State.SUSPENDING.equals(getPartitionState(partitionName));
   }

   public static boolean isPartitionShutdown() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = cic.getPartitionName();
      return partitionName == null ? false : State.SHUTDOWN.equals(getPartitionState(partitionName));
   }

   private void setResourceGroupStates(Set resourceGroups, ResourceGroupLifecycleOperations.RGState state) {
      if (resourceGroups != null) {
         Iterator var3 = resourceGroups.iterator();

         while(var3.hasNext()) {
            String resourceGroup = (String)var3.next();
            WebAppResourceGroupManagerInterceptor.setResourceGroupState(resourceGroup, state);
         }
      }

   }

   private Set getAffectedRGs(MethodInvocation methodInvocation) throws ResourceGroupLifecycleException {
      Set allResourceGroups = PartitionUtils.getRGsAffectedByPartitionOperation(this.getPartition(methodInvocation));
      Set nonAdminRGs = PartitionUtils.getNonAdminRGNames(allResourceGroups);
      return nonAdminRGs.size() > 0 ? nonAdminRGs : Collections.EMPTY_SET;
   }
}
