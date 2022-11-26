package weblogic.management.internal;

import java.util.Date;
import javax.management.AttributeChangeNotification;
import javax.management.ObjectName;

public class WebLogicAttributeChangeNotification extends AttributeChangeNotification {
   private static final long serialVersionUID = -2892133712717201347L;
   private static long sequenceNumber = 0L;

   public WebLogicAttributeChangeNotification(ObjectName source, String name, Object oldValue, Object newValue) {
      this(source, generateSequenceNumber(), (new Date()).getTime(), "WebLogic MBean Attribute change for " + name + " from " + oldValue + " to " + newValue, name, "UNKNOWN TYPE", oldValue, newValue);
   }

   public WebLogicAttributeChangeNotification(ObjectName source, String name, String type, Object oldValue, Object newValue) {
      this(source, generateSequenceNumber(), (new Date()).getTime(), "WebLogic MBean Attribute change for " + name + " from " + oldValue + " to " + newValue, name, type, oldValue, newValue);
   }

   public WebLogicAttributeChangeNotification(Object source, String msg, String name, String type, Object oldValue, Object newValue) {
      this(source, generateSequenceNumber(), (new Date()).getTime(), msg, name, type, oldValue, newValue);
   }

   protected WebLogicAttributeChangeNotification(Object source, long sequenceNumber, long timeStamp, String msg, String attributeName, String attributeType, Object oldValue, Object newValue) {
      super(source, sequenceNumber, timeStamp, msg, attributeName, attributeType, oldValue, newValue);
   }

   public static long getChangeNotificationCount() {
      return sequenceNumber;
   }

   private static synchronized long generateSequenceNumber() {
      return (long)(sequenceNumber++);
   }

   public String toString() {
      String o = this.getOldValue().toString();
      String n = this.getNewValue().toString();
      return this.getClass().getName() + ": " + this.getAttributeName() + " from " + o + " to " + n + " - " + super.toString();
   }
}
