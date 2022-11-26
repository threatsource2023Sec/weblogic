package weblogic.j2eeclient;

import java.io.Serializable;
import java.lang.reflect.Field;

public final class Descriptor implements Serializable {
   private static final long serialVersionUID = -9180444863326113384L;
   static final Field NAME;
   static final Field VALUE;
   static final Field TYPE;
   public String name;
   public String value;
   public String type;

   public String toString() {
      return "<name=" + this.name + ", value=" + this.value + ", type=" + this.type + ">";
   }

   static {
      try {
         NAME = Descriptor.class.getField("name");
         VALUE = Descriptor.class.getField("value");
         TYPE = Descriptor.class.getField("type");
      } catch (NoSuchFieldException var1) {
         throw new Error(var1);
      }
   }
}
