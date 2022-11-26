package weblogic.xml.util;

import java.util.Enumeration;
import java.util.Vector;

public class ReadOnlyAttributes {
   Vector attributes;

   public ReadOnlyAttributes() {
      this.attributes = new Vector();
   }

   public ReadOnlyAttributes(Vector v) {
      this.attributes = v;
   }

   public ReadOnlyAttributes(int elems) {
      this.attributes = new Vector(elems);
   }

   public int size() {
      return this.attributes.size();
   }

   public Object get(Name name) {
      Attribute a = this.lookup(name);
      return a == null ? null : a.getValue();
   }

   public Enumeration attributes() {
      return this.attributes.elements();
   }

   public Attribute lookup(Name name) {
      Enumeration e = this.attributes.elements();

      Attribute a;
      do {
         if (!e.hasMoreElements()) {
            return null;
         }

         a = (Attribute)e.nextElement();
      } while(a.name != name);

      return a;
   }

   public String toString() {
      return this.getClass().getName();
   }
}
