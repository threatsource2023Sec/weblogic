package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.ContentType;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Transition;
import java.lang.reflect.Field;

public class DynamicValueDescriptor extends ValueDescriptor {
   private final int index;

   public DynamicValueDescriptor(String id, String name, String description, String relationKey, ContentType type, Transition transition, String constantPool, Class valueType, int index) throws InvalidValueException {
      super(id, name, description, relationKey, type, transition, constantPool, (Field)null, valueType);
      this.index = index;
   }

   public DynamicValueDescriptor(ValueDescriptor d, int index) {
      super(d);
      this.index = index;
   }

   public int getIndex() {
      return this.index;
   }

   public Object loadValue(Object reciever) throws IllegalArgumentException {
      return ((Object[])((Object[])reciever))[this.index];
   }

   public void setValue(Object reciever, Object value) throws IllegalArgumentException {
      ((Object[])((Object[])reciever))[this.index] = value;
   }
}
