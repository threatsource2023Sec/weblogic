package weblogic.ejb.container.deployer;

import java.util.concurrent.ConcurrentHashMap;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;

@Service
@Interceptor
@ContractsProvided({EJBPartitionManagerInterceptor.class, MethodInterceptor.class})
public class EJBPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
   private static final ConcurrentHashMap partitionStates = new ConcurrentHashMap();

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      setPartitionState(partitionName, State.SHUTDOWN);
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

   public static boolean isPartitionShutdown() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = cic.getPartitionName();
      return partitionName == null ? false : State.SHUTDOWN.equals(getPartitionState(partitionName));
   }

   protected static synchronized PartitionRuntimeMBean.State getPartitionState(String partitionName) {
      return (PartitionRuntimeMBean.State)partitionStates.get(partitionName);
   }
}
