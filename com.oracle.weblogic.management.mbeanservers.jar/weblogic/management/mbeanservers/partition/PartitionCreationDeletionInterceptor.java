package weblogic.management.mbeanservers.partition;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventBus;
import weblogic.management.eventbus.apis.InternalEventBusFactory;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.eventbus.apis.InternalEvent.EventType;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.PartitionInterceptorServiceAPI;

@PartitionInterceptorServiceAPI
@Service
@Interceptor
@ContractsProvided({PartitionCreationDeletionInterceptor.class, MethodInterceptor.class})
public class PartitionCreationDeletionInterceptor implements MethodInterceptor {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String POST_CREATE_METHOD_NAME = "onPostPartitionCreate";
   private static final String PRE_DELETE_METHOD_NAME = "onPreDeletePartition";
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugPartitionJMX");

   public Object invoke(MethodInvocation inv) throws Throwable {
      this.dump(inv);
      Object ret = inv.proceed();
      if (this.isCreate(inv)) {
         this.handleCreate(inv);
      } else if (this.isDelete(inv)) {
         this.handleDelete(inv);
      }

      return ret;
   }

   private void handleCreate(MethodInvocation inv) {
      String partitionName = this.getPartitionName(inv);
      InternalEvent.EventType eventType = EventType.MANAGEMENT_PARTITION_MBEAN_REGISTER;
      this.handleCreateOrDelete(partitionName, eventType);
   }

   private void handleDelete(MethodInvocation inv) {
      String partitionName = this.getPartitionName(inv);
      InternalEvent.EventType eventType = EventType.MANAGEMENT_PARTITION_MBEAN_UNREGISTER;
      this.handleCreateOrDelete(partitionName, eventType);
   }

   private void handleCreateOrDelete(String partitionName, InternalEvent.EventType eventType) {
      String path = DomainDir.getConfigDir() + "/partitions/" + partitionName + "/fmwconfig";
      MBeanServer domainRuntimeMBeanServer = ManagementService.getDomainRuntimeMBeanServer(kernelId);
      MBeanServer runtimeMBeanServer = ManagementService.getRuntimeMBeanServer(kernelId);
      InternalEventBus internalEventbus = InternalEventBusFactory.getInstance();
      Map eventPayload = new HashMap();
      eventPayload.put("partition_name", partitionName);
      eventPayload.put("partition_fmwconfig_path", path);
      eventPayload.put("domain_runtime_mbean_server", domainRuntimeMBeanServer);
      eventPayload.put("runtime_mbean_server", runtimeMBeanServer);
      if (eventType == EventType.MANAGEMENT_PARTITION_MBEAN_REGISTER) {
         eventPayload.put("create_partition", Boolean.TRUE);
      } else if (eventType == EventType.MANAGEMENT_PARTITION_MBEAN_UNREGISTER) {
         eventPayload.put("destroy_partition", Boolean.TRUE);
      }

      InternalEvent event = new InternalEventImpl(eventType, eventPayload);
      this.debug("Sending event: type=" + eventType);
      internalEventbus.send(event);
   }

   private boolean isCreate(MethodInvocation inv) {
      return "onPostPartitionCreate".equals(this.getMethodName(inv));
   }

   private boolean isDelete(MethodInvocation inv) {
      return "onPreDeletePartition".equals(this.getMethodName(inv));
   }

   private String getMethodName(MethodInvocation inv) {
      Method method = inv.getMethod();
      return method == null ? null : method.getName();
   }

   private String getPartitionName(MethodInvocation inv) {
      PartitionMBean mbean = this.getArg(inv);
      return mbean == null ? null : mbean.getName();
   }

   private PartitionMBean getArg(MethodInvocation inv) {
      Object[] args = inv.getArguments();
      if (args != null && args.length == 1) {
         return !(args[0] instanceof PartitionMBean) ? null : (PartitionMBean)PartitionMBean.class.cast(args[0]);
      } else {
         return null;
      }
   }

   private void debug(String s) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug(this.getClass().getCanonicalName() + " ==> " + s);
      }

   }

   private void dump(MethodInvocation inv) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         PartitionMBean mbean = this.getArg(inv);
         String name = mbean == null ? "NULL" : mbean.getName();
         this.debug(" -- Intercepting Method " + this.getMethodName(inv) + ", PartitionMBean Name=" + name);
      }
   }
}
