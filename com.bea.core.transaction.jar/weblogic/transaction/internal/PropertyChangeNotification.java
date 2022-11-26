package weblogic.transaction.internal;

import java.util.Arrays;

public class PropertyChangeNotification extends Notification {
   protected String name;
   protected Object oldValue;
   protected Object newValue;

   public PropertyChangeNotification(Object source, long sequenceNumber, long timeStamp, String message, String name, Object oldValue, Object newValue) {
      super(source, sequenceNumber, timeStamp, message);
      this.name = name;
      this.oldValue = oldValue;
      this.newValue = newValue;
   }

   public String getName() {
      return this.name;
   }

   public Object getOldValue() {
      return this.oldValue;
   }

   public Object getNewValue() {
      return this.newValue;
   }

   public String toString() {
      return "PropertyChangeNotification {name=" + this.name + ",oldValue=" + (this.oldValue instanceof String[] ? Arrays.toString((String[])((String[])this.oldValue)) : this.oldValue) + ",newValue=" + (this.newValue instanceof String[] ? Arrays.toString((String[])((String[])this.newValue)) : this.newValue) + "," + super.toString() + "}";
   }
}
