package weblogic.servlet.jsp;

import java.lang.reflect.Method;

class MethodEntry {
   Method m;
   Class[] paramTypes;
   String paramPart;

   public boolean equals(Object o) {
      if (o != null && o instanceof MethodEntry) {
         MethodEntry m = (MethodEntry)o;
         return this.paramPart.equals(m.paramPart);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.paramPart.hashCode();
   }
}
