package weblogic.jms.common;

import java.util.Hashtable;

public class JMSOBSHelper {
   public static int convertSecurityPolicyToInt(String securityPolicy) {
      if (securityPolicy.equals("ThreadBased")) {
         return 0;
      } else if (securityPolicy.equals("ObjectBasedDelegated")) {
         return 1;
      } else if (securityPolicy.equals("ObjectBasedAnonymous")) {
         return 2;
      } else if (securityPolicy.equals("ObjectBasedThread")) {
         return 3;
      } else {
         throw new IllegalArgumentException("Invalid SecurityPolicy: " + securityPolicy);
      }
   }

   public static String convertSecurityPolicyToString(int securityPolicy) {
      switch (securityPolicy) {
         case 0:
            return "ThreadBased";
         case 1:
            return "ObjectBasedDelegated";
         case 2:
            return "ObjectBasedAnonymous";
         case 3:
            return "ObjectBasedThread";
         default:
            throw new IllegalArgumentException("Invalid SecurityPolicy: " + securityPolicy);
      }
   }

   public static Hashtable filterProperties(Hashtable properties) {
      if (properties != null && properties.containsKey("java.naming.security.credentials")) {
         Hashtable filteredProperties = (Hashtable)properties.clone();
         filteredProperties.put("java.naming.security.credentials", "******");
         return filteredProperties;
      } else {
         return properties;
      }
   }
}
