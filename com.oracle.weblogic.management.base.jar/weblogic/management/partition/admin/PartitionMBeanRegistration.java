package weblogic.management.partition.admin;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.DomainDir;
import weblogic.management.configuration.util.PartitionManagerPartitionAPI;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBus;
import weblogic.management.eventbus.apis.InternalEventBusFactory;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.eventbus.apis.InternalEvent.EventType;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Interceptor
@ContractsProvided({MethodInterceptor.class, PartitionMBeanRegistration.class})
@PartitionManagerPartitionAPI
@ServerServiceInterceptor(PartitionMBeanService.class)
public class PartitionMBeanRegistration implements MethodInterceptor {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      InternalEvent.EventType eventType = this.createEvent(methodInvocation);
      if (eventType == null) {
         return methodInvocation.proceed();
      } else {
         Object[] args = methodInvocation.getArguments();
         String partitionName = (String)args[0];
         if (partitionName != null && partitionName.length() > 0) {
            String path = DomainDir.getConfigDir() + "/partitions/" + partitionName + "/fmwconfig";
            MBeanServer domainRuntimeMBeanServer = ManagementService.getDomainRuntimeMBeanServer(kernelId);
            MBeanServer runtimeMBeanServer = ManagementService.getRuntimeMBeanServer(kernelId);
            InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
            Map eventPayload = new HashMap();
            eventPayload.put("partition_name", partitionName);
            eventPayload.put("partition_fmwconfig_path", path);
            eventPayload.put("domain_runtime_mbean_server", domainRuntimeMBeanServer);
            eventPayload.put("runtime_mbean_server", runtimeMBeanServer);
            InternalEvent partitionRegistration = new InternalEventImpl(eventType, eventPayload);
            internalEventbus.send(partitionRegistration);
            return methodInvocation.proceed();
         } else {
            return methodInvocation.proceed();
         }
      }
   }

   private InternalEvent.EventType createEvent(MethodInvocation methodInvocation) {
      String methodName = methodInvocation.getMethod().getName();
      if (methodName == null) {
         return null;
      } else if (this.isSomeSortOfStart(methodName)) {
         return EventType.MANAGEMENT_PARTITION_MBEAN_REGISTER;
      } else {
         return this.isSomeSortOfStop(methodName) ? EventType.MANAGEMENT_PARTITION_MBEAN_UNREGISTER : null;
      }
   }

   private boolean isSomeSortOfStart(String methodName) {
      return methodName.equals("startPartition") || methodName.equals("startPartitionInAdmin") || methodName.equals("resumePartition");
   }

   private boolean isSomeSortOfStop(String methodName) {
      return methodName.equals("shutdownPartition") || methodName.equals("forceShutdownPartition") || methodName.equals("suspendPartition") || methodName.equals("haltPartition") || methodName.equals("forceSuspendPartition");
   }

   private boolean ok(String s) {
      return s != null && s.length() > 0;
   }
}
