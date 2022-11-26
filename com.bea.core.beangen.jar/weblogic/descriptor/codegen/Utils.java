package weblogic.descriptor.codegen;

public class Utils {
   public static String plural(String n) {
      if (n.endsWith("y")) {
         if (!n.equalsIgnoreCase("key") && !n.endsWith("Key")) {
            n = n.substring(0, n.length() - 1) + "ies";
            return n;
         } else {
            return n + "s";
         }
      } else if (n.endsWith("ss")) {
         n = n + "es";
         return n;
      } else if (n.endsWith("ch")) {
         n = n + "es";
         return n;
      } else if (n.endsWith("x")) {
         n = n + "es";
         return n;
      } else if (n.endsWith("ies")) {
         return n;
      } else if (n.endsWith("s")) {
         if (!n.endsWith("Params") && !n.endsWith("Parms")) {
            if (n.endsWith("tions")) {
               return n;
            } else {
               n = n + "es";
               return n;
            }
         } else {
            return n;
         }
      } else {
         n = n + "s";
         return n;
      }
   }

   public static String singular(String n) {
      if (n.endsWith("ies")) {
         return n.substring(0, n.length() - 3) + "y";
      } else if (n.endsWith("ses")) {
         return n.endsWith("Cases") ? n.substring(0, n.length() - 1) : n.substring(0, n.length() - 2);
      } else {
         return n.endsWith("s") ? n.substring(0, n.length() - 1) : n;
      }
   }
}
