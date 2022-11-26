package weblogic.management.mbeanservers.edit.internal;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanServer;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerService;

@Service
@Named
@RunLevel(10)
public final class EditServerService extends BaseEditServerService {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static EditServerService singleton;
   @Inject
   @Named("EditSessionConfigurationManagerService")
   private ServerService dependencyOnEditSessionConfigurationManagerService;

   private static void setSingleton(EditServerService one) {
      singleton = one;
   }

   public EditServerService() {
      setSingleton(this);
   }

   public static WLSModelMBeanContext getContext() {
      return singleton.context;
   }

   protected String getEditServiceJndiName() {
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      return this.getEditSessionServerManager().constructJndiName(partitionName, "default");
   }

   protected String getEditServiceObjectName() {
      return EditServiceMBean.OBJECT_NAME;
   }

   protected EditServiceMBean createEditService(WLSModelMBeanContext context) {
      return new EditServiceMBeanImpl(this.getEditAccess(), context);
   }

   protected EditAccess getEditAccess() {
      return ManagementServiceRestricted.getEditAccess(kernelId);
   }

   protected InternalEvent createEvent(InternalEvent.EventType eventType) {
      Map payload = new HashMap();
      payload.put(MBeanServer.class.getName(), this.getMBeanServer());
      payload.put("session_name", "default");
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      payload.put("partition_name", partitionName);
      return new InternalEventImpl(eventType, payload);
   }
}
