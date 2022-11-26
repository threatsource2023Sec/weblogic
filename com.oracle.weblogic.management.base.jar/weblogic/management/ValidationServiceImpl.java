package weblogic.management;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.Operation;
import org.glassfish.hk2.api.ValidationInformation;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.api.Validator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import weblogic.management.internal.ConfigLogger;
import weblogic.utils.annotation.Secure;

@Singleton
public class ValidationServiceImpl implements ValidationService {
   private static final Permission DEFAULT_REQUIRED_PERMISSION = new RuntimePermission("weblogic.kernelPermission");
   private static final Filter FILTER = new Filter() {
      public boolean matches(Descriptor d) {
         return d.getQualifiers().contains(Secure.class.getName());
      }
   };
   private final Validator VALIDATOR = new ValidatorImpl();

   public Filter getLookupFilter() {
      return FILTER;
   }

   public Validator getValidator() {
      return this.VALIDATOR;
   }

   private static class ValidatorImpl implements Validator {
      private ValidatorImpl() {
      }

      private boolean checkBinder(ValidationInformation info) {
         ActiveDescriptor descriptor = info.getCandidate();
         return ValidationServiceImpl.FILTER.matches(descriptor) ? checkPerm(descriptor, getVariablePermission(descriptor)) : true;
      }

      private boolean checkUnBinder(ValidationInformation info) {
         ActiveDescriptor descriptor = info.getCandidate();
         return ValidationServiceImpl.FILTER.matches(descriptor) ? checkPerm(descriptor, getVariablePermission(descriptor)) : true;
      }

      private boolean checkLookupAPI(ValidationInformation info) {
         return checkPerm(info.getCandidate(), getVariablePermission(info.getCandidate()));
      }

      private static Permission getVariablePermission(ActiveDescriptor descriptor) {
         if (descriptor == null) {
            return ValidationServiceImpl.DEFAULT_REQUIRED_PERMISSION;
         } else if (!descriptor.getQualifiers().contains(Secure.class.getName())) {
            return ValidationServiceImpl.DEFAULT_REQUIRED_PERMISSION;
         } else {
            String value = ServiceLocatorUtilities.getOneMetadataField(descriptor, "weblogic.required.permission");
            if (value == null) {
               return ValidationServiceImpl.DEFAULT_REQUIRED_PERMISSION;
            } else {
               return (Permission)("weblogic.kernelPermission".equals(value) ? ValidationServiceImpl.DEFAULT_REQUIRED_PERMISSION : new RuntimePermission(value));
            }
         }
      }

      private boolean checkInjectionPoint(final ValidationInformation info) {
         Injectee injectee = info.getInjectee();
         final Class injecteeClass = injectee.getInjecteeClass();
         final ProtectionDomain pd = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction() {
            public ProtectionDomain run() {
               return injecteeClass.getProtectionDomain();
            }
         });
         final Permission permission = getVariablePermission(info.getCandidate());
         boolean retVal = pd.implies(permission);
         if (!retVal) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  ConfigLogger.logInjectionFailure(info.getCandidate().toString(), injecteeClass.getName(), pd.toString(), permission.toString());
                  return null;
               }
            });
         }

         return retVal;
      }

      private boolean checkLookup(ValidationInformation info) {
         return info.getInjectee() == null ? this.checkLookupAPI(info) : this.checkInjectionPoint(info);
      }

      public boolean validate(ValidationInformation info) {
         Operation operation = info.getOperation();
         if (Operation.BIND.equals(operation)) {
            return this.checkBinder(info);
         } else if (Operation.UNBIND.equals(operation)) {
            return this.checkUnBinder(info);
         } else {
            return Operation.LOOKUP.equals(operation) ? this.checkLookup(info) : false;
         }
      }

      private static boolean checkPerm(final ActiveDescriptor descriptor, final Permission permission) {
         try {
            AccessController.checkPermission(permission);
            return true;
         } catch (final AccessControlException var3) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  ConfigLogger.logServiceValidationPermissionFailure(descriptor.toString(), permission.toString(), var3);
                  return null;
               }
            });
            return false;
         }
      }

      // $FF: synthetic method
      ValidatorImpl(Object x0) {
         this();
      }
   }
}
