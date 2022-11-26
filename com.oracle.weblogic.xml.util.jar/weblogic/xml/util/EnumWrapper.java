package weblogic.xml.util;

import java.util.Enumeration;

public class EnumWrapper implements Enumeration {
   public static EnumWrapper emptyEnumeration = new EnumWrapper((Object)null);
   boolean done;
   Object object;

   public EnumWrapper(Object o) {
      this.object = o;
      this.done = false;
   }

   public boolean hasMoreElements() {
      return !this.done && this.object != null;
   }

   public Object nextElement() {
      if (!this.done) {
         this.done = true;
         return this.object;
      } else {
         return null;
      }
   }
}
