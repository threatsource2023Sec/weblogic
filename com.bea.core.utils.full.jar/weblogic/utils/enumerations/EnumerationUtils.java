package weblogic.utils.enumerations;

import java.util.Enumeration;
import java.util.Hashtable;

public class EnumerationUtils {
   private EnumerationUtils() {
   }

   public static Enumeration union(Enumeration[] enums) {
      Hashtable set = new Hashtable();

      for(int i = 0; i < enums.length; ++i) {
         if (enums[i] != null) {
            while(enums[i].hasMoreElements()) {
               Object elem = enums[i].nextElement();
               if (!set.containsKey(elem)) {
                  set.put(elem, elem);
               }
            }
         }
      }

      return set.keys();
   }

   public static String toString(Enumeration enum_, String sep) {
      StringBuffer buf = new StringBuffer();
      boolean more = enum_.hasMoreElements();

      while(more) {
         buf.append(enum_.nextElement().toString());
         if (more = enum_.hasMoreElements()) {
            buf.append(sep);
         }
      }

      return buf.toString();
   }

   public static String toString(Enumeration enum_) {
      return toString(enum_, ", ");
   }
}
