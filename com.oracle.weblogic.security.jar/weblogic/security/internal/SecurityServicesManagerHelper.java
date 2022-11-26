package weblogic.security.internal;

import java.util.Hashtable;
import weblogic.security.spi.SecurityServices;

public class SecurityServicesManagerHelper {
   private static Hashtable securityServices = new Hashtable();

   protected static void registerSecurityServices(SecurityServices services, String realm) {
      securityServices.put(realm, services);
   }

   public static SecurityServices getSecurityServices(String realm) {
      return (SecurityServices)securityServices.get(realm);
   }
}
