package weblogic.security;

import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

public abstract class SecurityEnvironment {
   private static SecurityEnvironment singleton;

   public static SecurityEnvironment getSecurityEnvironment() {
      if (singleton == null) {
         try {
            singleton = (SecurityEnvironment)Class.forName("weblogic.security.WLSSecurityEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            try {
               singleton = (SecurityEnvironment)Class.forName("weblogic.security.ClientSecurityEnvironmentImpl").newInstance();
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2.toString());
            }
         }
      }

      return singleton;
   }

   public static void setSecurityEnvironment(SecurityEnvironment helper) {
      singleton = helper;
   }

   public abstract Logger getServerLogger();

   public abstract void decrementOpenSocketCount(SSLSocket var1);
}
