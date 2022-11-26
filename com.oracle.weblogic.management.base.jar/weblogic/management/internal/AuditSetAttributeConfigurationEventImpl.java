package weblogic.management.internal;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.AuditSetAttributeConfigurationEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditSetAttributeConfigurationEventImpl extends AuditConfigurationBaseEventImpl implements AuditSetAttributeConfigurationEvent {
   private static final String ATTRIBUTE_ATTR = "Attribute";
   private static final String FROM_ATTR = "From";
   private static final String TO_ATTR = "To";
   private String attrName;
   private Object oldValue;
   private Object newValue;

   public AuditSetAttributeConfigurationEventImpl(AuditSeverity severity, AuthenticatedSubject subject, String objectName, String attributeName, Object oldval, Object newval) {
      super(severity, "SetAttribute Configuration Audit Event", subject, objectName);
      this.attrName = attributeName;
      this.oldValue = oldval;
      this.newValue = newval;
   }

   public String getAttributeName() {
      return this.attrName;
   }

   public Object getOldValue() {
      return this.oldValue;
   }

   public Object getNewValue() {
      return this.newValue;
   }

   public void writeAttributes(StringBuffer buf) {
      super.writeAttributes(buf);
      buf.append("<");
      buf.append("Attribute");
      buf.append(" = ");
      buf.append(this.attrName);
      buf.append("><");
      buf.append("From");
      buf.append(" = ");
      buf.append(this.oldValue);
      buf.append("><");
      buf.append("To");
      buf.append(" = ");
      buf.append(this.newValue);
      buf.append(">");
   }
}
