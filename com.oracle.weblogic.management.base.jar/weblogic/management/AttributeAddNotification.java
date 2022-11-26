package weblogic.management;

import javax.management.ObjectName;
import weblogic.management.internal.WebLogicAttributeChangeNotification;

/** @deprecated */
@Deprecated
public final class AttributeAddNotification extends WebLogicAttributeChangeNotification {
   private static final long serialVersionUID = 823184172044137571L;

   /** @deprecated */
   @Deprecated
   public AttributeAddNotification(ObjectName source, String name, Object addedValue) {
      super(source, name, (Object)null, addedValue);
   }

   public AttributeAddNotification(ObjectName source, String name, String type, Object addedValue) {
      super(source, name, type, (Object)null, addedValue);
   }

   public AttributeAddNotification(ObjectName objectName, long seqno, long time, String s, String propertyName, String propertyClassName, Object added) {
      super(objectName, seqno, time, s, propertyName, propertyClassName, (Object)null, added);
   }

   public Object getAddedValue() {
      return this.getNewValue();
   }
}
