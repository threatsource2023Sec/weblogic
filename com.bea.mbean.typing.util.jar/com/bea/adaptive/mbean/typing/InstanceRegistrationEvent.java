package com.bea.adaptive.mbean.typing;

import javax.management.ObjectName;

public class InstanceRegistrationEvent {
   static final String REGISTRATION_EVENT = "JMX.mbean.registered";
   static final String UNREGISTRATION_EVENT = "JMX.mbean.unregistered";
   private EventType event;
   private ObjectName objectName;
   private int hash;

   public InstanceRegistrationEvent(ObjectName name, EventType eventType) {
      this.event = InstanceRegistrationEvent.EventType.UNKNOWN;
      this.objectName = name;
      this.event = eventType;
   }

   public InstanceRegistrationEvent(ObjectName name, String eventType) {
      this(name, InstanceRegistrationEvent.EventType.createEventType(eventType));
   }

   EventType getEvent() {
      return this.event;
   }

   int getEventVal() {
      return this.event.ordinal();
   }

   ObjectName getObjectName() {
      return this.objectName;
   }

   public int hashCode() {
      if (this.hash == 0) {
         this.hash = this.objectName.getCanonicalName().hashCode();
      }

      return this.hash;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else {
         return obj instanceof InstanceRegistrationEvent ? this.objectName.equals(((InstanceRegistrationEvent)obj).getObjectName()) : false;
      }
   }

   public boolean isAdd() {
      return this.event == InstanceRegistrationEvent.EventType.ADD;
   }

   public boolean isDelete() {
      return this.event == InstanceRegistrationEvent.EventType.DELETE;
   }

   public static enum EventType {
      UNKNOWN,
      ADD,
      DELETE;

      public static EventType createEventType(String eventString) {
         if (eventString.equals("JMX.mbean.registered")) {
            return ADD;
         } else {
            return eventString.equals("JMX.mbean.unregistered") ? DELETE : UNKNOWN;
         }
      }
   }
}
