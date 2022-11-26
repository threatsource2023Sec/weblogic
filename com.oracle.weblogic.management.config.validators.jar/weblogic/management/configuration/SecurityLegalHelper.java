package weblogic.management.configuration;

import com.bea.security.utils.keystore.KssAccessor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.Kernel;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.net.ConnectionFilter;
import weblogic.security.net.ConnectionFilterRulesListener;
import weblogic.security.net.ConnectionFilterService;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.LocatorUtilities;

@Service
@ContractsProvided({DomainMBeanValidator.class})
public final class SecurityLegalHelper implements DomainMBeanValidator {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static boolean isLegalFilterRules(SecurityConfigurationMBean aMBean, String[] filterList) {
      return isLegalFilterRules(aMBean.getConnectionFilter(), filterList);
   }

   private static boolean isLegalFilterRules(String filterClass, String[] filterList) {
      if (filterClass != null) {
         ConnectionFilterService connectionService = (ConnectionFilterService)LocatorUtilities.getService(ConnectionFilterService.class);
         if (connectionService.getConnectionFilterEnabled()) {
            ConnectionFilter filterObject = connectionService.getConnectionFilter();

            try {
               Class c = Class.forName(filterClass);
               if (ConnectionFilterRulesListener.class.isAssignableFrom(c)) {
                  try {
                     String methodName = "checkRules";
                     Class[] params = new Class[]{String[].class};
                     Method setter = c.getMethod(methodName, params);
                     Object[] parameters = new Object[]{filterList};
                     setter.invoke(filterObject, parameters);
                  } catch (InvocationTargetException var9) {
                     Throwable thr = var9.getTargetException();
                     if (thr.toString().startsWith("java.text.ParseException")) {
                        String error = thr.getMessage();
                        SecurityLogger.logUpdateFilterWarn(error);
                        throw new IllegalArgumentException(error + "  Rules will not be updated.");
                     }

                     throw var9;
                  }
               }
            } catch (Throwable var10) {
               IllegalArgumentException e = new IllegalArgumentException("problem with connection filter. Exception:" + var10);
               e.initCause(var10);
               throw e;
            }
         }
      }

      return true;
   }

   public static void validateSecurityConfiguration(SecurityConfigurationMBean security) throws IllegalArgumentException {
      boolean legal = isLegalFilterRules(security, security.getConnectionFilterRules());
      if (!legal) {
         throw new IllegalArgumentException("ConnectionFilterRules string is not valid");
      }
   }

   public static void validatePrincipalName(String principalName) throws IllegalArgumentException {
      if (Kernel.isServer()) {
         AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelId);
         if (SubjectUtils.isAdminPrivilegeEscalation(currSubject, principalName, (String)null)) {
            throw new IllegalArgumentException("The principal name : " + principalName + " has higher privileges than the current user: " + currSubject + ". Hence the current user cannot set the principal name. Modify the principal name with admin privileged user.");
         }
      }
   }

   public static void validateUseKSSForDemo(boolean useKss) {
      if (useKss) {
         if (Kernel.isServer() && !KssAccessor.isKssAvailable()) {
            throw new IllegalArgumentException("Unable to use KSS for Demo Key Stores, KSS is unavailable.");
         }
      }
   }

   public void validate(DomainMBean domain) {
   }

   public static void validateSecureMode(SecureModeMBean security) throws IllegalArgumentException {
      DomainMBean domain = (DomainMBean)security.getParentBean().getParentBean();
      if (security.isSecureModeEnabled() && !domain.isProductionModeEnabled()) {
         throw new IllegalArgumentException("Your domain must be in production mode to enable secure mode");
      }
   }
}
