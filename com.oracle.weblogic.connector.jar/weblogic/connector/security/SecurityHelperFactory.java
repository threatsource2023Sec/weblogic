package weblogic.connector.security;

public class SecurityHelperFactory {
   private static SecurityHelper instance = new WLSSecurityHelper();

   public static SecurityHelper getInstance() {
      return instance;
   }
}
