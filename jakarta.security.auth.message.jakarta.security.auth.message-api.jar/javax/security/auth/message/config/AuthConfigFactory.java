package javax.security.auth.message.config;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.Security;
import java.security.SecurityPermission;
import java.util.Map;

public abstract class AuthConfigFactory {
   private static AuthConfigFactory factory = null;
   public static final String DEFAULT_FACTORY_SECURITY_PROPERTY = "authconfigprovider.factory";
   private static final String PROVIDER_SECURITY_PROPERTY = "authconfigfactory.provider";
   public static final String GET_FACTORY_PERMISSION_NAME = "getProperty.authconfigprovider.factory";
   public static final String SET_FACTORY_PERMISSION_NAME = "setProperty.authconfigprovider.factory";
   public static final String PROVIDER_REGISTRATION_PERMISSION_NAME = "setProperty.authconfigfactory.provider";
   public static final SecurityPermission getFactorySecurityPermission = new SecurityPermission("getProperty.authconfigprovider.factory");
   public static final SecurityPermission setFactorySecurityPermission = new SecurityPermission("setProperty.authconfigprovider.factory");
   public static final SecurityPermission providerRegistrationSecurityPermission = new SecurityPermission("setProperty.authconfigfactory.provider");

   private static void checkPermission(Permission permission) throws SecurityException {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null) {
         securityManager.checkPermission(permission);
      }

   }

   public static synchronized AuthConfigFactory getFactory() {
      checkPermission(getFactorySecurityPermission);
      if (factory == null) {
         final String className = Security.getProperty("authconfigprovider.factory");
         if (className != null) {
            checkPermission(setFactorySecurityPermission);

            try {
               factory = (AuthConfigFactory)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                  public AuthConfigFactory run() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
                     return (AuthConfigFactory)Class.forName(className, true, Thread.currentThread().getContextClassLoader()).newInstance();
                  }
               });
            } catch (PrivilegedActionException var2) {
               throw new SecurityException(var2.getException());
            }
         }
      }

      return factory;
   }

   public static synchronized void setFactory(AuthConfigFactory factory) {
      checkPermission(setFactorySecurityPermission);
      AuthConfigFactory.factory = factory;
   }

   public abstract AuthConfigProvider getConfigProvider(String var1, String var2, RegistrationListener var3);

   public abstract String registerConfigProvider(String var1, Map var2, String var3, String var4, String var5);

   public abstract String registerConfigProvider(AuthConfigProvider var1, String var2, String var3, String var4);

   public abstract boolean removeRegistration(String var1);

   public abstract String[] detachListener(RegistrationListener var1, String var2, String var3);

   public abstract String[] getRegistrationIDs(AuthConfigProvider var1);

   public abstract RegistrationContext getRegistrationContext(String var1);

   public abstract void refresh();

   public interface RegistrationContext {
      String getMessageLayer();

      String getAppContext();

      String getDescription();

      boolean isPersistent();
   }
}
