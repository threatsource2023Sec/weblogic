package weblogic.entitlement.util;

import weblogic.security.service.ContextHandler;

public class IdentityDomainUtil {
   private static final String ADMIN_IDENTITY_DOMAIN = "com.oracle.contextelement.security.AdminIdentityDomain";
   private static final String RESOURCE_IDENTITY_DOMAIN = "com.oracle.contextelement.security.ResourceIdentityDomain";

   public static String fetchAdminIDD(ContextHandler context) {
      return fetchContextAttribute("com.oracle.contextelement.security.AdminIdentityDomain", context);
   }

   public static String fetchOwnerIDD(ContextHandler context) {
      return fetchContextAttribute("com.oracle.contextelement.security.ResourceIdentityDomain", context);
   }

   public static String fetchContextAttribute(String attrName, ContextHandler context) {
      String attrValue = null;
      if (context != null) {
         Object attrObj = context.getValue(attrName);
         if (attrObj != null) {
            attrValue = attrObj.toString();
         }
      }

      return attrValue;
   }

   public static boolean isMatch(String idd1, String idd2) {
      if (idd1 != null && !idd1.equals("") || idd2 != null && !idd2.equals("")) {
         return idd1 != null && idd1.equals(idd2);
      } else {
         return true;
      }
   }
}
