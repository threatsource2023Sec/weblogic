package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.policy.IdReference;
import java.util.StringTokenizer;

public class ConstraintUtil {
   private static final int COMPARE_EQUAL = 0;
   private static final int COMPARE_LESS = 1;
   private static final int COMPARE_GREATER = 2;

   public static boolean meetsConstraint(String version, IdReference reference) throws DocumentParseException {
      return matches(version, reference.getVersion()) && isEarlier(version, reference.getLatestVersion()) && isLater(version, reference.getEarliestVersion());
   }

   public static boolean matches(String version, String constraint) throws DocumentParseException {
      return compareHelper(version, constraint, 0);
   }

   public static boolean isEarlier(String version, String constraint) throws DocumentParseException {
      return compareHelper(version, constraint, 1);
   }

   public static boolean isLater(String version, String constraint) throws DocumentParseException {
      return compareHelper(version, constraint, 2);
   }

   private static boolean compareHelper(String version, String constraint, int type) throws DocumentParseException {
      if (constraint == null) {
         return true;
      } else if (version == null) {
         return false;
      } else {
         StringTokenizer vtok = new StringTokenizer(version, ".");
         StringTokenizer ctok = new StringTokenizer(constraint, ".");

         String c;
         String v;
         do {
            if (!vtok.hasMoreTokens()) {
               if (ctok.hasMoreTokens()) {
                  return type == 1;
               }

               return true;
            }

            if (!ctok.hasMoreTokens()) {
               return type == 2;
            }

            c = ctok.nextToken();
            if (c.equals("+")) {
               return true;
            }

            v = vtok.nextToken();
         } while(c.equals("*") || v.equals(c));

         if (type == 0) {
            return false;
         } else {
            try {
               int cint = Integer.parseInt(c);
               int vint = Integer.parseInt(v);
               return type == 1 ? vint <= cint : vint >= cint;
            } catch (NumberFormatException var9) {
               throw new DocumentParseException(var9);
            }
         }
      }
   }
}
