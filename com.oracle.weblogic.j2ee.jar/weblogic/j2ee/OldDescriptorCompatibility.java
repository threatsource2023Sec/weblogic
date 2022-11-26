package weblogic.j2ee;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OldDescriptorCompatibility {
   public static final Map CANON_VALUES = new HashMap();

   public static String canonicalize(String eltName, String value) {
      Collection canVals = (Collection)CANON_VALUES.get(eltName);
      if (canVals == null) {
         return null;
      } else {
         Iterator i = canVals.iterator();

         String cval;
         do {
            if (!i.hasNext()) {
               return null;
            }

            cval = (String)i.next();
         } while(!value.equalsIgnoreCase(cval));

         return cval;
      }
   }

   static {
      CANON_VALUES.put("transaction-support", Arrays.asList("NoTransaction", "LocalTransaction", "XATransaction"));
      CANON_VALUES.put("res-auth", Arrays.asList("Container", "Application"));
      CANON_VALUES.put("cmp-version", Arrays.asList("1.x", "2.x"));
      CANON_VALUES.put("method-intf", Arrays.asList("Home", "Remote", "LocalHome", "Local"));
      CANON_VALUES.put("multiplicity", Arrays.asList("One", "Many"));
      CANON_VALUES.put("persistence-type", Arrays.asList("Bean", "Container"));
      CANON_VALUES.put("reentrant", Arrays.asList("true", "false"));
      CANON_VALUES.put("result-type-mapping", Arrays.asList("Local", "Remote"));
      CANON_VALUES.put("session-type", Arrays.asList("Stateful", "Stateless"));
      CANON_VALUES.put("ejb-ref-type", Arrays.asList("Session", "Entity"));
      CANON_VALUES.put("trans-attribute", Arrays.asList("NotSupported", "Supports", "Required", "RequiresNew", "Mandatory", "Never"));
      CANON_VALUES.put("transaction-type", Arrays.asList("Bean", "Container"));
      CANON_VALUES.put("res-sharing-scope", Arrays.asList("Shareable", "Unshareable"));
      CANON_VALUES.put("transport-guarantee", Arrays.asList("NONE", "INTEGRAL", "CONFIDENTIAL"));
      CANON_VALUES.put("http-method", Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "TRACE"));
   }
}
