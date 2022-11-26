package weblogic.management.mbeanservers.edit.internal;

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServer;
import weblogic.management.eventbus.apis.InternalEvent;
import weblogic.management.eventbus.apis.InternalEventImpl;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.EditSessionServiceMBean;
import weblogic.management.provider.EditAccess;

public final class EditSessionServer extends BaseEditServerService {
   private final EditAccess editAccess;

   EditSessionServer(EditAccess editAccess) {
      super(false);
      this.editAccess = editAccess;
   }

   protected String getEditServiceJndiName() {
      return this.getEditSessionServerManager().constructJndiName(this.editAccess.getPartitionName(), this.editAccess.getEditSessionName());
   }

   protected String getEditServiceObjectName() {
      return EditSessionServiceMBean.OBJECT_NAME;
   }

   protected EditServiceMBean createEditService(WLSModelMBeanContext context) {
      return new EditSessionServiceMBeanImpl(this.getEditAccess(), context);
   }

   protected EditAccess getEditAccess() {
      return this.editAccess;
   }

   protected InternalEvent createEvent(InternalEvent.EventType eventType) {
      Map payload = new HashMap();
      payload.put(MBeanServer.class.getName(), this.getMBeanServer());
      payload.put("session_name", this.editAccess.getEditSessionName());
      payload.put("partition_name", this.editAccess.getPartitionName());
      return new InternalEventImpl(eventType, payload);
   }
}
