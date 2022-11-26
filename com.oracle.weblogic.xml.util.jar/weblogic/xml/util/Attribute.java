package weblogic.xml.util;

public class Attribute {
   Name name;
   Object value;

   public Attribute() {
   }

   public Attribute(Name n, Object v) {
      this.name = n;
      this.value = v;
   }

   public Name getName() {
      return this.name;
   }

   public Object getValue() {
      return this.value;
   }

   void setValue(Object o) {
      this.value = o;
   }
}
