package weblogic.security.principal;

import com.bea.common.security.ApiLogger;
import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class PrincipalConfigurationDelegate {
   private static final String WEBLOGIC_NAME = "weblogic.Name";
   private static PrincipalConfigurationDelegate instance = null;
   private static final String OPDELEGATEIMPL = "com.bea.common.security.principal.OPPrincipalConfigurationDelegateImpl";
   private static final String WLSDELEGATEIMPL = "weblogic.security.principal.WLSPrincipalConfigurationDelegateImpl";

   public static synchronized PrincipalConfigurationDelegate getInstance() {
      if (instance != null) {
         return instance;
      } else {
         boolean isOnWLS = false;

         try {
            isOnWLS = isOnWLS();
            if (isOnWLS) {
               try {
                  instance = (PrincipalConfigurationDelegate)Class.forName("weblogic.security.principal.WLSPrincipalConfigurationDelegateImpl", true, Thread.currentThread().getContextClassLoader()).newInstance();
               } catch (RuntimeException var4) {
                  instance = (PrincipalConfigurationDelegate)Class.forName("com.bea.common.security.principal.OPPrincipalConfigurationDelegateImpl", true, Thread.currentThread().getContextClassLoader()).newInstance();
               }
            } else {
               instance = (PrincipalConfigurationDelegate)Class.forName("com.bea.common.security.principal.OPPrincipalConfigurationDelegateImpl", true, Thread.currentThread().getContextClassLoader()).newInstance();
            }
         } catch (InstantiationException var6) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipalConfigurationDelegate(var6));
         } catch (IllegalAccessException var7) {
            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipalConfigurationDelegate(var7));
         } catch (ClassNotFoundException var8) {
            Exception exc = var8;
            if (isOnWLS) {
               try {
                  instance = (PrincipalConfigurationDelegate)Class.forName("com.bea.common.security.principal.OPPrincipalConfigurationDelegateImpl", true, Thread.currentThread().getContextClassLoader()).newInstance();
                  return instance;
               } catch (Exception var5) {
                  exc = var5;
               }
            }

            throw new IllegalStateException(ApiLogger.getUnableToInstantiatePrincipalConfigurationDelegate((Throwable)exc));
         }

         return instance;
      }
   }

   public abstract boolean isEqualsCaseInsensitive();

   public abstract boolean isEqualsCompareDnAndGuid();

   static boolean isOnWLS() {
      boolean isOnWLS = false;

      try {
         Class.forName("weblogic.version");
         String wlsName = getWlsName();
         if (wlsName != null && !wlsName.isEmpty()) {
            isOnWLS = true;
         }
      } catch (ClassNotFoundException var2) {
         isOnWLS = false;
      }

      return isOnWLS;
   }

   private static String getWlsName() {
      String wlsName = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public String run() {
            return System.getProperty("weblogic.Name");
         }
      });
      return wlsName;
   }
}
