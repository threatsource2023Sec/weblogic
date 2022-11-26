package weblogic.management.eventbus.apis;

import java.util.Map;

public interface InternalEvent {
   long getId();

   EventType getType();

   Map getPayload();

   Object getPayloadEntry(String var1);

   public static enum EventType {
      MANAGEMENT_EDIT_SESSION_COMMITTED_SUCCESS("weblogic.management.provider.internal.EDIT_SESSION_COMMITTED_SUCCESS"),
      MANAGEMENT_MBEANSERVERS_EDIT_MBEANSERVER_POST_INITIALIZATION("weblogic.management.mbeanservers.EDIT_MBEANSERVER_POST_INITIALIZATION"),
      MANAGEMENT_MBEANSERVERS_DOMAIN_MBEANSERVER_POST_INITIALIZATION("weblogic.management.mbeanservers.DOMAIN_MBEANSERVER_POST_INITIALIZATION"),
      MANAGEMENT_MBEANSERVERS_RUNTIME_MBEANSERVER_POST_INITIALIZATION("weblogic.management.mbeanservers.RUNTIME_MBEANSERVER_POST_INITIALIZATION"),
      MANAGEMENT_MBEANSERVERS_EDIT_MBEANSERVER_SET("weblogic.management.mbeanservers.EDIT_MBEANSERVER_SET"),
      MANAGEMENT_EDIT_SESSION_STARTED("weblogic.management.provider.internal.EDIT_SESSION_STARTED"),
      MANAGEMENT_EDIT_SESSION_STOPPED("weblogic.management.provider.internal.EDIT_SESSION_STOPPED"),
      MANAGEMENT_EDIT_SESSION_CANCELLED("weblogic.management.provider.internal.EDIT_SESSION_CANCELLED"),
      MANAGEMENT_EDIT_SESSION_DESTROYED("weblogic.management.provider.internal.EDIT_SESSION_DESTROYED"),
      MANAGEMENT_EDIT_SESSION_UNDO_CHANGES("weblogic.management.provider.internal.EDIT_SESSION_UNDO_CHANGES"),
      MANAGEMENT_PARTITION_MBEAN_REGISTER("weblogic.management.partition.MBEAN_REGISTER"),
      MANAGEMENT_PARTITION_MBEAN_UNREGISTER("weblogic.management.partition.MBEAN_UNREGISTER");

      private final String type;

      private EventType(String type) {
         this.type = type;
      }

      public String toString() {
         return new String(this.type);
      }
   }
}
