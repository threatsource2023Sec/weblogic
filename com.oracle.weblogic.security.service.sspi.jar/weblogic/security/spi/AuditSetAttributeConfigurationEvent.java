package weblogic.security.spi;

public interface AuditSetAttributeConfigurationEvent extends AuditConfigurationEvent {
   String SETATTRIBUTE_EVENT = "SetAttribute Configuration Audit Event";

   String getAttributeName();

   Object getOldValue();

   Object getNewValue();
}
