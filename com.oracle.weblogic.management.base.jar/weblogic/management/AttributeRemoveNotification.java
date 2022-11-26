package weblogic.management;

import javax.management.ObjectName;
import weblogic.management.internal.WebLogicAttributeChangeNotification;

/** @deprecated */
@Deprecated
public final class AttributeRemoveNotification extends WebLogicAttributeChangeNotification {
   private static final long serialVersionUID = -7366904839966430571L;

   public AttributeRemoveNotification(ObjectName source, String name, Object removedValue) {
      super(source, name, removedValue, (Object)null);
   }

   public AttributeRemoveNotification(ObjectName source, String name, String type, Object removedValue) {
      super(source, name, type, removedValue, (Object)null);
   }

   public AttributeRemoveNotification(ObjectName objectName, long seqno, long time, String s, String propertyName, String propertyClassName, Object removedValue) {
      super(objectName, seqno, time, s, propertyName, propertyClassName, removedValue, (Object)null);
   }

   public Object getRemovedValue() {
      return this.getOldValue();
   }
}
