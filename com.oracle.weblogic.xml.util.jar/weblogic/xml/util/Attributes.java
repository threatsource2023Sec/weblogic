package weblogic.xml.util;

import java.util.Vector;

public class Attributes extends ReadOnlyAttributes {
   public Attributes() {
   }

   public Attributes(int elems) {
      super(elems);
   }

   public Attributes(ReadOnlyAttributes attrs) {
      Vector v = attrs.attributes;
      if (v != null) {
         int l = v.size();
         this.attributes = new Vector(l);
         this.attributes.setSize(l);

         for(int i = 0; i < l; ++i) {
            Attribute a = (Attribute)v.elementAt(i);
            this.attributes.setElementAt(new Attribute(a.name, a.getValue()), i);
         }
      }

   }

   public Attributes(Vector v) {
      super(v);
   }

   public void remove(Name name) {
      Attribute a = this.lookup(name);
      if (a != null) {
         this.attributes.removeElement(a);
      }

   }

   public Object put(Name name, Object value) {
      Attribute a = this.lookup(name);
      Object o = null;
      if (a != null) {
         o = a.getValue();
         a.setValue(value);
      } else {
         this.attributes.addElement(new Attribute(name, value));
      }

      return o;
   }

   public Object put(Attribute v) {
      Object o = null;
      Attribute a = this.lookup(v.getName());
      if (a != null) {
         o = a.getValue();
         this.attributes.removeElement(a);
      }

      this.attributes.addElement(v);
      return o;
   }

   public void removeAll() {
      this.attributes.removeAllElements();
   }
}
